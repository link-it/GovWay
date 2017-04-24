
package org.openspcoop2.core.registry.ws.client.accordocooperazione.crud;

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
 * 2017-04-24T11:58:11.439+02:00
 * Generated source version: 3.1.7
 * 
 */
public final class AccordoCooperazione_AccordoCooperazionePortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/registry/management", "AccordoCooperazioneSoap11Service");

    private AccordoCooperazione_AccordoCooperazionePortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = AccordoCooperazioneSoap11Service.WSDL_LOCATION;
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
      
        AccordoCooperazioneSoap11Service ss = new AccordoCooperazioneSoap11Service(wsdlURL, AccordoCooperazione_AccordoCooperazionePortSoap11_Client.SERVICE_NAME);
        AccordoCooperazione port = ss.getAccordoCooperazionePortSoap11();
	
		new org.openspcoop2.core.registry.ws.client.utils.RequestContextUtils("accordoCooperazione.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
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
        org.openspcoop2.core.registry.AccordoCooperazione _delete_accordoCooperazione = new org.openspcoop2.core.registry.AccordoCooperazione();
        try {
            port.delete(_delete_accordoCooperazione);

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
        org.openspcoop2.core.registry.IdAccordoCooperazione _deleteById_idAccordoCooperazione = new org.openspcoop2.core.registry.IdAccordoCooperazione();
        try {
            port.deleteById(_deleteById_idAccordoCooperazione);

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
        org.openspcoop2.core.registry.IdAccordoCooperazione _updateOrCreate_oldIdAccordoCooperazione = new org.openspcoop2.core.registry.IdAccordoCooperazione();
        org.openspcoop2.core.registry.AccordoCooperazione _updateOrCreate_accordoCooperazione = new org.openspcoop2.core.registry.AccordoCooperazione();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdAccordoCooperazione, _updateOrCreate_accordoCooperazione);

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
        org.openspcoop2.core.registry.ws.client.accordocooperazione.crud.SearchFilterAccordoCooperazione _deleteAllByFilter_filter = new org.openspcoop2.core.registry.ws.client.accordocooperazione.crud.SearchFilterAccordoCooperazione();
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
        org.openspcoop2.core.registry.AccordoCooperazione _create_accordoCooperazione = new org.openspcoop2.core.registry.AccordoCooperazione();
        try {
            port.create(_create_accordoCooperazione);

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
        org.openspcoop2.core.registry.IdAccordoCooperazione _update_oldIdAccordoCooperazione = new org.openspcoop2.core.registry.IdAccordoCooperazione();
        org.openspcoop2.core.registry.AccordoCooperazione _update_accordoCooperazione = new org.openspcoop2.core.registry.AccordoCooperazione();
        try {
            port.update(_update_oldIdAccordoCooperazione, _update_accordoCooperazione);

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
