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
package org.openspcoop2.core.transazioni.ws.server.exception;

import java.io.Serializable;

/**     
 * TransazioniServiceException_Exception (contains FaultInfo TransazioniServiceException)
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */


@javax.xml.ws.WebFault(name = "transazioni-service-exception", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
public class TransazioniServiceException_Exception extends Exception implements Serializable {
	
	private static final long serialVersionUID = -1L;

	private org.openspcoop2.core.transazioni.ws.server.exception.TransazioniServiceException faultInfo;

	// Deprecated: Costruttori utili se i beans vengono usati al posto di quelli generati tramite tools automatici
	@Deprecated
	public TransazioniServiceException_Exception(String message, java.lang.Throwable t){
		super(message, t);
	}
	@Deprecated
	public TransazioniServiceException_Exception(String message){
		super(message);
	}
	
	public TransazioniServiceException_Exception(String message, org.openspcoop2.core.transazioni.ws.server.exception.TransazioniServiceException faultInfo) {
		super(message);
		this.faultInfo = faultInfo;
    }

	public TransazioniServiceException_Exception(String message, org.openspcoop2.core.transazioni.ws.server.exception.TransazioniServiceException faultInfo, Throwable cause) {
		super(message, cause);
		this.faultInfo = faultInfo;
	}

	public org.openspcoop2.core.transazioni.ws.server.exception.TransazioniServiceException getFaultInfo() {
		return this.faultInfo;
	}

}