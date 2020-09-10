/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.openspcoop2.pdd.logger.info;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.transazioni.constants.PddRuolo;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.pdd.logger.LogLevels;
import org.openspcoop2.pdd.logger.MsgDiagnosticiProperties;
import org.openspcoop2.protocol.sdk.constants.EsitoTransazioneName;
import org.openspcoop2.protocol.sdk.diagnostica.MsgDiagnostico;
import org.openspcoop2.protocol.utils.EsitiProperties;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.json.JSONUtils;
import org.slf4j.Logger;

/**
 * InfoEsitoTransazioneFormatUtils
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */
public class InfoEsitoTransazioneFormatUtils {

	public static boolean isEsitoOk(Logger log, Integer esito, String protocollo){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(log,protocollo);
			boolean res = esitiProperties.getEsitiCodeOk_senzaFaultApplicativo().contains(esito);
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+esito+")");
			return res;
		}catch(Exception e){
			log.error("Errore durante l'analisi dell'esito ["+esito+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public static boolean isEsitoFaultApplicativo(Logger log, Integer esito, String protocollo){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(log,protocollo);
			boolean res = esitiProperties.getEsitiCodeFaultApplicativo().contains(esito);
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+esito+")");
			return res;
		}catch(Exception e){
			log.error("Errore durante l'analisi dell'esito ["+esito+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public static boolean isEsitoKo(Logger log, Integer esito, String protocollo){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(log,protocollo);
			boolean res = esitiProperties.getEsitiCodeKo_senzaFaultApplicativo().contains(esito);
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+esito+")");
			return res;
		}catch(Exception e){
			log.error("Errore durante l'analisi dell'esito ["+esito+"]: "+e.getMessage(),e);
			return false;
		}
	}

	public static boolean isVisualizzaFault(Logger log, String fault){
		boolean visualizzaMessaggio = true;

		if(fault == null)
			return false;

		StringBuilder contenutoDocumentoStringBuilder = new StringBuilder();
		String errore = FormatUtils.getTestoVisualizzabile(log, fault.getBytes(),contenutoDocumentoStringBuilder, false);
		if(errore!= null)
			return false;

		return visualizzaMessaggio;
	}

	public static String getFaultPretty(Logger log, String fault, String formatoFault){
		String toRet = null;
		if(fault !=null) {
			StringBuilder contenutoDocumentoStringBuilder = new StringBuilder();
			String errore = FormatUtils.getTestoVisualizzabile(log, fault.getBytes(),contenutoDocumentoStringBuilder, true);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(formatoFault)) {
				messageType = MessageType.valueOf(formatoFault);
			}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(fault));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = FormatUtils.prettifyXml(log, fault);
				break;
			}
		}

		if(toRet == null)
			toRet = fault != null ? fault : "";

		return toRet;
	}


	public static String getDettaglioErrore(Logger log, DatiEsitoTransazione datiEsitoTransazione, List<MsgDiagnostico> msgsParams) {

		if(isEsitoFaultApplicativo(log, datiEsitoTransazione.getEsito(), datiEsitoTransazione.getProtocollo())) {

			if(PddRuolo.APPLICATIVA.equals(datiEsitoTransazione.getPddRuolo())) {
				if(isVisualizzaFault(log, datiEsitoTransazione.getFaultIntegrazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultIntegrazione(), datiEsitoTransazione.getFormatoFaultIntegrazione());
				}
				else if(isVisualizzaFault(log, datiEsitoTransazione.getFaultCooperazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultCooperazione(), datiEsitoTransazione.getFormatoFaultCooperazione());
				}
			}
			else if(PddRuolo.DELEGATA.equals(datiEsitoTransazione.getPddRuolo())) {
				if(isVisualizzaFault(log, datiEsitoTransazione.getFaultCooperazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultCooperazione(), datiEsitoTransazione.getFormatoFaultCooperazione());
				}
				else if(isVisualizzaFault(log, datiEsitoTransazione.getFaultIntegrazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultIntegrazione(), datiEsitoTransazione.getFormatoFaultIntegrazione());
				}
			}

		}

		// Esito
		EsitiProperties esitiProperties = null;
		EsitoTransazioneName esitoTransactionName = null;
		try {
			esitiProperties = EsitiProperties.getInstance(log, datiEsitoTransazione.getProtocollo());
			esitoTransactionName = esitiProperties.getEsitoTransazioneName(datiEsitoTransazione.getEsito());
		}catch(Exception e){
			log.error("Errore durante il recupero dell'esito della transazione: "+e.getMessage(),e);
			return ""; // non dovrebbe mai succedere
		}

		// lista diagnostici
		List<MsgDiagnostico> msgs = null;
		if(msgsParams!=null) {
			msgs = new ArrayList<MsgDiagnostico>();
			if(!msgsParams.isEmpty()) {
				for (MsgDiagnostico msgDiagnostico : msgsParams) {
					if(msgDiagnostico.getSeverita()<=LogLevels.SEVERITA_ERROR_INTEGRATION) {
						msgs.add(msgDiagnostico);
					}
				}
			}
		}
		

		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder erroreConnessone = new StringBuilder();
			StringBuilder erroreSegnalaGenerazioneRispostaErrore = new StringBuilder();
			if(msgs!=null && !msgs.isEmpty()) {
				for (MsgDiagnostico msgDiagnostico : msgs) {
					String codice = msgDiagnostico.getCodice();

					if(isEsitoKo(log, datiEsitoTransazione.getEsito(), datiEsitoTransazione.getProtocollo())) {
						// salto gli errori 'warning'
						if(MsgDiagnosticiProperties.MSG_DIAGNOSTICI_WARNING.contains(codice)) {
							continue;
						}
					}

					if(EsitoTransazioneName.isErroreRisposta(esitoTransactionName) && MsgDiagnosticiProperties.MSG_DIAGNOSTICI_ERRORE_CONNETTORE.contains(codice)) {
						if(erroreConnessone.length()>0) {
							erroreConnessone.append("\n");
						}
						erroreConnessone.append(msgDiagnostico.getMessaggio());
					}
					else if(MsgDiagnosticiProperties.MSG_DIAGNOSTICI_SEGNALA_GENERATA_RISPOSTA_ERRORE.contains(codice)) {
						if(erroreSegnalaGenerazioneRispostaErrore.length()>0) {
							erroreSegnalaGenerazioneRispostaErrore.append("\n");
						}
						erroreSegnalaGenerazioneRispostaErrore.append(msgDiagnostico.getMessaggio());
					}
					else {
						if(sb.length()>0) {
							sb.append("\n");
						}
						sb.append(msgDiagnostico.getMessaggio());

						break; // serializzo solo il primo diagnostico
					}
				}
			}
			if(sb.length()>0) {
				return sb.toString();
			}
			if(erroreConnessone.length()>0) {
				return erroreConnessone.toString();
			}
			if(erroreSegnalaGenerazioneRispostaErrore.length()>0) {
				return erroreSegnalaGenerazioneRispostaErrore.toString();
			}

		}catch(Exception e){
			log.error("Errore durante il recupero dell'errore: "+e.getMessage(),e);
		}

		if(!isEsitoFaultApplicativo(log, datiEsitoTransazione.getEsito(), datiEsitoTransazione.getProtocollo())) {

			if(PddRuolo.APPLICATIVA.equals(datiEsitoTransazione.getPddRuolo())) {
				if(isVisualizzaFault(log, datiEsitoTransazione.getFaultIntegrazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultIntegrazione(), datiEsitoTransazione.getFormatoFaultIntegrazione());
				}
				else if(isVisualizzaFault(log, datiEsitoTransazione.getFaultCooperazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultCooperazione(), datiEsitoTransazione.getFormatoFaultCooperazione());
				}
			}
			else if(PddRuolo.DELEGATA.equals(datiEsitoTransazione.getPddRuolo())) {
				if(isVisualizzaFault(log, datiEsitoTransazione.getFaultCooperazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultCooperazione(), datiEsitoTransazione.getFormatoFaultCooperazione());
				}
				else if(isVisualizzaFault(log, datiEsitoTransazione.getFaultIntegrazione())) {
					return getFaultPretty(log,datiEsitoTransazione.getFaultIntegrazione(), datiEsitoTransazione.getFormatoFaultIntegrazione());
				}
			}

		}

		return null;
	}

}
