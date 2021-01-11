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
package org.openspcoop2.example.pdd.client.sdi.trasmissione_fatture;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-10-10T14:24:52.622+02:00
 * Generated source version: 2.7.4
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
@WebServiceClient(name = "TrasmissioneFatture_service", 
                  wsdlLocation = "TrasmissioneFatture_v1.1.wsdl",
                  targetNamespace = "http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0") 
public class TrasmissioneFattureService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0", "TrasmissioneFatture_service");
    public final static QName TrasmissioneFatturePort = new QName("http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0", "TrasmissioneFatture_port");
    static {
        URL url = TrasmissioneFattureService.class.getResource("TrasmissioneFatture_v1.1.wsdl");
        if (url == null) {
            url = TrasmissioneFattureService.class.getClassLoader().getResource("TrasmissioneFatture_v1.1.wsdl");
        } 
        if (url == null) {
        	System.out.println("Can not initialize the default wsdl from TrasmissioneFatture_v1.1.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public TrasmissioneFattureService(URL wsdlLocation) {
        super(wsdlLocation, TrasmissioneFattureService.SERVICE);
    }

    public TrasmissioneFattureService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TrasmissioneFattureService() {
        super(TrasmissioneFattureService.WSDL_LOCATION, TrasmissioneFattureService.SERVICE);
    }
    
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public TrasmissioneFattureService(WebServiceFeature ... features) {
//        super(TrasmissioneFattureService.WSDL_LOCATION, TrasmissioneFattureService.SERVICE, features);
//    }
//
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public TrasmissioneFattureService(URL wsdlLocation, WebServiceFeature ... features) {
//        super(wsdlLocation, TrasmissioneFattureService.SERVICE, features);
//    }
//
//    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
//    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
//    //compliant code instead.
//    public TrasmissioneFattureService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
//        super(wsdlLocation, serviceName, features);
//    }

    /**
     *
     * @return
     *     returns TrasmissioneFatture
     */
    @WebEndpoint(name = "TrasmissioneFatture_port")
    public TrasmissioneFatture getTrasmissioneFatturePort() {
        return super.getPort(TrasmissioneFattureService.TrasmissioneFatturePort, TrasmissioneFatture.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TrasmissioneFatture
     */
    @WebEndpoint(name = "TrasmissioneFatture_port")
    public TrasmissioneFatture getTrasmissioneFatturePort(WebServiceFeature... features) {
        return super.getPort(TrasmissioneFattureService.TrasmissioneFatturePort, TrasmissioneFatture.class, features);
    }

}
