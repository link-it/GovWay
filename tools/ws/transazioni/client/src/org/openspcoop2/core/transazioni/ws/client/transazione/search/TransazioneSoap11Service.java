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

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2018-03-23T14:34:37.880+01:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "TransazioneSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/TransazioneSearch_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/transazioni/management") 
public class TransazioneSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/transazioni/management", "TransazioneSoap11Service");
    public final static QName TransazionePortSoap11 = new QName("http://www.openspcoop2.org/core/transazioni/management", "TransazionePortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/TransazioneSearch_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TransazioneSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/TransazioneSearch_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TransazioneSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, TransazioneSoap11Service.SERVICE);
    }

    public TransazioneSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransazioneSoap11Service() {
        super(TransazioneSoap11Service.WSDL_LOCATION, TransazioneSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns Transazione
     */
    @WebEndpoint(name = "TransazionePortSoap11")
    public Transazione getTransazionePortSoap11() {
        return super.getPort(TransazioneSoap11Service.TransazionePortSoap11, Transazione.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Transazione
     */
    @WebEndpoint(name = "TransazionePortSoap11")
    public Transazione getTransazionePortSoap11(WebServiceFeature... features) {
        return super.getPort(TransazioneSoap11Service.TransazionePortSoap11, Transazione.class, features);
    }

}
