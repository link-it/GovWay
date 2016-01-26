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


package org.openspcoop2.web.ctrlstat.servlet.config;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openspcoop2.pdd.core.jmx.InformazioniStatoPorta;
import org.openspcoop2.pdd.core.jmx.InformazioniStatoPortaCache;
import org.openspcoop2.utils.resources.MimeTypes;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.servlet.archivi.ArchiviCostanti;

/**
 * Questa servlet si occupa di esportare le tracce in formato xml zippandole
 * 
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Sandra Giangrandi (sandra@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class ConfigurazioneSistemaExporter extends HttpServlet {

	private static final long serialVersionUID = -7341279067126334095L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.processRequest(req, resp);
	}

	/**
	 * Processa la richiesta pervenuta e si occupa di fornire i dati richiesti
	 * in formato zip
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ControlStationCore.logDebug("Ricevuta Richiesta di esportazione configurazione di Sistema...");
		Enumeration<?> en = request.getParameterNames();
		ControlStationCore.logDebug("Parametri (nome = valore):\n-----------------");
		while (en.hasMoreElements()) {
			String param = (String) en.nextElement();
			String value = request.getParameter(param);
			ControlStationCore.logDebug(param + " = " + value);
		}
		ControlStationCore.logDebug("-----------------");

		String alias = request.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_SISTEMA_NODO_CLUSTER);
		
		try {
			
			ConfigurazioneCore confCore = new ConfigurazioneCore();
			
			// setto content-type e header per gestire il download lato client
			MimeTypes mimeTypes = MimeTypes.getInstance();
			String mimeType = null;
			String ext = "txt";
			if(mimeTypes.existsExtension(ext)){
				mimeType = mimeTypes.getMimeType(ext);
				//System.out.println("CUSTOM ["+mimeType+"]");		
			}
			else{
				mimeType = ArchiviCostanti.HEADER_X_DOWNLOAD;
			}
			
			response.setContentType(mimeType);
			response.setHeader(ArchiviCostanti.HEADER_CONTENT_DISPOSITION, ArchiviCostanti.HEADER_ATTACH_FILE + "configurazioneSistema.txt");
	
			OutputStream out = response.getOutputStream();	
			out.write(getInformazioniStatoPorta(alias, confCore).getBytes());
			out.flush();
			out.close();
		
		} catch (Exception e) {
			ControlStationCore.logError("Errore durante l'export della configurazione di sistema: "+e.getMessage(), e);
			throw new ServletException(e);
		} 
	}

	
	private String getInformazioniStatoPorta(String alias, ConfigurazioneCore confCore) throws Exception{
		
		InformazioniStatoPorta infoStatoPorta = new InformazioniStatoPorta();
		
		Object gestoreRisorseJMX = confCore.getGestoreRisorseJMX(alias);
		
		String versionePdD = null;
		try{
			versionePdD = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versionePdD(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione della PdD (jmxResourcePdD): "+e.getMessage(),e);
			versionePdD = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String versioneBaseDati = null;
		try{
			versioneBaseDati = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versioneBaseDati(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione della base dati (jmxResourcePdD): "+e.getMessage(),e);
			versioneBaseDati = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String confDir = null;
		try{
			confDir = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_directoryConfigurazione(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della directory di configurazione (jmxResourcePdD): "+e.getMessage(),e);
			confDir = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String versioneJava = null;
		try{
			versioneJava = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_versioneJava(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura della versione di java (jmxResourcePdD): "+e.getMessage(),e);
			versioneJava = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoDatabase = null;
		try{
			infoDatabase = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_informazioniDatabase(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sul database (jmxResourcePdD): "+e.getMessage(),e);
			infoDatabase = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		String infoProtocolli = null;
		try{
			infoProtocolli = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeRisorsa(alias), 
					confCore.getJmxPdD_configurazioneSistema_nomeMetodo_pluginProtocols(alias));
		}catch(Exception e){
			ControlStationCore.logError("Errore durante la lettura delle informazioni sui protocolli (jmxResourcePdD): "+e.getMessage(),e);
			infoProtocolli = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
		}
		
		InformazioniStatoPortaCache [] cacheArray = null;
		
		List<String> caches = confCore.getJmxPdD_caches(alias);
		if(caches!=null && caches.size()>0){
			
			cacheArray = new InformazioniStatoPortaCache[caches.size()];
			int i = 0;
			for (String cacheName : caches) {
				
				boolean enabled = false;
				try{
					String stato = confCore.readJMXAttribute(gestoreRisorseJMX,alias,confCore.getJmxPdD_configurazioneSistema_type(alias), 
							cacheName,
							confCore.getJmxPdD_cache_nomeAttributo_cacheAbilitata(alias));
					if(stato.equalsIgnoreCase("true")){
						enabled = true;
					}
				}catch(Exception e){
					ControlStationCore.logError("Errore durante la lettura dello stato della cache ["+cacheName+"](jmxResourcePdD): "+e.getMessage(),e);
				}
				
				cacheArray[i] = new InformazioniStatoPortaCache(cacheName, enabled);
				
				if(enabled){
					String params = null;
					try{
						params = confCore.invokeJMXMethod(gestoreRisorseJMX,alias,confCore.getJmxPdD_cache_type(alias), 
								cacheName,
								confCore.getJmxPdD_cache_nomeMetodo_statoCache(alias));
					}catch(Exception e){
						ControlStationCore.logError("Errore durante la lettura dello stato della cache ["+cacheName+"](jmxResourcePdD): "+e.getMessage(),e);
						params = ConfigurazioneCostanti.LABEL_INFORMAZIONE_NON_DISPONIBILE;
					}
					cacheArray[i].setStatoCache(params);
				}
						
				i++;
			}
		}
		
		return infoStatoPorta.formatStatoPorta(versionePdD, versioneBaseDati, confDir, versioneJava, infoDatabase, infoProtocolli, cacheArray);
	}
}
