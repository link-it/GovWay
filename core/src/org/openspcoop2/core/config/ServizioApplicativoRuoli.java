/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it).
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
package org.openspcoop2.core.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for servizio-applicativo-ruoli complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="servizio-applicativo-ruoli">
 * 		&lt;sequence>
 * 			&lt;element name="ruolo" type="{http://www.openspcoop2.org/core/config}ruolo" minOccurs="0" maxOccurs="unbounded"/>
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
@XmlType(name = "servizio-applicativo-ruoli", 
  propOrder = {
  	"ruolo"
  }
)

@XmlRootElement(name = "servizio-applicativo-ruoli")

public class ServizioApplicativoRuoli extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public ServizioApplicativoRuoli() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return new Long(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=new Long(-1);
  }

  public void addRuolo(Ruolo ruolo) {
    this.ruolo.add(ruolo);
  }

  public Ruolo getRuolo(int index) {
    return this.ruolo.get( index );
  }

  public Ruolo removeRuolo(int index) {
    return this.ruolo.remove( index );
  }

  public List<Ruolo> getRuoloList() {
    return this.ruolo;
  }

  public void setRuoloList(List<Ruolo> ruolo) {
    this.ruolo=ruolo;
  }

  public int sizeRuoloList() {
    return this.ruolo.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="ruolo",required=true,nillable=false)
  protected List<Ruolo> ruolo = new ArrayList<Ruolo>();

  /**
   * @deprecated Use method getRuoloList
   * @return List<Ruolo>
  */
  @Deprecated
  public List<Ruolo> getRuolo() {
  	return this.ruolo;
  }

  /**
   * @deprecated Use method setRuoloList
   * @param ruolo List<Ruolo>
  */
  @Deprecated
  public void setRuolo(List<Ruolo> ruolo) {
  	this.ruolo=ruolo;
  }

  /**
   * @deprecated Use method sizeRuoloList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeRuolo() {
  	return this.ruolo.size();
  }

}