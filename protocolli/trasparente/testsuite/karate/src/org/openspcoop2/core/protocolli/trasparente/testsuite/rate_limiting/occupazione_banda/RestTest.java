package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.occupazione_banda;

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
	
	private static final int requestSizeBytes = 1024;
	
	@Test
	public void perMinutoErogazione() throws UtilsException, HttpUtilsException, InterruptedException {
		final int maxRequests = 5;

		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "OccupazioneBandaRest", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "OccupazioneBandaRest", PolicyAlias.MINUTO);
		// Utils.checkPreConditionsNumeroRichieste(idPolicy); TODO
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setContent(generatePayload(requestSizeBytes));
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/OccupazioneBandaRest/v1/minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests + 1);
		
		System.out.println("Risposta: " + new String(responses.get(0).getContent()));
		
		// Utils.checkPostConditionsNumeroRichieste(idPolicy, maxRequests); TODO				
	}

	private byte[] generatePayload(int payloadSize) {		
		byte[] ret = new byte[payloadSize];
		for (int i=0;i<payloadSize;i++) {
			ret[i] = 97;
		}
		return ret;
	}

}
