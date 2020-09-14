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

package org.openspcoop2.core.protocolli.modipa.testsuite.proxy_mock;

import com.intuit.karate.FileUtils;
import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;
import com.intuit.karate.netty.FeatureServer;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import org.openspcoop2.utils.UtilsException;
/**
 *
 */
@RunWith(Karate.class)
@KarateOptions(features = "classpath:test/proxy_mock/modipa-test.feature")
public class MockProxyTest {
    
    private static FeatureServer server;
    private static final String propFileName = "mock.properties";
    private static Properties prop = new Properties();
    
    @BeforeClass
    public static void beforeClass() throws FileNotFoundException, IOException, UtilsException {       

    	InputStream inputStream = MockProxyTest.class.getClassLoader().getResourceAsStream(propFileName);
 
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        String configLoaderPath = prop.getProperty("config_loader_path");
        String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "createOrUpdate.cmd" : "createOrUpdate.sh");
        System.out.println("Config loader path: " + scriptPath);

        String modipaBundle = new File("src/configurazioni-govway/modipaTestBundle.zip").getAbsolutePath();
        System.out.println("ModIPA bundle path: " + modipaBundle);
        
        org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        scriptInvoker.run(new File(configLoaderPath), modipaBundle);

        File file = FileUtils.getFileRelativeTo(MockProxyTest.class, "modipa-mock.feature");
        server = FeatureServer.start(file, Integer.valueOf(prop.getProperty("http_port")), false, new HashMap<String,Object>((Map) prop));
    }
        
    @AfterClass
    public static void afterClass() throws UtilsException {
        server.stop();

        String configLoaderPath = prop.getProperty("config_loader_path");
        String scriptPath = configLoaderPath + "/" + (FileUtils.isOsWindows() ? "delete.cmd" : "delete.sh");
        String modipaBundle = new File("src/configurazioni-govway/modipaTestBundle.zip").getAbsolutePath();
        org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        scriptInvoker.run(new File(configLoaderPath), modipaBundle);
    }     
    
}