package org.openspcoop2.core.config.ws.client.soggetto.crud;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:43:24.824+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "SoggettoSoap11Service", 
                  wsdlLocation = "file:deploy/wsdl/SoggettoCRUD_PortSoap11.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/config/management") 
public class SoggettoSoap11Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/config/management", "SoggettoSoap11Service");
    public final static QName SoggettoPortSoap11 = new QName("http://www.openspcoop2.org/core/config/management", "SoggettoPortSoap11");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/SoggettoCRUD_PortSoap11.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SoggettoSoap11Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/SoggettoCRUD_PortSoap11.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SoggettoSoap11Service(URL wsdlLocation) {
        super(wsdlLocation, SoggettoSoap11Service.SERVICE);
    }

    public SoggettoSoap11Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SoggettoSoap11Service() {
        super(SoggettoSoap11Service.WSDL_LOCATION, SoggettoSoap11Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns Soggetto
     */
    @WebEndpoint(name = "SoggettoPortSoap11")
    public Soggetto getSoggettoPortSoap11() {
        return super.getPort(SoggettoSoap11Service.SoggettoPortSoap11, Soggetto.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Soggetto
     */
    @WebEndpoint(name = "SoggettoPortSoap11")
    public Soggetto getSoggettoPortSoap11(WebServiceFeature... features) {
        return super.getPort(SoggettoSoap11Service.SoggettoPortSoap11, Soggetto.class, features);
    }

}
