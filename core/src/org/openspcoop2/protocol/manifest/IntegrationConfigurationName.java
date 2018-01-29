/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
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
package org.openspcoop2.protocol.manifest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** <p>Java class for IntegrationConfigurationName complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntegrationConfigurationName">
 * 		&lt;sequence>
 * 			&lt;element name="param" type="{http://www.openspcoop2.org/protocol/manifest}IntegrationConfigurationElementName" minOccurs="1" maxOccurs="unbounded"/>
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
@XmlType(name = "IntegrationConfigurationName", 
  propOrder = {
  	"param"
  }
)

@XmlRootElement(name = "IntegrationConfigurationName")

public class IntegrationConfigurationName extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public IntegrationConfigurationName() {
  }

  public void addParam(IntegrationConfigurationElementName param) {
    this.param.add(param);
  }

  public IntegrationConfigurationElementName getParam(int index) {
    return this.param.get( index );
  }

  public IntegrationConfigurationElementName removeParam(int index) {
    return this.param.remove( index );
  }

  public List<IntegrationConfigurationElementName> getParamList() {
    return this.param;
  }

  public void setParamList(List<IntegrationConfigurationElementName> param) {
    this.param=param;
  }

  public int sizeParamList() {
    return this.param.size();
  }

  private static final long serialVersionUID = 1L;



  @XmlElement(name="param",required=true,nillable=false)
  protected List<IntegrationConfigurationElementName> param = new ArrayList<IntegrationConfigurationElementName>();

  /**
   * @deprecated Use method getParamList
   * @return List<IntegrationConfigurationElementName>
  */
  @Deprecated
  public List<IntegrationConfigurationElementName> getParam() {
  	return this.param;
  }

  /**
   * @deprecated Use method setParamList
   * @param param List<IntegrationConfigurationElementName>
  */
  @Deprecated
  public void setParam(List<IntegrationConfigurationElementName> param) {
  	this.param=param;
  }

  /**
   * @deprecated Use method sizeParamList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizeParam() {
  	return this.param.size();
  }

}
