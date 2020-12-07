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

package org.openspcoop2.core.protocolli.trasparente.testsuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.junit.BeforeClass;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.slf4j.Logger;

import com.intuit.karate.FileUtils;

/**
* ConfigLoader
*
* @author Francesco Scarlato (scarlato@link.it)
* @author $Author$
* @version $Rev$, $Date$
*/
public class ConfigLoader {

    private static final String propFileName = "testsuite.properties";
    private static final String trasparenteBundlePath = "src/configurazioni-govway/trasparenteTestBundle.zip";
    
    protected static Properties prop = new Properties();
    static {
        setupProperties();
	}
    
	protected static Logger logRateLimiting = null;
	
	public static Logger getLogger() {
		return logRateLimiting;
	}
	
	public static DbUtils getDbUtils() {
		return dbUtils;
	}
		
	@BeforeClass
	public static void setupLogger()throws Exception {
		logRateLimiting =  LoggerWrapperFactory.getLogger("testsuite.rate_limiting");
	}
	
	protected static DbUtils dbUtils;
	
	@BeforeClass
	public static void setupDbUtils() {
		var dbConfig = Map.of(
				"username", System.getProperty("db_username"),
				"password", System.getProperty("db_password"),
				"url", System.getProperty("db_url"),
				"driverClassName", System.getProperty("db_driverClassName")
			);
		dbUtils = new DbUtils(dbConfig);
	}

    @BeforeClass
    public static void setupProperties() {
    
        try(InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(propFileName);) {
            
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            
            System.setProperty("govway_base_path", prop.getProperty("govway_base_path"));
            System.setProperty("connect_timeout", prop.getProperty("connect_timeout"));
            System.setProperty("read_timeout", prop.getProperty("read_timeout"));
            System.setProperty("db_username", prop.getProperty("db_username"));
            System.setProperty("db_password", prop.getProperty("db_password"));
            System.setProperty("db_url", prop.getProperty("db_url"));
            System.setProperty("db_driverClassName", prop.getProperty("db_driverClassName"));
            System.setProperty("db_sleep_before_read", prop.getProperty("db_sleep_before_read"));
            System.setProperty("jmx_username", prop.getProperty("jmx_cache_username"));
            System.setProperty("jmx_password", prop.getProperty("jmx_cache_password"));
            System.setProperty("rl_check_policy_conditions_retry", prop.getProperty("rl_check_policy_conditions_retry"));
            System.setProperty("rl_check_policy_conditions_delay", prop.getProperty("rl_check_policy_conditions_delay"));
            System.setProperty("soglia_congestione", prop.getProperty("soglia_congestione"));
            System.setProperty("eventi_db_delay", prop.getProperty("eventi_db_delay"));
            System.setProperty("congestion_delay", prop.getProperty("congestion_delay"));
            
            
            
            
            setupLogger();
            Utils.toggleErrorDisclosure(false);

        }catch(Throwable t) {
            throw new RuntimeException(t.getMessage(),t);
        }
    }
    
    public static void main(String [] args) throws Exception {
    	boolean load = true;
    	if(args!=null && args.length>0) {
    		load = Boolean.valueOf(args[0]);
    	}
    	if(load) {
    		prepareConfig();
    	}
    	else {
    		deleteConfig();
    	}
    }
    
    public static void prepareConfig() throws Exception {

        org.slf4j.Logger logger = LoggerWrapperFactory.getLogger("com.intuit.karate");

        String configLoaderPath = prop.getProperty("config_loader_path");
        String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "createOrUpdate.cmd" : "createOrUpdate.sh");
        String trasparenteBundle = new File(trasparenteBundlePath).getAbsolutePath();

        logger.debug("Script path: " + scriptPath);
        logger.debug("Config loader path: " + configLoaderPath);
        logger.debug("trasparente bundle path: " + trasparenteBundle);
        logger.debug("Carico la configurazione su govway...");
        
        org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        scriptInvoker.run(new File(configLoaderPath), trasparenteBundle);

        // Dopo aver caricato lo script, resetto le cache
        String jmx_user = prop.getProperty("jmx_cache_username");
        String jmx_pass = prop.getProperty("jmx_cache_password"); 
        
        String[] govwayCaches = prop.getProperty("jmx_cache_resources").split(",");
        for (String resource : govwayCaches) {
            logger.debug("Resetto cache: " + resource);
            String url = prop.getProperty("govway_base_path") + "/check?methodName=resetCache&resourceName=" + resource;
            org.openspcoop2.utils.transport.http.HttpUtilities.check(url, jmx_user, jmx_pass);
        }

    }

    public static void deleteConfig() throws Exception {
    	
        String configLoaderPath = prop.getProperty("config_loader_path");
        String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "delete.cmd" : "delete.sh");
        String trasparenteBundle = new File("src/configurazioni-govway/trasparenteTestBundle.zip").getAbsolutePath();
        
        org.slf4j.Logger logger = LoggerWrapperFactory.getLogger("com.intuit.karate");
        
        logger.debug("Script path: " + scriptPath);
        logger.debug("Config loader path: " + configLoaderPath);
        logger.debug("trasparente bundle path: " + trasparenteBundle);
        logger.debug("Elimino la configurazione su govway...");
                
        org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        scriptInvoker.run(new File(configLoaderPath), trasparenteBundle);

    }

}
