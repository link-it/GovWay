/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it). 
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



package org.openspcoop2.pdd.core.trasformazioni;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.openspcoop2.core.config.TrasformazioneRegola;
import org.openspcoop2.core.config.TrasformazioneRegolaRichiesta;
import org.openspcoop2.core.config.TrasformazioneRegolaRisposta;
import org.openspcoop2.core.config.Trasformazioni;
import org.openspcoop2.core.config.constants.VersioneSOAP;
import org.openspcoop2.core.constants.TipoPdD;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2RestJsonMessage;
import org.openspcoop2.message.OpenSPCoop2RestXmlMessage;
import org.openspcoop2.message.OpenSPCoop2SoapMessage;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.core.transazioni.Transaction;
import org.openspcoop2.pdd.logger.MsgDiagnostico;
import org.openspcoop2.protocol.engine.RequestInfo;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.protocol.sdk.constants.CodiceErroreIntegrazione;
import org.openspcoop2.protocol.sdk.constants.ErroreIntegrazione;
import org.openspcoop2.protocol.sdk.constants.ErroriIntegrazione;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.json.JsonPathExpressionEngine;
import org.openspcoop2.utils.transport.http.HttpConstants;
import org.openspcoop2.utils.xml.AbstractXPathExpressionEngine;
import org.slf4j.Logger;
import org.w3c.dom.Element;

