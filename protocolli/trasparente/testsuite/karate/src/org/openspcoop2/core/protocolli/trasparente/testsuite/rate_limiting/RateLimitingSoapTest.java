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
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class RateLimitingSoapTest extends ConfigLoader {
	
	
	@Test 
	public void richiesteSimultaneeErogazione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteSimultanee xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteSimultanee>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
	}
	
	
	@Test
	public void richiesteSimultaneeFruizione() throws Exception {
		
		final int maxConcurrentRequests = 10;
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteSimultanee xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteSimultanee>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
	}
	

	
	@Test
	public void richiestePerMinutoErogazione() throws Exception {
		System.out.println("Test richieste per minuto erogazione...");
		final int maxRequests = 5;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_MINUTO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);
		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiestePerMinuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiestePerMinuto>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);				
	}
	
	
	@Test
	public void richiesteOrarieErogazione() throws Exception {
		System.out.println("Test richieste orarie erogazione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_ORARIE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_ORARIE);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewHour();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteOrarie xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteOrarie>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);	
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	@Test
	public void richiesteGiornaliereErogazione() throws Exception {
		System.out.println("Test richieste giornaliere erogazione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteOrarie xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteOrarie>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);				
	}
	
	
	@Test
	public void richiestePerMinutoFruizione() throws Exception {
		System.out.println("Test richieste per minuto fruizione...");
		final int maxRequests = 5;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_MINUTO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiestePerMinuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiestePerMinuto>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	@Test
	public void richiesteOrarieFruizione() throws Exception {
		System.out.println("Test richieste orarie fruizione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_ORARIE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_ORARIE);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare dell'ora

		Utils.waitForNewHour();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteOrarie xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteOrarie>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);

		// TODO: Dovrei testare che lo header X-RateLimit-Reset è in un range giusto, 
		// nel caso di richieste orarie, deve indicare il numero di secondi allo scoccare della prossima ora
		// ecc..
	}
	
	
	@Test
	public void richiesteGiornaliereFruizione() throws Exception {
		System.out.println("Test richieste giornaliere fruizione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "RateLimitingTestSoap", TipoPolicy.RICHIESTE_GIORNALIERE);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);


		// Aspetto lo scoccare del nuovo giorno

		Utils.waitForNewDay();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteGiornaliere xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteGiornaliere>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/RateLimitingTestSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	private void checkAssertionsNumeroRichieste(Vector<HttpResponse> responses, int maxRequests) {

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
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
		
		// TODO: XPATH SUL BODY DELLA RICHIESTA FALLITA

		// Lo header X-RateLimit-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxRequests-1
		
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader(Headers.RateLimitRemaining))).collect(Collectors.toList());
		assertTrue(IntStream.range(0, maxRequests).allMatch(v -> counters.contains(v)));		
	}
	
	
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
		
		// TODO: XPATH SUL BODY DELLA RICHIESTA FALLITA

		assertEquals("0", failedResponse.getHeader(Headers.ConcurrentRequestsRemaining));
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}

}