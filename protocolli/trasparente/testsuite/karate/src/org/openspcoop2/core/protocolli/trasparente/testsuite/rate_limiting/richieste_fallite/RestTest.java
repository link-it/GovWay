package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.richieste_fallite;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilsException;

public class RestTest extends ConfigLoader {
	
	
	@Test
	public void perMinutoErogazione() throws UtilsException, HttpUtilsException, InterruptedException {
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteFalliteRest", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "RichiesteFalliteRest", PolicyAlias.MINUTO);
		// Utils.checkPreConditionsNumeroRichieste(idPolicy); TODO
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/RichiesteFalliteRest/v1/minuto?returnCode=500");
						
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, maxRequests);
		
		Utils.waitForZeroActiveRequests(idPolicy, maxRequests);
		
		
		// TODO: Trasformalo in makeParallelRequests quando andrea ha fixato il conteggio
		responses.addAll(Utils.makeSequentialRequests(request, 3));
		
		// Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests); TODO		
		
	}

}
