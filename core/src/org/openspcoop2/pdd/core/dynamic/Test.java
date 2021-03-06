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

package org.openspcoop2.pdd.core.dynamic;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openspcoop2.message.AttachmentsProcessingMode;
import org.openspcoop2.message.OpenSPCoop2MessageFactory;
import org.openspcoop2.message.OpenSPCoop2MessageParseResult;
import org.openspcoop2.message.constants.MessageRole;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.pdd.core.PdDContext;
import org.openspcoop2.pdd.core.connettori.ConnettoreMsg;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.Utilities;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpConstants;
import org.slf4j.Logger;

/**
 * Test
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class Test {

	private static final String ENVELOPE = 
		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
		"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><prova>test</prova><prova2>test2</prova2><list>v1</list><list>v2</list><list>v3</list></soapenv:Body></soapenv:Envelope>";
	
	private static final String JSON = 
		"{\n"+	   
            "\"SortAs\": \"SGML\",\n"+
            "\"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
            "\"Acronym\": \"SGML\",\n"+
            "\"Abbrev\": \"ISO 8879:1986\",\n"+
            "\"Enabled\": true,\n"+
            "\"Year\": 2018,\n"+
            "\"Quote\": 1.45, \n"+
            "\"List\": [ \"Ford\", \"BMW\", \"Fiat\" ]\n"+
		"}";
		
	private static final String JSON_TEMPLATE = 
			"{\n"+	   
	            "\"SortAs\": \"${xpath://prova/text()}\",\n"+
	            "\"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
	            "\"Acronym\": \"${xPath://{http://schemas.xmlsoap.org/soap/envelope/}:Envelope/{http://schemas.xmlsoap.org/soap/envelope/}:Body/prova2/text()}\",\n"+
	            "\"Abbrev\": \"ISO 8879:1986\",\n"+
	            "\"Enabled\": true,\n"+
	            "\"Year\": 2018,\n"+
	            "\"Quote\": 1.45 \n"+
			"}";
	
	private static final String XML_TEMPLATE = 
			"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
			"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><prova>${jsonpath:$.Year}</prova><prova2>${jsonPath:$.Acronym}</prova2></soapenv:Body></soapenv:Envelope>";
	
	private static final String EXPECTED_JSON = 
			"{\n"+	   
	            "\"SortAs\": \"test\",\n"+
	            "\"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
	            "\"Acronym\": \"test2\",\n"+
	            "\"Abbrev\": \"ISO 8879:1986\",\n"+
	            "\"Enabled\": true,\n"+
	            "\"Year\": 2018,\n"+
	            "\"Quote\": 1.45 \n"+
			"}";
	private static final String EXPECTED_JSON_2 = 
			"{\n"+	   
	            "    \"SortAs\": \"test\",\n"+
	            "    \"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
	            "    \"Acronym\": \"test2\",\n"+
	            "    \"Abbrev\": \"ISO 8879:1986\",\n"+
	            "    \"Enabled\": true,\n"+
	            "    \"Year\": 2018,\n"+
	            "    \"Quote\": 1.45,\n"+
	            "    \"List\": [ \"v1\" ,\"v2\" ,\"v3\"  ]\n"+
			"}";
	private static final String EXPECTED_JSON_3 = 
			"{\n"+	   
	            "    \"SortAs\": \"test\",\n"+
	            "    \"GlossTerm\": \"Standard Generalized Markup Language\",\n"+
	            "    \"Acronym\": \"test2\",\n"+
	            "    \"Abbrev\": \"ISO 8879:1986\",\n"+
	            "    \"Enabled\": true,\n"+
	            "    \"Year\": 2018,\n"+
	            "    \"Quote\": 1.45,\n"+
	            "    \"include1\": {\n"+
	            "		\"SortAs\": \"111_test\",\n"+
	            "		\"GlossTerm\": \"TestPROVA1\"\n"+
	            "    },\n"+
	            "    \"include2\": {\n"+
	            "		\"SortAs\": \"222_test\",\n"+
	            "		\"GlossTerm\": \"TestPROVA2\"\n"+
	            "    },\n"+
	            "    \"List\": [ \"v1\" ,\"v2\" ,\"v3\"  ]\n"+
			"}";
			
	private static final String EXPECTED_XML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" 	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><prova>2018</prova><prova2>SGML</prova2></soapenv:Body></soapenv:Envelope>";
	
	private static final String EXPECTED_XML_2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n"+ 
			"			xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"+
			"			<soapenv:Body>\n"+
			"			<prova>2018</prova>\n"+
			"			<prova2>SGML</prova2>\n"+
			"			<list>Ford</list>\n"+
			"			<list>BMW</list>\n"+
			"			<list>Fiat</list>\n"+
			"		</soapenv:Body>\n"+
			"</soapenv:Envelope>\n"+
			"";
	
	private static final String EXPECTED_XML_3 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n"+
			"			xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"+
			"			<soapenv:Body>\n"+
			"			<prova>az2</prova>\n"+
			"			<prova2>test</prova2>\n"+
			"		</soapenv:Body>\n"+
			"</soapenv:Envelope>\n";
	
	public static void main(String [] args) throws Exception{
		
		boolean forceDollaro = true;
		String prefix = "$";
		
		Logger log = LoggerWrapperFactory.getLogger(Test.class);
		
		Map<String, Object> dynamicMap = new HashMap<>();
		
		ConnettoreMsg connettoreMsg = new ConnettoreMsg();
		
		connettoreMsg.setPropertiesTrasporto(new HashMap<String, List<String>>());
		TransportUtils.addHeader(connettoreMsg.getPropertiesTrasporto(),"Header1", "Valore1");
		TransportUtils.addHeader(connettoreMsg.getPropertiesTrasporto(),"Header2", "Valore2");
		
		connettoreMsg.setPropertiesUrlBased(new HashMap<String, List<String>>());
		TransportUtils.addParameter(connettoreMsg.getPropertiesUrlBased(),"P1", "Valore1URL");
		TransportUtils.addParameter(connettoreMsg.getPropertiesUrlBased(),"P2", "Valore2URL");
		
		
		PdDContext pddContext = new PdDContext();
		pddContext.addObject("TEST1", "VALORE DI ESEMPIO");
		
		boolean bufferMessage_readOnly = true;
		String idTransazione = "xxyy";
		pddContext.addObject(org.openspcoop2.core.constants.Costanti.ID_TRANSAZIONE, idTransazione);
		
		DynamicInfo dInfo = new DynamicInfo(connettoreMsg, pddContext);
		
		
		DynamicUtils.fillDynamicMap(log, dynamicMap, dInfo);
		
		System.out.println("TEST PDD CONTEXT: "+DynamicUtils.convertDynamicPropertyValue("testPddContext", prefix + "{context:TEST1}", dynamicMap, pddContext, forceDollaro));
	      
		System.out.println("HEADER: "+DynamicUtils.convertDynamicPropertyValue("testHeader", prefix+"{header:Header1}", dynamicMap, pddContext, forceDollaro));
		
		System.out.println("URL: "+DynamicUtils.convertDynamicPropertyValue("testUrl", prefix+"{query:P1}", dynamicMap, pddContext, forceDollaro));
		
		dynamicMap = new HashMap<>();
		dInfo = new DynamicInfo(connettoreMsg, pddContext);
		OpenSPCoop2MessageParseResult parser = OpenSPCoop2MessageFactory.getDefaultMessageFactory().createMessage(MessageType.SOAP_11, MessageRole.NONE, HttpConstants.CONTENT_TYPE_SOAP_1_1, ENVELOPE.getBytes(), AttachmentsProcessingMode.getMemoryCacheProcessingMode());
		MessageContent messageContent = new MessageContent(parser.getMessage().castAsSoap(), bufferMessage_readOnly, pddContext);
		dInfo.setMessageContent(messageContent);
		DynamicUtils.fillDynamicMap(log, dynamicMap, dInfo);
		
		String expr = prefix+"{xPath://{http://schemas.xmlsoap.org/soap/envelope/}:Envelope/{http://schemas.xmlsoap.org/soap/envelope/}:Body/prova/text()}";
		DynamicUtils.validate("testXml", expr, forceDollaro, true);
		String value = DynamicUtils.convertDynamicPropertyValue("testXml", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern1: "+value);
		String expected = "test";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{xPath://{http://schemas.xmlsoap.org/soap/envelope/}Envelope/{http://schemas.xmlsoap.org/soap/envelope/}Body/prova/text()}";
		DynamicUtils.validate("testXml", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testXml", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern1b: "+value);
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = "{xPath://{http://schemas.xmlsoap.org/soap/envelope/}Envelope/{http://schemas.xmlsoap.org/soap/envelope/}Body/prova/text()}";
		DynamicUtils.validate("testXml", expr, !forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testXml", expr, dynamicMap, pddContext, !forceDollaro);
		System.out.println("Pattern1c: "+value);
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{xpath://prova/text()}";
		DynamicUtils.validate("testXml2", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testXml2", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern2: "+value);
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = "Metto un po ("+prefix+"{xPath://prova/text()}) di testo prima ("+prefix+"{xPath://{http://schemas.xmlsoap.org/soap/envelope/}:Envelope/{http://schemas.xmlsoap.org/soap/envelope/}:Body/prova2/text()}) e dopo";
		DynamicUtils.validate("testXml3", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testXml3", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern3: "+value);
		expected = "Metto un po (test) di testo prima (test2) e dopo";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{xpath://provaNonEsiste/text()}";
		DynamicUtils.validate("testXmlPatternNotFound", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testXmlPatternNotFound", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern4: "+value);
		expected = "";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{xpath://provaNonEsiste/Text()}";
		DynamicUtils.validate("testXmlPatternError", expr, forceDollaro, true);
		try {
			System.out.println("Pattern5: "+DynamicUtils.convertDynamicPropertyValue("testXmlPatternError", expr, dynamicMap, pddContext, forceDollaro));
			throw new Exception("Attesa eccezione pattern malformato");
		}catch(Exception e) {
			//e.printStackTrace(System.out);
			if(e.getMessage().contains("Compilazione dell'espressione XPATH ha causato un errore (Unknown nodetype: Text)")) {
				System.out.println("Pattern5: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		
		expr = prefix+"{xPath://{http://schemas.xmlsoap.org/soap/envelope/}Envelope/{http://schemas.xmlsoap.org/soap/envelope/}Body/prova/text()";
		try {
			DynamicUtils.validate("testChiusuraMancante", expr, forceDollaro, true);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'xPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		try {
			value = DynamicUtils.convertDynamicPropertyValue("testChiusuraMancante", expr, dynamicMap, pddContext, forceDollaro);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'xPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		
		expr = prefix+"{xPath://{http://schemas.xmlsoap.org/soap/envelope/}Envelope/{http://schemas.xmlsoap.org/soap/envelope/Body/prova/text()}";
		try {
			DynamicUtils.validate("testChiusuraInternaMancante", expr, forceDollaro, true);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'xPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata2: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		try {
			value = DynamicUtils.convertDynamicPropertyValue("testChiusuraInternaMancante", expr, dynamicMap, pddContext, forceDollaro);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'xPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata2: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		
		if(prefix.equals("$")) {
			expr = JSON_TEMPLATE;
			DynamicUtils.validate("xml2json", expr, forceDollaro, true);
			value = DynamicUtils.convertDynamicPropertyValue("xml2json", JSON_TEMPLATE, dynamicMap, pddContext, forceDollaro);
			System.out.println("Test conversione xml2json: \n"+value);
			expected = EXPECTED_JSON;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		if(prefix.equals("$")) {
			byte[]template = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJson.ftl")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DynamicUtils.convertFreeMarkerTemplate("xml2jsonFTL", template, dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione xml2json via freemarker: \n"+value);
			expected = EXPECTED_JSON_2;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		if(prefix.equals("$")) {
			byte[]template = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJsonInclude.ftl")).toByteArray();
			byte[]prova1 = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJsonInclude_1.ftl")).toByteArray();
			byte[]prova2 = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJsonInclude_2.ftl")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			Map<String, byte[]> templateIncludes = new HashMap<>();
			templateIncludes.put("TestJsonInclude_1.ftl", prova1);
			templateIncludes.put("lib/TestJsonInclude_2.ftl", prova2);
			DynamicUtils.convertFreeMarkerTemplate("xml2jsonFTL_INCLUDE_MANUALE", template, templateIncludes, dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione xml2json via freemarker (con include): \n"+value);
			expected = EXPECTED_JSON_3;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		if(prefix.equals("$")) {
			byte[]zip = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJsonInclude.ftl.zip")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DynamicUtils.convertZipFreeMarkerTemplate("xml2jsonFTL_INCLUDE_ZIP", zip, dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione xml2json via freemarker (con include in archivio zip): \n"+value);
			expected = EXPECTED_JSON_3+"\n";
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		if(prefix.equals("$")) {
			byte[]zip = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestJsonInclude2.ftl.zip")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DynamicUtils.convertZipFreeMarkerTemplate("xml2jsonFTL_INCLUDE_ZIP2", zip, dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione xml2json via freemarker (con include in archivio zip test2): \n"+value);
			expected = EXPECTED_JSON_3+"\n";
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		dynamicMap = new HashMap<>();
		dInfo = new DynamicInfo(connettoreMsg, pddContext);
		OpenSPCoop2MessageParseResult parserJson = OpenSPCoop2MessageFactory.getDefaultMessageFactory().createMessage(MessageType.JSON, MessageRole.NONE, HttpConstants.CONTENT_TYPE_JSON, JSON.getBytes(), AttachmentsProcessingMode.getMemoryCacheProcessingMode());
		MessageContent messageContentJson = new MessageContent(parserJson.getMessage().castAsRestJson(), bufferMessage_readOnly, pddContext);
		dInfo.setMessageContent(messageContentJson);
		DynamicUtils.fillDynamicMap(log, dynamicMap, dInfo);
		
		expr = prefix+"{jsonPath:$.Year}";
		DynamicUtils.validate("testJson", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testJson", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern1: "+value);
		expected = "2018";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = "Metto un po ("+prefix+"{jsonpath:$.Year}) di altro test ("+prefix+"{jsonPath:$.Acronym}) fine";
		DynamicUtils.validate("test2Json", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("test2Json", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern2: "+value);
		expected = "Metto un po (2018) di altro test (SGML) fine";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{jsonPath:$.NotFound}";
		DynamicUtils.validate("testJsonPatternNotFound", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testJsonPatternNotFound", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("Pattern3: "+value);
		expected = "";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{jsonPath:$$$dedde}";
		DynamicUtils.validate("testJsonPatternError", expr, forceDollaro, true);
		try {
			System.out.println("Pattern4: "+DynamicUtils.convertDynamicPropertyValue("testJsonPatternError", expr, dynamicMap, pddContext, forceDollaro));
			throw new Exception("Attesa eccezione pattern malformato");
		}catch(Exception e) {
			if(e.getMessage().contains("Illegal character at position 1 expected '.' or '[")) {
				System.out.println("Pattern4: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
			//e.printStackTrace(System.out);
		}
		
		expr = prefix+"{jsonPath:$.NotFound";
		try {
			DynamicUtils.validate("testChiusuraMancante", expr, forceDollaro, true);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'jsonPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		try {
			value = DynamicUtils.convertDynamicPropertyValue("testChiusuraMancante", expr, dynamicMap, pddContext, forceDollaro);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'jsonPath' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		
		
		if(prefix.equals("$")) {
			expr = XML_TEMPLATE;
			DynamicUtils.validate("json2xml", expr, forceDollaro, true);
			value = DynamicUtils.convertDynamicPropertyValue("json2xml", expr, dynamicMap, pddContext, forceDollaro);
			System.out.println("Test conversione json2xml: \n"+value);
			expected = EXPECTED_XML;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		if(prefix.equals("$")) {
			byte[]template = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestXml.ftl")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DynamicUtils.convertFreeMarkerTemplate("json2xmlFTL", template,  dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione json2xml via freemarker: \n"+value);
			expected = EXPECTED_XML_2;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
		
		
		
		String url = "/govway/out/ENTE/Erogatore/Servizio/v1/azione/test?prova=test&azione=az2";
		dynamicMap = new HashMap<>();
		dInfo = new DynamicInfo(connettoreMsg, pddContext);
		dInfo.setUrl(url);
		DynamicUtils.fillDynamicMap(log, dynamicMap, dInfo);
		
		expr = prefix+"{urlRegExp:.+azione=([^&]*).*}";
		DynamicUtils.validate("testUrl", expr, forceDollaro, true);
		value = DynamicUtils.convertDynamicPropertyValue("testUrl", expr, dynamicMap, pddContext, forceDollaro);
		System.out.println("PatternUrl1: "+value);
		expected = "az2";
		if(!expected.equals(value)) {
			throw new Exception("Expected value '"+expected+"', found '"+value+"'");
		}
		
		expr = prefix+"{urlRegExp:.+azione=([^&]*).*";
		try {
			DynamicUtils.validate("testChiusuraMancante", expr, forceDollaro, true);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'urlRegExp' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		try {
			value = DynamicUtils.convertDynamicPropertyValue("testChiusuraMancante", expr, dynamicMap, pddContext, forceDollaro);
			throw new Exception("Attesa eccezione expr malformata");
		}catch(Exception e) {
			if(e.getMessage().contains("Trovata istruzione 'urlRegExp' non correttamente formata (chiusura '}' non trovata)")) {
				System.out.println("PatternExprErrata1: attesa eccezione "+e.getMessage());
			}
			else {
				throw e;
			}
		}
		
		if(prefix.equals("$")) {
			byte[]template = Utilities.getAsByteArrayOuputStream(Test.class.getResourceAsStream("/org/openspcoop2/pdd/core/dynamic/TestUrl.ftl")).toByteArray();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DynamicUtils.convertFreeMarkerTemplate("testUrlFTL", template, dynamicMap, bout);
			bout.flush();
			bout.close();
			value = bout.toString();
			System.out.println("Test conversione via freemarker: \n"+value);
			expected = EXPECTED_XML_3;
			if(!expected.equals(value)) {
				throw new Exception("Expected value '"+expected+"', found '"+value+"'");
			}
		}
	}
	
}
