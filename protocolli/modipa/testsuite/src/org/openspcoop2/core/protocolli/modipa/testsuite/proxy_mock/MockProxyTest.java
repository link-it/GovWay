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
/**
 *
 */
@RunWith(Karate.class)
@KarateOptions(features = "classpath:test/proxy_mock/modipa-test.feature")
public class MockProxyTest {
    
    private static FeatureServer server;
    private static final String propFileName = "mock.properties";
    private Properties prop = new Properties();
    
    @BeforeClass
    public static void beforeClass() throws FileNotFoundException, IOException {       

    	InputStream inputStream = MockProxyTest.class.getClassLoader().getResourceAsStream(propFileName);
 
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }

        String configLoaderPath = prop.getProperty("config_loader_path"); // "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/tools/command_line_interfaces/config_loader/distrib";
        String scriptPath = configLoaderPath + "/" + FileUtils.isOsWindows() ? "createOrUpdate.cmd" : "createOrUpdate.sh";
        String modipa_bundle = new File("configurazioni-govway/modipaTestBundle.zip").getAbsolutePath();

        
        //org.openspcoop2.utils.resources.ScriptInvoker scriptInvoker = new org.openspcoop2.utils.resources.ScriptInvoker(scriptPath);
        //scriptInvoker.run(parametroScriptFrontend);

        File file = FileUtils.getFileRelativeTo(MockProxyTest.class, "modipa-mock.feature");
        server = FeatureServer.start(file, Integer.valueOf(prop.getProperty("http_port")), false, new HashMap<String,Object>((Map) prop));
    }
        
    @AfterClass
    public static void afterClass() {
        server.stop();

        String configLoaderPath = prop.getProperty("config_loader_path"); // "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/tools/command_line_interfaces/config_loader/distrib";
        String scriptPath = configLoaderPath + "/" + FileUtils.isOsWindows() ? "delete.cmd" : "delete.sh";
        String modipa_bundle = new File("configurazioni-govway/modipaTestBundle.zip").getAbsolutePath();
    }     
    
}