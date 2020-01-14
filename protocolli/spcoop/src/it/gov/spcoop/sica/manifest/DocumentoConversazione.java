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
package it.gov.spcoop.sica.manifest;

import it.gov.spcoop.sica.manifest.constants.TipoDocumentoConversazione;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for DocumentoConversazione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentoConversazione">
 * 		<xsd:simpleContent>
 * 			<xsd:extension base="{http://www.w3.org/2001/XMLSchema}string">
 * 				&lt;attribute name="tipo" type="{http://spcoop.gov.it/sica/manifest}TipoDocumentoConversazione" use="optional" default="WSBL"/>
 * 			</xsd:extension>
 * 		</xsd:simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentoConversazione")

@XmlRootElement(name = "DocumentoConversazione")

public class DocumentoConversazione extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public DocumentoConversazione() {
  }

  public String getBase() {
    if(this.base!=null && ("".equals(this.base)==false)){
		return this.base.trim();
	}else{
		return null;
	}

  }

  public void setBase(String base) {
    this.base=base;
  }

  public void set_value_tipo(String value) {
    this.tipo = (TipoDocumentoConversazione) TipoDocumentoConversazione.toEnumConstantFromString(value);
  }

  public String get_value_tipo() {
    if(this.tipo == null){
    	return null;
    }else{
    	return this.tipo.toString();
    }
  }

  public it.gov.spcoop.sica.manifest.constants.TipoDocumentoConversazione getTipo() {
    return this.tipo;
  }

  public void setTipo(it.gov.spcoop.sica.manifest.constants.TipoDocumentoConversazione tipo) {
    this.tipo = tipo;
  }

  private static final long serialVersionUID = 1L;



  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @javax.xml.bind.annotation.XmlValue()
  public String base;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.String _value_tipo;

  @XmlAttribute(name="tipo",required=false)
  protected TipoDocumentoConversazione tipo = (TipoDocumentoConversazione) TipoDocumentoConversazione.toEnumConstantFromString("WSBL");

}
