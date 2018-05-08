/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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

package org.openspcoop2.utils.json;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.openspcoop2.utils.UtilsException;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

/**	
 * PathExpressionEngine
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author: apoli $
 * @version $Rev: 13574 $, $Date: 2018-01-26 12:24:34 +0100(ven, 26 gen 2018) $
 */
public class JsonPathExpressionEngine {

	public static String getAsString(JsonNode element) {
		try{
			return getJsonUtils().toString(element);
		}catch(Exception e){
			return null;
		}
	}

	private static JSONUtils jsonUtils;
	public static synchronized JSONUtils getJsonUtils() throws UtilsException {
		if(jsonUtils == null) jsonUtils = new JSONUtils();
		return jsonUtils;
	}
	
	
	public static JSONObject getJSONObject(InputStream is) throws JsonPathException {
		if(is == null)
			throw new JsonPathException("Document (InputStream) is null");

		try {
			return getJSONParser().parse(is, JSONObject.class);
		} catch (UnsupportedEncodingException e) {
			throw new JsonPathException(e.getMessage(), e);
		} catch (ParseException e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}

	public static JSONObject getJSONObject(String contenuto) throws JsonPathException {
		if(contenuto == null)
			throw new JsonPathException("Document (String) is null");

		try {
			return getJSONParser().parse(contenuto, JSONObject.class);
		} catch (ParseException e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}

	public static JSONObject getJSONObject(JsonNode document) throws JsonPathException {
		if(document == null)
			throw new JsonPathException("Document (JsonNode) is null");

		try {
			return getJSONParser().parse(getAsString(document), JSONObject.class);
		} catch (ParseException e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}


	private static JSONParser jsonParser;
	public static synchronized JSONParser getJSONParser() {
		if(jsonParser == null) {
			jsonParser = new JSONParser(JSONParser.MODE_PERMISSIVE); 
		}
		return jsonParser; 
	}

	/* ---------- METODI RITORNANO STRINGHE -------------- */
	
	public List<String> getStringMatchPattern(JSONObject input, String pattern) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JSONObject) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(input, pattern);
	}

	public List<String> getStringMatchPattern(JsonNode document, String pattern) throws JsonPathException{
		
		if(document == null)
				throw new JsonPathException("Document (JsonNode) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(getAsString(document), pattern);
	}
	
	public List<String> getStringMatchPattern(InputStream is, String pattern) throws JsonPathException{
		if(is == null)
			throw new JsonPathException("Document (InputStream) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			return JsonPath.read(is, pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public List<String> getStringMatchPattern(String contenuto, String pattern) throws JsonPathException{
		if(contenuto == null)
			throw new JsonPathException("Document (String) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			return JsonPath.read(contenuto, pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	/* ---------- METODI RITORNANO NUMBER -------------- */

	public List<Number> getNumberMatchPattern(JSONObject input, String pattern) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JSONObject) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(input, pattern);
	}

	public List<Number> getNumberMatchPattern(JsonNode document, String pattern) throws JsonPathException{
		if(document == null)
			throw new JsonPathException("Document (JsonNode) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			return JsonPath.read(getAsString(document), pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public List<Number> getNumberMatchPattern(InputStream is, String pattern) throws JsonPathException{
		if(is == null)
			throw new JsonPathException("Document (InputStream) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");


		try {
			return JsonPath.read(is, pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public List<Number> getNumberMatchPattern(String contenuto, String pattern) throws JsonPathException{
		if(contenuto == null)
			throw new JsonPathException("Document (String) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(contenuto, pattern);
	}

	/* ---------- METODI RITORNANO BOOLEAN -------------- */

	public List<Boolean> getBooleanMatchPattern(JSONObject input, String pattern) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JSONObject) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(input, pattern);
	}

	public List<Boolean> getBooleanMatchPattern(JsonNode document, String pattern) throws JsonPathException{
		if(document == null)
			throw new JsonPathException("Document (JsonNode) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(getAsString(document), pattern);
	}
	
	public List<Boolean> getBooleanMatchPattern(InputStream is, String pattern) throws JsonPathException{
		if(is == null)
			throw new JsonPathException("Document (InputStream) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			return JsonPath.read(is, pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public List<Boolean> getBooleanMatchPattern(String contenuto, String pattern) throws JsonPathException{
		if(contenuto == null)
			throw new JsonPathException("Document (String) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		return JsonPath.read(contenuto, pattern);
	}

	/* ---------- METODI RITORNANO JSON NODE -------------- */

	public JsonNode getJsonNodeMatchPattern(JSONObject input, String pattern) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JSONObject) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			JSONArray jsonObject = JsonPath.read(input, pattern);
			
			return getJsonUtils().getAsNode(jsonObject.toString());
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}

	public JsonNode getJsonNodeMatchPattern(JsonNode document, String pattern) throws JsonPathException{
		if(document == null)
			throw new JsonPathException("Document (JsonNode) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");
		try {
			String inputString = getAsString(document);
			JSONArray jsonObject = JsonPath.read(inputString, pattern);
			
			return getJsonUtils().getAsNode(jsonObject.toString());
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public JsonNode getJsonNodeMatchPattern(InputStream is, String pattern) throws JsonPathException{
		if(is == null)
			throw new JsonPathException("Document (InputStream) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");
		
		try {
			JSONArray jsonObject = JsonPath.read(is, pattern);
			return getJsonUtils().getAsNode(jsonObject.toString());
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}
	}
	
	public JsonNode getJsonNodeMatchPattern(String contenuto, String pattern) throws JsonPathException{
		if(contenuto == null)
			throw new JsonPathException("Document (String) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		try {
			JSONArray jsonObject = JsonPath.read(contenuto, pattern);
			return getJsonUtils().getAsNode(jsonObject.toString());
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}

	}
	

	/* ---------- METODI RITORNANO OGGETTI -------------- */

	public Object getMatchPattern(JSONObject input, String pattern, JsonPathReturnType returnType) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JSONObject)is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		if(returnType == null)
			throw new JsonPathException("JsonPathReturnType is null");

		switch(returnType) {
		case STRING: return getStringMatchPattern(input, pattern);
		case NUMBER: return getNumberMatchPattern(input, pattern);
		case BOOLEAN: return getBooleanMatchPattern(input, pattern);
		case NODE: return getJsonNodeMatchPattern(input, pattern);
		default:
			break;}
		return JsonPath.read(input, pattern);
	}
	

	public Object getMatchPattern(JsonNode input, String pattern, JsonPathReturnType returnType) throws JsonPathException {
		if(input == null)
			throw new JsonPathException("Document (JsonNode)is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		if(returnType == null)
			throw new JsonPathException("JsonPathReturnType is null");

		String inputString = getAsString(input);
		switch(returnType) {
		case STRING: return getStringMatchPattern(inputString, pattern);
		case NUMBER: return getNumberMatchPattern(inputString, pattern);
		case BOOLEAN: return getBooleanMatchPattern(inputString, pattern);
		case NODE: return getJsonNodeMatchPattern(inputString, pattern);
		default:
			break;}
		return JsonPath.read(input, pattern);
	}
	
	public Object getMatchPattern(InputStream input, String pattern, JsonPathReturnType returnType)throws JsonPathException{
		if(input == null)
			throw new JsonPathException("Document (InputStream) is null");

		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		if(returnType == null)
			throw new JsonPathException("JsonPathReturnType is null");

		switch(returnType) {
		case STRING: return getStringMatchPattern(input, pattern);
		case NUMBER: return getNumberMatchPattern(input, pattern);
		case BOOLEAN: return getBooleanMatchPattern(input, pattern);
		case NODE: return getJsonNodeMatchPattern(input, pattern);
		default:
			break;}
		
		try {
			return JsonPath.read(input, pattern);
		} catch(Exception e) {
			throw new JsonPathException(e.getMessage(), e);
		}

	}
	
	public Object getMatchPattern(String input, String pattern, JsonPathReturnType returnType) throws JsonPathException{
		if(input == null)
			throw new JsonPathException("Document (String) is null");
		
		if(pattern == null)
			throw new JsonPathException("Pattern is null");

		if(returnType == null)
			throw new JsonPathException("JsonPathReturnType is null");

		switch(returnType) {
		case STRING: return getStringMatchPattern(input, pattern);
		case NUMBER: return getNumberMatchPattern(input, pattern);
		case BOOLEAN: return getBooleanMatchPattern(input, pattern);
		case NODE: return getJsonNodeMatchPattern(input, pattern);
		default:
			break;}
		return JsonPath.read(input, pattern);
	}
	
	
	/* ---------- VALIDATORE -------------- */
	
	public void validate(String path) throws JsonPathNotValidException{
		try {
			JsonPath.compile(path);
		} catch(Exception e) {
			throw new JsonPathNotValidException(e.getMessage());
		}
	}
	
}
