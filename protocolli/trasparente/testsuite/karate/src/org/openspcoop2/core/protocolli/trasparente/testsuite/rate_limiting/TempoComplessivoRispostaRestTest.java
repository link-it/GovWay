package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.TipoPolicy;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;
import org.slf4j.Logger;

public class TempoComplessivoRispostaRestTest extends ConfigLoader {
	
	private final static Logger log = LoggerWrapperFactory.getLogger(TempoComplessivoRispostaRestTest.class);
	
	@Test
	public void perMinutoErogazione() throws UtilsException, HttpUtilsException, InterruptedException {
		//gw_SoggettoInternoTest/gw_TempoComplessivoRisposta/v1
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", TipoPolicy.TEMPO_RISPOSTA_MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", TipoPolicy.TEMPO_RISPOSTA_MINUTO);
		// Utils.checkPreConditionsNumeroRichieste(idPolicy); TODO Checka preconditions
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=500");
		
		// TODO: Perchè il logger non mi logga?
						
		HttpResponse response = HttpUtilities.httpInvoke(request);
		System.out.println("ResponseStatus: " + response.getResultHTTPOperation());
		System.out.println("ResponseHeaders: " + response.getHeaders());
		System.out.println("ResponseBody: " + new String(response.getContent()));
		
		request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=500");
						
		response = HttpUtilities.httpInvoke(request);
		System.out.println("ResponseStatus: " + response.getResultHTTPOperation());
		System.out.println("ResponseHeaders: " + response.getHeaders());
		System.out.println("ResponseBody: " + new String(response.getContent()));
		
		
		// Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests);		
	}

}
