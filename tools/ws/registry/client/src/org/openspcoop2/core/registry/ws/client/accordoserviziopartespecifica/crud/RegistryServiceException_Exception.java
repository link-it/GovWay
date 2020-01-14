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

package org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.2.6
 * 2019-09-19T15:25:23.339+02:00
 * Generated source version: 3.2.6
 */

@WebFault(name = "registry-service-exception", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class RegistryServiceException_Exception extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.RegistryServiceException registryServiceException;

    public RegistryServiceException_Exception() {
        super();
    }

    public RegistryServiceException_Exception(String message) {
        super(message);
    }

    public RegistryServiceException_Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public RegistryServiceException_Exception(String message, org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.RegistryServiceException registryServiceException) {
        super(message);
        this.registryServiceException = registryServiceException;
    }

    public RegistryServiceException_Exception(String message, org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.RegistryServiceException registryServiceException, java.lang.Throwable cause) {
        super(message, cause);
        this.registryServiceException = registryServiceException;
    }

    public org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.RegistryServiceException getFaultInfo() {
        return this.registryServiceException;
    }
}
