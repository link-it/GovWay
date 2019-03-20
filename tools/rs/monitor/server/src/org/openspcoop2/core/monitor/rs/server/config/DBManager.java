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


package org.openspcoop2.core.monitor.rs.server.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.openspcoop2.generic_project.utils.ServiceManagerProperties;
import org.slf4j.Logger;

/**
 * DBManager
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class DBManager {

	/** Logger utilizzato per debug. */
	private static Logger log = null;

	/** DBManager */
	private static DBManager manager = null;

	private static boolean initialized = false;

	private ServiceManagerProperties serviceManagerProperties;
	public ServiceManagerProperties getServiceManagerProperties() {
		return this.serviceManagerProperties;
	}

	/** DataSource dove attingere connessioni */
	private DataSource dataSource = null;

	private String dataSourceName = null;
	private java.util.Properties dataSourceContext = null;
	public String getDataSourceName() {
		return this.dataSourceName;
	}

	public java.util.Properties getDataSourceContext() {
		return this.dataSourceContext;
	}
	
	/**
	 * Viene chiamato in causa per istanziare il datasource
	 * 
	 * @param jndiName
	 *            Nome JNDI del Datasource
	 * @param context
	 *            Contesto JNDI da utilizzare
	 */
	private DBManager(String jndiName, java.util.Properties context, 
			String tipoDB, boolean debug) throws Exception {
		try {
			DBManager.log = LoggerProperties.getLoggerCore();
			this.dataSourceName = jndiName;
			this.dataSourceContext = context;
			
			if (context != null) {
				DBManager.log.info("Proprieta' di contesto:" + context.size());
				Enumeration<?> en = context.keys();
				while (en.hasMoreElements()) {
					String key = (String) en.nextElement();
					DBManager.log.info("\tNome[" + key + "] Valore[" + context.getProperty(key) + "]");
				}
			} else {
				DBManager.log.info("Proprieta' di contesto non fornite");
			}

			DBManager.log.info("Nome dataSource:" + jndiName);

			InitialContext initC = null;
			if (context != null && context.size() > 0)
				initC = new InitialContext(context);
			else
				initC = new InitialContext();
			this.dataSource = (DataSource) initC.lookup(jndiName);
			initC.close();
			
			this.serviceManagerProperties = new ServiceManagerProperties();
			this.serviceManagerProperties.setDatabaseType(tipoDB);
			this.serviceManagerProperties.setShowSql(debug);

		} catch (Exception e) {
			DBManager.log.error("Lookup datasource non riuscita", e);
			throw e;
		}
	}

	/**
	 * Il Metodo si occupa di inizializzare il propertiesReader del QueueManager
	 * 
	 * @param jndiName
	 *            Nome JNDI del Datasource
	 * @param context
	 *            Contesto JNDI da utilizzare
	 */
	public static boolean initialize(String jndiName, java.util.Properties context, 
			String tipoDB, boolean debug) throws Exception {
		try {
			if (DBManager.manager == null) {
				DBManager.manager = new DBManager(jndiName, context, tipoDB, debug);
			}
			DBManager.setInitialized(true);
			return true;
		} catch (Exception e) {
			DBManager.setInitialized(false);
			DBManager.log.debug("Errore di inizializzazione del Database", e);
			throw e;
		}
	}

	/**
	 * Ritorna l'istanza di questo DBManager
	 * 
	 * @return Istanza di DBManager
	 */
	public static DBManager getInstance() {
		return DBManager.manager;
	}

	/**
	 * Viene chiamato in causa per ottenere una connessione al DB
	 */
	public java.sql.Connection getConnection() {
		if (this.dataSource == null) {
			return null;
		}

		Connection connectionDB = null;
		try {
			connectionDB = this.dataSource.getConnection();
		} catch (Exception e) {
			DBManager.log.error("getConnection from db", e);
			return null;
		}

		return connectionDB;
	}

	/**
	 * Viene chiamato in causa per rilasciare una connessione al DB, effettuando
	 * precedentemente un commit
	 * 
	 * @param connectionDB
	 *            Connessione da rilasciare.
	 */
	public void releaseConnection(java.sql.Connection connectionDB) {
		try {
			if (connectionDB != null) {
				connectionDB.close();
			}
		} catch (SQLException e) {
			DBManager.log.error("closeConnection db", e);
		}
	}

	public static boolean isInitialized() {
		return DBManager.initialized;
	}

	private static void setInitialized(boolean initialized) {
		DBManager.initialized = initialized;
	}
}
