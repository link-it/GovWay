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
	private static final String testIdHeader = "GovWay-TestSuite-RL-Grouping";

	@Test
	public void completateConSuccessoErogazione() {
		completateConSuccesso(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void completateConSuccessoFruizione() {
		completateConSuccesso(TipoServizio.FRUIZIONE);
	}
	
	@Test
	public void occupazioneBandaErogazione() {
		occupazioneBanda(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void occupazioneBandaFruizione() {
		occupazioneBanda(TipoServizio.FRUIZIONE);
	}
	
	public static void occupazioneBanda(TipoServizio tipoServizio) {
		final String erogazione = "CustomPolicySoap";
		String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		final int nrequests = 5;
		final int payload_size = 1024;
		
				
		// La policy con l'intervallo più breve è quella oraria, allora per essere
		// sicuri di non sforare in tutti i casi nell'intervallo successivo, è sufficiente
		// aspettare l'ora
		Utils.waitForNewHour();
		
		final String[] headerPolicies = { "OccupazioneBandaOrario", "OccupazioneBandaGiornaliero", "OccupazioneBandaSettimanale", "OccupazioneBandaMensile" };
		final int[] headerRemaining = new int[headerPolicies.length];
		

		final String body = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +  
				"    <soap:Body>\n" + 
				"        <ns2:Minuto xmlns:ns2=\"http://amministrazioneesempio.it/nomeinterfacciaservizio\">\n" +
				generatePayload(payload_size) +
				"        </ns2:Minuto>\n" + 
				"    </soap:Body>\n" + 
				"</soap:Envelope>";
		
		for(int i=0;i<headerPolicies.length;i++) {
			logRateLimiting.info("Attivo conteggio su "+headerPolicies[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(body.getBytes());
			request.addHeader(testIdHeader, headerPolicies[i]);
			
			
			// Vedo quante ne mancano
			HttpResponse firstResp = Utils.makeRequest(request);
			assertEquals(200, firstResp.getResultHTTPOperation());
			headerRemaining[i] = Integer.valueOf(firstResp.getHeader(Headers.BandWidthQuotaRemaining));
			
			// Faccio n richieste
			Vector<HttpResponse> responses = Utils.makeParallelRequests(request, nrequests);

			// Le richieste devono essere andate tutte bene
			assertEquals(nrequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());	
		}
		

		logRateLimiting.info("Headers remaining: " );
		for(var h : headerRemaining) {
			logRateLimiting.info( " - " + h);
		}
	
		logRateLimiting.info("Aspetto che le statistiche vengano generate...");
		org.openspcoop2.utils.Utilities.sleep(16000);
		
		for(int i=0;i<headerPolicies.length;i++) {
			logRateLimiting.info("Controllo il conteggio su endpoint /"+headerPolicies[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(body.getBytes());
			request.addHeader(testIdHeader, headerPolicies[i]);
			
			// Faccio un'altra richiesta e verifico che il remaining sia diminuito di payload_size*(nrequests+1)
			
			HttpResponse lastResp = Utils.makeRequest(request);
			assertEquals(200, lastResp.getResultHTTPOperation());
			int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.BandWidthQuotaRemaining));
			
			// Ogni richiesta è da 1Kb, e nella risposta viene restituito il payload della richiesta
			// quindi 2*(nrequests+1)
			
			int shouldRemaining = headerRemaining[i] - 2*(nrequests+1);
			logRateLimiting.info("Banda rimanente su "+headerPolicies[i]+": " + updatedHeaderRemaining);
			logRateLimiting.info("Dovrebbero rimanerne meno di: " + shouldRemaining);
			assertTrue(updatedHeaderRemaining <= shouldRemaining);
			
		}
	}
	
	
	public static void completateConSuccesso(TipoServizio tipoServizio) {
		final String erogazione = "CustomPolicySoap";
		final String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		final int nrequests = 5;
		
		// La policy con l'intervallo più breve è quella oraria, allora per essere
		// sicuri di non sforare in tutti i casi nell'intervallo successivo, è sufficiente
		// aspettare l'ora.
		Utils.waitForNewHour();
		final String[] headerPolicies = { "CompletateConSuccessoOrario", "CompletateConSuccessoGiornaliero", "CompletateConSuccessoSettimanale", "CompletateConSuccessoMensile" };
		final int[] headerRemaining = new int[headerPolicies.length];
		
		for(int i=0;i<headerPolicies.length;i++) {
			logRateLimiting.info("Attivo conteggio su "+headerPolicies[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(SoapBodies.get(PolicyAlias.MINUTO).getBytes());
			request.addHeader(testIdHeader, headerPolicies[i]);
			
			// Vedo quante ne mancano
			HttpResponse firstResp = Utils.makeRequest(request);
			assertEquals(200, firstResp.getResultHTTPOperation());
			headerRemaining[i] = Integer.valueOf(firstResp.getHeader(Headers.RequestSuccesfulRemaining));
			
			// Faccio n richieste
			Vector<HttpResponse> responses = Utils.makeParallelRequests(request, nrequests);

			// Le richieste devono essere andate tutte bene
			assertEquals(nrequests, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());	
		}
		
		logRateLimiting.info("Headers remaining: " );
		for(var h : headerRemaining) {
			logRateLimiting.info(String.valueOf(h));
		}
		
		logRateLimiting.info("Aspetto che le statistiche vengano generate...");
		org.openspcoop2.utils.Utilities.sleep(16000);
		
		for(int i=0;i<headerPolicies.length;i++) {
			logRateLimiting.info("Controllo il conteggio su "+headerPolicies[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/soap+xml");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url);
			request.setContent(SoapBodies.get(PolicyAlias.MINUTO).getBytes());
			request.addHeader(testIdHeader, headerPolicies[i]);
			
			// Faccio un'altra richiesta e verifico che il remaining sia diminuito di nrequests+1
			
			HttpResponse lastResp = Utils.makeRequest(request);
			assertEquals(200, lastResp.getResultHTTPOperation());
			int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.RequestSuccesfulRemaining));
			assertEquals(headerRemaining[i] - (nrequests+1),updatedHeaderRemaining);			
		}

	}
	
	private static byte[] generatePayload(int payloadSize) {
		byte[] ret = new byte[payloadSize];
		Arrays.fill(ret, (byte) 97);
		return ret;
	}

}
