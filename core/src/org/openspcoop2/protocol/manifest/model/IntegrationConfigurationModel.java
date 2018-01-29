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
package org.openspcoop2.protocol.manifest.model;

import org.openspcoop2.protocol.manifest.IntegrationConfiguration;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model IntegrationConfiguration 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class IntegrationConfigurationModel extends AbstractModel<IntegrationConfiguration> {

	public IntegrationConfigurationModel(){
	
		super();
	
		this.NAME = new org.openspcoop2.protocol.manifest.model.IntegrationConfigurationNameModel(new Field("name",org.openspcoop2.protocol.manifest.IntegrationConfigurationName.class,"IntegrationConfiguration",IntegrationConfiguration.class));
		this.RESOURCE_IDENTIFICATION = new org.openspcoop2.protocol.manifest.model.IntegrationConfigurationResourceIdentificationModel(new Field("resourceIdentification",org.openspcoop2.protocol.manifest.IntegrationConfigurationResourceIdentification.class,"IntegrationConfiguration",IntegrationConfiguration.class));
	
	}
	
	public IntegrationConfigurationModel(IField father){
	
		super(father);
	
		this.NAME = new org.openspcoop2.protocol.manifest.model.IntegrationConfigurationNameModel(new ComplexField(father,"name",org.openspcoop2.protocol.manifest.IntegrationConfigurationName.class,"IntegrationConfiguration",IntegrationConfiguration.class));
		this.RESOURCE_IDENTIFICATION = new org.openspcoop2.protocol.manifest.model.IntegrationConfigurationResourceIdentificationModel(new ComplexField(father,"resourceIdentification",org.openspcoop2.protocol.manifest.IntegrationConfigurationResourceIdentification.class,"IntegrationConfiguration",IntegrationConfiguration.class));
	
	}
	
	

	public org.openspcoop2.protocol.manifest.model.IntegrationConfigurationNameModel NAME = null;
	 
	public org.openspcoop2.protocol.manifest.model.IntegrationConfigurationResourceIdentificationModel RESOURCE_IDENTIFICATION = null;
	 

	@Override
	public Class<IntegrationConfiguration> getModeledClass(){
		return IntegrationConfiguration.class;
	}
	
	@Override
	public String toString(){
		if(this.getModeledClass()!=null){
			return this.getModeledClass().getName();
		}else{
			return "N.D.";
		}
	}

}