/**
 * Gestione delle trasformazioni
 *
 * @author Andrea Poli <apoli@link.it>
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class GestoreTrasformazioni {

	/** Logger utilizzato per debug. */
	private Logger log = null;

	private IDServizio idServizio;
	private IDSoggetto soggettoFruitore;
	private String servizioApplicativoFruitore;
	private Trasformazioni trasformazioni;
	private Transaction transaction;
	private PdDContext pddContext;
	private RequestInfo requestInfo;
	private ErroreIntegrazione errore;
	private MsgDiagnostico msgDiag;
	private TipoPdD tipoPdD;

	private OpenSPCoop2Properties op2Properties;
	
	private Map<String, Object> dynamicMapRequest = null;
	private OpenSPCoop2Message messageRequest = null;
	
	
	/* ********  C O S T R U T T O R E  ******** */
	/**
	 * Costruttore. 
	 *
	 */
	public GestoreTrasformazioni(Logger alog,
			MsgDiagnostico msgDiag, 
			IDServizio idServizio,
			IDSoggetto soggettoFruitore,
			String servizioApplicativoFruitore,
			Trasformazioni trasformazioni,
			Transaction transaction,
			PdDContext pddContext,
			RequestInfo requestInfo,
			TipoPdD tipoPdD){
		if(alog!=null){
			this.log = alog;
		}else{
			this.log = LoggerWrapperFactory.getLogger(GestoreTrasformazioni.class);
		}
		this.msgDiag = msgDiag;
		this.idServizio = idServizio;
		this.soggettoFruitore = soggettoFruitore;
		this.servizioApplicativoFruitore = servizioApplicativoFruitore;
		this.trasformazioni = trasformazioni;
		this.transaction = transaction;
		this.pddContext = pddContext;
		this.requestInfo = requestInfo;
		this.op2Properties = OpenSPCoop2Properties.getInstance();
		this.tipoPdD = tipoPdD;
	}


	
	
	
	public OpenSPCoop2Message trasformazioneRichiesta(OpenSPCoop2Message message, Busta busta) throws GestoreTrasformazioniException{
		if(this.transaction!=null) {
			this.transaction.getTempiElaborazione().startTrasformazioneRichiesta();
		}
		try {
			return this._trasformazioneRichiesta(message, busta);
		}
		finally {
			if(this.transaction!=null) {
				this.transaction.getTempiElaborazione().endTrasformazioneRichiesta();
			}
		}
	}
	public OpenSPCoop2Message trasformazioneRisposta(OpenSPCoop2Message message, Busta busta) throws GestoreTrasformazioniException{
		if(this.transaction!=null) {
			this.transaction.getTempiElaborazione().startTrasformazioneRisposta();
		}
		try {
			return this._trasformazioneRisposta(message, busta);
		}
		finally {
			if(this.transaction!=null) {
				this.transaction.getTempiElaborazione().endTrasformazioneRisposta();
			}
		}
	}
	
	public ErroreIntegrazione getErrore() {
		return this.errore;
	}
	
	
	
	private TrasformazioneRegola regolaTrasformazione; // viene valorizzata durante la gestione della richiesta
	
	private OpenSPCoop2Message _trasformazioneRichiesta(OpenSPCoop2Message message, Busta busta) throws GestoreTrasformazioniException{

		if(this.trasformazioni==null || this.trasformazioni.sizeRegolaList()<=0) {
			this.msgDiag.logPersonalizzato("trasformazione.processamentoRichiestaDisabilitato");
			this.log.debug("Non esistono regole di trasformazione");
			return message;
		}
				
		
		
		// *** Lettura Contenuto Richiesta ****
		
		Element element = null;
		String elementJson = null;
		boolean contenutoNonNavigabile = false;
		Properties parametriTrasporto = null;
		Properties parametriUrl = null;
		String urlInvocazione = null;
		
		try{
			if(ServiceBinding.SOAP.equals(message.getServiceBinding())){
				OpenSPCoop2SoapMessage soapMessage = message.castAsSoap();
				element = soapMessage.getSOAPPart().getEnvelope();
			}
			else{
				if(MessageType.XML.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestXmlMessage xml = message.castAsRestXml();
					element = xml.getContent();	
				}
				else if(MessageType.JSON.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestJsonMessage json = message.castAsRestJson();
					elementJson = json.getContent();
				}
				else {
					contenutoNonNavigabile = true;
				}
			}
			

			if(message.getTransportRequestContext()!=null) {
				if(message.getTransportRequestContext().getParametersTrasporto()!=null &&
					!message.getTransportRequestContext().getParametersTrasporto().isEmpty()) {
					parametriTrasporto = message.getTransportRequestContext().getParametersTrasporto();
				}
				if(message.getTransportRequestContext().getParametersFormBased()!=null &&
						!message.getTransportRequestContext().getParametersFormBased().isEmpty()) {
					parametriUrl = message.getTransportRequestContext().getParametersFormBased();
				}
				urlInvocazione = message.getTransportRequestContext().getUrlInvocazione_formBased();
			}
			else {
				throw new Exception("Transport Request Context non disponibile");
			}
			
		}catch(Exception e){
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(e, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Lettura contenuto della richiesta non riuscita: "+e.getMessage();
			this.log.error(msgErrore, e);
			throw new GestoreTrasformazioniException(msgErrore,e);
		}
		
		
		
		
		// *** Identificazione regola ****
		
		try {
			
			this.log.debug("Identificazione regola di trasformazione tra le "+this.trasformazioni.sizeRegolaList()+" disponibili ...");
			
			for (int i = 0; i < this.trasformazioni.sizeRegolaList(); i++) {
				
				String suffix = "[Regola-"+i+"] ";
				
				this.log.debug(suffix+"Verifica applicabilità della regola ...");
				
				TrasformazioneRegola check = this.trasformazioni.getRegola(i);
				
				
				// prendo la prima che ha un match nell'ordine
				
				if(check.getApplicabilita()!=null) {
				
					// controllo azione
					this.log.debug(suffix+" check applicabilità tra le '"+check.getApplicabilita().sizeAzioneList()+"' azioni ");
					if(check.getApplicabilita().sizeAzioneList()>0) {
						boolean found = false;
						for (String checkAzione : check.getApplicabilita().getAzioneList()) {
							if(checkAzione.equals(this.idServizio.getAzione())) {
								found = true;
								break;
							}
						}
						if(!found) {
							continue;
						}
					}
					
					// controllo contentType
					this.log.debug(suffix+" check applicabilità tra i "+check.getApplicabilita().sizeContentTypeList()+" content types ");
					if(check.getApplicabilita().sizeContentTypeList()>0) {
						if(GestoreTrasformazioniUtilities.isMatchContentType(message.getContentType(), check.getApplicabilita().getContentTypeList())==false) {
							continue;
						}
					}
		
					// Controllo Pattern
					if(check.getApplicabilita().getPattern()!=null && !"".equals(check.getApplicabilita().getPattern())) {
						
						this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+") ...");
						
						if(contenutoNonNavigabile) {
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), messaggio non conforme al match: "+message.getMessageType());
							continue; // no match
						}
						
						if(element==null && elementJson==null){
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), messaggio ("+message.getMessageType()+") senza contenuto");
							continue; // no match
						}
						String valore = null;
						try {
							if(element!=null) {
								AbstractXPathExpressionEngine xPathEngine = new org.openspcoop2.message.xml.XPathExpressionEngine();
								valore = AbstractXPathExpressionEngine.extractAndConvertResultAsString(element, xPathEngine, check.getApplicabilita().getPattern(), this.log);
							}
							else {
								valore = JsonPathExpressionEngine.extractAndConvertResultAsString(elementJson, check.getApplicabilita().getPattern(), this.log);
							}
						}catch(Exception e){
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+") fallita: "+e.getMessage(),e);
						}
						if(valore==null) {
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), match fallito ("+message.getMessageType()+")");
							continue;
						}
					}
					
					// controllo applicativi e soggetti
					this.log.debug(suffix+" check applicabilità tra i "+check.getApplicabilita().sizeServizioApplicativoList()+" servizi applicativi e i "+check.getApplicabilita().sizeSoggettoList()+" soggetti");
					if(check.getApplicabilita().sizeServizioApplicativoList()>0 || check.getApplicabilita().sizeSoggettoList()>0) {
						boolean applicativi = true;
						boolean soggetti = TipoPdD.APPLICATIVA.equals(this.tipoPdD);
						if(check.getApplicabilita().sizeServizioApplicativoList()>0) {
							applicativi = GestoreTrasformazioniUtilities.isMatchServizioApplicativo(this.soggettoFruitore, this.servizioApplicativoFruitore, check.getApplicabilita().getServizioApplicativoList());
						}
						if(check.getApplicabilita().sizeSoggettoList()>0) {
							soggetti = GestoreTrasformazioniUtilities.isMatchSoggetto(this.soggettoFruitore, check.getApplicabilita().getSoggettoList());
						}
						if(!applicativi && !soggetti) {
							continue;
						}
					}
				
				}
				
				this.log.debug(suffix+" check applicabilità, regola applicabile alla richiesta in corso");
				this.regolaTrasformazione = check;
				break; // trovata!
				
			}

		} catch(Throwable er) {
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(er, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Identificazione regola di trasformazione non riuscita: "+er.getMessage();
			this.log.error(msgErrore, er);
			throw new GestoreTrasformazioniException(msgErrore,er);
		}
			
		
		if(this.regolaTrasformazione==null) {
			this.msgDiag.logPersonalizzato("trasformazione.processamentoRichiestaNessunMatch");
			this.log.debug("Nessuna regola di trasformazione trovata");
			return message;
		}
		
		this.messageRequest = message;
		
		
		
		
		// *** EmissioneDiagnostico ****
		
		StringBuffer bf = new StringBuffer();
		TrasformazioneRegolaRichiesta richiesta = this.regolaTrasformazione.getRichiesta();
		if(richiesta.getConversione()) {
			if(richiesta.getTrasformazioneSoap()!=null) {
				bf.append("soap ");	
			}
			else if(richiesta.getTrasformazioneRest()!=null) {
				bf.append("rest ");	
			}
			bf.append(richiesta.getConversioneTipo());	
		}
		if(richiesta.getHeaderList()!=null && !richiesta.getHeaderList().isEmpty()) {
			if(bf.length()>0) {
				bf.append(" ");	
			}
			bf.append("headers");	
		}
		if(richiesta.getParametroUrlList()!=null && !richiesta.getParametroUrlList().isEmpty()) {
			if(bf.length()>0) {
				bf.append(" ");	
			}
			bf.append("queryParameters");	
		}
		if(bf.length()<=0) {
			bf.append("nessuna");	
		}
		this.msgDiag.addKeyword(CostantiPdD.KEY_TIPO_TRASFORMAZIONE_RICHIESTA, bf.toString());
		this.msgDiag.logPersonalizzato("trasformazione.processamentoRichiestaInCorso");
		
		
		
		
		
		
		// *** Costruzione Dynamic Map ****
		
		this.log.debug("Costruzione dynamic map ...");
		Map<String, Object> dynamicMap = new Hashtable<String, Object>();
		GestoreTrasformazioniUtilities.fillDynamicMapRequest(this.log, dynamicMap, this.pddContext, urlInvocazione,
				element, elementJson, 
				busta, parametriTrasporto, parametriUrl);
		this.dynamicMapRequest = dynamicMap;
		this.log.debug("Costruzione dynamic map completata");
		
		
		
		
		// *** Trasformazione Richiesta ****
		
		try {
						
			boolean trasformazioneContenuto = richiesta.getConversione();
			RisultatoTrasformazioneContenuto risultato = null;
			
			if(!trasformazioneContenuto) {
				this.log.debug("Trasformazione contenuto della richiesta disabilitato");
			}
			else {
				risultato = 
						GestoreTrasformazioniUtilities.trasformazioneContenuto(this.log, 
								richiesta.getConversioneTipo(), richiesta.getConversioneTemplate(), "richiesta", dynamicMap, message, element, this.pddContext);
			}
			
			// conversione header
			Properties trasporto = parametriTrasporto;
			Properties forceAddTrasporto = new Properties();
			GestoreTrasformazioniUtilities.trasformazione(this.log, richiesta.getHeaderList(), trasporto, forceAddTrasporto, "Header", dynamicMap, this.pddContext);
			if(richiesta.getContentType()!=null) {
				trasporto.remove(HttpConstants.CONTENT_TYPE);
				trasporto.remove(HttpConstants.CONTENT_TYPE.toLowerCase());
				trasporto.remove(HttpConstants.CONTENT_TYPE.toUpperCase());
				trasporto.put(HttpConstants.CONTENT_TYPE, richiesta.getContentType());
			}
			
			// conversione url
			Properties url = parametriUrl;
			Properties forceAddUrl = new Properties();
			GestoreTrasformazioniUtilities.trasformazione(this.log, richiesta.getParametroUrlList(), url, forceAddUrl, "QueryParameter", dynamicMap, this.pddContext);
			
			if(!trasformazioneContenuto) {
				GestoreTrasformazioniUtilities.addTransportInfo(forceAddTrasporto, forceAddUrl, null, message);
				return message;
			}
			
			boolean trasformazioneRest = false;
			String trasformazioneRest_method = null;
			String trasformazioneRest_path = null;
			if(richiesta.getTrasformazioneRest()!=null) {
				trasformazioneRest = true;
				trasformazioneRest_method = richiesta.getTrasformazioneRest().getMetodo();
				trasformazioneRest_path = richiesta.getTrasformazioneRest().getPath();
			}
			
			boolean trasformazioneSoap = false;
			VersioneSOAP trasformazioneSoap_versione = null;
			String trasformazioneSoap_soapAction = null;
			boolean trasformazioneSoap_envelope = false;
			boolean trasformazioneSoap_envelopeAsAttachment = false;
			String trasformazioneSoap_tipoConversione = null;
			byte[] trasformazioneSoap_templateConversione = null;
			if(richiesta.getTrasformazioneSoap()!=null) {
				trasformazioneSoap = true;
				trasformazioneSoap_versione = richiesta.getTrasformazioneSoap().getVersione();
				trasformazioneSoap_soapAction = richiesta.getTrasformazioneSoap().getSoapAction();
				trasformazioneSoap_envelope = richiesta.getTrasformazioneSoap().isEnvelope();
				trasformazioneSoap_envelopeAsAttachment = richiesta.getTrasformazioneSoap().isEnvelopeAsAttachment();
				trasformazioneSoap_tipoConversione = richiesta.getTrasformazioneSoap().getEnvelopeBodyConversioneTipo();
				trasformazioneSoap_templateConversione = richiesta.getTrasformazioneSoap().getEnvelopeBodyConversioneTemplate();
			}
			
			OpenSPCoop2Message msg = GestoreTrasformazioniUtilities.trasformaMessaggio(this.log, message, element, 
					this.requestInfo, dynamicMap, this.pddContext, this.op2Properties, 
					trasporto, forceAddTrasporto,
					url, forceAddUrl,
					-1, 
					richiesta.getContentType(), null, 
					risultato, 
					trasformazioneRest, 
					trasformazioneRest_method, trasformazioneRest_path,
					trasformazioneSoap, 
					trasformazioneSoap_versione, trasformazioneSoap_soapAction, 
					trasformazioneSoap_envelope, trasformazioneSoap_envelopeAsAttachment, 
					trasformazioneSoap_tipoConversione, trasformazioneSoap_templateConversione);

			this.msgDiag.logPersonalizzato("trasformazione.processamentoRichiestaEffettuato");
			
			return msg;
						
		} catch(Throwable er) {
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(er, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Trasformazione richiesta fallita: "+er.getMessage();
			this.log.error(msgErrore, er);
			throw new GestoreTrasformazioniException(msgErrore,er);
		}
		
		

		
	}
	
	
	private OpenSPCoop2Message _trasformazioneRisposta(OpenSPCoop2Message message, Busta busta) throws GestoreTrasformazioniException{

		if(this.regolaTrasformazione==null || this.regolaTrasformazione.sizeRispostaList()<=0) {
			this.msgDiag.logPersonalizzato("trasformazione.processamentoRispostaDisabilitato");
			this.log.debug("Non esistono regole di trasformazione della risposta");
			return message;
		}
	
		
		// *** Lettura Contenuto Risposta ****
		
		Element element = null;
		String elementJson = null;
		boolean contenutoNonNavigabile = false;
		Properties parametriTrasporto = null;
		int httpStatus = -1;
		try{
			if(ServiceBinding.SOAP.equals(message.getServiceBinding())){
				OpenSPCoop2SoapMessage soapMessage = message.castAsSoap();
				element = soapMessage.getSOAPPart().getEnvelope();
			}
			else{
				if(MessageType.XML.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestXmlMessage xml = message.castAsRestXml();
					element = xml.getContent();	
				}
				else if(MessageType.JSON.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestJsonMessage json = message.castAsRestJson();
					elementJson = json.getContent();
				}
				else {
					contenutoNonNavigabile = true;
				}
			}
			
			if(message.getTransportResponseContext()!=null) {
				if(message.getTransportResponseContext().getParametersTrasporto()!=null &&
					!message.getTransportResponseContext().getParametersTrasporto().isEmpty()) {
					parametriTrasporto = message.getTransportResponseContext().getParametersTrasporto();
				}
				try {
					httpStatus = Integer.parseInt(message.getTransportResponseContext().getCodiceTrasporto());
				}catch(Exception e) {}
			}
			else {
				throw new Exception("Transport Response Context non disponibile");
			}
			
		}catch(Exception e){
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(e, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Lettura contenuto della risposta non riuscita: "+e.getMessage();
			this.log.error(msgErrore, e);
			throw new GestoreTrasformazioniException(msgErrore,e);
		}
				
		
		
		// *** Identificazione regola ****
		
		TrasformazioneRegolaRisposta trasformazioneRisposta = null;
		try {
			
			this.log.debug("Identificazione regola di trasformazione della risposta tra le "+this.regolaTrasformazione.sizeRispostaList()+" disponibili ...");
			
			for (int i = 0; i < this.regolaTrasformazione.sizeRispostaList(); i++) {
				
				String suffix = "[Regola-"+i+"] ";
				
				this.log.debug(suffix+"Verifica applicabilità della regola ...");
				
				TrasformazioneRegolaRisposta check = this.regolaTrasformazione.getRisposta(i);
				
				// prendo la prima che ha un match nell'ordine
				
				if(check.getApplicabilita()!=null) {
				
					// controllo status code
					if(check.getApplicabilita().getReturnCodeMax()!=null || check.getApplicabilita().getReturnCodeMin()!=null) {
						String min = "*";
						if(check.getApplicabilita().getReturnCodeMin()!=null) {
							min = check.getApplicabilita().getReturnCodeMin().intValue()+"";
						} 
						String max = "*";
						if(check.getApplicabilita().getReturnCodeMax()!=null) {
							max = check.getApplicabilita().getReturnCodeMax().intValue()+"";
						} 
						this.log.debug(suffix+" check applicabilità return code ["+min+"-"+max+"]");
						if(check.getApplicabilita().getReturnCodeMin()!=null) {
							if(httpStatus< check.getApplicabilita().getReturnCodeMin().intValue()) {
								continue;
							}
						}
						if(check.getApplicabilita().getReturnCodeMax()!=null) {
							if(httpStatus> check.getApplicabilita().getReturnCodeMax().intValue()) {
								continue;
							}
						}
					}
					
					// controllo contentType
					if(GestoreTrasformazioniUtilities.isMatchContentType(message.getContentType(), check.getApplicabilita().getContentTypeList())==false) {
						continue;
					}
		
					// Controllo Pattern
					if(check.getApplicabilita().getPattern()!=null && !"".equals(check.getApplicabilita().getPattern())) {
						
						this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+") ...");
						
						if(contenutoNonNavigabile) {
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), messaggio non conforme al match: "+message.getMessageType());
							continue; // no match
						}
						
						if(element==null && elementJson==null){
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), messaggio ("+message.getMessageType()+") senza contenuto");
							continue; // no match
						}
						String valore = null;
						try {
							if(element!=null) {
								AbstractXPathExpressionEngine xPathEngine = new org.openspcoop2.message.xml.XPathExpressionEngine();
								valore = AbstractXPathExpressionEngine.extractAndConvertResultAsString(element, xPathEngine, check.getApplicabilita().getPattern(), this.log);
							}
							else {
								valore = JsonPathExpressionEngine.extractAndConvertResultAsString(elementJson, check.getApplicabilita().getPattern(), this.log);
							}
						}catch(Exception e){
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+") fallita: "+e.getMessage(),e);
						}
						if(valore==null) {
							this.log.debug(suffix+" check applicabilità content-based pattern("+check.getApplicabilita().getPattern()+"), match fallito ("+message.getMessageType()+")");
							continue;
						}
					}
					
				}
				
				this.log.debug(suffix+" check applicabilità, regola applicabile alla risposta in corso");
				trasformazioneRisposta = check;
				break; // trovata!
				
			}
			
		} catch(Throwable er) {
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(er, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Identificazione regola di trasformazione della risposta non riuscita: "+er.getMessage();
			this.log.error(msgErrore, er);
			throw new GestoreTrasformazioniException(msgErrore,er);
		}
			
		
		if(trasformazioneRisposta==null) {
			this.msgDiag.logPersonalizzato("trasformazione.processamentoRispostaNessunMatch");
			this.log.debug("Nessuna regola di trasformazione della risposta trovata");
			return message;
		}
	
		
		
		
		
		// *** EmissioneDiagnostico ****
		
		StringBuffer bf = new StringBuffer();
		if(trasformazioneRisposta.getConversione()) {
			// !inverto!
			if(this.regolaTrasformazione.getRichiesta().getTrasformazioneRest()!=null) {
				bf.append("soap ");	
			}
			else if(this.regolaTrasformazione.getRichiesta().getTrasformazioneSoap()!=null) {
				bf.append("rest ");	
			}
			bf.append(trasformazioneRisposta.getConversioneTipo());	
		}
		if(trasformazioneRisposta.getHeaderList()!=null && !trasformazioneRisposta.getHeaderList().isEmpty()) {
			if(bf.length()>0) {
				bf.append(" ");	
			}
			bf.append("headers");	
		}
		if(bf.length()<=0) {
			bf.append("nessuna");	
		}
		this.msgDiag.addKeyword(CostantiPdD.KEY_TIPO_TRASFORMAZIONE_RISPOSTA, bf.toString());
		this.msgDiag.logPersonalizzato("trasformazione.processamentoRispostaInCorso");
		
		
		
		
		
		// *** Costruzione Dynamic Map ****
		
		this.log.debug("Costruzione dynamic map ...");
		Map<String, Object> dynamicMap = new Hashtable<String, Object>();
		GestoreTrasformazioniUtilities.fillDynamicMapResponse(this.log, dynamicMap, this.dynamicMapRequest, this.pddContext, element, elementJson, busta, parametriTrasporto);
		this.log.debug("Costruzione dynamic map completata");
		
		
		
		
		// *** Trasformazione Risposta ****
		
		try {
			boolean trasformazioneContenuto = trasformazioneRisposta.getConversione();
			RisultatoTrasformazioneContenuto risultato = null;
			
			if(!trasformazioneContenuto) {
				this.log.debug("Trasformazione contenuto della richiesta disabilitato");
			}
			else {
				risultato = 
						GestoreTrasformazioniUtilities.trasformazioneContenuto(this.log, 
								trasformazioneRisposta.getConversioneTipo(), trasformazioneRisposta.getConversioneTemplate(), "risposta", dynamicMap, message, element, this.pddContext);
			}
			
			// conversione header
			Properties trasporto = parametriTrasporto;
			Properties forceAddTrasporto = new Properties();
			GestoreTrasformazioniUtilities.trasformazione(this.log, trasformazioneRisposta.getHeaderList(), trasporto, forceAddTrasporto, "Header", dynamicMap, this.pddContext);
			if(trasformazioneRisposta.getContentType()!=null) {
				trasporto.remove(HttpConstants.CONTENT_TYPE);
				trasporto.remove(HttpConstants.CONTENT_TYPE.toLowerCase());
				trasporto.remove(HttpConstants.CONTENT_TYPE.toUpperCase());
				trasporto.put(HttpConstants.CONTENT_TYPE, trasformazioneRisposta.getContentType());
			}
			
			if(!trasformazioneContenuto) {
				Integer forceResponseStatus = null;
				if(trasformazioneRisposta.getReturnCode()!=null) {
					forceResponseStatus = trasformazioneRisposta.getReturnCode();
				}
				else {
					forceResponseStatus = httpStatus;
				}
				GestoreTrasformazioniUtilities.addTransportInfo(forceAddTrasporto, null, forceResponseStatus, message);
				return message;
			}
			
			boolean trasformazioneRest = false;
			if(this.regolaTrasformazione.getRichiesta().getTrasformazioneSoap()!=null) {
				trasformazioneRest = true; // devo tornare rest
			}
			
			boolean trasformazioneSoap = false;
			VersioneSOAP trasformazioneSoap_versione = null;
			String trasformazioneSoap_soapAction = null;
			boolean trasformazioneSoap_envelope = false;
			boolean trasformazioneSoap_envelopeAsAttachment = false;
			String trasformazioneSoap_tipoConversione = null;
			byte[] trasformazioneSoap_templateConversione = null;
			if(this.regolaTrasformazione.getRichiesta().getTrasformazioneRest()!=null) {
				// devo tornare soap
				trasformazioneSoap = true;
				if(this.messageRequest!=null) {
					if(MessageType.SOAP_11.equals(this.messageRequest.getMessageType())) {
						trasformazioneSoap_versione = VersioneSOAP._1_1;
					}
					else if(MessageType.SOAP_12.equals(this.messageRequest.getMessageType())) {
						trasformazioneSoap_versione = VersioneSOAP._1_2;
					}
					else {
						throw new Exception("Atteso messaggio di richiesta di tipo SOAP, in presenza di una trasformazione REST attiva");
					}
					trasformazioneSoap_soapAction = this.messageRequest.castAsSoap().getSoapAction();
				}
				else {
					// non dovrebbe mai succedere
					trasformazioneSoap_versione = VersioneSOAP._1_1;
				}
				if(trasformazioneRisposta.getTrasformazioneSoap()!=null) {
					trasformazioneSoap_envelope = trasformazioneRisposta.getTrasformazioneSoap().isEnvelope();
					trasformazioneSoap_envelopeAsAttachment = trasformazioneRisposta.getTrasformazioneSoap().isEnvelopeAsAttachment();
					trasformazioneSoap_tipoConversione = trasformazioneRisposta.getTrasformazioneSoap().getEnvelopeBodyConversioneTipo();
					trasformazioneSoap_templateConversione = trasformazioneRisposta.getTrasformazioneSoap().getEnvelopeBodyConversioneTemplate();
				}
			}
			
			OpenSPCoop2Message msg = GestoreTrasformazioniUtilities.trasformaMessaggio(this.log, message, element, 
					this.requestInfo, dynamicMap, this.pddContext, this.op2Properties, 
					trasporto, forceAddTrasporto, 
					null, null,
					httpStatus, 
					trasformazioneRisposta.getContentType(), trasformazioneRisposta.getReturnCode(), 
					risultato, 
					trasformazioneRest, 
					null, null,
					trasformazioneSoap, 
					trasformazioneSoap_versione, trasformazioneSoap_soapAction, 
					trasformazioneSoap_envelope, trasformazioneSoap_envelopeAsAttachment, 
					trasformazioneSoap_tipoConversione, trasformazioneSoap_templateConversione);

			this.msgDiag.logPersonalizzato("trasformazione.processamentoRispostaEffettuato");
			
			return msg;
						
		} catch(Throwable er) {
			this.errore = ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(er, CodiceErroreIntegrazione.CODICE_562_TRASFORMAZIONE);
			String msgErrore = "Trasformazione risposta fallita: "+er.getMessage();
			this.log.error(msgErrore, er);
			throw new GestoreTrasformazioniException(msgErrore,er);
		}
	}
	
}

