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
package org.openspcoop2.core.transazioni.ws.client.transazione.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-23T14:34:42.842+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", name = "Transazione")
@XmlSeeAlso({ObjectFactory.class, org.openspcoop2.core.transazioni.ObjectFactory.class})
public interface Transazione {

    @WebMethod(action = "find")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "findResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", partName = "findResponse")
    public FindResponse find(
        @WebParam(partName = "find", name = "find", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        Find find
    ) throws TransazioniServiceException_Exception, TransazioniNotFoundException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception, TransazioniMultipleResultException_Exception;

    @WebMethod(action = "get")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "getResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", partName = "getResponse")
    public GetResponse get(
        @WebParam(partName = "get", name = "get", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        Get get
    ) throws TransazioniServiceException_Exception, TransazioniNotFoundException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception, TransazioniMultipleResultException_Exception;

    @WebMethod(action = "findAllIds")
    @RequestWrapper(localName = "findAllIds", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.FindAllIds")
    @ResponseWrapper(localName = "findAllIdsResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.FindAllIdsResponse")
    @WebResult(name = "idTransazione", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
    public java.util.List<java.lang.String> findAllIds(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        org.openspcoop2.core.transazioni.ws.client.transazione.search.SearchFilterTransazione filter
    ) throws TransazioniServiceException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception;

    @WebMethod(action = "findAll")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(name = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", partName = "findAllResponse")
    public FindAllResponse findAll(
        @WebParam(partName = "findAll", name = "findAll", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        FindAll findAll
    ) throws TransazioniServiceException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception;

    @WebMethod(action = "count")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.CountResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        org.openspcoop2.core.transazioni.ws.client.transazione.search.SearchFilterTransazione filter
    ) throws TransazioniServiceException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception;

    @WebMethod(action = "exists")
    @RequestWrapper(localName = "exists", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.Exists")
    @ResponseWrapper(localName = "existsResponse", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management", className = "org.openspcoop2.core.transazioni.ws.client.transazione.search.ExistsResponse")
    @WebResult(name = "exists", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
    public boolean exists(
        @WebParam(name = "idTransazione", targetNamespace = "http://www.openspcoop2.org/core/transazioni/management")
        java.lang.String idTransazione
    ) throws TransazioniServiceException_Exception, TransazioniNotImplementedException_Exception, TransazioniNotAuthorizedException_Exception, TransazioniMultipleResultException_Exception;
}
