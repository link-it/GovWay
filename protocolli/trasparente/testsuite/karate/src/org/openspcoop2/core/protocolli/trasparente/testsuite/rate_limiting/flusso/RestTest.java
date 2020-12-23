package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.flusso;

import java.util.Vector;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.TipoServizio;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;

public class RestTest extends ConfigLoader {

	/**
	 * Le policy per una metrica vengono valutate dall'alto verso il basso.
	 * Quando il flusso di elaborazione per una policy è impostato su "Interrompi", nel momento in cui una policy per quella metrica viene
	 * conteggtiata, si interrompe il controllo delle policy.
	 * 
	 * Impostando l'elaborazione a "Prosegui", il conteggio continua.
	 * Questi test verificano che il conteggio continui.
	 */
	private static final String basePath = System.getProperty("govway_base_path");
	
	@Test
	public void controlloFlussoErogazione() {
		controlloFlusso(TipoServizio.EROGAZIONE);
	}
	
	@Test
	public void controlloFlussoFruizione() {
		controlloFlusso(TipoServizio.FRUIZIONE);
	}
	
	public void controlloFlusso(TipoServizio tipoServizio) {
		
		final String erogazione = "FlussoValutazionePolicyRest";
		final String urlServizio =  tipoServizio == TipoServizio.EROGAZIONE
				? basePath + "/SoggettoInternoTest/"+erogazione+"/v1"
				: basePath + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/"+erogazione+"/v1";
		
		PolicyAlias policy1 = PolicyAlias.ORARIO;				// Elaborazione: Prosegui
		PolicyAlias policy2 = PolicyAlias.MINUTO;				// Elaborazione: Interrompi
		PolicyAlias policy3 = PolicyAlias.GIORNALIERO;			// Elaborazione: Prosegui
		
		int maxRequestsPolicy1 = 10;
		int maxRequestsPolicy3 = 5;
		
		int windowSizePolicy1 = Utils.getPolicyWindowSize(policy1);
		int windowSizePolicy3 = Utils.getPolicyWindowSize(policy3);
		
		final String idPolicy1 = tipoServizio == TipoServizio.EROGAZIONE
				? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy1)
				: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy1);
				
		final String idPolicy2 = tipoServizio == TipoServizio.EROGAZIONE
					? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy2)
					: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy2);
					
		final String idPolicy3 = tipoServizio == TipoServizio.EROGAZIONE
							? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy3)
							: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy3);
		
		Utils.resetCounters(idPolicy1);
		Utils.checkConditionsNumeroRichieste(idPolicy1, 0, 0, 0);
		
		Utils.resetCounters(idPolicy2);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, 0, 0);
		
		Utils.resetCounters(idPolicy3);
		Utils.checkConditionsNumeroRichieste(idPolicy3, 0, 0, 0);

		// Su quale policy devo aspettare?
		// Utils.waitForPolicy(policy3);			 
		
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(urlServizio + "/orario");
		
		// Faccio maxRequestsPolicy2 Richieste e verifico che vengano conteggiate
		Vector<HttpResponse> responsesOk = Utils.makeSequentialRequests(request, maxRequestsPolicy3);
		
		// Gli headers vengono valorizzati con i dati della policy più stringente, per questo passo le info sulla policy 3
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkOkRequests(responsesOk, windowSizePolicy3, maxRequestsPolicy3);
		
		Utils.checkConditionsNumeroRichieste(idPolicy1, 0, maxRequestsPolicy3, 0);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, 0, 0);
		Utils.checkConditionsNumeroRichieste(idPolicy3, 0, maxRequestsPolicy3, 0);
		
		
		Vector<HttpResponse> responsesFailed = Utils.makeSequentialRequests(request, 3);
		
		Utils.checkConditionsNumeroRichieste(idPolicy1, 0, maxRequestsPolicy3, 3);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, 0, 0);
		Utils.checkConditionsNumeroRichieste(idPolicy3, 0, maxRequestsPolicy3, 3);
		
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkFailedRequests(responsesFailed, windowSizePolicy3, maxRequestsPolicy3);

		
		// Verifico che vengano conteggiate Orario e Giornaliero, perchè
		// "Minuto" ha dei filtri che non fanno attivare il conteggio
	}
}
