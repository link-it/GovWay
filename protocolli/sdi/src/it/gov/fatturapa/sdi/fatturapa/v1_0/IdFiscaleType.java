/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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
package it.gov.fatturapa.sdi.fatturapa.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for IdFiscaleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdFiscaleType">
 * 		&lt;sequence>
 * 			&lt;element name="IdPaese" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}string" minOccurs="1" maxOccurs="1"/>
 * 			&lt;element name="IdCodice" type="{http://www.fatturapa.gov.it/sdi/fatturapa/v1.0}string" minOccurs="1" maxOccurs="1"/>
 * 		&lt;/sequence>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdFiscaleType", 
  propOrder = {
  	"idPaese",
  	"idCodice"
  }
)

@XmlRootElement(name = "IdFiscaleType")

public class IdFiscaleType extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IdFiscaleType() {
  }

  public java.lang.String getIdPaese() {
    return this.idPaese;
  }

  public void setIdPaese(java.lang.String idPaese) {
    this.idPaese = idPaese;
  }

  public java.lang.String getIdCodice() {
    return this.idCodice;
  }

  public void setIdCodice(java.lang.String idCodice) {
    this.idCodice = idCodice;
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="IdPaese",required=true,nillable=false)
  protected java.lang.String idPaese;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlElement(name="IdCodice",required=true,nillable=false)
  protected java.lang.String idCodice;

}
