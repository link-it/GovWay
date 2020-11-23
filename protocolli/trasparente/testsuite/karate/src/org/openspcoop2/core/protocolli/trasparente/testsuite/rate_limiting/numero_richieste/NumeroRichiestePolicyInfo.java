package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste;

public class NumeroRichiestePolicyInfo {

	public Integer richiesteAttive = null;
	public Integer richiesteConteggiate = null;
	public Integer richiesteBloccate = null;
	
	public NumeroRichiestePolicyInfo(String jmxPolicyInfo) {		
		String[] lines = jmxPolicyInfo.split(System.lineSeparator());
		for (String l : lines) {
			l = l.strip();
			String[] keyValue = l.split(":");
			if(keyValue.length == 2) {
				if (keyValue[0].equals("Richieste Attive")) {
					this.richiesteAttive = Integer.valueOf(keyValue[1].strip());
				}
				if (keyValue[0].equals("Numero Richieste Conteggiate")) {
					this.richiesteConteggiate = Integer.valueOf(keyValue[1].strip());
				}
				if (keyValue[0].equals("Numero Richieste Bloccate")) {
					this.richiesteBloccate = Integer.valueOf(keyValue[1].strip());
				}
			}
		}
		if (this.richiesteAttive == null || this.richiesteConteggiate == null || this.richiesteBloccate == null) {
			throw new RuntimeException("Errore durante la lettura della policy, informazioni mancanti.");
		}		
	}
}
