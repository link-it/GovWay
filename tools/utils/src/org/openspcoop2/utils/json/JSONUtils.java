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

package org.openspcoop2.utils.json;

import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**	
 * JSONUtils
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class JSONUtils extends AbstractUtils {
	
	private static JSONUtils jsonUtils = null;
	private static JSONUtils jsonUtilsPretty = null;
	private static synchronized void init(boolean prettyPrint){
		if(prettyPrint) {
			if(JSONUtils.jsonUtilsPretty==null){
				JSONUtils.jsonUtilsPretty = new JSONUtils(true);
			}
		}
		else {
			if(JSONUtils.jsonUtils==null){
				JSONUtils.jsonUtils = new JSONUtils(false);
			}
		}
	}
	public static JSONUtils getInstance(){
		return getInstance(false);
	}
	public static JSONUtils getInstance(boolean prettyPrint){
		if(prettyPrint) {
			if(JSONUtils.jsonUtilsPretty==null){
				JSONUtils.init(true);
			}
			return JSONUtils.jsonUtilsPretty;
		}
		else {
			if(JSONUtils.jsonUtils==null){
				JSONUtils.init(false);
			}
			return JSONUtils.jsonUtils;
		}
	}
	

	private static Boolean mapperSynchronized = true;
	private static ObjectMapper _mapper;
	private static void initMapper()  {
		synchronized(mapperSynchronized){
			if(_mapper==null){
				_mapper = new ObjectMapper();
				_mapper.setTimeZone(TimeZone.getDefault());
				_mapper.setSerializationInclusion(Include.NON_NULL);
				_mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
				_mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
					    WRITE_DATES_AS_TIMESTAMPS , false);
			}
		}
	}
	public static void setMapperTimeZone(TimeZone timeZone) {
		if(_mapper==null){
			initMapper();
		}
		synchronized(mapperSynchronized){
			_mapper.setTimeZone(timeZone);
		}
	}
	public static void registerJodaModule() {
		if(_mapper==null){
			initMapper();
		}
		synchronized(mapperSynchronized){
			_mapper.registerModule(new JodaModule());
		}
	}
	public static void registerJavaTimeModule() {
		if(_mapper==null){
			initMapper();
		}
		synchronized(mapperSynchronized){
			_mapper.registerModule(new JavaTimeModule());
		}
	}
	
	public static ObjectMapper getObjectMapper() {
		if(_mapper==null){
			initMapper();
		}
		return _mapper;
	}
	
	private static ObjectWriter writer;
	private synchronized static void initWriter()  {
		if(_mapper==null){
			initMapper();
		}
		if(writer==null){
			writer = _mapper.writer();
		}
	}
	public static ObjectWriter getObjectWriter() {
		if(writer==null){
			initWriter();
		}
		return writer;
	}
	
	private static ObjectWriter writerPrettyPrint;
	private synchronized static void initWriterPrettyPrint()  {
		if(_mapper==null){
			initMapper();
		}
		if(writerPrettyPrint==null){
			writerPrettyPrint = _mapper.writer().withDefaultPrettyPrinter();
		}
	}
	public static ObjectWriter getObjectWriterPrettyPrint() {
		if(writerPrettyPrint==null){
			initWriterPrettyPrint();
		}
		return writerPrettyPrint;
	}
	
	
	
	protected JSONUtils(boolean prettyPrint) {
		super(prettyPrint);
	}
	
	@Override
	protected void _initMapper() {
		initMapper();
	}
	@Override
	protected void _initWriter(boolean prettyPrint) {
		if(prettyPrint) { 
			initWriterPrettyPrint();
		}
		else {
			initWriter();
		}
	}
	
	@Override
	protected ObjectMapper _getObjectMapper() {
		return getObjectMapper();
	}
	@Override
	protected ObjectWriter _getObjectWriter(boolean prettyPrint) {
		if(prettyPrint) { 
			return getObjectWriterPrettyPrint();
		}
		else {
			return getObjectWriter();
		}
	}
	
	
	// IS
	
	public boolean isJson(byte[]jsonBytes){
		return this.isValid(jsonBytes);
	}
	
	public boolean isJson(String jsonString){
		return this.isValid(jsonString);
	}
	
}
