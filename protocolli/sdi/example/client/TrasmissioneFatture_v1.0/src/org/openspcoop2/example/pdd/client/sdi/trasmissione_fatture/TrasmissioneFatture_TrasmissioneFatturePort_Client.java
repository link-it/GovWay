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

package org.openspcoop2.example.pdd.client.sdi.trasmissione_fatture;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.openspcoop2.utils.resources.FileSystemUtilities;

/**
 * This class was generated by Apache CXF 2.7.4
 * 2014-10-10T14:24:52.550+02:00
 * Generated source version: 2.7.4
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public final class TrasmissioneFatture_TrasmissioneFatturePort_Client {

    private static final QName SERVICE_NAME = new QName("http://www.fatturapa.gov.it/sdi/ws/trasmissione/v1.0", "TrasmissioneFatture_service");

    private TrasmissioneFatture_TrasmissioneFatturePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = TrasmissioneFattureService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        
        
        /*
    	 * Leggo la configurazione
    	 */
    	
    	java.util.Properties reader = new java.util.Properties();
		try{  
			reader.load(new FileInputStream("Client.properties")); 
		}catch(java.io.IOException e) {
			System.err.println("ERROR : "+e.toString());
			return;
		}
		
		
		String tipoOperazione = reader.getProperty("tipoOperazione");
		if(tipoOperazione==null){
			throw new Exception("Property [tipoOperazione] not definded");
		}
		else{
			tipoOperazione = tipoOperazione.trim();
		}
		
		String pdd = reader.getProperty("pdd");
		if(pdd==null){
			throw new Exception("Property [pdd] not definded");
		}
		else{
			pdd = pdd.trim();
			if(pdd.endsWith("/")==false){
				pdd = pdd + "/";
			}
		}
		
		String pa = reader.getProperty("pa");
		if(pa==null){
			throw new Exception("Property [pa] not definded");
		}
		else{
			pa = pa.trim();
			if(pa.endsWith("/")==false){
				pa = pa + "/";
			}
		}
		
		String username = reader.getProperty("username");
		if(username!=null){
			username = username.trim();
		}
		String password = reader.getProperty("password");
		if(password!=null){
			password = password.trim();
		}
		
		String mtom = reader.getProperty("mtom");
		boolean isMTOMEnabled = false;
		if(mtom==null){
			throw new Exception("Property [mtom] not definded");
		}
		else{
			mtom = mtom.trim();
			isMTOMEnabled = Boolean.parseBoolean(mtom);
		}
        
		String identificativoSDI = reader.getProperty("identificativoSDI");
		if(identificativoSDI==null){
			throw new Exception("Property [identificativoSDI] not definded");
		}
		else{
			identificativoSDI = identificativoSDI.trim();
		}
		
    	String file = reader.getProperty(tipoOperazione);
		if(file==null){
			throw new Exception("Property ["+tipoOperazione+"] not definded");
		}
		else{
			file = file.trim();
		}
      
		String url = pdd+pa;
		//url = url + tipoOperazione;
		System.out.println("URL: ["+url+"]");
		
		
		String bustaP = reader.getProperty("busta."+tipoOperazione);
		EGOVHeader egovHeader = null;
		if(bustaP!=null){
			java.text.SimpleDateFormat dateformat = new java.text.SimpleDateFormat ("yyyy-MM-dd");
			java.text.SimpleDateFormat timeformat = new java.text.SimpleDateFormat ("HH:mm:ss.SSS");
			java.text.SimpleDateFormat dateidformat = new java.text.SimpleDateFormat ("yyyy-MM-dd");
			java.text.SimpleDateFormat timeidformat = new java.text.SimpleDateFormat ("HH:mm");
			String data = FileSystemUtilities.readFile(new File(bustaP));
			data = data.replaceAll("@ID-DATE@", dateidformat.format(new java.util.Date()));
			data = data.replaceAll("@ID-TIME@", timeidformat.format(new java.util.Date()));
			data = data.replaceAll("@DATE@", dateformat.format(new java.util.Date()));
			data = data.replaceAll("@TIME@", timeformat.format(new java.util.Date()));
			byte[]busta = data.getBytes();
			egovHeader = new EGOVHeader(busta);
		}
		
        TrasmissioneFattureService ss = new TrasmissioneFattureService(wsdlURL, TrasmissioneFatture_TrasmissioneFatturePort_Client.SERVICE_NAME);
        if(egovHeader!=null){
			ss.setHandlerResolver(new org.openspcoop2.example.pdd.client.sdi.trasmissione_fatture.HandlerResolver(egovHeader));
		}
        TrasmissioneFatture port = ss.getTrasmissioneFatturePort();  
        ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        ((javax.xml.ws.soap.SOAPBinding)((BindingProvider)port).getBinding()).setMTOMEnabled(isMTOMEnabled);
        if(username!=null && password!=null){
            ((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,  username);
        	((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,  password);
        }
        
        org.openspcoop2.example.pdd.client.sdi.trasmissione_fatture.FileSdIType fileSdi = new FileSdIType();
        fileSdi.setIdentificativoSdI(new BigInteger(identificativoSDI));
        fileSdi.setNomeFile((new File(file).getName()));
        fileSdi.setFile(FileSystemUtilities.readBytesFromFile(file));
        
        
        if("RicevutaConsegna".equals(tipoOperazione)){
        	port.ricevutaConsegna(fileSdi);
        }
        else if("NotificaMancataConsegna".equals(tipoOperazione)){
        	port.notificaMancataConsegna(fileSdi);
        }
		else if("NotificaScarto".equals(tipoOperazione)){
		     port.notificaScarto(fileSdi);
		}
		else if("NotificaEsito".equals(tipoOperazione)){
			port.notificaEsito(fileSdi);
		}
		else if("NotificaDecorrenzaTermini".equals(tipoOperazione)){
			port.notificaDecorrenzaTermini(fileSdi);
		}
		else if("AttestazioneTrasmissioneFattura".equals(tipoOperazione)){
			port.attestazioneTrasmissioneFattura(fileSdi);
		}
        
        
       

        System.exit(0);
    }

}
