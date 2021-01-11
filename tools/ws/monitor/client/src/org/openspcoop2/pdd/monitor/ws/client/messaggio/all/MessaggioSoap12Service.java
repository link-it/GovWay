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
package org.openspcoop2.pdd.monitor.ws.client.messaggio.all;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-12-01T13:16:06.106+01:00
 * Generated source version: 2.7.4
 * 
 */
@WebServiceClient(name = "MessaggioSoap12Service", 
                  wsdlLocation = "deploy/wsdl/MessaggioAll_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/pdd/monitor/management") 
public class MessaggioSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/pdd/monitor/management", "MessaggioSoap12Service");
    public final static QName MessaggioPortSoap12 = new QName("http://www.openspcoop2.org/pdd/monitor/management", "MessaggioPortSoap12");
    static {
        URL url = MessaggioSoap12Service.class.getResource("deploy/wsdl/MessaggioAll_PortSoap12.wsdl");
        if (url == null) {
            url = MessaggioSoap12Service.class.getClassLoader().getResource("deploy/wsdl/MessaggioAll_PortSoap12.wsdl");
        } 
        if (url == null) {
            
		}
		if (url==null ){
			url = MessaggioSoap12Service.class.getResource("/monitor/MessaggioAll_PortSoap12.wsdl");
		}
		if (url==null ){
			url = MessaggioSoap12Service.class.getClassLoader().getResource("/monitor/MessaggioAll_PortSoap12.wsdl");
		}
		if (url==null ){
			java.util.logging.Logger.getLogger(MessaggioSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "deploy/wsdl/MessaggioAll_PortSoap12.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public MessaggioSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, MessaggioSoap12Service.SERVICE);
    }

    public MessaggioSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MessaggioSoap12Service() {
        super(MessaggioSoap12Service.WSDL_LOCATION, MessaggioSoap12Service.SERVICE);
    }
    

    /**
     *
     * @return
     *     returns Messaggio
     */
    @WebEndpoint(name = "MessaggioPortSoap12")
    public Messaggio getMessaggioPortSoap12() {
        return super.getPort(MessaggioSoap12Service.MessaggioPortSoap12, Messaggio.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Messaggio
     */
    @WebEndpoint(name = "MessaggioPortSoap12")
    public Messaggio getMessaggioPortSoap12(WebServiceFeature... features) {
        return super.getPort(MessaggioSoap12Service.MessaggioPortSoap12, Messaggio.class, features);
    }

}
