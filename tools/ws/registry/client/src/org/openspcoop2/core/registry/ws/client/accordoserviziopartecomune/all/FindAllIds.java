
package org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for findAllIds complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="findAllIds"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="filter" type="{http://www.openspcoop2.org/core/registry/management}search-filter-accordo-servizio-parte-comune"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findAllIds", propOrder = {
    "filter"
})
public class FindAllIds {

    @XmlElement(required = true)
    protected SearchFilterAccordoServizioParteComune filter;

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link SearchFilterAccordoServizioParteComune }
     *     
     */
    public SearchFilterAccordoServizioParteComune getFilter() {
        return this.filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchFilterAccordoServizioParteComune }
     *     
     */
    public void setFilter(SearchFilterAccordoServizioParteComune value) {
        this.filter = value;
    }

}
