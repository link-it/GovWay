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

package org.openspcoop2.core.protocolli.modipa.testsuite;

import com.intuit.karate.FileUtils;
import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;
import com.intuit.karate.netty.FeatureServer;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.LoggerWrapperFactory;

/**
* ConfigLoader
*
* @author Francesco Scarlato (scarlato@link.it)
* @author $Author$
* @version $Rev$, $Date$
*/
public class ConfigLoader {

    private static final String propFileName = "testsuite.properties";
    private static final String modipaBundlePath = "src/configurazioni-govway/modipaTestBundle.zip";
    protected static Properties prop = new Properties();
    
    @BeforeClass
    public static void prepareConfig() throws FileNotFoundException, IOException, UtilsException, Exception {

        try(InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(propFileName);) {
 
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            org.slf4j.Logger logger = LoggerWrapperFactory.getLogger("com.intuit.karate");

            String configLoaderPath = prop.getProperty("config_loader_path");
            String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "createOrUpdate.cmd" : "createOrUpdate.sh");
            String modipaBundle = new File(modipaBundlePath).getAbsolutePath();

            logger.debug("Config loader path: " + scriptPath);
            logger.debug("ModIPA bundle path: " + modipaBundle);
            logger.debug("Carico la configurazione su govway...");
            
            org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
            scriptInvoker.run(new File(configLoaderPath), modipaBundle);

            // Dopo aver caricato lo script, resetto le cache
            String[] govwayCaches = prop.getProperty("jmx_cache_resources").split(",");
            for (String resource : govwayCaches) {
                logger.debug("Resetto cache: " + resource);
                String url = prop.getProperty("govway_base_path") + "/check?methodName=resetCache&resourceName=" + resource;
                org.openspcoop2.utils.transport.http.HttpUtilities.check(
                    url,
                    prop.getProperty("jmx_cache_username"),
                    prop.getProperty("jmx_cache_password")
                );
            }

            System.setProperty("govway_base_path", prop.getProperty("govway_base_path"));
            System.setProperty("connect_timeout", prop.getProperty("connect_timeout"));
            System.setProperty("read_timeout", prop.getProperty("read_timeout"));
        }
    }

    @AfterClass
    public static void deleteConfig() throws UtilsException {
        String configLoaderPath = prop.getProperty("config_loader_path");
        String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "delete.cmd" : "delete.sh");
        String modipaBundle = new File("src/configurazioni-govway/modipaTestBundle.zip").getAbsolutePath();
        org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        scriptInvoker.run(new File(configLoaderPath), modipaBundle);
    }

}