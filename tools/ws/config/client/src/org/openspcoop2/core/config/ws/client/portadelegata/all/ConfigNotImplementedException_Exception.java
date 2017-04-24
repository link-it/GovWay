
package org.openspcoop2.core.config.ws.client.portadelegata.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:44:50.649+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "config-not-implemented-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigNotImplementedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigNotImplementedException configNotImplementedException;

    public ConfigNotImplementedException_Exception() {
        super();
    }
    
    public ConfigNotImplementedException_Exception(String message) {
        super(message);
    }
    
    public ConfigNotImplementedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigNotImplementedException_Exception(String message, org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigNotImplementedException configNotImplementedException) {
        super(message);
        this.configNotImplementedException = configNotImplementedException;
    }

    public ConfigNotImplementedException_Exception(String message, org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigNotImplementedException configNotImplementedException, Throwable cause) {
        super(message, cause);
        this.configNotImplementedException = configNotImplementedException;
    }

    public org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigNotImplementedException getFaultInfo() {
        return this.configNotImplementedException;
    }
}
