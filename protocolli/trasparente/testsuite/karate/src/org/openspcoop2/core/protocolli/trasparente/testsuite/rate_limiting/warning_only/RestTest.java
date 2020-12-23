package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.warning_only;

import static org.junit.Assert.assertEquals;

import java.util.Map;
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

	private static final String basePath = System.getProperty("govway_base_path");

	
	@Test
	public void erogazione() {
		testWarningOnly(TipoServizio.EROGAZIONE);
	}
	
	
	@Test
	public void fruizione() {
		testWarningOnly(TipoServizio.FRUIZIONE);
	}
	
	
	public void testWarningOnly(TipoServizio tipoServizio) {
		
		final PolicyAlias policy = PolicyAlias.ORARIO;
		final PolicyAlias policy2 = PolicyAlias.GIORNALIERO;
		
		final int maxRequests = 5;
		final int windowSize = Utils.getPolicyWindowSize(policy);
		final String erogazione = "WarningOnlyRest";
		
		String path = Utils.getPolicyPath(policy);
		
		
		String idPolicy = tipoServizio == TipoServizio.EROGAZIONE
				? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy)
				: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy);
		
		
		String idPolicy2 = tipoServizio == TipoServizio.EROGAZIONE
						? dbUtils.getIdPolicyErogazione("SoggettoInternoTest", erogazione, policy2)
						: dbUtils.getIdPolicyFruizione("SoggettoInternoTestFruitore", "SoggettoInternoTest", erogazione, policy2);

										
		Utils.resetCounters(idPolicy);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, 0, 0);
		
		
		Utils.resetCounters(idPolicy2);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, 0, 0);
		
		Utils.waitForPolicy(policy2);
		Utils.waitForPolicy(policy);
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/"+erogazione+"/v1/"+path);
						
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, maxRequests);		
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests, 0);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, maxRequests, 0);
		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkOkRequests(responses, windowSize, maxRequests);
		
		Vector<HttpResponse>  warningResponses = Utils.makeParallelRequests(request, maxRequests);
		Utils.checkConditionsNumeroRichieste(idPolicy, 0, maxRequests*2, 0);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, maxRequests, 0);
		
		Vector<HttpResponse>  blockedResponses = Utils.makeParallelRequests(request, 5);
		Utils.checkConditionsNumeroRichieste(idPolicy2, 0, maxRequests*2, 5);

		org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste_completate_con_successo.RestTest.checkOkRequests(warningResponses, windowSize, maxRequests);
		
		// Pesco dal db l'evento collegato ad una delle warningResponses e controllo che abbia esito Warning RateLimiting
		int idx = (int) (Math.random()*maxRequests);
		String tid = warningResponses.get(idx).getHeader("GovWay-Transaction-ID");
		Map<String, Object> result = dbUtils.readRow("select id,esito from transazioni where id ='"+tid+"'");
		Integer esito = (Integer) result.get("esito");
		
		assertEquals(Integer.valueOf(19), esito);
		
	}

}
