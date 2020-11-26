package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo;

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
	
	@Test
	public void perMinutoErogazione() throws Exception {
		

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteCompletateConSuccessoRest", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteCompletateConSuccessoRest", PolicyAlias.MINUTO);
		Commons.checkPreConditionsRichiesteCompletateConSuccesso(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RichiesteCompletateConSuccessoRest/v1/minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		// Aspetto che tutte le richieste siano state elaborate e che 
		// il numero di richieste conteggiate sia però uguale al numero
		// di richieste fatte.
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeSequentialRequests(request, 1));
		
		Commons.checkPostConditionsRichiesteCompletateConSuccesso(idPolicy);
		checkAssertions(responses, maxRequests, 60);
	}
	
	
	private void checkAssertions(Vector<HttpResponse> responses, int maxLimit, int windowSize) throws Exception {
		
		responses.forEach(r -> { 			
				assertNotEquals(null,Integer.valueOf(r.getHeader(Headers.RequestSuccesfulReset)));
				Utils.checkXLimitHeader(Headers.RequestSuccesfulLimit, maxLimit);
								
				if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
					Map<Integer,Integer> windowMap = Map.of(windowSize,maxLimit);							
					Utils.checkXLimitWindows(r.getHeader(Headers.RequestSuccesfulLimit), maxLimit, windowMap);
				}
			});

		// Tutte le richieste tranne una devono restituire 200
		
		assertEquals(responses.size()-1, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());

		
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null,failedResponse);
		
		JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(failedResponse.getContent()));
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		assertEquals("https://govway.org/handling-errors/429/LimitExceeded.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
		assertEquals("LimitExceeded", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
		assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
		assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));	
		assertEquals("Limit exceeded detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
		
		assertEquals("0", failedResponse.getHeader(Headers.RequestSuccesfulRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}

}
