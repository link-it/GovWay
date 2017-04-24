
package org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T12:06:25.484+02:00
 * Generated source version: 3.1.7
 * 
 */
public final class AccordoServizioParteSpecifica_AccordoServizioParteSpecificaPortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoServizioParteSpecificaSoap11Service");

    private AccordoServizioParteSpecifica_AccordoServizioParteSpecificaPortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = AccordoServizioParteSpecificaSoap11Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        AccordoServizioParteSpecificaSoap11Service ss = new AccordoServizioParteSpecificaSoap11Service(wsdlURL, AccordoServizioParteSpecifica_AccordoServizioParteSpecificaPortSoap11_Client.SERVICE_NAME);
        AccordoServizioParteSpecifica port = ss.getAccordoServizioParteSpecificaPortSoap11();
	
		new org.openspcoop2.core.registry.ws.client.utils.RequestContextUtils("accordoServizioParteSpecifica.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking deleteAll...");
        try {
            long _deleteAll__return = port.deleteAll();
            System.out.println("deleteAll.result=" + _deleteAll__return);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking delete...");
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica _delete_accordoServizioParteSpecifica = new org.openspcoop2.core.registry.AccordoServizioParteSpecifica();
        try {
            port.delete(_delete_accordoServizioParteSpecifica);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteById...");
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica _deleteById_idAccordoServizioParteSpecifica = new org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica();
        try {
            port.deleteById(_deleteById_idAccordoServizioParteSpecifica);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateOrCreate...");
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica _updateOrCreate_oldIdAccordoServizioParteSpecifica = new org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica();
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica _updateOrCreate_accordoServizioParteSpecifica = new org.openspcoop2.core.registry.AccordoServizioParteSpecifica();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdAccordoServizioParteSpecifica, _updateOrCreate_accordoServizioParteSpecifica);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAllByFilter...");
        org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.SearchFilterAccordoServizioParteSpecifica _deleteAllByFilter_filter = new org.openspcoop2.core.registry.ws.client.accordoserviziopartespecifica.crud.SearchFilterAccordoServizioParteSpecifica();
        try {
            long _deleteAllByFilter__return = port.deleteAllByFilter(_deleteAllByFilter_filter);
            System.out.println("deleteAllByFilter.result=" + _deleteAllByFilter__return);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking create...");
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica _create_accordoServizioParteSpecifica = new org.openspcoop2.core.registry.AccordoServizioParteSpecifica();
        try {
            port.create(_create_accordoServizioParteSpecifica);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking update...");
        org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica _update_oldIdAccordoServizioParteSpecifica = new org.openspcoop2.core.registry.IdAccordoServizioParteSpecifica();
        org.openspcoop2.core.registry.AccordoServizioParteSpecifica _update_accordoServizioParteSpecifica = new org.openspcoop2.core.registry.AccordoServizioParteSpecifica();
        try {
            port.update(_update_oldIdAccordoServizioParteSpecifica, _update_accordoServizioParteSpecifica);

        } catch (RegistryNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: registry-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryServiceException_Exception e) { 
            System.out.println("Expected exception: registry-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotImplementedException_Exception e) { 
            System.out.println("Expected exception: registry-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (RegistryNotFoundException_Exception e) { 
            System.out.println("Expected exception: registry-not-found-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
