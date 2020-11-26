package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.richieste_fallite;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class SoapTest extends ConfigLoader {
	
	final static int maxRequests = 5;
	
	@Test
	public void perMinutoErogazione() throws Exception {

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteFalliteSoap", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteFalliteSoap", PolicyAlias.MINUTO);
		// Commons.checkPreConditionsTempoComplessivoRisposta(idPolicy); 
		
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
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RichiesteFalliteSoap/v1");
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);
			
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		// TODO: Trasformalo in makeParallelRequests quando andrea ha fixato il conteggio
		responses.addAll(Utils.makeSequentialRequests(request, 3));
		
		// checkAssertions(responses, 1, 60);		
		
		// Commons.checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}

}
