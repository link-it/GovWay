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



package org.openspcoop2.protocol.modipa.constants;

import org.openspcoop2.core.constants.CostantiDB;
import org.openspcoop2.protocol.engine.constants.Costanti;
import org.openspcoop2.security.message.constants.SecurityConstants;
import org.openspcoop2.security.message.constants.SignatureAlgorithm;
import org.openspcoop2.security.message.constants.SignatureC14NAlgorithm;

/**
 * Classe dove sono fornite le stringhe costanti, definite dalla specifica del protocollo ModI, 
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */

public class ModICostanti {
   
    public static final String MODIPA_PROTOCOL_NAME = Costanti.MODIPA_PROTOCOL_NAME;
	
	public final static String OPENSPCOOP2_LOCAL_HOME = "GOVWAY_HOME";
	
    public final static String MODIPA_PROPERTIES_LOCAL_PATH = "modipa_local.properties";
    public final static String MODIPA_PROPERTIES = "MODIPA_PROPERTIES";
	
    public final static String MODIPA_USE_BODY_NAMESPACE = "useBodyNamespace";
    
    public final static String MODIPA_CONTEXT_REQUEST_DIGEST = "MODIPA_REQUEST_DIGEST";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE = "ProfiloInterazione";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX = "ProfiloInterazioneAsincrona-";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_TIPO = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"Tipo";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_RUOLO = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"Ruolo";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_API_RICHIESTA_CORRELATA = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"ApiCorrelata";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_SERVIZIO_RICHIESTA_CORRELATA = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"ServizioCorrelato";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_AZIONE_RICHIESTA_CORRELATA = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"AzioneCorrelata";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_RISORSA_RICHIESTA_CORRELATA = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"RisorsaCorrelata";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_ID_CORRELAZIONE = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"CorrelationID";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_REPLY_TO = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"ReplyTo";
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_LOCATION = MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_PREFIX+"Location";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_INTERAZIONE_ASINCRONA_ID_CORRELAZIONE_AGGIUNTO_PER_CONSENTIRE_VALIDAZIONE_CONTENUTI = "__@@SkipValidation##__";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_CANALE = "ProfiloSicurezzaCanale";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO = "ProfiloSicurezzaMessaggio";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_PREFIX = "ProfiloSicurezzaMessaggio-";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_X509_SUBJECT = "ProfiloSicurezzaMessaggio-X509-Subject";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_X509_ISSUER = "ProfiloSicurezzaMessaggio-X509-Issuer";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_NBF = "ProfiloSicurezzaMessaggio-NotBefore";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_IAT = "ProfiloSicurezzaMessaggio-IssuedAt";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_EXP = "ProfiloSicurezzaMessaggio-Expiration";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_ID = "ProfiloSicurezzaMessaggio-MessageId";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_RELATES_TO = "ProfiloSicurezzaMessaggio-RelatesTo";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_DIGEST = "ProfiloSicurezzaMessaggio-Digest";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_SUBJECT = "ProfiloSicurezzaMessaggio-Subject";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_ISSUER = "ProfiloSicurezzaMessaggio-Issuer";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_CLIENT_ID = "ProfiloSicurezzaMessaggio-ClientId";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_REST_AUDIENCE = "ProfiloSicurezzaMessaggio-Audience";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SOAP_WSA_FROM = "ProfiloSicurezzaMessaggio-WSA-From";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SOAP_WSA_TO = "ProfiloSicurezzaMessaggio-WSA-To";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SIGNED_HEADER_PREFIX = "ProfiloSicurezzaMessaggioSignedHeader-";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SIGNED_HEADER_MULTIPLE_VALUE_SUFFIX = "___#";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_SIGNED_SOAP_PREFIX = "ProfiloSicurezzaMessaggioSignedSoap-";
    
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CORNICE_SICUREZZA_ENTE = "ProfiloSicurezzaMessaggio-CorniceSicurezza-Ente";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CORNICE_SICUREZZA_USER = "ProfiloSicurezzaMessaggio-CorniceSicurezza-User";
    public final static String MODIPA_BUSTA_EXT_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CORNICE_SICUREZZA_USER_IP = "ProfiloSicurezzaMessaggio-CorniceSicurezza-UserIP";
        
