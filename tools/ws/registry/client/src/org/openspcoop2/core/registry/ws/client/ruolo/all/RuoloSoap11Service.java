package org.openspcoop2.core.registry.ws.client.ruolo.all;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T12:02:12.504+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "RuoloSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/RuoloAll_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/registry/management") 
public class RuoloSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/registry/management", "RuoloSoap11Service");
    public final static QName RuoloPortSoap11 = new QName("http://www.openspcoop2.org/core/registry/management", "RuoloPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/RuoloAll_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(RuoloSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/RuoloAll_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public RuoloSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, RuoloSoap11Service.SERVICE);
    }

    public RuoloSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RuoloSoap11Service() {
        super(RuoloSoap11Service.WSDL_LOCATION, RuoloSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns Ruolo
     */
    @WebEndpoint(name = "RuoloPortSoap11")
    public Ruolo getRuoloPortSoap11() {
        return super.getPort(RuoloSoap11Service.RuoloPortSoap11, Ruolo.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Ruolo
     */
    @WebEndpoint(name = "RuoloPortSoap11")
    public Ruolo getRuoloPortSoap11(WebServiceFeature... features) {
        return super.getPort(RuoloSoap11Service.RuoloPortSoap11, Ruolo.class, features);
    }

}
