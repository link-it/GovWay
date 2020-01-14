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
package org.openspcoop2.core.registry.ws.client.soggetto.crud;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.6
  * 2019-09-19T15:23:51.759+02:00
 * Generated source version: 3.2.6
 *
 */
@WebServiceClient(name = "SoggettoSoap12Service",
                  wsdlLocation = "file:deploy/wsdl/SoggettoCRUD_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class SoggettoSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/registry/management", "SoggettoSoap12Service");
    public final static QName SoggettoPortSoap12 = new QName("http://www.openspcoop2.org/core/registry/management", "SoggettoPortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/SoggettoCRUD_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SoggettoSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/SoggettoCRUD_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SoggettoSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, SoggettoSoap12Service.SERVICE);
    }

    public SoggettoSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SoggettoSoap12Service() {
        super(SoggettoSoap12Service.WSDL_LOCATION, SoggettoSoap12Service.SERVICE);
    }





    /**
     *
     * @return
     *     returns Soggetto
     */
    @WebEndpoint(name = "SoggettoPortSoap12")
    public Soggetto getSoggettoPortSoap12() {
        return super.getPort(SoggettoSoap12Service.SoggettoPortSoap12, Soggetto.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Soggetto
     */
    @WebEndpoint(name = "SoggettoPortSoap12")
    public Soggetto getSoggettoPortSoap12(WebServiceFeature... features) {
        return super.getPort(SoggettoSoap12Service.SoggettoPortSoap12, Soggetto.class, features);
    }

}
