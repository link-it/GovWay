package org.openspcoop2.pdd.timers;

import java.sql.Connection;

import org.openspcoop2.core.commons.dao.DAOFactory;
import org.openspcoop2.core.commons.dao.DAOFactoryProperties;
import org.openspcoop2.generic_project.utils.ServiceManagerProperties;
import org.openspcoop2.pdd.config.DBTransazioniManager;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.config.Resource;
import org.openspcoop2.pdd.core.handlers.HandlerException;
import org.openspcoop2.pdd.core.transazioni.GestoreTransazioniStateful;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.utils.Utilities;
import org.slf4j.Logger;


public class TimerRepositoryStatefulThread extends Thread{

	private static final String ID_MODULO = "TimerRepositoryStateful";
	
	/**
	 * Timeout che definisce la cadenza di avvio di questo timer. 
	 */
	private long timeout = 10; // ogni 10 secondi avvio il Thread
	
	/** Logger utilizzato per debug. */
	private Logger log = null;
	private Logger logSql = null;

	/** Indicazione se deve essere effettuato il log delle query */
	private boolean debug = false;	
		
	/** Database */
	private String tipoDatabaseRuntime = null; //tipoDatabase
	
	private DAOFactory daoFactory = null;
    private ServiceManagerProperties daoFactoryServiceManagerPropertiesTransazioni = null;
    private Logger daoFactoryLoggerTransazioni = null;
    
	/**
	 * OpenSPCoop2Properties resources
	 */
	private OpenSPCoop2Properties openspcoopProperties = null;
	
	/** Gestore */
	private GestoreTransazioniStateful gestore = null;
	
    // VARIABILE PER STOP
	private boolean stop = false;
	
	public boolean isStop() {
		return this.stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
	
	/** Costruttore */
	public TimerRepositoryStatefulThread() throws Exception{
	
		this.openspcoopProperties = OpenSPCoop2Properties.getInstance();
		
		this.timeout = this.openspcoopProperties.getTransazioniStatefulTimerIntervalSeconds();
		
		this.debug = this.openspcoopProperties.isTransazioniStatefulDebug();

		this.log = OpenSPCoop2Logger.getLoggerOpenSPCoopTransazioniStateful(this.debug);
		this.logSql = OpenSPCoop2Logger.getLoggerOpenSPCoopTransazioniStatefulSql(this.debug);
		
		// DB
		try{
			this.tipoDatabaseRuntime = this.openspcoopProperties.getDatabaseType();
			if(this.tipoDatabaseRuntime==null){
				throw new Exception("Tipo Database non definito");
			}

			DAOFactoryProperties daoFactoryProperties = null;
			this.daoFactoryLoggerTransazioni = this.logSql;
			this.daoFactory = DAOFactory.getInstance(this.daoFactoryLoggerTransazioni);
			daoFactoryProperties = DAOFactoryProperties.getInstance(this.daoFactoryLoggerTransazioni);
			this.daoFactoryServiceManagerPropertiesTransazioni = daoFactoryProperties.getServiceManagerProperties(org.openspcoop2.core.transazioni.utils.ProjectInfo.getInstance());
			this.daoFactoryServiceManagerPropertiesTransazioni.setShowSql(this.debug);	

		}catch(Exception e){
			throw new HandlerException("Errore durante l'inizializzazione delle risorse per l'accesso al database: "+e.getMessage(),e);
		}
				
		try{
			this.gestore = new GestoreTransazioniStateful(this.log, this.logSql,
					this.tipoDatabaseRuntime, this.debug);
		}catch(Exception e){
			throw new Exception("Errore durante l'inizializzazione del gestore: "+e.getMessage(),e);
		}
		
		
	}
	
	/**
	 * Metodo che fa partire il Thread. 
	 *
	 */
	@Override
	public void run(){
		
		if(this.gestore==null){
			this.log.error("Gestore non correttamente inizializzato");
			return;
		}
		
		while(this.stop == false){
			
			DBTransazioniManager dbManager = null;
	    	Resource r = null;
			try{
				dbManager = DBTransazioniManager.getInstance();
				r = dbManager.getResource(this.openspcoopProperties.getIdentitaPortaDefault(null), ID_MODULO, null);
				if(r==null){
					throw new Exception("Risorsa al database non disponibile");
				}
				Connection con = (Connection) r.getResource();
				if(con == null)
					throw new Exception("Connessione non disponibile");	
				
				if(this.debug){
					this.log.debug("Esecuzione thread per gestione delle transazioni stateful....");
				}
				
				this.gestore.verificaOggettiPresentiRepository(this.daoFactory,this.daoFactoryServiceManagerPropertiesTransazioni, this.daoFactoryLoggerTransazioni, con);
				if(this.debug){
					this.log.debug("Esecuzione thread per gestione delle transazioni stateful terminata");
				}
				
			}catch(Exception e){
				this.log.error("Errore durante la gestione delle transazioni stateful: "+e.getMessage(),e);
			}finally{
				try{
					if(r!=null)
						dbManager.releaseResource(this.openspcoopProperties.getIdentitaPortaDefault(null), ID_MODULO, r);
				}catch(Exception eClose){}
			}
			
					
			// CheckInterval
			if(this.stop==false){
				int i=0;
				while(i<this.timeout){
					Utilities.sleep(1000);		
					if(this.stop){
						break; // thread terminato, non lo devo far piu' dormire
					}
					i++;
				}
			}
		} 
		
		this.log.info("Thread per la gestione delle transazioni stateful terminato");

	}
}