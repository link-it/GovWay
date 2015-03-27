/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2015 Link.it srl (http://link.it).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2015-02-18T17:09:13.591+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebServiceClient(name = "TracciaSoap11Service", 
                  wsdlLocation = "deploy/wsdl/TracciaSearch_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/tracciamento/management") 
public class TracciaSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/tracciamento/management", "TracciaSoap11Service");
    public final static QName TracciaPortSoap11 = new QName("http://www.openspcoop2.org/core/tracciamento/management", "TracciaPortSoap11");
    static {
        URL url = TracciaSoap11Service.class.getResource("deploy/wsdl/TracciaSearch_PortSoap11.wsdl");
        if (url == null) {
            url = TracciaSoap11Service.class.getClassLoader().getResource("deploy/wsdl/TracciaSearch_PortSoap11.wsdl");
        } 
        if (url == null) {
            
		}
		if (url==null ){
			url = TracciaSoap11Service.class.getResource("/tracciamento/TracciaSearch_PortSoap11.wsdl");
		}
		if (url==null ){
			url = TracciaSoap11Service.class.getClassLoader().getResource("/tracciamento/TracciaSearch_PortSoap11.wsdl");
		}
		if (url==null ){
			java.util.logging.Logger.getLogger(TracciaSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "deploy/wsdl/TracciaSearch_PortSoap11.wsdl");
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
