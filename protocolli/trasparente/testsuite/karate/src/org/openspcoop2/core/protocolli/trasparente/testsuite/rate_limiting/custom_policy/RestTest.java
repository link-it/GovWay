package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.custom_policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class RestTest extends ConfigLoader {
	
	private static final String basePath = System.getProperty("govway_base_path");

	@Test
	public void erogazione() {
		customPolicy(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void fruizione() {
		customPolicy(TipoServizio.FRUIZIONE);
	}
	
	
	
	public static void customPolicy(TipoServizio tipoServizio) {
		
		/** Per questi test non è necessario far fallire la policy, è sufficiente verificare
		 *  che venga attivata quella giusta con il relativo conteggio.
		 *  
		 */
		
		final String erogazione = "CustomPolicyRest";
		String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		final int nrequests = 10;
		
		// Endpoints:
		//	/orario 	 -> policy oraria
		//	/giornaliero -> policy giornaliera
		//  /no-policy 	 -> policy settimanale
		// 	/minuto		 -> policy mensile
		
		String[] endpoints = { /*"orario", "giornaliero",*/ "no-policy", "minuto" };
		int[] headerRemaining = new int[endpoints.length];
		
		for(int i=0;i<endpoints.length;i++) {
			logRateLimiting.info("Attivo conteggio su endpoint /"+endpoints[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/json");
			request.setMethod(HttpRequestMethod.GET);
			request.setUrl(url + "/" + endpoints[i]);
			
			// Vedo quante ne mancano
			HttpResponse firstResp = Utils.makeRequest(request);
			assertEquals(200, firstResp.getResultHTTPOperation());
			headerRemaining[i] = Integer.valueOf(firstResp.getHeader(Headers.RequestSuccesfulRemaining));
			
			// Faccio n richieste
			Vector<HttpResponse> responses = Utils.makeParallelRequests(request, nrequests);

			// Le richieste devono essere andate tutte bene
			assertEquals(nrequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());	
		}
		
		// aspetto che le statistiche vengano generate
		logRateLimiting.info("Headers remaining: " );
		for(var h : headerRemaining) {
			logRateLimiting.info( " - " + h);
		}
		logRateLimiting.info("Aspetto che le statistiche vengano generate...");
		org.openspcoop2.utils.Utilities.sleep(16000);
		
		for(int i=0;i<endpoints.length;i++) {
			logRateLimiting.info("Controllo il conteggio su endpoint /"+endpoints[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/json");
			request.setMethod(HttpRequestMethod.GET);
			request.setUrl(url + "/" + endpoints[i]);
			
			// Faccio un'altra richiesta e verifico che il remaining sia diminuito di nrequests+1
			
			HttpResponse lastResp = Utils.makeRequest(request);
			assertEquals(200, lastResp.getResultHTTPOperation());
			int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.RequestSuccesfulRemaining));
			assertEquals(headerRemaining[i] - (nrequests+1),updatedHeaderRemaining);			
		}
		
		
		
		/*HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(url + "/orario");
		
		// Vedo quante ne mancano
		HttpResponse firstResp = Utils.makeRequest(request);
		assertEquals(200, firstResp.getResultHTTPOperation());
		int headerRemaining = Integer.valueOf(firstResp.getHeader(Headers.RequestSuccesfulRemaining));
		
		// Faccio n richieste
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, nrequests);
		
		// Le richieste devono essere andate tutte bene
		assertEquals(nrequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		// aspetto che le statistiche vengano generate
		logRateLimiting.info("Aspetto che le statistiche vengano generate...");
		org.openspcoop2.utils.Utilities.sleep(16000);
		
		// Faccio un'altra richiesta e verifico che il remaining sia diminuito di nrequests+1
		HttpResponse lastResp = Utils.makeRequest(request);
		assertEquals(200, lastResp.getResultHTTPOperation());
		int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.RequestSuccesfulRemaining));
		assertTrue(updatedHeaderRemaining == headerRemaining - (nrequests+1) );*/
		
	}
}
