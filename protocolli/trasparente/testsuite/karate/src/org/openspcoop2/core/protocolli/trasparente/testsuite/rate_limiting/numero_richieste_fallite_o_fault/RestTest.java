package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_fallite_o_fault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.HeaderValues;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.json.JsonPathExpressionEngine;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

import net.minidev.json.JSONObject;

public class RestTest extends ConfigLoader {
	
	final static int maxRequests = 5;
	final static int toFailRequests = 4;
	final static int totalRequests = maxRequests + toFailRequests;
	
	@Test
	public void perMinutoErogazione() throws Exception {
		testErogazione("RichiesteFalliteFaultRest", PolicyAlias.MINUTO);
	}

	
	public void testErogazione(String erogazione, PolicyAlias policy) throws Exception {
		
		// Facciamo un batch di richieste fallite e un batch di richieste con fault,
		// che devono essere tutte conteggiate
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		String path = Utils.getPolicyPath(policy);
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		int toFail = maxRequests/2;
		int toFault = maxRequests-toFail;
		
		HttpRequest toFailRequest = new HttpRequest();
		toFailRequest.setContentType("application/json");
		toFailRequest.setMethod(HttpRequestMethod.GET);
		toFailRequest.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/"+erogazione+"/v1/"+path+"?returnCode=500");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(toFailRequest, toFail);
		
		HttpRequest toFaultRequest = new HttpRequest();
		toFaultRequest.setContentType("application/json");
		toFaultRequest.setMethod(HttpRequestMethod.GET);
		toFaultRequest.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/"+erogazione+"/v1/"+path+"?problem=true");
		
		responses.addAll(Utils.makeParallelRequests(toFaultRequest, toFault));

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse>  blockedResponses = Utils.makeParallelRequests(toFailRequest, toFailRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(blockedResponses, windowSize);		
	}
	
	
	private void checkOkRequests(Vector<HttpResponse> responses, int windowSize) {
		// Delle richieste ok Controllo lo header *-Limit, *-Reset e lo status code
		
		responses.forEach( r -> {
			
			Utils.checkXLimitHeader(r.getHeader(Headers.FailedOrFaultLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FailedOrFaultLimit), maxRequests, windowMap);
			}
			assertNotEquals(null, Integer.valueOf(r.getHeader(Headers.FailedOrFaultReset)));
			assertEquals(500, r.getResultHTTPOperation());			
		});
	}
	
	private void checkFailedRequests(Vector<HttpResponse> responses, int windowSize) throws Exception {
		
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		for (var r: responses) {
			Utils.checkXLimitHeader(r.getHeader(Headers.FailedOrFaultLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FailedOrFaultLimit), maxRequests, windowMap);
			}
			assertNotEquals(null, Integer.valueOf(r.getHeader(Headers.FailedOrFaultReset)));
			assertEquals(429, r.getResultHTTPOperation());
			
			JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(r.getContent()));
			
			assertEquals("https://govway.org/handling-errors/429/LimitExceeded.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
			assertEquals("LimitExceeded", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
			assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
			assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));	
			assertEquals("Limit exceeded detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
			
			assertEquals("0", r.getHeader(Headers.FailedOrFaultRemaining));
			assertEquals(HeaderValues.LimitExceeded, r.getHeader(Headers.GovWayTransactionErrorType));
			assertEquals(HeaderValues.ReturnCodeTooManyRequests, r.getHeader(Headers.ReturnCode));
			assertNotEquals(null, r.getHeader(Headers.RetryAfter));
		}	
	}
}
