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
package org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.6
 * 2019-09-19T15:24:53.154+02:00
 * Generated source version: 3.2.6
 *
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/registry/management", name = "AccordoServizioParteSpecifica")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.registry.ObjectFactory.class})
public interface AccordoServizioParteSpecifica {

    @WebMethod(action = "find")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.Find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.FindResponse")
    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.AccordoServizioParteSpecifica find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "get")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.Get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.GetResponse")
    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.AccordoServizioParteSpecifica get(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "findAllIds")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.FindAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.FindAllIdsResponse")
    @WebResult(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public java.util.List<org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "count")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.CountResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "inUse")
    @RequestWrapper(localName = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.InUse")
    @ResponseWrapper(localName = "inUseResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.InUseResponse")
    @WebResult(name = "inUse", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.UseInfo inUse(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryServiceException_Exception, RegistryNotFoundException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "exists")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.Exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.ExistsResponse")
    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public boolean exists(
        @WebParam(name = "idAccordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica idAccordoServizioParteSpecifica
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryMultipleResultException_Exception, RegistryNotAuthorizedException_Exception;

    @WebMethod(action = "findAll")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/registry/management", className = "org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.FindAllResponse")
    @WebResult(name = "accordoServizioParteSpecifica", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
    public java.util.List<org.openspcoop2.core.registry.AccordoServizioParteSpecifica> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.search.SearchFilterAccordoServizioParteSpecifica filter
    ) throws RegistryServiceException_Exception, RegistryNotImplementedException_Exception, RegistryNotAuthorizedException_Exception;
}
