package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.tempo_complessivo_risposta;

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
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JsonPathExpressionEngine;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;

import net.minidev.json.JSONObject;

public class RestTest extends ConfigLoader {
	
	
	@Test
	public void perMinutoErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
	
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=2000");
		
		checkAssertions(responses, 1, 60);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void orarioErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.ORARIO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
				
		Utils.waitForNewHour();
		
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/orario?sleep=2000");

		checkAssertions(responses, 1, 3600);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void giornalieroErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.GIORNALIERO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
				
		Utils.waitForNewDay();
		
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/giornaliero?sleep=2000");

		checkAssertions(responses, 1, 86400);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void perMinutoFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
				
		Utils.waitForNewMinute();
	
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=2000");
		
		checkAssertions(responses, 1, 60);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void orarioFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.ORARIO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
				
		Utils.waitForNewMinute();
	
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRisposta/v1/orario?sleep=2000");
		
		checkAssertions(responses, 1, 3600);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void giornalieroFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.GIORNALIERO);
		checkPreConditionsTempoComplessivoRisposta(idPolicy); 
				
		Utils.waitForNewMinute();
	
		Vector<HttpResponse> responses = makeRequests(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRisposta/v1/giornaliero?sleep=2000");
		
		checkAssertions(responses, 1, 86400);
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}


		
	private static void checkPreConditionsTempoComplessivoRisposta(String idPolicy)  {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = Utils.getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}				
				logRateLimiting.info(jmxPolicyInfo);
				Map<String, String> policyValues = Utils.parsePolicy(jmxPolicyInfo);
				
				assertEquals("0", policyValues.get("Richieste Attive"));
				assertEquals("0", policyValues.get("Numero Richieste Conteggiate"));
				assertEquals("0 secondi (0 ms)", policyValues.get("Contatore"));
				assertEquals("0 secondi (0 ms)", policyValues.get("Valore Medio"));
				assertEquals("0", policyValues.get("Numero Richieste Bloccate"));
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}
	
	
	private static void checkPostConditionsTempoComplessivoRisposta(String idPolicy)  {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = Utils.getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}				
				logRateLimiting.info(jmxPolicyInfo);
				Map<String, String> policyValues = Utils.parsePolicy(jmxPolicyInfo);
				
				assertEquals("0", policyValues.get("Richieste Attive"));
				assertEquals("1", policyValues.get("Numero Richieste Conteggiate"));
				assertEquals("1", policyValues.get("Numero Richieste Bloccate"));
				
				String secondiContatore = policyValues.get("Contatore").split(" ")[0];
				assertEquals("2", secondiContatore);
				
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}
	
	private Vector<HttpResponse> makeRequests(String url) throws UtilsException {
		
		Vector<HttpResponse> responses = new Vector<>();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( url );
								
		HttpResponse response = HttpUtilities.httpInvoke(request);
		responses.add(response);
		logRateLimiting.info("Request: " + request.getUrl());
		logRateLimiting.info("ResponseStatus: " + response.getResultHTTPOperation());
		logRateLimiting.info("ResponseHeaders:\n" + response.getHeaders());
		logRateLimiting.info("ResponseBody: " + new String(response.getContent()));
		
		request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( url );
						
		response = HttpUtilities.httpInvoke(request);
		responses.add(response);
		logRateLimiting.info("Request: " + request.getUrl());
		logRateLimiting.info("ResponseStatus: " + response.getResultHTTPOperation());
		logRateLimiting.info("ResponseHeaders:\n" + response.getHeaders());
		logRateLimiting.info("ResponseBody: " + new String(response.getContent()));
		
		return responses;
	}
	
	private void checkAssertions(Vector<HttpResponse> responses, int maxSeconds, int windowSize) throws Exception {
				
		responses.forEach(r -> { 			
				assertNotEquals(null,Integer.valueOf(r.getHeader(Headers.RateLimitTimeResponseQuotaReset)));
				assertNotEquals(null,r.getHeader(Headers.RateLimitTimeResponseQuotaLimit));
				
				if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
					Map<Integer,Integer> windowMap = Map.of(windowSize,maxSeconds);							
					Utils.checkXLimitWindows(r.getHeader(Headers.RateLimitTimeResponseQuotaLimit), maxSeconds, windowMap);
				}
			});

		// Tutte le richieste tranne una devono restituire 200
		
		assertEquals(1, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());

		
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
		
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitTimeResponseQuotaRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}
	
	

}
