
package org.openspcoop2.core.registry.ws.client.accordocooperazione.all;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-04-24T11:57:22.725+02:00
 * Generated source version: 3.1.7
 */

@WebFault(name = "registry-not-found-exception", targetNamespace = "http://www.openspcoop2.org/core/registry/management")
public class RegistryNotFoundException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.openspcoop2.core.registry.ws.client.accordocooperazione.all.RegistryNotFoundException registryNotFoundException;

    public RegistryNotFoundException_Exception() {
        super();
    }
    
    public RegistryNotFoundException_Exception(String message) {
        super(message);
    }
    
    public RegistryNotFoundException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistryNotFoundException_Exception(String message, org.openspcoop2.core.registry.ws.client.accordocooperazione.all.RegistryNotFoundException registryNotFoundException) {
        super(message);
        this.registryNotFoundException = registryNotFoundException;
    }

    public RegistryNotFoundException_Exception(String message, org.openspcoop2.core.registry.ws.client.accordocooperazione.all.RegistryNotFoundException registryNotFoundException, Throwable cause) {
        super(message, cause);
        this.registryNotFoundException = registryNotFoundException;
    }

    public org.openspcoop2.core.registry.ws.client.accordocooperazione.all.RegistryNotFoundException getFaultInfo() {
        return this.registryNotFoundException;
    }
}
