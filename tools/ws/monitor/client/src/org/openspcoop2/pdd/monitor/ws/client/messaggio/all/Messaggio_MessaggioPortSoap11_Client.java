/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it).
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

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-12-01T13:15:58.077+01:00
 * Generated source version: 2.7.4
 * 
 */
public final class Messaggio_MessaggioPortSoap11_Client {

    private static final QName SERVICE_NAME = new QName("http://www.openspcoop2.org/pdd/monitor/management", "MessaggioSoap11Service");

    private Messaggio_MessaggioPortSoap11_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = MessaggioSoap11Service.WSDL_LOCATION;
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
      
        MessaggioSoap11Service ss = new MessaggioSoap11Service(wsdlURL, Messaggio_MessaggioPortSoap11_Client.SERVICE_NAME);
        Messaggio port = ss.getMessaggioPortSoap11();
	
		new org.openspcoop2.pdd.monitor.ws.client.utils.RequestContextUtils("messaggio.soap11").addRequestContextParameters((javax.xml.ws.BindingProvider)port);  
        
        {
        System.out.println("Invoking count...");
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio _count_filter = new SearchFilterMessaggio();
        try {
            long _count__return = port.count(_count_filter);
            System.out.println("count.result=" + _count__return);

        } catch (MonitorServiceException_Exception e) { 
            System.out.println("Expected exception: monitor-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotImplementedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking deleteAllByFilter...");
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio _deleteAllByFilter_filter = new SearchFilterMessaggio();
        try {
            long _deleteAllByFilter__return = port.deleteAllByFilter(_deleteAllByFilter_filter);
            System.out.println("deleteAllByFilter.result=" + _deleteAllByFilter__return);

        } catch (MonitorServiceException_Exception e) { 
            System.out.println("Expected exception: monitor-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotImplementedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking findAll...");
        org.openspcoop2.pdd.monitor.ws.client.messaggio.all.SearchFilterMessaggio _findAll_filter = new SearchFilterMessaggio();
        try {
            java.util.List<org.openspcoop2.pdd.monitor.Messaggio> _findAll__return = port.findAll(_findAll_filter);
            System.out.println("findAll.result=" + _findAll__return.size());

        } catch (MonitorServiceException_Exception e) { 
            System.out.println("Expected exception: monitor-service-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotImplementedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-implemented-exception has occurred.");
            System.out.println(e.toString());
        } catch (MonitorNotAuthorizedException_Exception e) { 
            System.out.println("Expected exception: monitor-not-authorized-exception has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
