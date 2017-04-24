
package org.openspcoop2.core.config.ws.client.soggetto.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:42:54.812+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "config-not-authorized-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigNotAuthorizedException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.config.ws.client.soggetto.search.ConfigNotAuthorizedException configNotAuthorizedException;

    public ConfigNotAuthorizedException_Exception() {
        super();
    }
    
    public ConfigNotAuthorizedException_Exception(String message) {
        super(message);
    }
    
    public ConfigNotAuthorizedException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigNotAuthorizedException_Exception(String message, org.openspcoop2.core.config.ws.client.soggetto.search.ConfigNotAuthorizedException configNotAuthorizedException) {
        super(message);
        this.configNotAuthorizedException = configNotAuthorizedException;
    }

    public ConfigNotAuthorizedException_Exception(String message, org.openspcoop2.core.config.ws.client.soggetto.search.ConfigNotAuthorizedException configNotAuthorizedException, Throwable cause) {
        super(message, cause);
        this.configNotAuthorizedException = configNotAuthorizedException;
    }

    public org.openspcoop2.core.config.ws.client.soggetto.search.ConfigNotAuthorizedException getFaultInfo() {
        return this.configNotAuthorizedException;
    }
}
