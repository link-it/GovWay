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
package org.openspcoop2.protocol.information_missing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for RequisitoInput complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequisitoInput">
 * 		&lt;sequence>
 * 			&lt;element name="proprieta" type="{http://www.openspcoop2.org/protocol/information_missing}ProprietaRequisitoInput" minOccurs="1" maxOccurs="unbounded"/>
 * 		&lt;/sequence>
 * 		&lt;attribute name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string" use="required"/>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequisitoInput", 
  propOrder = {
  	"proprieta"
  }
)

@XmlRootElement(name = "RequisitoInput")

public class RequisitoInput extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public RequisitoInput() {
  }

  public void addProprieta(ProprietaRequisitoInput proprieta) {
    this.proprieta.add(proprieta);
  }

  public ProprietaRequisitoInput getProprieta(int index) {
    return this.proprieta.get( index );
  }

  public ProprietaRequisitoInput removeProprieta(int index) {
    return this.proprieta.remove( index );
  }

  public List<ProprietaRequisitoInput> getProprietaList() {
    return this.proprieta;
  }

  public void setProprietaList(List<ProprietaRequisitoInput> proprieta) {
    this.proprieta=proprieta;
  }

  public int sizeProprietaList() {
    return this.proprieta.size();
  }

  public java.lang.String getDescrizione() {
    return this.descrizione;
  }

  public void setDescrizione(java.lang.String descrizione) {
    this.descrizione = descrizione;
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="proprieta",required=true,nillable=false)
  protected List<ProprietaRequisitoInput> proprieta = new ArrayList<ProprietaRequisitoInput>();

  /**
   * @deprecated Use method getProprietaList
   * @return List<ProprietaRequisitoInput>
  */
  @Deprecated
  public List<ProprietaRequisitoInput> getProprieta() {
  	return this.proprieta;
  }

  /**
   * @deprecated Use method setProprietaList
   * @param proprieta List<ProprietaRequisitoInput>
  */
  @Deprecated
  public void setProprieta(List<ProprietaRequisitoInput> proprieta) {
  	this.proprieta=proprieta;
  }

  /**
   * @deprecated Use method sizeProprietaList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeProprieta() {
  	return this.proprieta.size();
  }

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="descrizione",required=true)
  protected java.lang.String descrizione;

}
