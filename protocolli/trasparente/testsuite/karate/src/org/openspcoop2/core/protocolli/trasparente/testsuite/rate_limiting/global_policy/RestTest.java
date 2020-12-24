package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.global_policy;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class RestTest extends ConfigLoader {
	
	@Test
	public void erogazione() {
		testGlobalPolicy(TipoServizio.EROGAZIONE);
	}
	
	
	static void testGlobalPolicy(TipoServizio tipoServizio) {
		// Usiamo l'erogazione già presente per il test numero_richieste,
		// visto che li ci sono policy sul numero di richieste parallele passando lo header
		// Così vedo anche che succede agli headers quando c'è una policy globale+policy locale

		// GovWay-TestSuite-RL-GlobalPolicy=Orario per attivare la policy globale
		final int maxRequests = 5;
		Utils.waitForNewHour();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteRest/v1/orario");
		request.addHeader("GovWay-TestSuite-RL-GlobalPolicy", "Orario");
		
		Vector<HttpResponse> responseOk = Utils.makeSequentialRequests(request, maxRequests);
		Vector<HttpResponse> responseBlocked = Utils.makeSequentialRequests(request, maxRequests);
		
		// TODO: Continua
		

	}

}
