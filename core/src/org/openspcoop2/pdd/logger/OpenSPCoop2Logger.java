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




package org.openspcoop2.pdd.logger;


import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.openspcoop2.core.config.MessaggiDiagnostici;
import org.openspcoop2.core.config.constants.CostantiConfigurazione;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.driver.IDServizioFactory;
import org.openspcoop2.pdd.config.ClassNameProperties;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.pdd.core.IPdDContextSerializer;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.engine.builder.DateBuilder;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.diagnostica.IDiagnosticProducer;
import org.openspcoop2.protocol.sdk.diagnostica.MsgDiagnostico;
import org.openspcoop2.protocol.sdk.dump.IDumpProducer;
import org.openspcoop2.protocol.sdk.tracciamento.ITracciaProducer;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.properties.CollectionProperties;
import org.openspcoop2.utils.properties.PropertiesUtilities;
import org.openspcoop2.utils.resources.Charset;
import org.openspcoop2.utils.resources.Loader;
import org.slf4j.Logger;

/**
 * Contiene la definizione un Logger utilizzato dai nodi dell'infrastruttura di OpenSPCoop
 * per la registrazione di messaggi riguardanti :.
 * <ul>
 * <li> Tracciamento
 * <li> MsgDiagnostici
 * </ul>
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class OpenSPCoop2Logger {

	/**  Logger log4j utilizzato per scrivere i tracciamenti */
	protected static Logger loggerTracciamento = null;
	/** Logger log4j utilizzato per scrivere i tracciamenti: impostazione */
	public static boolean loggerTracciamentoAbilitato = false;
	/**  Logger log4j utilizzato per scrivere i msgDiagnostici */
	protected static org.apache.logging.log4j.Logger loggerMsgDiagnostico = null;
	/** Logger log4j utilizzato per scrivere i msg diagnostici: impostazione */
	public static boolean loggerMsgDiagnosticoAbilitato = false;
	/**  Logger log4j utilizzato per scrivere i msgDiagnostici */
	protected static org.apache.logging.log4j.Logger loggerOpenSPCoop2 = null;
	/** Logger log4j utilizzato per scrivere i msg diagnostici 'readable': impostazione */
	public static boolean loggerMsgDiagnosticoReadableAbilitato = false;
	/**  Logger log4j utilizzato per scrivere i msgDiagnostici del servizio di IntegrationManager */
	protected static org.apache.logging.log4j.Logger loggerIntegrationManager = null;
	/** Logger log4j utilizzato per scrivere i msg diagnostici 'readable' del servizio di IntegrationManager: impostazione */
	public static boolean loggerIntegrationManagerAbilitato = false;
	/**  Logger log4j utilizzato per effettuare un dump dei messaggi applicativi */
	protected static Logger loggerDump = null;
	/** Logger log4j utilizzato per scrivere i dump applicativi: impostazione */
	public static boolean loggerDumpAbilitato = false;
	/**  Logger log4j utilizzato per segnalare a video errori gravi (FATAL) o di informazione sull'inizializzazione (INFO)*/
	protected static Logger loggerOpenSPCoopConsole = LoggerWrapperFactory.getLogger("openspcoop2.startup");
	/** Logger log4j utilizzato per segnalare a video errori gravi (FATAL) o di informazione sull'inizializzazione (INFO) */
	protected static boolean loggerOpenSPCoopConsoleStartupAgganciatoLog = false;
	/**  Logger log4j utilizzato per il core di OpenSPCoop */
	protected static Logger loggerOpenSPCoopCore = null;
	/**  Logger log4j utilizzato per registrare le operazioni dei Timer che ripuliscono i repository buste e messaggi*/
	protected static Logger loggerOpenSPCoopTimers = null;
	/**  Logger log4j utilizzato per registrare le operazioni di monitoraggio delle risorse utilizzate dalla PdD (Timer che verficano le risorse della PdD) */
	protected static Logger loggerOpenSPCoopResources = null;
	protected static org.apache.logging.log4j.Logger loggerOpenSPCoopResourcesAsLoggerImpl = null;
	/**  Logger log4j utilizzato per la configurazione di sistema */
	protected static Logger loggerOpenSPCoopConfigurazioneSistema = null;
	/**  Logger log4j utilizzato per i connettori */
	protected static Logger loggerOpenSPCoopConnettori = null;
	/**  Logger log4j utilizzato per i dati binari del servizio PD */
	protected static Logger loggerOpenSPCoopDumpBinarioPD = null;
	/**  Logger log4j utilizzato per i dati binari del servizio PA */
	protected static Logger loggerOpenSPCoopDumpBinarioPA = null;
	/** Appender personalizzati per i messaggi diagnostici di OpenSPCoop */
	protected static List<IDiagnosticProducer> loggerMsgDiagnosticoOpenSPCoopAppender = new ArrayList<IDiagnosticProducer>(); 
	protected static List<String> tipoMsgDiagnosticoOpenSPCoopAppender = new ArrayList<String>();
	/** Appender personalizzati per i tracciamenti di OpenSPCoop */
	protected static List<ITracciaProducer> loggerTracciamentoOpenSPCoopAppender = new ArrayList<ITracciaProducer>(); 
	protected static List<String> tipoTracciamentoOpenSPCoopAppender = new ArrayList<String>();
	/** Appender personalizzati per i dump applicativi di OpenSPCoop */
	protected static List<IDumpProducer> loggerDumpOpenSPCoopAppender = new ArrayList<IDumpProducer>(); 
	protected static List<String> tipoDumpOpenSPCoopAppender = new ArrayList<String>();
	/** PdDContextSerializer */
	private static IPdDContextSerializer pddContextSerializer = null;
	
	
	public static boolean isLoggerOpenSPCoopConsoleStartupAgganciatoLog(){
		return OpenSPCoop2Logger.loggerOpenSPCoopConsoleStartupAgganciatoLog;
	}
	
	/**
	 * Il Metodo si occupa di inizializzare il Logger utilizzato da OpenSPCoop (file,DB scelti da openspcoop2.log4j2.properties)
	 *
	 * @param logConsole Log console
	 * @return true in caso di inizializzazione con successo, false altrimenti.
	 * 
	 */
	public static boolean initializeLogConsole(Logger logConsole){
		InputStream isOp2 = null;
		InputStream isLogger = null;
		try{
			isOp2 = OpenSPCoop2Logger.class.getResourceAsStream("/openspcoop2.properties");
			String confDir = null;
			if(isOp2!=null){
				java.util.Properties op2Properties = new java.util.Properties();
				op2Properties.load(isOp2);
				confDir = op2Properties.getProperty("org.openspcoop2.pdd.confDirectory");
			}
			
			java.util.Properties loggerProperties = new java.util.Properties();
			isLogger = OpenSPCoop2Logger.class.getResourceAsStream("/openspcoop2.log4j2.properties");
			if(isLogger!=null){
				loggerProperties.load(isLogger);
			}
			
			// Cerco eventuale ridefinizione
			CollectionProperties loggerPropertiesRidefinito =  
					PropertiesUtilities.searchLocalImplementation(CostantiPdD.OPENSPCOOP2_LOCAL_HOME,LoggerWrapperFactory.getLogger(OpenSPCoop2Logger.class), 
							CostantiPdD.OPENSPCOOP2_LOGGER_PROPERTIES, CostantiPdD.OPENSPCOOP2_LOGGER_LOCAL_PATH, 
							confDir);
			
			if(loggerPropertiesRidefinito!=null && loggerPropertiesRidefinito.size()>0){
				Enumeration<?> ridefinito = loggerPropertiesRidefinito.keys();
				while (ridefinito.hasMoreElements()) {
					String key = (String) ridefinito.nextElement();
					String value = (String) loggerPropertiesRidefinito.get(key);
					if(loggerProperties.containsKey(key)){
						//Object o = 
						loggerProperties.remove(key);
					}
					loggerProperties.put(key, value);
					//System.out.println("CHECK NUOVO VALORE: "+loggerProperties.get(key));
				}
			}
			
			LoggerWrapperFactory.setLogConfiguration(loggerProperties);
			
			return true;
		}catch(Exception e){
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione del sistema di logging di OpenSPCoop: "
					+e.getMessage(),e);
			return false;
		}finally{
			try{
				if(isOp2!=null){
					isOp2.close();
				}
			}catch(Exception eClose){}
			try{
				if(isLogger!=null){
					isLogger.close();
				}
			}catch(Exception eClose){}
		}
	}
	
	
	public static boolean initialize(Logger logConsole,String rootDirectory,Properties objectProperties){
		return initialize(logConsole, rootDirectory, objectProperties, true);
	}
	public static boolean initialize(Logger logConsole,String rootDirectory,Properties objectProperties, boolean loadExternalConfiguration){
		try{
			
			// Originale
			java.util.Properties loggerProperties = new java.util.Properties();
			java.io.File loggerFile = new java.io.File(rootDirectory+"openspcoop2.log4j2.properties");
			if(loggerFile .exists() == false ){
				loggerProperties.load(OpenSPCoop2Logger.class.getResourceAsStream("/openspcoop2.log4j2.properties"));
			}else{
				FileInputStream fin = null;
				try{
					fin = new java.io.FileInputStream(loggerFile);
					loggerProperties.load(fin);
				}finally{
					try{
						if(fin!=null){
							fin.close();
						}
					}catch(Exception eClose){}
				}
			}
			
			if(loadExternalConfiguration){
			
				loadExternal(logConsole, CostantiPdD.OPENSPCOOP2_LOGGER_PROPERTIES ,CostantiPdD.OPENSPCOOP2_LOGGER_LOCAL_PATH, 
						rootDirectory, loggerProperties, objectProperties);
				
			}

			LoggerWrapperFactory.setLogConfiguration(loggerProperties);
			
			// STARTUP CONSOLE
			String tmp = loggerProperties.getProperty("logger.openspcoop2_startup.level");
			if(tmp!=null){
				if(!tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsoleStartupAgganciatoLog = true;
				}
			}
			
			// TRACCIAMENTO
			OpenSPCoop2Logger.loggerTracciamento = LoggerWrapperFactory.getLogger("openspcoop2.tracciamento");
			if(OpenSPCoop2Logger.loggerTracciamento==null)
				throw new Exception("Logger openspcoop2.tracciamento non trovato");
			// Abilitazione log da Log4j
			tmp = loggerProperties.getProperty("logger.openspcoop2_tracciamento.level");
			if(tmp!=null){
				tmp.trim();
				if(tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging delle tracce disabilitato da log4j (OFF).");
					OpenSPCoop2Logger.loggerTracciamentoAbilitato = false;
				}
				else
					OpenSPCoop2Logger.loggerTracciamentoAbilitato = true;
			}
			
			// MESSAGGI DIAGNOSTICI
			OpenSPCoop2Logger.loggerMsgDiagnostico = LoggerWrapperFactory.getLoggerImpl("openspcoop2.msgDiagnostico");
			if(OpenSPCoop2Logger.loggerMsgDiagnostico==null)
				throw new Exception("Logger openspcoop2.msgDiagnostico non trovato");
			// Abilitazione log da Log4j
			tmp = loggerProperties.getProperty("logger.openspcoop2_msgDiagnostico.level");
			if(tmp!=null){
				tmp.trim();
				if(tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging dei messaggi diagnostici disabilitato da log4j (OFF).");
					OpenSPCoop2Logger.loggerMsgDiagnosticoAbilitato = false;
				}else
					OpenSPCoop2Logger.loggerMsgDiagnosticoAbilitato = true;
			}
			
			// MESSAGGI DIAGNOSTICI LEGGIBILI
			OpenSPCoop2Logger.loggerOpenSPCoop2 = LoggerWrapperFactory.getLoggerImpl("openspcoop2.portaDiDominio");
			if(OpenSPCoop2Logger.loggerOpenSPCoop2==null)
				throw new Exception("Logger openspcoop2.portaDiDominio non trovato");
			// Abilitazione log da Log4j
			tmp = loggerProperties.getProperty("logger.openspcoop2_portaDiDominio.level");
			if(tmp!=null){
				tmp.trim();
				if(tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging dei messaggi diagnostici 'readable' disabilitato da log4j (OFF).");
					OpenSPCoop2Logger.loggerMsgDiagnosticoReadableAbilitato = false;
				}else
					OpenSPCoop2Logger.loggerMsgDiagnosticoReadableAbilitato = true;
			}
			
			// MESSAGGI DIAGNOSTICI DELL'INTEGRATION MANAGER
			OpenSPCoop2Logger.loggerIntegrationManager = LoggerWrapperFactory.getLoggerImpl("openspcoop2.integrationManager");
			if(OpenSPCoop2Logger.loggerIntegrationManager==null)
				throw new Exception("Logger openspcoop2.integrationManager non trovato");
			// Abilitazione log da Log4j
			tmp = loggerProperties.getProperty("logger.openspcoop2_integrationManager.level");
			if(tmp!=null){
				tmp.trim();
				if(tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging dei messaggi diagnostici 'readable' per il servizio di IntegrationManager disabilitato da log4j (OFF).");
					OpenSPCoop2Logger.loggerIntegrationManagerAbilitato = false;
				}else
					OpenSPCoop2Logger.loggerIntegrationManagerAbilitato = true;
			}
			
			// DUMP APPLICATIVO
			OpenSPCoop2Logger.loggerDump = LoggerWrapperFactory.getLogger("openspcoop2.dump");
			if(OpenSPCoop2Logger.loggerDump==null)
				throw new Exception("Logger openspcoop2.dump non trovato");
			// Abilitazione log da Log4j
			tmp = loggerProperties.getProperty("logger.openspcoop2_dump.level");
			if(tmp!=null){
				tmp.trim();
				if(tmp.equalsIgnoreCase("OFF")){
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging dei contenuti applicativi (dump) disabilitato da log4j (OFF).");
					OpenSPCoop2Logger.loggerDumpAbilitato = false;
				}
				else
					OpenSPCoop2Logger.loggerDumpAbilitato = true;
			}

			// OPENSPCOOP CORE
			OpenSPCoop2Logger.loggerOpenSPCoopCore = LoggerWrapperFactory.getLogger("openspcoop2.core");
			if(OpenSPCoop2Logger.loggerOpenSPCoopCore==null)
				throw new Exception("Logger openspcoop2.core non trovato");
			
			// TIMERS LOG
			OpenSPCoop2Logger.loggerOpenSPCoopTimers = LoggerWrapperFactory.getLogger("openspcoop2.timers");
			if(OpenSPCoop2Logger.loggerOpenSPCoopTimers==null)
				throw new Exception("Logger openspcoop2.timers non trovato");
			
			// RESOURCES LOG
			OpenSPCoop2Logger.loggerOpenSPCoopResources = LoggerWrapperFactory.getLogger("openspcoop2.resources");
			if(OpenSPCoop2Logger.loggerOpenSPCoopResources==null)
				throw new Exception("Logger openspcoop2.resources non trovato");
			OpenSPCoop2Logger.loggerOpenSPCoopResourcesAsLoggerImpl = LoggerWrapperFactory.getLoggerImpl("openspcoop2.resources");
			if(OpenSPCoop2Logger.loggerOpenSPCoopResourcesAsLoggerImpl==null)
				throw new Exception("Logger(Impl) openspcoop2.resources non trovato");
			
			// CONFIGURAZIONE SISTEMA LOG
			OpenSPCoop2Logger.loggerOpenSPCoopConfigurazioneSistema = LoggerWrapperFactory.getLogger("openspcoop2.configurazioneSistema");
			if(OpenSPCoop2Logger.loggerOpenSPCoopConfigurazioneSistema==null)
				throw new Exception("Logger openspcoop2.configurazioneSistema non trovato");
			
			// CONNETTORI LOG
			OpenSPCoop2Logger.loggerOpenSPCoopConnettori = LoggerWrapperFactory.getLogger("openspcoop2.connettori");
			if(OpenSPCoop2Logger.loggerOpenSPCoopConnettori==null)
				throw new Exception("Logger openspcoop2.connettori non trovato");
			
			// RAW DATA SERVIZIO PD LOG
			OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPD = LoggerWrapperFactory.getLogger("openspcoop2.dumpBinarioPD");
			if(OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPD==null)
				throw new Exception("Logger openspcoop2.dumpBinarioPD non trovato");
			
			// RAW DATA SERVIZIO PD LOG
			OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPA = LoggerWrapperFactory.getLogger("openspcoop2.dumpBinarioPA");
			if(OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPA==null)
				throw new Exception("Logger openspcoop2.dumpBinarioPA non trovato");
			
			// CONSOLE
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging correttamente inizializzato.");
			
			// PddContextSerializer
			OpenSPCoop2Properties propertiesReader = OpenSPCoop2Properties.getInstance();
			String pddContextSerializerClass = propertiesReader.getPddContextSerializer();
			if(pddContextSerializerClass!=null && !CostantiConfigurazione.NONE.equals(pddContextSerializerClass)){
				try{
					OpenSPCoop2Logger.pddContextSerializer = (IPdDContextSerializer) Loader.getInstance().newInstance(pddContextSerializerClass);
				}catch(Exception e){
					throw new Exception("Inizializzione IPdDContextSerializer non riuscita ["+pddContextSerializerClass+"]:"+e.getMessage(),e);
				}
			}
			
			return true;
		}catch(Exception e){
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione del sistema di logging di OpenSPCoop: "
					+e.getMessage(),e);
			return false;
		}
	}
	
	private static void loadExternal(Logger logConsole, String loggerProperty, String loggerPath, String rootDirectory,
			java.util.Properties loggerProperties, Properties objectProperties) {
			
		// File Local Implementation
		CollectionProperties loggerPropertiesRidefinito =  
			PropertiesUtilities.searchLocalImplementation(CostantiPdD.OPENSPCOOP2_LOCAL_HOME,logConsole, loggerProperty ,loggerPath, rootDirectory);
		if(loggerPropertiesRidefinito!=null && loggerPropertiesRidefinito.size()>0){
			Enumeration<?> ridefinito = loggerPropertiesRidefinito.keys();
			while (ridefinito.hasMoreElements()) {
				String key = (String) ridefinito.nextElement();
				String value = (String) loggerPropertiesRidefinito.get(key);
				if(loggerProperties.containsKey(key)){
					//Object o = 
					loggerProperties.remove(key);
				}
				loggerProperties.put(key, value);
				//System.out.println("CHECK NUOVO VALORE: "+loggerProperties.get(key));
			}
		}
		
		// File Object Implementation
		if(objectProperties!=null && objectProperties.size()>0){
			Enumeration<?> ridefinito = objectProperties.keys();
			while (ridefinito.hasMoreElements()) {
				String key = (String) ridefinito.nextElement();
				String value = (String) objectProperties.get(key);
				if(loggerProperties.containsKey(key)){
					//Object o = 
					loggerProperties.remove(key);
				}
				loggerProperties.put(key, value);
				//System.out.println("CHECK NUOVO VALORE: "+loggerProperties.get(key));
			}
		}

	}
	
	public static boolean initializeProtocolLogger(Logger logConsole, boolean loadExternalConfiguration,String rootDirectory,
			Properties objectProperties) {
		
		try{
		
			ProtocolFactoryManager protocolFactoryManager = ProtocolFactoryManager.getInstance();
			Enumeration<String> protocolNames = protocolFactoryManager.getProtocolNames();
			while (protocolNames.hasMoreElements()) {
				String protocol = (String) protocolNames.nextElement();
				if(protocolFactoryManager.isSupportedProtocolLogger(protocol)) {
					
					java.util.Properties loggerPropertiesProtocolAdjunct = null;
					InputStream isLoggerProtocol = OpenSPCoop2Logger.class.getResourceAsStream("/openspcoop2.protocolAdjunct.log4j2.properties");
					if(isLoggerProtocol!=null){
						String content = Utilities.getAsString(isLoggerProtocol, Charset.UTF_8.getValue());
						if(content!=null) {
							content = content.replaceAll(CostantiPdD.OPENSPCOOP2_LOGGER_PROTOCOL_ID_PROTOCOLLO, protocol);
						}
						loggerPropertiesProtocolAdjunct = new java.util.Properties();
						StringReader sr = new StringReader(content);
						loggerPropertiesProtocolAdjunct.load(sr);
						sr.close();
					}
					if(loadExternalConfiguration){
						
						loadExternal(logConsole, CostantiPdD.getOpenspcoop2LoggerProtocolProperties(protocol),
								CostantiPdD.getOpenspcoop2LoggerProtocolLocalPath(protocol) ,
								rootDirectory, loggerPropertiesProtocolAdjunct, objectProperties);
						
					}
					logConsole.info("Protocol '"+protocol+"': Log4j config append");
					LoggerWrapperFactory.setLogConfiguration(loggerPropertiesProtocolAdjunct,true);
					
					Logger log = LoggerWrapperFactory.getLogger(CostantiPdD.getOpenspcoop2LoggerFactoryName(protocol));
					protocolFactoryManager.getProtocolFactoryByName(protocol).initProtocolLogger(log);
					log.info("Inizializzazione completata");
					
				}
			}
			
			return true;
			
		}catch(Exception e){
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione del sistema di logging di OpenSPCoop per i protocolli: "
					+e.getMessage(),e);
			return false;
		}
	}

	/**
	 * Il Metodo si occupa di inizializzare gli appender personalizzati di OpenSPCoop che permettono di effettuare log dei msg diagnostici senza passare da log4j
	 * @param msgDiagConfig Configurazione
	 * @return true in caso di inizializzazione con successo, false altrimenti.
	 */
	public static boolean initializeMsgDiagnosticiOpenSPCoopAppender(MessaggiDiagnostici msgDiagConfig){
		try{
			// Inizializzazione msg diagnostici appender personalizzati
			if(msgDiagConfig!=null){
				ClassNameProperties prop = ClassNameProperties.getInstance();
				for(int i=0; i< msgDiagConfig.sizeOpenspcoopAppenderList(); i++){

					// MsgDiagAppenderClass
					String msgDiagAppenderClass = prop.getMsgDiagnosticoOpenSPCoopAppender(msgDiagConfig.getOpenspcoopAppender(i).getTipo());
					if(msgDiagAppenderClass == null){
						throw new Exception("Riscontrato errore durante il caricamento del msg diagnostico appender ["+msgDiagConfig.getOpenspcoopAppender(i).getTipo()+"]: appender non registrato.");
					}

					// Carico appender richiesto
					IDiagnosticProducer appender = null;
					try{
						appender = (IDiagnosticProducer) Loader.getInstance().newInstance(msgDiagAppenderClass);
						appender.initializeAppender(msgDiagConfig.getOpenspcoopAppender(i));
					}catch(ClassNotFoundException e){
						throw new Exception("Riscontrato errore durante il caricamento del msg diagnostico appender specificato ["+msgDiagConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}catch(Exception e){
						throw new Exception("Riscontrato errore durante l'inizializzazione del msg diagnostico appender specificato ["+msgDiagConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}
					
					// Aggiungo agli appender registrati
					OpenSPCoop2Logger.loggerMsgDiagnosticoOpenSPCoopAppender.add(appender);
					OpenSPCoop2Logger.tipoMsgDiagnosticoOpenSPCoopAppender.add(msgDiagConfig.getOpenspcoopAppender(i).getTipo());
					
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging: MsgDiagnosticoOpenSPCoopAppender di tipo ["+msgDiagConfig.getOpenspcoopAppender(i).getTipo()+"] correttamente inizializzato.");
					
				}
			}
			return true;
		}catch(Exception e){
			// Azzero gli appender personalizzati
			OpenSPCoop2Logger.loggerMsgDiagnosticoOpenSPCoopAppender.clear(); 
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione degli appender personalizzati per msg diagnostici: "
					+e.getMessage());
			return false;
		}
	}

	
	/**
	 * Il Metodo si occupa di inizializzare gli appender personalizzati di OpenSPCoop che permettono di effettuare log dei tracciamenti senza passare da log4j
	 * @param tracciamentoConfig Configurazione
	 * @return true in caso di inizializzazione con successo, false altrimenti.
	 */
	public static boolean initializeTracciamentoOpenSPCoopAppender(org.openspcoop2.core.config.Tracciamento tracciamentoConfig){
		try{
			// Inizializzazione tracciamento appender personalizzati
			if(tracciamentoConfig!=null){
				ClassNameProperties prop = ClassNameProperties.getInstance();
				for(int i=0; i< tracciamentoConfig.sizeOpenspcoopAppenderList(); i++){
					
					// Tracciamento appender class
					String tracciamentoAppenderClass = prop.getTracciamentoOpenSPCoopAppender(tracciamentoConfig.getOpenspcoopAppender(i).getTipo());
					if(tracciamentoAppenderClass == null){
						throw new Exception("Riscontrato errore durante il caricamento del tracciamento appender ["+tracciamentoConfig.getOpenspcoopAppender(i).getTipo()+"]: appender non registrato.");
					}

					// Carico appender richiesto
					ITracciaProducer appender = null;
					try{
						appender = (ITracciaProducer) Loader.getInstance().newInstance(tracciamentoAppenderClass);
						appender.initializeAppender(tracciamentoConfig.getOpenspcoopAppender(i));
					}catch(ClassNotFoundException e){
						throw new Exception("Riscontrato errore durante il caricamento del tracciamento appender specificato ["+tracciamentoConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}catch(Exception e){
						throw new Exception("Riscontrato errore durante l'inizializzazione del tracciamento appender specificato ["+tracciamentoConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}
					
					// Aggiungo agli appender registrati
					OpenSPCoop2Logger.loggerTracciamentoOpenSPCoopAppender.add(appender);
					OpenSPCoop2Logger.tipoTracciamentoOpenSPCoopAppender.add(tracciamentoConfig.getOpenspcoopAppender(i).getTipo());
					
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging: TracciamentoOpenSPCoopAppender di tipo ["+tracciamentoConfig.getOpenspcoopAppender(i).getTipo()+"] correttamente inizializzato.");
					
				}
			}
			return true;
		}catch(Exception e){
			// Azzero gli appender personalizzati
			OpenSPCoop2Logger.loggerTracciamentoOpenSPCoopAppender.clear(); 
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione degli appender personalizzati per il tracciamento: "
					+e.getMessage());
			return false;
		}
	}
	
	/**
	 * Il Metodo si occupa di inizializzare gli appender personalizzati di OpenSPCoop che permettono di effettuare log dei dump applicativi senza passare da log4j
	 * @param dumpConfig Configurazione
	 * @return true in caso di inizializzazione con successo, false altrimenti.
	 */
	public static boolean initializeDumpOpenSPCoopAppender(org.openspcoop2.core.config.Dump dumpConfig){
		try{
			// Inizializzazione dump appender personalizzati
			if(dumpConfig!=null){
				ClassNameProperties prop = ClassNameProperties.getInstance();
				for(int i=0; i< dumpConfig.sizeOpenspcoopAppenderList(); i++){
					
					// Dump Appender class
					String dumpAppenderClass = prop.getTracciamentoOpenSPCoopAppender(dumpConfig.getOpenspcoopAppender(i).getTipo());
					if(dumpAppenderClass == null){
						throw new Exception("Riscontrato errore durante il caricamento del tracciamento appender ["+dumpConfig.getOpenspcoopAppender(i).getTipo()+"]: appender non registrato.");
					}

					// Carico appender richiesto
					IDumpProducer appender = null;
					try{
						appender = (IDumpProducer) Loader.getInstance().newInstance(dumpAppenderClass);
						appender.initializeAppender(dumpConfig.getOpenspcoopAppender(i));
					}catch(ClassNotFoundException e){
						throw new Exception("Riscontrato errore durante il caricamento del dump appender specificato ["+dumpConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}catch(Exception e){
						throw new Exception("Riscontrato errore durante l'inizializzazione del dump appender specificato ["+dumpConfig.getOpenspcoopAppender(i).getTipo()+"]: "+e.getMessage());
					}
					
					// Aggiungo agli appender registrati
					OpenSPCoop2Logger.loggerDumpOpenSPCoopAppender.add(appender);
					OpenSPCoop2Logger.tipoDumpOpenSPCoopAppender.add(dumpConfig.getOpenspcoopAppender(i).getTipo());
					
					OpenSPCoop2Logger.loggerOpenSPCoopConsole.info("Sistema di logging: DumpOpenSPCoopAppender di tipo ["+dumpConfig.getOpenspcoopAppender(i).getTipo()+"] correttamente inizializzato.");
				}
			}
			return true;
		}catch(Exception e){
			// Azzero gli appender personalizzati
			OpenSPCoop2Logger.loggerDumpOpenSPCoopAppender.clear(); 
			OpenSPCoop2Logger.loggerOpenSPCoopConsole.error("Riscontrato errore durante l'inizializzazione degli appender personalizzati per il dump applicativo: "
					+e.getMessage());
			return false;
		}
	}
	

	public static String humanReadable(MsgDiagnostico msgDiag,String protocol){
		return humanReadable(msgDiag, null, null, 
				null, false, null, null, null, protocol);
	}
	public static String humanReadable(MsgDiagnostico msgDiag,IProtocolFactory<?> protocolFactory){
		return humanReadable(msgDiag, null, null, 
				null, false, null, null, null, protocolFactory.getProtocol());
	}
	public static String humanReadable(MsgDiagnostico msgDiag,String idCorrelazioneApplicativa,String idCorrelazioneApplicativaRisposta,
			String porta,boolean delegata,IDSoggetto fruitore,IDServizio servizio,String servizioApplicativo,IProtocolFactory<?> protocolFactory){
		return humanReadable(msgDiag, idCorrelazioneApplicativa, idCorrelazioneApplicativaRisposta, 
				porta, delegata, fruitore, servizio, servizioApplicativo, protocolFactory.getProtocol());
	}

	/**
	 * Trasforma il messaggio diagnostico in una forma leggibile.
	 * 
	 * @param msgDiag MSg diagnostico
	 * @param idCorrelazioneApplicativa Identificativo di correlazione applicativa
	 * @param porta Identificativo della Porta
	 * @param delegata Indicazione se siamo in un contesto di porta delegata o applicativa
	 * @param fruitore Fruitore
	 * @param servizio Servizio
	 * @param servizioApplicativo Servizio Applicativo
	 * @return messaggio diagnostico in una forma leggibile.
	 */
	public static String humanReadable(MsgDiagnostico msgDiag,String idCorrelazioneApplicativa,String idCorrelazioneApplicativaRisposta,
			String porta,boolean delegata,IDSoggetto fruitore,IDServizio servizio,String servizioApplicativo,String protocol){

		Date gdo = msgDiag.getGdo();
		String idPorta = msgDiag.getIdSoggetto().getCodicePorta()+"."+msgDiag.getIdSoggetto().toString();
		String idFunzione = msgDiag.getIdFunzione();
		int valueLivello = msgDiag.getSeverita();
		String text = msgDiag.getMessaggio();
		String idBusta = msgDiag.getIdBusta();
		String codiceDiagnostico = msgDiag.getCodice();
		
		StringBuffer showMsg = new StringBuffer();
		
		if(protocol!=null)
			showMsg.append("<").append(protocol).append(">");
		
		if(OpenSPCoop2Logger.pddContextSerializer!=null){
			Hashtable<String, String> contextSerializerParameters = OpenSPCoop2Logger.pddContextSerializer.getLoggerKeywords();
			if(contextSerializerParameters!=null && contextSerializerParameters.size()>0){
				Enumeration<String> keywordContext = contextSerializerParameters.keys();
				while(keywordContext.hasMoreElements()){
					String keyword =  keywordContext.nextElement();
					if(msgDiag.getPropertiesNames()!=null){
						String [] propertyNames = msgDiag.getPropertiesNames();
						for (int i = 0; i < propertyNames.length; i++) {
							if(keyword.equals(propertyNames[i])){
								if(showMsg.length()>0){
									showMsg.append(" ");
								}
								showMsg.append(propertyNames[i]+":");
								showMsg.append(msgDiag.getProperty(propertyNames[i]));
							}
						}
					}
				}
			}
		}
		
		if(codiceDiagnostico!=null){
			if(showMsg.length()>0){
				showMsg.append(" ");
			}
			showMsg.append(codiceDiagnostico);
			showMsg.append(" ");
		}
		showMsg.append(idPorta);
		showMsg.append(" ");
		showMsg.append(idFunzione);
		showMsg.append(" <");
		showMsg.append(DateBuilder.getDate_Format(gdo));
		showMsg.append("> ");
		showMsg.append("(");
		showMsg.append((LogLevels.toLog4J(valueLivello)).toString());
		showMsg.append(")");
		if(idBusta!=null){
			showMsg.append(" ID:");
			showMsg.append(idBusta);
		}
		if(idCorrelazioneApplicativa!=null){
			showMsg.append(" IDApplicativo:");
			showMsg.append(idCorrelazioneApplicativa);
		}
		if(idCorrelazioneApplicativaRisposta!=null){
			showMsg.append(" IDApplicativoRisposta:");
			showMsg.append(idCorrelazioneApplicativaRisposta);
		}
		if(porta!=null && !"".equals(porta)) {
			if(delegata) {
				showMsg.append(" PD:"+porta);
			}else {
				showMsg.append(" PA:"+porta);
			}
		}
		if(servizioApplicativo!=null){
			showMsg.append(" SA:"+servizioApplicativo);
		}
		if( fruitore!=null ){
			showMsg.append(" FR:");
			showMsg.append(fruitore.toString());
		}
		if( fruitore!=null && servizio!=null)
			showMsg.append(" -> ");
		if( servizio!=null ){
			if(servizio.getNome()!=null){
				showMsg.append(" S:");
				try{
					showMsg.append(IDServizioFactory.getInstance().getUriFromIDServizio(servizio));
				}catch(Exception e){
					showMsg.append(servizio.toString(false));
				}
			}else if(servizio.getSoggettoErogatore()!=null){
				showMsg.append(" ER:");
				showMsg.append(servizio.getSoggettoErogatore().toString());
			}
			
			if(servizio.getAzione()!=null){
				showMsg.append(" A:");
				showMsg.append(servizio.getAzione());
			}
		}
				
		showMsg.append("\n");
		showMsg.append(text);
		showMsg.append("\n");

		return showMsg.toString();


	}

	public static Logger getLoggerOpenSPCoopConsole() {
		return OpenSPCoop2Logger.loggerOpenSPCoopConsole;
	}

	public static Logger getLoggerOpenSPCoopCore() {
		return OpenSPCoop2Logger.loggerOpenSPCoopCore;
	}
	
	public static Logger getLoggerOpenSPCoopTimers() {
		return OpenSPCoop2Logger.loggerOpenSPCoopTimers;
	}
	
	public static Logger getLoggerOpenSPCoopResources() {
		return OpenSPCoop2Logger.loggerOpenSPCoopResources;
	}

	public static Logger getLoggerOpenSPCoopConfigurazioneSistema() {
		return OpenSPCoop2Logger.loggerOpenSPCoopConfigurazioneSistema;
	}
	
	public static Logger getLoggerOpenSPCoopConnettori() {
		return OpenSPCoop2Logger.loggerOpenSPCoopConnettori;
	}
	
	public static Logger getLoggerOpenSPCoopDumpBinarioPD() {
		return OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPD;
	}
	
	public static Logger getLoggerOpenSPCoopDumpBinarioPA() {
		return OpenSPCoop2Logger.loggerOpenSPCoopDumpBinarioPA;
	}
	
	public static List<IDiagnosticProducer> getLoggerMsgDiagnosticoOpenSPCoopAppender() {
		return OpenSPCoop2Logger.loggerMsgDiagnosticoOpenSPCoopAppender;
	}

	public static List<String> getTipoMsgDiagnosticoOpenSPCoopAppender() {
		return OpenSPCoop2Logger.tipoMsgDiagnosticoOpenSPCoopAppender;
	}

	public static List<ITracciaProducer> getLoggerTracciamentoOpenSPCoopAppender() {
		return OpenSPCoop2Logger.loggerTracciamentoOpenSPCoopAppender;
	}

	public static List<String> getTipoTracciamentoOpenSPCoopAppender() {
		return OpenSPCoop2Logger.tipoTracciamentoOpenSPCoopAppender;
	}

}


