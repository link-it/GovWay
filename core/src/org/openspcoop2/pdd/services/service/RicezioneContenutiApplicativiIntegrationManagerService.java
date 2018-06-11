/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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

package org.openspcoop2.pdd.services.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPFault;

import org.openspcoop2.core.config.DumpConfigurazione;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.constants.TipoPdD;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.message.OpenSPCoop2MessageParseResult;
import org.openspcoop2.message.OpenSPCoop2SoapMessage;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.message.exception.ParseException;
import org.openspcoop2.message.exception.ParseExceptionUtils;
import org.openspcoop2.message.soap.SoapUtils;
import org.openspcoop2.message.utils.MessageUtilities;
import org.openspcoop2.pdd.config.ConfigurazionePdDManager;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.pdd.core.GestoreMessaggi;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.core.credenziali.Credenziali;
import org.openspcoop2.pdd.core.connettori.IConnettore;
import org.openspcoop2.pdd.core.connettori.RepositoryConnettori;
import org.openspcoop2.pdd.core.handlers.GestoreHandlers;
import org.openspcoop2.pdd.core.handlers.PostOutResponseContext;
import org.openspcoop2.pdd.core.handlers.PreInRequestContext;
import org.openspcoop2.pdd.core.integrazione.HeaderIntegrazione;
import org.openspcoop2.pdd.core.state.OpenSPCoopStateful;
import org.openspcoop2.pdd.core.transazioni.TransactionContext;
import org.openspcoop2.pdd.logger.Dump;
import org.openspcoop2.pdd.logger.MsgDiagnosticiProperties;
import org.openspcoop2.pdd.logger.MsgDiagnostico;
import org.openspcoop2.pdd.services.DumpRaw;
import org.openspcoop2.pdd.services.ServicesUtils;
import org.openspcoop2.pdd.services.connector.ConnectorUtils;
import org.openspcoop2.pdd.services.connector.messages.HttpServletConnectorInMessage;
import org.openspcoop2.pdd.services.connector.messages.HttpServletConnectorOutMessage;
import org.openspcoop2.pdd.services.core.RicezioneContenutiApplicativi;
import org.openspcoop2.pdd.services.core.RicezioneContenutiApplicativiContext;
import org.openspcoop2.pdd.services.error.RicezioneContenutiApplicativiInternalErrorGenerator;
import org.openspcoop2.pdd.services.skeleton.IntegrationManager;
import org.openspcoop2.pdd.services.skeleton.IntegrationManagerException;
import org.openspcoop2.pdd.services.skeleton.IntegrationManagerMessage;
import org.openspcoop2.pdd.services.skeleton.IntegrationManagerUtility;
import org.openspcoop2.pdd.services.skeleton.ProtocolHeaderInfo;
import org.openspcoop2.protocol.basic.registry.ServiceIdentificationReader;
import org.openspcoop2.protocol.engine.RequestInfo;
import org.openspcoop2.protocol.engine.URLProtocolContext;
import org.openspcoop2.protocol.engine.constants.Costanti;
import org.openspcoop2.protocol.engine.constants.IDService;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.builder.EsitoTransazione;
import org.openspcoop2.protocol.sdk.builder.InformazioniErroriInfrastrutturali;
import org.openspcoop2.protocol.sdk.builder.ProprietaErroreApplicativo;
import org.openspcoop2.protocol.sdk.constants.CodiceErroreIntegrazione;
import org.openspcoop2.protocol.sdk.constants.ErroriIntegrazione;
import org.openspcoop2.protocol.sdk.constants.EsitoTransazioneName;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.io.notifier.NotifierInputStreamParams;
import org.slf4j.Logger;

