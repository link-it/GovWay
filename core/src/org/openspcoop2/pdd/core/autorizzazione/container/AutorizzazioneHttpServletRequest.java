/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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
package org.openspcoop2.pdd.core.autorizzazione.container;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.resources.WrappedHttpServletRequest;


/**
 * AutorizzazioneHttpServletRequest
 * 
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AutorizzazioneHttpServletRequest extends WrappedHttpServletRequest {


	private IAutorizzazioneSecurityContainer authEngine;
	
	public AutorizzazioneHttpServletRequest(HttpServletRequest httpServletRequest, IAutorizzazioneSecurityContainer authEngine) throws UtilsException{
		super(httpServletRequest);
		
		this.authEngine = authEngine;
	}
	
	@Override
	public Principal getUserPrincipal() {
		String p = this.authEngine.getUserPrincipal();
		if(p!=null) {
			return new AutorizzazioneSecurityContainerPrincipal(p);
		}
		return null;
	}
	
	@Override
	public boolean isUserInRole(String role) {
		return this.authEngine.isUserInRole(role);
	}
}

