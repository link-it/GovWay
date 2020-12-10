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

package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.congestione;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;

public class RestTest extends ConfigLoader {

	private static final String basePath = System.getProperty("govway_base_path");
	public static final int sogliaCongestione = Integer.valueOf(System.getProperty("soglia_congestione"));
	
	@Test
	public void congestioneAttivaErogazione() {
		congestioneAttiva(basePath + "/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000");
	}
	
	
	@Test
	public void congestioneAttivaFruizione() {
		congestioneAttiva(basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000");
	}
	
	
	@Test
	public void congestioneAttivaConViolazioneRLErogazione() {
		final String idServizio = "SoggettoInternoTest/NumeroRichiesteRest/v1";
		congestioneAttivaConViolazioneRL(basePath + "/SoggettoInternoTest/NumeroRichiesteRest/v1/richieste-simultanee/?sleep=5000", idServizio);
	}
	
	@Test
	public void congestioneAttivaConViolazioneRLFruizione() {
		final String idServizio = "SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1";
		congestioneAttivaConViolazioneRL(basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/richieste-simultanee?sleep=5000", idServizio);
	}
	
	@Test
	public void congestioneAttivaViolazioneRichiesteComplessiveErogazione() {
		congestioneAttivaViolazioneRichiesteComplessive(basePath + "/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000");
		
	}
	
	@Test
	public void congestioneAttivaViolazioneRichiesteComplessiveFruizione() {
		congestioneAttivaViolazioneRichiesteComplessive(basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000");
		
	}
	
	@Test
	public void rateLimitingInPresenzaCongestioneErogazione() throws Exception {
		rateLimitingInPresenzaCongestione(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void rateLimitingInPresenzaCongestioneFruizione() throws Exception {
		rateLimitingInPresenzaCongestione(TipoServizio.FRUIZIONE);
	}
	
	@Test
	public void noRateLimitingSeCongestioneRisoltaErogazione() throws UtilsException, HttpUtilsException {
		noRateLimitingSeCongestioneRisolta(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void noRateLimitingSeCongestioneRisoltaFruizione() throws UtilsException, HttpUtilsException {
		noRateLimitingSeCongestioneRisolta(TipoServizio.FRUIZIONE);
		
	}
	

	/** 
	 * 	Controlliamo che la policy di rate limiting venga applicata solo in
	 *  presenza di congestione.
	 *  
	 *	La policy di RL è: NumeroRichiesteCompletateConSuccesso.
	 */
	public void rateLimitingInPresenzaCongestione(TipoServizio tipoServizio) throws Exception {
		
		final int maxRequests = 5;
		final String erogazione = "InPresenzaCongestioneRest";
		final PolicyAlias policy = PolicyAlias.ORARIO;
		
		final String idPolicy = tipoServizio == TipoServizio.EROGAZIONE
				? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy)
				: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		
		final String urlServizio = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1/orario"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1/orario";
				
		final BiConsumer<String,PolicyAlias> testToRun = tipoServizio == TipoServizio.EROGAZIONE
				? org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest::testErogazione
				: org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest::testFruizione;
		
		
		Utils.resetCounters(idPolicy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		Utils.waitForPolicy(policy);


		// Faccio n richieste per superare la policy e controllo che non scatti,
		// perchè la congestione non è attiva.
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(urlServizio);
						
		Vector<HttpResponse> responses = Utils.makeRequestsAndCheckPolicy(request, maxRequests+1, idPolicy);
		
		// Controllo che non sia scattata la policy
		assertEquals( maxRequests+1, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		// Faccio attivare la congestione
		String url = basePath + "/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=10000";
		HttpRequest congestionRequest = new HttpRequest();
		congestionRequest.setContentType("application/json");
		congestionRequest.setMethod(HttpRequestMethod.GET);
		congestionRequest.setUrl(url);
		
		int count = sogliaCongestione;
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);

		for (int i = 0; i < count; i++) {
			executor.execute(() -> {
				try {
					logRateLimiting.info(congestionRequest.getMethod() + " " + congestionRequest.getUrl());
					 HttpResponse r = HttpUtilities.httpInvoke(congestionRequest);
					 assertEquals(200, r.getResultHTTPOperation());
					logRateLimiting.info("Richiesta effettuata..");
				} catch (UtilsException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});
		}
		
		// Aspetto che il sistema vada in congestione..
		org.openspcoop2.utils.Utilities.sleep(Long.parseLong(System.getProperty("congestion_delay")));
		
		responses = Utils.makeSequentialRequests(request, maxRequests+1);
		
		// Tutte le risposte devono essere bloccate, perchè siamo in congestione
		// e le richieste iniziali sono state conteggiate
		assertEquals( maxRequests+1, responses.stream().filter(r -> r.getResultHTTPOperation() == 429).count());
		logRateLimiting.info(Utils.getPolicy(idPolicy));

		// Nel mentre siamo in congestione rieseguo per intero il test sul Numero Richieste Completate con successo
		testToRun.accept(erogazione, PolicyAlias.ORARIO);
				
		try {
			executor.shutdown();
			executor.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logRateLimiting.error("Le richieste hanno impiegato più di venti secondi!");
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	/**
	 * Controlliamo che la policy di rate limiting non venga applicata se lo stato 
	 * di congestione è stato risolto.
	 * 
	 */
	public void noRateLimitingSeCongestioneRisolta(TipoServizio tipoServizio) throws UtilsException, HttpUtilsException {
		final int maxRequests = 5;
		final String erogazione = "InPresenzaCongestioneRest";
		final PolicyAlias policy = PolicyAlias.ORARIO;
		
		final String idPolicy = tipoServizio == TipoServizio.EROGAZIONE
				? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy)
				: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		
		final String urlServizio = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1/orario"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1/orario";
		
		Utils.resetCounters(idPolicy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		Utils.waitForPolicy(policy);

		// La policy di RL è: NumeroRichiesteCompletateConSuccesso.

		// Faccio n richieste per superare la policy e controllo che non scatti,
		// perchè la congestione non è attiva.
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(urlServizio);
						
		// Anche se la congestione non è attiva, comunque le richieste devono essere conteggiate
		Vector<HttpResponse> responses = Utils.makeRequestsAndCheckPolicy(request, maxRequests+1, idPolicy);
				
		// Controllo che non sia scattata la policy
		assertEquals( maxRequests+1, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		
		// Faccio attivare la congestione
		String url = basePath + "/SoggettoInternoTest/NumeroRichiesteRest/v1/no-policy?sleep=5000";
		HttpRequest congestionRequest = new HttpRequest();
		congestionRequest.setContentType("application/json");
		congestionRequest.setMethod(HttpRequestMethod.GET);
		congestionRequest.setUrl(url);
		
		Vector<HttpResponse> congestionResponses = Utils.makeParallelRequests(congestionRequest, sogliaCongestione+1);
		
		// Verifico che siano andate tutte bene
		assertEquals(sogliaCongestione+1, congestionResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
	
		// Siccome il congestionamento termina nel momento in cui terminano le richieste simultanee,
		// se faccio richieste conteggiate dalla policy, questa comunque non deve scattare.
		
		responses = Utils.makeSequentialRequests(request, maxRequests+1);
		
		// Controllo che non sia scattata la policy
		assertEquals(maxRequests+1, responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());		
	}
	
	
	
	
	/**
	 * Controlliamo che la policy di rate limiting venga applicata solo
	 * in presenza di degrado prestazionale, facendo scattare il degrado
	 * superando il tempo medio risposta globale

 		La policy di RL è: NumeroRichiesteCompletateConSuccesso.
 		
	 */
	@Test
	public void rateLimitingInPresenzaDegradoGlobale() throws UtilsException, HttpUtilsException {
		final int maxRequests = 5;
		final String erogazione = "InPresenzaDegradoRest";
		final PolicyAlias policy = PolicyAlias.ORARIO;
		final String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);
		
		Utils.resetCounters(idPolicy);
		Utils.waitForPolicy(policy);
		
		//Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		//


		// Faccio n richieste per superare la policy e controllo che non scatti,
		// e nemmeno che le richieste vengano conteggiate, perchè la congestione non è attiva.
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(basePath + "/SoggettoInternoTest/"+erogazione+"/v1/orario");
						
		
		Vector<HttpResponse> responses = Utils.makeSequentialRequests(request, maxRequests+1);
		Utils.waitForZeroActiveRequests(idPolicy, 0);
		
		
		// Faccio le richieste verificando che la policy non venga attivata
		
		// Faccio due richieste parallele da 4 secondi l'una per entrare in degrado prestazionale
		
		// Rifaccio le richieste e verifico che la policy sia stata attivata
		
	}
	
	
	/**
	 * Controlliamo che la policy di rate limiting venga applicata solo
	 * in presenza di degrado prestazionale, facendo scattare il degrado
	 * superando il tempo medio risposta sul singolo connettore
	 */
	@Test
	public void rateLimitingInPresenzaDegradoServizio() {
		
	}
	
	
	/**
	 * Controlliamo che la policy di rate limiting venga applicata solo
	 * se contemporaneamente attivi il degrado prestazionale, e la congestione
	 */
	@Test
	public void rateLimitingInPresenzaDegradoECongestione() {
		
	}
	
	
	
	/** 
	 * 	Qui si testa la generazione dell'evento di congestione e del successivo evento
	 *  che segnala la violazione del massimo numero di richieste simultanee
	 */
	public void congestioneAttivaViolazioneRichiesteComplessive(String url) {
			
		final int sogliaRichiesteSimultanee = 15;
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(url);
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaRichiesteSimultanee+1);
		
		SoapTest.checkCongestioneAttivaViolazioneRichiesteComplessive(dataSpedizione, responses);
	}		
	
	
	/**
	 * Controlla che il sistema registri gli eventi di congestione
	 * e gli eventi di violazione di una policy di rate limiting.
	 * 
	 */
	public void congestioneAttivaConViolazioneRL(String url, String idErogazione) {
		
		final int sogliaRichiesteSimultanee = 10;
		
		// Affinchè il test faccia scattare tutti e due gli eventi è necessario
		// che la soglia di congestione sia più bassa della soglia di RL
		assertTrue(sogliaRichiesteSimultanee > sogliaCongestione);
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(url);
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaRichiesteSimultanee+1);
		
		EventiUtils.checkEventiCongestioneAttivaConViolazioneRL(idErogazione, dataSpedizione, Optional.empty(), responses);
	}
	
	
	/**
	 * Controlla che il sistema entri effettivamente in congestione.
	 * 
	 */
	
	public void congestioneAttiva(String url) {
		
		LocalDateTime dataSpedizione = LocalDateTime.now();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(url);
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaCongestione+1);
		
		EventiUtils.checkEventiCongestioneAttiva(dataSpedizione, responses);
	}
}
