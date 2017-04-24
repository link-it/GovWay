
package org.openspcoop2.core.config.ws.client.portadelegata.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:44:50.670+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "config-multiple-result-exception", targetNamespace = "http://www.openspcoop2.org/core/config/management")
public class ConfigMultipleResultException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigMultipleResultException configMultipleResultException;

    public ConfigMultipleResultException_Exception() {
        super();
    }
    
    public ConfigMultipleResultException_Exception(String message) {
        super(message);
    }
    
    public ConfigMultipleResultException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigMultipleResultException_Exception(String message, org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigMultipleResultException configMultipleResultException) {
        super(message);
        this.configMultipleResultException = configMultipleResultException;
    }

    public ConfigMultipleResultException_Exception(String message, org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigMultipleResultException configMultipleResultException, Throwable cause) {
        super(message, cause);
        this.configMultipleResultException = configMultipleResultException;
    }

    public org.openspcoop2.core.config.ws.client.portadelegata.all.ConfigMultipleResultException getFaultInfo() {
        return this.configMultipleResultException;
    }
}
