/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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

package org.openspcoop2.core.transazioni.ws.client.transazione.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-23T14:34:42.827+01:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "transazioni-service-exception", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
public class TransazioniServiceException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniServiceException transazioniServiceException;

    public TransazioniServiceException_Exception() {
        super();
    }
    
    public TransazioniServiceException_Exception(String message) {
        super(message);
    }
    
    public TransazioniServiceException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public TransazioniServiceException_Exception(String message, org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniServiceException transazioniServiceException) {
        super(message);
        this.transazioniServiceException = transazioniServiceException;
    }

    public TransazioniServiceException_Exception(String message, org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniServiceException transazioniServiceException, Throwable cause) {
        super(message, cause);
        this.transazioniServiceException = transazioniServiceException;
    }

    public org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniServiceException getFaultInfo() {
        return this.transazioniServiceException;
    }
}
