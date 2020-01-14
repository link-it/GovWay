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

package org.openspcoop2.core.tracciamento.ws.client.traccia.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-26T15:02:43.236+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "tracciamento-service-exception", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
public class TracciamentoServiceException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.tracciamento.ws.client.traccia.search.TracciamentoServiceException tracciamentoServiceException;

    public TracciamentoServiceException_Exception() {
        super();
    }
    
    public TracciamentoServiceException_Exception(String message) {
        super(message);
    }
    
    public TracciamentoServiceException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public TracciamentoServiceException_Exception(String message, org.openspcoop2.core.tracciamento.ws.client.traccia.search.TracciamentoServiceException tracciamentoServiceException) {
        super(message);
        this.tracciamentoServiceException = tracciamentoServiceException;
    }

    public TracciamentoServiceException_Exception(String message, org.openspcoop2.core.tracciamento.ws.client.traccia.search.TracciamentoServiceException tracciamentoServiceException, Throwable cause) {
        super(message, cause);
        this.tracciamentoServiceException = tracciamentoServiceException;
    }

    public org.openspcoop2.core.tracciamento.ws.client.traccia.search.TracciamentoServiceException getFaultInfo() {
        return this.tracciamentoServiceException;
    }
}
