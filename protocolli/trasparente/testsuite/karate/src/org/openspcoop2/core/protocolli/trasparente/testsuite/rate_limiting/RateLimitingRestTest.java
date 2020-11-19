package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.TipoPolicy;
import org.openspcoop2.utils.json.JsonPathExpressionEngine;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

import net.minidev.json.JSONObject;

public class RateLimitingRestTest extends ConfigLoader {
	
	

	@Test
	public void richiestePerMinutoErogazione() throws Exception {
		System.out.println("Test richieste per minuto");
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-per-minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);
		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiesteOrarieErogazione() throws Exception {
		System.out.println("Test richieste per ora");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_ORARIE);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_ORARIE);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-orarie");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);
		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiesteGiornaliereErogazione() throws Exception {
		System.out.println("Test richieste per ora");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		Utils.waitForNewDay();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-giornaliere");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);
		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}	
	
	
	@Test
	public void richiestePerMinutoFruizione() throws Exception {
		System.out.println("Test richieste per minuto fruizione");
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-per-minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	@Test
	public void richiesteOrarieFruizione() throws Exception {
		System.out.println("Test richieste orarie fruizione");
		final int maxRequests = 10;

		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_ORARIE);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_ORARIE);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-orarie");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	@Test
	public void richiesteGiornaliereFruizione() throws Exception {
		System.out.println("Test richieste giornaliere fruizione");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewDay();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-giornaliere");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	

	@Test
	public void richiesteSimultaneeErogazione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-simultanee?sleep=5000");
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicy, maxConcurrentRequests);
	}
	
	
	@Test
	public void richiesteSimultaneeFruizione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestRest", TipoPolicy.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicy);

		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestRest/v1/richieste-simultanee?sleep=5000");
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicy, maxConcurrentRequests);
	}
	
	
	/*@Test
	public void richiesteSimultaneeRipetuto() throws Exception {
		for(int i=0;i<20;i++) {
			richiesteSimultaneeErogazione();
		}
		
		for(int i=0;i<20;i++) {
			richiesteSimultaneeFruizione();
		}
	}*/

	
	private void checkAssertionsRichiesteSimultanee(Vector<HttpResponse> responses, int maxConcurrentRequests) throws Exception {
		// Tutte le richieste tranne 1 devono restituire 200
		
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxConcurrentRequests);

		// Tutte le richieste devono avere lo header GovWay-RateLimit-ConcurrentRequest-Limit=10
		// Tutte le richieste devono avere lo header ConcurrentRequestsRemaining impostato ad un numero positivo		
		
		responses.forEach(r -> {
			assertTrue(r.getHeader(Headers.ConcurrentRequestsLimit).equals(String.valueOf(maxConcurrentRequests)));
			assertTrue(Integer.valueOf(r.getHeader(Headers.ConcurrentRequestsRemaining)) >= 0);
		});
			
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);
		
		JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(failedResponse.getContent()));
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		assertEquals("https://govway.org/handling-errors/429/TooManyRequests.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
		assertEquals("TooManyRequests", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
		assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
		assertEquals("Too many requests detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
		assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));
		

		assertEquals("0", failedResponse.getHeader(Headers.ConcurrentRequestsRemaining));
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}

	
	private void checkAssertionsNumeroRichieste(Vector<HttpResponse> responses, int maxRequests) throws Exception {

		// Tutte le richieste devono avere lo header X-RateLimit-Reset impostato ad un numero
		// Tutte le richieste devono avere lo header X-RateLimit-Limit
		
		responses.forEach(r -> { 			
				assertTrue( Integer.valueOf(r.getHeader(Headers.RateLimitReset)) != null);
				assertNotEquals(null,r.getHeader("X-RateLimit-Limit"));
			});

		// Tutte le richieste tranne una devono restituire 200
		
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxRequests);

		
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);
		
		JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(failedResponse.getContent()));
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		assertEquals("https://govway.org/handling-errors/429/LimitExceeded.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
		assertEquals("LimitExceeded", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
		assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
		assertEquals("Limit exceeded detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
		assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));
		
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));

		// Lo header X-RateLimit-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxRequests-1
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader(Headers.RateLimitRemaining))).collect(Collectors.toList());
		assertTrue(IntStream.range(0, maxRequests).allMatch(v -> counters.contains(v)));	
	}
	
}
