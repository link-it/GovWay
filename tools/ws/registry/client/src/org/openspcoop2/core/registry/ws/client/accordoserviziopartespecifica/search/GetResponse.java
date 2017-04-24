
package org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;


/**
 * <p>Java class for getResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="accordoServizioParteSpecifica" type="{http://www.openspcoop2.org/core/registry}accordo-servizio-parte-specifica"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResponse", propOrder = {
    "accordoServizioParteSpecifica"
})
public class GetResponse {

    @XmlElement(required = true)
    protected AccordoServizioParteSpecifica accordoServizioParteSpecifica;

    /**
     * Gets the value of the accordoServizioParteSpecifica property.
     * 
     * @return
     *     possible object is
     *     {@link AccordoServizioParteSpecifica }
     *     
     */
    public AccordoServizioParteSpecifica getAccordoServizioParteSpecifica() {
        return this.accordoServizioParteSpecifica;
    }

    /**
     * Sets the value of the accordoServizioParteSpecifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccordoServizioParteSpecifica }
     *     
     */
    public void setAccordoServizioParteSpecifica(AccordoServizioParteSpecifica value) {
        this.accordoServizioParteSpecifica = value;
    }

}
