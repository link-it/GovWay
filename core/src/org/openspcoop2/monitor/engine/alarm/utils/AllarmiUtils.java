package org.openspcoop2.monitor.engine.alarm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openspcoop2.core.allarmi.Allarme;
import org.openspcoop2.core.allarmi.AllarmeFiltro;
import org.openspcoop2.core.allarmi.constants.StatoAllarme;
import org.openspcoop2.core.allarmi.constants.TipoAllarme;
import org.openspcoop2.core.allarmi.utils.AllarmiConverterUtils;
import org.openspcoop2.monitor.engine.alarm.wrapper.ConfigurazioneAllarmeBean;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.utils.beans.BeanUtils;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.slf4j.Logger;

public class AllarmiUtils {

	
	/***
	 * 
	 * Restituisce true se i tipi indicati sono compatibili, false altrimenti.
	 * 
	 * @param filtro
	 * @return
	 */
	public static boolean controllaTipiIndicatiNelFiltro(AllarmeFiltro filtro, String valoreQualsiasi) throws Exception{

		String tipoMittente = filtro.getTipoFruitore();
		String tipoDestinatario = filtro.getTipoErogatore();
		String tipoServizio = filtro.getTipoServizio();

		List<String> protocolloMittente = new ArrayList<String>();
		List<String> protocolloDestinatario = new ArrayList<String>();
		List<String> protocolloServizio = new ArrayList<String>();


		try{

			// 1. Carico i tipi disponibili per mittente, destinatario e servizio, se uno dei tre non e' stato scelto ('*') la lista risultera' vuota
			if(!tipoDestinatario.equals(valoreQualsiasi) ){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByOrganizationType(tipoDestinatario);
				protocolloDestinatario.add(protocolFactory.getProtocol());
				//				tipiDisponibiliDestinatario = protocolFactory.createProtocolConfiguration().getTipiSoggetti();
			}

			if(!tipoMittente.equals(valoreQualsiasi)){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByOrganizationType(tipoMittente);
				protocolloMittente.add(protocolFactory.getProtocol());
				//				tipiDisponibiliMittente = protocolFactory.createProtocolConfiguration().getTipiSoggetti();
			}

			if(!tipoServizio.equals(valoreQualsiasi)){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByServiceType(tipoServizio);
				protocolloServizio.add(protocolFactory.getProtocol());
				//				tipiDisponibiliServizio = protocolFactory.createProtocolConfiguration().getTipiServizi();
			}

			// Inserisco tutti i tipi trovati in una mappa, se i tre tipi sono compatibili, l'elenco delle chiavi coincide con la dimesione delle liste di quelli settati.
			Map<String, String> mappaProtocolli = new HashMap<String, String>();
			if(protocolloServizio != null && protocolloServizio.size() > 0)
				for (String tipo : protocolloServizio) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}

			if(protocolloMittente != null && protocolloMittente.size() > 0)
				for (String tipo : protocolloMittente) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}

			if(protocolloDestinatario != null && protocolloDestinatario.size() > 0)
				for (String tipo : protocolloDestinatario) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}	

			// controllo di validita  
			if(protocolloDestinatario.size() > 0 && protocolloDestinatario.size() != mappaProtocolli.keySet().size())
				return false;

			if(protocolloMittente.size() > 0 && protocolloMittente.size() != mappaProtocolli.keySet().size())
				return false;

			if(protocolloServizio.size() > 0 && protocolloServizio.size() != mappaProtocolli.keySet().size())
				return false;

			return true;
		}catch(Exception e){
			throw e;
		}
	}
	
	public static void sendToAllarmi(List<String> urls, Logger log) throws Exception{
		if(urls!=null && urls.size()>0){
			for (String url : urls) {
				log.debug("Invoke ["+url+"] ...");
				HttpResponse response = HttpUtilities.getHTTPResponse(url);
				if(response.getContent()!=null){
					log.debug("Invoked ["+url+"] Status["+response.getResultHTTPOperation()+"] Message["+new String(response.getContent())+"]");	
				}
				else{
					log.debug("Invoked ["+url+"] Status["+response.getResultHTTPOperation()+"]");
				}
				if(response.getResultHTTPOperation()>202){
					throw new Exception("Error occurs during invoke url["+url+"] Status["+response.getResultHTTPOperation()+"] Message["+new String(response.getContent())+"]");	
				}	
			}
		}
	}
	
	public static void notifyStateActiveThread(boolean isAdd, boolean modificatoStato, boolean modificatoAckwoldegment,
			ConfigurazioneAllarmeBean oldAllarmePrimaModifica, ConfigurazioneAllarmeBean allarme, Logger log, AllarmiConfig allarmiConfig) throws Exception{
		if(TipoAllarme.PASSIVO.equals(allarme.getTipoAllarme())){
			// NOTA: il tipo di allarme non è modificabile.
			log.debug("Allarme ["+allarme.getNome()+"] è passivo. Non viene attivato alcun thread");
			return;
		}
		
		String prefixUrl = allarmiConfig.getAllarmiActiveServiceUrl();
		if(prefixUrl.endsWith("/")==false){
			prefixUrl = prefixUrl + "/";
		}
		prefixUrl = prefixUrl + allarme.getNome() + "?";
		List<String> urls = new ArrayList<String>();
		if(isAdd){
			if(allarme.getEnabled()==1){
				// invoco servlet start
				urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixStartAlarm());
			}
		}
		else{
			// Se è stato modificato solo lo stato non richiede un stop/restart 
			boolean equals = false;
			StringBuilder bfDiff = null;
			if(oldAllarmePrimaModifica!=null){
				
				List<String> fieldEsclusi = new ArrayList<String>();
				fieldEsclusi.add("id");
				if(modificatoStato){
					fieldEsclusi.add("stato");
					fieldEsclusi.add("_value_stato");
					fieldEsclusi.add("precedenteStato");
					fieldEsclusi.add("_value_precedenteStato");
					fieldEsclusi.add("dettaglioStato");
				}
				if(modificatoAckwoldegment){
					fieldEsclusi.add("acknowledged");
				}
				
				// i metodi equals e diff non funzionano in caso di extends
				
				Allarme old = new Allarme();
				BeanUtils.copy(old, oldAllarmePrimaModifica, null);
				
				Allarme attuale = new Allarme();
				BeanUtils.copy(attuale, allarme, null);
				
				equals = attuale.equals(old, fieldEsclusi);
				if(!equals){
					bfDiff = new StringBuilder();
					attuale.diff(old, bfDiff, false, fieldEsclusi);
				}
			}
			if(equals){			
				if(modificatoStato){
					StatoAllarme statoAllarme = AllarmiConverterUtils.toStatoAllarme(allarme.getStato());
					switch (statoAllarme) {
					case OK:
						urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixUpdateStateOkAlarm());
						break;
					case WARNING:
						urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm());
						break;
					case ERROR:
						urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm());
						break;
					}
				}
				if(modificatoAckwoldegment){
					if(allarme.getAcknowledged()==1){
						urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm());
					}
					else{
						urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm());
					}
				}
				//else{
				// non è cambiato nulla
				//}
			}
			else{
				if(bfDiff!=null){	
					log.debug("Rilevata modifica, diff: "+bfDiff.toString());
				}
				
				if(allarme.getEnabled()==0){
					// invoco servlet stop
					urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixStopAlarm());
				}
				else{
					// invoco servlet restart
					urls.add(prefixUrl+allarmiConfig.getAllarmiActiveServiceUrl_SuffixReStartAlarm());
				}
			}
		}
		AllarmiUtils.sendToAllarmi(urls, log);
	}
}
