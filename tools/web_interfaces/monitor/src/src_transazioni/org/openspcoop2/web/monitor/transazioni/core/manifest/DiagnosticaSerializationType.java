//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.03 at 04:21:16 PM CEST 
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
 * &lt;simpleType name="diagnostica_serialization_type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Standard"/>
 *     &lt;enumeration value="Optimized"/>
 *     &lt;enumeration value="OptimizedError"/>
 *     &lt;enumeration value="OptimizedNotFound"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "diagnostica_serialization_type", namespace = "http://www.link.it/pdd/monitor/web/pddmonitor/transazioni/core/manifest")
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
