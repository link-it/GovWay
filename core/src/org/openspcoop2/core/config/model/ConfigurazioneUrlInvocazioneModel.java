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
package org.openspcoop2.core.config.model;

import org.openspcoop2.core.config.ConfigurazioneUrlInvocazione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model ConfigurazioneUrlInvocazione 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConfigurazioneUrlInvocazioneModel extends AbstractModel<ConfigurazioneUrlInvocazione> {

	public ConfigurazioneUrlInvocazioneModel(){
	
		super();
	
		this.REGOLA = new org.openspcoop2.core.config.model.ConfigurazioneUrlInvocazioneRegolaModel(new Field("regola",org.openspcoop2.core.config.ConfigurazioneUrlInvocazioneRegola.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class));
		this.BASE_URL = new Field("base-url",java.lang.String.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class);
		this.BASE_URL_FRUIZIONE = new Field("base-url-fruizione",java.lang.String.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class);
	
	}
	
	public ConfigurazioneUrlInvocazioneModel(IField father){
	
		super(father);
	
		this.REGOLA = new org.openspcoop2.core.config.model.ConfigurazioneUrlInvocazioneRegolaModel(new ComplexField(father,"regola",org.openspcoop2.core.config.ConfigurazioneUrlInvocazioneRegola.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class));
		this.BASE_URL = new ComplexField(father,"base-url",java.lang.String.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class);
		this.BASE_URL_FRUIZIONE = new ComplexField(father,"base-url-fruizione",java.lang.String.class,"configurazione-url-invocazione",ConfigurazioneUrlInvocazione.class);
	
	}
	
	

	public org.openspcoop2.core.config.model.ConfigurazioneUrlInvocazioneRegolaModel REGOLA = null;
	 
	public IField BASE_URL = null;
	 
	public IField BASE_URL_FRUIZIONE = null;
	 

	@Override
	public Class<ConfigurazioneUrlInvocazione> getModeledClass(){
		return ConfigurazioneUrlInvocazione.class;
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