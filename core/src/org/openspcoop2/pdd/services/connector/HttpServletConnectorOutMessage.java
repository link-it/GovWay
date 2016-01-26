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
package org.openspcoop2.pdd.services.connector;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.protocol.engine.constants.IDService;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.utils.resources.Charset;
import org.openspcoop2.utils.resources.RFC2047Encoding;
import org.openspcoop2.utils.resources.RFC2047Utilities;

/**
 * HttpServletConnectorOutMessage
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class HttpServletConnectorOutMessage implements ConnectorOutMessage {

	protected HttpServletResponse res;
	protected OutputStream out;
	protected IProtocolFactory protocolFactory;
	protected String idModulo;
	protected IDService idModuloAsIDService;
	protected OpenSPCoop2Properties openspcoopProperties;
	
	
	public HttpServletConnectorOutMessage(IProtocolFactory protocolFactory, HttpServletResponse res,
			IDService idModuloAsIDService, String idModulo) throws ConnectorException{
		try{
			this.res = res;
			this.out = this.res.getOutputStream();
			this.protocolFactory = protocolFactory;
			this.idModuloAsIDService = idModuloAsIDService;
			this.idModulo = idModulo;
			this.openspcoopProperties =  OpenSPCoop2Properties.getInstance();
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}

	@Override
	public void sendResponse(OpenSPCoop2Message msg, boolean consume) throws ConnectorException {
		try{
			// il save e' necessario con i connettori directVM in caso di errori di validazione
			if(msg!=null && msg.getSOAPBody()!=null && msg.getSOAPBody().hasFault()){
				msg.saveChanges();
			}
			msg.writeTo(this.out,consume);		
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	
	@Override
	public void sendResponse(byte[] message) throws ConnectorException{
		try{
			this.out.write(message);	
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	
	@Override
	public void setHeader(String key,String value) throws ConnectorException{
		try{		
			boolean encodingRFC2047 = false;
			Charset charsetRFC2047 = null;
			RFC2047Encoding encodingAlgorithmRFC2047 = null;
			if(this.idModuloAsIDService!=null){
				switch (this.idModuloAsIDService) {
				case PORTA_DELEGATA_SOAP:
				case PORTA_DELEGATA_INTEGRATION_MANAGER:
				case PORTA_DELEGATA_XML_TO_SOAP:
				case PORTA_DELEGATA_API:
					encodingRFC2047 = this.openspcoopProperties.isEnabledEncodingRFC2047HeaderValue_ricezioneContenutiApplicativi();
					charsetRFC2047 = this.openspcoopProperties.getCharsetEncodingRFC2047HeaderValue_ricezioneContenutiApplicativi();
					encodingAlgorithmRFC2047 = this.openspcoopProperties.getEncodingRFC2047HeaderValue_ricezioneContenutiApplicativi();
					break;
				case PORTA_APPLICATIVA_SOAP:
				case PORTA_APPLICATIVA_API:
					encodingRFC2047 = this.openspcoopProperties.isEnabledEncodingRFC2047HeaderValue_ricezioneBuste();
					charsetRFC2047 = this.openspcoopProperties.getCharsetEncodingRFC2047HeaderValue_ricezioneBuste();
					encodingAlgorithmRFC2047 = this.openspcoopProperties.getEncodingRFC2047HeaderValue_ricezioneBuste();
					break;
				default:
					break;
				}
			}
			
			if(encodingRFC2047){
				if(RFC2047Utilities.isAllCharactersInCharset(value, charsetRFC2047)==false){
					String encoded = RFC2047Utilities.encode(new String(value), charsetRFC2047, encodingAlgorithmRFC2047);
					//System.out.println("@@@@ RESPONSE CODIFICA ["+value+"] in ["+encoded+"]");
					this.res.setHeader(key,encoded);
				}
				else{
					this.res.setHeader(key,value);
				}
			}
			else{
				this.res.setHeader(key,value);
			}	
			
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	
	@Override
	public void setContentLength(int length) throws ConnectorException{
		try{
			this.res.setContentLength(length);
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	
	@Override
	public void setContentType(String type) throws ConnectorException{
		try{
			this.res.setContentType(type);
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	
	private int status = -1;
	@Override
	public void setStatus(int status) throws ConnectorException{
		try{
			this.res.setStatus(status);
			this.status = status;
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}
	}
	@Override
	public int getResponseStatus() throws ConnectorException{
		return this.status;
	}
	
	@Override
	public void flush(boolean throwException) throws ConnectorException{
		try{
			// Flush and close response
			// NOTA: per poter ottenere l'errore di BrokenPipe sempre, deve essere disabilitato il socketBufferOutput sul servlet container.
			// Se non lo si disabilta, l'errore viene ritornato solo se il messaggio supera la dimensione del buffer (default: 8192K)
			// Ad esempio in tomcat utilizzare (socketBuffer="-1"): 
			//    <Connector protocol="HTTP/1.1" port="8080" address="${jboss.bind.address}" 
            //       connectionTimeout="20000" redirectPort="8443" socketBuffer="-1" />
			if(this.res!=null){
				try{
					this.res.flushBuffer();
				}catch(Exception e){
					if(throwException){
						throw e;
					}
				}
			}
			if(this.out!=null){
				try{
					this.out.flush();
				}catch(Exception e){
					if(throwException){
						throw e;
					}
				}
			}
		}catch(ConnectorException e){
			throw e;
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}	
	}
	
	@Override
	public void close(boolean throwException) throws ConnectorException{
		try{
			if(this.out!=null){
				try{
					this.out.close();
					this.out = null;
				}catch(Exception e){
					if(throwException){
						throw e;
					}
				}
			}
		}catch(ConnectorException e){
			throw e;
		}catch(Exception e){
			throw new ConnectorException(e.getMessage(),e);
		}	
	}
}
