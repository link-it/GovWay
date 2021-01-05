package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.custom_policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.SoapBodies;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class SoapTest extends ConfigLoader {
	
	private static final String basePath = System.getProperty("govway_base_path");

	@Test
	public void customPolicyErogazione() {
		customPolicy(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void customPolicyFruizione() {
		customPolicy(TipoServizio.FRUIZIONE);
	}
	
	
	/**
	 * Testo contemporaneamente le tre policy custom: 
	 * 		NumeroRichiesteCompletateConSuccesso, OccupazioneBanda e TempoMedioRisposta
	 *
	 * Controllo che gli headers remaining abbiano il conteggio giusto.
	 * Per il tempo medio invece, non avendo uno header -Remaining, controllo i contatori sulla policy
	 * TODO: Potrei controllare che lo header -Limit abbia la finestra adatta?
	 * @param tipoServizio
	 */
	public static void customPolicy(TipoServizio tipoServizio) {
		final String erogazione = "CustomPolicySoap";
		final int nrequests = 5;
		final int payload_size = 1024;
		
		final String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		

		// Aspettiamo nuove statistiche pulite.
		Utils.waitForDbStats();

		// La policy con l'intervallo più breve è quella oraria, allora per essere
		// sicuri di non sforare in tutti i casi nell'intervallo successivo, è sufficiente
		// aspettare l'ora
		Utils.waitForNewHour();	
		
		// Creo un messaggio grande payload_size bytes, e con un tempo di attesa di 2 secondi.
		// Faccio le richieste, che devono andare tutte bene e controllo che
		//	1 - lo header RequestSuccesfullRemaining sia corretto
		//	2 - Quello dell'occupazione banda anche
		// 	3 - I dati sulla policy per il tempo medio risposta siano corretti.
				
		//	/orario 	 -> policy oraria
		//	/giornaliero -> policy giornaliera
		//  /no-policy 	 -> policy settimanale
		// 	/minuto		 -> policy mensile

		final PolicyAlias[] policies = { PolicyAlias.ORARIO, PolicyAlias.GIORNALIERO, PolicyAlias.NO_POLICY, PolicyAlias.MINUTO };

		final int[] succesfullHeaderRemaining = new int[policies.length];
		final int[] bandwidthQuotaRemaining = new int[policies.length];
		
		// Faccio una richiesta per vedere quante ne mancano ogni header
		// e altre n richieste per far incrementare il conteggio.
		for(int i=0;i<policies.length;i++) {
			logRateLimiting.info("Attivo conteggio su " + policies[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(SoapBodies.get(policies[i], new String(generatePayload(payload_size))).getBytes());
			
			
			// Vedo quante ne mancano per ogni header
			HttpResponse firstResp = Utils.makeRequest(request);
			assertEquals(200, firstResp.getResultHTTPOperation());
			succesfullHeaderRemaining[i] = Integer.valueOf(firstResp.getHeader(Headers.RequestSuccesfulRemaining));
			bandwidthQuotaRemaining[i] = Integer.valueOf(firstResp.getHeader(Headers.BandWidthQuotaRemaining));
						
			// Faccio n richieste
			Vector<HttpResponse> responses = Utils.makeParallelRequests(request, nrequests);

			// Le richieste devono essere andate tutte bene
			assertEquals(nrequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count()); 
		}
		
		logRateLimiting.info("Succesful Headers remaining: " );
		for(var h : succesfullHeaderRemaining) {
			logRateLimiting.info(String.valueOf(h));
		}
		logRateLimiting.info("Bandwidth quota Headers remaining: " );
		for(var h : bandwidthQuotaRemaining) {
			logRateLimiting.info(String.valueOf(h));
		}
		
		Utils.waitForDbStats();

		// Faccio per ogni azione una nuova richiesta in modo da controllare
		// che gli headers remaining siano diminuiti opportunamente 
		for(int i=0;i<policies.length;i++) {
			
			logRateLimiting.info("Controllo il conteggio su "+policies[i]);

			HttpRequest request = new HttpRequest();
			String body = SoapBodies.get(policies[i], new String(generatePayload(payload_size)));
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(body.getBytes());
			
			HttpResponse lastResp = Utils.makeRequest(request);
			assertEquals(200, lastResp.getResultHTTPOperation());
			
			logRateLimiting.info("Controllo " + Headers.RequestSuccesfulRemaining);
			
			int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.RequestSuccesfulRemaining));
			assertEquals(succesfullHeaderRemaining[i] - (nrequests+1), updatedHeaderRemaining);
			
			logRateLimiting.info("Controllo " + Headers.BandWidthQuotaRemaining);
			
			updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.BandWidthQuotaRemaining));
		
			int shouldRemaining = bandwidthQuotaRemaining[i] - (2*(nrequests+1)*body.getBytes().length)/1024;
			logRateLimiting.info("Banda rimanente su "+policies[i]+": " + updatedHeaderRemaining);
			logRateLimiting.info("Dovrebbero rimanerne meno di: " + shouldRemaining);
			assertTrue(updatedHeaderRemaining <= shouldRemaining);
			
			
			logRateLimiting.info("Controllo presenza header:" + Headers.AvgTimeResponseLimit);
			assertTrue(lastResp.getHeader(Headers.AvgTimeResponseLimit) != null);
		}

	}
	
	private static byte[] generatePayload(int payloadSize) {
		byte[] ret = new byte[payloadSize];
		Arrays.fill(ret, (byte) 97);
		return ret;
	}

}
