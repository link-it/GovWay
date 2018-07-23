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
package org.openspcoop2.protocol.information_missing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for Description complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Description">
 * 		&lt;sequence>
 * 			&lt;element name="item" type="{http://www.openspcoop2.org/protocol/information_missing}DescriptionType" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "Description", 
  propOrder = {
  	"item"
  }
)

@XmlRootElement(name = "Description")

public class Description extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public Description() {
  }

  public void addItem(DescriptionType item) {
    this.item.add(item);
  }

  public DescriptionType getItem(int index) {
    return this.item.get( index );
  }

  public DescriptionType removeItem(int index) {
    return this.item.remove( index );
  }

  public List<DescriptionType> getItemList() {
    return this.item;
  }

  public void setItemList(List<DescriptionType> item) {
    this.item=item;
  }

  public int sizeItemList() {
    return this.item.size();
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="item",required=true,nillable=false)
  protected List<DescriptionType> item = new ArrayList<DescriptionType>();

  /**
   * @deprecated Use method getItemList
   * @return List<DescriptionType>
  */
  @Deprecated
  public List<DescriptionType> getItem() {
  	return this.item;
  }

  /**
   * @deprecated Use method setItemList
   * @param item List<DescriptionType>
  */
  @Deprecated
  public void setItem(List<DescriptionType> item) {
  	this.item=item;
  }

  /**
   * @deprecated Use method sizeItemList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeItem() {
  	return this.item.size();
  }

}