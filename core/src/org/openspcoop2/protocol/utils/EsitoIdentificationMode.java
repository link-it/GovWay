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

package org.openspcoop2.protocol.utils;

/**
* EsitoIdentificationMode
*
* @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
*/
public enum EsitoIdentificationMode {

	STATIC ("static"),
	SOAP_FAULT ("soapFault"),
	CONTEXT_PROPERTY ("contextProperty");

	private final String valore;

	EsitoIdentificationMode(String valore)
	{
		this.valore = valore;
	}

	public String getValore()
	{
		return this.valore;
	}

	public static String[] toStringArray(){
		String[] res = new String[EsitoIdentificationMode.values().length];
		int i=0;
		for (EsitoIdentificationMode tmp : EsitoIdentificationMode.values()) {
			res[i]=tmp.getValore();
			i++;
		}
		return res;
	}
	public static String[] toEnumNameArray(){
		String[] res = new String[EsitoIdentificationMode.values().length];
		int i=0;
		for (EsitoIdentificationMode tmp : EsitoIdentificationMode.values()) {
			res[i]=tmp.name();
			i++;
		}
		return res;
	}


	public static EsitoIdentificationMode toEnumConstant(String val){

		EsitoIdentificationMode res = null;

		if(EsitoIdentificationMode.STATIC.toString().equals(val)){
			res = EsitoIdentificationMode.STATIC;
		}else if(EsitoIdentificationMode.SOAP_FAULT.toString().equals(val)){
			res = EsitoIdentificationMode.SOAP_FAULT;
		}else if(EsitoIdentificationMode.CONTEXT_PROPERTY.toString().equals(val)){
			res = EsitoIdentificationMode.CONTEXT_PROPERTY;
		}
		return res;
	}

	
	@Override
	public String toString(){
		return this.valore;
	}
	public boolean equals(EsitoIdentificationMode esito){
		return this.toString().equals(esito.toString());
	}
	
}
