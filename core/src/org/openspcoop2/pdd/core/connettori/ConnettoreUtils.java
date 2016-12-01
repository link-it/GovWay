/*
 * OpenSPCoop - Customizable API Gateway 
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

package org.openspcoop2.pdd.core.connettori;

import java.util.Enumeration;
import java.util.Properties;

import org.openspcoop2.core.constants.CostantiConnettori;
import org.openspcoop2.core.constants.TipiConnettore;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.message.rest.RestUtilities;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.slf4j.Logger;

/**
 * ConnettoreUtils
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConnettoreUtils {

	public static String formatLocation(HttpRequestMethod httpMethod, String location){
		if(httpMethod!=null)
			return location+" http-method:"+httpMethod;
		else
			return location;
	}
	
	public static String getAndReplaceLocationWithBustaValues(ConnettoreMsg connettoreMsg,Busta busta,Logger log) throws ConnettoreException{
		
		String location = null;
		if(TipiConnettore.NULL.getNome().equals(connettoreMsg.getTipoConnettore())){
			location = ConnettoreNULL.LOCATION;
		}
		else if(TipiConnettore.NULLECHO.getNome().equals(connettoreMsg.getTipoConnettore())){
			location = ConnettoreNULLEcho.LOCATION;
		}
		else if(ConnettoreStresstest.TIPO.equals(connettoreMsg.getTipoConnettore())){
			location = ConnettoreStresstest.LOCATION;
		}
		else if(ConnettoreRicezioneBusteDirectVM.TIPO.equals(connettoreMsg.getTipoConnettore())){
			ConnettoreRicezioneBusteDirectVM c = new ConnettoreRicezioneBusteDirectVM();
			try{
				c.validate(connettoreMsg);
				c.buildLocation(connettoreMsg.getConnectorProperties(),false);
			}catch(Exception e){
				log.error("Errore durante la costruzione della location: "+e.getMessage(),e);
			}
			location = c.getLocation();
		}
		else if(ConnettoreRicezioneContenutiApplicativiDirectVM.TIPO.equals(connettoreMsg.getTipoConnettore())){
			ConnettoreRicezioneContenutiApplicativiDirectVM c = new ConnettoreRicezioneContenutiApplicativiDirectVM();
			try{
				c.validate(connettoreMsg);
				c.buildLocation(connettoreMsg.getConnectorProperties(),false);
			}catch(Exception e){
				log.error("Errore durante la costruzione della location: "+e.getMessage(),e);
			}
			location = c.getLocation();
		}
		else if(ConnettoreRicezioneContenutiApplicativiHTTPtoSOAPDirectVM.TIPO.equals(connettoreMsg.getTipoConnettore())){
			ConnettoreRicezioneContenutiApplicativiHTTPtoSOAPDirectVM c = new ConnettoreRicezioneContenutiApplicativiHTTPtoSOAPDirectVM();
			try{
				c.validate(connettoreMsg);
				c.buildLocation(connettoreMsg.getConnectorProperties(),false);
			}catch(Exception e){
				log.error("Errore durante la costruzione della location: "+e.getMessage(),e);
			}
			location = c.getLocation();
		}
		else{
			if(connettoreMsg.getConnectorProperties().get(CostantiConnettori.CONNETTORE_LOCATION)!=null){
				location = connettoreMsg.getConnectorProperties().get(CostantiConnettori.CONNETTORE_LOCATION);
			}
		}
		
		if(location !=null && (location.equals("")==false) ){
			location = location.replace(CostantiConnettori.CONNETTORE_JMS_LOCATION_REPLACE_TOKEN_TIPO_SERVIZIO,busta.getTipoServizio());
			location = location.replace(CostantiConnettori.CONNETTORE_JMS_LOCATION_REPLACE_TOKEN_NOME_SERVIZIO,busta.getServizio());
			if(busta.getAzione()!=null){
				location = location.replace(CostantiConnettori.CONNETTORE_JMS_LOCATION_REPLACE_TOKEN_AZIONE,busta.getAzione());
			}
			connettoreMsg.getConnectorProperties().put(CostantiConnettori.CONNETTORE_LOCATION,location);
		}
		
		return location;
	}
	
	public static String buildLocationWithURLBasedParameter(OpenSPCoop2Message msg, Properties propertiesURLBased, String locationParam) throws ConnettoreException{
		
		try{
		
			Properties p = propertiesURLBased;
			if(msg.getForwardUrlProperties()!=null && msg.getForwardUrlProperties().size()>0){
				if(p==null){
					p = new Properties();
				}
				Enumeration<?> keys = msg.getForwardUrlProperties().getKeys();
				while (keys.hasMoreElements()) {
					String key = (String) keys.nextElement();
					String value = msg.getForwardUrlProperties().getProperty(key);
					if(value!=null){
						if(p.containsKey(key)){
							p.remove(key);
						}
						p.setProperty(key, value);
					}
				}
			}
			
			String location = locationParam;
			if(ServiceBinding.REST.equals(msg.getServiceBinding())){
				return RestUtilities.buildUrl(location, p, msg.getTransportRequestContext());
			}
			else{
				return TransportUtils.buildLocationWithURLBasedParameter(p, location, OpenSPCoop2Logger.getLoggerOpenSPCoopCore());
			}
			
		}catch(Exception e){
			throw new ConnettoreException(e.getMessage(),e);
		}
	}

	public static String limitLocation255Character(String location){
		return TransportUtils.limitLocation255Character(location);
	}
}
