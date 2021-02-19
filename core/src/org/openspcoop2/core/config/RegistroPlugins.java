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


/** <p>Java class for registro-plugins complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registro-plugins"&gt;
 * 		&lt;sequence&gt;
 * 			&lt;element name="plugin" type="{http://www.openspcoop2.org/core/config}registro-plugin" minOccurs="0" maxOccurs="unbounded"/&gt;
 * 		&lt;/sequence&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * @version $Rev$, $Date$
 * 
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registro-plugins", 
  propOrder = {
  	"plugin"
  }
)

@XmlRootElement(name = "registro-plugins")

public class RegistroPlugins extends org.openspcoop2.utils.beans.BaseBean implements Serializable , Cloneable {
  public RegistroPlugins() {
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

  public void addPlugin(RegistroPlugin plugin) {
    this.plugin.add(plugin);
  }

  public RegistroPlugin getPlugin(int index) {
    return this.plugin.get( index );
  }

  public RegistroPlugin removePlugin(int index) {
    return this.plugin.remove( index );
  }

  public List<RegistroPlugin> getPluginList() {
    return this.plugin;
  }

  public void setPluginList(List<RegistroPlugin> plugin) {
    this.plugin=plugin;
  }

  public int sizePluginList() {
    return this.plugin.size();
  }

  private static final long serialVersionUID = 1L;

  @XmlTransient
  private Long id;



  @XmlElement(name="plugin",required=true,nillable=false)
  protected List<RegistroPlugin> plugin = new ArrayList<RegistroPlugin>();

  /**
   * @deprecated Use method getPluginList
   * @return List&lt;RegistroPlugin&gt;
  */
  @Deprecated
  public List<RegistroPlugin> getPlugin() {
  	return this.plugin;
  }

  /**
   * @deprecated Use method setPluginList
   * @param plugin List&lt;RegistroPlugin&gt;
  */
  @Deprecated
  public void setPlugin(List<RegistroPlugin> plugin) {
  	this.plugin=plugin;
  }

  /**
   * @deprecated Use method sizePluginList
   * @return lunghezza della lista
  */
  @Deprecated
  public int sizePlugin() {
  	return this.plugin.size();
  }

}