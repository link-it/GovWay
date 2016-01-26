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

package org.openspcoop2.protocol.spcoop.backward_compatibility.integrazione;

import org.apache.log4j.Logger;
import org.openspcoop2.pdd.core.AbstractCore;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.core.integrazione.HeaderIntegrazione;
import org.openspcoop2.pdd.core.integrazione.HeaderIntegrazioneException;
import org.openspcoop2.pdd.core.integrazione.IGestoreIntegrazionePD;
import org.openspcoop2.pdd.core.integrazione.InRequestPDMessage;
import org.openspcoop2.pdd.core.integrazione.InResponsePDMessage;
import org.openspcoop2.pdd.core.integrazione.OutRequestPDMessage;
import org.openspcoop2.pdd.core.integrazione.OutResponsePDMessage;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.spcoop.backward_compatibility.config.BackwardCompatibilityProperties;
import org.openspcoop2.protocol.spcoop.backward_compatibility.config.Costanti;



/**
 * Classe utilizzata per la ricezione di informazioni di integrazione 
 * dai servizi applicativi verso la porta di dominio.
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class GestoreIntegrazionePDUrlBased extends AbstractCore implements IGestoreIntegrazionePD{

	/** Utility per l'integrazione */
	private UtilitiesIntegrazione utilities = null;
	
	/** Logger utilizzato per debug. */
	private Logger log = null;
	
	/** BackwardCompatibilityProperties */
	private BackwardCompatibilityProperties backwardCompatibilityProperties = null;
	
	/** GestoreIntegrazionePD Originale */
	private org.openspcoop2.pdd.core.integrazione.GestoreIntegrazionePDUrlBased gestoreIntegrazioneOpenSPCoopV2 = null;
	
	public GestoreIntegrazionePDUrlBased(){
		this.log = OpenSPCoop2Logger.getLoggerOpenSPCoopCore();
		if(this.log==null){
			this.log = Logger.getLogger(GestoreIntegrazionePDUrlBased.class);
		}
		try{
			this.utilities = UtilitiesIntegrazione.getInstance(true);
			this.backwardCompatibilityProperties = BackwardCompatibilityProperties.getInstance(true);
		}catch(Exception e){
			this.log.error("Errore durante l'inizializzazione: "+e.getMessage(),e);
		}
		try{
			this.gestoreIntegrazioneOpenSPCoopV2 = new org.openspcoop2.pdd.core.integrazione.GestoreIntegrazionePDUrlBased();
		}catch(Exception e){
			this.log.error("Errore durante l'instanziazione del gestoreIntegrazioneOpenSPCoopV2: "+e.getMessage(),e);
		}
	}
	@Override
	public void init(PdDContext pddContext, IProtocolFactory protocolFactory,
			Object... args) {
		super.init(pddContext, protocolFactory, args);
		try{
			if(this.gestoreIntegrazioneOpenSPCoopV2!=null){
				this.gestoreIntegrazioneOpenSPCoopV2.init(this.getPddContext(), this.getProtocolFactory(), this.getArgs());
			}
		}catch(Exception e){
			this.log.error("Errore durante l'instanziazione del gestoreIntegrazioneOpenSPCoopV2: "+e.getMessage(),e);
		}
	}
	
	
	// IN - Request
	
	@Override
	public void readInRequestHeader(HeaderIntegrazione integrazione,
			InRequestPDMessage inRequestPDMessage) throws HeaderIntegrazioneException{
		
		try{
			if( 
				(!this.backwardCompatibilityProperties.isSwitchOpenSPCoopV2PortaDelegata())
				||
				(this.backwardCompatibilityProperties.isSwitchOpenSPCoopV2PortaDelegata() && this.getPddContext().containsKey(Costanti.OPENSPCOOP2_BACKWARD_COMPATIBILITY))
			){
				this.utilities.readUrlProperties(inRequestPDMessage.getUrlProtocolContext().getParametersFormBased(), integrazione);
			}
			else{
				this.gestoreIntegrazioneOpenSPCoopV2.readInRequestHeader(integrazione, inRequestPDMessage);
			}
		}catch(Exception e){
			throw new HeaderIntegrazioneException("GestoreIntegrazionePDUrlBased, "+e.getMessage(),e);
		}
		
	}

	// OUT - Request
	
	@Override
	public void setOutRequestHeader(HeaderIntegrazione integrazione,
			OutRequestPDMessage outRequestPDMessage) throws HeaderIntegrazioneException{
	
		// nop;
		
	}
	
	// IN - Response
	
	@Override
	public void readInResponseHeader(HeaderIntegrazione integrazione,
			InResponsePDMessage inResponsePDMessage) throws HeaderIntegrazioneException{
		// NOP
		// Non esiste un header di integrazione basato sulla url per la risposta
	}
	
	// OUT - Response
	
	@Override
	public void setOutResponseHeader(HeaderIntegrazione integrazione,
			OutResponsePDMessage outResponsePDMessage) throws HeaderIntegrazioneException{
		// NOP
		// Non esiste un header di integrazione basato sulla url per la risposta
	}
}