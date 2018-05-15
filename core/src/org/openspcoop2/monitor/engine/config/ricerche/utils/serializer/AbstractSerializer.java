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
package org.openspcoop2.monitor.engine.config.ricerche.utils.serializer;

import org.openspcoop2.generic_project.exception.SerializerException;
import org.openspcoop2.utils.beans.WriteToSerializerType;
import org.openspcoop2.utils.xml.JaxbUtils;

import org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin;
import org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione;
import org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio;
import org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio;
import org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione;
import org.openspcoop2.monitor.engine.config.ricerche.IdPlugin;
import org.openspcoop2.monitor.engine.config.ricerche.Plugin;
import org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca;
import org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.File;
import java.lang.reflect.Method;

import javax.xml.bind.JAXBElement;

/**     
 * XML Serializer of beans
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public abstract class AbstractSerializer {


	protected abstract WriteToSerializerType getType();
	
	protected void _objToXml(OutputStream out, Class<?> c, Object object,
			boolean prettyPrint) throws Exception {
		if(object instanceof JAXBElement){
			// solo per il tipo WriteToSerializerType.JAXB
			JaxbUtils.objToXml(out, c, object, prettyPrint);
		}else{
			Method m = c.getMethod("writeTo", OutputStream.class, WriteToSerializerType.class);
			m.invoke(object, out, this.getType());
		}
	}
	
	protected void objToXml(OutputStream out,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		try{
			this._objToXml(out, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				out.flush();
			}catch(Exception e){}
		}
	}
	protected void objToXml(String fileName,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		try{
			this.objToXml(new File(fileName), c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
	}
	protected void objToXml(File file,Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		FileOutputStream fout = null;
		try{
			fout = new FileOutputStream(file);
			this._objToXml(fout, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				fout.flush();
			}catch(Exception e){}
			try{
				fout.close();
			}catch(Exception e){}
		}
	}
	protected ByteArrayOutputStream objToXml(Class<?> c,Object object,boolean prettyPrint) throws SerializerException{
		ByteArrayOutputStream bout = null;
		try{
			bout = new ByteArrayOutputStream();
			this._objToXml(bout, c, object, prettyPrint);
		}catch(Exception e){
			throw new SerializerException(e.getMessage(), e);
		}
		finally{
			try{
				bout.flush();
			}catch(Exception e){}
			try{
				bout.close();
			}catch(Exception e){}
		}
		return bout;
	}




	/*
	 =================================================================================
	 Object: info-plugin
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,InfoPlugin infoPlugin) throws SerializerException {
		this.objToXml(fileName, InfoPlugin.class, infoPlugin, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,InfoPlugin infoPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, InfoPlugin.class, infoPlugin, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param file Xml file to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,InfoPlugin infoPlugin) throws SerializerException {
		this.objToXml(file, InfoPlugin.class, infoPlugin, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param file Xml file to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,InfoPlugin infoPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, InfoPlugin.class, infoPlugin, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param out OutputStream to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,InfoPlugin infoPlugin) throws SerializerException {
		this.objToXml(out, InfoPlugin.class, infoPlugin, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param out OutputStream to serialize the object <var>infoPlugin</var>
	 * @param infoPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,InfoPlugin infoPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, InfoPlugin.class, infoPlugin, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param infoPlugin Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(InfoPlugin infoPlugin) throws SerializerException {
		return this.objToXml(InfoPlugin.class, infoPlugin, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param infoPlugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(InfoPlugin infoPlugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(InfoPlugin.class, infoPlugin, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param infoPlugin Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(InfoPlugin infoPlugin) throws SerializerException {
		return this.objToXml(InfoPlugin.class, infoPlugin, false).toString();
	}
	/**
	 * Serialize to String the object <var>infoPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.InfoPlugin}
	 * 
	 * @param infoPlugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(InfoPlugin infoPlugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(InfoPlugin.class, infoPlugin, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: configurazione-servizio-azione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneServizioAzione configurazioneServizioAzione) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneServizioAzione configurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneServizioAzione configurazioneServizioAzione) throws SerializerException {
		this.objToXml(file, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneServizioAzione configurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneServizioAzione configurazioneServizioAzione) throws SerializerException {
		this.objToXml(out, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneServizioAzione</var>
	 * @param configurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneServizioAzione configurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, ConfigurazioneServizioAzione.class, configurazioneServizioAzione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param configurazioneServizioAzione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneServizioAzione configurazioneServizioAzione) throws SerializerException {
		return this.objToXml(ConfigurazioneServizioAzione.class, configurazioneServizioAzione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param configurazioneServizioAzione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneServizioAzione configurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneServizioAzione.class, configurazioneServizioAzione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param configurazioneServizioAzione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneServizioAzione configurazioneServizioAzione) throws SerializerException {
		return this.objToXml(ConfigurazioneServizioAzione.class, configurazioneServizioAzione, false).toString();
	}
	/**
	 * Serialize to String the object <var>configurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizioAzione}
	 * 
	 * @param configurazioneServizioAzione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneServizioAzione configurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneServizioAzione.class, configurazioneServizioAzione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: configurazione-servizio
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneServizio configurazioneServizio) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneServizio.class, configurazioneServizio, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneServizio configurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneServizio.class, configurazioneServizio, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneServizio configurazioneServizio) throws SerializerException {
		this.objToXml(file, ConfigurazioneServizio.class, configurazioneServizio, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneServizio configurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, ConfigurazioneServizio.class, configurazioneServizio, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneServizio configurazioneServizio) throws SerializerException {
		this.objToXml(out, ConfigurazioneServizio.class, configurazioneServizio, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneServizio</var>
	 * @param configurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneServizio configurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, ConfigurazioneServizio.class, configurazioneServizio, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param configurazioneServizio Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneServizio configurazioneServizio) throws SerializerException {
		return this.objToXml(ConfigurazioneServizio.class, configurazioneServizio, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param configurazioneServizio Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneServizio configurazioneServizio,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneServizio.class, configurazioneServizio, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param configurazioneServizio Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneServizio configurazioneServizio) throws SerializerException {
		return this.objToXml(ConfigurazioneServizio.class, configurazioneServizio, false).toString();
	}
	/**
	 * Serialize to String the object <var>configurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneServizio}
	 * 
	 * @param configurazioneServizio Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneServizio configurazioneServizio,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneServizio.class, configurazioneServizio, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-configurazione-servizio
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneServizio idConfigurazioneServizio) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneServizio.class, idConfigurazioneServizio, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneServizio idConfigurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneServizio.class, idConfigurazioneServizio, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneServizio idConfigurazioneServizio) throws SerializerException {
		this.objToXml(file, IdConfigurazioneServizio.class, idConfigurazioneServizio, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneServizio idConfigurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdConfigurazioneServizio.class, idConfigurazioneServizio, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneServizio idConfigurazioneServizio) throws SerializerException {
		this.objToXml(out, IdConfigurazioneServizio.class, idConfigurazioneServizio, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneServizio</var>
	 * @param idConfigurazioneServizio Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneServizio idConfigurazioneServizio,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdConfigurazioneServizio.class, idConfigurazioneServizio, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param idConfigurazioneServizio Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneServizio idConfigurazioneServizio) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizio.class, idConfigurazioneServizio, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param idConfigurazioneServizio Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneServizio idConfigurazioneServizio,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizio.class, idConfigurazioneServizio, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param idConfigurazioneServizio Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneServizio idConfigurazioneServizio) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizio.class, idConfigurazioneServizio, false).toString();
	}
	/**
	 * Serialize to String the object <var>idConfigurazioneServizio</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizio}
	 * 
	 * @param idConfigurazioneServizio Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneServizio idConfigurazioneServizio,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizio.class, idConfigurazioneServizio, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-configurazione-servizio-azione
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione) throws SerializerException {
		this.objToXml(file, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione) throws SerializerException {
		this.objToXml(out, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneServizioAzione</var>
	 * @param idConfigurazioneServizioAzione Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneServizioAzione idConfigurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param idConfigurazioneServizioAzione Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneServizioAzione idConfigurazioneServizioAzione) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param idConfigurazioneServizioAzione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneServizioAzione idConfigurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param idConfigurazioneServizioAzione Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneServizioAzione idConfigurazioneServizioAzione) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, false).toString();
	}
	/**
	 * Serialize to String the object <var>idConfigurazioneServizioAzione</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneServizioAzione}
	 * 
	 * @param idConfigurazioneServizioAzione Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneServizioAzione idConfigurazioneServizioAzione,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneServizioAzione.class, idConfigurazioneServizioAzione, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-plugin
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdPlugin idPlugin) throws SerializerException {
		this.objToXml(fileName, IdPlugin.class, idPlugin, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdPlugin idPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdPlugin.class, idPlugin, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param file Xml file to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdPlugin idPlugin) throws SerializerException {
		this.objToXml(file, IdPlugin.class, idPlugin, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param file Xml file to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdPlugin idPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdPlugin.class, idPlugin, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param out OutputStream to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdPlugin idPlugin) throws SerializerException {
		this.objToXml(out, IdPlugin.class, idPlugin, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param out OutputStream to serialize the object <var>idPlugin</var>
	 * @param idPlugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdPlugin idPlugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdPlugin.class, idPlugin, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param idPlugin Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdPlugin idPlugin) throws SerializerException {
		return this.objToXml(IdPlugin.class, idPlugin, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param idPlugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdPlugin idPlugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdPlugin.class, idPlugin, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param idPlugin Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdPlugin idPlugin) throws SerializerException {
		return this.objToXml(IdPlugin.class, idPlugin, false).toString();
	}
	/**
	 * Serialize to String the object <var>idPlugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdPlugin}
	 * 
	 * @param idPlugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdPlugin idPlugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdPlugin.class, idPlugin, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: plugin
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Plugin plugin) throws SerializerException {
		this.objToXml(fileName, Plugin.class, plugin, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param fileName Xml file to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,Plugin plugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, Plugin.class, plugin, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param file Xml file to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Plugin plugin) throws SerializerException {
		this.objToXml(file, Plugin.class, plugin, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param file Xml file to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,Plugin plugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, Plugin.class, plugin, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param out OutputStream to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Plugin plugin) throws SerializerException {
		this.objToXml(out, Plugin.class, plugin, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param out OutputStream to serialize the object <var>plugin</var>
	 * @param plugin Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,Plugin plugin,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, Plugin.class, plugin, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param plugin Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Plugin plugin) throws SerializerException {
		return this.objToXml(Plugin.class, plugin, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param plugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(Plugin plugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Plugin.class, plugin, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param plugin Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Plugin plugin) throws SerializerException {
		return this.objToXml(Plugin.class, plugin, false).toString();
	}
	/**
	 * Serialize to String the object <var>plugin</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.Plugin}
	 * 
	 * @param plugin Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(Plugin plugin,boolean prettyPrint) throws SerializerException {
		return this.objToXml(Plugin.class, plugin, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: configurazione-ricerca
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneRicerca configurazioneRicerca) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneRicerca.class, configurazioneRicerca, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param fileName Xml file to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,ConfigurazioneRicerca configurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, ConfigurazioneRicerca.class, configurazioneRicerca, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneRicerca configurazioneRicerca) throws SerializerException {
		this.objToXml(file, ConfigurazioneRicerca.class, configurazioneRicerca, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param file Xml file to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,ConfigurazioneRicerca configurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, ConfigurazioneRicerca.class, configurazioneRicerca, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneRicerca configurazioneRicerca) throws SerializerException {
		this.objToXml(out, ConfigurazioneRicerca.class, configurazioneRicerca, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param out OutputStream to serialize the object <var>configurazioneRicerca</var>
	 * @param configurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,ConfigurazioneRicerca configurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, ConfigurazioneRicerca.class, configurazioneRicerca, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param configurazioneRicerca Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneRicerca configurazioneRicerca) throws SerializerException {
		return this.objToXml(ConfigurazioneRicerca.class, configurazioneRicerca, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param configurazioneRicerca Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(ConfigurazioneRicerca configurazioneRicerca,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneRicerca.class, configurazioneRicerca, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param configurazioneRicerca Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneRicerca configurazioneRicerca) throws SerializerException {
		return this.objToXml(ConfigurazioneRicerca.class, configurazioneRicerca, false).toString();
	}
	/**
	 * Serialize to String the object <var>configurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.ConfigurazioneRicerca}
	 * 
	 * @param configurazioneRicerca Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(ConfigurazioneRicerca configurazioneRicerca,boolean prettyPrint) throws SerializerException {
		return this.objToXml(ConfigurazioneRicerca.class, configurazioneRicerca, prettyPrint).toString();
	}
	
	
	
	/*
	 =================================================================================
	 Object: id-configurazione-ricerca
	 =================================================================================
	*/
	
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneRicerca idConfigurazioneRicerca) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, false);
	}
	/**
	 * Serialize to file system in <var>fileName</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param fileName Xml file to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(String fileName,IdConfigurazioneRicerca idConfigurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(fileName, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, prettyPrint);
	}
	
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneRicerca idConfigurazioneRicerca) throws SerializerException {
		this.objToXml(file, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, false);
	}
	/**
	 * Serialize to file system in <var>file</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param file Xml file to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(File file,IdConfigurazioneRicerca idConfigurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(file, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, prettyPrint);
	}
	
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneRicerca idConfigurazioneRicerca) throws SerializerException {
		this.objToXml(out, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, false);
	}
	/**
	 * Serialize to output stream <var>out</var> the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param out OutputStream to serialize the object <var>idConfigurazioneRicerca</var>
	 * @param idConfigurazioneRicerca Object to be serialized in xml file <var>fileName</var>
	 * @param prettyPrint if true output the XML with indenting
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public void write(OutputStream out,IdConfigurazioneRicerca idConfigurazioneRicerca,boolean prettyPrint) throws SerializerException {
		this.objToXml(out, IdConfigurazioneRicerca.class, idConfigurazioneRicerca, prettyPrint);
	}
			
	/**
	 * Serialize to byte array the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param idConfigurazioneRicerca Object to be serialized
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneRicerca idConfigurazioneRicerca) throws SerializerException {
		return this.objToXml(IdConfigurazioneRicerca.class, idConfigurazioneRicerca, false).toByteArray();
	}
	/**
	 * Serialize to byte array the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param idConfigurazioneRicerca Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized in byte array
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public byte[] toByteArray(IdConfigurazioneRicerca idConfigurazioneRicerca,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneRicerca.class, idConfigurazioneRicerca, prettyPrint).toByteArray();
	}
	
	/**
	 * Serialize to String the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param idConfigurazioneRicerca Object to be serialized
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneRicerca idConfigurazioneRicerca) throws SerializerException {
		return this.objToXml(IdConfigurazioneRicerca.class, idConfigurazioneRicerca, false).toString();
	}
	/**
	 * Serialize to String the object <var>idConfigurazioneRicerca</var> of type {@link org.openspcoop2.monitor.engine.config.ricerche.IdConfigurazioneRicerca}
	 * 
	 * @param idConfigurazioneRicerca Object to be serialized
	 * @param prettyPrint if true output the XML with indenting
	 * @return Object to be serialized as String
	 * @throws SerializerException The exception that is thrown when an error occurs during serialization
	 */
	public String toString(IdConfigurazioneRicerca idConfigurazioneRicerca,boolean prettyPrint) throws SerializerException {
		return this.objToXml(IdConfigurazioneRicerca.class, idConfigurazioneRicerca, prettyPrint).toString();
	}
	
	
	

}
