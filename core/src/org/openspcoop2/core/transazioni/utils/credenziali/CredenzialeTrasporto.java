/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 * 
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2019 Link.it srl (http://link.it).
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

package org.openspcoop2.core.transazioni.utils.credenziali;

import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.transazioni.utils.TipoCredenzialeMittente;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.certificate.CertificateUtils;
import org.openspcoop2.utils.certificate.PrincipalType;

/**     
 * CredenzialeTrasporto
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class CredenzialeTrasporto extends AbstractCredenziale {

	private String tipoAutenticazione;
	private String credential;
	
	public CredenzialeTrasporto(String tipoAutenticazione, String credential) {
		super(TipoCredenzialeMittente.trasporto);
		this.tipoAutenticazione = tipoAutenticazione;
		this.credential = credential;
	}
	
	@Override
	public String getTipo() throws UtilsException {
		return getTipoTrasporto(this.tipo, this.tipoAutenticazione);
	}

	public static String getTipoTrasporto(TipoCredenzialeMittente tipo, String tipoAutenticazione) {
		return tipo.name()+"_"+tipoAutenticazione;
	}
	
	@Override
	public String getCredenziale() throws UtilsException {
		if(isSsl(this.tipoAutenticazione)) {
			return CertificateUtils.formatPrincipal(this.credential, PrincipalType.subject);
		}
		else {
			return this.credential;
		}
	}
	
	public static boolean isSsl(String tipoAutenticazione) {
		return TipoAutenticazione.SSL.getValue().equalsIgnoreCase(tipoAutenticazione);
	}
	
	@Override
	public void updateCredenziale(String newCredential) throws UtilsException{
		this.credential = newCredential;
	}
}