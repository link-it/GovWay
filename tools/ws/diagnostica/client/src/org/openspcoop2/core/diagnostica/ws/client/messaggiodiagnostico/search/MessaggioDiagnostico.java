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
package org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-26T14:57:40.096+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", name = "MessaggioDiagnostico")
@XmlSeeAlso({org.openspcoop2.core.diagnostica.ObjectFactory.class, ObjectFactory.class})
public interface MessaggioDiagnostico {

    @WebMethod(action = "count")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.CountResponse")
    @WebResult(name = "count", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    public long count(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotImplementedException_Exception, DiagnosticaNotAuthorizedException_Exception;

    @WebMethod(action = "findAll")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management", className = "org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.FindAllResponse")
    @WebResult(name = "messaggioDiagnostico", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
    public java.util.List<org.openspcoop2.core.diagnostica.MessaggioDiagnostico> findAll(
        @WebParam(name = "filter", targetNamespace = "http://www.openspcoop2.org/core/diagnostica/management")
        org.openspcoop2.core.diagnostica.ws.client.messaggiodiagnostico.search.SearchFilterMessaggioDiagnostico filter
    ) throws DiagnosticaServiceException_Exception, DiagnosticaNotImplementedException_Exception, DiagnosticaNotAuthorizedException_Exception;
}
