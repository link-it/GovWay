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
package org.openspcoop2.pdd.core;

import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.pdd.config.ClassNameProperties;
import org.openspcoop2.pdd.services.connector.ConnectorException;
import org.openspcoop2.protocol.engine.RequestInfo;
import org.openspcoop2.protocol.registry.RegistroServiziManager;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.Trasmissione;
import org.openspcoop2.utils.resources.Loader;
import org.slf4j.Logger;

/**
 * Utilities PdD
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Utilities {

	public static void refreshIdentificativiPorta(Busta busta,IDSoggetto idSoggettoDefault,RegistroServiziManager registroServiziManager,IProtocolFactory<?> protocolFactory){
		if(busta.getMittente()!=null && busta.getTipoMittente()!=null && busta.getIdentificativoPortaMittente()==null){
			try{
				if(idSoggettoDefault.getTipo().equals(busta.getTipoMittente()) &&
						idSoggettoDefault.getNome().equals(busta.getMittente())){
					busta.setIdentificativoPortaMittente(idSoggettoDefault.getCodicePorta());
				}
				else{
					busta.setIdentificativoPortaMittente(registroServiziManager.getDominio(new IDSoggetto(busta.getTipoMittente(), busta.getMittente()), null, protocolFactory));
				}
			}catch(Exception e){}
		}
		if(busta.getDestinatario()!=null && busta.getTipoDestinatario()!=null && busta.getIdentificativoPortaDestinatario()==null){
			try{
				if(idSoggettoDefault.getTipo().equals(busta.getTipoDestinatario()) &&
						idSoggettoDefault.getNome().equals(busta.getDestinatario())){
					busta.setIdentificativoPortaDestinatario(idSoggettoDefault.getCodicePorta());
				}
				else{
					busta.setIdentificativoPortaDestinatario(registroServiziManager.getDominio(new IDSoggetto(busta.getTipoDestinatario(), busta.getDestinatario()), null, protocolFactory));
				}
			}catch(Exception e){}
		}
		for (int i = 0; i < busta.sizeListaTrasmissioni(); i++) {
			Trasmissione tr = busta.getTrasmissione(i);
			if(tr.getOrigine()!=null && tr.getTipoOrigine()!=null && tr.getIdentificativoPortaOrigine()==null){
				try{
					if(idSoggettoDefault.getTipo().equals(tr.getTipoOrigine()) &&
							idSoggettoDefault.getNome().equals(tr.getOrigine())){
						tr.setIdentificativoPortaOrigine(idSoggettoDefault.getCodicePorta());
					}
					else{
						tr.setIdentificativoPortaOrigine(registroServiziManager.getDominio(new IDSoggetto(tr.getTipoOrigine(), tr.getOrigine()), null, protocolFactory));
					}
				}catch(Exception e){}
			}
			if(tr.getDestinazione()!=null && tr.getTipoDestinazione()!=null && tr.getIdentificativoPortaDestinazione()==null){
				try{
					if(idSoggettoDefault.getTipo().equals(tr.getTipoDestinazione()) &&
							idSoggettoDefault.getNome().equals(tr.getDestinazione())){
						tr.setIdentificativoPortaDestinazione(idSoggettoDefault.getCodicePorta());
					}
					else{
						tr.setIdentificativoPortaDestinazione(registroServiziManager.getDominio(new IDSoggetto(tr.getTipoDestinazione(), tr.getDestinazione()), null, protocolFactory));
					}
				}catch(Exception e){}
			}
		}
	}

	
	private static OpenSPCoop2MessageFactory _factory = OpenSPCoop2MessageFactory.getDefaultMessageFactory();
	public static OpenSPCoop2MessageFactory getOpenspcoop2MessageFactory(Logger log, RequestInfo requestInfo, MessageRole role) throws ConnectorException{
		return getOpenspcoop2MessageFactory(log, null, requestInfo, role);
	}
	public static OpenSPCoop2MessageFactory getOpenspcoop2MessageFactory(Logger log, OpenSPCoop2Message requestMessage, RequestInfo requestInfo, MessageRole role) throws ConnectorException{
		OpenSPCoop2MessageFactory useFactory = _factory;
		if(requestMessage!=null && requestMessage.getFactory()!=null) {
			useFactory = requestMessage.getFactory();
		}
		else {
			if(requestInfo!=null && requestInfo.getMessageFactory()!=null) {
				String classNameMessageFactory = ClassNameProperties.getInstance().getOpenSPCoop2MessageFactory(requestInfo.getMessageFactory());
				if(classNameMessageFactory==null) {
					throw new ConnectorException("MessageFactory '"+requestInfo.getMessageFactory()+"' not found");
				}
				try {
					useFactory = (OpenSPCoop2MessageFactory) Loader.getInstance().newInstance(classNameMessageFactory);
				}catch(Exception e){
					throw new ConnectorException(e.getMessage(),e);
				}	
			}
		}
		log.debug("MessageEngineFactory ["+role+"] ["+useFactory.getClass().getName()+"]");
		return useFactory;
	}
}
