/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
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
package org.openspcoop2.core.config.ws.client.portaapplicativa.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:48:42.009+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/config/management", name = "PortaApplicativa")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.config.ObjectFactory.class})
public interface PortaApplicativa {

    @WebResult(name = "idPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.FindAllIds")
    @WebMethod(action = "findAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.FindAllIdsResponse")
    public java.util.List<org.openspcoop2.core.config.IdPortaApplicativa> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.portaapplicativa.search.SearchFilterPortaApplicativa filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.Find")
    @WebMethod(action = "find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.FindResponse")
    public org.openspcoop2.core.config.PortaApplicativa find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.portaapplicativa.search.SearchFilterPortaApplicativa filter
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.Get")
    @WebMethod(action = "get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.GetResponse")
    public org.openspcoop2.core.config.PortaApplicativa get(
        @WebParam(name = "idPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa idPortaApplicativa
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "portaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.FindAll")
    @WebMethod(action = "findAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.FindAllResponse")
    public java.util.List<org.openspcoop2.core.config.PortaApplicativa> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.portaapplicativa.search.SearchFilterPortaApplicativa filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "inUse", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "inUse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.InUse")
    @WebMethod(action = "inUse")
    @ResponseWrapper(localName = "inUseResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.InUseResponse")
    public org.openspcoop2.core.config.ws.client.portaapplicativa.search.UseInfo inUse(
        @WebParam(name = "idPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa idPortaApplicativa
    ) throws ConfigNotFoundException_Exception, ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.Exists")
    @WebMethod(action = "exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.ExistsResponse")
    public boolean exists(
        @WebParam(name = "idPortaApplicativa", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.IdPortaApplicativa idPortaApplicativa
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigMultipleResultException_Exception, ConfigServiceException_Exception;

    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.Count")
    @WebMethod(action = "count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/config/management", className = "org.openspcoop2.core.config.ws.client.portaapplicativa.search.CountResponse")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/config/management")
        org.openspcoop2.core.config.ws.client.portaapplicativa.search.SearchFilterPortaApplicativa filter
    ) throws ConfigNotAuthorizedException_Exception, ConfigNotImplementedException_Exception, ConfigServiceException_Exception;
}
