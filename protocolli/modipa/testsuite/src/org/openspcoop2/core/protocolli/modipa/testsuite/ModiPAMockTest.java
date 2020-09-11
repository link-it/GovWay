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
import org.junit.runner.RunWith;
import java.util.Map;

/**
 *
 * @author pthomas3
 */
@RunWith(Karate.class)
@KarateOptions(features = "classpath:test/modipa-test.feature")
public class ModiPAMockTest {
    
    private static FeatureServer server;
    private static Map<String,Object> mockConfig = Map.of(
        "url_invocazione_erogazione", "http://localhost:8080/govway/rest/in/DemoSoggettoErogatore/ApiDemoBlockingRest/v1"
    );
    
    @BeforeClass
    public static void beforeClass() {       
        File file = FileUtils.getFileRelativeTo(ModiPAMockTest.class, "modipa-mock.feature");
        server = FeatureServer.start(file, 8090, false, mockConfig);
        //String paymentServiceUrl = "http://localhost:" + server.getPort();
        //System.setProperty("payment.service.url", paymentServiceUrl);
    }
        
    @AfterClass
    public static void afterClass() {
        server.stop();        
    }     
    
}