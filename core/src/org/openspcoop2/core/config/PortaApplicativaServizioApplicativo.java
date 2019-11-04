/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/** <p>Java class for porta-applicativa-servizio-applicativo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="porta-applicativa-servizio-applicativo">
 * 		&lt;sequence>
 * 			&lt;element name="dati-connettore" type="{http://www.openspcoop2.org/core/config}porta-applicativa-servizio-applicativo-connettore" minOccurs="0" maxOccurs="1"/>
 * 		&lt;/sequence>
 * 		&lt;attribute name="nome" type="{http://www.w3.org/2001/XMLSchema}string" use="required"/>
 * 		&lt;attribute name="id-servizio-applicativo" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" use="optional"/>
 * &lt;/complexType>
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "porta-applicativa-servizio-applicativo", 
  propOrder = {
  	"datiConnettore"
  }
)

@XmlRootElement(name = "porta-applicativa-servizio-applicativo")

public class PortaApplicativaServizioApplicativo extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public PortaApplicativaServizioApplicativo() {
  }

  public Long getId() {
    if(this.id!=null)
		return this.id;
	else
		return Long.valueOf(-1);
  }

  public void setId(Long id) {
    if(id!=null)
		this.id=id;
	else
		this.id=Long.valueOf(-1);
  }

  public PortaApplicativaServizioApplicativoConnettore getDatiConnettore() {
    return this.datiConnettore;
  }

  public void setDatiConnettore(PortaApplicativaServizioApplicativoConnettore datiConnettore) {
    this.datiConnettore = datiConnettore;
  }

  public java.lang.String getNome() {
    return this.nome;
  }

  public void setNome(java.lang.String nome) {
    this.nome = nome;
  }

  public java.lang.Long getIdServizioApplicativo() {
    return this.idServizioApplicativo;
  }

  public void setIdServizioApplicativo(java.lang.Long idServizioApplicativo) {
    this.idServizioApplicativo = idServizioApplicativo;
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="dati-connettore",required=false,nillable=false)
  protected PortaApplicativaServizioApplicativoConnettore datiConnettore;

  @javax.xml.bind.annotation.XmlSchemaType(name="string")
  @XmlAttribute(name="nome",required=true)
  protected java.lang.String nome;

  @javax.xml.bind.annotation.XmlTransient
  protected java.lang.Long idServizioApplicativo;

}
