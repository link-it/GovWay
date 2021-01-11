/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it).
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
package backend.ecodex.org._1_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for PayloadType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayloadType"&gt;
 * 		&lt;xsd:simpleContent&gt;
 * 			&lt;xsd:extension base="{http://org.ecodex.backend/1_1/}byte[]"&gt;
 * 				&lt;attribute name="payloadId" type="{http://www.w3.org/2001/XMLSchema}token" use="required"/&gt;
 * 				&lt;attribute name="contentType" type="{http://www.w3.org/2001/XMLSchema}token" use="optional"/&gt;
 * 			&lt;/xsd:extension&gt;
 * 		&lt;/xsd:simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayloadType")

@XmlRootElement(name = "PayloadType")

public class PayloadType extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PayloadType() {
  }

  public byte[] getBase() {
    return this.base;
  }

  public void setBase(byte[] base) {
    this.base=base;
  }

  public java.lang.String getPayloadId() {
    return this.payloadId;
  }

  public void setPayloadId(java.lang.String payloadId) {
    this.payloadId = payloadId;
  }

  public java.lang.String getContentType() {
    return this.contentType;
  }

  public void setContentType(java.lang.String contentType) {
    this.contentType = contentType;
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlValue()
  public byte[] base;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.CollapsedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="token")
  @XmlAttribute(name="payloadId",required=true)
  protected java.lang.String payloadId;

  @javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter(javax.xml.bind.annotation.adapters.CollapsedStringAdapter.class)
  @javax.xml.bind.annotation.XmlSchemaType(name="token")
  @XmlAttribute(name="contentType",required=false)
  protected java.lang.String contentType;

}