/**
 * RicezioneContenutiApplicativiIntegrationManagerService
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class RicezioneContenutiApplicativiIntegrationManagerService {

	public static OpenSPCoop2MessageFactory factory = OpenSPCoop2MessageFactory.getMessageFactory();
	
	public IntegrationManagerMessage process(String tipoOperazione, String portaDelegata, IntegrationManagerMessage msg,
			String idInvocazionePerRiferimento,
			Logger logCore, javax.servlet.http.HttpServletRequest req, HttpServletResponse res,
			IProtocolFactory<?> protocolFactory, Date dataAccettazioneRichiesta, Date dataIngressoRichiesta) throws IntegrationManagerException {

		String idModulo = RicezioneContenutiApplicativi.ID_MODULO+IntegrationManager.ID_MODULO;
				
		// Proprieta' OpenSPCoop
		OpenSPCoop2Properties openSPCoopProperties = OpenSPCoop2Properties.getInstance();
		if (openSPCoopProperties == null) {
			String msgError = "Inizializzazione di OpenSPCoop non correttamente effettuata: OpenSPCoopProperties";
			logCore.error(msgError);
			try{
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
						get5XX_ErroreProcessamento(msgError,CodiceErroreIntegrazione.CODICE_501_PDD_NON_INIZIALIZZATA));
			}catch(Throwable eError){
				logCore.error("Errore generazione SOAPFault",eError);
				throw new RuntimeException(eError); // errore che non dovrebbe accadare
			}
		}

		RequestInfo requestInfo = null;
		try{
			// Request Info
			URLProtocolContext urlProtocolContext = new URLProtocolContext(req,logCore,true,true,openSPCoopProperties.getCustomContexts());
			urlProtocolContext.setInterfaceName(portaDelegata);
			requestInfo = ConnectorUtils.getRequestInfo(protocolFactory, urlProtocolContext);
		}catch(Exception e){
			String msgError = "Lettura RequestInfo non riuscita: "+Utilities.readFirstErrorValidMessageFromException(e);
			logCore.error(msgError);
			throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(msgError));
		}
		
		// Configurazione Reader
		ConfigurazionePdDManager configPdDManager = null;
		try{
			configPdDManager = ConfigurazionePdDManager.getInstance();
			if(configPdDManager==null || configPdDManager.isInitializedConfigurazionePdDReader()==false){
				throw new Exception("ConfigurazionePdDManager not initialized");
			}
		}catch(Throwable e){
			String msgError = "Inizializzazione di OpenSPCoop non correttamente effettuata: ConfigurazionePdDManager";
			logCore.error(msgError,e);
			try{
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
						get5XX_ErroreProcessamento(msgError,CodiceErroreIntegrazione.CODICE_501_PDD_NON_INIZIALIZZATA));
			}catch(Throwable eError){
				logCore.error("Errore generazione SOAPFault",eError);
				throw new RuntimeException(eError); // errore che non dovrebbe accadare
			}
		}
			
		// DumpRaw
		DumpRaw dumpRaw = null;
		try{
			if(configPdDManager.dumpBinarioPD()){
				dumpRaw = new DumpRaw(logCore,true);
			}
		}catch(Throwable e){
			String msgError = "Inizializzazione di OpenSPCoop non correttamente effettuata: DumpRaw";
			logCore.error(msgError,e);
			try{
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
						get5XX_ErroreProcessamento(msgError,CodiceErroreIntegrazione.CODICE_501_PDD_NON_INIZIALIZZATA));
			}catch(Throwable eError){
				logCore.error("Errore generazione SOAPFault",eError);
				throw new RuntimeException(eError); // errore che non dovrebbe accadare
			}
		}
		
		
		
		// Identifico Servizio per comprendere correttamente il messageType
		ServiceIdentificationReader serviceIdentificationReader = null;
		try{
			serviceIdentificationReader = ServicesUtils.getServiceIdentificationReader(logCore, requestInfo);
		}catch(Exception e){
			String msgError = "Inizializzazione RegistryReader fallita: "+Utilities.readFirstErrorValidMessageFromException(e);
			logCore.error(msgError,e);
			try{
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
						get5XX_ErroreProcessamento(msgError));
			}catch(Throwable eError){
				logCore.error("Errore generazione SOAPFault",eError);
				throw new RuntimeException(eError); // errore che non dovrebbe accadare
			}
		}
		
		
		// Logger dei messaggi diagnostici
		MsgDiagnostico msgDiag = new MsgDiagnostico(IntegrationManager.ID_MODULO);
		msgDiag.setPrefixMsgPersonalizzati(MsgDiagnosticiProperties.MSG_DIAG_INTEGRATION_MANAGER);
		msgDiag.addKeyword(CostantiPdD.KEY_TIPO_OPERAZIONE_IM, tipoOperazione);
		
		
		// GeneratoreErrore
		RicezioneContenutiApplicativiInternalErrorGenerator generatoreErrore = null;
		try{
			generatoreErrore = 
						new RicezioneContenutiApplicativiInternalErrorGenerator(logCore, idModulo, requestInfo);
		}catch(Exception e){
			String msgError = "Inizializzazione Generatore Errore fallita: "+Utilities.readFirstErrorValidMessageFromException(e);
			msgDiag.logErroreGenerico(e,"Inizializzazione Generatore Errore");
			throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
					get5XX_ErroreProcessamento(msgError));
		}
		
		// Aggiorno RequestInfo
		try{
			if(RicezioneContenutiApplicativiServiceUtils.updatePortaDelegataRequestInfo(requestInfo, logCore, null,
					generatoreErrore, serviceIdentificationReader,msgDiag)==false){
				try{
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							getErroreIntegrazione());
				}catch(Throwable eError){
					logCore.error("Errore generazione SOAPFault",eError);
					throw new RuntimeException(eError); // errore che non dovrebbe accadare
				}
			}
		}catch(Exception e){
			String msgError = "Aggiornamento RequestInfo fallito: "+Utilities.readFirstErrorValidMessageFromException(e);
			logCore.error(msgError,e);
			try{
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
						get5XX_ErroreProcessamento(msgError));
			}catch(Throwable eError){
				logCore.error("Errore generazione SOAPFault",eError);
				throw new RuntimeException(eError); // errore che non dovrebbe accadare
			}
		}
		
		


		
		
		
		
		/* ------------  Gestione ------------- */
		
		OpenSPCoopStateful stato = null;
		
		RicezioneContenutiApplicativiContext context = null;
		try{
			// viene generato l'UUID
			context = new RicezioneContenutiApplicativiContext(IDService.PORTA_DELEGATA_INTEGRATION_MANAGER, dataAccettazioneRichiesta,requestInfo);
		}catch(Exception e){
			msgDiag.logErroreGenerico(e,"invocaPortaDelegata_engine("+tipoOperazione+").newRicezioneContenutiApplicativiContext()");
			throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.getErroreIntegrazione());
		}
		context.setDataIngressoRichiesta(dataIngressoRichiesta);
		context.getPddContext().addObject(CostantiPdD.KEY_TIPO_OPERAZIONE_IM, tipoOperazione);
		context.setTipoPorta(TipoPdD.DELEGATA);
		msgDiag.setPddContext(context.getPddContext(), protocolFactory);
		
		try{
			if(openSPCoopProperties.isTransazioniEnabled()) {
				TransactionContext.createTransaction((String)context.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE));
			}
		}catch(Exception e){
			logCore.error("Errore durante la creazione della transazione",e);
		}
		
		try{
			msgDiag.logPersonalizzato("ricezioneRichiesta.firstLog");
		}catch(Exception e){
			logCore.error("Errore generazione diagnostico di ingresso",e);
		}
		
		if(dumpRaw!=null){
			dumpRaw.serializeContext(context, protocolFactory.getProtocol());
		}
		
		// PddContext
		String idTransazione = PdDContext.getValue(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE,context.getPddContext()); 
		context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE, idTransazione);
		context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.TIPO_OPERAZIONE_IM, tipoOperazione.toString());
		context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.ID_MESSAGGIO, idInvocazionePerRiferimento);
		context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.PORTA_DELEGATA, portaDelegata);
		context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.PROTOCOL_NAME, protocolFactory.getProtocol());
		
		EsitoTransazione esito = null;
		String errore = null;
		OpenSPCoop2Message msgRequest = null;
		OpenSPCoop2Message msgResponse = null;
		IntegrationManagerMessage msgReturn = null;
		String descrizioneSoapFault = "";
		URLProtocolContext urlProtocolContext = null;
		try{

			
			/* ------------  PreInHandler ------------- */
			
			// build context
			PreInRequestContext preInRequestContext = new PreInRequestContext(context.getPddContext());
			preInRequestContext.setTipoPorta(TipoPdD.DELEGATA);
			preInRequestContext.setIdModulo(idModulo);
			preInRequestContext.setProtocolFactory(protocolFactory);
			Hashtable<String, Object> transportContext = new Hashtable<String, Object>();
			HttpServletConnectorInMessage httpIn = null;
			try{
				httpIn = new HttpServletConnectorInMessage(requestInfo, req, IntegrationManager.ID_SERVICE, IntegrationManager.ID_MODULO);
				transportContext.put(PreInRequestContext.SERVLET_REQUEST, httpIn);
			}catch(Exception e){
				ConnectorUtils.getErrorLog().error("HttpServletConnectorInMessage init error: "+e.getMessage(),e);
				//throw new ServletException(e.getMessage(),e);
			}
			HttpServletConnectorOutMessage httpOut = null;
			try{
				httpOut = new HttpServletConnectorOutMessage(protocolFactory, res, IntegrationManager.ID_SERVICE, IntegrationManager.ID_MODULO);
				transportContext.put(PreInRequestContext.SERVLET_RESPONSE, httpOut);
			}catch(Exception e){
				ConnectorUtils.getErrorLog().error("HttpServletConnectorOutMessage init error: "+e.getMessage(),e);
				//throw new ServletException(e.getMessage(),e);
			}
			preInRequestContext.setTransportContext(transportContext);	
			preInRequestContext.setLogCore(logCore);
			
			// invocazione handler
			GestoreHandlers.preInRequest(preInRequestContext, msgDiag, logCore);
			
			// aggiungo eventuali info inserite nel preInHandler
			context.getPddContext().addAll(preInRequestContext.getPddContext(), false);
			
			// Lettura risposta parametri NotifierInputStream
			NotifierInputStreamParams notifierInputStreamParams = preInRequestContext.getNotifierInputStreamParams();
			context.setNotifierInputStreamParams(notifierInputStreamParams);
			
			msgDiag.logPersonalizzato("ricezioneRichiesta.elaborazioneDati.tipologiaMessaggio");
			
			
			
			

			/* ------------  Validazione INPUT in base all'operaizone invocata ------------- */
			if("invocaPortaDelegata".equals(tipoOperazione)){
				// check presenza body applicativo
				if(msg==null || msg.getMessage()==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegata.contenutoApplicativoNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
			}
			else if("invocaPortaDelegataPerRiferimento".equals(tipoOperazione)){
				//	 check presenza riferimento
				if(idInvocazionePerRiferimento==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegataPerRiferimento.riferimentoMessaggioNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
			}
			else if("sendRispostaAsincronaSimmetrica".equals(tipoOperazione)){
				// check presenza body applicativo
				if(msg==null || msg.getMessage()==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegata.contenutoApplicativoNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
				//	check presenza id di correlazione asincrona
				if(msg.getProtocolHeaderInfo()==null || msg.getProtocolHeaderInfo().getRiferimentoMessaggio()==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegata.profiloAsincrono.riferimentoMessaggioNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
			}
			else if("sendRichiestaStatoAsincronaAsimmetrica".equals(tipoOperazione)){
				//	check presenza body applicativo
				if(msg==null || msg.getMessage()==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegata.contenutoApplicativoNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
				//	check presenza id di correlazione asincrona
				if(msg.getProtocolHeaderInfo()==null || msg.getProtocolHeaderInfo().getRiferimentoMessaggio()==null){
					msgDiag.logPersonalizzato("invocazionePortaDelegata.profiloAsincrono.riferimentoMessaggioNonPresente");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}
			}
			else{
				throw new Exception("Tipo operazione ["+tipoOperazione+"] non gestita");
			}
			
			
			
			/* ------------  Lettura parametri della richiesta ------------- */
		
			// Credenziali utilizzate nella richiesta
			Credenziali credenziali = new Credenziali(requestInfo.getProtocolContext().getCredential());
			// Credenziali credenziali = (Credenziali) msgContext.get("openspcoop.credenziali");

			String credenzialiFornite = "";
			if(credenziali!=null){
				credenzialiFornite = credenziali.toString();
				msgDiag.addKeyword(CostantiPdD.KEY_CREDENZIALI_SA_FRUITORE, credenzialiFornite);
			}

			String invPerRiferimento = "";
			if(idInvocazionePerRiferimento!=null)
				invPerRiferimento = " idInvocazionePerRiferimento["+idInvocazionePerRiferimento+"]";

			String riferimentoMessaggio = "";
			if(msg.getProtocolHeaderInfo()!=null && msg.getProtocolHeaderInfo().getRiferimentoMessaggio()!=null){
				riferimentoMessaggio = " riferimentoMessaggio["+msg.getProtocolHeaderInfo().getRiferimentoMessaggio()+"]";
			}

			String param = "PD["+portaDelegata+"]"+credenzialiFornite+invPerRiferimento+riferimentoMessaggio;
			msgDiag.addKeyword(CostantiPdD.KEY_PARAMETRI_OPERAZIONE_IM, param);
			msgDiag.logPersonalizzato("logInvocazioneOperazione");

			
			
			// Properties Trasporto
			java.util.Properties headerTrasporto = null;
			if(openSPCoopProperties.integrationManager_readInformazioniTrasporto()){
				headerTrasporto = 
					new java.util.Properties();	    
				java.util.Enumeration<?> enTrasporto = req.getHeaderNames();
				while(enTrasporto.hasMoreElements()){
					String nomeProperty = (String)enTrasporto.nextElement();
					headerTrasporto.setProperty(nomeProperty,req.getHeader(nomeProperty));
					//log.info("Proprieta' Trasporto: nome["+nomeProperty+"] valore["+req.getHeader(nomeProperty)+"]");
				} 
			}
			
			
			// Parametri della porta delegata invocata
			urlProtocolContext = new URLProtocolContext();
			urlProtocolContext.setInterfaceName(portaDelegata);
			urlProtocolContext.setFunctionParameters(portaDelegata);
			urlProtocolContext.setRequestURI(portaDelegata);
			urlProtocolContext.setFunction(URLProtocolContext.IntegrationManager_FUNCTION);
			urlProtocolContext.setProtocol(protocolFactory.getProtocol(),
					requestInfo.getProtocolContext().getProtocolWebContext());
			if(openSPCoopProperties.integrationManager_readInformazioniTrasporto()){
				urlProtocolContext.setParametersTrasporto(headerTrasporto);
			}
			

			//	Messaggio di richiesta
			msgDiag.logPersonalizzato("ricezioneRichiesta.elaborazioneDati.inCorso");
			Utilities.printFreeMemory("IntegrationManager - Pre costruzione richiesta");
			if(idInvocazionePerRiferimento!=null){

				if(dumpRaw!=null){
					String contentTypeRichiesta = null; // idInvocazionePerRiferimento
					Integer contentLengthRichiesta = null;
					String rawMessage = idInvocazionePerRiferimento;
					dumpRaw.serializeRequest(contentTypeRichiesta, contentLengthRichiesta, requestInfo.getProtocolContext().getCredential(), 
							urlProtocolContext, rawMessage, null);
				}
				
				stato = new OpenSPCoopStateful();
				stato.initResource(openSPCoopProperties.getIdentitaPortaDefault(protocolFactory.getProtocol()),IntegrationManager.ID_MODULO,idTransazione);
				msgDiag.updateState(stato.getStatoRichiesta(),stato.getStatoRisposta());
				
				// Leggo Messaggio
				try{
					GestoreMessaggi gestoreMessaggio = new GestoreMessaggi(stato, true,idInvocazionePerRiferimento,Costanti.INBOX,msgDiag,context.getPddContext());
					msgRequest = gestoreMessaggio.getMessage();	
				}catch(Exception e){
					msgDiag.logErroreGenerico(e,"gestoreMessaggio.getMessagePerRiferimento("+idInvocazionePerRiferimento+")");
					throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
							get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_520_READ_MSG_FROM_INTEGRATION_MANAGER));
				}

				// Rilascio Connessione DB
				stato.releaseResource();
				stato = null;

			}else{

				if(dumpRaw!=null){
					String contentTypeRichiesta = "text/xml"; // per ora e' cablato.
					Integer contentLengthRichiesta = null;
					String rawMessage = null;
					if(msg.getMessage()!=null){
						try{
							contentLengthRichiesta = msg.getMessage().length;
							rawMessage = new String(msg.getMessage());
						}catch(Throwable t){
							logCore.error("Dump error: "+t.getMessage(),t);
						}
					}
					dumpRaw.serializeRequest(contentTypeRichiesta, contentLengthRichiesta, requestInfo.getProtocolContext().getCredential(), 
							urlProtocolContext, rawMessage, null);
				}
				
				// Ricostruzione Messaggio
				try{
					MessageType messageType = MessageType.SOAP_11; // todo parametrico
					String contentType = MessageUtilities.getDefaultContentType(messageType); // todo parametrico
					String soapAction = "OpenSPCoop"; // todo parametrico
					ByteArrayInputStream bin = new ByteArrayInputStream(msg.getMessage());
					
					OpenSPCoop2MessageParseResult pr = null;
					if(msg.getImbustamento()){
						pr = factory.envelopingMessage(messageType, MessageRole.REQUEST, contentType, soapAction, bin, 
								notifierInputStreamParams, openSPCoopProperties.getAttachmentsProcessingMode(), 
								openSPCoopProperties.isDeleteInstructionTargetMachineXml());
					}
					else{
						pr = factory.createMessage(messageType, MessageRole.REQUEST, contentType, 
								bin, notifierInputStreamParams, openSPCoopProperties.getAttachmentsProcessingMode());
					}
					if(pr.getParseException()!=null){
						context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO_PARSE_EXCEPTION, pr.getParseException());
					}
					msgRequest = pr.getMessage_throwParseException();
					msgRequest.setTransportRequestContext(urlProtocolContext);
				}catch(Exception e){
					
					Throwable tParsing = null;
					if(context.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO_PARSE_EXCEPTION)){
						tParsing = ((ParseException) context.getPddContext().removeObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO_PARSE_EXCEPTION)).getParseException();
					}
					if(tParsing==null){
						tParsing = ParseExceptionUtils.getParseException(e);
					}
					if(tParsing==null){
						tParsing = e;
					}
					
					String msgErrore = tParsing.getMessage();
					if(msgErrore==null){
						msgErrore = tParsing.toString();
					}
					
					context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO, true);
					
					ConfigurazionePdDManager configurazionePdDReader = ConfigurazionePdDManager.getInstance();
					DumpConfigurazione dumpConfig = null;
					if(portaDelegata!=null) {							
						IDPortaDelegata identificativoPortaDelegata = new IDPortaDelegata();
						identificativoPortaDelegata.setNome(portaDelegata);
						PortaDelegata portaDelegataObject = configurazionePdDReader.getPortaDelegata_SafeMethod(identificativoPortaDelegata);
						if(portaDelegataObject!=null) {
							dumpConfig = configurazionePdDReader.getDumpConfigurazione(portaDelegataObject);
						}
					}
					if(dumpConfig==null) {
						dumpConfig = ConfigurazionePdDManager.getInstance().getDumpConfigurazione();
					}
					
					Dump dumpApplicativo = new Dump(openSPCoopProperties.getIdentitaPortaDefault(protocolFactory.getProtocol()),
							IntegrationManager.ID_MODULO,TipoPdD.DELEGATA,context.getPddContext(),
							null,null,
							dumpConfig);
					dumpApplicativo.dumpRichiestaIngressoByIntegrationManagerError(msg.getMessage(),urlProtocolContext);
							//IntegrationManager.buildInfoConnettoreIngresso(req, credenziali, urlProtocolContext));
					if(msg.getImbustamento()==false){
						msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_PROCESSAMENTO, msgErrore);
						msgDiag.logPersonalizzato("buildMsg.nonRiuscito");
						throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_421_MSG_SOAP_NON_COSTRUIBILE_TRAMITE_RICHIESTA_APPLICATIVA.
								getErrore421_MessaggioSOAPNonGenerabile(msgErrore));
					} else {
						msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_PROCESSAMENTO, msgErrore);
						msgDiag.logPersonalizzato("buildMsg.imbustamentoSOAP.nonRiuscito");
						throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_422_IMBUSTAMENTO_SOAP_NON_RIUSCITO_RICHIESTA_APPLICATIVA.
								getErrore422_MessaggioSOAPNonGenerabileTramiteImbustamentoSOAP(msgErrore));
					}
				}
			}
			Utilities.printFreeMemory("IntegrationManager - Post costruzione richiesta");
			msgRequest.setProtocolName(protocolFactory.getProtocol());

			


			//	Costruisco HeaderIntegrazione IntegrationManager
			HeaderIntegrazione headerIntegrazioneRichiesta = new HeaderIntegrazione(idTransazione);


			// Identificazione Porta Delegata INPUT-BASED
			ProtocolHeaderInfo protocolHeaderInfo = msg.getProtocolHeaderInfo();
			if(protocolHeaderInfo!=null){

				if(protocolHeaderInfo.getTipoMittente()!=null && (!"".equals(protocolHeaderInfo.getTipoMittente())))
					headerIntegrazioneRichiesta.getBusta().setTipoMittente(protocolHeaderInfo.getTipoMittente());
				if(protocolHeaderInfo.getMittente()!=null && (!"".equals(protocolHeaderInfo.getMittente())))
					headerIntegrazioneRichiesta.getBusta().setMittente(protocolHeaderInfo.getMittente());

				if(protocolHeaderInfo.getTipoDestinatario()!=null && (!"".equals(protocolHeaderInfo.getTipoDestinatario())))
					headerIntegrazioneRichiesta.getBusta().setTipoDestinatario(protocolHeaderInfo.getTipoDestinatario());
				if(protocolHeaderInfo.getDestinatario()!=null && (!"".equals(protocolHeaderInfo.getDestinatario())))
					headerIntegrazioneRichiesta.getBusta().setDestinatario(protocolHeaderInfo.getDestinatario());

				if(protocolHeaderInfo.getTipoServizio()!=null && (!"".equals(protocolHeaderInfo.getTipoServizio())))
					headerIntegrazioneRichiesta.getBusta().setTipoServizio(protocolHeaderInfo.getTipoServizio());
				if(protocolHeaderInfo.getServizio()!=null && (!"".equals(protocolHeaderInfo.getServizio())))
					headerIntegrazioneRichiesta.getBusta().setServizio(protocolHeaderInfo.getServizio());

				if(protocolHeaderInfo.getAzione()!=null && (!"".equals(protocolHeaderInfo.getAzione())))
					headerIntegrazioneRichiesta.getBusta().setAzione(protocolHeaderInfo.getAzione());

				if(protocolHeaderInfo.getID()!=null && (!"".equals(protocolHeaderInfo.getID())))
					headerIntegrazioneRichiesta.getBusta().setID(protocolHeaderInfo.getID());

				if(protocolHeaderInfo.getIdCollaborazione()!=null && (!"".equals(protocolHeaderInfo.getIdCollaborazione())))
					headerIntegrazioneRichiesta.getBusta().setIdCollaborazione(protocolHeaderInfo.getIdCollaborazione());

				if(protocolHeaderInfo.getRiferimentoMessaggio()!=null && (!"".equals(protocolHeaderInfo.getRiferimentoMessaggio())))
					headerIntegrazioneRichiesta.getBusta().setRiferimentoMessaggio(protocolHeaderInfo.getRiferimentoMessaggio());
			}
			if(msg.getIdApplicativo()!=null && (!"".equals(msg.getIdApplicativo())))
				headerIntegrazioneRichiesta.setIdApplicativo(msg.getIdApplicativo());
			if(msg.getServizioApplicativo()!=null && (!"".equals(msg.getServizioApplicativo())))
				headerIntegrazioneRichiesta.setServizioApplicativo(msg.getServizioApplicativo());

			//	Contesto di Richiesta
			context.setTipoPorta(TipoPdD.DELEGATA);
			context.setCredenziali(credenziali);
			context.setIdModulo(idModulo);
			context.setGestioneRisposta(true); // siamo in un webServices, la risposta deve essere aspettata
			context.setInvocazionePDPerRiferimento(idInvocazionePerRiferimento!=null);
			context.setIdInvocazionePDPerRiferimento(idInvocazionePerRiferimento);
			context.setMessageRequest(msgRequest);
			context.setUrlProtocolContext(urlProtocolContext);
			context.setHeaderIntegrazioneRichiesta(headerIntegrazioneRichiesta);
			context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.PROTOCOL_NAME, protocolFactory.getProtocol());
			context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.REQUEST_INFO, requestInfo);
			context.setProprietaErroreAppl(generatoreErrore.getProprietaErroreAppl());
			
			// Log elaborazione dati completata
			msgDiag.logPersonalizzato("ricezioneRichiesta.elaborazioneDati.completata");
			
			// Invocazione...
			RicezioneContenutiApplicativi gestoreRichiesta = new RicezioneContenutiApplicativi(context,generatoreErrore);
			gestoreRichiesta.process(req);
			msgResponse = context.getMessageResponse();
			if(context.getMsgDiagnostico()!=null){
				msgDiag = context.getMsgDiagnostico();
				// Aggiorno informazioni
				msgDiag.addKeyword(CostantiPdD.KEY_TIPO_OPERAZIONE_IM, tipoOperazione);
			}

			// Check parsing request
			if((msgRequest!=null && msgRequest.getParseException() != null) || 
					(context.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO_PARSE_EXCEPTION))){
			
				// Senno l'esito viene forzato essere 5XX
				try{
					esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext,EsitoTransazioneName.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO);
				}catch(Exception eBuildError){
					esito = EsitoTransazione.ESITO_TRANSAZIONE_ERROR;
				}
				
				context.getPddContext().addObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO, true);
				ParseException parseException = null;
				if( msgRequest!=null && msgRequest.getParseException() != null ){
					parseException = msgRequest.getParseException();
				}
				else{
					parseException = (ParseException) context.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RICHIESTA_NON_RICONOSCIUTO_PARSE_EXCEPTION);
				}
				String msgErrore = parseException.getParseException().getMessage();
				if(msgErrore==null){
					msgErrore = parseException.getParseException().toString();
				}	
				msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_PROCESSAMENTO, msgErrore);
				logCore.error("parsingExceptionRichiesta",parseException.getSourceException());
