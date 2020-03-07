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
package it.cnipa.schemas._2003.egovit.exception1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for EccezioneProcessamento complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EccezioneProcessamento"&gt;
 * 		&lt;attribute name="codiceEccezione" type="{http://www.w3.org/2001/XMLSchema}string" use="required"/&gt;
 * 		&lt;attribute name="descrizioneEccezione" type="{http://www.w3.org/2001/XMLSchema}string" use="optional"/&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EccezioneProcessamento")

@XmlRootElement(name = "EccezioneProcessamento")

public class EccezioneProcessamento extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public EccezioneProcessamento() {
  }

  public java.lang.String getCodiceEccezione() {
    return this.codiceEccezione;
  }

  public void setCodiceEccezione(java.lang.String codiceEccezione) {
    this.codiceEccezione = codiceEccezione;
  }

  public java.lang.String getDescrizioneEccezione() {
    return this.descrizioneEccezione;
  }

  public void setDescrizioneEccezione(java.lang.String descrizioneEccezione) {
    this.descrizioneEccezione = descrizioneEccezione;
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="codiceEccezione",required=true)
  protected java.lang.String codiceEccezione;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="descrizioneEccezione",required=false)
  protected java.lang.String descrizioneEccezione;

}
