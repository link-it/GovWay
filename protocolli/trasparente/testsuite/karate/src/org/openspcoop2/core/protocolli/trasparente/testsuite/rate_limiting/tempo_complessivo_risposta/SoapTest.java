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
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.pdd.core.dynamic.DynamicException;
import org.openspcoop2.pdd.core.dynamic.PatternExtractor;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.w3c.dom.Element;

public class SoapTest extends ConfigLoader {
	

	@Test
	public void perMinutoErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.MINUTO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 60);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void orarioErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.ORARIO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 3600);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void giornalieroErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.GIORNALIERO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 86400);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}

	
	@Test
	public void perMinutoFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.MINUTO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 60);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void orarioFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.ORARIO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.ORARIO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 3600);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	
	
	@Test
	public void giornalieroFruizione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.GIORNALIERO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", "TempoComplessivoRispostaSoap", PolicyAlias.GIORNALIERO);
		Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/TempoComplessivoRispostaSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, 2);

		checkAssertions(responses, 1, 86400);		
		
		Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}
	

	
	private void checkAssertions(Vector<HttpResponse> responses, int maxSeconds, int windowSize) throws DynamicException {
	
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
		
		String body = new String(failedResponse.getContent());
		logRateLimiting.info(body);
		
		Element element = Utils.buildXmlElement(failedResponse.getContent());
		
		PatternExtractor matcher = new PatternExtractor(OpenSPCoop2MessageFactory.getDefaultMessageFactory(), element, logRateLimiting);
		assertEquals("Limit Exceeded", matcher.read("/html/head/title/text()"));
		assertEquals("Limit Exceeded", matcher.read("/html/body/h1/text()"));
		assertEquals("Limit exceeded detected", matcher.read("/html/body/p/text()"));
		
		
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitTimeResponseQuotaRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));
	}
	

}