    public final static String MODIPA_OPENSPCOOP2_MSG_CONTEXT_AUDIENCE_CHECK = "AUDIENCE_CHECK";
    public final static String MODIPA_OPENSPCOOP2_MSG_CONTEXT_BUILD_SECURITY_REQUEST_TOKEN = "BUILD_SECURITY_REQUEST_TOKEN";
    public final static String MODIPA_OPENSPCOOP2_MSG_CONTEXT_SBUSTAMENTO_REST = "MODIPA_SBUSTAMENTO_REST";
    public final static String MODIPA_OPENSPCOOP2_MSG_CONTEXT_SBUSTAMENTO_SOAP = "MODIPA_SBUSTAMENTO_SOAP";
    
    public static final String MODIPA_VALUE_UNDEFINED = Costanti.MODIPA_VALUE_UNDEFINED;
        
    public static final String MODIPA_KEYSTORE_MODE = "modipaKeystoreMode";
    public static final String MODIPA_KEYSTORE_MODE_VALUE_ARCHIVE = "archive";
    public static final String MODIPA_KEYSTORE_MODE_VALUE_PATH = "path";
    
    public static final String MODIPA_KEYSTORE_ARCHIVE = "modipaKeystoreArchive";
    
    public static final String MODIPA_KEYSTORE_PATH = CostantiDB.MODIPA_KEYSTORE_PATH;
    
    public static final String MODIPA_KEYSTORE_TYPE = "modipaKeystoreType";
    public static final String MODIPA_KEYSTORE_TYPE_VALUE_JKS = "jks";
    public static final String MODIPA_KEYSTORE_TYPE_VALUE_PKCS12 = "pkcs12";
    
    public static final String MODIPA_KEYSTORE_PASSWORD = "modipaKeystorePassword";
    
    public static final String MODIPA_KEY_ALIAS = "modipaKeyAlias";
    
    public static final String MODIPA_KEY_PASSWORD = "modipaKeyPassword";
    
    public static final String MODIPA_KEY_CN_SUBJECT = "modipaKeyCNSubject";
    public static final String MODIPA_KEY_CN_ISSUER = "modipaKeyCNIssuer";
        
    public static final String MODIPA_PROFILO_DEFAULT = "default";
    public static final String MODIPA_PROFILO_RIDEFINISCI = "ridefinisci";
    
    public static final String MODIPA_PROFILO_INTERAZIONE = CostantiDB.MODIPA_PROFILO_INTERAZIONE;
    public static final String MODIPA_PROFILO_INTERAZIONE_VALUE_CRUD = CostantiDB.MODIPA_PROFILO_INTERAZIONE_VALUE_CRUD;
    public static final String MODIPA_PROFILO_INTERAZIONE_VALUE_BLOCCANTE = CostantiDB.MODIPA_PROFILO_INTERAZIONE_VALUE_BLOCCANTE;
    public static final String MODIPA_PROFILO_INTERAZIONE_VALUE_NON_BLOCCANTE = CostantiDB.MODIPA_PROFILO_INTERAZIONE_VALUE_NON_BLOCCANTE;
    public static final String MODIPA_PROFILO_INTERAZIONE_DEFAULT_REST_VALUE = MODIPA_PROFILO_INTERAZIONE_VALUE_CRUD;
    public static final String MODIPA_PROFILO_INTERAZIONE_DEFAULT_SOAP_VALUE = MODIPA_PROFILO_INTERAZIONE_VALUE_BLOCCANTE;
    
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_VALUE_PUSH = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_VALUE_PUSH;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_VALUE_PULL = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_VALUE_PULL;
    
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RICHIESTA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RICHIESTA;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RICHIESTA_STATO = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RICHIESTA_STATO;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RISPOSTA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_RUOLO_VALUE_RISPOSTA;
    
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_API_RICHIESTA_CORRELATA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_API_RICHIESTA_CORRELATA;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_SERVIZIO_RICHIESTA_CORRELATA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_SERVIZIO_RICHIESTA_CORRELATA;
    public static final String MODIPA_PROFILO_INTERAZIONE_ASINCRONA_AZIONE_RICHIESTA_CORRELATA = CostantiDB.MODIPA_PROFILO_INTERAZIONE_ASINCRONA_AZIONE_RICHIESTA_CORRELATA;
    
