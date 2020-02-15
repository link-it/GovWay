/*
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
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


package org.openspcoop2.security.message.wss4j;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.str.STRParser;
import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2SoapMessage;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.message.soap.reference.Reference;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.protocol.sdk.constants.CodiceErroreCooperazione;
import org.openspcoop2.security.SecurityException;
import org.openspcoop2.security.message.AbstractSOAPMessageSecurityReceiver;
import org.openspcoop2.security.message.MessageSecurityContext;
import org.openspcoop2.security.message.SubErrorCodeSecurity;
import org.openspcoop2.security.message.constants.SecurityConstants;
import org.openspcoop2.security.message.engine.MessageUtilities;
import org.openspcoop2.security.message.engine.WSSUtilities;
import org.openspcoop2.security.message.utils.AttachmentProcessingPart;
import org.openspcoop2.security.message.utils.AttachmentsConfigReaderUtils;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.id.IDUtilities;



/**
 * Classe per la gestione della WS-Security (WSDoAllReceiver).
 *
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class MessageSecurityReceiver_wss4j extends AbstractSOAPMessageSecurityReceiver{


	@Override
	public void process(MessageSecurityContext wssContext,OpenSPCoop2Message messageParam,Busta busta) throws SecurityException{
		try{
			
			if(ServiceBinding.SOAP.equals(messageParam.getServiceBinding())==false){
				throw new SecurityException("WSS4J Engine usable only with SOAP Binding");
			}
			OpenSPCoop2SoapMessage message = messageParam.castAsSoap();
			
			
			// ** Inizializzo handler CXF **/
			
			WSS4JInInterceptor inHandler = new WSS4JInInterceptor();
			SoapMessage msgCtx = new SoapMessage(new MessageImpl());
			Exchange ex = new ExchangeImpl();
	        ex.setInMessage(msgCtx);
			msgCtx.setContent(SOAPMessage.class, message.getSOAPMessage());
	        setIncomingProperties(wssContext,inHandler,msgCtx);
	        
	        
	        // ** Registro attachments da trattare **/
	        
	        AttachmentProcessingPart app = AttachmentsConfigReaderUtils.getSecurityOnAttachments(wssContext);
	        List<Attachment> listAttachments = null;
	        if(app!=null){
	        	// Non è possibile effettuare il controllo della posizione puntuale sulla ricezione. 
	        	// Può essere usato solo per specificare quale attach firmare/cifrare in spedizione
	        	// Alcune implementazioni modificano l'ordine degli attachments una volta applicata la sicurezza
	        	if(app.isAllAttachments()==false){
	        		List<String> cidAttachmentsForSecurity = AttachmentsConfigReaderUtils.getListCIDAttachmentsForSecurity(wssContext);
	        		listAttachments = org.openspcoop2.security.message.wss4j.WSSUtilities.readAttachments(cidAttachmentsForSecurity, message);
	        	}
	        	else{
	        		listAttachments = org.openspcoop2.security.message.wss4j.WSSUtilities.readAttachments(app, message);
	        	}
	        	if(listAttachments!=null && listAttachments.size()>0){
	        		msgCtx.setAttachments(listAttachments);
	        	}
	        }
	        
	        
	        // ** Applico sicurezza tramite CXF **/
	        
			inHandler.handleMessage(msgCtx);
			List<?> results = (List<?>) msgCtx.getContextualProperty(WSHandlerConstants.RECV_RESULTS);
			wssContext.getLog().debug("Print wssRecever results...");
			org.openspcoop2.security.message.wss4j.WSSUtilities.printWSResult(wssContext.getLog(), results);
			
			
			// ** Riporto modifica degli attachments **/
			
			org.openspcoop2.security.message.wss4j.WSSUtilities.updateAttachments(listAttachments, message);
			
			
			// ** Lettura Subject Certificato e informazioni relative al processamento effettuato **/
			
			this.examineResults(msgCtx, wssContext.getActor());
						
		} catch (Exception e) {
			
			SecurityException wssException = null;
			
			
			/* **** MESSAGGIO ***** */
			String msg = Utilities.getInnerNotEmptyMessageException(e).getMessage();
			
			Throwable innerExc = Utilities.getLastInnerException(e);
			String innerMsg = null;
			if(innerExc!=null){
				innerMsg = innerExc.getMessage();
			}
			
			String messaggio = null;
			if(msg!=null){
				messaggio = new String(msg);
				if(innerMsg!=null && !innerMsg.equals(msg)){
					messaggio = messaggio + " ; " + innerMsg;
				}
			}
			else{
				if(innerMsg!=null){
					messaggio = innerMsg;
				}
			}
			
			// L'if scopre l'eventuale motivo preciso riguardo al fallimento della cifratura/firma.
			if(Utilities.existsInnerException(e, WSSecurityException.class)){
				Throwable t = Utilities.getLastInnerException(e);
				if(t instanceof WSSecurityException){
					if(messaggio!=null){
						messaggio = messaggio + " ; " + t.getMessage();
					}
					else{
						messaggio = t.getMessage();
					}
				}
			}
			
			wssException = new SecurityException(messaggio, e);
			wssException.setMsgErrore(messaggio);
			
			
			/* ***** CODICE **** */
			
			boolean signature = false;
			boolean encrypt = false;
			try{
				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				PrintStream printStream = new PrintStream(bout);
				e.printStackTrace(printStream);
				bout.flush();
				printStream.flush();
				bout.close();
				printStream.close();
				
				if(bout.toString().contains(".processor.SignatureProcessor")){
					signature = true;
				}
				else if(bout.toString().contains(".processor.SignatureConfirmationProcessor")){
					signature = true;
				}
				else if(bout.toString().contains(".processor.EncryptedKeyProcessor")){
					encrypt = true;
				}
				else if(bout.toString().contains(".processor.EncryptedDataProcessor")){
					encrypt = true;
				}
				
			}catch(Exception eClose){}
			
			if(signature){
				wssException.setCodiceErrore(CodiceErroreCooperazione.SICUREZZA_FIRMA_NON_VALIDA);
			}
			else if(encrypt){
				wssException.setCodiceErrore(CodiceErroreCooperazione.SICUREZZA_CIFRATURA_NON_VALIDA);
			}
			else if(Utilities.existsInnerMessageException(e, "The signature or decryption was invalid", true)){
				wssException.setCodiceErrore(CodiceErroreCooperazione.SICUREZZA_FIRMA_NON_VALIDA);
			} else {
				wssException.setCodiceErrore(CodiceErroreCooperazione.SICUREZZA);
			}
			
			
			throw wssException;
		}

	}

	private Timestamp timestamp;
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	private List<WSDataRef> signatureRefs;
	public List<WSDataRef> getSignatureRefs() {
		return this.signatureRefs;
	}

	private List<WSDataRef> encryptionRefs;
	public List<WSDataRef> getEncryptionRefs() {
		return this.encryptionRefs;
	}
	
	private String certificate;
	private STRParser.REFERENCE_TYPE x509ReferenceType;
	private X509Certificate x509Certificate;
	private X509Certificate [] x509Certificates;
	private PublicKey publicKey;
	@Override
	public String getCertificate() throws SecurityException{
		return this.certificate;
	}
	public STRParser.REFERENCE_TYPE getX509ReferenceType() {
		return this.x509ReferenceType;
	}
	@Override
	public X509Certificate getX509Certificate() throws SecurityException {
		return this.x509Certificate;
	}
	public X509Certificate[] getX509Certificates() {
		return this.x509Certificates;
	}
	@Override
	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	
	private void setIncomingProperties(MessageSecurityContext wssContext,WSS4JInInterceptor interceptor, SoapMessage msgCtx) {
		Hashtable<?,?> wssIncomingProperties =  wssContext.getIncomingProperties();
		if (wssIncomingProperties != null && wssIncomingProperties.size() > 0) {
			for (Enumeration<?> e = wssIncomingProperties.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				Object oValue = wssIncomingProperties.get(key);
				String value = null;
				if(oValue!=null && oValue instanceof String) {
					value = (String) oValue;
				}
				if(SecurityConstants.PASSWORD_CALLBACK_REF.equals(key)) {
					msgCtx.put(key, oValue);
				}
				else if(SecurityConstants.SIGNATURE_PROPERTY_REF_ID.equals(key) || 
						SecurityConstants.SIGNATURE_VERIFICATION_PROPERTY_REF_ID.equals(key) || 
						SecurityConstants.SIGNATURE_TRUSTSTORE_PROPERTY_REF_ID.equals(key) || 
						SecurityConstants.ENCRYPTION_PROPERTY_REF_ID.equals(key) || 
						SecurityConstants.DECRYPTION_PROPERTY_REF_ID.equals(key) ) {
					if(value!=null) {
						msgCtx.put(key, value);
					}
					else { 
						String id = key+"_"+IDUtilities.getUniqueSerialNumber();
						msgCtx.put(key, id);
						msgCtx.put(id, oValue);
					}
				}
				else{
					msgCtx.put(key, value);
					interceptor.setProperty(key, value);
				}
			}
		}
		if(wssContext.getActor()!=null){
			interceptor.setProperty(SecurityConstants.ACTOR, wssContext.getActor());
			msgCtx.put(SecurityConstants.ACTOR, wssContext.getActor());
		}
    }

	private void examineResults(SoapMessage msgCtx, String actor) {
				
		List<?> results = (List<?>) msgCtx.get(WSHandlerConstants.RECV_RESULTS);
		//System.out.println("Potential number of usernames: " + results.size());
		for (int i = 0; results != null && i < results.size(); i++) {
			//System.out.println("RESULT["+i+"]: "+results.get(i).getClass().getName());
			WSHandlerResult hResult = (WSHandlerResult)results.get(i);
			//System.out.println("RESULT["+i+"] actor: "+hResult.getActor());
			
			boolean actorCompatibile = false;
			if(actor==null) {
				actorCompatibile = hResult.getActor()==null;
			}
			else {
				actorCompatibile = actor.equals(hResult.getActor());
			}
			
			if(actorCompatibile) {
				
				//System.out.println("RESULT["+i+"] ESAMINO SENZA ["+actor+"]");
				
				List<WSSecurityEngineResult> hResults = hResult.getResults();
				for (int j = 0; j < hResults.size(); j++) {
					WSSecurityEngineResult eResult = hResults.get(j);
					
					/*
					Iterator<String> resultIt = eResult.keySet().iterator();
					while (resultIt.hasNext()) {
						String resultItKey = (String) resultIt.next();
						Object resultItObject = eResult.get(resultItKey);
						System.out.println("RESULT-INTERNAL ["+i+"]["+j+"] ["+resultItKey+"]["+resultItObject+"]["+(resultItObject!=null ? resultItObject.getClass().getName() : "n.d.")+"]");
					}*/
					
					int actionGet = ((java.lang.Integer)  eResult.get(WSSecurityEngineResult.TAG_ACTION)).intValue();
					
					// An encryption or timestamp action does not have an associated principal,
					// only Signature and UsernameToken actions return a principal
					if ((actionGet == WSConstants.SIGN) || 
							(actionGet == WSConstants.UT) || 
							(actionGet == WSConstants.UT_SIGN) || 
							(actionGet == WSConstants.ST_SIGNED)) {
						
						Object oPrincipal = eResult.get(WSSecurityEngineResult.TAG_PRINCIPAL);
						if(oPrincipal!=null && oPrincipal instanceof Principal) {
							Principal principal =  (Principal) oPrincipal;
							if(principal!=null) {
								this.certificate = principal.getName();
							}
						}
	
						Object oX509Certificate = eResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATE);
						if(oX509Certificate!=null && oX509Certificate instanceof X509Certificate) {
							this.x509Certificate = (X509Certificate) oX509Certificate;
							if(this.x509Certificate!=null) {
								this.publicKey = this.x509Certificate.getPublicKey();
							}
						}
							
						Object oListX509Certificates = eResult.get(WSSecurityEngineResult.TAG_X509_CERTIFICATES);
						if(oListX509Certificates!=null && oListX509Certificates instanceof X509Certificate []){
							this.x509Certificates = (X509Certificate []) oListX509Certificates;
							
							/*
							if(this.x509Certificates!=null && this.x509Certificates.length>0) {
								System.out.println("CERTS: "+this.x509Certificates.length);
								int index = 0;
								for (X509Certificate X509Certificate : this.x509Certificates) {
									System.out.println("CERT["+index+"]: "+X509Certificate.getSubjectX500Principal().toString());	
									index ++;
								}
							}
							*/
							
						}
						
						Object oX509ReferenceType = eResult.get(WSSecurityEngineResult.TAG_X509_REFERENCE_TYPE);
						if(oX509ReferenceType!=null && oX509ReferenceType instanceof STRParser.REFERENCE_TYPE) {
							this.x509ReferenceType = (STRParser.REFERENCE_TYPE) oX509ReferenceType;
							
							//System.out.println("REFERENCE TYPE: "+this.x509ReferenceType);
						}
						
						Object oSignatureRefs = eResult.get(WSSecurityEngineResult.TAG_DATA_REF_URIS);
						if(oSignatureRefs!=null && oSignatureRefs instanceof List<?>) {
							List<WSDataRef> l = new ArrayList<WSDataRef>();
							List<?> lRefs = (List<?>) oSignatureRefs;
							for (Object oRef : lRefs) {
								if(oRef!=null && oRef instanceof WSDataRef) {
									l.add((WSDataRef)oRef);
								}
							}
							if(!l.isEmpty()) {
								this.signatureRefs = l;
							}
						}
					}
					
					if( actionGet == WSConstants.ENCR ) {
						
						Object oEncryptionRefs = eResult.get(WSSecurityEngineResult.TAG_DATA_REF_URIS);
						if(oEncryptionRefs!=null && oEncryptionRefs instanceof List<?>) {
							List<WSDataRef> l = new ArrayList<WSDataRef>();
							List<?> lRefs = (List<?>) oEncryptionRefs;
							for (Object oRef : lRefs) {
								if(oRef!=null && oRef instanceof WSDataRef) {
									l.add((WSDataRef)oRef);
								}
							}
							if(!l.isEmpty()) {
								this.encryptionRefs = l;
							}
						}
						
					}
					
					if( actionGet == WSConstants.TS ) {
						
						Object oTimestamp = eResult.get(WSSecurityEngineResult.TAG_TIMESTAMP);
						if(oTimestamp!=null && oTimestamp instanceof Timestamp) {
							this.timestamp =  (Timestamp) oTimestamp;
						}
						
					}
				}
				
			}

		}

		/*
		String key = "org.apache.cxf.headers.Header.list";
		if(msgCtx.containsKey(key)) {
			Object o = msgCtx.get(key);
			if(o instanceof List<?> ) {
				List<?> l = (List<?>) o;
				System.out.println("SIZE ["+l.size()+"]");
				if(!l.isEmpty()) {
					int index = 0;
					for (Object entry : l) {
						if(entry!=null) {
							System.out.println("ENTRY ["+index+"]: "+entry.getClass().getName());
							if(entry instanceof org.apache.cxf.binding.soap.SoapHeader) {
								org.apache.cxf.binding.soap.SoapHeader soapHdr = (org.apache.cxf.binding.soap.SoapHeader) entry;
								System.out.println("HDR ("+soapHdr.getActor()+") ("+soapHdr.getObject().getClass().getName()+"): "+soapHdr.getName());
							}
							
						}
						index++;
					}
				}
			}
		}
		*/
	}
	

	@Override
	public List<Reference> getDirtyElements(
			org.openspcoop2.security.message.MessageSecurityContext messageSecurityContext,
			OpenSPCoop2SoapMessage message) throws SecurityException {
		return WSSUtilities.getDirtyElements(messageSecurityContext, message);
	}

	@Override
	public Map<QName, QName> checkEncryptSignatureParts(
			org.openspcoop2.security.message.MessageSecurityContext messageSecurityContext,
			List<Reference> elementsToClean, OpenSPCoop2SoapMessage message,
			List<SubErrorCodeSecurity> codiciErrore) throws SecurityException {
		return MessageUtilities.checkEncryptSignatureParts(messageSecurityContext, elementsToClean, message, codiciErrore, SecurityConstants.QNAME_WSS_ELEMENT_SECURITY);
	}

	@Override
	public void checkEncryptionPartElements(Map<QName, QName> notResolved,
			OpenSPCoop2SoapMessage message,
			List<SubErrorCodeSecurity> erroriRilevati) throws SecurityException {
		MessageUtilities.checkEncryptionPartElements(notResolved, message, erroriRilevati);
	}

	@Override
	public void cleanDirtyElements(
			org.openspcoop2.security.message.MessageSecurityContext messageSecurityContext,
			OpenSPCoop2SoapMessage message, List<Reference> elementsToClean,
			boolean detachHeaderWSSecurity, boolean removeAllIdRef)
			throws SecurityException {
		WSSUtilities.cleanDirtyElements(messageSecurityContext, message, elementsToClean,detachHeaderWSSecurity,removeAllIdRef);
		
	}
}
