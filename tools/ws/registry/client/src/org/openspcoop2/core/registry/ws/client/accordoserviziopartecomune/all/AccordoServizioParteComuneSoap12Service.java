package org.openspcoop2.core.registry.ws.client.accordoserviziopartecomune.all;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:59:06.021+02:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "AccordoServizioParteComuneSoap12Service", 
                  wsdlLocation = "file:deploy/wsdl/AccordoServizioParteComuneAll_PortSoap12.wsdl",
                  targetNamespace = "http://www.openspcoop2.org/core/registry/management") 
public class AccordoServizioParteComuneSoap12Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComuneSoap12Service");
    public final static QName AccordoServizioParteComunePortSoap12 = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteComunePortSoap12");
    static {
        URL url = null;
        try {
            url = new URL("file:deploy/wsdl/AccordoServizioParteComuneAll_PortSoap12.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AccordoServizioParteComuneSoap12Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:deploy/wsdl/AccordoServizioParteComuneAll_PortSoap12.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AccordoServizioParteComuneSoap12Service(URL wsdlLocation) {
        super(wsdlLocation, AccordoServizioParteComuneSoap12Service.SERVICE);
    }

    public AccordoServizioParteComuneSoap12Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AccordoServizioParteComuneSoap12Service() {
        super(AccordoServizioParteComuneSoap12Service.WSDL_LOCATION, AccordoServizioParteComuneSoap12Service.SERVICE);
    }
    




    /**
     *
     * @return
     *     returns AccordoServizioParteComune
     */
    @WebEndpoint(name = "AccordoServizioParteComunePortSoap12")
    public AccordoServizioParteComune getAccordoServizioParteComunePortSoap12() {
        return super.getPort(AccordoServizioParteComuneSoap12Service.AccordoServizioParteComunePortSoap12, AccordoServizioParteComune.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AccordoServizioParteComune
     */
    @WebEndpoint(name = "AccordoServizioParteComunePortSoap12")
    public AccordoServizioParteComune getAccordoServizioParteComunePortSoap12(WebServiceFeature... features) {
        return super.getPort(AccordoServizioParteComuneSoap12Service.AccordoServizioParteComunePortSoap12, AccordoServizioParteComune.class, features);
    }

}
