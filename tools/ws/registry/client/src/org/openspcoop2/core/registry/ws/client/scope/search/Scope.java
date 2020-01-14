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
package org.openspcoop2.core.registry.ws.client.scope.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.6
 * 2019-09-19T15:21:50.063+02:00
 * Generated source version: 3.2.6
 *
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/registry/management", name = "Scope")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.registry.ObjectFactory.class})
public interface Scope {

    @WebMethod(action = "find")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.Find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.FindResponse")
    @WebResult(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.Scope find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.scope.search.SearchFilterScope filter
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "get")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.Get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.GetResponse")
    @WebResult(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.Scope get(
        @WebParam(name = "idScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope idScope
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "findAllIds")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.FindAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.FindAllIdsResponse")
    @WebResult(name = "idScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public java.util.List<org.openspcoop2.core.registry.IdScope> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.scope.search.SearchFilterScope filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "count")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.CountResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.scope.search.SearchFilterScope filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "inUse")
    @RequestWrapper(localName = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.InUse")
    @ResponseWrapper(localName = "inUseResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.InUseResponse")
    @WebResult(name = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.ws.client.scope.search.UseInfo inUse(
        @WebParam(name = "idScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope idScope
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "exists")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.Exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.ExistsResponse")
    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public boolean exists(
        @WebParam(name = "idScope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdScope idScope
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "findAll")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.scope.search.FindAllResponse")
    @WebResult(name = "scope", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public java.util.List<org.openspcoop2.core.registry.Scope> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.scope.search.SearchFilterScope filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;
}
