package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_fault;

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
import org.openspcoop2.utils.transport.http.HttpUtilities;

import net.minidev.json.JSONObject;

public class RestTest extends ConfigLoader {

	final static int maxRequests = 5;
	final static int toFailRequests = 4;
	final static int totalRequests = maxRequests + toFailRequests;
	
	@Test
	public void perMinutoErogazione() throws Exception {
		testErogazione("RichiesteFaultRest", PolicyAlias.MINUTO);
	}
	
	@Test
	public void orarioErogazione() throws Exception {
		testErogazione("RichiesteFaultRest", PolicyAlias.ORARIO);
	}
	
	@Test
	public void giornalieroErogazione() throws Exception {
		testErogazione("RichiesteFaultRest", PolicyAlias.GIORNALIERO);
	}
	
	@Test
	public void perMinutoErogazioneSequenziali() throws Exception {
		testErogazioneSequenziale("RichiesteFaultRest", PolicyAlias.MINUTO);
	}
	
	@Test
	public void orarioErogazioneSequenziali() throws Exception {
		testErogazioneSequenziale("RichiesteFaultRest", PolicyAlias.ORARIO);
	}
	
	@Test
	public void giornalieroErogazioneSequenziali() throws Exception {
		testErogazioneSequenziale("RichiesteFaultRest", PolicyAlias.GIORNALIERO);
	}
	
	@Test
	public void perMinutoFruizione() throws Exception {
		testFruizione("RichiesteFaultRest", PolicyAlias.MINUTO);
	}
	
	@Test
	public void orarioFruizione() throws Exception {
		testFruizione("RichiesteFaultRest", PolicyAlias.ORARIO);
	}
	
	@Test
	public void giornalieroFruizione() throws Exception {
		testFruizione("RichiesteFaultRest", PolicyAlias.GIORNALIERO);
	}
	
	@Test
	public void perMinutoFruizioneSequenziali() throws Exception {
		testFruizioneSequenziale("RichiesteFaultRest", PolicyAlias.MINUTO);
	}
	
	@Test
	public void orarioFruizioneSequenziali() throws Exception {
		testFruizioneSequenziale("RichiesteFaultRest", PolicyAlias.ORARIO);
	}
	
	@Test
	public void giornalieroFruizioneSequenziali() throws Exception {
		testFruizioneSequenziale("RichiesteFaultRest", PolicyAlias.GIORNALIERO);
	}
	
	
	public void testErogazione(String erogazione, PolicyAlias policy) throws Exception {
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		String path = Utils.getPolicyPath(policy);
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RichiesteFaultRest/v1/"+path+"?problem=true");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse>  failedResponses = Utils.makeParallelRequests(request, toFailRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(failedResponses, windowSize);		
	}
	
	
	public void testErogazioneSequenziale(String erogazione, PolicyAlias policy) throws Exception {
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		String path = Utils.getPolicyPath(policy);
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RichiesteFaultRest/v1/"+path+"?problem=true");
						
		Vector<HttpResponse> responses = makeRequestsAndCheckPolicy(request, maxRequests, idPolicy);
		checkHeaderFaultRemaining(responses, Headers.FaultRemaining, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse> failedResponses = Utils.makeParallelRequests(request, toFailRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(failedResponses, windowSize);
	}
	
	
	public void testFruizione(String erogazione, PolicyAlias policy) throws Exception {
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		String path = Utils.getPolicyPath(policy);
		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RichiesteFaultRest/v1/"+path+"?problem=true");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse>  failedResponses = Utils.makeParallelRequests(request, toFailRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(failedResponses, windowSize);		
	}
	
	
	public void testFruizioneSequenziale(String erogazione, PolicyAlias policy) throws Exception {
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		String path = Utils.getPolicyPath(policy);
		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RichiesteFaultRest/v1/"+path+"?problem=true");
						
		Vector<HttpResponse> responses = makeRequestsAndCheckPolicy(request, maxRequests, idPolicy);
		checkHeaderFaultRemaining(responses, Headers.FaultRemaining, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse> failedResponses = Utils.makeParallelRequests(request, toFailRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(failedResponses, windowSize);
	}
	
	
	/**
	 * Controlla che le risposteabbiamo il valore dello header *-Remaining decrescente
	 * a partire da maxRequests-1
	 * 
	 * @param responses
	 * @param header
	 */
	
	private void checkHeaderFaultRemaining(Vector<HttpResponse> responses, String header, int limit) {
		for(int i=0;i<limit;i++) {
			var r = responses.get(i);
			assertEquals(limit-i-1, Integer.parseInt(r.getHeader(Headers.FaultRemaining)));
		}
	}
	
	
	private void checkOkRequests(Vector<HttpResponse> responses, int windowSize) {
		// Delle richieste ok Controllo lo header *-Limit, *-Reset e lo status code
		
		responses.forEach( r -> {
			
			Utils.checkXLimitHeader(r.getHeader(Headers.FaultLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FaultLimit), maxRequests, windowMap);
			}
			assertNotEquals(null, Integer.valueOf(r.getHeader(Headers.FaultReset)));
			assertEquals(500, r.getResultHTTPOperation());			
		});
	}
	
	
	private void checkFailedRequests(Vector<HttpResponse> responses, int windowSize) throws Exception {
		
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		for (var r: responses) {
			Utils.checkXLimitHeader(r.getHeader(Headers.FaultLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FaultLimit), maxRequests, windowMap);
			}
			assertNotEquals(null, Integer.valueOf(r.getHeader(Headers.FaultReset)));
			assertEquals(429, r.getResultHTTPOperation());
			
			JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(r.getContent()));
			
			assertEquals("https://govway.org/handling-errors/429/LimitExceeded.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
			assertEquals("LimitExceeded", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
			assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
			assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));	
			assertEquals("Limit exceeded detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
			
			assertEquals("0", r.getHeader(Headers.FaultRemaining));
			assertEquals(HeaderValues.LimitExceeded, r.getHeader(Headers.GovWayTransactionErrorType));
			assertEquals(HeaderValues.ReturnCodeTooManyRequests, r.getHeader(Headers.ReturnCode));
			assertNotEquals(null, r.getHeader(Headers.RetryAfter));
		}
		
	}
	
	/**
	 * Effettua n richieste sequenziali che ci si aspetta non vengano bloccate dalla policy
	 * e che vengano effettivamente conteggiate. Tra una richiesta e l'altra si aspetta
	 * che i contatori della policy vengano aggiornati.
	 * 
	 */
	
	public static Vector<HttpResponse> makeRequestsAndCheckPolicy(HttpRequest request, int count, String idPolicy) {
		final Vector<HttpResponse> responses = new Vector<>();

		for(int i=0; i<count;i++) {
			logRateLimiting.info(request.getMethod() + " " + request.getUrl());
			try {
				responses.add(HttpUtilities.httpInvoke(request));
				logRateLimiting.info("Richiesta effettuata..");
				Utils.checkConditionsNumeroRichieste(idPolicy, 0, i+1, 0);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}		
		}
		
		logRateLimiting.info("RESPONSES: ");
		responses.forEach(r -> {
			logRateLimiting.info("statusCode: " + r.getResultHTTPOperation());
			logRateLimiting.info("headers: " + r.getHeaders());
		});
		
		return responses;		
	}
	
}
