
package org.openspcoop2.core.config.ws.client.soggetto.search;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:42:54.828+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "config-service-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigServiceException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.config.ws.client.soggetto.search.ConfigServiceException configServiceException;

    public ConfigServiceException_Exception() {
        super();
    }
    
    public ConfigServiceException_Exception(String message) {
        super(message);
    }
    
    public ConfigServiceException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigServiceException_Exception(String message, org.openspcoop2.core.config.ws.client.soggetto.search.ConfigServiceException configServiceException) {
        super(message);
        this.configServiceException = configServiceException;
    }

    public ConfigServiceException_Exception(String message, org.openspcoop2.core.config.ws.client.soggetto.search.ConfigServiceException configServiceException, Throwable cause) {
        super(message, cause);
        this.configServiceException = configServiceException;
    }

    public org.openspcoop2.core.config.ws.client.soggetto.search.ConfigServiceException getFaultInfo() {
        return this.configServiceException;
    }
}
