/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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



package org.openspcoop2.pdd.config;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openspcoop2.core.config.AccessoConfigurazione;
import org.openspcoop2.core.config.AccessoConfigurazionePdD;
import org.openspcoop2.core.config.AccessoDatiAutenticazione;
import org.openspcoop2.core.config.AccessoDatiAutorizzazione;
import org.openspcoop2.core.config.AccessoRegistro;
import org.openspcoop2.core.config.Configurazione;
import org.openspcoop2.core.config.Credenziali;
import org.openspcoop2.core.config.GestioneErrore;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaServizioApplicativo;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.config.PortaDelegataServizioApplicativo;
import org.openspcoop2.core.config.RoutingTable;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.Soggetto;
import org.openspcoop2.core.config.StatoServiziPdd;
import org.openspcoop2.core.config.SystemProperties;
import org.openspcoop2.core.config.constants.CostantiConfigurazione;
import org.openspcoop2.core.config.constants.CredenzialeTipo;
import org.openspcoop2.core.config.driver.DriverConfigurazioneException;
import org.openspcoop2.core.config.driver.DriverConfigurazioneNotFound;
import org.openspcoop2.core.config.driver.FiltroRicercaPorteApplicative;
import org.openspcoop2.core.config.driver.FiltroRicercaPorteDelegate;
import org.openspcoop2.core.config.driver.FiltroRicercaServiziApplicativi;
import org.openspcoop2.core.config.driver.FiltroRicercaSoggetti;
import org.openspcoop2.core.config.driver.IDriverConfigurazioneGet;
import org.openspcoop2.core.config.driver.db.DriverConfigurazioneDB;
import org.openspcoop2.core.config.driver.xml.DriverConfigurazioneXML;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDServizioApplicativo;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziException;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziNotFound;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.protocol.registry.RegistroServiziManager;
import org.openspcoop2.protocol.registry.RegistroServiziReader;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.cache.Cache;
import org.openspcoop2.utils.cache.CacheAlgorithm;
import org.slf4j.Logger;