//				msgDiag.logPersonalizzato("parsingExceptionRichiesta");
//				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_432_PARSING_EXCEPTION_RICHIESTA.
//						getErrore432_MessaggioRichiestaMalformato(parseException.getParseException()));
				// Per l'IntegrationManager esiste un codice specifico
				msgDiag.logPersonalizzato(MsgDiagnosticiProperties.MSG_DIAG_INTEGRATION_MANAGER,"buildMsg.nonRiuscito");
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_421_MSG_SOAP_NON_COSTRUIBILE_TRAMITE_RICHIESTA_APPLICATIVA.
						getErrore421_MessaggioSOAPNonGenerabile(msgErrore));
			}
			
			// Raccolgo l'eventuale header di integrazione
			java.util.Properties headerIntegrazioneRisposta = context.getHeaderIntegrazioneRisposta();
			ProtocolHeaderInfo protocolHeaderInfoResponse = null; 
			if(headerIntegrazioneRisposta!=null){
				java.util.Properties keyValue = openSPCoopProperties.getKeyValue_HeaderIntegrazioneTrasporto();
				protocolHeaderInfoResponse = new ProtocolHeaderInfo();

				protocolHeaderInfoResponse.setID(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_ID_MESSAGGIO)));
				protocolHeaderInfoResponse.setRiferimentoMessaggio(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_RIFERIMENTO_MESSAGGIO)));
				protocolHeaderInfoResponse.setIdCollaborazione(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_COLLABORAZIONE)));

				protocolHeaderInfoResponse.setMittente(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_MITTENTE)));
				protocolHeaderInfoResponse.setTipoMittente(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_TIPO_MITTENTE)));

				protocolHeaderInfoResponse.setDestinatario(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_DESTINATARIO)));
				protocolHeaderInfoResponse.setTipoDestinatario(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_TIPO_DESTINATARIO)));

				protocolHeaderInfoResponse.setServizio(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_SERVIZIO)));
				protocolHeaderInfoResponse.setTipoServizio(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_TIPO_SERVIZIO)));

				protocolHeaderInfoResponse.setAzione(headerIntegrazioneRisposta.getProperty(keyValue.getProperty(CostantiPdD.HEADER_INTEGRAZIONE_AZIONE)));
			}

			InformazioniErroriInfrastrutturali informazioniErrori = ServicesUtils.readInformazioniErroriInfrastrutturali(context.getPddContext());
			
			//	IntepretazioneRisposta
			if(msgResponse!=null){
				
				esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext,
						200, requestInfo.getIntegrationServiceBinding(),
						 msgResponse, 
						context.getProprietaErroreAppl(), informazioniErrori, 
						(context.getPddContext()!=null ? context.getPddContext().getContext() : null));			
												
				IntegrationManagerException exc = null;
				try{
					if(ServiceBinding.SOAP.equals(msgResponse.getServiceBinding())){
						OpenSPCoop2SoapMessage soapMessage = msgResponse.castAsSoap();
						if(soapMessage.getSOAPBody().hasFault()){
	
							descrizioneSoapFault = " ("+SoapUtils.toString(soapMessage.getSOAPBody().getFault(), false)+")";
							
							// Potenziale MsgErroreApplicativo
							SOAPFault fault = soapMessage.getSOAPBody().getFault();
							ProprietaErroreApplicativo pea = context.getProprietaErroreAppl();
							if(pea!=null && fault.getFaultActor()!=null && fault.getFaultActor().equals(pea.getFaultActor())){
								String prefix = org.openspcoop2.protocol.basic.Costanti.ERRORE_INTEGRAZIONE_PREFIX_CODE;
								if(context.getProprietaErroreAppl().getFaultPrefixCode()!=null){
									prefix = context.getProprietaErroreAppl().getFaultPrefixCode();
								}
								if(openSPCoopProperties.isErroreApplicativoIntoDetails() && fault.getDetail()!=null){
									exc = IntegrationManagerUtility.mapXMLIntoProtocolException(protocolFactory,fault.getDetail().getFirstChild(),
											prefix);
								}else{
									exc = IntegrationManagerUtility.mapXMLIntoProtocolException(protocolFactory,fault.getFaultString(),
											prefix);
								}
								if(exc== null)
									throw new Exception("Costruzione Eccezione fallita: null");
							}
						}
					}
				}catch(Exception e){
					try{
						if( (msgResponse!=null && msgResponse.getParseException() != null) ||
								(context.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.CONTENUTO_RISPOSTA_NON_RICONOSCIUTO_PARSE_EXCEPTION))){
							informazioniErrori.setContenutoRispostaNonRiconosciuto(true);
							ParseException parseException = null;
							if( msgResponse!=null && msgResponse.getParseException() != null ){
								parseException = msgResponse.getParseException();
							}
							else{
								parseException = (ParseException) context.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RISPOSTA_NON_RICONOSCIUTO_PARSE_EXCEPTION);
							}
							String msgErrore = parseException.getParseException().getMessage();
							if(msgErrore==null){
								msgErrore = parseException.getParseException().toString();
							}
							msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_PROCESSAMENTO, msgErrore);
							logCore.error("parsingExceptionRisposta",parseException.getSourceException());
							msgDiag.logPersonalizzato("parsingExceptionRisposta");
							throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_440_PARSING_EXCEPTION_RISPOSTA.
									getErrore440_MessaggioRispostaMalformato(parseException.getParseException()));
						}
						else{
							msgDiag.logErroreGenerico(e,"buildProtocolException("+tipoOperazione+")");
							throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
									get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_524_CREAZIONE_PROTOCOL_EXCEPTION));
						}
					}finally{
						// ricalcolo esito
						esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext, 
								500, requestInfo.getIntegrationServiceBinding(),
								msgResponse, 
								context.getProprietaErroreAppl(), informazioniErrori, 
								(context.getPddContext()!=null ? context.getPddContext().getContext() : null));		
					}
				}	
				if(exc!=null){
					throw exc;
				}

				// Se non ho lanciato l'eccezione, costruisco una risposta
				try{
					msgReturn = new IntegrationManagerMessage(msgResponse,false);
				}catch(Exception e){
					try{
						if( (msgResponse!=null && msgResponse.getParseException() != null) ||
								(context.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.CONTENUTO_RISPOSTA_NON_RICONOSCIUTO_PARSE_EXCEPTION))){
							informazioniErrori.setContenutoRispostaNonRiconosciuto(true);
							ParseException parseException = null;
							if( msgResponse!=null && msgResponse.getParseException() != null ){
								parseException = msgResponse.getParseException();
							}
							else{
								parseException = (ParseException) context.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.CONTENUTO_RISPOSTA_NON_RICONOSCIUTO_PARSE_EXCEPTION);
							}
							String msgErrore = parseException.getParseException().getMessage();
							if(msgErrore==null){
								msgErrore = parseException.getParseException().toString();
							}
							msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_PROCESSAMENTO, msgErrore);
							logCore.error("parsingExceptionRisposta",parseException.getSourceException());
							msgDiag.logPersonalizzato("parsingExceptionRisposta");
							throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_440_PARSING_EXCEPTION_RISPOSTA.
									getErrore440_MessaggioRispostaMalformato(parseException.getParseException()));
						}
						else{
							msgDiag.logErroreGenerico(e,"buildMessage_response("+tipoOperazione+")");
							throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.
									get5XX_ErroreProcessamento(CodiceErroreIntegrazione.CODICE_523_CREAZIONE_PROTOCOL_MESSAGE));
						}
					}finally{
						// ricalcolo esito
						esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext, 
								500, requestInfo.getIntegrationServiceBinding(),
								msgResponse, 
								context.getProprietaErroreAppl(), informazioniErrori, 
								(context.getPddContext()!=null ?context.getPddContext().getContext() : null));		
					}
				}
				
				msgDiag.addKeyword(CostantiPdD.KEY_SOAP_FAULT, descrizioneSoapFault);
				msgDiag.logPersonalizzato(MsgDiagnosticiProperties.MSG_DIAG_RICEZIONE_CONTENUTI_APPLICATIVI,"integrationManager.consegnaRispostaApplicativaEffettuata");
				
			}else{
				/*
				// Con la nuova versione di RicezioneContenutiApplicativi, in caso di oneway non viene ritornato un messaggio SOAP empty, ma proprio null
				// Una risposta deve esistere per forza.
				msgDiag.logErroreGenerico("Risposta non presente","gestioneRisposta("+tipoOperazione+")");
				throw new IntegrationManagerException(CostantiPdD.CODICE_511_READ_RESPONSE_MSG,
						CostantiPdD.MSG_5XX_SISTEMA_NON_DISPONIBILE);
				*/
				msgReturn = new IntegrationManagerMessage();
				
				esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext, 
						200, requestInfo.getIntegrationServiceBinding(),
						msgResponse, 
						context.getProprietaErroreAppl(), informazioniErrori, 
						(context.getPddContext()!=null ? context.getPddContext().getContext() : null));	
				// ok oneway
				
				msgDiag.logPersonalizzato(MsgDiagnosticiProperties.MSG_DIAG_RICEZIONE_CONTENUTI_APPLICATIVI,"integrationManager.consegnaRispostaApplicativaVuota");
			}	    

			msgReturn.setProtocolHeaderInfo(protocolHeaderInfoResponse);
			
			if(dumpRaw!=null){
				String contentTypeRisposta = "text/xml"; // per ora e' cablato.
				String rawMessage = null;
				Integer contentLengthRisposta = null;
				if(msgReturn.getMessage()!=null){
					try{
						contentLengthRisposta = msgReturn.getMessage().length;
						rawMessage = new String(msgReturn.getMessage());
					}catch(Throwable t){
						logCore.error("Dump error: "+t.getMessage(),t);
					}
				}
				dumpRaw.serializeResponse(rawMessage, null, null, contentLengthRisposta, contentTypeRisposta, 200);
			}
			
			return msgReturn;
			
		}catch(Exception e){
			
			if(dumpRaw!=null){
				String contentTypeRisposta = "text/xml"; // per ora e' cablato.
				String rawMessage = "[Exception "+e.getClass().getName()+"]: "+ e.getMessage(); // Non riesco ad avere il marshal xml della risposta ritornata
				Integer contentLengthRisposta = null;
				dumpRaw.serializeResponse(rawMessage, null,null, contentLengthRisposta, contentTypeRisposta, 500);
			}
			
			errore = e.getMessage();
			msgDiag.addKeyword(CostantiPdD.KEY_SOAP_FAULT, descrizioneSoapFault);
			
			// Controllo Eccezioni
			if(e instanceof IntegrationManagerException){
				// Lanciata all'interno del metodo
				
				if(esito==null){
					// Altrimenti l'esito era settato
					try{
						esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext,EsitoTransazioneName.ERRORE_PROCESSAMENTO_PDD_5XX);
					}catch(Exception eBuildError){
						esito = EsitoTransazione.ESITO_TRANSAZIONE_ERROR;
					}
				}
				
				IntegrationManagerException eSPC = (IntegrationManagerException)e;
				//String prefix = eSPC.getProprietaErroreApplicativo().getFaultPrefixCode();
				//boolean isFaultAsGenericCode = eSPC.getProprietaErroreApplicativo().isFaultAsGenericCode();
				if(descrizioneSoapFault!=null && !"".equals(descrizioneSoapFault)){
					// stesse informazioni presenti gia nel fault
					msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_CONSEGNA,"ProtocolException/"+eSPC.getCodiceEccezione());
				}else{
					msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_CONSEGNA,"ProtocolException/"+eSPC.getCodiceEccezione() + " " + eSPC.getDescrizioneEccezione());
				}
				msgDiag.logPersonalizzato(MsgDiagnosticiProperties.MSG_DIAG_RICEZIONE_CONTENUTI_APPLICATIVI,"integrationManager.consegnaRispostaApplicativaFallita");
				
				throw eSPC;
			}else{
				msgDiag.logErroreGenerico(e,"invocaPortaDelegata("+tipoOperazione+")");
				try{
					esito = protocolFactory.createEsitoBuilder().getEsito(urlProtocolContext,EsitoTransazioneName.ERRORE_PROCESSAMENTO_PDD_5XX);
				}catch(Exception eBuildError){
					esito = EsitoTransazione.ESITO_TRANSAZIONE_ERROR;
				}
				
				msgDiag.addKeyword(CostantiPdD.KEY_ERRORE_CONSEGNA,errore);
				msgDiag.logPersonalizzato(MsgDiagnosticiProperties.MSG_DIAG_RICEZIONE_CONTENUTI_APPLICATIVI,"integrationManager.consegnaRispostaApplicativaFallita");
				
				throw new IntegrationManagerException(protocolFactory,ErroriIntegrazione.ERRORE_5XX_GENERICO_PROCESSAMENTO_MESSAGGIO.getErroreIntegrazione());
			}
			
		}finally{
			
			try{
				if(stato!=null)
					stato.releaseResource();
			}catch(Exception eClose){}
			
			
			
			
			// *** Chiudo connessione verso PdD Destinazione per casi stateless ***
			String location = "...";
			try{
				IConnettore c = null;
				if(context!=null && context.getIdMessage()!=null){
					c = RepositoryConnettori.removeConnettorePD(context.getIdMessage());
				}
				if(c!=null){
					location = c.getLocation();
					c.disconnect();
				}
			}catch(Exception e){
				msgDiag.logDisconnectError(e, location);
			}
			
			
			
			
			/* ------------  PostOutResponseHandler ------------- */
			PostOutResponseContext postOutResponseContext = new PostOutResponseContext(logCore, protocolFactory);
			try{
				context.getPddContext().addObject(CostantiPdD.DATA_ACCETTAZIONE_RICHIESTA, dataAccettazioneRichiesta);
				if(dataIngressoRichiesta!=null){
					context.getPddContext().addObject(CostantiPdD.DATA_INGRESSO_RICHIESTA, dataIngressoRichiesta);
				}
				postOutResponseContext.setPddContext(context.getPddContext());
				postOutResponseContext.setDataElaborazioneMessaggio(DateManager.getDate());
				postOutResponseContext.setDataPrimaSpedizioneRisposta(DateManager.getDate());
				postOutResponseContext.setEsito(esito);
				postOutResponseContext.setMessaggio(msgResponse);
				
				if(msgRequest!=null){
					postOutResponseContext.setInputRequestMessageSize(msgRequest.getIncomingMessageContentLength());
					postOutResponseContext.setOutputRequestMessageSize(msgRequest.getOutgoingMessageContentLength());
				}else{
					if(msg!=null && msg.getMessage()!=null)
						postOutResponseContext.setInputRequestMessageSize(Long.valueOf(msg.getMessage().length));
				}
				if(msgResponse!=null){
					postOutResponseContext.setInputResponseMessageSize(msgResponse.getIncomingMessageContentLength());
					if(msgReturn!=null && msgReturn.getMessage()!=null)
						postOutResponseContext.setOutputResponseMessageSize(Long.valueOf(msgReturn.getMessage().length));
				}
				
				if(errore!=null){
					postOutResponseContext.setReturnCode(500);
				}else{
					postOutResponseContext.setReturnCode(200);
				}
//				if(context!=null){
				if(context.getTipoPorta()!=null)
					postOutResponseContext.setTipoPorta(context.getTipoPorta());
				else
					postOutResponseContext.setTipoPorta(TipoPdD.DELEGATA);
				postOutResponseContext.setProtocollo(context.getProtocol());
				postOutResponseContext.setIntegrazione(context.getIntegrazione());
//				}else{
//					postOutResponseContext.setTipoPorta(TipoPdD.DELEGATA);
//				}
				postOutResponseContext.setIdModulo(idModulo);
								
			}catch(Exception e){
				msgDiag.logErroreGenerico(e,"postOutResponse, preparazione contesto");
			}
			
			GestoreHandlers.postOutResponse(postOutResponseContext, msgDiag, logCore);
		}
	}
	
}
