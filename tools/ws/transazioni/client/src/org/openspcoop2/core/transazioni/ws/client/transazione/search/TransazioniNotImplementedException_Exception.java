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
 * 2018-03-23T14:34:42.834+01:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "transazioni-not-implemented-exception", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
public class TransazioniNotImplementedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniNotImplementedException transazioniNotImplementedException;

    public TransazioniNotImplementedException_Exception() {
        super();
    }
    
    public TransazioniNotImplementedException_Exception(String message) {
        super(message);
    }
    
    public TransazioniNotImplementedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public TransazioniNotImplementedException_Exception(String message, org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniNotImplementedException transazioniNotImplementedException) {
        super(message);
        this.transazioniNotImplementedException = transazioniNotImplementedException;
    }

    public TransazioniNotImplementedException_Exception(String message, org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniNotImplementedException transazioniNotImplementedException, Throwable cause) {
        super(message, cause);
        this.transazioniNotImplementedException = transazioniNotImplementedException;
    }

    public org.openspcoop2.core.transazioni.ws.client.transazione.search.TransazioniNotImplementedException getFaultInfo() {
        return this.transazioniNotImplementedException;
    }
}