    public static final String MODIPA_PROFILO_SICUREZZA_CANALE = CostantiDB.MODIPA_PROFILO_SICUREZZA_CANALE;
    public static final String MODIPA_PROFILO_SICUREZZA_CANALE_VALUE_IDAC01 = CostantiDB.MODIPA_PROFILO_SICUREZZA_CANALE_VALUE_IDAC01;
    public static final String MODIPA_PROFILO_SICUREZZA_CANALE_VALUE_IDAC02 = CostantiDB.MODIPA_PROFILO_SICUREZZA_CANALE_VALUE_IDAC02;
    
	public static final String MODIPA_SICUREZZA_MESSAGGIO = CostantiDB.MODIPA_SICUREZZA_MESSAGGIO;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_UNDEFINED = MODIPA_VALUE_UNDEFINED;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM01 = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM01;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM02 = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM02;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM0301 = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM0301;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM0302 = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_VALUE_IDAM0302;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER = "modipaSecurityMessageHeaderName";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_VALUE_MODIPA = "modipa";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_VALUE_AUTHORIZATION = "authorization";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_IDAM03_DEFAULT_VALUE = MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_VALUE_MODIPA;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_NOT_IDAM03_DEFAULT_VALUE = MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HEADER_VALUE_AUTHORIZATION;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_LABEL = "Informazioni Utente";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CODICE_ENTE_MODE_LABEL = "Codice Ente";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_USER_MODE_LABEL = "UserID Utente";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_IP_USER_MODE_LABEL = "Indirizzo IP Utente";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_ACTION_MODE = "modipaSecurityMessageProfileActionMode";
    
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE = "modipaSecurityMessageConfig";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_ENTRAMBI = "entrambi";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_RICHIESTA = "richiesta";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_RISPOSTA = "risposta";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_ENTRAMBI_CON_ATTACHMENTS = "entrambi_attachments";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_RICHIESTA_CON_ATTACHMENTS = "richiesta_attachments";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_RISPOSTA_CON_ATTACHMENTS = "risposta_attachments";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_PERSONALIZZATO = "custom";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_DEFAULT = MODIPA_CONFIGURAZIONE_SICUREZZA_MESSAGGIO_MODE_VALUE_ENTRAMBI;
    
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE = "modipaSecurityRequest";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE_VALUE_ABILITATO = "true";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE_VALUE_DISABILITATO = "false";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE_VALUE_PERSONALIZZATO = "custom";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE_VALUE_DEFAULT = MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_MODE_VALUE_ABILITATO;
    
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RICHIESTA_CONTENT_TYPE_MODE_ID = "modipaSecurityRequestContentType";
   
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE = "modipaSecurityResponse";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE_VALUE_ABILITATO = "true";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE_VALUE_DISABILITATO = "false";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE_VALUE_PERSONALIZZATO = "custom";
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE_VALUE_DEFAULT = MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_MODE_VALUE_ABILITATO;
    
    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_CONTENT_TYPE_MODE_ID = "modipaSecurityResponseContentType";

