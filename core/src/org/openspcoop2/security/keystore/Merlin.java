/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2021 Link.it srl (https://link.it). 
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

package org.openspcoop2.security.keystore;

import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.security.auth.callback.CallbackHandler;

import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.ext.WSSecurityException.ErrorCode;
import org.openspcoop2.security.keystore.cache.GestoreKeystoreCache;

/**
 * Implementazione che estente l'implementazione di default e permette di caricare keystore utilizzando la cache o il binario passato direttamente.
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Merlin extends org.apache.wss4j.common.crypto.Merlin {

	public Merlin() {
		super();
	}

	public Merlin(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor)
			throws WSSecurityException, IOException {
		super(properties, loader, passwordEncryptor);
	}

	private org.openspcoop2.utils.certificate.KeyStore op2KeyStore;
	private org.openspcoop2.utils.certificate.KeyStore op2TrustStore;
	
	@Override
	public void loadProperties(Properties properties, ClassLoader loader, PasswordEncryptor passwordEncryptor)
			throws WSSecurityException, IOException {

		if (properties == null) {
			return;
		}
		this.properties = properties;
		this.passwordEncryptor = passwordEncryptor;

		String prefix = PREFIX;
		for (Object key : properties.keySet()) {
			if (key instanceof String) {
				String propKey = (String)key;
				if (propKey.startsWith(PREFIX)) {
					break;
				} else if (propKey.startsWith(OLD_PREFIX)) {
					prefix = OLD_PREFIX;
					break;
				}
			}
		}


		//
		// Load the KeyStore
		//
		String keyStoreLocation = properties.getProperty(prefix + KEYSTORE_FILE);
		if (keyStoreLocation == null) {
			keyStoreLocation = properties.getProperty(prefix + OLD_KEYSTORE_FILE);
		}
		Object keyStoreArchiveObject = properties.get(prefix + KeystoreConstants.KEYSTORE);
		byte [] keyStoreArchive = null;
		if(keyStoreArchiveObject!=null && keyStoreArchiveObject instanceof byte[]) {
			keyStoreArchive = (byte[]) keyStoreArchiveObject;
		}
		
		String keyStorePassword = properties.getProperty(prefix + KEYSTORE_PASSWORD, "security");
		if (keyStorePassword != null) {
			keyStorePassword = keyStorePassword.trim();
			keyStorePassword = decryptPassword(keyStorePassword, passwordEncryptor);
		}
		String keyStoreType = properties.getProperty(prefix + KEYSTORE_TYPE, KeyStore.getDefaultType());
		if (keyStoreType != null) {
			keyStoreType = keyStoreType.trim();
		}
				
		if (keyStoreLocation != null) {
			
			// rimuovo la proprietà per non farla trovare quando chiamo il super.loadProperties
			this.properties.remove(prefix + KEYSTORE_FILE);
			this.properties.remove(prefix + OLD_KEYSTORE_FILE);
		
			// il set 'privatePasswordSet' e' stato riportato poichè eliminando sopra le due proprietà, questo codice non verra utilizzato nel super.loadProperties
			String privatePasswd = properties.getProperty(prefix + KEYSTORE_PRIVATE_PASSWORD);
			if (privatePasswd != null) {
				this.privatePasswordSet = true;
			}
			
			
			keyStoreLocation = keyStoreLocation.trim();

			try {
				MerlinKeystore merlinKs = GestoreKeystoreCache.getMerlinKeystore(keyStoreLocation, keyStoreType, 
						keyStorePassword);
				if(merlinKs==null) {
					throw new Exception("Accesso al keystore '"+keyStoreLocation+"' non riuscito");
				}
				if(merlinKs.getKeyStore()==null) {
					throw new Exception("Accesso al keystore '"+keyStoreLocation+"' non riuscito");
				}
				this.op2KeyStore = merlinKs.getKeyStore();
				this.keystore = this.op2KeyStore.getKeystore();
			}catch(Exception e) {
				throw new IOException("[Keystore-File] '"+keyStoreLocation+"' "+e.getMessage(),e);
			}

		}
		else if (keyStoreArchive != null) {
			try {
				MerlinKeystore merlinKs = GestoreKeystoreCache.getMerlinKeystore(keyStoreArchive, keyStoreType, 
						keyStorePassword);
				if(merlinKs==null) {
					throw new Exception("Accesso al keystore non riuscito");
				}
				if(merlinKs.getKeyStore()==null) {
					throw new Exception("Accesso al keystore non riuscito");
				}
				this.op2KeyStore = merlinKs.getKeyStore();
				this.keystore = this.op2KeyStore.getKeystore();
			}catch(Exception e) {
				throw new IOException("[Keystore-Archive] "+e.getMessage(),e);
			}
		}
		

		//
		// Load the TrustStore
		//
		
		String trustStoreLocation = properties.getProperty(prefix + TRUSTSTORE_FILE);
		Object trustStoreArchiveObject = properties.get(prefix + KeystoreConstants.TRUSTSTORE);
		byte [] trustStoreArchive = null;
		if(trustStoreArchiveObject!=null && trustStoreArchiveObject instanceof byte[]) {
			trustStoreArchive = (byte[]) trustStoreArchiveObject;
		}
		
		String trustStorePassword = properties.getProperty(prefix + TRUSTSTORE_PASSWORD, "security");
		if (trustStorePassword != null) {
			trustStorePassword = trustStorePassword.trim();
			trustStorePassword = decryptPassword(trustStorePassword, passwordEncryptor);
		}
		String trustStoreType = properties.getProperty(prefix + TRUSTSTORE_TYPE, KeyStore.getDefaultType());
		if (trustStoreType != null) {
			trustStoreType = trustStoreType.trim();
		}
		
		if (trustStoreLocation != null) {
			
			// rimuovo la proprietà per non farla trovare quando chiamo il super.loadProperties
			this.properties.remove(prefix + TRUSTSTORE_FILE);
		
			// il set 'loadCACerts' e' stato riportato poichè eliminando sopra le due proprietà, questo codice non verra utilizzato nel super.loadProperties
			this.loadCACerts = false;
			
			trustStoreLocation = trustStoreLocation.trim();

			try {
				MerlinTruststore merlinTs = GestoreKeystoreCache.getMerlinTruststore(trustStoreLocation, trustStoreType, 
						trustStorePassword);
				if(merlinTs==null) {
					throw new Exception("Accesso al truststore '"+trustStoreLocation+"' non riuscito");
				}
				if(merlinTs.getTrustStore()==null) {
					throw new Exception("Accesso al truststore '"+keyStoreLocation+"' non riuscito");
				}
				this.op2TrustStore = merlinTs.getTrustStore();
				this.truststore = this.op2TrustStore.getKeystore();
			}catch(Exception e) {
				throw new IOException("[Truststore-File] '"+trustStoreLocation+"' "+e.getMessage(),e);
			}

		}
		else if (trustStoreArchive != null) {
			try {
				MerlinTruststore merlinTs = GestoreKeystoreCache.getMerlinTruststore(trustStoreArchive, trustStoreType, 
						trustStorePassword);
				if(merlinTs==null) {
					throw new Exception("Accesso al truststore non riuscito");
				}
				if(merlinTs.getTrustStore()==null) {
					throw new Exception("Accesso al truststore non riuscito");
				}
				this.op2TrustStore = merlinTs.getTrustStore();
				this.truststore = this.op2TrustStore.getKeystore();
			}catch(Exception e) {
				throw new IOException("[Truststore-Archive] "+e.getMessage(),e);
			}
		}
		
		if(this.truststore!=null) {
			
			// Devo evitare che venga eseguito il loadCerts quando invoco super.loadProperties
			
			properties.remove(prefix + LOAD_CA_CERTS);
			properties.setProperty(prefix + LOAD_CA_CERTS, "false");
			
		}
		


		//
		// Load the CRL file(s)
		//
		String crlLocations = properties.getProperty(prefix + X509_CRL_FILE);
		if (crlLocations != null) {
			
			// rimuovo la proprietà per non farla trovare quando chiamo il super.loadProperties
			this.properties.remove(prefix + X509_CRL_FILE);
			
			try {
				CRLCertstore crlCertstore = GestoreKeystoreCache.getCRLCertstore(crlLocations);
				if(crlCertstore==null) {
					throw new Exception("Accesso alle crl '"+crlLocations+"' non riuscito");
				}
				this.crlCertStore = crlCertstore.getCertStore();
			}catch(Exception e) {
				throw new IOException("[CRLCertstore] "+e.getMessage(),e);
			}
			
		}



		//
		// Super
		//
		super.loadProperties(properties, loader, passwordEncryptor);
	}

	@Override
	public PrivateKey getPrivateKey(PublicKey publicKey, CallbackHandler callbackHandler) throws WSSecurityException {
		//System.out.println("@@@ getPrivateKey PUBLIC KEY, CALLBACK");
		return super.getPrivateKey(publicKey, callbackHandler);
	}

	@Override
	public PrivateKey getPrivateKey(String alias, String password) throws WSSecurityException {
		//System.out.println("@@@ getPrivateKey arg0["+alias+"] arg1["+password+"]");
		if(this.op2KeyStore!=null) {
			try {
				return this.op2KeyStore.getPrivateKey(alias, password);
			}catch(Exception e) {
				throw new WSSecurityException(ErrorCode.SECURITY_ERROR,e);
			}
		}
		return super.getPrivateKey(alias, password);
	}

	@Override
	public PrivateKey getPrivateKey(X509Certificate x509, CallbackHandler callbackHandler) throws WSSecurityException {
		//System.out.println("@@@ getPrivateKey X509Certificatw, CALLBACK");
		return super.getPrivateKey(x509, callbackHandler);
	}
}
