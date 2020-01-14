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
package org.openspcoop2.core.config.ws.client.portaapplicativa.search;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:48:11.938+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "PortaApplicativaSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/PortaApplicativaSearch_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/config/management") 
public class PortaApplicativaSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/config/management", "PortaApplicativaSoap11Service");
    public final static QName PortaApplicativaPortSoap11 = new QName("http://www.openspcoop2.org/core/config/management", "PortaApplicativaPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/PortaApplicativaSearch_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(PortaApplicativaSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/PortaApplicativaSearch_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public PortaApplicativaSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, PortaApplicativaSoap11Service.SERVICE);
    }

    public PortaApplicativaSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PortaApplicativaSoap11Service() {
        super(PortaApplicativaSoap11Service.WSDL_LOCATION, PortaApplicativaSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns PortaApplicativa
     */
    @WebEndpoint(name = "PortaApplicativaPortSoap11")
    public PortaApplicativa getPortaApplicativaPortSoap11() {
        return super.getPort(PortaApplicativaSoap11Service.PortaApplicativaPortSoap11, PortaApplicativa.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PortaApplicativa
     */
    @WebEndpoint(name = "PortaApplicativaPortSoap11")
    public PortaApplicativa getPortaApplicativaPortSoap11(WebServiceFeature... features) {
        return super.getPort(PortaApplicativaSoap11Service.PortaApplicativaPortSoap11, PortaApplicativa.class, features);
    }

}
