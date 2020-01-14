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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-26T15:02:43.250+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", name = "Traccia")
@XmlSeeAlso({org.openspcoop2.core.tracciamento.ObjectFactory.class, ObjectFactory.class})
public interface Traccia {

    @WebMethod(action = "findAll")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.FindAllResponse")
    @WebResult(name = "traccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public java.util.List<org.openspcoop2.core.tracciamento.Traccia> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.ws.client.traccia.search.SearchFilterTraccia filter
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoNotAuthorizedException_Exception;

    @WebMethod(action = "count")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.CountResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.ws.client.traccia.search.SearchFilterTraccia filter
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoNotAuthorizedException_Exception;

    @WebMethod(action = "exists")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.Exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.ExistsResponse")
    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public boolean exists(
        @WebParam(name = "idTraccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.IdTraccia idTraccia
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoMultipleResultException_Exception, TracciamentoNotAuthorizedException_Exception;

    @WebMethod(action = "findAllIds")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.FindAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.FindAllIdsResponse")
    @WebResult(name = "idTraccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public java.util.List<org.openspcoop2.core.tracciamento.IdTraccia> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.ws.client.traccia.search.SearchFilterTraccia filter
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoNotAuthorizedException_Exception;

    @WebMethod(action = "find")
    @RequestWrapper(localName = "find", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.Find")
    @ResponseWrapper(localName = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.FindResponse")
    @WebResult(name = "traccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public org.openspcoop2.core.tracciamento.Traccia find(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.ws.client.traccia.search.SearchFilterTraccia filter
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoMultipleResultException_Exception, TracciamentoNotFoundException_Exception, TracciamentoNotAuthorizedException_Exception;

    @WebMethod(action = "get")
    @RequestWrapper(localName = "get", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.Get")
    @ResponseWrapper(localName = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management", className = "org.openspcoop2.core.tracciamento.ws.client.traccia.search.GetResponse")
    @WebResult(name = "traccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
    public org.openspcoop2.core.tracciamento.Traccia get(
        @WebParam(name = "idTraccia", targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management")
        org.openspcoop2.core.tracciamento.IdTraccia idTraccia
    ) throws TracciamentoServiceException_Exception, TracciamentoNotImplementedException_Exception, TracciamentoMultipleResultException_Exception, TracciamentoNotFoundException_Exception, TracciamentoNotAuthorizedException_Exception;
}
