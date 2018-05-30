
package org.openspcoop2.core.registry.ws.client.scope.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.registry.IdScope;


/**
 * <p>Java class for exists complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exists"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idScope" type="{http://www.openspcoop2.org/core/registry}id-scope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exists", propOrder = {
    "idScope"
})
public class Exists {

    @XmlElement(required = true)
    protected IdScope idScope;

    /**
     * Gets the value of the idScope property.
     * 
     * @return
     *     possible object is
     *     {@link IdScope }
     *     
     */
    public IdScope getIdScope() {
        return this.idScope;
    }

    /**
     * Sets the value of the idScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdScope }
     *     
     */
    public void setIdScope(IdScope value) {
        this.idScope = value;
    }

}