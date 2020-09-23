/**
    Tests da fare: 1 - Giro Normale: Richiesta della risorsa. Consultazione
        stato. Ottenimento risorsa.

        Al passo (2), l’erogatore DEVE fornire insieme all’acknowledgement della richiesta, 
        un percorso di risorsa per interrogare lo stato di processamento utilizzando HTTP header Location ;
        Il codice HTTP di stato DEVE essere HTTP status 202 Accepted a meno che non si verifichino errori;

        Il body dell'acknowledgement, lo status code e lo header location sono forniti dal
        server di echo seguendo i parametri query inviati dal client.

        E' il client quindi che crea delle configurazioni sbagliate.

        2 - Testare cosa succede quando il client dice all'echo di restituire uno status code diverso da 202
        3 - Testare l'assenza dello header location in caso di status code 202

        Dove devo testare il fatto che l'erogazione ha segnalato quegli errori?
        Nel client di test o nel mock proxy?

        4 - Quando consulto una risorsa non pronta(è sempre il client a dire al server di echo
            cosa rispondere), restituire uno stato diverso da 200 ok. Testare che l'erogazione si arrabbi.

        5 - Quando consunlto una risorsa e questa è pronta (codice 303) non mettere lo header location e
            testare che l'erogazione si arrabbi.

        6 - Quando recupero una risorsa pronta, testare uno status code diverso da 200 OK e controllare
            che l'erogazione si arrabbi.

    Per testare la fruizione avrò bisogno del mock invece. Siccome

    Il messaggio di acknowledgement ricevuto viene validato al fine di verificare la presenza dell’header http “Location” come previsto dalla specifica “ModI PA”
        7 - Fare rimuovere al proxy lo header Location in caso di richiesta accettata
        8 - Verificare (sul client o sul mock?) che sia presente lo header GovWay-Conversation-ID
        9 - Ripetere i test 1-7 ma questa volta facendo arrabbiare la fruizione?

    
        
        TYPO NELLA DOCUMENTAZIONE: Le risposte vengono gestite da GovWay vengono validate da GovWay verificando che il codice HTTP di stato sia 200.

 */

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

package org.openspcoop2.core.protocolli.modipa.testsuite.rest.non_bloccante;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openspcoop2.core.protocolli.modipa.testsuite.ConfigLoader;

import com.intuit.karate.FileUtils;
import com.intuit.karate.KarateOptions;
import com.intuit.karate.junit4.Karate;
import com.intuit.karate.netty.FeatureServer;

/**
 * ApplicativiTest
 *
 * @author Francesco Scarlato (scarlato@link.it)
 * @version $Rev$, $Date$
 */

@RunWith(Karate.class)
@KarateOptions( features = { 
    "classpath:test/rest/non-bloccante/pull.feature",
    "classpath:test/rest/non-bloccante/pull-errori-fruizione.feature",
    "classpath:test/rest/non-bloccante/pull-errori-erogazione.feature"
    })
public class NonBloccanteRestTest extends ConfigLoader { 
    
    private static FeatureServer server;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeClass
    public static void beforeClass() {       
        File file = FileUtils.getFileRelativeTo(NonBloccanteRestTest.class, "mock-pull.feature");
        server = FeatureServer.start(file, Integer.valueOf(prop.getProperty("http_port")), false, new HashMap<String,Object>((Map) prop));
    }
        
    @AfterClass
    public static void afterClass() {
        server.stop();
    }     

}