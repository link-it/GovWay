/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it).
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

package org.openspcoop2.core.config.ws.client.soggetto.all;

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
 * 2016-12-05T16:21:24.098+01:00
 * Generated source version: 3.1.7
 * 
 */
public final class Soggetto_SoggettoPortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/core/config/management", "SoggettoSoap11Service");

    private Soggetto_SoggettoPortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = SoggettoSoap11Service.WSDL_LOCATION;
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
      
        SoggettoSoap11Service ss = new SoggettoSoap11Service(wsdlURL, Soggetto_SoggettoPortSoap11_Client.SERVICE_NAME);
        Soggetto port = ss.getSoggettoPortSoap11();
	
		new org.openspcoop2.core.config.ws.client.utils.RequestContextUtils("soggetto.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking deleteById...");
        org.openspcoop2.core.config.IdSoggetto _deleteById_idSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        try {
            port.deleteById(_deleteById_idSoggetto);

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
        System.out.println("Invoking find...");
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto _find_filter = new org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto();
        try {
            org.openspcoop2.core.config.Soggetto _find__return = port.find(_find_filter);
            System.out.println("find.result=" + _find__return);

        } catch (ConfigNotFoundException_Exception e) { 
            System.out.println("Expected exception: config-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigMultipleResultException_Exception e) { 
            System.out.println("Expected exception: config-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking updateOrCreate...");
        org.openspcoop2.core.config.IdSoggetto _updateOrCreate_oldIdSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        org.openspcoop2.core.config.Soggetto _updateOrCreate_soggetto = new org.openspcoop2.core.config.Soggetto();
        try {
            port.updateOrCreate(_updateOrCreate_oldIdSoggetto, _updateOrCreate_soggetto);

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
        System.out.println("Invoking get...");
        org.openspcoop2.core.config.IdSoggetto _get_idSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        try {
            org.openspcoop2.core.config.Soggetto _get__return = port.get(_get_idSoggetto);
            System.out.println("get.result=" + _get__return);

        } catch (ConfigNotFoundException_Exception e) { 
            System.out.println("Expected exception: config-not-found-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigMultipleResultException_Exception e) { 
            System.out.println("Expected exception: config-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking delete...");
        org.openspcoop2.core.config.Soggetto _delete_soggetto = new org.openspcoop2.core.config.Soggetto();
        try {
            port.delete(_delete_soggetto);

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
        System.out.println("Invoking findAllIds...");
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto _findAllIds_filter = new org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto();
        try {
            java.util.List<org.openspcoop2.core.config.IdSoggetto> _findAllIds__return = port.findAllIds(_findAllIds_filter);
            System.out.println("findAllIds.result=" + _findAllIds__return);

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
        org.openspcoop2.core.config.Soggetto _create_soggetto = new org.openspcoop2.core.config.Soggetto();
        try {
            port.create(_create_soggetto);

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
        System.out.println("Invoking findAll...");
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto _findAll_filter = new org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto();
        try {
            java.util.List<org.openspcoop2.core.config.Soggetto> _findAll__return = port.findAll(_findAll_filter);
            System.out.println("findAll.result=" + _findAll__return);

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
        org.openspcoop2.core.config.IdSoggetto _update_oldIdSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        org.openspcoop2.core.config.Soggetto _update_soggetto = new org.openspcoop2.core.config.Soggetto();
        try {
            port.update(_update_oldIdSoggetto, _update_soggetto);

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
        System.out.println("Invoking inUse...");
        org.openspcoop2.core.config.IdSoggetto _inUse_idSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        try {
            org.openspcoop2.core.config.ws.client.soggetto.all.UseInfo _inUse__return = port.inUse(_inUse_idSoggetto);
            System.out.println("inUse.result=" + _inUse__return);

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
        System.out.println("Invoking exists...");
        org.openspcoop2.core.config.IdSoggetto _exists_idSoggetto = new org.openspcoop2.core.config.IdSoggetto();
        try {
            boolean _exists__return = port.exists(_exists_idSoggetto);
            System.out.println("exists.result=" + _exists__return);

        } catch (ConfigNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: config-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigNotImplementedException_Exception e) { 
            System.out.println("Expected exception: config-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigMultipleResultException_Exception e) { 
            System.out.println("Expected exception: config-multiple-result-exception has occurred.");
            System.out.println(e.toString());
        } catch (ConfigServiceException_Exception e) { 
            System.out.println("Expected exception: config-service-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking count...");
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto _count_filter = new org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto();
        try {
            long _count__return = port.count(_count_filter);
            System.out.println("count.result=" + _count__return);

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
        org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto _deleteAllByFilter_filter = new org.openspcoop2.core.config.ws.client.soggetto.all.SearchFilterSoggetto();
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
