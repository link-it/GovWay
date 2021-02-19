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

package org.openspcoop2.core.config.constants;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.core.commons.Filtri;

/**
 * PluginCostanti
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PluginCostanti {

	public final static String FILTRO_RUOLO_NOME = Filtri.FILTRO_RUOLO_NOME;
	public final static String FILTRO_RUOLO_VALORE_ENTRAMBI = Filtri.FILTRO_RUOLO_VALORE_ENTRAMBI;
	public final static String FILTRO_RUOLO_LABEL_ENTRAMBI = "Erogazione/Fruizione";
	public final static String FILTRO_RUOLO_VALORE_FRUIZIONE = Filtri.FILTRO_RUOLO_VALORE_FRUIZIONE;
	public final static String FILTRO_RUOLO_VALORE_EROGAZIONE = Filtri.FILTRO_RUOLO_VALORE_EROGAZIONE;
	public final static List<String> FILTRO_RUOLO_VALORI_SENZA_ENTRAMBI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_VALORI_SENZA_ENTRAMBI.add(FILTRO_RUOLO_VALORE_EROGAZIONE);
		FILTRO_RUOLO_VALORI_SENZA_ENTRAMBI.add(FILTRO_RUOLO_VALORE_FRUIZIONE);
	}
	public final static List<String> FILTRO_RUOLO_VALORI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_VALORI.addAll(FILTRO_RUOLO_VALORI_SENZA_ENTRAMBI);
		FILTRO_RUOLO_VALORI.add(FILTRO_RUOLO_VALORE_ENTRAMBI);
	}
	public final static List<String> FILTRO_RUOLO_LABELS_SENZA_ENTRAMBI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_LABELS_SENZA_ENTRAMBI.add(FILTRO_RUOLO_VALORE_EROGAZIONE);
		FILTRO_RUOLO_LABELS_SENZA_ENTRAMBI.add(FILTRO_RUOLO_VALORE_FRUIZIONE);
	}
	public final static List<String> FILTRO_RUOLO_LABELS = new ArrayList<String>();
	static {
		FILTRO_RUOLO_LABELS.addAll(FILTRO_RUOLO_LABELS_SENZA_ENTRAMBI);
		FILTRO_RUOLO_LABELS.add(FILTRO_RUOLO_LABEL_ENTRAMBI);
	}
	
	public final static String FILTRO_RUOLO_LABEL = "Tipologia API";
	
	
	public final static String FILTRO_APPLICABILITA_NOME = Filtri.FILTRO_APPLICABILITA_NOME;
	public final static String FILTRO_APPLICABILITA_VALORE_QUALSIASI = Filtri.FILTRO_APPLICABILITA_VALORE_QUALSIASI;
	public final static String FILTRO_APPLICABILITA_VALORE_IMPLEMENTAZIONE_API = Filtri.FILTRO_APPLICABILITA_VALORE_IMPLEMENTAZIONE_API;
	public final static String FILTRO_APPLICABILITA_LABEL_IMPLEMENTAZIONE_API = "Erogazione/Fruizione";
	public final static String FILTRO_APPLICABILITA_VALORE_FRUIZIONE = Filtri.FILTRO_APPLICABILITA_VALORE_FRUIZIONE;
	public final static String FILTRO_APPLICABILITA_VALORE_EROGAZIONE = Filtri.FILTRO_APPLICABILITA_VALORE_EROGAZIONE;
	public final static String FILTRO_APPLICABILITA_VALORE_CONFIGURAZIONE = Filtri.FILTRO_APPLICABILITA_VALORE_CONFIGURAZIONE;
	public final static List<String> FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI = new ArrayList<String>();
	static {
		FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_CONFIGURAZIONE);
		FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_EROGAZIONE);
		FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_FRUIZIONE);
		FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_IMPLEMENTAZIONE_API);
	}
	public final static List<String> FILTRO_APPLICABILITA_VALORI = new ArrayList<String>();
	static {
		FILTRO_APPLICABILITA_VALORI.addAll(FILTRO_APPLICABILITA_VALORI_SENZA_QUALSIASI);
		FILTRO_APPLICABILITA_VALORI.add(FILTRO_APPLICABILITA_VALORE_QUALSIASI);
	}
	public final static List<String> FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI = new ArrayList<String>();
	static {
		FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_CONFIGURAZIONE);
		FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_EROGAZIONE);
		FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_VALORE_FRUIZIONE);
		FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI.add(FILTRO_APPLICABILITA_LABEL_IMPLEMENTAZIONE_API);
	}
	public final static List<String> FILTRO_APPLICABILITA_LABELS = new ArrayList<String>();
	static {
		FILTRO_APPLICABILITA_LABELS.addAll(FILTRO_APPLICABILITA_LABELS_SENZA_QUALSIASI);
		FILTRO_APPLICABILITA_LABELS.add(FILTRO_APPLICABILITA_VALORE_QUALSIASI);
	}
	
	public final static String FILTRO_APPLICABILITA_LABEL = "Applicabilità";
	
	
	public final static String FILTRO_SERVICE_HANDLER_NOME = "ServiceHandler";
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INIT = FaseServiceHandler.INIT.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_EXIT = FaseServiceHandler.EXIT.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_REQUEST = FaseServiceHandler.INTEGRATION_MANAGER_REQUEST.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_RESPONSE = FaseServiceHandler.INTEGRATION_MANAGER_RESPONSE.getValue();
	public final static List<String> FILTRO_SERVICE_HANDLER_VALORI_SENZA_INTEGRATION_MANAGER = new ArrayList<String>();
	static {
		FILTRO_SERVICE_HANDLER_VALORI_SENZA_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_INIT);
		FILTRO_SERVICE_HANDLER_VALORI_SENZA_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_EXIT);
	}	
	public final static List<String> FILTRO_SERVICE_HANDLER_VALORI_CON_INTEGRATION_MANAGER = new ArrayList<String>();
	static {
		FILTRO_SERVICE_HANDLER_VALORI_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_INIT);
		FILTRO_SERVICE_HANDLER_VALORI_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_EXIT);
		FILTRO_SERVICE_HANDLER_VALORI_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_REQUEST);
		FILTRO_SERVICE_HANDLER_VALORI_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_RESPONSE);
	}
	
	public final static String FILTRO_SERVICE_HANDLER_LABEL = "Fase Processamento";
	public final static String FILTRO_SERVICE_HANDLER_LABEL_INIT = "Startup Gateway";
	public final static String FILTRO_SERVICE_HANDLER_LABEL_EXIT = "Shutdown Gateway";
	public final static String FILTRO_SERVICE_HANDLER_LABEL_INTEGRATION_MANAGER_REQUEST = "Richiesta al servizio IntegrationManager/MessageBox";
	public final static String FILTRO_SERVICE_HANDLER_LABEL_INTEGRATION_MANAGER_RESPONSE = "Risposta dal servizio IntegrationManager/MessageBox";
	public final static List<String> FILTRO_SERVICE_HANDLER_LABEL_SENZA_INTEGRATION_MANAGER = new ArrayList<String>();
	static {
		FILTRO_SERVICE_HANDLER_LABEL_SENZA_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_INIT);
		FILTRO_SERVICE_HANDLER_LABEL_SENZA_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_EXIT);
	}	
	public final static List<String> FILTRO_SERVICE_HANDLER_LABEL_CON_INTEGRATION_MANAGER = new ArrayList<String>();
	static {
		FILTRO_SERVICE_HANDLER_LABEL_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_INIT);
		FILTRO_SERVICE_HANDLER_LABEL_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_EXIT);
		FILTRO_SERVICE_HANDLER_LABEL_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_INTEGRATION_MANAGER_REQUEST);
		FILTRO_SERVICE_HANDLER_LABEL_CON_INTEGRATION_MANAGER.add(FILTRO_SERVICE_HANDLER_LABEL_INTEGRATION_MANAGER_RESPONSE);
	}
	
	public final static String FILTRO_FASE_MESSAGE_HANDLER_NOME = "FaseMessageHandler";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_PRE_IN = FaseMessageHandler.PRE_IN.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN = FaseMessageHandler.IN.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN_PROTOCOL_INFO = FaseMessageHandler.IN_PROTOCOL_INFO.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_OUT = FaseMessageHandler.OUT.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_POST_OUT = FaseMessageHandler.POST_OUT.getValue();
	public final static List<String> FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA = new ArrayList<String>();
	static {
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_PRE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN_PROTOCOL_INFO);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_OUT);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_POST_OUT);
	}
	public final static List<String> FILTRO_FASE_MESSAGE_HANDLER_VALORI_RISPOSTA = new ArrayList<String>();
	static {
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_PRE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_OUT);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_POST_OUT);
	}
	
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL = "Fase Processamento";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_PRE_IN_SHORT = "Pre-In";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_PRE_IN = FILTRO_FASE_MESSAGE_HANDLER_LABEL_PRE_IN_SHORT+" (precedente alla ricezione dei dati)";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_SHORT = "In"; 
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN = FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_SHORT+" (dopo aver ricevuto i dati)";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_PROTOCOL_INFO_SHORT = "InProfileInfo";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_PROTOCOL_INFO = FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_PROTOCOL_INFO_SHORT+" (dopo aver identificato i soggetti e l'API richiesta)";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_OUT_SHORT = "Out";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_OUT = FILTRO_FASE_MESSAGE_HANDLER_LABEL_OUT_SHORT+" (precedente all'inoltro dei dati)";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_POST_OUT_SHORT = "Post-Out";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_LABEL_POST_OUT = FILTRO_FASE_MESSAGE_HANDLER_LABEL_POST_OUT_SHORT+" (successiva all'inoltro dei dati)";
	public final static List<String> FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA = new ArrayList<String>();
	static {
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_PRE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN_PROTOCOL_INFO);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_OUT);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RICHIESTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_POST_OUT);
	}
	public final static List<String> FILTRO_FASE_MESSAGE_HANDLER_LABEL_RISPOSTA = new ArrayList<String>();
	static {
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_PRE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_IN);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_OUT);
		FILTRO_FASE_MESSAGE_HANDLER_LABEL_RISPOSTA.add(FILTRO_FASE_MESSAGE_HANDLER_LABEL_POST_OUT);
	}
	
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_NOME = "RuoloMessageHandler";
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RICHIESTA = RuoloMessageHandler.RICHIESTA.getValue();
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RISPOSTA = RuoloMessageHandler.RISPOSTA.getValue();
	public final static List<String> FILTRO_RUOLO_MESSAGE_HANDLER_VALORI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_MESSAGE_HANDLER_VALORI.add(FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RICHIESTA);
		FILTRO_RUOLO_MESSAGE_HANDLER_VALORI.add(FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RISPOSTA);
	}

	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_LABEL = "Ruolo Messaggio";
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_LABEL_RICHIESTA = "Richiesta";
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_LABEL_RISPOSTA = "Risposta";
	public final static List<String> FILTRO_RUOLO_MESSAGE_HANDLER_LABELS = new ArrayList<String>();
	static {
		FILTRO_RUOLO_MESSAGE_HANDLER_LABELS.add(FILTRO_RUOLO_MESSAGE_HANDLER_LABEL_RICHIESTA);
		FILTRO_RUOLO_MESSAGE_HANDLER_LABELS.add(FILTRO_RUOLO_MESSAGE_HANDLER_LABEL_RISPOSTA);
	}
	
}