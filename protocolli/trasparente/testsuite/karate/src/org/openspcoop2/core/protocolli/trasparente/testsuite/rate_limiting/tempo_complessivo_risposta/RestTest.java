package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.tempo_complessivo_risposta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.NumeroRichiestePolicyInfo;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;
import org.slf4j.Logger;

public class RestTest extends ConfigLoader {
	
	private final static Logger log = LoggerWrapperFactory.getLogger(RestTest.class);
	
	private static void checkPreConditionsTempoComplessivoRisposta(String idPolicy)  {
		String jmxPolicyInfo = Utils.getPolicy(idPolicy);
		System.out.println(jmxPolicyInfo);
		
		// Se non sono mai state fatte richieste che attivano la policy, ottengo questa
		// risposta, e le precondizioni sono soddisfatte
		
		if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
			return;
		}
		
		/*NumeroRichiestePolicyInfo polInfo = new NumeroRichiestePolicyInfo(jmxPolicyInfo);
		
		assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
		assertEquals(Integer.valueOf(0), polInfo.richiesteConteggiate);
		assertEquals(Integer.valueOf(0), polInfo.richiesteBloccate);*/
	}
	
	
	private static void checkPostConditionsTempoComplessivoRisposta(String idPolicy)  {
		String jmxPolicyInfo = Utils.getPolicy(idPolicy);
		System.out.println(jmxPolicyInfo);
		
		// Se non sono mai state fatte richieste che attivano la policy, ottengo questa
		// risposta, e le precondizioni sono soddisfatte
		
		if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
			return;
		}
		
		/*NumeroRichiestePolicyInfo polInfo = new NumeroRichiestePolicyInfo(jmxPolicyInfo);
		
		assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
		assertEquals(Integer.valueOf(0), polInfo.richiesteConteggiate);
		assertEquals(Integer.valueOf(0), polInfo.richiesteBloccate);*/
	}
	
	@Test
	public void perMinutoErogazione() throws UtilsException, HttpUtilsException, InterruptedException {
		//gw_SoggettoInternoTest/gw_TempoComplessivoRisposta/v1
		String idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		Utils.resetCounters(idPolicy);
		
		idPolicy = dbUtils.getIdPolicyErogazione("SoggettoInternoTest", "TempoComplessivoRisposta", PolicyAlias.MINUTO);
		Utils.checkPreConditionsNumeroRichieste(idPolicy); 
		
		// Aspetto lo scoccare del minuto
		
		Utils.waitForNewMinute();
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=2000");
		
		// TODO: Perchè il logger non mi logga?
						
		HttpResponse response = HttpUtilities.httpInvoke(request);
		System.out.println("ResponseStatus: " + response.getResultHTTPOperation());
		System.out.println("ResponseHeaders: " + response.getHeaders());
		System.out.println("ResponseBody: " + new String(response.getContent()));
		
		request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/TempoComplessivoRisposta/v1/minuto?sleep=500");
						
		response = HttpUtilities.httpInvoke(request);
		System.out.println("ResponseStatus: " + response.getResultHTTPOperation());
		System.out.println("ResponseHeaders: " + response.getHeaders());
		System.out.println("ResponseBody: " + new String(response.getContent()));
		
		
		checkPostConditionsTempoComplessivoRisposta(idPolicy);		
	}

}
