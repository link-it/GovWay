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




package org.openspcoop2.pdd.core.connettori;

import org.openspcoop2.core.config.ResponseCachingConfigurazione;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.pdd.core.Utilities;


/**
 * Classe utilizzata per effettuare consegne di messaggi Soap, attraverso
 * l'invocazione di un server http. 
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class ConnettoreNULL extends ConnettoreBase {
	
	public final static String LOCATION = "govway://dev/null";
    
	


	/* ********  METODI  ******** */

	@Override
	protected boolean initializePreSend(ResponseCachingConfigurazione responseCachingConfig, ConnettoreMsg request) {
		
		if(this.initialize(request, false, responseCachingConfig)==false){
			return false;
		}
		
		return true;
		
	}
	
	@Override
	protected boolean send(ConnettoreMsg request) {
		
		this.codice = 200;
		
		try{
			
			// SIMULAZIONE WRITE_TO
			
			org.apache.commons.io.output.NullOutputStream nullOutputStream = new org.apache.commons.io.output.NullOutputStream();
			this.requestMsg.writeTo(nullOutputStream,true);
			nullOutputStream.flush();
			nullOutputStream.close();
		
		}catch(Exception e){
			this.eccezioneProcessamento = e;
			this.logger.error("Riscontrato errore durante la writeTo",e);
			this.errore = "Riscontrato errore durante la writeTo: " +this.readExceptionMessageFromException(e);
			return false;
		}
		
		try{
			
			/* ------------  PostOutRequestHandler ------------- */
			this.postOutRequest();
			
		}catch(Exception e){
			this.eccezioneProcessamento = e;
			this.logger.error("Riscontrato errore durante la gestione PostOutRequestHandler",e);
			this.errore = "Riscontrato errore durante la gestione PostOutRequestHandler: " +this.readExceptionMessageFromException(e);
			return false;
		}
			
//		org.openspcoop2.utils.io.notifier.NotifierInputStreamParams notifierInputStreamParams = null;
//		try{
//			
//			/* ------------  PreInResponseHandler ------------- */
//			this.preInResponse();
//			
//			// Lettura risposta parametri NotifierInputStream per la risposta
//			if(this.preInResponseContext!=null){
//				notifierInputStreamParams = this.preInResponseContext.getNotifierInputStreamParams();
//			}
//			
//		}catch(Exception e){
//			this.eccezioneProcessamento = e;
//			ConnettoreNULL.log.error("Riscontrato errore durante la gestione PreInResponseHandler",e);
//			this.errore = "Riscontrato errore durante la gestione PreInResponseHandler: " +e.getMessage();
//			return false;
//		}
		
		try{
					
			OpenSPCoop2MessageFactory messageFactory = Utilities.getOpenspcoop2MessageFactory(this.logger.getLogger(),this.requestMsg, this.requestInfo, MessageRole.RESPONSE);
			this.responseMsg = messageFactory.createEmptyMessage(this.requestMsg.getMessageType(),MessageRole.RESPONSE);
			
		}catch(Exception e){
			this.eccezioneProcessamento = e;
			this.logger.error("Riscontrato errore durante la generazione di un msg SoapVuoto",e);
			this.errore = "Riscontrato errore durante la generazione di un msg SoapVuoto: " +this.readExceptionMessageFromException(e);
			return false;
		}
		
		return true;
	}

    /**
     * Ritorna l'informazione su dove il connettore sta spedendo il messaggio
     * 
     * @return location di inoltro del messaggio
     * @throws ConnettoreException 
     */
    @Override
	public String getLocation() throws ConnettoreException{
    	// Il connettore NULL ho possiede possibilita' di consegnare su di una url
		//return ConnettoreUtils.buildLocationWithURLBasedParameter(this.requestMsg, this.propertiesUrlBased, LOCATION);
    	return LOCATION;
    }
    
}





