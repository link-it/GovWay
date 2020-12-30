/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.custom_policy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;


/** 
 * Per questi test non è necessario far fallire la policy, è sufficiente verificare
 *  che venga attivata quella giusta con il relativo conteggio.
 *  
 */

public class RestTest extends ConfigLoader {
	
	private static final String basePath = System.getProperty("govway_base_path");

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
		final String erogazione = "CustomPolicyRest";
		String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		final int nrequests = 5;
		final int payload_size = 1024;
		// Endpoints:
		//	/orario 	 -> policy oraria
		//	/giornaliero -> policy giornaliera
		//  /no-policy 	 -> policy settimanale
		// 	/minuto		 -> policy mensile
		
		// La policy con l'intervallo più breve è quella oraria, allora per essere
		// sicuri di non sforare in tutti i casi nell'intervallo successivo, è sufficiente
		// aspettare l'ora.
		Utils.waitForNewHour();
		
		String[] endpoints = { "orario", "giornaliero", "no-policy", "minuto" };
		int[] headerRemaining = new int[endpoints.length];
		
		for(int i=0;i<endpoints.length;i++) {
			logRateLimiting.info("Attivo conteggio su endpoint /"+endpoints[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/json");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url + "/" + endpoints[i]);
			request.setContent(generatePayload(payload_size));
			
			
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
		
		for(int i=0;i<endpoints.length;i++) {
			logRateLimiting.info("Controllo il conteggio su endpoint /"+endpoints[i]);
			HttpRequest request = new HttpRequest();
			request.setContentType("application/json");
			request.setMethod(HttpRequestMethod.POST);
			request.setUrl(url + "/" + endpoints[i]);
			request.setContent(generatePayload(payload_size));
			
			// Faccio un'altra richiesta e verifico che il remaining sia diminuito di payload_size*(nrequests+1)
			
			HttpResponse lastResp = Utils.makeRequest(request);
			assertEquals(200, lastResp.getResultHTTPOperation());
			int updatedHeaderRemaining = Integer.valueOf(lastResp.getHeader(Headers.BandWidthQuotaRemaining));
			
			// Ogni richiesta è da 1Kb, e nella risposta viene restituito il payload della richiesta
			// quindi 2*(nrequests+1)
			
			int shouldRemaining = headerRemaining[i] - 2*(nrequests+1);
			logRateLimiting.info("Banda rimanente su /"+endpoints[i]+": " + updatedHeaderRemaining);
			logRateLimiting.info("Dovrebbero rimanerne meno di: " + shouldRemaining);
			assertTrue(updatedHeaderRemaining <= shouldRemaining);
			
		}
		
	}
	
	public static void completateConSuccesso(TipoServizio tipoServizio) {
		final String erogazione = "CustomPolicyRest";
		final String url = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		final int nrequests = 5;
		
		// Endpoints:
		//	/orario 	 -> policy oraria
		//	/giornaliero -> policy giornaliera
		//  /no-policy 	 -> policy settimanale
		// 	/minuto		 -> policy mensile
		
		// La policy con l'intervallo più breve è quella oraria, allora per essere
		// sicuri di non sforare in tutti i casi nell'intervallo successivo, è sufficiente
		// aspettare l'ora.
		Utils.waitForNewHour();
		final String[] endpoints = { "orario", "giornaliero", "no-policy", "minuto" };
		final int[] headerRemaining = new int[endpoints.length];
		
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
		
	}
	
	
	private static byte[] generatePayload(int payloadSize) {
		byte[] ret = new byte[payloadSize];
		Arrays.fill(ret, (byte) 97);
		return ret;
	}
}
