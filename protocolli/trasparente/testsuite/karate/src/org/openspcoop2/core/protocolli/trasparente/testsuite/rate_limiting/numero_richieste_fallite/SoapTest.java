package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_fallite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.HeaderValues;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.SoapBodies;
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
	
	final static int maxRequests = 5;
	final static int toFailRequests = 4;
	final static int totalRequests = maxRequests + toFailRequests;
	
	@Test
	public void perMinutoErogazione() throws Exception {
		testErogazione("RichiesteFalliteSoap", PolicyAlias.MINUTO);
	}
	
/*	@Test
	public void orarioErogazione() throws Exception {
		testErogazione("RichiesteFalliteSoap", PolicyAlias.ORARIO);
	}
	
	@Test
	public void giornalieroErogazione() throws Exception {
		testErogazione("RichiesteFalliteSoap", PolicyAlias.GIORNALIERO);
	}*/
	
	
	public void testErogazione(String erogazione, PolicyAlias policy) throws Exception {
		
		int windowSize = Utils.getPolicyWindowSize(policy);
		
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
				
		Utils.waitForPolicy(policy);
		
		String body = SoapBodies.get(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");		
		request.setMethod(HttpRequestMethod.POST);
		request.setContent(body.getBytes());
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/"+erogazione+"/v1?returnCode=500");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		Vector<HttpResponse>  failedResponses = Utils.makeParallelRequests(request, toFailRequests);

		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, toFailRequests);
		
		checkOkRequests(responses, windowSize);
		checkFailedRequests(failedResponses, windowSize);		
	}
	
	
	private void checkFailedRequests(Vector<HttpResponse> responses, int windowSize) throws Exception {
		
		for (var r: responses) {
			Utils.checkXLimitHeader(r.getHeader(Headers.FailedLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FailedLimit), maxRequests, windowMap);
			}
			assertTrue(Integer.valueOf(r.getHeader(Headers.FailedReset)) <= windowSize);			
			assertEquals(429, r.getResultHTTPOperation());
			
			Element element = Utils.buildXmlElement(r.getContent());
			Utils.matchLimitExceededSoap(element);
			
			assertEquals("0", r.getHeader(Headers.FailedRemaining));
			assertEquals(HeaderValues.LimitExceeded, r.getHeader(Headers.GovWayTransactionErrorType));
			assertEquals(HeaderValues.ReturnCodeTooManyRequests, r.getHeader(Headers.ReturnCode));
			assertNotEquals(null, r.getHeader(Headers.RetryAfter));
		}
		
	}


	private void checkOkRequests(Vector<HttpResponse> responses, int windowSize) throws DynamicException {
	
		// Per ogni richiesta controllo gli headers e anche che il body
		// sia effettivamente un fault.
		for (var r: responses){
			
			Utils.checkXLimitHeader(r.getHeader(Headers.FailedLimit), maxRequests);			
			if ("true".equals(prop.getProperty("rl_check_limit_windows"))) {
				Map<Integer,Integer> windowMap = Map.of(windowSize,maxRequests);							
				Utils.checkXLimitWindows(r.getHeader(Headers.FailedLimit), maxRequests, windowMap);
			}
			
			assertTrue(Integer.valueOf(r.getHeader(Headers.FailedReset)) <= windowSize);
			assertNotEquals(null, Integer.valueOf(r.getHeader(Headers.FailedRemaining)));
			assertEquals(500, r.getResultHTTPOperation());
						
			Element element = Utils.buildXmlElement(r.getContent());
			PatternExtractor matcher = new PatternExtractor(OpenSPCoop2MessageFactory.getDefaultMessageFactory(), element, logRateLimiting);
			assertEquals("env:Receiver", matcher.read("/Envelope/Body/Fault/Code/Value/text()"));
			assertEquals("ns1:Server.OpenSPCoopExampleFault", matcher.read("/Envelope/Body/Fault/Code/Subcode/Value/text()"));
			assertEquals("Fault ritornato dalla servlet di trace, esempio di OpenSPCoop", matcher.read("/Envelope/Body/Fault/Reason/Text/text()"));
			assertEquals("OpenSPCoopTrace", matcher.read("/Envelope/Body/Fault/Role/text()"));			
		}
		
	}

}
