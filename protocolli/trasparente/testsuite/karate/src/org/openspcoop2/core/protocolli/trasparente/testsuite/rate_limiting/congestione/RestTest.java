package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.congestione;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

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
	
	/** 
	 * 	Controlliamo che la policy di rate limiting venga applicata solo in
	 *  presenza di congestione.
	 * @throws Exception 
	 */
	@Test
	public void rateLimitingInPresenzaCongestione() throws Exception {
		final int maxRequests = 5;
		final String erogazione = "InPresenzaCongestioneRest";
		// La policy di RL è: NumeroRichiesteCompletateConSuccesso.

		// Faccio n richieste per superare la policy e controllo che non scatti,
		// perchè la congestione non è attiva.
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(basePath + "/SoggettoInternoTest/"+erogazione+"/v1/minuto");
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests+1);
		
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
		assertEquals( sogliaCongestione+1, congestionResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		
		// Eseguo il normale test sulla policy di rate limiting
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.testErogazione(erogazione, PolicyAlias.MINUTO);	
	}
	
	
	/**
	 * Controlliamo che la policy di rate limiting venga applicata solo
	 * in presenza di degrado prestazionale, facendo scattare il degrado
	 * superando il tempo medio risposta globale
	 */
	@Test
	public void rateLimitingInPresenzaDegradoGlobale() {
		// La policy di RL è: NumeroRichiesteCompletateConSuccesso.

		
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