    public static final String MODIPA_CONFIGURAZIONE_SICUREZZA_RISPOSTA_RETURN_CODE_MODE_ID = "modipaSecurityResponseReturnCode";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_ALG = "modipaSecurityMessageRestRequestAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_ALG = "modipaSecurityMessageRestResponseAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_RS256 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.RS256.name();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_RS384 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.RS384.name();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_RS512 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.RS512.name();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_ES256 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.ES256.name();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_ES384 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.ES384.name();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_ALG_ES512 = org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm.ES512.name();
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_RIFERIMENTO_X509 = "modipaSecurityMessageRestRequestX509Cert";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509 = "modipaSecurityMessageRestResponseX509Cert";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5U = "x5u";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5C = "x5c";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RIFERIMENTO_X509_VALUE_X5T = "x5t";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_RIFERIMENTO_X509_X5C_USE_CERTIFICATE_CHAIN = "modipaSecurityMessageRestRequestX509CertUseCertificateChain";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509_X5C_USE_CERTIFICATE_CHAIN = "modipaSecurityMessageRestResponseX509CertUseCertificateChain";
        
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509_AS_REQUEST = "modipaSecurityMessageRestResponseX509CertAsReq";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509_AS_REQUEST_VALUE_TRUE = "true";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_RIFERIMENTO_X509_AS_REQUEST_VALUE_FALSE = "false";
       
