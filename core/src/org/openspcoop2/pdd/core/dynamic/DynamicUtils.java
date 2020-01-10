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

package org.openspcoop2.pdd.core.dynamic;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.message.OpenSPCoop2RestJsonMessage;
import org.openspcoop2.message.OpenSPCoop2RestXmlMessage;
import org.openspcoop2.message.OpenSPCoop2SoapMessage;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.message.utils.DumpAttachment;
import org.openspcoop2.message.utils.DumpMessaggio;
import org.openspcoop2.message.xml.XMLUtils;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.core.token.InformazioniToken;
import org.openspcoop2.protocol.engine.RequestInfo;
import org.openspcoop2.protocol.sdk.Busta;
import org.openspcoop2.utils.DynamicStringReplace;
import org.openspcoop2.utils.date.DateManager;
import org.openspcoop2.utils.io.ArchiveType;
import org.openspcoop2.utils.io.CompressorUtilities;
import org.openspcoop2.utils.io.Entry;
import org.openspcoop2.utils.io.ZipUtilities;
import org.openspcoop2.utils.resources.FreemarkerTemplateLoader;
import org.openspcoop2.utils.resources.TemplateUtils;
import org.openspcoop2.utils.resources.VelocityTemplateLoader;
import org.openspcoop2.utils.resources.VelocityTemplateUtils;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;

