package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
	
	

	public void richiestePerMinutoErogazione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste per minuto");
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);		
		checkAssertionsNumeroRichieste(responses, maxRequests, 60, disclosure);
	}
	
	
	@Test
	public void richiestePerMinutoErogazioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiestePerMinutoErogazione(false);
	}
	
	@Test
	public void richiestePerMinutoErogazioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiestePerMinutoErogazione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	
	public void richiesteOrarieErogazione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste per ora");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.ORARIO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/orario");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		checkAssertionsNumeroRichieste(responses, maxRequests,3600, disclosure);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	@Test
	public void richiesteOrarieErogazioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiesteOrarieErogazione(false);
	}
	
	
	@Test
	public void richiesteOrarieErogazioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiesteOrarieErogazione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	
	public void richiesteGiornaliereErogazione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste per ora");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.GIORNALIERO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		Utils.waitForNewDay();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/giornaliero");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		checkAssertionsNumeroRichieste(responses, maxRequests, 86400, disclosure);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiesteGiornaliereErogazioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiesteGiornaliereErogazione(false);
	}
	
	
	@Test
	public void richiesteGiornaliereErogazioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiesteGiornaliereErogazione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	
	public void richiestePerMinutoFruizione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste per minuto fruizione");
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		checkAssertionsNumeroRichieste(responses, maxRequests, 60, disclosure);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiestePerMinutoFruizioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiestePerMinutoFruizione(false);
	}
	
	
	@Test
	public void richiestePerMinutoFruizioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiestePerMinutoFruizione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	
	public void richiesteOrarieFruizione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste orarie fruizione");
		final int maxRequests = 10;

		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.ORARIO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/orario");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		checkAssertionsNumeroRichieste(responses, maxRequests, 3600, disclosure);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiesteOrarieFruizioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiesteOrarieFruizione(false);
	}
	
	
	@Test
	public void richiesteOrarieFruizioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiesteOrarieFruizione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	
	public void richiesteGiornaliereFruizione(boolean disclosure) throws Exception {
		logRateLimiting.info("Test richieste giornaliere fruizione");
		final int maxRequests = 10;

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.GIORNALIERO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy);
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewDay();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/giornaliero");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		responses.addAll(Utils.makeParallelRequests(request, 1));
		
		checkAssertionsNumeroRichieste(responses, maxRequests, 86400, disclosure);
		Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);
	}
	
	
	@Test
	public void richiesteGiornaliereFruizioneNoDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(false);
		this.richiesteGiornaliereFruizione(false);
	}
	
	
	@Test
	public void richiesteGiornaliereFruizioneDisclosure() throws Exception {
		Utils.toggleErrorDisclosure(true);
		this.richiesteGiornaliereFruizione(true);
		Utils.toggleErrorDisclosure(false);
	}
	
	

	@Test
	public void richiesteSimultaneeErogazione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/richieste-simultanee?sleep=5000");
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicy);
	}
	
	
	@Test
	public void richiesteSimultaneeFruizione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.resetCounters(idPolicy);

		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteRest", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicy);

		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/richieste-simultanee?sleep=5000");
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicy);
	}
	
	@Test
	public void richiesteSimultaneeGlobali() throws Exception {
		final int maxConcurrentRequests = 15;
		
		// TODO: Meglio coinvolgere anche la fruizione.
		
		// Aspetto che i threads attivi sul server siano 0
		Utils.waitForZeroGovWayThreads();
		
		// Effettuo le n richieste
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000");
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null,failedResponse);
		
		JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(failedResponse.getContent()));
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		
		assertEquals("https://govway.org/handling-errors/429/TooManyRequests.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
		assertEquals("TooManyRequests", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
		assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
		assertEquals("Too many requests detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
		assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));
		
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));

		
		Utils.waitForZeroGovWayThreads();		
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
		
		assertEquals(maxConcurrentRequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());

		// Tutte le richieste devono avere lo header GovWay-RateLimit-ConcurrentRequest-Limit=10
		// Tutte le richieste devono avere lo header ConcurrentRequestsRemaining impostato ad un numero positivo		
		
		responses.forEach(r -> {
			assertEquals(String.valueOf(maxConcurrentRequests), r.getHeader(Headers.ConcurrentRequestsLimit));
			assertTrue(Integer.valueOf(r.getHeader(Headers.ConcurrentRequestsRemaining)) >= 0);
		});
			
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null,failedResponse);
		
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

	
	private void checkAssertionsNumeroRichieste(Vector<HttpResponse> responses, int maxRequests, int windowSize, boolean disclosure) throws Exception {

		// Tutte le richieste devono avere lo header X-RateLimit-Reset impostato ad un numero
		// Tutte le richieste devono avere lo header X-RateLimit-Limit
		
		responses.forEach(r -> { 			
				assertNotEquals(null,Integer.valueOf(r.getHeader(Headers.RateLimitReset)));
				Utils.checkXLimitHeader(r.getHeader(Headers.RateLimitLimit), maxRequests);
				
				if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
					Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
					Utils.checkXLimitWindows(r.getHeader(Headers.RateLimitLimit), maxRequests, windowMap);
				}
			});

		// Tutte le richieste tranne una devono restituire 200
		
		assertEquals(maxRequests,responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());

		
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null,failedResponse);
		
		JSONObject jsonResp = JsonPathExpressionEngine.getJSONObject(new String(failedResponse.getContent()));
		JsonPathExpressionEngine jsonPath = new JsonPathExpressionEngine();
		logRateLimiting.info(new String(failedResponse.getContent()));
		
		assertEquals("https://govway.org/handling-errors/429/LimitExceeded.html", jsonPath.getStringMatchPattern(jsonResp, "$.type").get(0));
		assertEquals("LimitExceeded", jsonPath.getStringMatchPattern(jsonResp, "$.title").get(0));
		assertEquals(429, jsonPath.getNumberMatchPattern(jsonResp, "$.status").get(0));
		assertNotEquals(null, jsonPath.getStringMatchPattern(jsonResp, "$.govway_id").get(0));
		
		if (disclosure) {
			assertEquals("Servizio Temporaneamente Non Erogabile [CP02]", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));			
		}
		else {
			assertEquals("Limit exceeded detected", jsonPath.getStringMatchPattern(jsonResp, "$.detail").get(0));
		}
		
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));

		// Lo header X-RateLimit-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxRequests-1 TODO questo mettilo nel test a parte
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader(Headers.RateLimitRemaining))).collect(Collectors.toList());
		assertTrue(IntStream.range(0, maxRequests).allMatch(v -> counters.contains(v)));	
	}
	
}