    // Deprecato, spostato su S.A.
    // public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RICHIESTA_X509_VALUE_X5URL = "modipaSecurityMessageRestRequestX509Url";ù
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_SA_RICHIESTA_X509_VALUE_X5URL = "modipaSecurityMessageRestRequestX509Url";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_REST_RISPOSTA_X509_VALUE_X5URL = "modipaSecurityMessageRestResponseX509Url";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_ALG = "modipaSecurityMessageSoapRequestAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_ALG = "modipaSecurityMessageSoapResponseAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_DSA_SHA_256 = SignatureAlgorithm.DSA_SHA256.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_RSA_SHA_224 = SignatureAlgorithm.RSA_SHA224.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_RSA_SHA_256 = SignatureAlgorithm.RSA_SHA256.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_RSA_SHA_384 = SignatureAlgorithm.RSA_SHA384.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_RSA_SHA_512 = SignatureAlgorithm.RSA_SHA512.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_ECDSA_SHA_224 = SignatureAlgorithm.ECDSA_SHA224.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_ECDSA_SHA_256 = SignatureAlgorithm.ECDSA_SHA256.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_ECDSA_SHA_384 = SignatureAlgorithm.ECDSA_SHA384.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_ALG_ECDSA_SHA_512 = SignatureAlgorithm.ECDSA_SHA512.getUri();
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_CANONICALIZATION_ALG = "modipaSecurityMessageSoapRequestCanonicalizationAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_CANONICALIZATION_ALG = "modipaSecurityMessageSoapResponseCanonicalizationAlg";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_CANONICALIZATION_ALG_INCLUSIVE_C14N_10 = SignatureC14NAlgorithm.INCLUSIVE_C14N_10_OMITS_COMMENTS.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_CANONICALIZATION_ALG_INCLUSIVE_C14N_11 = SignatureC14NAlgorithm.INCLUSIVE_C14N_11_OMITS_COMMENTS.getUri();
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_CANONICALIZATION_ALG_EXCLUSIVE_C14N_10 = SignatureC14NAlgorithm.EXCLUSIVE_C14N_10_OMITS_COMMENTS.getUri();
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509 = "modipaSecurityMessageSoapRequestX509Cert";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509 = "modipaSecurityMessageSoapResponseX509Cert";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RIFERIMENTO_X509_VALUE_BINARY_SECURITY_TOKEN = SecurityConstants.KEY_IDENTIFIER_BST_DIRECT_REFERENCE;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RIFERIMENTO_X509_VALUE_SECURITY_TOKEN_REFERENCE = SecurityConstants.KEY_IDENTIFIER_ISSUER_SERIAL;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RIFERIMENTO_X509_VALUE_KEY_IDENTIFIER_X509 = SecurityConstants.KEY_IDENTIFIER_X509;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RIFERIMENTO_X509_VALUE_KEY_IDENTIFIER_THUMBPRINT = SecurityConstants.KEY_IDENTIFIER_THUMBPRINT;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RIFERIMENTO_X509_VALUE_KEY_IDENTIFIER_SKI = SecurityConstants.KEY_IDENTIFIER_SKI;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_USE_CERTIFICATE_CHAIN = "modipaSecurityMessageSoapRequestX509CertUseCertificateChain";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_USE_CERTIFICATE_CHAIN = "modipaSecurityMessageSoapResponseX509CertUseCertificateChain";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RICHIESTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_INCLUDE_SIGNATURE_TOKEN = "modipaSecurityMessageSoapRequestX509CertIncludeSignatureToken";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_RISPOSTA_RIFERIMENTO_X509_BINARY_SECURITY_TOKEN_INCLUDE_SIGNATURE_TOKEN = "modipaSecurityMessageSoapResponseX509CertIncludeSignatureToken";
        
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_EXPIRED = "modipaSecurityMessageRequestExp";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_EXPIRED = "modipaSecurityMessageResponseExp";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_AUDIENCE = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RICHIESTA_AUDIENCE;
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_AUDIENCE;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_REQUEST_DIGEST = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_RISPOSTA_REQUEST_DIGEST;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_HTTP_HEADERS_REST = "modipaSecurityMessageHttpHeaders";

    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SOAP_HEADERS_SOAP = "modipaSecurityMessageSoapHeaders";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CODICE_ENTE_MODE = "modipaSecurityMessageCorniceSicurezzaCodiceEnteMode";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_CODICE_ENTE = "modipaSecurityMessageCorniceSicurezzaCodiceEnte";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_USER_MODE = "modipaSecurityMessageCorniceSicurezzaUserMode";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_USER = "modipaSecurityMessageCorniceSicurezzaUser";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_IP_USER_MODE = "modipaSecurityMessageCorniceSicurezzaIPUserMode";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CORNICE_SICUREZZA_IP_USER = "modipaSecurityMessageCorniceSicurezzaIPUser";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_MODE = "modipaTruststoreMode";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_PATH = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_PATH;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_TYPE = "modipaTruststoreType";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_TYPE_VALUE_JKS = "jks";
    //public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_TYPE_VALUE_PKCS12 = "pkcs12";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_PASSWORD = "modipaTruststorePassword";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_CRLS = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_TRUSTSTORE_CRLS;
    
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_MODE = "modipaSslTruststoreMode";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_PATH = CostantiDB.MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_PATH;
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_TYPE = "modipaSslTruststoreType";
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_TYPE_VALUE_JKS = "jks";
    //public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_TYPE_VALUE_PKCS12 = "pkcs12";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_PASSWORD = "modipaSslTruststorePassword";
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_SSL_TRUSTSTORE_CRLS = "modipaSslTruststoreCRLs";
    
    
    public static final String MODIPA_PROFILO_SICUREZZA_MESSAGGIO_CERTIFICATI_KEYSTORE_MODE = "modipaKeystoreCertMode";

    public static final String MODIPA_PROFILO_INTERAZIONE_HTTP_CODE_2XX = "2xx";
    public static final int MODIPA_PROFILO_INTERAZIONE_HTTP_CODE_2XX_INT_VALUE = -2;    
    
    public static final String CONFIG_MODIPA_SOGGETTO_MITTENTE_KEYWORD = "#SoggettoMittente#";
    
    public static final String CONFIG_MODIPA_SOAP_SECURITY_TOKEN_WSA_TO_KEYWORD_SOAP_ACTION = "soapAction";
    public static final String CONFIG_MODIPA_SOAP_SECURITY_TOKEN_WSA_TO_KEYWORD_OPERATION = "operation";
    public static final String CONFIG_MODIPA_SOAP_SECURITY_TOKEN_WSA_TO_KEYWORD_NONE = "none";

}





