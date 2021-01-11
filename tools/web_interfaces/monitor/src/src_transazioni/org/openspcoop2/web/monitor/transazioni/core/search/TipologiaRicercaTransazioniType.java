/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it).
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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.06.20 at 04:38:18 PM CEST 
//


package org.openspcoop2.web.monitor.transazioni.core.search;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tipologia-ricerca-transazioni_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tipologia-ricerca-transazioni_type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Erogazione/Fruizione"/&gt;
 *     &lt;enumeration value="Erogazione"/&gt;
 *     &lt;enumeration value="Fruizione"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@XmlType(name = "tipologia-ricerca-transazioni_type", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/search")
@XmlEnum
public enum TipologiaRicercaTransazioniType {

    @XmlEnumValue("Erogazione/Fruizione")
    EROGAZIONE_FRUIZIONE("Erogazione/Fruizione"),
    @XmlEnumValue("Erogazione")
    EROGAZIONE("Erogazione"),
    @XmlEnumValue("Fruizione")
    FRUIZIONE("Fruizione");
    private final String value;

    TipologiaRicercaTransazioniType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TipologiaRicercaTransazioniType fromValue(String v) {
        for (TipologiaRicercaTransazioniType c: TipologiaRicercaTransazioniType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
