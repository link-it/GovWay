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

package org.openspcoop2.pdd.config.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.core.config.constants.FaseMessageHandler;
import org.openspcoop2.core.config.constants.FaseServiceHandler;
import org.openspcoop2.core.config.constants.RuoloMessageHandler;

/**
 * PluginCostanti
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PluginCostanti {

	public final static String FILTRO_RUOLO_NOME = "Ruolo";
	public final static String FILTRO_RUOLO_VALORE_FRUIZIONE = "Fruizione";
	public final static String FILTRO_RUOLO_VALORE_EROGAZIONE = "Erogazione";
	public final static List<String> FILTRO_RUOLO_VALORI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_VALORI.add(FILTRO_RUOLO_VALORE_EROGAZIONE);
		FILTRO_RUOLO_VALORI.add(FILTRO_RUOLO_VALORE_FRUIZIONE);
	}
	
	public final static String FILTRO_SERVICE_HANDLER_NOME = "ServiceHandler";
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INIT = FaseServiceHandler.INIT.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_EXIT = FaseServiceHandler.EXIT.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_REQUEST = FaseServiceHandler.INTEGRATION_MANAGER_REQUEST.getValue();
	public final static String FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_RESPONSE = FaseServiceHandler.INTEGRATION_MANAGER_RESPONSE.getValue();
	public final static List<String> FILTRO_SERVICE_HANDLER_VALORI = new ArrayList<String>();
	static {
		FILTRO_SERVICE_HANDLER_VALORI.add(FILTRO_SERVICE_HANDLER_VALORE_INIT);
		FILTRO_SERVICE_HANDLER_VALORI.add(FILTRO_SERVICE_HANDLER_VALORE_EXIT);
		FILTRO_SERVICE_HANDLER_VALORI.add(FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_REQUEST);
		FILTRO_SERVICE_HANDLER_VALORI.add(FILTRO_SERVICE_HANDLER_VALORE_INTEGRATION_MANAGER_RESPONSE);
	}
	
	public final static String FILTRO_FASE_MESSAGE_HANDLER_NOME = "FaseMessageHandler";
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_PRE_IN = FaseMessageHandler.PRE_IN.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN = FaseMessageHandler.IN.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN_PROTOCOL_INFO = FaseMessageHandler.IN_PROTOCOL_INFO.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_OUT = FaseMessageHandler.OUT.getValue();
	public final static String FILTRO_FASE_MESSAGE_HANDLER_VALORE_POST_OUT = FaseMessageHandler.POST_OUT.getValue();
	public final static List<String> FILTRO_FASE_MESSAGE_HANDLER_VALORI = new ArrayList<String>();
	static {
		FILTRO_FASE_MESSAGE_HANDLER_VALORI.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_PRE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_IN_PROTOCOL_INFO);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_OUT);
		FILTRO_FASE_MESSAGE_HANDLER_VALORI.add(FILTRO_FASE_MESSAGE_HANDLER_VALORE_POST_OUT);
	}
	
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_NOME = "RuoloMessageHandler";
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RICHIESTA = RuoloMessageHandler.RICHIESTA.getValue();
	public final static String FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RISPOSTA = RuoloMessageHandler.RISPOSTA.getValue();
	public final static List<String> FILTRO_RUOLO_MESSAGE_HANDLER_VALORI = new ArrayList<String>();
	static {
		FILTRO_RUOLO_MESSAGE_HANDLER_VALORI.add(FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RICHIESTA);
		FILTRO_RUOLO_MESSAGE_HANDLER_VALORI.add(FILTRO_RUOLO_MESSAGE_HANDLER_VALORE_RISPOSTA);
	}
	
}
