
package org.openspcoop2.core.registry.ws.client.portadominio.search;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.registry.PortaDominio;


/**
 * <p>Java class for findAllResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findAllResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="portaDominio" type="{http://www.openspcoop2.org/core/registry}porta-dominio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllResponse", propOrder = {
    "portaDominio"
})
public class FindAllResponse {

    protected List<PortaDominio> portaDominio;

    /**
     * Gets the value of the portaDominio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portaDominio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortaDominio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PortaDominio }
     * 
     * 
     */
    public List<PortaDominio> getPortaDominio() {
        if (this.portaDominio == null) {
            this.portaDominio = new ArrayList<PortaDominio>();
        }
        return this.portaDominio;
    }

}
