package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.tempo_complessivo_risposta;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

public class Commons {

	static void checkPreConditionsTempoComplessivoRisposta(String idPolicy)  {
		
		Logger logRateLimiting = LoggerWrapperFactory.getLogger("testsuite.rate_limiting");
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = Utils.getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}				
				logRateLimiting.info(jmxPolicyInfo);
				Map<String, String> policyValues = Utils.parsePolicy(jmxPolicyInfo);
				
				assertEquals("0", policyValues.get("Richieste Attive"));
				assertEquals("0", policyValues.get("Numero Richieste Conteggiate"));
				assertEquals("0 secondi (0 ms)", policyValues.get("Contatore"));
				assertEquals("0 secondi (0 ms)", policyValues.get("Valore Medio"));
				assertEquals("0", policyValues.get("Numero Richieste Bloccate"));
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}

	static void checkPostConditionsTempoComplessivoRisposta(String idPolicy)  {
		
		Logger logRateLimiting = LoggerWrapperFactory.getLogger("testsuite.rate_limiting");
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = Utils.getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}				
				logRateLimiting.info(jmxPolicyInfo);
				Map<String, String> policyValues = Utils.parsePolicy(jmxPolicyInfo);
				
				assertEquals("0", policyValues.get("Richieste Attive"));
				assertEquals("1", policyValues.get("Numero Richieste Conteggiate"));
				assertEquals("1", policyValues.get("Numero Richieste Bloccate"));
				
				String secondiContatore = policyValues.get("Contatore").split(" ")[0];
				assertEquals("2", secondiContatore);
				
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}

}