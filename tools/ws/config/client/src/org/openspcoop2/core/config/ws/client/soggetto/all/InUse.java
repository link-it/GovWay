/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
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

package org.openspcoop2.core.config.ws.client.soggetto.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.config.IdSoggetto;


/**
 * <p>Java class for inUse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inUse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idSoggetto" type="{http://www.openspcoop2.org/core/config}id-soggetto"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inUse", propOrder = {
    "idSoggetto"
})
public class InUse {

    @XmlElement(required = true)
    protected IdSoggetto idSoggetto;

    /**
     * Gets the value of the idSoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link IdSoggetto }
     *     
     */
    public IdSoggetto getIdSoggetto() {
        return this.idSoggetto;
    }

    /**
     * Sets the value of the idSoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdSoggetto }
     *     
     */
    public void setIdSoggetto(IdSoggetto value) {
        this.idSoggetto = value;
    }

}
