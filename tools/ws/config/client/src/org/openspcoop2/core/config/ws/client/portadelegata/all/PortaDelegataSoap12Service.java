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
package org.openspcoop2.core.config.ws.client.portadelegata.all;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:44:50.688+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "PortaDelegataSoap12Service", 
                  wsdlLocation = "file:deploy/wsdl/PortaDelegataAll_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/config/management") 
public class PortaDelegataSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/config/management", "PortaDelegataSoap12Service");
    public final static QName PortaDelegataPortSoap12 = new QName("http://www.openspcoop2.org/core/config/management", "PortaDelegataPortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/PortaDelegataAll_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(PortaDelegataSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/PortaDelegataAll_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public PortaDelegataSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, PortaDelegataSoap12Service.SERVICE);
    }

    public PortaDelegataSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PortaDelegataSoap12Service() {
        super(PortaDelegataSoap12Service.WSDL_LOCATION, PortaDelegataSoap12Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns PortaDelegata
     */
    @WebEndpoint(name = "PortaDelegataPortSoap12")
    public PortaDelegata getPortaDelegataPortSoap12() {
        return super.getPort(PortaDelegataSoap12Service.PortaDelegataPortSoap12, PortaDelegata.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PortaDelegata
     */
    @WebEndpoint(name = "PortaDelegataPortSoap12")
    public PortaDelegata getPortaDelegataPortSoap12(WebServiceFeature... features) {
        return super.getPort(PortaDelegataSoap12Service.PortaDelegataPortSoap12, PortaDelegata.class, features);
    }

}