/**
 * Classe utilizzata per effettuare query a registri di servizio di openspcoop.
 * E' possibile indicare piu' di una sorgente e/o una cache
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class ConfigurazionePdD  {

	/** Fonti su cui effettuare le query:
	 * - CACHE
	 * - configurazione
	 */
	private Cache cache = null;
	private IDriverConfigurazioneGet driverConfigurazionePdD;

	private boolean useConnectionPdD = false;
	private String tipoDatabase = null;

	/** Logger utilizzato per debug. */
	private Logger log = null;


	/** Variabili statiche contenenti le configurazioni della Porta di Dominio
	 *  - ConfigurazioneGenerale
	 *  - GestioneErrore
	 *  - RoutingTable
	 *  - AccessoRegistro
	 */
	private static Configurazione configurazioneGeneralePdD = null;
	private static GestioneErrore gestioneErroreComponenteCooperazione = null;
	private static GestioneErrore gestioneErroreComponenteIntegrazione = null;
	private static RoutingTable routingTable = null;
	private static AccessoRegistro accessoRegistro = null;
	private static AccessoConfigurazione accessoConfigurazione = null;
	private static AccessoDatiAutorizzazione accessoDatiAutorizzazione = null;
	private static AccessoDatiAutenticazione accessoDatiAutenticazione = null;
	/** ConfigurazioneDinamica */
	private boolean configurazioneDinamica = false;

	/** OpenSPCoop Properties */
	private OpenSPCoop2Properties openspcoopProperties = null;

	/** ConfigLocalProperties */
	private ConfigLocalProperties configLocalProperties = null;



	/* --------------- Cache --------------------*/
	public boolean isCacheAbilitata(){
		return this.cache!=null;
	}
	public void resetCache() throws DriverConfigurazioneException{
		if(this.cache!=null){
			try{
				this.cache.clear();
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}
	}
	public String printStatsCache(String separator) throws DriverConfigurazioneException{
		if(this.cache!=null){
			try{
				return this.cache.printStats(separator);
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}else{
			throw new DriverConfigurazioneException("Cache non abilitata");
		}
	}
	public void abilitaCache() throws DriverConfigurazioneException{
		if(this.cache!=null)
			throw new DriverConfigurazioneException("Cache gia' abilitata");
		else{
			try{
				this.cache = new Cache(CostantiConfigurazione.CACHE_CONFIGURAZIONE_PDD);
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}
	}
	public void abilitaCache(Long dimensioneCache,Boolean algoritmoCacheLRU,Long itemIdleTime,Long itemLifeSecond) throws DriverConfigurazioneException{
		if(this.cache!=null)
			throw new DriverConfigurazioneException("Cache gia' abilitata");
		else{
			try{
				org.openspcoop2.core.config.Cache configurazioneCache = new org.openspcoop2.core.config.Cache();
				if(dimensioneCache!=null){
					configurazioneCache.setDimensione(dimensioneCache+"");
				}
				if(algoritmoCacheLRU!=null){
					if(algoritmoCacheLRU)
						configurazioneCache.setAlgoritmo(CostantiConfigurazione.CACHE_LRU);
					else
						configurazioneCache.setAlgoritmo(CostantiConfigurazione.CACHE_MRU);
				}
				if(itemIdleTime!=null){
					configurazioneCache.setItemIdleTime(itemIdleTime+"");
				}
				if(itemLifeSecond!=null){
					configurazioneCache.setItemLifeSecond(itemLifeSecond+"");
				}
				initCacheConfigurazione(configurazioneCache, null, false);
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}
	}
	public void disabilitaCache() throws DriverConfigurazioneException{
		if(this.cache==null)
			throw new DriverConfigurazioneException("Cache gia' disabilitata");
		else{
			try{
				this.cache.clear();
				this.cache = null;
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}
	}
	public String listKeysCache(String separator) throws DriverConfigurazioneException{
		if(this.cache!=null){
			try{
				return this.cache.printKeys(separator);
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}else{
			throw new DriverConfigurazioneException("Cache non abilitata");
		}
	}
	public String getObjectCache(String key) throws DriverConfigurazioneException{
		if(this.cache!=null){
			try{
				Object o = this.cache.get(key);
				if(o!=null){
					return o.toString();
				}else{
					return "oggetto con chiave ["+key+"] non presente";
				}
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}else{
			throw new DriverConfigurazioneException("Cache non abilitata");
		}
	}
	public void removeObjectCache(String key) throws DriverConfigurazioneException{
		if(this.cache!=null){
			try{
				this.cache.remove(key);
			}catch(Exception e){
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}else{
			throw new DriverConfigurazioneException("Cache non abilitata");
		}
	}



	/*   -------------- Costruttore -----------------  */ 

	/**
	 * Si occupa di inizializzare l'engine che permette di effettuare
	 * query alla configurazione di OpenSPCoop.
	 * L'engine inizializzato sara' diverso a seconda del <var>tipo</var> di configurazione :
	 * <ul>
	 * <li> {@link DriverConfigurazioneXML}, interroga una configurazione realizzata tramite un file xml.
	 * <li> {@link DriverConfigurazioneDB}, interroga una configurazione realizzata tramite un database relazionale.
	 * </ul>
	 *
	 * @param accessoConfigurazione Informazioni per accedere alla configurazione della PdD OpenSPCoop.
	 */
	public ConfigurazionePdD(AccessoConfigurazionePdD accessoConfigurazione,Logger alog,Logger alogConsole,Properties localProperties, 
			String jndiNameDatasourcePdD, boolean forceDisableCache, boolean useOp2UtilsDatasource, boolean bindJMX, boolean prefillCache)throws DriverConfigurazioneException{

		try{ 
			// Inizializzo OpenSPCoopProperties
			this.openspcoopProperties = OpenSPCoop2Properties.getInstance();
			this.configurazioneDinamica = this.openspcoopProperties.isConfigurazioneDinamica();

			if(alog!=null)
				this.log = alog;
			else
				this.log = LoggerWrapperFactory.getLogger(ConfigurazionePdD.class);

			String msg = "Leggo configurazione di tipo["+accessoConfigurazione.getTipo()+"]   location["+accessoConfigurazione.getLocation()+"]";
			this.log.info(msg);
			if(alogConsole!=null)
				alogConsole.info(msg);

			// inizializzazione XML
			if(CostantiConfigurazione.CONFIGURAZIONE_XML.equalsIgnoreCase(accessoConfigurazione.getTipo())){
				this.driverConfigurazionePdD = new DriverConfigurazioneXML(accessoConfigurazione.getLocation(),this.log);
				if(this.driverConfigurazionePdD ==null || ((DriverConfigurazioneXML)this.driverConfigurazionePdD).create==false){
					throw new DriverConfigurazioneException("Riscontrato errore durante l'inizializzazione della configurazione di tipo "+
							accessoConfigurazione.getTipo()+" con location: "+accessoConfigurazione.getLocation());
				}
			} 

			// inizializzazione DB
			else if(CostantiConfigurazione.CONFIGURAZIONE_DB.equalsIgnoreCase(accessoConfigurazione.getTipo())){			
				this.driverConfigurazionePdD = new DriverConfigurazioneDB(accessoConfigurazione.getLocation(),accessoConfigurazione.getContext(),
						this.log,accessoConfigurazione.getTipoDatabase(),accessoConfigurazione.isCondivisioneDatabasePddRegistro(),
						useOp2UtilsDatasource, bindJMX);
				if(this.driverConfigurazionePdD ==null || ((DriverConfigurazioneDB)this.driverConfigurazionePdD).create==false){
					throw new DriverConfigurazioneException("Riscontrato errore durante l'inizializzazione della configurazione di tipo "+
							accessoConfigurazione.getTipo()+" con location: "+accessoConfigurazione.getLocation());
				}
				this.useConnectionPdD = jndiNameDatasourcePdD.equals(accessoConfigurazione.getLocation());
				this.tipoDatabase = accessoConfigurazione.getTipoDatabase();
			} 

			// tipo di configurazione non conosciuto
			else{
				throw new DriverConfigurazioneException("Riscontrato errore durante l'inizializzazione della configurazione di tipo sconosciuto "+
						accessoConfigurazione.getTipo()+" con location: "+accessoConfigurazione.getLocation());
			}

			
			// Inizializzazione ConfigLocalProperties
			this.configLocalProperties = new ConfigLocalProperties(this.log, this.openspcoopProperties.getRootDirectory(),localProperties);
			

			// Inizializzazione della Cache
			AccessoConfigurazione accessoDatiConfigurazione = null;
			try{
				accessoDatiConfigurazione = this.driverConfigurazionePdD.getAccessoConfigurazione();
			}catch(DriverConfigurazioneNotFound notFound){}
			if(accessoDatiConfigurazione!=null && accessoDatiConfigurazione.getCache()!=null){
				if(forceDisableCache==false){
					initCacheConfigurazione(accessoDatiConfigurazione.getCache(),alogConsole, prefillCache);
				}
			}

		}catch(Exception e){
			String msg = "Riscontrato errore durante l'inizializzazione della configurazione di OpenSPCoop: "+e.getMessage();
			this.log.error(msg,e);
			if(alogConsole!=null)
				alogConsole.info(msg);
			throw new DriverConfigurazioneException("Riscontrato errore durante l'inizializzazione della configurazione di OpenSPCoop: "+e.getMessage());
		}
	}

	private void initCacheConfigurazione(org.openspcoop2.core.config.Cache configurazioneCache,Logger alogConsole, boolean prefillCache)throws Exception{
		this.cache = new Cache(CostantiConfigurazione.CACHE_CONFIGURAZIONE_PDD);

		String msg = null;
		if( (configurazioneCache.getDimensione()!=null) ||
				(configurazioneCache.getAlgoritmo() != null) ){

			try{
				if( configurazioneCache.getDimensione()!=null ){
					int dimensione = -1;				
					if(prefillCache){
						dimensione = Integer.MAX_VALUE;
					}
					else{
						dimensione = Integer.parseInt(configurazioneCache.getDimensione());
					}
					msg = "Dimensione della cache (ConfigurazionePdD) impostata al valore: "+dimensione;
					if(prefillCache){
						msg = "[Prefill Enabled] " + msg;
					}
					if(prefillCache){
						this.log.warn(msg);
					}
					else{
						this.log.info(msg);
					}
					if(alogConsole!=null){
						if(prefillCache){
							alogConsole.warn(msg);
						}
						else{
							alogConsole.info(msg);
						}
					}
					this.cache.setCacheSize(dimensione);
				}
				else{
					if(prefillCache){
						int dimensione = Integer.MAX_VALUE;
						msg = "[Prefill Enabled] Dimensione della cache (ConfigurazionePdD) impostata al valore: "+dimensione;
						this.log.warn(msg);
						if(alogConsole!=null){
							alogConsole.warn(msg);
						}
						this.cache.setCacheSize(dimensione);
					}
				}
			}catch(Exception error){
				throw new DriverConfigurazioneException("Parametro errato per la dimensione della cache (ConfigurazionePdD): "+error.getMessage());
			}
			
			if( configurazioneCache.getAlgoritmo() != null ){
				msg = "Algoritmo di cache (ConfigurazionePdD) impostato al valore: "+configurazioneCache.getAlgoritmo();
				this.log.info(msg);
				if(alogConsole!=null)
					alogConsole.info(msg);
				if(CostantiConfigurazione.CACHE_MRU.equals(configurazioneCache.getAlgoritmo()))
					this.cache.setCacheAlgoritm(CacheAlgorithm.MRU);
				else
					this.cache.setCacheAlgoritm(CacheAlgorithm.LRU);
			}

		}

		if( (configurazioneCache.getItemIdleTime() != null) ||
				(configurazioneCache.getItemLifeSecond() != null) ){

			try{
				if( configurazioneCache.getItemIdleTime() != null  ){
					int itemIdleTime = -1;
					if(prefillCache){
						itemIdleTime = -1;
					}
					else{
						itemIdleTime = Integer.parseInt(configurazioneCache.getItemIdleTime());
					}
					msg = "Attributo 'IdleTime' (ConfigurazionePdD) impostato al valore: "+itemIdleTime;
					if(prefillCache){
						msg = "[Prefill Enabled] " + msg;
					}
					if(prefillCache){
						this.log.warn(msg);
					}
					else{
						this.log.info(msg);
					}
					if(alogConsole!=null){
						if(prefillCache){
							alogConsole.warn(msg);
						}
						else{
							alogConsole.info(msg);
						}
					}
					this.cache.setItemIdleTime(itemIdleTime);	
				}
				else{
					if(prefillCache){
						int itemIdleTime = -1;
						msg = "[Prefill Enabled] Attributo 'IdleTime' (ConfigurazionePdD) impostato al valore: "+itemIdleTime;
						this.log.warn(msg);
						if(alogConsole!=null){
							alogConsole.warn(msg);
						}
						this.cache.setItemIdleTime(itemIdleTime);
					}
				}
			}catch(Exception error){
				throw new DriverConfigurazioneException("Parametro errato per l'attributo 'IdleTime' (ConfigurazionePdD): "+error.getMessage());
			}
			
			try{
				if( configurazioneCache.getItemLifeSecond() != null  ){
					int itemLifeSecond = -1;				
					if(prefillCache){
						itemLifeSecond = -1;
					}
					else{
						itemLifeSecond = Integer.parseInt(configurazioneCache.getItemLifeSecond());
					}
					msg = "Attributo 'MaxLifeSecond' (ConfigurazionePdD) impostato al valore: "+itemLifeSecond;
					if(prefillCache){
						msg = "[Prefill Enabled] " + msg;
					}
					if(prefillCache){
						this.log.warn(msg);
					}
					else{
						this.log.info(msg);
					}
					if(alogConsole!=null){
						if(prefillCache){
							alogConsole.warn(msg);
						}
						else{
							alogConsole.info(msg);
						}
					}
					this.cache.setItemLifeTime(itemLifeSecond);
				}
				else{
					if(prefillCache){
						int itemLifeSecond = -1;
						msg = "Attributo 'MaxLifeSecond' (ConfigurazionePdD) impostato al valore: "+itemLifeSecond;
						this.log.warn(msg);
						if(alogConsole!=null){
							alogConsole.warn(msg);
						}
						this.cache.setItemLifeTime(itemLifeSecond);
					}
				}
			}catch(Exception error){
				throw new DriverConfigurazioneException("Parametro errato per l'attributo 'MaxLifeSecond' (ConfigurazionePdD): "+error.getMessage());
			}

		}
		
		if(prefillCache){
			this.prefillCache(null,alogConsole);
		}
	}


	protected IDriverConfigurazioneGet getDriverConfigurazionePdD() {
		return this.driverConfigurazionePdD;
	} 


	
	public void prefillCache(Connection connectionPdD,Logger alogConsole){
		
		String msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD) in corso ...";
		this.log.info(msg);
		if(alogConsole!=null){
			alogConsole.info(msg);
		}
		
		// *** Soggetti ***
		FiltroRicercaSoggetti filtroRicercaSoggetti = new FiltroRicercaSoggetti();
		List<IDSoggetto> idSoggetti = null;
		try{
			idSoggetti = this.driverConfigurazionePdD.getAllIdSoggetti(filtroRicercaSoggetti);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		if(idSoggetti!=null && idSoggetti.size()>0){
			
			msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura di "+idSoggetti.size()+" soggetti ...";
			this.log.debug(msg);
			if(alogConsole!=null){
				alogConsole.debug(msg);
			}
			
			for (IDSoggetto idSoggetto : idSoggetti) {
				
				try{
					this.cache.remove(_getKey_getSoggettoByID(idSoggetto));
					this.getSoggetto(connectionPdD, idSoggetto);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
			}
		}
		
		FiltroRicercaPorteDelegate filtroPorteDelegate = new FiltroRicercaPorteDelegate();
		List<IDPortaDelegata> idPDs = null;
		try{
			idPDs = this.driverConfigurazionePdD.getAllIdPorteDelegate(filtroPorteDelegate);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		if(idPDs!=null && idPDs.size()>0){
			
			msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura di "+idPDs.size()+" porte delegate ...";
			this.log.debug(msg);
			if(alogConsole!=null){
				alogConsole.debug(msg);
			}
			
			for (IDPortaDelegata idPortaDelegata : idPDs) {
				
				try{
					this.cache.remove(_getKey_getIDPortaDelegata(idPortaDelegata.getNome()));
					this.getIDPortaDelegata(connectionPdD, idPortaDelegata.getNome());
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				try{
					this.cache.remove(_getKey_getPortaDelegata(idPortaDelegata));
					this.getPortaDelegata(connectionPdD, idPortaDelegata);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				PortaDelegata pd = null;
				try{
					pd = this.driverConfigurazionePdD.getPortaDelegata(idPortaDelegata);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				if(pd!=null){
					
					IDSoggetto idSoggettoProprietario = new IDSoggetto(pd.getTipoSoggettoProprietario(), pd.getNomeSoggettoProprietario());
										
					IDServizioApplicativo idSA_anonimo = new IDServizioApplicativo();
					idSA_anonimo.setIdSoggettoProprietario(idSoggettoProprietario);
					idSA_anonimo.setNome(CostantiPdD.SERVIZIO_APPLICATIVO_ANONIMO);
					try{
						this.cache.remove(_getKey_getServizioApplicativo(idSA_anonimo));
						this.getServizioApplicativo(connectionPdD, idSA_anonimo);
					}
					catch(DriverConfigurazioneNotFound notFound){}
					catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
					
					if(pd.sizeServizioApplicativoList()>0){
						for (PortaDelegataServizioApplicativo saPD : pd.getServizioApplicativoList()) {
							
							IDServizioApplicativo idSA = new IDServizioApplicativo();
							idSA.setIdSoggettoProprietario(idSoggettoProprietario);
							idSA.setNome(saPD.getNome());
							
							try{
								this.cache.remove(_getKey_getServizioApplicativo(idSA));
								this.getServizioApplicativo(connectionPdD, idSA);
							}
							catch(DriverConfigurazioneNotFound notFound){}
							catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
							
							ServizioApplicativo sa = null;
							try{
								sa = this.driverConfigurazionePdD.getServizioApplicativo(idSA);
							}
							catch(DriverConfigurazioneNotFound notFound){}
							catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
									
							if(sa!=null){
								if(sa.getInvocazionePorta()!=null && sa.getInvocazionePorta().sizeCredenzialiList()>0){
									for (Credenziali credenziale : sa.getInvocazionePorta().getCredenzialiList()) {
										if(CredenzialeTipo.BASIC.equals(credenziale.getTipo())){
											try{
												this.cache.remove(_getKey_getServizioApplicativoByCredenzialiBasic(credenziale.getUser(), credenziale.getPassword()));
												this.getServizioApplicativoByCredenzialiBasic(connectionPdD, credenziale.getUser(), credenziale.getPassword());
											}
											catch(DriverConfigurazioneNotFound notFound){}
											catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
										}
										else if(CredenzialeTipo.SSL.equals(credenziale.getTipo())){
											try{
												this.cache.remove(_getKey_getServizioApplicativoByCredenzialiSsl(credenziale.getSubject()));
												this.getServizioApplicativoByCredenzialiSsl(connectionPdD, credenziale.getSubject());
											}
											catch(DriverConfigurazioneNotFound notFound){}
											catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
										}
										else if(CredenzialeTipo.PRINCIPAL.equals(credenziale.getTipo())){
											try{
												this.cache.remove(_getKey_getServizioApplicativoByCredenzialiPrincipal(credenziale.getUser()));
												this.getServizioApplicativoByCredenzialiPrincipal(connectionPdD, credenziale.getUser());
											}
											catch(DriverConfigurazioneNotFound notFound){}
											catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura di router/soggettiVirtuali ...";
		this.log.debug(msg);
		if(alogConsole!=null){
			alogConsole.debug(msg);
		}
		
		try{
			this.cache.remove(this._getKey_getRouter());
			this.getRouter(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getSoggettiVirtuali());
			this.getSoggettiVirtuali(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getServizi_SoggettiVirtuali());
			this.getServizi_SoggettiVirtuali(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		// *** PorteDelegate ***
		// getPortaDelegata invocata precedentemente con i soggetti
		
		// *** PorteApplicative ***
		FiltroRicercaPorteApplicative filtroPorteApplicative = new FiltroRicercaPorteApplicative();
		List<IDPortaApplicativa> idPAs = null;
		try{
			idPAs = this.driverConfigurazionePdD.getAllIdPorteApplicative(filtroPorteApplicative);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		if(idPAs!=null && idPAs.size()>0){
			
			msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura di "+idPAs.size()+" porte applicative ...";
			this.log.debug(msg);
			if(alogConsole!=null){
				alogConsole.debug(msg);
			}
			
			for (IDPortaApplicativa idPA : idPAs) {
								
				try{
					this.cache.remove(_getKey_getIDPortaApplicativa(idPA.getNome()));
					this.getIDPortaApplicativa(connectionPdD, idPA.getNome());
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				try{
					this.cache.remove(_getKey_getPortaApplicativa(idPA));
					this.getPortaApplicativa(connectionPdD, idPA);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				PortaApplicativa pa  = null;
				try{
					pa  = this.driverConfigurazionePdD.getPortaApplicativa(idPA);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				if(pa!=null){
					
					if(pa!=null){
						if(pa.sizeServizioApplicativoList()>0){
							IDSoggetto idSoggettoProprietario = new IDSoggetto(pa.getTipoSoggettoProprietario(), pa.getNomeSoggettoProprietario());
							for (PortaApplicativaServizioApplicativo saPA : pa.getServizioApplicativoList()) {
								try{
									IDServizioApplicativo idSA = new IDServizioApplicativo();
									idSA.setIdSoggettoProprietario(idSoggettoProprietario);
									idSA.setNome(saPA.getNome());
									this.cache.remove(_getKey_getServizioApplicativo(idSA));
									this.getServizioApplicativo(connectionPdD, idSA);
								}
								catch(DriverConfigurazioneNotFound notFound){}
								catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
							}
						}
					}
					
				}
				
			}
		}
		
		// getPorteApplicative_SoggettiVirtuali non inizializzabile
		
		// *** ServiziApplicativi ***
		// getServizioApplicativo(PD) invocata precedentemente con i soggetti
		// getServizioApplicativo(PA) invocata precedentemente con le porte applicative
		// getServizioApplicativoAutenticatoBasic(PD) invocata precedentemente con i soggetti
		// getServizioApplicativoAutenticatoSsl(PD) invocata precedentemente con i soggetti
		// getServizioApplicativoAutenticatoPrincipal(PD) invocata precedentemente con i soggetti
		FiltroRicercaServiziApplicativi filtroServiziApplicativi = new FiltroRicercaServiziApplicativi();
		List<IDServizioApplicativo> idSAs = null;
		try{
			idSAs = this.driverConfigurazionePdD.getAllIdServiziApplicativi(filtroServiziApplicativi);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		if(idSAs!=null && idSAs.size()>0){
			
			msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura di "+idSAs.size()+" servizi applicativi ...";
			this.log.debug(msg);
			if(alogConsole!=null){
				alogConsole.debug(msg);
			}
			
			for (IDServizioApplicativo idSA : idSAs) {
				ServizioApplicativo sa = null;
				try{
					sa = this.driverConfigurazionePdD.getServizioApplicativo(idSA);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(DriverConfigurazioneException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
						
				if(sa!=null){
					if(sa.getInvocazionePorta()!=null && sa.getInvocazionePorta().sizeCredenzialiList()>0){
						for (Credenziali credenziale : sa.getInvocazionePorta().getCredenzialiList()) {
							if(CredenzialeTipo.BASIC.equals(credenziale.getTipo())){
								try{
									this.cache.remove(_getKey_getServizioApplicativoByCredenzialiBasic(credenziale.getUser(), credenziale.getPassword()));
									this.getServizioApplicativoByCredenzialiBasic(connectionPdD, credenziale.getUser(), credenziale.getPassword());
								}
								catch(DriverConfigurazioneNotFound notFound){}
								catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
							}
							else if(CredenzialeTipo.SSL.equals(credenziale.getTipo())){
								try{
									this.cache.remove(_getKey_getServizioApplicativoByCredenzialiSsl(credenziale.getSubject()));
									this.getServizioApplicativoByCredenzialiSsl(connectionPdD, credenziale.getSubject());
								}
								catch(DriverConfigurazioneNotFound notFound){}
								catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
							}
							else if(CredenzialeTipo.PRINCIPAL.equals(credenziale.getTipo())){
								try{
									this.cache.remove(_getKey_getServizioApplicativoByCredenzialiPrincipal(credenziale.getUser()));
									this.getServizioApplicativoByCredenzialiPrincipal(connectionPdD, credenziale.getUser());
								}
								catch(DriverConfigurazioneNotFound notFound){}
								catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}				
							}
						}
					}
				}
			}
		}
		
		// *** Configurazione ***
		
		msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD), lettura della configurazione della porta ...";
		this.log.debug(msg);
		if(alogConsole!=null){
			alogConsole.debug(msg);
		}
		
		try{
			this.cache.remove(_getKey_getRoutingTable());
			this.getRoutingTable(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getAccessoRegistro());
			this.getAccessoRegistro(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getAccessoConfigurazione());
			this.getAccessoConfigurazione(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getAccessoDatiAutorizzazione());
			this.getAccessoDatiAutorizzazione(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getAccessoDatiAutenticazione());
			this.getAccessoDatiAutenticazione(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getGestioneErrore(false));
			this.getGestioneErroreComponenteIntegrazione(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getGestioneErrore(true));
			this.getGestioneErroreComponenteCooperazione(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}

		try{
			this.cache.remove(_getKey_getConfigurazioneGenerale());
			this.getConfigurazioneGenerale(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		try{
			this.cache.remove(_getKey_getConfigurazioneWithOnlyExtendedInfo());
			this.getConfigurazioneWithOnlyExtendedInfo(connectionPdD);
		}
		catch(DriverConfigurazioneNotFound notFound){}
		catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		

		msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD) completata";
		this.log.info(msg);
		if(alogConsole!=null){
			alogConsole.info(msg);
		}
	}
	
	public void prefillCacheConInformazioniRegistro(Logger alogConsole){
		
		String msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD-RegistroServizi) in corso ...";
		this.log.info(msg);
		if(alogConsole!=null){
			alogConsole.info(msg);
		}
		
		RegistroServiziReader registroServiziReader = RegistroServiziManager.getInstance().getRegistroServiziReader();
		
		
		org.openspcoop2.core.registry.driver.FiltroRicercaSoggetti filtroSoggetti = new org.openspcoop2.core.registry.driver.FiltroRicercaSoggetti();
		List<IDSoggetto> listSoggetti = null;
		try{
			listSoggetti = registroServiziReader.getAllIdSoggetti_noCache(filtroSoggetti,null);
		}
		catch(DriverRegistroServiziNotFound notFound){}
		catch(DriverRegistroServiziException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		org.openspcoop2.core.registry.driver.FiltroRicercaServizi filtroServizi = new org.openspcoop2.core.registry.driver.FiltroRicercaServizi();
		List<IDServizio> listIdServizi = null;
		try{
			listIdServizi = registroServiziReader.getAllIdServizi_noCache(filtroServizi,null);
		}
		catch(DriverRegistroServiziNotFound notFound){}
		catch(DriverRegistroServiziException e){this.log.error("[prefill] errore"+e.getMessage(),e);}
		
		if(listIdServizi!=null && listIdServizi.size()>0){
		
			msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD-RegistroServizi), lettura di "+listIdServizi.size()+" servizi ...";
			this.log.debug(msg);
			if(alogConsole!=null){
				alogConsole.debug(msg);
			}
			
			for (IDServizio idServizio : listIdServizi) {
			
				try{
					this.cache.remove(_getKey_getPorteApplicative(idServizio,false));
					this.getPorteApplicative(null, idServizio, false);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				try{
					this.cache.remove(_getKey_getPorteApplicative(idServizio,true));
					this.getPorteApplicative(null, idServizio, true);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				try{
					this.cache.remove(_getKey_getPorteApplicative_SoggettiVirtuali(idServizio));
					this.getPorteApplicative_SoggettiVirtuali(null, idServizio, null, false);
				}
				catch(DriverConfigurazioneNotFound notFound){}
				catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
				
				if(listSoggetti!=null && listSoggetti.size()>0){
				
					msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD-RegistroServizi), lettura di "+listSoggetti.size()+" soggetto per il servizio ["+idServizio+"] ...";
					this.log.debug(msg);
					if(alogConsole!=null){
						alogConsole.debug(msg);
					}
					
					for (IDSoggetto idSoggetto : listSoggetti) {
						
						try{
							this.cache.remove(_getKey_getPorteApplicativeVirtuali(idSoggetto, idServizio,false));
							this.getPorteApplicativeVirtuali(null, idSoggetto, idServizio, false);
						}
						catch(DriverConfigurazioneNotFound notFound){}
						catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
						
						try{
							this.cache.remove(_getKey_getPorteApplicativeVirtuali(idSoggetto, idServizio,true));
							this.getPorteApplicativeVirtuali(null, idSoggetto, idServizio, true);
						}
						catch(DriverConfigurazioneNotFound notFound){}
						catch(Exception e){this.log.error("[prefill] errore"+e.getMessage(),e);}
						
					}
					
				}
			}
		}
		
		msg = "[Prefill] Inizializzazione cache (ConfigurazionePdD-RegistroServizi) completata";
		this.log.info(msg);
		if(alogConsole!=null){
			alogConsole.info(msg);
		}
	}
	
	
	
	
	
	/**
	 * Si occupa di effettuare una ricerca nella configurazione, e di inserire la ricerca in cache
	 */
	public Object getObjectCache(String keyCache,String methodName,
			Connection connectionPdD,
			Object ... instances) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		Class<?>[] classArgoments = null;
		Object[] values = null;
		if(instances!=null && instances.length>0){
			classArgoments = new Class<?>[instances.length];
			values = new Object[instances.length];
			for (int i = 0; i < instances.length; i++) {
				classArgoments[i] = instances[i].getClass();
				values[i] = instances[i];
			}
		}
		return getObjectCache(keyCache, methodName, connectionPdD, classArgoments, values);
	}
	public synchronized Object getObjectCache(String keyCache,String methodName,
			Connection connectionPdD,
			Class<?>[] classArgoments, Object[] values) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{


		DriverConfigurazioneNotFound dNotFound = null;
		Object obj = null;
		try{

			//			System.out.println("@"+keyCache+"@ INFO CACHE: "+this.cache.toString());
			//			System.out.println("@"+keyCache+"@ KEYS: \n\t"+this.cache.printKeys("\n\t"));

			// Raccolta dati
			if(keyCache == null)
				throw new DriverConfigurazioneException("["+methodName+"]: KeyCache non definita");	

			// se e' attiva una cache provo ad utilizzarla
			if(this.cache!=null){
				org.openspcoop2.utils.cache.CacheResponse response = 
						(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(keyCache);
				if(response != null){
					if(response.getObject()!=null){
						this.log.debug("Oggetto (tipo:"+response.getObject().getClass().getName()+") con chiave ["+keyCache+"] (methodo:"+methodName+") in cache.");
						return response.getObject();
					}else if(response.getException()!=null){
						this.log.debug("Eccezione (tipo:"+response.getException().getClass().getName()+") con chiave ["+keyCache+"] (methodo:"+methodName+") in cache.");
						throw (Exception) response.getException();
					}else{
						this.log.error("In cache non e' presente ne un oggetto ne un'eccezione.");
					}
				}
			}

			// Effettuo le query nella mia gerarchia di registri.
			this.log.debug("oggetto con chiave ["+keyCache+"] (methodo:"+methodName+") ricerco nella configurazione...");
			try{
				obj = getObject(methodName,connectionPdD,classArgoments,values);
			}catch(DriverConfigurazioneNotFound e){
				dNotFound = e;
			}

			// Aggiungo la risposta in cache (se esiste una cache)	
			// Se ho una eccezione aggiungo in cache solo una not found
			if( this.cache!=null ){ 	
				if(dNotFound!=null){
					this.log.info("Aggiungo eccezione not found ["+keyCache+"] in cache");
				}else if(obj!=null){
					this.log.info("Aggiungo oggetto ["+keyCache+"] in cache");
				}else{
					throw new Exception("Metodo ("+methodName+") ha ritornato un valore null");
				}
				try{	
					org.openspcoop2.utils.cache.CacheResponse responseCache = new org.openspcoop2.utils.cache.CacheResponse();
					if(dNotFound!=null){
						responseCache.setException(dNotFound);
					}else{
						responseCache.setObject((java.io.Serializable)obj);
					}
					this.cache.put(keyCache,responseCache);
				}catch(UtilsException e){
					this.log.error("Errore durante l'inserimento in cache ["+keyCache+"]: "+e.getMessage());
				}
			}

		}catch(DriverConfigurazioneException e){
			throw e;
		}catch(DriverConfigurazioneNotFound e){
			throw e;
		}catch(Exception e){
			if(DriverConfigurazioneNotFound.class.getName().equals(e.getClass().getName()))
				throw (DriverConfigurazioneNotFound) e;
			else
				throw new DriverConfigurazioneException("Configurazione, Algoritmo di Cache fallito: "+e.getMessage(),e);
		}

		if(dNotFound!=null){
			throw dNotFound;
		}else
			return obj;
	}

	/**
	 * Si occupa di effettuare una ricerca nella configurazione
	 */
	public Object getObject(String methodNameParam,
			Connection connectionPdD,
			Object ... instances) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		Class<?>[] classArgoments = null;
		Object[] values = null;
		if(instances!=null && instances.length>0){
			classArgoments = new Class<?>[instances.length];
			values = new Object[instances.length];
			for (int i = 0; i < instances.length; i++) {
				classArgoments[i] = instances[i].getClass();
				values[i] = instances[i];
			}
		}
		return getObject(methodNameParam, connectionPdD, classArgoments, values);
	}
	public Object getObject(String methodNameParam,
			Connection connectionPdD,
			Class<?>[] classArgoments, Object[] values) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		String methodName = null;
		if("getConfigurazioneWithOnlyExtendedInfo".equals(methodNameParam)){
			methodName = "getConfigurazioneGenerale";
		}
		else{
			methodName = methodNameParam+"";
		}

		// Effettuo le query nella mia gerarchia di registri.
		Object obj = null;
		DriverConfigurazioneNotFound notFound = null;
		this.log.debug("Cerco nella configurazione");
		try{
			org.openspcoop2.core.config.driver.IDriverConfigurazioneGet driver = getDriver(connectionPdD);
			this.log.debug("invocazione metodo ["+methodName+"]...");
			if(classArgoments==null || classArgoments.length==0){
				Method method =  driver.getClass().getMethod(methodName);
				obj = method.invoke(driver);
			}else if(classArgoments.length==1){
				Method method =  driver.getClass().getMethod(methodName,classArgoments[0]);
				obj = method.invoke(driver,values[0]);
			}else if(classArgoments.length==2){
				Method method =  driver.getClass().getMethod(methodName,classArgoments[0],classArgoments[1]);
				obj = method.invoke(driver,values[0],values[1]);
			}else if(classArgoments.length==3){
				Method method =  driver.getClass().getMethod(methodName,classArgoments[0],classArgoments[1],classArgoments[2]);
				obj = method.invoke(driver,values[0],values[1],values[2]);
			}else
				throw new Exception("Troppi argomenti per gestire la chiamata del metodo");
		}catch(DriverConfigurazioneNotFound e){
			this.log.debug("Ricerca nella configurazione non riuscita (metodo ["+methodName+"]): "+e.getMessage());
			notFound=e;
		}
		catch(java.lang.reflect.InvocationTargetException e){
			if(e.getTargetException()!=null){
				if(DriverConfigurazioneNotFound.class.getName().equals(e.getTargetException().getClass().getName())){
					// Non presente
					this.log.debug("Ricerca nella configurazione non riuscita [NotFound] (metodo ["+methodName+"]): "+e.getTargetException().getMessage());
					notFound=new DriverConfigurazioneNotFound(e.getTargetException().getMessage(),e.getTargetException());
				}else if(DriverConfigurazioneException.class.getName().equals(e.getTargetException().getClass().getName())){
					// Non presente
					this.log.debug("Ricerca nella configurazione non riuscita [DriverException] (metodo ["+methodName+"]): "+e.getTargetException().getMessage(),e.getTargetException());
					throw new DriverConfigurazioneException(e.getTargetException().getMessage(),e.getTargetException());
				}else{
					this.log.debug("Ricerca nella configurazione non riuscita [InvTargetExcp.getTarget] (metodo ["+methodName+"]), "+e.getTargetException().getMessage(),e.getTargetException());
					throw new DriverConfigurazioneException(e.getTargetException().getMessage(),e.getTargetException());
				}
			}else{
				this.log.debug("Ricerca nella configurazione non riuscita [InvTargetExcp] (metodo ["+methodName+"]), "+e.getMessage(),e);
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}
		catch(Exception e){
			this.log.debug("ricerca nella configurazione non riuscita (metodo ["+methodName+"]), "+e.getMessage(),e);
			throw new DriverConfigurazioneException(e.getMessage(),e);
		}

		// Refresh configurazione locale
		if(notFound!=null){
			try{
				if("getGestioneErroreComponenteCooperazione".equals(methodName)){
					obj = this.configLocalProperties.updateGestioneErroreCooperazione(null);
				}
				else if("getGestioneErroreComponenteIntegrazione".equals(methodName)){
					obj = this.configLocalProperties.updateGestioneErroreIntegrazione(null);
				}				
			}catch(Exception e){
				this.log.debug("Refresh nella configurazione locale non riuscita (metodo ["+methodName+"]): "+e.getMessage());
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
			if(obj==null){
				throw notFound;
			}
		}
		else{		
			try{
				if("getRoutingTable".equals(methodName)){
					obj = this.configLocalProperties.updateRouting((RoutingTable)obj);
				}
				else if("getAccessoRegistro".equals(methodName)){
					obj = this.configLocalProperties.updateAccessoRegistro((AccessoRegistro)obj);
				}
				else if("getAccessoConfigurazione".equals(methodName)){
					obj = this.configLocalProperties.updateAccessoConfigurazione((AccessoConfigurazione)obj);
				}
				else if("getAccessoDatiAutorizzazione".equals(methodName)){
					obj = this.configLocalProperties.updateAccessoDatiAutorizzazione((AccessoDatiAutorizzazione)obj);
				}
				else if("getAccessoDatiAutenticazione".equals(methodName)){
					obj = this.configLocalProperties.updateAccessoDatiAutenticazione((AccessoDatiAutenticazione)obj);
				}
				else if("getGestioneErroreComponenteCooperazione".equals(methodName)){
					obj = this.configLocalProperties.updateGestioneErroreCooperazione((GestioneErrore)obj);
				}
				else if("getGestioneErroreComponenteIntegrazione".equals(methodName)){
					obj = this.configLocalProperties.updateGestioneErroreIntegrazione((GestioneErrore)obj);
				}
				else if("getConfigurazioneGenerale".equals(methodName)){
					obj = this.configLocalProperties.updateConfigurazione((Configurazione)obj);
				}
			}catch(Exception e){
				this.log.debug("Refresh nella configurazione locale non riuscita (metodo ["+methodName+"]): "+e.getMessage());
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}

			try{
				if("getConfigurazioneWithOnlyExtendedInfo".equals(methodNameParam)){
					Configurazione config = (Configurazione) obj;
					Configurazione c = new Configurazione();
					for (Object object : config.getExtendedInfoList()) {
						c.addExtendedInfo(object);
					}
					obj = c;
				}
			}catch(Exception e){
				this.log.debug("Aggiornamento Configurazione Extended non riuscita (metodo ["+methodNameParam+"]): "+e.getMessage());
				throw new DriverConfigurazioneException(e.getMessage(),e);
			}
		}

		this.log.debug("invocazione metodo ["+methodName+"] completata.");
		return obj;

	}


	private org.openspcoop2.core.config.driver.IDriverConfigurazioneGet getDriver(Connection connectionPdD) throws DriverConfigurazioneException{

		org.openspcoop2.core.config.driver.IDriverConfigurazioneGet driver = this.driverConfigurazionePdD;
		if((driver instanceof DriverConfigurazioneDB)){
			if(connectionPdD!=null && this.useConnectionPdD){
				//System.out.println("[CONFIG] USE CONNECTION VERSIONE!!!!!");
				driver = new DriverConfigurazioneDB(connectionPdD, this.log, this.tipoDatabase);
				if( ((DriverConfigurazioneDB)driver).create == false){
					throw new DriverConfigurazioneException("Inizializzazione DriverConfigurazioneDB con connessione PdD non riuscita");
				}
			}
			else{
				//System.out.println("[CONFIG] USE DATASOURCE VERSIONE!!!!!");
			}
		}

		return driver;
	}	










	// SOGGETTO 

	private String _getKey_getSoggettoByID(IDSoggetto aSoggetto){
		return "getSoggetto_" + aSoggetto.getTipo() + aSoggetto.getNome();
	}
	public Soggetto getSoggetto(Connection connectionPdD,IDSoggetto aSoggetto) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		// Raccolta dati
		if(aSoggetto == null || aSoggetto.getNome()==null || aSoggetto.getTipo()==null)
			throw new DriverConfigurazioneException("[getSoggetto]: Parametro non definito");	

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getSoggettoByID(aSoggetto);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((Soggetto) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		Soggetto sogg = null;
		if(this.cache!=null){
			sogg = (Soggetto) this.getObjectCache(key,"getSoggetto",connectionPdD,aSoggetto);
		}else{
			sogg = (Soggetto) this.getObject("getSoggetto",connectionPdD,aSoggetto);
		}

		if(sogg!=null)
			return sogg;
		else
			throw new DriverConfigurazioneNotFound("[getSoggetto] Soggetto non trovato");
	}

	private String _getKey_getRouter(){
		return "getRouter";
	}
	public Soggetto getRouter(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getRouter();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((Soggetto) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		Soggetto sogg = null;
		if(this.cache!=null){
			sogg = (Soggetto) this.getObjectCache(key,"getRouter",connectionPdD);
		}else{
			sogg = (Soggetto) this.getObject("getRouter",connectionPdD);
		}

		if(sogg!=null)
			return sogg;
		else
			throw new DriverConfigurazioneNotFound("[getRouter] Soggetto non trovato");
	}

	private String _getKey_getSoggettiVirtuali(){
		return "getSoggettiVirtuali";
	}
	@SuppressWarnings(value = "unchecked")
	public HashSet<String> getSoggettiVirtuali(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getSoggettiVirtuali();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((HashSet<String>) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		HashSet<String> lista = null;
		if(this.cache!=null){
			lista = (HashSet<String>) this.getObjectCache(key,"getSoggettiVirtuali",connectionPdD);
		}else{
			lista = (HashSet<String>) this.getObject("getSoggettiVirtuali",connectionPdD);
		}

		if(lista!=null)
			return lista;
		else
			throw new DriverConfigurazioneNotFound("[getSoggettiVirtuali] Lista Soggetti Virtuali non costruita");
	}

	private String _getKey_getServizi_SoggettiVirtuali(){
		return "getServizi_SoggettiVirtuali";
	}
	@SuppressWarnings(value = "unchecked")
	public HashSet<IDServizio> getServizi_SoggettiVirtuali(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			 key = this._getKey_getServizi_SoggettiVirtuali();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((HashSet<IDServizio>) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		HashSet<IDServizio> lista = null;
		if(this.cache!=null){
			lista = (HashSet<IDServizio>) this.getObjectCache(key,"getServizi_SoggettiVirtuali",connectionPdD);
		}else{
			lista = (HashSet<IDServizio>) this.getObject("getServizi_SoggettiVirtuali",connectionPdD);
		}

		if(lista!=null)
			return lista;
		else
			throw new DriverConfigurazioneNotFound("[getServizi_SoggettiVirtuali] Lista Servizi erogati da Soggetti Virtuali non costruita");

	}





	// PORTA DELEGATA

	private String _getKey_getIDPortaDelegata(String nome){
		 return "getIDPortaDelegata_" + nome;
	}
	public IDPortaDelegata getIDPortaDelegata(Connection connectionPdD,String nome) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(nome==null)
			throw new DriverConfigurazioneException("[getIDPortaDelegata]: Parametro non definito (nome)");

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getIDPortaDelegata(nome);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((IDPortaDelegata) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		IDPortaDelegata idPD = null;
		if(this.cache!=null){
			idPD = (IDPortaDelegata) this.getObjectCache(key,"getIDPortaDelegata",connectionPdD,nome);
		}else{
			idPD = (IDPortaDelegata) this.getObject("getIDPortaDelegata",connectionPdD,nome);
		}

		if(idPD!=null)
			return idPD;
		else
			throw new DriverConfigurazioneNotFound("Porta Delegata ["+nome+"] non esistente");
	} 
	
	private String _getKey_getPortaDelegata(IDPortaDelegata idPD){
		 return "getPortaDelegata_" + idPD.getNome();
	}
	public PortaDelegata getPortaDelegata(Connection connectionPdD,IDPortaDelegata idPD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(idPD==null)
			throw new DriverConfigurazioneException("[getPortaDelegata]: Parametro non definito (idPD)");
		if(idPD.getNome()==null)
			throw new DriverConfigurazioneException("[getPortaDelegata]: Parametro non definito (nome)");

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getPortaDelegata(idPD);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((PortaDelegata) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		PortaDelegata pd = null;
		if(this.cache!=null){
			pd = (PortaDelegata) this.getObjectCache(key,"getPortaDelegata",connectionPdD,idPD);
		}else{
			pd = (PortaDelegata) this.getObject("getPortaDelegata",connectionPdD,idPD);
		}

		if(pd!=null)
			return pd;
		else
			throw new DriverConfigurazioneNotFound("Porta Delegata ["+idPD.getNome()+"] non esistente");
	} 
	
	
	
	
	// PORTA APPLICATIVA
	
	private String _getKey_getIDPortaApplicativa(String nome){
		 return "getIDPortaApplicativa_" + nome;
	}
	public IDPortaApplicativa getIDPortaApplicativa(Connection connectionPdD,String nome) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(nome==null)
			throw new DriverConfigurazioneException("[getIDPortaApplicativa]: Parametro non definito (nome)");

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getIDPortaApplicativa(nome);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((IDPortaApplicativa) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		IDPortaApplicativa idPA = null;
		if(this.cache!=null){
			idPA = (IDPortaApplicativa) this.getObjectCache(key,"getIDPortaApplicativa",connectionPdD,nome);
		}else{
			idPA = (IDPortaApplicativa) this.getObject("getIDPortaApplicativa",connectionPdD,nome);
		}

		if(idPA!=null)
			return idPA;
		else
			throw new DriverConfigurazioneNotFound("Porta Applicativa ["+nome+"] non esistente");
	} 
	
	private String _getKey_getPortaApplicativa(IDPortaApplicativa idPA){
		 return "getPortaApplicativa_" + idPA.getNome();
	}
	public PortaApplicativa getPortaApplicativa(Connection connectionPdD,IDPortaApplicativa idPA) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(idPA == null)
			throw new DriverConfigurazioneException("[getPortaApplicativa]: Parametro non definito (idPA is null)");
		if(idPA.getNome()==null)
			throw new DriverConfigurazioneException("[getPortaApplicativa]: Parametro non definito (idPA.getNome() is null)");

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getPortaApplicativa(idPA);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					PortaApplicativa pa = (PortaApplicativa) response.getObject();
					if(pa!=null){
						return pa;
					}
				}
			}
		}

		// Algoritmo CACHE
		PortaApplicativa pa = null;
		if(this.cache!=null){
			pa = (PortaApplicativa) this.getObjectCache(key,"getPortaApplicativa",connectionPdD,idPA);
		}else{
			pa = (PortaApplicativa) this.getObject("getPortaApplicativa",connectionPdD,idPA);
		}

		if(pa!=null){
			return pa;
		}
		else{
			throw new DriverConfigurazioneNotFound("Porta Applicativa ["+idPA.getNome()+"] non esistente");
		}
	} 
	
	private String _getKey_getPorteApplicative(IDServizio idServizio, boolean ricercaPuntuale){
		String key = "getPorteApplicative_ricercaPuntuale("+ricercaPuntuale+")_" + 
				idServizio.getSoggettoErogatore().getTipo()+idServizio.getSoggettoErogatore().getNome() + "_" + 
				idServizio.getTipo() + idServizio.getNome()+ ":" + idServizio.getVersione();
		if(idServizio.getAzione()!=null)
			key = key + "_" + idServizio.getAzione();
		return key;
	}
	@SuppressWarnings("unchecked")
	public List<PortaApplicativa> getPorteApplicative(Connection connectionPdD,IDServizio idServizio, boolean ricercaPuntuale) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(idServizio == null || idServizio.getNome()==null || idServizio.getTipo()==null || idServizio.getVersione()==null ||
				idServizio.getSoggettoErogatore()==null || idServizio.getSoggettoErogatore().getTipo()==null || idServizio.getSoggettoErogatore().getNome()==null)
			throw new DriverConfigurazioneException("[getPorteApplicative]: Parametri non definito");	
		

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getPorteApplicative(idServizio, ricercaPuntuale);
			
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					List<PortaApplicativa> list = (List<PortaApplicativa>) response.getObject();
					if(list!=null){
						return list;
					}
				}
			}
		}

		Class<?> [] classArgoments = {IDServizio.class,boolean.class};
		Object [] values = {idServizio,ricercaPuntuale};
		
		// Algoritmo CACHE
		List<PortaApplicativa> list = null;
		if(this.cache!=null){
			list = (List<PortaApplicativa>) this.getObjectCache(key,"getPorteApplicative",connectionPdD,classArgoments,values);
		}else{
			list = (List<PortaApplicativa>) this.getObject("getPorteApplicative",connectionPdD,classArgoments,values);
		}

		if(list!=null){
			return list;
		}
		else{
			throw new DriverConfigurazioneNotFound("Porte Applicative non trovate");
		}
	} 
	
	private String _getKey_getPorteApplicativeVirtuali(IDSoggetto soggettoVirtuale,IDServizio idServizio, boolean ricercaPuntuale){
		String key = "getPorteApplicativeVirtuali_ricercaPuntuale("+ricercaPuntuale+")_" + 
				soggettoVirtuale.getTipo()+soggettoVirtuale.getNome()+"_"+
				idServizio.getSoggettoErogatore().getTipo()+idServizio.getSoggettoErogatore().getNome() + "_" + 
				idServizio.getTipo() + idServizio.getNome()+":"+idServizio.getVersione();
		if(idServizio.getAzione()!=null)
			key = key + "_" + idServizio.getAzione();
		return key;
	}
	@SuppressWarnings("unchecked")
	public List<PortaApplicativa> getPorteApplicativeVirtuali(Connection connectionPdD,IDSoggetto soggettoVirtuale,IDServizio idServizio, boolean ricercaPuntuale) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(soggettoVirtuale==null || soggettoVirtuale.getTipo()==null || soggettoVirtuale.getNome()==null){
			throw new DriverConfigurazioneException("[getPorteApplicative]: Parametri (SoggettoVirtuale) non definito");	
		}
		if(idServizio == null || idServizio.getNome()==null || idServizio.getTipo()==null || idServizio.getVersione()==null ||
				idServizio.getSoggettoErogatore()==null || idServizio.getSoggettoErogatore().getTipo()==null || idServizio.getSoggettoErogatore().getNome()==null)
			throw new DriverConfigurazioneException("[getPorteApplicative]: Parametri (IDServizio) non definito");	
		

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getPorteApplicativeVirtuali(soggettoVirtuale, idServizio, ricercaPuntuale);
			
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					List<PortaApplicativa> list = (List<PortaApplicativa>) response.getObject();
					if(list!=null){
						return list;
					}
				}
			}
		}

		Class<?> [] classArgoments = {IDSoggetto.class,IDServizio.class,boolean.class};
		Object [] values = {soggettoVirtuale,idServizio,ricercaPuntuale};
		
		// Algoritmo CACHE
		List<PortaApplicativa> list = null;
		if(this.cache!=null){
			list = (List<PortaApplicativa>) this.getObjectCache(key,"getPorteApplicativeVirtuali",connectionPdD,classArgoments,values);
		}else{
			list = (List<PortaApplicativa>) this.getObject("getPorteApplicativeVirtuali",connectionPdD,classArgoments,values);
		}

		if(list!=null){
			return list;
		}
		else{
			throw new DriverConfigurazioneNotFound("Porte Applicative non trovate");
		}
	} 
	
	private String _getKey_getPorteApplicative_SoggettiVirtuali(IDServizio idServizio){
		String key = "getPorteApplicative_SoggettiVirtuali_" + idServizio.getSoggettoErogatore().getTipo()+idServizio.getSoggettoErogatore().getNome() + "_" + 
				idServizio.getTipo() + idServizio.getNome()+":"+idServizio.getVersione();
		if(idServizio.getAzione()!=null)
			key = key + "_" + idServizio.getAzione();
		return key;
	}
	@SuppressWarnings(value = "unchecked")
	public Map<IDSoggetto,PortaApplicativa> getPorteApplicative_SoggettiVirtuali(Connection connectionPdD,IDServizio idServizio,
			Map<String, String> proprietaPresentiBustaRicevuta,boolean useFiltroProprieta)throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(idServizio == null || idServizio.getNome()==null || idServizio.getTipo()==null || idServizio.getVersione()==null ||
				idServizio.getSoggettoErogatore()==null || idServizio.getSoggettoErogatore().getTipo()==null || idServizio.getSoggettoErogatore().getNome()==null)
			throw new DriverConfigurazioneException("[getPorteApplicative_SoggettiVirtuali]: Parametro non definito");	

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			this.log.debug("Search porte applicative soggetti virtuali in cache...");
			key = this._getKey_getPorteApplicative_SoggettiVirtuali(idServizio);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					if(response.getObject()!=null){
						this.log.debug("Oggetto in cache trovato. Analizzo porte virtuali trovate rispetto alle proprieta' presenti nella busta arrivata: "+this.toStringFiltriProprieta(proprietaPresentiBustaRicevuta));
						Map<IDSoggetto,PortaApplicativa> pa = (Map<IDSoggetto,PortaApplicativa>) response.getObject();
						Map<IDSoggetto,PortaApplicativa> paChecked = new java.util.Hashtable<IDSoggetto,PortaApplicativa>();
						Iterator<IDSoggetto> it = pa.keySet().iterator();
						while (it.hasNext()) {
							IDSoggetto idS = (IDSoggetto) it.next();
							PortaApplicativa paC = pa.get(idS);
							this.log.debug("Analizzo pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]...");
							if(useFiltroProprieta){
								boolean ricezioneAutorizzata = checkProprietaFiltroPortaApplicativa(paC,proprietaPresentiBustaRicevuta);
								if(ricezioneAutorizzata){
									this.log.debug("Filtri matchano le protocol properties della pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
									paChecked.put(idS,paC);
								}else{
									this.log.debug("Filtri non matchano le protocol properties della pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
								}
							}else{
								this.log.debug("Invocazione metodo senza la richiesta di filtro per proprieta, aggiunta pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
								paChecked.put(idS,paC);
							}
						}
						if(paChecked.size()>0)
							return paChecked;
					}
				}
			}
		}

		// Algoritmo CACHE
		Map<IDSoggetto,PortaApplicativa> pa = null;
		if(this.cache!=null){
			pa = (Map<IDSoggetto,PortaApplicativa>) this.getObjectCache(key,"getPorteApplicative_SoggettiVirtuali",connectionPdD,idServizio);
		}else{
			pa = (Map<IDSoggetto,PortaApplicativa>) this.getObject("getPorteApplicative_SoggettiVirtuali",connectionPdD,idServizio);
		}

		if(pa!=null){
			this.log.debug("Oggetto trovato. Analizzo porte virtuali trovate rispetto alle proprieta' presenti nella busta arrivata: "+this.toStringFiltriProprieta(proprietaPresentiBustaRicevuta));
			Map<IDSoggetto,PortaApplicativa> paChecked = new java.util.Hashtable<IDSoggetto,PortaApplicativa>();
			Iterator<IDSoggetto> it = pa.keySet().iterator();
			while (it.hasNext()) {
				IDSoggetto idS = (IDSoggetto) it.next();
				PortaApplicativa paC = pa.get(idS);
				this.log.debug("Analizzo pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]...");
				if(useFiltroProprieta){
					boolean ricezioneAutorizzata = checkProprietaFiltroPortaApplicativa(paC,proprietaPresentiBustaRicevuta);
					if(ricezioneAutorizzata){
						this.log.debug("Filtri matchano le protocol properties della pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
						paChecked.put(idS,paC);
					}else{
						this.log.debug("Filtri non matchano le protocol properties della pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
					}
				}else{
					this.log.debug("Invocazione metodo senza la richiesta di filtro per proprieta, aggiunta pa ["+paC.getNome()+"] del soggetto ["+idS.toString()+"]");
					paChecked.put(idS,paC);
				}
			}
			if(paChecked.size()>0)
				return paChecked;
		}

		throw new DriverConfigurazioneNotFound("[getPorteApplicative_SoggettiVirtuali] PA_SoggettiVirtuali non trovati");
	} 
	
	private String toStringFiltriProprieta(Map<String, String> filtroProprietaPorteApplicative){
		StringBuffer bf = new StringBuffer();
		if(filtroProprietaPorteApplicative==null || filtroProprietaPorteApplicative.size()<=0){
			bf.append("Non presenti");
		}else{
			Iterator<String> it = filtroProprietaPorteApplicative.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				bf.append("\n");
				bf.append(key+" = "+filtroProprietaPorteApplicative.get(key));
			}
		}
		return bf.toString();
	}
	private boolean checkProprietaFiltroPortaApplicativa(PortaApplicativa pa,Map<String, String> proprietaPresentiBustaRicevuta){
		boolean filtriPAPresenti = false;
		//System.out.println("Entro.... PROPRIETA' PRESENTI["+pa.sizeSetProtocolPropertyList()+"] JMS["+filtroProprietaPorteApplicative.size()+"]");

		/*Enumeration<String> en = filtroProprietaPorteApplicative.keys();
		while (en.hasMoreElements()){
			String key = en.nextElement();
			String value = filtroProprietaPorteApplicative.get(key);
			System.out.println("Proprieta' JMS: ["+key+"]=["+value+"]");
		}*/

		if(proprietaPresentiBustaRicevuta!=null && (proprietaPresentiBustaRicevuta.size()>0) ){

			for(int i=0; i<pa.sizeProprietaIntegrazioneProtocolloList(); i++){
				String nome = pa.getProprietaIntegrazioneProtocollo(i).getNome();
				String value = pa.getProprietaIntegrazioneProtocollo(i).getValore();
				this.log.debug("ProprietaProtocollo della PA["+pa.getNome()+"] nome["+nome+"] valore["+value+"]");

				//System.out.println("Esamino ["+nome+"]["+value+"]");
				if(value.indexOf("!#!")!=-1){
					//System.out.println("FILTRIPAPresenti");
					filtriPAPresenti = true;
					String [] split = value.split("!#!");
					if(split!=null && split.length==2){
						// operator come filtro
						// I filtri nelle Protocol Properties sono in OR
						//System.out.println("prelevo proprrieta...");

						String operatoreP = split[1].trim();
						String valoreP = split[0].trim();

						String proprietaArrivata = proprietaPresentiBustaRicevuta.get(nome);

						this.log.debug("ProtocolProperty della PA["+pa.getNome()+"] nome["+nome+"] valore["+value+"] interpretata come ["+nome+"]["+operatoreP+"]["+valoreP+"]. Viene utilizzata per la proprieta' della busta con valore ["+proprietaArrivata+"]");

						if(proprietaArrivata!=null){

							proprietaArrivata = proprietaArrivata.trim();
							//System.out.println("NOMEP["+nome+"] OPERATORE["+operatoreP+"] PROPRIETA' ARRIVATA["+proprietaArrivata+"] VALORE["+split[0].trim()+"]");

							if("=".equals(operatoreP)){
								if(proprietaArrivata.equals(valoreP)){
									return true;
								}
							}else if(">".equals(operatoreP)){
								if(proprietaArrivata.compareTo(valoreP)>0){
									return true;
								}
							}else if(">=".equals(operatoreP)){
								if(proprietaArrivata.compareTo(valoreP)>=0){
									return true;
								}
							}else if("<".equals(operatoreP)){
								if(proprietaArrivata.compareTo(valoreP)<0){
									return true;
								}
							}else if("<=".equals(operatoreP)){
								if(proprietaArrivata.compareTo(valoreP)<=0){
									return true;
								}
							}else if("<>".equals(operatoreP)){
								if(!proprietaArrivata.equals(valoreP)){
									return true;
								}
							}else if("like".equalsIgnoreCase(operatoreP)){
								String valoreMatch = valoreP.replaceAll("%", "");
								int indexExpr = proprietaArrivata.indexOf(valoreMatch);
								if(indexExpr>=0){
									return true;
								}
							}
							else{
								this.log.error("[checkProprietaFiltroPortaApplicativa] Operator ["+operatoreP+"] non supportato per le protocol properties...");
							}
						}
					}
				}
			}

		}else{

			// Proprieta' non presenti nella busta ricevuta
			// Controllo se la PA richiedeva un filtro per protocol properties.
			for(int i=0; i<pa.sizeProprietaIntegrazioneProtocolloList(); i++){
				String nome = pa.getProprietaIntegrazioneProtocollo(i).getNome();
				String value = pa.getProprietaIntegrazioneProtocollo(i).getValore();
				this.log.debug("ProtocolProperty della PA["+pa.getNome()+"] nome["+nome+"] valore["+value+"]");

				//System.out.println("Esamino ["+nome+"]["+value+"]");
				if(value.indexOf("!#!")!=-1){
					//System.out.println("FILTRIPAPresenti");
					this.log.debug("ProtocolProperty della PA["+pa.getNome()+"] nome["+nome+"] valore["+value+"] contiene una richiesta di filtro, siccome la busta arrivata non contiene proprieta', la PA non ha diritto a riceverla.");
					filtriPAPresenti = true;
					break;
				}
			}
		}


		//System.out.println("FILTRI PA["+filtriPAPresenti+"]");
		if(filtriPAPresenti)
			return false;
		else
			return true;
	}
 
	 
	 
	
	
	// SERVIZIO APPLICATIVO
	
	private String _getKey_getServizioApplicativo(IDServizioApplicativo idServizioApplicativo){
		String key = "getServizioApplicativo_" + idServizioApplicativo.getIdSoggettoProprietario().getTipo() + idServizioApplicativo.getIdSoggettoProprietario().getNome()+"_"+
				idServizioApplicativo.getNome();
		return key;
	}
	public ServizioApplicativo getServizioApplicativo(Connection connectionPdD,IDServizioApplicativo idServizioApplicativo)throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(idServizioApplicativo == null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (idServizioApplicativo)");	
		if(idServizioApplicativo.getNome()==null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (nome)");
		if(idServizioApplicativo.getIdSoggettoProprietario()==null || idServizioApplicativo.getIdSoggettoProprietario().getTipo()==null || idServizioApplicativo.getIdSoggettoProprietario().getNome()==null )
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (soggetto proprietario)");
		
		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getServizioApplicativo(idServizioApplicativo);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((ServizioApplicativo) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		ServizioApplicativo s = null;
		if(this.cache!=null){
			s = (ServizioApplicativo) this.getObjectCache(key,"getServizioApplicativo",connectionPdD,idServizioApplicativo);
		}else{
			s = (ServizioApplicativo) this.getObject("getServizioApplicativo",connectionPdD,idServizioApplicativo);
		}

		if(s!=null)
			return s;
		else
			throw new DriverConfigurazioneNotFound("Servizio Applicativo non trovato");
	} 
	
	private String _getKey_getServizioApplicativoByCredenzialiBasic(String aUser,String aPassword){
		String key = "getServizioApplicativoByCredenzialiBasic";
		key = key +"_"+aUser+"_"+aPassword;
		return key;
	}
	public ServizioApplicativo getServizioApplicativoByCredenzialiBasic(Connection connectionPdD,String aUser,String aPassword)throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(aUser == null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (username)");	
		if(aPassword == null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (password)");	
		
		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getServizioApplicativoByCredenzialiBasic(aUser, aPassword);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((ServizioApplicativo) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		ServizioApplicativo s = null;
		if(this.cache!=null){
			s = (ServizioApplicativo) this.getObjectCache(key,"getServizioApplicativoByCredenzialiBasic",connectionPdD,aUser,aPassword);
		}else{
			s = (ServizioApplicativo) this.getObject("getServizioApplicativoByCredenzialiBasic",connectionPdD,aUser,aPassword);
		}

		if(s!=null)
			return s;
		else
			throw new DriverConfigurazioneNotFound("Servizio Applicativo non trovato");
	} 
	
	private String _getKey_getServizioApplicativoByCredenzialiSsl(String aSubject){
		String key = "getServizioApplicativoByCredenzialiSsl";
		key = key +"_"+aSubject;
		return key;
	}
	public ServizioApplicativo getServizioApplicativoByCredenzialiSsl(Connection connectionPdD,String aSubject)throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(aSubject == null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (subject)");		
		
		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getServizioApplicativoByCredenzialiSsl(aSubject);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((ServizioApplicativo) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		ServizioApplicativo s = null;
		if(this.cache!=null){
			s = (ServizioApplicativo) this.getObjectCache(key,"getServizioApplicativoByCredenzialiSsl",connectionPdD,aSubject);
		}else{
			s = (ServizioApplicativo) this.getObject("getServizioApplicativoByCredenzialiSsl",connectionPdD,aSubject);
		}

		if(s!=null)
			return s;
		else
			throw new DriverConfigurazioneNotFound("Servizio Applicativo non trovato");
	} 
	
	private String _getKey_getServizioApplicativoByCredenzialiPrincipal(String principal){
		String key = "getServizioApplicativoByCredenzialiPrincipal";
		key = key +"_"+principal;
		return key;
	}
	public ServizioApplicativo getServizioApplicativoByCredenzialiPrincipal(Connection connectionPdD,String principal)throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Raccolta dati
		if(principal == null)
			throw new DriverConfigurazioneException("[getServizioApplicativo]: Parametro non definito (principal)");		
		
		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getServizioApplicativoByCredenzialiPrincipal(principal);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((ServizioApplicativo) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		ServizioApplicativo s = null;
		if(this.cache!=null){
			s = (ServizioApplicativo) this.getObjectCache(key,"getServizioApplicativoByCredenzialiPrincipal",connectionPdD,principal);
		}else{
			s = (ServizioApplicativo) this.getObject("getServizioApplicativoByCredenzialiPrincipal",connectionPdD,principal);
		}

		if(s!=null)
			return s;
		else
			throw new DriverConfigurazioneNotFound("Servizio Applicativo non trovato");
	}
	

	


	
	// CONFIGURAZIONE

	private String _getKey_getRoutingTable(){
		return "getRoutingTable";
	}
	public RoutingTable getRoutingTable(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.routingTable!=null)
				return ConfigurazionePdD.routingTable;
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getRoutingTable();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((RoutingTable) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		RoutingTable r = null;
		if(this.cache!=null){
			r = (RoutingTable) this.getObjectCache(key,"getRoutingTable",connectionPdD);
		}else{
			r = (RoutingTable) this.getObject("getRoutingTable",connectionPdD);
		}

		if(r!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.routingTable= r;
			}
			return r;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getRoutingTable] RoutingTable non trovata");
		}
	} 

	private String _getKey_getAccessoRegistro(){
		return "getAccessoRegistro";
	}
	public AccessoRegistro getAccessoRegistro(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.accessoRegistro!=null)
				return ConfigurazionePdD.accessoRegistro;
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = _getKey_getAccessoRegistro();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((AccessoRegistro) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		AccessoRegistro ar = null;
		if(this.cache!=null){
			ar = (AccessoRegistro) this.getObjectCache(key,"getAccessoRegistro",connectionPdD);
		}else{
			ar = (AccessoRegistro) this.getObject("getAccessoRegistro",connectionPdD);
		}

		if(ar!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.accessoRegistro = ar;
			}
			return ar;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getAccessoRegistro] Configurazione di accesso ai registri non trovata");
		}
	} 

	private String _getKey_getAccessoConfigurazione(){
		return "getAccessoConfigurazione";
	}
	public AccessoConfigurazione getAccessoConfigurazione(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.accessoConfigurazione!=null)
				return ConfigurazionePdD.accessoConfigurazione;
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getAccessoConfigurazione();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((AccessoConfigurazione) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		AccessoConfigurazione object = null;
		if(this.cache!=null){
			object = (AccessoConfigurazione) this.getObjectCache(key,"getAccessoConfigurazione",connectionPdD);
		}else{
			object = (AccessoConfigurazione) this.getObject("getAccessoConfigurazione",connectionPdD);
		}

		if(object!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.accessoConfigurazione = object;
			}
			return object;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getAccessoConfigurazione] Configurazione di accesso alla configurazione non trovata");
		}
	} 

	private String _getKey_getAccessoDatiAutorizzazione(){
		return "getAccessoDatiAutorizzazione";
	}
	public AccessoDatiAutorizzazione getAccessoDatiAutorizzazione(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.accessoDatiAutorizzazione!=null)
				return ConfigurazionePdD.accessoDatiAutorizzazione;
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getAccessoDatiAutorizzazione();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((AccessoDatiAutorizzazione) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		AccessoDatiAutorizzazione object = null;
		if(this.cache!=null){
			object = (AccessoDatiAutorizzazione) this.getObjectCache(key,"getAccessoDatiAutorizzazione",connectionPdD);
		}else{
			object = (AccessoDatiAutorizzazione) this.getObject("getAccessoDatiAutorizzazione",connectionPdD);
		}

		if(object!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.accessoDatiAutorizzazione = object;
			}
			return object;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getAccessoDatiAutorizzazione] Configurazione di accesso ai dati di autorizzazione non trovata");
		}
	} 
	
	private String _getKey_getAccessoDatiAutenticazione(){
		return "getAccessoDatiAutenticazione";
	}
	public AccessoDatiAutenticazione getAccessoDatiAutenticazione(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		
		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.accessoDatiAutenticazione!=null)
				return ConfigurazionePdD.accessoDatiAutenticazione;
		}
		
		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = _getKey_getAccessoDatiAutenticazione();
			org.openspcoop2.utils.cache.CacheResponse response = 
				(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((AccessoDatiAutenticazione) response.getObject());
				}
			}
		}
			
		// Algoritmo CACHE
		AccessoDatiAutenticazione object = null;
		if(this.cache!=null){
			object = (AccessoDatiAutenticazione) this.getObjectCache(key,"getAccessoDatiAutenticazione",connectionPdD);
		}else{
			object = (AccessoDatiAutenticazione) this.getObject("getAccessoDatiAutenticazione",connectionPdD);
		}
		
		if(object!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.accessoDatiAutenticazione = object;
			}
			return object;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getAccessoDatiAutenticazione] Configurazione di accesso ai dati di autenticazione non trovata");
		}
	} 



	public GestioneErrore getGestioneErroreComponenteCooperazione(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		return this.getGestioneErrore(connectionPdD,true);
	}

	public GestioneErrore getGestioneErroreComponenteIntegrazione(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		return this.getGestioneErrore(connectionPdD,false);
	}

	private String _getKey_getGestioneErrore(boolean cooperazione){
		String key = "getGestioneErrore";
		if(cooperazione){
			key = key + "ComponenteCooperazione";
		}else{
			key = key + "ComponenteIntegrazione";
		}
		return key;
	}
	private GestioneErrore getGestioneErrore(Connection connectionPdD,boolean cooperazione) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(cooperazione){
				if(ConfigurazionePdD.gestioneErroreComponenteCooperazione!=null)
					return ConfigurazionePdD.gestioneErroreComponenteCooperazione;
			}else{
				if(ConfigurazionePdD.gestioneErroreComponenteIntegrazione!=null)
					return ConfigurazionePdD.gestioneErroreComponenteIntegrazione;
			}
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getGestioneErrore(cooperazione);
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((GestioneErrore) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		GestioneErrore ge = null;
		String nomeMetodo = "getGestioneErrore";
		if(cooperazione){
			nomeMetodo = nomeMetodo + "ComponenteCooperazione";
		}else{
			nomeMetodo = nomeMetodo + "ComponenteIntegrazione";
		}
		if(this.cache!=null){
			try{
				ge = (GestioneErrore) this.getObjectCache(key,nomeMetodo,connectionPdD);
			}catch(DriverConfigurazioneException e){
				String erroreCooperazione= "Configurazione, Algoritmo di Cache fallito: Metodo (getGestioneErroreComponenteCooperazione) ha ritornato un valore null";
				String erroreIntegrazione= "Configurazione, Algoritmo di Cache fallito: Metodo (getGestioneErroreComponenteIntegrazione) ha ritornato un valore null";
				if(e.getMessage()!=null 
						&& !erroreCooperazione.equals(e.getMessage())
						&& !erroreIntegrazione.equals(e.getMessage())){
					throw e;
				}
			}
		}else{
			ge = (GestioneErrore) this.getObject(nomeMetodo,connectionPdD);
		}

		if(ge!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				if(cooperazione)
					ConfigurazionePdD.gestioneErroreComponenteCooperazione = ge;
				else
					ConfigurazionePdD.gestioneErroreComponenteIntegrazione = ge;
			}
			return ge;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getGestioneErrore] GestioneErrore non trovato");
		}
	} 


	private String _getKey_getConfigurazioneGenerale(){
		return "getConfigurazioneGenerale";
	}
	public Configurazione getConfigurazioneGenerale(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// Se e' attiva una configurazione statica, la utilizzo.
		if(this.configurazioneDinamica==false){
			if(ConfigurazionePdD.configurazioneGeneralePdD!=null){
				return ConfigurazionePdD.configurazioneGeneralePdD;
			}
		}

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getConfigurazioneGenerale();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((Configurazione) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		Configurazione c = null;
		if(this.cache!=null){
			c = (Configurazione) this.getObjectCache(key,"getConfigurazioneGenerale",connectionPdD);
		}else{
			c = (Configurazione) this.getObject("getConfigurazioneGenerale",connectionPdD);
		}

		if(c!=null){
			// Se e' attiva una configurazione statica, la utilizzo.
			if(this.configurazioneDinamica==false){
				ConfigurazionePdD.configurazioneGeneralePdD= c;
			}
			return c;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getConfigurazioneGenerale] Configurazione Generale non trovata");
		}
	}


	public StatoServiziPdd getStatoServiziPdD() throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		return this.driverConfigurazionePdD.getStatoServiziPdD();
	}
	public void updateStatoServiziPdD(StatoServiziPdd servizi) throws DriverConfigurazioneException{
		if(this.driverConfigurazionePdD instanceof DriverConfigurazioneDB){
			((DriverConfigurazioneDB)this.driverConfigurazionePdD).updateStatoServiziPdD(servizi);
		}
	} 

	public SystemProperties getSystemPropertiesPdD() throws DriverConfigurazioneException,DriverConfigurazioneNotFound{
		return this.driverConfigurazionePdD.getSystemPropertiesPdD();
	}
	public void updateSystemPropertiesPdD(SystemProperties systemProperties) throws DriverConfigurazioneException{
		if(this.driverConfigurazionePdD instanceof DriverConfigurazioneDB){
			((DriverConfigurazioneDB)this.driverConfigurazionePdD).updateSystemPropertiesPdD(systemProperties);
		}
	}


	private String _getKey_getConfigurazioneWithOnlyExtendedInfo(){
		return "getConfigurazioneWithOnlyExtendedInfo";
	}
	public Configurazione getConfigurazioneWithOnlyExtendedInfo(Connection connectionPdD) throws DriverConfigurazioneException,DriverConfigurazioneNotFound{

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = this._getKey_getConfigurazioneWithOnlyExtendedInfo();
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return ((Configurazione) response.getObject());
				}
			}
		}

		// Algoritmo CACHE
		Configurazione c = null;
		if(this.cache!=null){
			c = (Configurazione) this.getObjectCache(key,"getConfigurazioneWithOnlyExtendedInfo",connectionPdD);
		}else{
			c = (Configurazione) this.getObject("getConfigurazioneWithOnlyExtendedInfo",connectionPdD);
		}

		if(c!=null){
			return c;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getConfigurazioneWithOnlyExtendedInfo] Configurazione Generale non trovata");
		}
	}

	public static final String prefixKey_getSingleExtendedInfoConfigurazione = "getSingleExtendedInfoConfigurazione_";

	public Object getSingleExtendedInfoConfigurazione(String id, Connection connectionPdD) throws DriverConfigurazioneException, DriverConfigurazioneNotFound{

		// se e' attiva una cache provo ad utilizzarla
		String key = null;	
		if(this.cache!=null){
			key = prefixKey_getSingleExtendedInfoConfigurazione+id;
			org.openspcoop2.utils.cache.CacheResponse response = 
					(org.openspcoop2.utils.cache.CacheResponse) this.cache.get(key);
			if(response != null){
				if(response.getException()!=null){
					if(DriverConfigurazioneNotFound.class.getName().equals(response.getException().getClass().getName()))
						throw (DriverConfigurazioneNotFound) response.getException();
					else
						throw (DriverConfigurazioneException) response.getException();
				}else{
					return response.getObject();
				}
			}
		}

		// Algoritmo CACHE
		Object c = null;
		Configurazione configurazioneGenerale = this.getConfigurazioneGenerale(connectionPdD);
		if(this.cache!=null){
			c = this.getObjectCache(key,"getConfigurazioneExtended",connectionPdD, configurazioneGenerale, id);
		}else{
			c = this.getObject("getConfigurazioneExtended",connectionPdD, configurazioneGenerale, id);
		}

		if(c!=null){
			return c;
		}
		else{
			throw new DriverConfigurazioneNotFound("[getSingleExtendedInfoConfigurazione] Configurazione Generale non trovata");
		}

	}

}
