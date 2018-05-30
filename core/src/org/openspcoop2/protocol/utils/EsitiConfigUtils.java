package org.openspcoop2.protocol.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.openspcoop2.core.config.Tracciamento;
import org.openspcoop2.protocol.sdk.constants.EsitoTransazioneName;
import org.openspcoop2.protocol.utils.EsitiProperties;


public class EsitiConfigUtils {

	public static List<String> getRegistrazioneEsiti(Tracciamento config, Logger log, StringBuffer bf) throws Exception{
		if(config==null || config.getEsiti()==null || "".equals(config.getEsiti().trim())){
			
			// creo un default composto da tutti ad eccezione dell'esito (MaxThreads)
			EsitiProperties esiti = EsitiProperties.getInstance(log);
			List<Integer> esitiCodes = esiti.getEsitiCode();
			
			if(esitiCodes!=null && esitiCodes.size()>0){
				List<String> esitiDaRegistrare = new ArrayList<String>();
				for (Integer esito : esitiCodes) {
					int esitoMaxThreads = esiti.convertNameToCode(EsitoTransazioneName.CONTROLLO_TRAFFICO_MAX_THREADS.name());
					if(esito!=esitoMaxThreads){
						if(bf.length()>0){
							bf.append(",");
						}
						bf.append(esito);
						esitiDaRegistrare.add(esito+"");
					}
				}
				if(esitiDaRegistrare.size()>0){
					return esitiDaRegistrare;
				}
			}
			
			return null; // non dovrebbe succedere, degli esiti nell'EsitiProperties dovrebbero esistere
		}
		else{
			
			String [] tmp = config.getEsiti().split(",");
			if(tmp!=null && tmp.length>0){
				List<String> esitiDaRegistrare = new ArrayList<String>();
				for (int i = 0; i < tmp.length; i++) {
					String t = tmp[i];
					if(t!=null){
						t = t.trim();
						if(!"".equals(t)){
							if(bf.length()>0){
								bf.append(",");
							}
							bf.append(t);
							esitiDaRegistrare.add(t);
						}
					}
				}
				if(esitiDaRegistrare.size()>0){
					return esitiDaRegistrare;
				}
			}
			
			return null; // non dovrebbe succedere, si rientra nel ramo then dell'if principale
		}
	}
	
}