/**
 * DynamicUtils
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class DynamicUtils {

	// *** DYNAMIC MAP ***
	
	// NOTA: uso volutamente le stesse costanti del connettore File
	
	public static void fillDynamicMapRequest(Logger log, Map<String, Object> dynamicMap, PdDContext pddContext, String urlInvocazione,
			OpenSPCoop2Message message,
			Element element,
			String elementJson,
			Busta busta, Properties trasporto, Properties url,
			ErrorHandler errorHandler) {
		_fillDynamicMap(log, dynamicMap, pddContext, urlInvocazione, 
				message,
				element, 
				elementJson, 
				busta, trasporto, url,
				errorHandler);	
    }
	public static void fillDynamicMapResponse(Logger log, Map<String, Object> dynamicMap, Map<String, Object> dynamicMapRequest, PdDContext pddContext,
			OpenSPCoop2Message message,
			Element element,
			String elementJson,
			Busta busta, Properties trasporto,
			ErrorHandler errorHandler) {
		Map<String, Object> dynamicMapResponse = new HashMap<>();
		_fillDynamicMap(log, dynamicMapResponse, pddContext, null, 
				message,
				element, 
				elementJson, 
				busta, trasporto, null,
				errorHandler);
		if(dynamicMapResponse!=null && !dynamicMapResponse.isEmpty()) {
			Iterator<String> it = dynamicMapResponse.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object o = dynamicMapResponse.get(key);
				if(Costanti.MAP_ERROR_HANDLER_OBJECT.toLowerCase().equals(key.toLowerCase()) 
						|| 
					Costanti.MAP_RESPONSE.toLowerCase().equals(key.toLowerCase())){
					dynamicMap.put(key, o);
				}
				else {
					String keyResponse = key+Costanti.MAP_SUFFIX_RESPONSE;
					dynamicMap.put(keyResponse, o);
					dynamicMap.put(keyResponse.toLowerCase(), o);
				}
			}
		}
		if(dynamicMapRequest!=null && !dynamicMapRequest.isEmpty()) {
			Iterator<String> it = dynamicMapRequest.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if(Costanti.MAP_ERROR_HANDLER_OBJECT.toLowerCase().equals(key.toLowerCase())
						|| 
					Costanti.MAP_REQUEST.toLowerCase().equals(key.toLowerCase())){
					continue; // error handler viene usato quello istanziato per la risposta; mentre la richiesta è già stata consumata.
				}
				Object o = dynamicMapRequest.get(key);
				if(o instanceof PatternExtractor) {
					PatternExtractor pe = (PatternExtractor) o;
					pe.refreshContent();
				}
				dynamicMap.put(key, o);
			}
		}
	}
	public static void _fillDynamicMap(Logger log, Map<String, Object> dynamicMap, PdDContext pddContext, String urlInvocazione,
			OpenSPCoop2Message message,
			Element element,
			String elementJson,
			Busta busta, Properties trasporto, Properties url,
			ErrorHandler errorHandler) {
		DynamicInfo dInfo = new DynamicInfo();
		dInfo.setBusta(busta);
		dInfo.setPddContext(pddContext);
		if(trasporto!=null && !trasporto.isEmpty()) {
			Properties pNew = new Properties();
			pNew.putAll(trasporto);
			dInfo.setTrasporto(pNew);
		}
		if(url!=null && !url.isEmpty()) {
			Properties pNew = new Properties();
			pNew.putAll(url);
			dInfo.setQueryParameters(pNew);
		}
		if(urlInvocazione!=null) {
			dInfo.setUrl(urlInvocazione);
		}
		if(element!=null) {
			dInfo.setXml(element);
		}
		else if(elementJson!=null) {
			dInfo.setJson(elementJson);
		}
		if(message!=null) {
			dInfo.setMessage(message);
		}
		dInfo.setErrorHandler(errorHandler);
		DynamicUtils.fillDynamicMap(log, dynamicMap, dInfo);		
    }
	
	public static void fillDynamicMap(Logger log, Map<String, Object> dynamicMap, DynamicInfo dynamicInfo) {
		if(dynamicMap.containsKey(Costanti.MAP_DATE_OBJECT)==false) {
			dynamicMap.put(Costanti.MAP_DATE_OBJECT, DateManager.getDate());
		}
		
		if(dynamicInfo!=null && dynamicInfo.getPddContext()!=null && dynamicInfo.getPddContext().getContext()!=null) {
			if(dynamicMap.containsKey(Costanti.MAP_CTX_OBJECT)==false) {
				dynamicMap.put(Costanti.MAP_CTX_OBJECT, dynamicInfo.getPddContext().getContext());
			}
			if(dynamicMap.containsKey(Costanti.MAP_TRANSACTION_ID_OBJECT)==false) {
				if(dynamicInfo.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE)) {
					String idTransazione = (String)dynamicInfo.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE);
					dynamicMap.put(Costanti.MAP_TRANSACTION_ID_OBJECT, idTransazione);
				}
			}
			if(dynamicMap.containsKey(Costanti.MAP_URL_PROTOCOL_CONTEXT_OBJECT)==false) {
				if(dynamicInfo.getPddContext().containsKey(org.openspcoop2.core.constants.Costanti.REQUEST_INFO)) {
					RequestInfo requestInfo = (RequestInfo)dynamicInfo.getPddContext().getObject(org.openspcoop2.core.constants.Costanti.REQUEST_INFO);
					if(requestInfo.getProtocolContext()!=null) {
						dynamicMap.put(Costanti.MAP_URL_PROTOCOL_CONTEXT_OBJECT, requestInfo.getProtocolContext());
						dynamicMap.put(Costanti.MAP_URL_PROTOCOL_CONTEXT_OBJECT.toLowerCase(), requestInfo.getProtocolContext());
					}
				}
			}
			if(dynamicMap.containsKey(Costanti.MAP_TOKEN_INFO)==false) {
				Object oInformazioniTokenNormalizzate = dynamicInfo.getPddContext().getObject(org.openspcoop2.pdd.core.token.Costanti.PDD_CONTEXT_TOKEN_INFORMAZIONI_NORMALIZZATE);
	    		if(oInformazioniTokenNormalizzate!=null) {
	    			InformazioniToken informazioniTokenNormalizzate = (InformazioniToken) oInformazioniTokenNormalizzate;
	    			dynamicMap.put(Costanti.MAP_TOKEN_INFO, informazioniTokenNormalizzate);
	    			dynamicMap.put(Costanti.MAP_TOKEN_INFO.toLowerCase(), informazioniTokenNormalizzate);
	    		}
			}
		}
		
		if(dynamicMap.containsKey(Costanti.MAP_BUSTA_OBJECT)==false && dynamicInfo!=null && dynamicInfo.getBusta()!=null) {
			dynamicMap.put(Costanti.MAP_BUSTA_OBJECT, dynamicInfo.getBusta());
		}
		if(dynamicMap.containsKey(Costanti.MAP_BUSTA_PROPERTY)==false && dynamicInfo!=null && 
				dynamicInfo.getBusta()!=null && dynamicInfo.getBusta().sizeProperties()>0) {
			Properties propertiesBusta = new Properties();
			String[] pNames = dynamicInfo.getBusta().getPropertiesNames();
			if(pNames!=null && pNames.length>0) {
				for (int j = 0; j < pNames.length; j++) {
					String pName = pNames[j];
					String pValue = dynamicInfo.getBusta().getProperty(pName);
					if(pValue!=null) {
						propertiesBusta.setProperty(pName, pValue);
					}
				}
			}
			if(!propertiesBusta.isEmpty()) {
				dynamicMap.put(Costanti.MAP_BUSTA_PROPERTY, propertiesBusta);
			}
		}
		
		if(dynamicMap.containsKey(Costanti.MAP_HEADER)==false && dynamicInfo!=null && 
				dynamicInfo.getTrasporto()!=null && !dynamicInfo.getTrasporto().isEmpty()) {
			dynamicMap.put(Costanti.MAP_HEADER, dynamicInfo.getTrasporto());
		}
		
		if(dynamicMap.containsKey(Costanti.MAP_QUERY_PARAMETER)==false && dynamicInfo!=null && 
				dynamicInfo.getQueryParameters()!=null && !dynamicInfo.getQueryParameters().isEmpty()) {
			dynamicMap.put(Costanti.MAP_QUERY_PARAMETER, dynamicInfo.getQueryParameters());
		}
		
		// questi sottostanti, non sono disponnibili sul connettore
		if(dynamicInfo!=null && dynamicInfo.getUrl()!=null) {
			URLRegExpExtractor urle = new URLRegExpExtractor(dynamicInfo.getUrl(), log);
			dynamicMap.put(Costanti.MAP_ELEMENT_URL_REGEXP, urle);
			dynamicMap.put(Costanti.MAP_ELEMENT_URL_REGEXP.toLowerCase(), urle);
		}
		if(dynamicInfo!=null && dynamicInfo.getXml()!=null) {
			OpenSPCoop2MessageFactory messageFactory = dynamicInfo.getMessage()!=null ? dynamicInfo.getMessage().getFactory() : OpenSPCoop2MessageFactory.getDefaultMessageFactory();
			PatternExtractor pe = new PatternExtractor(messageFactory,dynamicInfo.getXml(), log);
			dynamicMap.put(Costanti.MAP_ELEMENT_XML_XPATH, pe);
			dynamicMap.put(Costanti.MAP_ELEMENT_XML_XPATH.toLowerCase(), pe);
		}
		if(dynamicInfo!=null && dynamicInfo.getJson()!=null) {
			PatternExtractor pe = new PatternExtractor(dynamicInfo.getJson(), log);
			dynamicMap.put(Costanti.MAP_ELEMENT_JSON_PATH, pe);
			dynamicMap.put(Costanti.MAP_ELEMENT_JSON_PATH.toLowerCase(), pe);
		}
		if(dynamicInfo!=null && dynamicInfo.getMessage()!=null) {
			ContentExtractor content = new ContentExtractor(dynamicInfo.getMessage(), log);
			if(MessageRole.REQUEST.equals(dynamicInfo.getMessage().getMessageRole())) {
				dynamicMap.put(Costanti.MAP_REQUEST, content);
			}
			else {
				dynamicMap.put(Costanti.MAP_RESPONSE, content);
			}
		}
		if(dynamicInfo!=null && dynamicInfo.getErrorHandler()!=null) {
			dynamicMap.put(Costanti.MAP_ERROR_HANDLER_OBJECT, dynamicInfo.getErrorHandler());
			dynamicMap.put(Costanti.MAP_ERROR_HANDLER_OBJECT.toLowerCase(), dynamicInfo.getErrorHandler());
		}
	}

	
	
	
	// READ DYNAMIC INFO
	
	public static DynamicInfo readDynamicInfo(OpenSPCoop2Message message) throws DynamicException {
		Element element = null;
		String elementJson = null;
		Properties parametriTrasporto = null;
		Properties parametriUrl = null;
		String urlInvocazione = null;
		
		try{
			if(ServiceBinding.SOAP.equals(message.getServiceBinding())){
				OpenSPCoop2SoapMessage soapMessage = message.castAsSoap();
				element = soapMessage.getSOAPPart().getEnvelope();
			}
			else{
				if(MessageType.XML.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestXmlMessage xml = message.castAsRestXml();
					element = xml.getContent();	
				}
				else if(MessageType.JSON.equals(message.getMessageType()) && message.castAsRest().hasContent()){
					OpenSPCoop2RestJsonMessage json = message.castAsRestJson();
					elementJson = json.getContent();
				}
			}
			

			if(message.getTransportRequestContext()!=null) {
				if(message.getTransportRequestContext().getParametersTrasporto()!=null &&
					!message.getTransportRequestContext().getParametersTrasporto().isEmpty()) {
					parametriTrasporto = message.getTransportRequestContext().getParametersTrasporto();
				}
				if(message.getTransportRequestContext().getParametersFormBased()!=null &&
						!message.getTransportRequestContext().getParametersFormBased().isEmpty()) {
					parametriUrl = message.getTransportRequestContext().getParametersFormBased();
				}
				urlInvocazione = message.getTransportRequestContext().getUrlInvocazione_formBased();
			}
			else if(message.getTransportResponseContext()!=null) {
				if(message.getTransportResponseContext().getParametersTrasporto()!=null &&
						!message.getTransportResponseContext().getParametersTrasporto().isEmpty()) {
					parametriTrasporto = message.getTransportResponseContext().getParametersTrasporto();
				}
			}
			
		}catch(Throwable e){
			throw new DynamicException(e.getMessage(),e);
		}
		
		DynamicInfo dInfo = new DynamicInfo();
		dInfo.setJson(elementJson);
		dInfo.setXml(element);
		dInfo.setTrasporto(parametriTrasporto);
		dInfo.setQueryParameters(parametriUrl);
		dInfo.setUrl(urlInvocazione);
		return dInfo;
	}
	
	
	
	
	// *** TEMPLATE GOVWAY ***

	public static String convertDynamicPropertyValue(String name,String tmpParam,Map<String,Object> dynamicMap,PdDContext pddContext, boolean forceStartWithDollaro) throws DynamicException{
		
		String tmp = tmpParam;
		if(!forceStartWithDollaro) {
			// per retrocompatibilità nel connettore file gestisco entrambi
			while(tmp.contains("${")) {
				tmp = tmp.replace("${", "{");
			}
		}
		
		String transactionIdConstant = Costanti.MAP_TRANSACTION_ID;
		if(forceStartWithDollaro) {
			transactionIdConstant = "$"+transactionIdConstant;
		}
		if(tmp.contains(transactionIdConstant)){
			String idTransazione = (String)pddContext.getObject(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE);
			while(tmp.contains(transactionIdConstant)){
				tmp = tmp.replace(transactionIdConstant, idTransazione);
			}
		}
		
		boolean request = false;
		boolean response = true;
		
		// conversione url
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				false, false, true, 
				forceStartWithDollaro, request);
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				false, false, true, 
				forceStartWithDollaro, response);
		
		// conversione xpath
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				true, false, false, 
				forceStartWithDollaro, request);
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				true, false, false, 
				forceStartWithDollaro, response);
		
		// conversione jsonpath
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				false, true, false, 
				forceStartWithDollaro, request);
		tmp = convertDynamicPropertyContent(tmp, dynamicMap, 
				false, true, false, 
				forceStartWithDollaro, response);
		
		try{
			tmp = DynamicStringReplace.replace(tmp, dynamicMap, forceStartWithDollaro);
		}catch(Exception e){
			throw new DynamicException("Proprieta' '"+name+"' contiene un valore non corretto: "+e.getMessage(),e);
		}
		return tmp;
	}
	
	private static String convertDynamicPropertyContent(String tmp, Map<String,Object> dynamicMap, 
			boolean xml, boolean json, boolean url, 
			boolean forceStartWithDollaro, boolean response) throws DynamicException {
		
		String istruzione = Costanti.MAP_ELEMENT_XML_XPATH;
		String prefix = Costanti.MAP_ELEMENT_XML_XPATH_PREFIX;
		if(json) {
			istruzione = Costanti.MAP_ELEMENT_JSON_PATH;
			prefix = Costanti.MAP_ELEMENT_JSON_PATH_PREFIX;
		}
		else if(url) {
			istruzione = Costanti.MAP_ELEMENT_URL_REGEXP;
			prefix = Costanti.MAP_ELEMENT_URL_REGEXP_PREFIX;
		}
		if(forceStartWithDollaro) {
			prefix = "$"+prefix;
		}
		if(response) {
			istruzione = istruzione+Costanti.MAP_SUFFIX_RESPONSE;
			prefix = prefix.substring(0,prefix.length()-1);
			prefix = prefix + Costanti.MAP_SUFFIX_RESPONSE + ":";
		}
		
		String tmpLowerCase = tmp.toLowerCase();
		String prefixLowerCase = prefix.toLowerCase();
		
		if(tmpLowerCase.contains(prefixLowerCase)){
			int maxIteration = 100;
			while (maxIteration>0 && tmpLowerCase.contains(prefixLowerCase)) {
				int indexOfStart = tmpLowerCase.indexOf(prefixLowerCase);
				String pattern = tmp.substring(indexOfStart+prefix.length(),tmp.length());
				if(pattern.contains("}")==false) {
					throw new DynamicException("Trovata istruzione '"+istruzione+"' non correttamente formata (chiusura '}' non trovata)");
				}
				
				// cerco chiusura, all'interno ci potrebbero essere altre aperture di { per le regole xpath
				char [] patternChars = pattern.toCharArray();
				int numAperture = 0;
				int positionChiusura = -1;
				for (int i = 0; i < patternChars.length; i++) {
					if(patternChars[i] == '{') {
						numAperture++;
					}
					if(patternChars[i] == '}') {
						if(numAperture==0) {
							positionChiusura = i;
							break;
						}
						else {
							numAperture--;
						}
					}
				}
				if(positionChiusura<=0) {
					throw new DynamicException("Trovata istruzione '"+istruzione+"' non correttamente formata (chiusura '}' non trovata)");
				}
				
				pattern = pattern.substring(0,positionChiusura);
				
				String complete = tmp.substring(indexOfStart, positionChiusura+indexOfStart+prefix.length()+1);
				Object o = dynamicMap.get(istruzione);
				if(o==null) {
					throw new DynamicException("Trovata istruzione '"+istruzione+"' non utilizzabile in questo contesto");
				}
				String value = null;
				if(json || xml) {
					if( !(o instanceof PatternExtractor) ) {
						throw new DynamicException("Trovata istruzione '"+istruzione+"' non utilizzabile in questo contesto (extractor wrong class: "+o.getClass().getName()+")");
					}
					PatternExtractor patternExtractor = (PatternExtractor) o;
					value = patternExtractor.read(pattern);
				}
				else {
					if( !(o instanceof URLRegExpExtractor) ) {
						throw new DynamicException("Trovata istruzione '"+istruzione+"' non utilizzabile in questo contesto (extractor wrong class: "+o.getClass().getName()+")");
					}
					URLRegExpExtractor urlExtractor = (URLRegExpExtractor) o;
					value = urlExtractor.read(pattern);
				}
				if(value==null) {
					value = "";
				}
				tmp = tmp.replace(complete, value);
				tmpLowerCase = tmp.toLowerCase();
				maxIteration--;
			}
		}
		
		return tmp;
	}
	
	
	// *** FREEMARKER ***
	
	public static void convertFreeMarkerTemplate(String name, 
			byte[] template,
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		convertFreeMarkerTemplate(name,
				template, null,
				dynamicMap,out);
	}
	public static void convertFreeMarkerTemplate(String name, 
			byte[] template, Map<String, byte[]> templateIncludes, 
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		try {			
			OutputStreamWriter oow = new OutputStreamWriter(out);
			_convertFreeMarkerTemplate(name, 
					template, templateIncludes, 
					dynamicMap, oow);
			oow.flush();
			oow.close();
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	
	public static void convertFreeMarkerTemplate(String name, 
			byte[] template, 
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		convertFreeMarkerTemplate(name,
				template, null,
				dynamicMap,writer);
	}
	public static void convertFreeMarkerTemplate(String name, 
			byte[] template, Map<String, byte[]> templateIncludes, 
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		_convertFreeMarkerTemplate(name, 
				template, templateIncludes, 
				dynamicMap, writer);
	}
	
	public static void convertZipFreeMarkerTemplate(String name, 
			byte[] zip,
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		try {			
			OutputStreamWriter oow = new OutputStreamWriter(out);
			convertZipFreeMarkerTemplate(name, 
					zip,  
					dynamicMap, oow);
			oow.flush();
			oow.close();
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	public static void convertZipFreeMarkerTemplate(String name, 
			byte[] zip,
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		
		List<Entry> entries = null;
		try {
			entries = ZipUtilities.read(zip);
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
		if(entries.isEmpty()) {
			throw new DynamicException("Entries not found");
		}
		byte[] template = null;
		Map<String, byte[]> templateIncludes = new HashMap<>();
		for (Entry entry : entries) {
			if(Costanti.ZIP_INDEX_ENTRY_FREEMARKER.equals(entry.getName())) {
				template = entry.getContent();
			}
			else if(!entry.getName().contains("/") && !entry.getName().contains("\\") && template==null) {
				// prende il primo
				template = entry.getContent();
			}
			else {
				templateIncludes.put(entry.getName(), entry.getContent());
			}
		}
		
		_convertFreeMarkerTemplate(name, 
				template, templateIncludes, 
				dynamicMap, writer);
	}
	
	private static void _convertFreeMarkerTemplate(String name, 
			byte[] template,
			Map<String, byte[]> templateIncludes, 
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		try {
			// ** Aggiungo utility per usare metodi statici ed istanziare oggetti
			
			// statici
			BeansWrapper wrapper = new BeansWrapper(Configuration.VERSION_2_3_23);
			TemplateModel classModel = wrapper.getStaticModels();
			dynamicMap.put(Costanti.MAP_CLASS_LOAD_STATIC, classModel);
			
			// newObject
			dynamicMap.put(Costanti.MAP_CLASS_NEW_INSTANCE, new freemarker.template.utility.ObjectConstructor());
			
			// Configurazione
			freemarker.template.Configuration config = TemplateUtils.newTemplateEngine();
			config.setAPIBuiltinEnabled(true); // serve per modificare le mappe in freemarker
			
			// template includes
			if(templateIncludes!=null && !templateIncludes.isEmpty()) {
				config.setTemplateLoader(new FreemarkerTemplateLoader(templateIncludes));
			}
			
			// ** costruisco template
			Template templateFTL = TemplateUtils.buildTemplate(config, name, template);
			templateFTL.process(dynamicMap, writer);
			writer.flush();
			
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	
	
	
	// *** VELOCITY ***
	
	public static void convertVelocityTemplate(String name, 
			byte[] template, 
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		convertVelocityTemplate(name, 
				template, null, 
				dynamicMap, out);
	}
	public static void convertVelocityTemplate(String name, 
			byte[] template, Map<String, byte[]> templateIncludes,
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		try {
			OutputStreamWriter oow = new OutputStreamWriter(out);
			_convertVelocityTemplate(name, 
					template, templateIncludes, 
					dynamicMap, oow);
			oow.flush();
			oow.close();
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	
	public static void convertVelocityTemplate(String name, byte[] template, Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		convertVelocityTemplate(name, 
				template, null, 
				dynamicMap, writer);
	}
	public static void convertVelocityTemplate(String name, 
			byte[] template, Map<String, byte[]> templateIncludes,
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		_convertVelocityTemplate(name, 
				template, templateIncludes,
				dynamicMap, writer);
	}
	
	public static void convertZipVelocityTemplate(String name, 
			byte[] zip,
			Map<String,Object> dynamicMap, OutputStream out) throws DynamicException {
		try {			
			OutputStreamWriter oow = new OutputStreamWriter(out);
			convertZipVelocityTemplate(name, 
					zip,  
					dynamicMap, oow);
			oow.flush();
			oow.close();
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	public static void convertZipVelocityTemplate(String name, 
			byte[] zip,
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		
		List<Entry> entries = null;
		try {
			entries = ZipUtilities.read(zip);
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
		if(entries.isEmpty()) {
			throw new DynamicException("Entries not found");
		}
		byte[] template = null;
		Map<String, byte[]> templateIncludes = new HashMap<>();
		for (Entry entry : entries) {
			if(Costanti.ZIP_INDEX_ENTRY_VELOCITY.equals(entry.getName())) {
				template = entry.getContent();
			}
			else if(!entry.getName().contains("/") && !entry.getName().contains("\\") && template==null) {
				// prende il primo
				template = entry.getContent();
			}
			else {
				templateIncludes.put(entry.getName(), entry.getContent());
			}
		}
		
		_convertVelocityTemplate(name, 
				template, templateIncludes, 
				dynamicMap, writer);
	}
	
	private static void _convertVelocityTemplate(String name, 
			byte[] template, 
			Map<String, byte[]> templateIncludes, 
			Map<String,Object> dynamicMap, Writer writer) throws DynamicException {
		try {
			// ** Aggiungo utility per usare metodi statici ed istanziare oggetti
			
			// statici
			dynamicMap.put(Costanti.MAP_CLASS_LOAD_STATIC, "".getClass());
			
			// newObject
			dynamicMap.put(Costanti.MAP_CLASS_NEW_INSTANCE, new ObjectConstructor());
			
			// Configurazione
			org.apache.velocity.Template templateVelocity = VelocityTemplateUtils.buildTemplate(name, template);
			
			// template includes
			if(templateIncludes!=null && !templateIncludes.isEmpty()) {
				templateVelocity.setResourceLoader(new VelocityTemplateLoader(templateIncludes));
			}
			
			// ** costruisco template
			templateVelocity.merge(VelocityTemplateUtils.toVelocityContext(dynamicMap), writer);
			writer.flush();
			
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
		
	
	
	// *** XSLT ***
	
	public static void convertXSLTTemplate(String name, byte[] template, Element element, OutputStream out) throws DynamicException {
		try {
			Source xsltSource = new StreamSource(new ByteArrayInputStream(template));
			Source xmlSource = new DOMSource(element);
			Transformer trans = XMLUtils.DEFAULT.getTransformerFactory().newTransformer(xsltSource);
			trans.transform(xmlSource, new StreamResult(out));
			out.flush();
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
	}
	
	
	
	// *** COMPRESS ***
	
	public static void convertCompressorTemplate(String name,byte[] template,Map<String,Object> dynamicMap,PdDContext pddContext, 
			ArchiveType archiveType, OutputStream out) throws DynamicException{
		try {
			try(ByteArrayInputStream bin = new ByteArrayInputStream(template)){
				Properties p = new Properties();
				p.load(bin);
				
				ContentExtractor contentExtractor = null;
				String ruolo = null;
				if(dynamicMap.containsKey(Costanti.MAP_REQUEST)) {
					contentExtractor = (ContentExtractor) dynamicMap.get(Costanti.MAP_REQUEST);
					ruolo = "messaggio di richiesta";
				}
				else if(dynamicMap.containsKey(Costanti.MAP_RESPONSE)) {
					contentExtractor = (ContentExtractor) dynamicMap.get(Costanti.MAP_RESPONSE);
					ruolo = "messaggio di risposta";
				}
				
				List<Entry> listEntries = new ArrayList<>();
				
				Enumeration<?> keys = p.keys();
				while (keys.hasMoreElements()) {
					String keyP = (String) keys.nextElement();
					keyP = keyP.trim();
					
					String oggetto = "property-"+keyP;
					String entryName = null;
					try {
						entryName = DynamicUtils.convertDynamicPropertyValue(oggetto, keyP, dynamicMap, pddContext, true);
					}catch(Exception e) {
						throw new Exception("["+oggetto+"] Conversione valore per entry name '"+keyP+"' non riuscita: "+e.getMessage(),e);
					}
					String prefixError = "["+keyP+"] ";
					
					String valoreP = p.getProperty(keyP);
					if(valoreP==null) {
						throw new Exception(prefixError+"Nessun valore fornito per la proprietà");
					}
					valoreP = valoreP.trim();
					byte[] content = null;
					if(Costanti.COMPRESS_CONTENT.equals(valoreP)) {
						if(contentExtractor==null || !contentExtractor.hasContent()) {
							throw new Exception(prefixError+"Il "+ruolo+" non possiede un payload");
						}
						content = contentExtractor.getContent();
					}
					else if(Costanti.COMPRESS_ENVELOPE.equals(valoreP) ||
							Costanti.COMPRESS_BODY.equals(valoreP) ||
							Costanti.COMPRESS_ENVELOPE.toLowerCase().equals(valoreP.toLowerCase()) ||
							Costanti.COMPRESS_BODY.toLowerCase().equals(valoreP.toLowerCase())) {
						if(contentExtractor==null || !contentExtractor.hasContent()) {
							throw new Exception(prefixError+"Il "+ruolo+" non possiede un payload");
						}
						if(!contentExtractor.isSoap()) {
							throw new Exception(prefixError+"Il "+ruolo+" non è un messaggio soap");
						}
						if(Costanti.COMPRESS_ENVELOPE.equals(valoreP) ||
								Costanti.COMPRESS_ENVELOPE.toLowerCase().equals(valoreP.toLowerCase())) {
							DumpMessaggio dump = contentExtractor.dumpMessage();
							if(dump==null) {
								throw new Exception(prefixError+"Dump del "+ruolo+" non disponibile");
							}
							content = dump.getBody();
						}
						else {
							content = contentExtractor.getContentSoapBody();
						}
					}
					else if(valoreP.startsWith(Costanti.COMPRESS_ATTACH_PREFIX) ||
							valoreP.startsWith(Costanti.COMPRESS_ATTACH_BY_ID_PREFIX) &&
							valoreP.endsWith(Costanti.COMPRESS_SUFFIX)) {
						String valoreInterno = valoreP.substring(Costanti.COMPRESS_ATTACH_PREFIX.length(), valoreP.length()-1);
						if(valoreInterno==null || "".equals(valoreInterno)) {
							throw new Exception(prefixError+"Non è stato definito un indice per l'attachment");
						}
						DumpMessaggio dump = contentExtractor.dumpMessage();
						if(dump==null) {
							throw new Exception(prefixError+"Dump del "+ruolo+" non disponibile");
						}
						
						DumpAttachment attach = null;
						boolean attachAtteso = true;
						if(valoreP.startsWith(Costanti.COMPRESS_ATTACH_PREFIX)) {
							int index = -1;
							try {
								index = Integer.valueOf(valoreInterno);
							}catch(Exception e) {
								throw new Exception(prefixError+"L'indice definito per l'attachment non è un numero intero: "+e.getMessage(),e);
							}
							if(contentExtractor.isRest() && index==0) {
								content = dump.getBody();
								attachAtteso = false;
							}
							else {
								if(contentExtractor.isRest()) {
									attach = dump.getAttachment((index-1));
								}
								else {
									attach = dump.getAttachment(index);
								}
							}
						}
						else {
							if(contentExtractor.isRest() &&
									dump.getMultipartInfoBody()!=null &&
									valoreInterno.equals(dump.getMultipartInfoBody().getContentId())) {
								content = dump.getBody();
								attachAtteso = false;
							}
							else {
								attach = dump.getAttachment(valoreInterno);
							}
						}
						
						if(attachAtteso) {
							if(attach==null) {
								throw new Exception(prefixError+"L'indice definito per l'attachment non ha identificato alcun attachment");
							}
							content = attach.getContent();
						}
					}
					else {
						String oggettoV = "valore-"+keyP;
						try {
							String v = DynamicUtils.convertDynamicPropertyValue(oggettoV, valoreP, dynamicMap, pddContext, true);
							if(v!=null) {
								content = v.getBytes();
							}
						}catch(Exception e) {
							throw new Exception(prefixError+"["+oggettoV+"] Conversione valore non riuscita: "+e.getMessage(),e);
						}
					}
					
					if(content==null) {
						throw new Exception(prefixError+"Nessun contenuto da associare alla entry trovato");
					}
					
					listEntries.add(new Entry(entryName, content));
				}
				
				out.write(CompressorUtilities.archive(listEntries, archiveType));
			}
		}catch(Exception e) {
			throw new DynamicException(e.getMessage(),e);
		}
		
	}
}
