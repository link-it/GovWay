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
package org.openspcoop2.monitor.engine.config.statistiche;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openspcoop2.monitor.engine.config.statistiche package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
*/

@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openspcoop2.monitor.engine.config.statistiche
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConfigurazioneServizioAzione }
     */
    public ConfigurazioneServizioAzione createConfigurazioneServizioAzione() {
        return new ConfigurazioneServizioAzione();
    }

    /**
     * Create an instance of {@link IdPlugin }
     */
    public IdPlugin createIdPlugin() {
        return new IdPlugin();
    }

    /**
     * Create an instance of {@link InfoPlugin }
     */
    public InfoPlugin createInfoPlugin() {
        return new InfoPlugin();
    }

    /**
     * Create an instance of {@link IdConfigurazioneServizioAzione }
     */
    public IdConfigurazioneServizioAzione createIdConfigurazioneServizioAzione() {
        return new IdConfigurazioneServizioAzione();
    }

    /**
     * Create an instance of {@link ConfigurazioneStatistica }
     */
    public ConfigurazioneStatistica createConfigurazioneStatistica() {
        return new ConfigurazioneStatistica();
    }

    /**
     * Create an instance of {@link IdConfigurazioneServizio }
     */
    public IdConfigurazioneServizio createIdConfigurazioneServizio() {
        return new IdConfigurazioneServizio();
    }

    /**
     * Create an instance of {@link IdConfigurazioneStatistica }
     */
    public IdConfigurazioneStatistica createIdConfigurazioneStatistica() {
        return new IdConfigurazioneStatistica();
    }

    /**
     * Create an instance of {@link ConfigurazioneServizio }
     */
    public ConfigurazioneServizio createConfigurazioneServizio() {
        return new ConfigurazioneServizio();
    }

    /**
     * Create an instance of {@link Plugin }
     */
    public Plugin createPlugin() {
        return new Plugin();
    }


 }
