package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste;

public class RichiesteSimultaneePolicyInfo {
	
	public Integer richiesteAttive = null;
	
	public RichiesteSimultaneePolicyInfo(String jmxPolicyInfo) {		
		String[] lines = jmxPolicyInfo.split(System.lineSeparator());
		for (String l : lines) {
			l = l.strip();
			String[] keyValue = l.split(":");
			if(keyValue.length == 2) {
				if (keyValue[0].equals("Richieste Attive")) {
					this.richiesteAttive = Integer.valueOf(keyValue[1].strip());
				}
			}
		}
		if (this.richiesteAttive == null) {
			throw new RuntimeException("Errore durante la lettura della policy, informazioni mancanti.");
		}		
	}

}
