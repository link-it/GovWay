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
package org.openspcoop2.core.tracciamento.ws.client.traccia.search;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-26T15:02:33.033+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "TracciaSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/TracciaSearch_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management") 
public class TracciaSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/tracciamento/management", "TracciaSoap11Service");
    public final static QName TracciaPortSoap11 = new QName("http://www.openspcoop2.org/core/tracciamento/management", "TracciaPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/TracciaSearch_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TracciaSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/TracciaSearch_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TracciaSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, TracciaSoap11Service.SERVICE);
    }

    public TracciaSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TracciaSoap11Service() {
        super(TracciaSoap11Service.WSDL_LOCATION, TracciaSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns Traccia
     */
    @WebEndpoint(name = "TracciaPortSoap11")
    public Traccia getTracciaPortSoap11() {
        return super.getPort(TracciaSoap11Service.TracciaPortSoap11, Traccia.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Traccia
     */
    @WebEndpoint(name = "TracciaPortSoap11")
    public Traccia getTracciaPortSoap11(WebServiceFeature... features) {
        return super.getPort(TracciaSoap11Service.TracciaPortSoap11, Traccia.class, features);
    }

}
