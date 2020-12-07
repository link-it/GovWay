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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.SoapBodies;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
/**
 * 
 * @author Francesco Scarlato {scarlato@link.it}
 *
 */
public class SoapTest extends ConfigLoader {

	private static final String basePath = System.getProperty("govway_base_path");
	public static final int sogliaCongestione = Integer.valueOf(System.getProperty("soglia_congestione"));
	
	@Test
	public void congestioneAttivaErogazione() {
		congestioneAttiva(basePath + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	
	@Test
	public void congestioneAttivaFruizione() {
		congestioneAttiva(basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	@Test
	public void congestioneAttivaConViolazioneRLErogazione() {
		congestioneAttivaConViolazioneRL(basePath + "/SoggettoInternoTest/NumeroRichiesteSoap/v1", "SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	@Test
	public void congestioneAttivaConViolazioneRLFruizione() {
		congestioneAttivaConViolazioneRL(basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1", "SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	@Test
	public void congestioneAttivaViolazioneRichiesteComplessiveErogazione() {
		String url = basePath + "/SoggettoInternoTest/NumeroRichiesteSoap/v1";
		congestioneAttivaViolazioneRichiesteComplessive(url);
	}
	
	
	@Test
	public void congestioneAttivaViolazioneRichiesteComplessiveFruizione() {
		String url = basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1";
		congestioneAttivaViolazioneRichiesteComplessive(url);
	}
	
	/** 
	 * 	Qui si testa la generazione dell'evento di congestione e del successivo evento
	 *  che segnala la violazione del massimo numero di richieste simultanee
	 */
	public void congestioneAttivaViolazioneRichiesteComplessive(String url) {
		
		
		final int sogliaRichiesteSimultanee = 15;
		
		String body = SoapBodies.get(PolicyAlias.NO_POLICY);
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(url);
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaRichiesteSimultanee+1);
		
		checkCongestioneAttivaViolazioneRichiesteComplessive(dataSpedizione, responses);
	}


	public static void checkCongestioneAttivaViolazioneRichiesteComplessive(LocalDateTime dataSpedizione,
			Vector<HttpResponse> responses) {
		
		EventiUtils.waitForDbEvents();
		
		//  Devo trovare tra le transazioni generate dalle richieste, almeno una transazione
		//  che abba la violazione della soglia di congestione e una transazione con la violazione
		//  della soglia di richieste simultanee globali
			

		var credenzialiToMatch = new ArrayList<String>(Arrays.asList(
				"##ControlloTraffico_SogliaCongestione_Violazione##",
				"##ControlloTraffico_NumeroMassimoRichiesteSimultanee_Violazione##"
				));
		

		credenzialiToMatch.removeIf( toCheck -> responses.stream()
				.anyMatch( r -> EventiUtils.testCredenzialiMittenteTransazione(
							r.getHeader(Headers.TransactionId),
							evento -> {						
								logRateLimiting.info(evento.toString());
								String credenziale = (String) evento.get("credenziale"); 
								return credenziale.contains(toCheck);
							}) 
						)
				);
		
		assertEquals(Arrays.asList(), credenzialiToMatch);

		List<Map<String, Object>> events = EventiUtils.getNotificheEventi(dataSpedizione);		
		logRateLimiting.info(events.toString());
		
		assertEquals(true, EventiUtils.findEventCongestioneViolata(events));
		assertEquals(true, EventiUtils.findEventCongestioneRisolta(events));
		
		boolean sogliaViolata =  events.stream()
				.anyMatch( ev -> {
					return  ev.get("tipo").equals("ControlloTraffico_NumeroMassimoRichiesteSimultanee") &&
							ev.get("codice").equals("Violazione") &&
							ev.get("severita").equals(1) && 
							ev.get("descrizione").equals("Superato il numero di richieste complessive (15) gestibili dalla PdD");
				});
		
		boolean violazioneRisolta = events.stream()
				.anyMatch( ev -> {
					return  ev.get("tipo").equals("ControlloTraffico_NumeroMassimoRichiesteSimultanee") &&
							ev.get("codice").equals("ViolazioneRisolta") &&
							ev.get("severita").equals(3); 
				}); 
		
		assertEquals(true, sogliaViolata);
		assertEquals(true, violazioneRisolta);
	}
	
	
	/**
	 * Controlla che il sistema entri effettivamente in congestione.
	 * 
	 */
	
	public void congestioneAttiva(String url) {
		String body = SoapBodies.get(PolicyAlias.NO_POLICY);
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(url);
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaCongestione+1);
		
		EventiUtils.checkEventiCongestioneAttiva(dataSpedizione, responses);		
	}
	
	
	/**
	 * Controlla che il sistema registri gli eventi di congestione
	 * e gli eventi di violazione di una policy di rate limiting.
	 * 
	 */
	public void congestioneAttivaConViolazioneRL(String url, String idServizio) {
		
		final int sogliaRichiesteSimultanee = 10;
		
		// Affinchè il test faccia scattare tutti e due gli eventi è necessario
		// che la soglia di congestione sia più bassa della soglia di RL
		assertTrue(sogliaRichiesteSimultanee > sogliaCongestione);
		
		String body = SoapBodies.get(PolicyAlias.RICHIESTE_SIMULTANEE);
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(url);
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaRichiesteSimultanee+1);
		
		EventiUtils.checkEventiCongestioneAttivaConViolazioneRL(idServizio, dataSpedizione, Optional.of("RichiesteSimultanee"), responses);
	}
	

	

}
