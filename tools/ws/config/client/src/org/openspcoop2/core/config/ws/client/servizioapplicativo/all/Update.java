/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

package org.openspcoop2.core.config.ws.client.servizioapplicativo.all;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.openspcoop2.core.config.IdServizioApplicativo;
import org.openspcoop2.core.config.ServizioApplicativo;


/**
 * <p>Java class for update complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="update"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="oldIdServizioApplicativo" type="{http://www.openspcoop2.org/core/config}id-servizio-applicativo"/&gt;
 *         &lt;element name="servizioApplicativo" type="{http://www.openspcoop2.org/core/config}servizio-applicativo"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "update", propOrder = {
    "oldIdServizioApplicativo",
    "servizioApplicativo"
})
public class Update {

    @XmlElement(required = true)
    protected IdServizioApplicativo oldIdServizioApplicativo;
    @XmlElement(required = true)
    protected ServizioApplicativo servizioApplicativo;

    /**
     * Gets the value of the oldIdServizioApplicativo property.
     * 
     * @return
     *     possible object is
     *     {@link IdServizioApplicativo }
     *     
     */
    public IdServizioApplicativo getOldIdServizioApplicativo() {
        return this.oldIdServizioApplicativo;
    }

    /**
     * Sets the value of the oldIdServizioApplicativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdServizioApplicativo }
     *     
     */
    public void setOldIdServizioApplicativo(IdServizioApplicativo value) {
        this.oldIdServizioApplicativo = value;
    }

    /**
     * Gets the value of the servizioApplicativo property.
     * 
     * @return
     *     possible object is
     *     {@link ServizioApplicativo }
     *     
     */
    public ServizioApplicativo getServizioApplicativo() {
        return this.servizioApplicativo;
    }

    /**
     * Sets the value of the servizioApplicativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServizioApplicativo }
     *     
     */
    public void setServizioApplicativo(ServizioApplicativo value) {
        this.servizioApplicativo = value;
    }

}
