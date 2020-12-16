package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.filtri;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.SoapBodies;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class SoapTest extends ConfigLoader {
	
	static final String basePath = System.getProperty("govway_base_path");
	
	@Test
	public void filtroSoggettoErogazione() {
		filtroRichiedente(TipoServizio.EROGAZIONE, "FiltroSoggettoSoap", Credenziali.nonFiltrateSoggetto, Credenziali.filtrateSoggetto);
	}
	
	
	@Test
	public void filtroApplicativoErogazione() {
		filtroRichiedente(TipoServizio.EROGAZIONE, "FiltroApplicativoSoap", Credenziali.nonFiltrateApplicativo, Credenziali.filtrateApplicativo);
	}
	
	
	@Test
	public void filtroApplicativoFruizione() {
		filtroRichiedente(TipoServizio.FRUIZIONE, "FiltroApplicativoSoap", Credenziali.applicativoSITFNonFiltrato, Credenziali.applicativoSITFFiltrato);
	}
	
	/**
	 * Testiamo che la policy venga applicata solo per il soggetto indicato
	 * nel filtro. Il filtro per soggetto è applicabile solamente per le erogazioni.
	 * 
	 * La policy che andiamo a testare è NumeroRichiesteCompletateConSuccesso con
	 * finestra Oraria.
	 */
	public void filtroRichiedente(TipoServizio tipoServizio, String erogazione, CredenzialiBasic nonFiltrate, CredenzialiBasic filtrate) {
		// Sulla stessa api 
		final int maxRequests = 5;
		
		final PolicyAlias policy = PolicyAlias.ORARIO;
		final int windowSize = Utils.getPolicyWindowSize(policy);
		
		final String idPolicy = tipoServizio == TipoServizio.EROGAZIONE
				? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy)
				: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
				

		final String urlServizio = tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		
		Utils.resetCounters(idPolicy);
		Utils.waitForPolicy(policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		
		// Faccio N richieste che non devono essere conteggiate perchè di un richiedente non filtrato
		
		HttpRequest requestNonFiltrata = new HttpRequest();
		requestNonFiltrata.setContentType("application/soap+xml");
		requestNonFiltrata.setMethod(HttpRequestMethod.POST);
		requestNonFiltrata.setUrl(urlServizio);
		requestNonFiltrata.setUsername(nonFiltrate.username);
		requestNonFiltrata.setPassword(nonFiltrate.password);
		requestNonFiltrata.setContent(SoapBodies.get(policy).getBytes());
		
		Vector<HttpResponse> nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests+1);
		
		assertEquals(maxRequests+1, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);

		// Faccio N richieste che devono far scattare la policy perchè di un richiedente filtrato
		
		HttpRequest requestFiltrata = new HttpRequest();
		requestFiltrata.setContentType("application/soap+xml");
		requestFiltrata.setMethod(HttpRequestMethod.POST);
		requestFiltrata.setUrl(urlServizio);
		requestFiltrata.setUsername(filtrate.username);
		requestFiltrata.setPassword(filtrate.password);
		requestFiltrata.setContent(SoapBodies.get(policy).getBytes());
		
		
		Vector<HttpResponse> filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);

		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		
		Vector<HttpResponse> filtrateResponsesBlocked = Utils.makeSequentialRequests(requestFiltrata, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkFailedRequests(filtrateResponsesBlocked, windowSize, maxRequests);
		
		// Faccio N richieste che non devono essere filtrate e devono passare
		
		nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests+1);
		
		assertEquals(maxRequests+1, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);		
	}
	
	
	
	/*
	 * Analogo al test filtroRichiedente ma filtriamo per ruoli, 
	 */
	@Test
	public void filtroRuoloRichiedenteErogazione() {
		
		String erogazione  = "FiltroRuoloSoap";
		// Sulla stessa api 
		final int maxRequests = 5;
		
		final PolicyAlias policy = PolicyAlias.ORARIO;
		final int windowSize = Utils.getPolicyWindowSize(policy);
		
		final String idPolicy =  dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy);

		final String urlServizio = basePath + "/SoggettoInternoTest/"+erogazione+"/v1";
		
		Utils.resetCounters(idPolicy);
		Utils.waitForPolicy(policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		
		// Faccio una richiesta con soggetto con ruolo che deve essere filtrato
		
		HttpRequest requestFiltrata = new HttpRequest();
		requestFiltrata.setContentType("application/soap+xml");
		requestFiltrata.setMethod(HttpRequestMethod.POST);
		requestFiltrata.setUrl(urlServizio);
		requestFiltrata.setUsername(Credenziali.soggettoRuoloFiltrato.username);
		requestFiltrata.setPassword(Credenziali.soggettoRuoloFiltrato.password);
		requestFiltrata.setContent(SoapBodies.get(policy).getBytes());

		Vector<HttpResponse> filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, 1);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 1, 0);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		
		// Faccio N richieste con soggetto senza ruolo che non deve essere contato
		
		HttpRequest requestNonFiltrata = new HttpRequest();
		requestNonFiltrata.setContentType("application/soap+xml");
		requestNonFiltrata.setMethod(HttpRequestMethod.POST);
		requestNonFiltrata.setUrl(urlServizio);
		requestNonFiltrata.setUsername(Credenziali.nonFiltrateSoggetto.username);
		requestNonFiltrata.setPassword(Credenziali.nonFiltrateSoggetto.password);
		requestNonFiltrata.setContent(SoapBodies.get(policy).getBytes());

		
		Vector<HttpResponse> nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests+1);
		
		assertEquals(maxRequests+1, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 1, 0);

		
		// Seconda richiesta con altro soggetto con ruolo che deve essere filtrato
		
		requestFiltrata.setUsername(Credenziali.soggettoRuoloFiltrato2.username);
		requestFiltrata.setPassword(Credenziali.soggettoRuoloFiltrato2.password);
		
		filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, 1);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 2, 0);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		
		
		// Altre N richieste con applicativo senza ruolo che non deve essere contato
		
		requestNonFiltrata.setUsername(Credenziali.nonFiltrateApplicativo.username);
		requestNonFiltrata.setPassword(Credenziali.nonFiltrateApplicativo.password);
		
		nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests+1);
		
		assertEquals(maxRequests+1, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 2, 0);

	
		// Terza richiesta con applicativo con ruolo che deve essere filtrato
		
		requestFiltrata.setUsername(Credenziali.applicativoRuoloFiltrato.username);
		requestFiltrata.setPassword(Credenziali.applicativoRuoloFiltrato.password);

		filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, 1);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 3, 0);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		// Restanti richieste con altro applicativo con ruolo che deve essere filtrato
		
		requestFiltrata.setUsername(Credenziali.applicativoRuoloFiltrato2.username);
		requestFiltrata.setPassword(Credenziali.applicativoRuoloFiltrato2.password);
		

		filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, 2);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		
		// Faccio altre n richieste che devono essere tutte bloccate.
		
		Vector<HttpResponse> filtrateResponsesBlocked = Utils.makeSequentialRequests(requestFiltrata, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkFailedRequests(filtrateResponsesBlocked, windowSize, maxRequests);
		
		// Faccio altre n richieste che non devono essere conteggiate
		
		nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests);
		
		assertEquals(maxRequests, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);
	}
	
	

	/*
	 * Analogo al test filtroRichiedente ma filtriamo per ruoli, 
	 */
	@Test
	public void filtroRuoloRichiedenteFruizione() {
		
		String erogazione  = "FiltroRuoloSoap";
		// Sulla stessa api 
		final int maxRequests = 5;
		
		final PolicyAlias policy = PolicyAlias.ORARIO;
		final int windowSize = Utils.getPolicyWindowSize(policy);
		
		final String idPolicy =  dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);

		final String urlServizio = basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		
		Utils.resetCounters(idPolicy);
		Utils.waitForPolicy(policy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		
		// Faccio maxRequests con applicativo con ruolo filtrato che deve essere conteggiata
		
		HttpRequest requestFiltrata = new HttpRequest();
		requestFiltrata.setContentType("application/soap+xml");
		requestFiltrata.setMethod(HttpRequestMethod.POST);
		requestFiltrata.setUrl(urlServizio);
		requestFiltrata.setUsername(Credenziali.applicativoSITFRuoloFiltrato.username);
		requestFiltrata.setPassword(Credenziali.applicativoSITFRuoloFiltrato.password);
		requestFiltrata.setContent(SoapBodies.get(policy).getBytes());

		Vector<HttpResponse> filtrateResponsesOk = Utils.makeSequentialRequests(requestFiltrata, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkOkRequests(filtrateResponsesOk, windowSize, maxRequests);
		
		// Faccio N richieste con applicativo con ruolo non filtrato che non deve essere conteggiata
		
		HttpRequest requestNonFiltrata = new HttpRequest();
		requestNonFiltrata.setContentType("application/soap+xml");
		requestNonFiltrata.setMethod(HttpRequestMethod.POST);
		requestNonFiltrata.setUrl(urlServizio);
		requestNonFiltrata.setUsername(Credenziali.applicativoSITFRuoloNonFiltrato.username);
		requestNonFiltrata.setPassword(Credenziali.applicativoSITFRuoloNonFiltrato.password);
		requestNonFiltrata.setContent(SoapBodies.get(policy).getBytes());

		
		Vector<HttpResponse> nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests+1);
		
		assertEquals(maxRequests+1, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		
		
		// Faccio altre n richieste che devono essere tutte bloccate.
		
		Vector<HttpResponse> filtrateResponsesBlocked = Utils.makeSequentialRequests(requestFiltrata, maxRequests);
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.SoapTest.checkFailedRequests(filtrateResponsesBlocked, windowSize, maxRequests);
		
		// Faccio altre n richieste che non devono essere conteggiate
		
		nonFiltrateResponses = Utils.makeSequentialRequests(requestNonFiltrata, maxRequests);
		
		assertEquals(maxRequests, nonFiltrateResponses.stream().filter(r -> r.getResultHTTPOperation() == 200).count());
		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, maxRequests);
	}

}
