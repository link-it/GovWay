/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
package org.openspcoop2.core.diagnostica.model;

import org.openspcoop2.core.diagnostica.DominioTransazione;

import org.openspcoop2.generic_project.beans.AbstractModel;
import org.openspcoop2.generic_project.beans.IField;
import org.openspcoop2.generic_project.beans.Field;
import org.openspcoop2.generic_project.beans.ComplexField;


/**     
 * Model DominioTransazione 
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DominioTransazioneModel extends AbstractModel<DominioTransazione> {

	public DominioTransazioneModel(){
	
		super();
	
		this.IDENTIFICATIVO_PORTA = new Field("identificativo-porta",java.lang.String.class,"dominio-transazione",DominioTransazione.class);
		this.SOGGETTO = new org.openspcoop2.core.diagnostica.model.DominioSoggettoModel(new Field("soggetto",org.openspcoop2.core.diagnostica.DominioSoggetto.class,"dominio-transazione",DominioTransazione.class));
	
	}
	
	public DominioTransazioneModel(IField father){
	
		super(father);
	
		this.IDENTIFICATIVO_PORTA = new ComplexField(father,"identificativo-porta",java.lang.String.class,"dominio-transazione",DominioTransazione.class);
		this.SOGGETTO = new org.openspcoop2.core.diagnostica.model.DominioSoggettoModel(new ComplexField(father,"soggetto",org.openspcoop2.core.diagnostica.DominioSoggetto.class,"dominio-transazione",DominioTransazione.class));
	
	}
	
	

	public IField IDENTIFICATIVO_PORTA = null;
	 
	public org.openspcoop2.core.diagnostica.model.DominioSoggettoModel SOGGETTO = null;
	 

	@Override
	public Class<DominioTransazione> getModeledClass(){
		return DominioTransazione.class;
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