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
// Generated on: 2018.06.20 at 04:38:17 PM CEST 
//


package org.openspcoop2.web.monitor.transazioni.core.manifest;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for diagnostica_serialization_type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="diagnostica_serialization_type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Standard"/&gt;
 *     &lt;enumeration value="Optimized"/&gt;
 *     &lt;enumeration value="OptimizedError"/&gt;
 *     &lt;enumeration value="OptimizedNotFound"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@XmlType(name = "diagnostica_serialization_type", namespace = "http://www.openspcoop2.org/web/monitor/transazioni/core/manifest")
@XmlEnum
public enum DiagnosticaSerializationType {

    @XmlEnumValue("Standard")
    STANDARD("Standard"),
    @XmlEnumValue("Optimized")
    OPTIMIZED("Optimized"),
    @XmlEnumValue("OptimizedError")
    OPTIMIZED_ERROR("OptimizedError"),
    @XmlEnumValue("OptimizedNotFound")
    OPTIMIZED_NOT_FOUND("OptimizedNotFound");
    private final String value;

    DiagnosticaSerializationType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DiagnosticaSerializationType fromValue(String v) {
        for (DiagnosticaSerializationType c: DiagnosticaSerializationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
