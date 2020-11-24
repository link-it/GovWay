package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.HeaderValues;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.pdd.core.dynamic.DynamicException;
import org.openspcoop2.pdd.core.dynamic.PatternExtractor;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.w3c.dom.Element;

public class SoapTest extends ConfigLoader {
	
	@BeforeClass
	public static void setup() {
		Utils.toggleErrorDisclosure(false);
	}
	
	@Test 
	public void richiesteSimultaneeErogazione() throws Exception {
		final int maxConcurrentRequests = 10;
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicies);
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteSimultanee xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteSimultanee>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicies);
	}
	
	
	@Test
	public void richiesteSimultaneeFruizione() throws Exception {		
		final int maxConcurrentRequests = 10;
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.RICHIESTE_SIMULTANEE);
		Utils.checkPreConditionsRichiesteSimultanee(idPolicies);
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:RichiesteSimultanee xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:RichiesteSimultanee>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);
		
		checkAssertionsRichiesteSimultanee(responses, maxConcurrentRequests);
		Utils.checkPostConditionsRichiesteSimultanee(idPolicies);
	}
	
	@Test
	public void richiesteSimultaneeGlobali() throws Exception {
		final int maxConcurrentRequests = 15;
		
		// TODO: Meglio coinvolgere anche la fruizione nelle richieste.
		
		// Aspetto che i threads attivi sul server siano 0
		Utils.waitForZeroGovWayThreads();
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:NoPolicy xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:NoPolicy>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxConcurrentRequests + 1);

		assertEquals(maxConcurrentRequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null, failedResponse);
		
		String bodyResp = new String(failedResponse.getContent());
		logRateLimiting.info(bodyResp);
		
		Element element = Utils.buildXmlElement(failedResponse.getContent());
		PatternExtractor matcher = new PatternExtractor(OpenSPCoop2MessageFactory.getDefaultMessageFactory(), element, LoggerWrapperFactory.getLogger(getClass()));
		assertEquals("Too Many Requests", matcher.read("/html/head/title/text()"));
		assertEquals("Too Many Requests", matcher.read("/html/body/h1/text()"));
		assertEquals("Too many requests detected", matcher.read("/html/body/p/text()"));
		
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));		
		
		Utils.waitForZeroGovWayThreads();		
	}

	
	@Test
	public void richiestePerMinutoErogazione() throws Exception {
		logRateLimiting.info("Test richieste per minuto erogazione...");
		final int maxRequests = 5;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);
		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Minuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Minuto>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 60);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);				
	}
	
	
	@Test
	public void richiesteOrarieErogazione() throws Exception {
		logRateLimiting.info("Test richieste orarie erogazione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.ORARIO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewHour();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Orario xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Orario>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 3600);	
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	@Test
	public void richiesteGiornaliereErogazione() throws Exception {
		logRateLimiting.info("Test richieste giornaliere erogazione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdErogazione("SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.GIORNALIERO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Giornaliero xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Giornaliero>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 86400);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);				
	}
	
	
	@Test
	public void richiestePerMinutoFruizione() throws Exception {
		logRateLimiting.info("Test richieste per minuto fruizione...");
		final int maxRequests = 5;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare del minuto

		Utils.waitForNewMinute();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Minuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Minuto>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 60);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	@Test
	public void richiesteOrarieFruizione() throws Exception {
		logRateLimiting.info("Test richieste orarie fruizione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.ORARIO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);

		
		// Aspetto lo scoccare dell'ora

		Utils.waitForNewHour();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Orario xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Orario>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 3600);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);

		// TODO: Dovrei testare che lo header X-RateLimit-Reset è in un range giusto, 
		// nel caso di richieste orarie, deve indicare il numero di secondi allo scoccare della prossima ora
		// ecc..
	}
	
	
	@Test
	public void richiesteGiornaliereFruizione() throws Exception {
		logRateLimiting.info("Test richieste giornaliere fruizione...");
		final int maxRequests = 10;

		// Resetto la policy di RL
		
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicies);
		
		idPolicies = dbUtils.getAllPoliciesIdFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "NumeroRichiesteSoap", PolicyAlias.GIORNALIERO);
		Utils.checkPreConditionsNumeroRichieste(idPolicies);


		// Aspetto lo scoccare del nuovo giorno

		Utils.waitForNewDay();		
		
		String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Giornaliero xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +  
				"        </ns2:Giornaliero>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);

		checkAssertionsNumeroRichieste(responses, maxRequests, 86400);
		Utils.checkPostConditionsNumeroRichieste(idPolicies, maxRequests);
	}
	
	
	private void checkAssertionsNumeroRichieste(Vector<HttpResponse> responses, int maxRequests, int windowSize) throws DynamicException {

		// Tutte le richieste devono avere lo header X-RateLimit-Reset impostato ad un numero
		// Tutte le richieste devono avere lo header X-RateLimit-Limit
		
		responses.forEach(r -> { 			
				assertTrue( Integer.valueOf(r.getHeader(Headers.RateLimitReset)) != null);
				assertNotEquals(null,r.getHeader(Headers.RateLimitLimit));
				
				if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
					Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
					Utils.checkXLimitWindows(r.getHeader(Headers.RateLimitLimit), maxRequests, windowMap);
				}
			});

		
		// Tutte le richieste tranne una devono restituire 200
		
		assertEquals(maxRequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null, failedResponse);
		
		String body = new String(failedResponse.getContent());
		logRateLimiting.info(body);
		
		Element element = Utils.buildXmlElement(failedResponse.getContent());
		
		PatternExtractor matcher = new PatternExtractor(OpenSPCoop2MessageFactory.getDefaultMessageFactory(), element, LoggerWrapperFactory.getLogger(getClass()));
		assertEquals("Limit Exceeded", matcher.read("/html/head/title/text()"));
		assertEquals("Limit Exceeded", matcher.read("/html/body/h1/text()"));
		assertEquals("Limit exceeded detected", matcher.read("/html/body/p/text()"));		
		
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
	
	
	private void checkAssertionsRichiesteSimultanee(Vector<HttpResponse> responses, int maxConcurrentRequests) throws Exception {
		// Tutte le richieste tranne 1 devono restituire 200
		
		assertEquals(maxConcurrentRequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());

		// Tutte le richieste devono avere lo header GovWay-RateLimit-ConcurrentRequest-Limit=10
		// Tutte le richieste devono avere lo header ConcurrentRequestsRemaining impostato ad un numero positivo		
		
		responses.forEach(r -> {
			assertTrue(r.getHeader(Headers.ConcurrentRequestsLimit).equals(String.valueOf(maxConcurrentRequests)));
			assertTrue(Integer.valueOf(r.getHeader(Headers.ConcurrentRequestsRemaining)) >= 0);
		});
			
		// La richiesta fallita deve avere status code 429
		
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertNotEquals(null, failedResponse);
		
		String body = new String(failedResponse.getContent());
		logRateLimiting.info(body);
		
		Element element = Utils.buildXmlElement(failedResponse.getContent());
		PatternExtractor matcher = new PatternExtractor(OpenSPCoop2MessageFactory.getDefaultMessageFactory(), element, LoggerWrapperFactory.getLogger(getClass()));
		assertEquals("Too Many Requests", matcher.read("/html/head/title/text()"));
		assertEquals("Too Many Requests", matcher.read("/html/body/h1/text()"));
		assertEquals("Too many requests detected", matcher.read("/html/body/p/text()"));
		
		assertEquals("0", failedResponse.getHeader(Headers.ConcurrentRequestsRemaining));
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}

}