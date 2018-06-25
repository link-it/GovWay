/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it).
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

package org.openspcoop2.core.config.ws.client.portadelegata.crud;

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
 * 2017-04-24T11:46:15.978+02:00
 * Generated source version: 3.1.7
 * 
 */
public final class PortaDelegata_PortaDelegataPortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/config/management", "PortaDelegataSoap11Service");

    private PortaDelegata_PortaDelegataPortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = PortaDelegataSoap11Service.WSDL_LOCATION;
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
      
        PortaDelegataSoap11Service ss = new PortaDelegataSoap11Service(wsdlURL, PortaDelegata_PortaDelegataPortSoap11_Client.SERVICE_NAME);
        PortaDelegata port = ss.getPortaDelegataPortSoap11();
	
		new org.openspcoop2.core.config.ws.client.utils.RequestContextUtils("portaDelegata.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking deleteById...");
        org.openspcoop2.core.config.IdPortaDelegata _deleteById_idPortaDelegata = new org.openspcoop2.core.config.IdPortaDelegata();
        try {
            port.deleteById(_deleteById_idPortaDelegata);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAll...");
        try {
            long _deleteAll__return = port.deleteAll();
            System.out.println("deleteAll.result=" + _deleteAll__return);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking create...");
        org.openspcoop2.core.config.PortaDelegata _create_portaDelegata = new org.openspcoop2.core.config.PortaDelegata();
        try {
            port.create(_create_portaDelegata);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateOrCreate...");
        org.openspcoop2.core.config.IdPortaDelegata _updateOrCreate_oldIdPortaDelegata = new org.openspcoop2.core.config.IdPortaDelegata();
        org.openspcoop2.core.config.PortaDelegata _updateOrCreate_portaDelegata = new org.openspcoop2.core.config.PortaDelegata();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdPortaDelegata, _updateOrCreate_portaDelegata);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking delete...");
        org.openspcoop2.core.config.PortaDelegata _delete_portaDelegata = new org.openspcoop2.core.config.PortaDelegata();
        try {
            port.delete(_delete_portaDelegata);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking update...");
        org.openspcoop2.core.config.IdPortaDelegata _update_oldIdPortaDelegata = new org.openspcoop2.core.config.IdPortaDelegata();
        org.openspcoop2.core.config.PortaDelegata _update_portaDelegata = new org.openspcoop2.core.config.PortaDelegata();
        try {
            port.update(_update_oldIdPortaDelegata, _update_portaDelegata);

        } catch (ConfigNotFoundException_Exception e) { 
            System.out.println("Expected exception: config-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAllByFilter...");
        org.openspcoop2.core.config.ws.client.portadelegata.crud.SearchFilterPortaDelegata _deleteAllByFilter_filter = new org.openspcoop2.core.config.ws.client.portadelegata.crud.SearchFilterPortaDelegata();
        try {
            long _deleteAllByFilter__return = port.deleteAllByFilter(_deleteAllByFilter_filter);
            System.out.println("deleteAllByFilter.result=" + _deleteAllByFilter__return);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
