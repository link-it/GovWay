/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
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
package org.openspcoop2.utils;

import java.security.Principal;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;

/**
 * Identity
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Identity {

	// getUserPrincipal (SERVLET API)
	private String principalName;
	private Principal principal;
	
	// SSL (HTTPS)
	private String subject;
	private java.security.cert.X509Certificate[] certs;
	
	// Basic (HTTP-Based)
	private String username;
	private String password;
	
	// Servlet Request
	protected HttpServletRequest httpServletRequest;
	
	
	public Identity(){
		
	}
	public Identity(HttpServletRequest req){
		this(req, null);
	}
	public Identity(HttpServletRequest req,Logger log){
		
		this.httpServletRequest = req;
		
		// Basic (HTTP-Based)
		String auth = req.getHeader("Authorization");
		if(auth != null){
			// Sbustring(6): elimina la parte "Basic "
			String decodeAuth = new String(Base64.decode(auth.substring(6)));
			String [] decodeAuthSplit = decodeAuth.split(":");
			if(decodeAuthSplit.length>1){
				this.username = decodeAuthSplit[0];
				this.password = decodeAuthSplit[1];
			}
		}
		
		// SSL (HTTPS)
		java.security.cert.X509Certificate[] certs =
			(java.security.cert.X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");

		if(certs!=null) {
			if(log!=null){
				try{
					log.info("Certificati presenti nella richiesta: "+certs.length);
					for (int i = 0; i < certs.length; i++) {
						java.security.cert.X509Certificate cert = certs[i];
						log.info("===================================================");
						log.info("\tCert["+i+"].toString()="+cert.toString());
						log.info("\tCert["+i+"].getType()="+cert.getType());
						log.info("\tCert["+i+"].getVersion()="+cert.getVersion());
						
						if(cert.getIssuerDN()!=null){
							log.info("\tCert["+i+"].cert.getIssuerDN().toString()="+cert.getIssuerDN().toString());
							log.info("\tCert["+i+"].cert.getIssuerDN().getName()="+cert.getIssuerDN().getName());
						}
						else{
							log.info("\tCert["+i+"].cert.getIssuerDN() is null");
						}
						
						if(cert.getIssuerX500Principal()!=null){
							log.info("\tCert["+i+"].getIssuerX500Principal().toString()="+cert.getIssuerX500Principal().toString());
							log.info("\tCert["+i+"].getIssuerX500Principal().getName()="+cert.getIssuerX500Principal().getName());
							log.info("\tCert["+i+"].getIssuerX500Principal().getName(X500Principal.CANONICAL)="+cert.getIssuerX500Principal().getName(X500Principal.CANONICAL));
							log.info("\tCert["+i+"].getIssuerX500Principal().getName(X500Principal.RFC1779)="+cert.getIssuerX500Principal().getName(X500Principal.RFC1779));
							log.info("\tCert["+i+"].getIssuerX500Principal().getName(X500Principal.RFC2253)="+cert.getIssuerX500Principal().getName(X500Principal.RFC2253));
//							Map<String,String> oidMapCanonical = new Hashtable<String, String>();
//							log.info("\tCert["+i+"].getIssuerX500Principal().getName(X500Principal.CANONICAL,oidMapCanonical)="+
//									cert.getIssuerX500Principal().getName(X500Principal.CANONICAL,oidMapCanonical));
//							if(oidMapCanonical!=null && oidMapCanonical.size()>0){
//								Iterator<String> it = oidMapCanonical.keySet().iterator();
//								while (it.hasNext()) {
//									String key = (String) it.next();
//									String value = oidMapCanonical.get(key);
//									log.info("\tCert["+i+"].getIssuerX500Principal() ["+key+"]=["+value+"]");
//								}
//							}
						}
						else{
							log.info("\tCert["+i+"].cert.getIssuerX500Principal() is null");
						}
						
						if(cert.getSubjectDN()!=null){
							log.info("\tCert["+i+"].getSubjectDN().toString()="+cert.getSubjectDN().toString());
							log.info("\tCert["+i+"].getSubjectDN().getName()="+cert.getSubjectDN().getName());
						}
						else{
							log.info("\tCert["+i+"].cert.getSubjectDN() is null");
						}
						
						log.info("\tCert["+i+"].getSerialNumber()="+cert.getSerialNumber());
						log.info("\tCert["+i+"].getNotAfter()="+cert.getNotAfter());
						log.info("\tCert["+i+"].getNotBefore()="+cert.getNotBefore());
						
						if(cert.getSubjectX500Principal()!=null){
							log.info("\tCert["+i+"].getSubjectX500Principal().toString()="+cert.getSubjectX500Principal().toString());
							log.info("\tCert["+i+"].getSubjectX500Principal().getName()="+cert.getSubjectX500Principal().getName());
							log.info("\tCert["+i+"].getSubjectX500Principal().getName(X500Principal.CANONICAL)="+cert.getSubjectX500Principal().getName(X500Principal.CANONICAL));
							log.info("\tCert["+i+"].getSubjectX500Principal().getName(X500Principal.RFC1779)="+cert.getSubjectX500Principal().getName(X500Principal.RFC1779));
							log.info("\tCert["+i+"].getSubjectX500Principal().getName(X500Principal.RFC2253)="+cert.getSubjectX500Principal().getName(X500Principal.RFC2253));
//							Map<String,String> oidMapCanonical = new Hashtable<String, String>();
//							log.info("\tCert["+i+"].getSubjectX500Principal().getName(X500Principal.CANONICAL,oidMapCanonical)="+
//									cert.getSubjectX500Principal().getName(X500Principal.CANONICAL,oidMapCanonical));
//							if(oidMapCanonical!=null && oidMapCanonical.size()>0){
//								Iterator<String> it = oidMapCanonical.keySet().iterator();
//								while (it.hasNext()) {
//									String key = (String) it.next();
//									String value = oidMapCanonical.get(key);
//									log.info("\tCert["+i+"].getSubjectX500Principal() ["+key+"]=["+value+"]");
//								}
//							}
						}
						else{
							log.info("\tCert["+i+"].cert.getSubjectX500Principal() is null");
						}
					}
				}catch(Throwable e){
					log.error("Print info certs error: "+e.getMessage(),e);
				}
			}
			if(certs.length > 0){
				//System.out.println("toString ["+certs[0].getSubjectX500Principal().toString()+"]"); // toString e' equivalente a RFC1779
				//System.out.println("getName ["+certs[0].getSubjectX500Principal().getName()+"]");
				//System.out.println("CANONICAL ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.CANONICAL)+"]");
				//System.out.println("RFC1779 ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.RFC1779)+"]");
				//System.out.println("RFC2253 ["+certs[0].getSubjectX500Principal().getName(javax.security.auth.x500.X500Principal.RFC2253)+"]");
				this.subject = certs[0].getSubjectX500Principal().toString();
				this.certs = certs;
			}
		}else{
			if(log!=null){
				log.info("Certificati non presenti nella richiesta");
			}
		}
		
		// getUserPrincipal (SERVLET API)
		if( req.getUserPrincipal()!=null ){
			this.principal = req.getUserPrincipal();
			this.principalName = this.principal.getName();
		}
	}
	
	
	public Principal getPrincipalObject() {
		return this.principal;
	}
	public void setPrincipalObject(Principal principalObject) {
		this.principal = principalObject;
	}
	public String getPrincipal() {
		return this.principalName;
	}
	public void setPrincipal(String principal) {
		this.principalName = principal;
	}
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public java.security.cert.X509Certificate[] getCerts() {
		return this.certs;
	}
	public void setCerts(java.security.cert.X509Certificate[] certs) {
		this.certs = certs;
	}
	
	public boolean isUserInRole(String role){
		if(this.httpServletRequest!=null){
			return this.httpServletRequest.isUserInRole(role);
		}
		else{
			throw new NotImplementedException("HttpServletRequest undefined");
		}
	}
	
	public Object getAttribute(String attributeName){
		if(this.httpServletRequest!=null){
			return this.httpServletRequest.getAttribute(attributeName);
		}
		else{
			throw new NotImplementedException("HttpServletRequest undefined");
		}
	}
	
	public Object getAttribute(String role, String attributeName){
		throw new NotImplementedException("HttpServletRequest unsupported");
	}

	
	
	@Override
	public String toString(){
		StringBuffer bf = new StringBuffer();

		if(this.principal!=null){
			
			if(bf.length()>0){
				bf.append(" ");
			}
			
			bf.append("principal(");
			bf.append(this.principal);
			bf.append(")");
		}
				
		if(this.subject!=null){
			
			if(bf.length()>0){
				bf.append(" ");
			}
			
			bf.append("subject(");
			bf.append(this.subject);
			bf.append(")");
		}
		
		if(this.username!=null){
			
			if(bf.length()>0){
				bf.append(" ");
			}
			
			bf.append("username(");
			bf.append(this.username);
			bf.append(")");
		}
		
		if(this.password!=null){
			
			if(bf.length()>0){
				bf.append(" ");
			}
			
			bf.append("password(");
			bf.append(this.password);
			bf.append(")");
		}
		
		return bf.toString();
	}
}
