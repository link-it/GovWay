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
package org.openspcoop2.web.ctrlstat.servlet.pd;

import java.util.Vector;

import org.openspcoop2.core.config.constants.CostantiConfigurazione;
import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.config.constants.TipoAutorizzazione;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.lib.mvc.ForwardParams;

/**
 * PorteDelegateCostanti
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PorteDelegateCostanti {

	/* OBJECT NAME */

	public final static String OBJECT_NAME_PORTE_DELEGATE = "porteDelegate";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY = "porteDelegateWS";
	public final static ForwardParams TIPO_OPERAZIONE_MESSAGE_SECURITY = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST = "porteDelegateWSRequest";
	public final static String OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE = "porteDelegateWSResponse";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO = "porteDelegateServizioApplicativo";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_RUOLI = "porteDelegateRuoli";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA = "porteDelegateCorrelazioneApplicativa";
	public final static ForwardParams TIPO_OPERAZIONE_CORRELAZIONE_APPLICATIVA = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST = "porteDelegateCorrelazioneApplicativaRequest";
	public final static String OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE = "porteDelegateCorrelazioneApplicativaResponse";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_MTOM = "porteDelegateMTOM";
	public final static ForwardParams TIPO_OPERAZIONE_MTOM = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_DELEGATE_MTOM_REQUEST = "porteDelegateMTOMRequest";
	public final static String OBJECT_NAME_PORTE_DELEGATE_MTOM_RESPONSE = "porteDelegateMTOMResponse";
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_EXTENDED = "porteDelegateExtended";

	public final static String OBJECT_NAME_PORTE_DELEGATE_CONTROLLO_ACCESSI = "porteDelegateControlloAccessi";
	public final static ForwardParams TIPO_OPERAZIONE_CONTROLLO_ACCESSI = ForwardParams.OTHER("");
	
	public final static String OBJECT_NAME_PORTE_DELEGATE_AZIONE = "porteDelegateAzione";

	/* SERVLET NAME */

	public final static String SERVLET_NAME_PORTE_DELEGATE_ADD = OBJECT_NAME_PORTE_DELEGATE+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CHANGE = OBJECT_NAME_PORTE_DELEGATE+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_DELETE = OBJECT_NAME_PORTE_DELEGATE+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_LIST = OBJECT_NAME_PORTE_DELEGATE+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_LIST);
	}


	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY+".do";

	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_ADD = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_CHANGE = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_DELETE = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_LIST = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_ADD = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_CHANGE = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_DELETE = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_LIST = OBJECT_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_LIST);
	}

	public final static String SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_ADD = OBJECT_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_DELETE = OBJECT_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_LIST = OBJECT_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_SERVIZIO_APPLICATIVO = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_DELEGATE_RUOLI_ADD = OBJECT_NAME_PORTE_DELEGATE_RUOLI+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_RUOLI_DELETE = OBJECT_NAME_PORTE_DELEGATE_RUOLI+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_RUOLI_LIST = OBJECT_NAME_PORTE_DELEGATE_RUOLI+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_RUOLI = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_RUOLI_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_RUOLI_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_RUOLI_LIST);
	}

	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA+".do";
	
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_ADD = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_CHANGE = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_DELETE = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_ADD = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_CHANGE = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_DELETE = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_LIST = OBJECT_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RESPONSE_LIST);
	}


	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM = OBJECT_NAME_PORTE_DELEGATE_MTOM+".do";

	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_ADD = OBJECT_NAME_PORTE_DELEGATE_MTOM_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_CHANGE = OBJECT_NAME_PORTE_DELEGATE_MTOM_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_DELETE = OBJECT_NAME_PORTE_DELEGATE_MTOM_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_LIST = OBJECT_NAME_PORTE_DELEGATE_MTOM_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_MTOM_REQUEST = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_ADD = OBJECT_NAME_PORTE_DELEGATE_MTOM_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_CHANGE = OBJECT_NAME_PORTE_DELEGATE_MTOM_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_DELETE = OBJECT_NAME_PORTE_DELEGATE_MTOM_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_LIST = OBJECT_NAME_PORTE_DELEGATE_MTOM_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_MTOM_RESPONSE = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_MTOM_RESPONSE_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_DELEGATE_EXTENDED_ADD = OBJECT_NAME_PORTE_DELEGATE_EXTENDED+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_EXTENDED_CHANGE = OBJECT_NAME_PORTE_DELEGATE_EXTENDED+"Change.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_EXTENDED_DELETE = OBJECT_NAME_PORTE_DELEGATE_EXTENDED+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_EXTENDED_LIST = OBJECT_NAME_PORTE_DELEGATE_EXTENDED+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_EXTENDED = new Vector<String>();
	static{
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_EXTENDED_ADD);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_EXTENDED_CHANGE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_EXTENDED_DELETE);
		SERVLET_PORTE_DELEGATE.add(SERVLET_NAME_PORTE_DELEGATE_EXTENDED_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_DELEGATE_CONTROLLO_ACCESSI = PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_CONTROLLO_ACCESSI+".do";
	
	public final static String SERVLET_NAME_PORTE_DELEGATE_AZIONE_ADD = PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_AZIONE+"Add.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_AZIONE_DELETE = PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_AZIONE+"Del.do";
	public final static String SERVLET_NAME_PORTE_DELEGATE_AZIONE_LIST = PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_AZIONE+"List.do";
	public final static Vector<String> SERVLET_PORTE_DELEGATE_AZIONE = new Vector<String>();
	
	static{
		PorteDelegateCostanti.SERVLET_PORTE_DELEGATE_AZIONE.add(PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_AZIONE_ADD);
		PorteDelegateCostanti.SERVLET_PORTE_DELEGATE_AZIONE.add(PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_AZIONE_DELETE);
		PorteDelegateCostanti.SERVLET_PORTE_DELEGATE_AZIONE.add(PorteDelegateCostanti.SERVLET_NAME_PORTE_DELEGATE_AZIONE_LIST);
	}
	

	/* LABEL GENERALI */

	public final static String LABEL_PORTE_DELEGATE = "Porte Delegate";

	public final static String LABEL_PD_MENU_VISUALE_AGGREGATA = "Porte Delegate";
	
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SOGGETTO = "Soggetto";
	
	public final static String LABEL_PARAMETRO_TITOLO_PORTE_DELEGATE_DATI_GENERALI = "Dati Generali";
	public final static String LABEL_PARAMETRO_TITOLO_PORTE_DELEGATE_DATI_SERVIZIO = "Dati Servizio";
	public final static String LABEL_COLUMN_PORTE_DELEGATE_STATO_PORTA = "Abilitato";
	

	/* PARAMETRI */

	public final static String PARAMETRO_PORTE_DELEGATE_ID = CostantiControlStation.PARAMETRO_ID;
	public final static String PARAMETRO_PORTE_DELEGATE_STATO_PORTA = "statoPorta";
	public final static String PARAMETRO_PORTE_DELEGATE_NOME_PORTA = "nomePorta";
	public final static String PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO = CostantiControlStation.PARAMETRO_ID_SOGGETTO;
	public final static String PARAMETRO_PORTE_DELEGATE_ID_ASPS = CostantiControlStation.PARAMETRO_ID_ASPS;
	public final static String PARAMETRO_PORTE_DELEGATE_ID_FRUIZIONE = CostantiControlStation.PARAMETRO_ID_FRUIZIONE;
	public final static String PARAMETRO_PORTE_DELEGATE_PROVIDER = "provider";
	public final static String PARAMETRO_PORTE_DELEGATE_ID_CORRELAZIONE = "idcorr";
	public final static String PARAMETRO_PORTE_DELEGATE_TIPO_SOGGETTO = "tipoprov";
	public final static String PARAMETRO_PORTE_DELEGATE_NOME_SOGGETTO = "nomeprov";
	public final static String PARAMETRO_PORTE_DELEGATE_DESCRIZIONE = "descr";
	public final static String PARAMETRO_PORTE_DELEGATE_TIPO_SERVIZIO = "tiposervizio";
	public final static String PARAMETRO_PORTE_DELEGATE_MODE_SERVIZIO = "modeservizio";
	public final static String PARAMETRO_PORTE_DELEGATE_SERVIZIO = "servizio";
	public final static String PARAMETRO_PORTE_DELEGATE_VERSIONE_SERVIZIO = "versioneservizio";
	public final static String PARAMETRO_PORTE_DELEGATE_AUTORIZZAZIONE_CONTENUTI = "autorizzazioneContenuti";
	public final static String PARAMETRO_PORTE_DELEGATE_TIPO_VALIDAZIONE = "tipo_validazione";
	public final static String PARAMETRO_PORTE_DELEGATE_XSD = "xsd";
	public final static String PARAMETRO_PORTE_DELEGATE_GESTIONE_MANIFEST = "gestManifest";
	public final static String PARAMETRO_PORTE_DELEGATE_GESTIONE_BODY = "gestBody";
	public final static String PARAMETRO_PORTE_DELEGATE_LOCAL_FORWARD = "localForward";
	public final static String PARAMETRO_PORTE_DELEGATE_LOCAL_FORWARD_PA = "localForwardPA";
	public final static String PARAMETRO_PORTE_DELEGATE_STATELESS = "stateless";
	public final static String PARAMETRO_PORTE_DELEGATE_AZIONE = CostantiControlStation.PARAMETRO_AZIONE;
	public final static String PARAMETRO_PORTE_DELEGATE_MODE_AZIONE = "modeaz";
	public final static String PARAMETRO_PORTE_DELEGATE_AZIONE_ID = "azid";
	public final static String PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_SIMMETRICA = "ricsim";
	public final static String PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_ASIMMETRICA = "ricasim";
	public final static String PARAMETRO_PORTE_DELEGATE_SOGGETTO_ID = "soggid";
	public final static String PARAMETRO_PORTE_DELEGATE_SERVIZIO_ID = "servid";
	public final static String PARAMETRO_PORTE_DELEGATE_SP = "sp";
	public final static String PARAMETRO_PORTE_DELEGATE_TIPO_SP = "tiposp";
	public final static String PARAMETRO_PORTE_DELEGATE_MODE_SP = "modesp";
	public final static String PARAMETRO_PORTE_DELEGATE_SCADENZA_CORRELAZIONE_APPLICATIVA = CostantiControlStation.PARAMETRO_SCADENZA_CORRELAZIONE_APPLICATIVA;
	public final static String PARAMETRO_PORTE_DELEGATE_INTEGRAZIONE = "integrazione";
	public final static String PARAMETRO_PORTE_DELEGATE_NOME = "nome";
	public final static String PARAMETRO_PORTE_DELEGATE_OLD_NOME_PD = "oldNomePD";
	public final static String PARAMETRO_PORTE_DELEGATE_SERVIZIO_APPLICATIVO = "servizioApplicativo";
	public final static String PARAMETRO_PORTE_DELEGATE_MODE = CostantiControlStation.PARAMETRO_MODE_CORRELAZIONE_APPLICATIVA;
	public final static String PARAMETRO_PORTE_DELEGATE_ELEMENTO_XML = CostantiControlStation.PARAMETRO_ELEMENTO_XML;
	public final static String PARAMETRO_PORTE_DELEGATE_GESTIONE_IDENTIFICAZIONE_FALLITA = "gif";
	public final static String PARAMETRO_PORTE_DELEGATE_PATTERN = CostantiControlStation.PARAMETRO_PATTERN;
	public final static String PARAMETRO_PORTE_DELEGATE_RIUSO_ID_MESSAGGIO = "riusoIdMessaggio";
	public final static String PARAMETRO_PORTE_DELEGATE_VALORE = "valore";
	public final static String PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY = "messageSecurity";
	public final static String PARAMETRO_PORTE_DELEGATE_FORCE_WSDL_BASED = "forceWsdlBased";
	public final static String PARAMETRO_PORTE_DELEGATE_IDENTIFICAZIONE = "identificazione";
	public final static String PARAMETRO_PORTE_DELEGATE_NOME_PORTA_DELEGANTE = "nomePortaDelegante";
	public final static String PARAMETRO_PORTE_DELEGATE_MODE_CREAZIONE = "modeCreazione";
	public final static String PARAMETRO_PORTE_DELEGATE_MAPPING = "mapping";
	
	public final static String PARAMETRO_PORTE_DELEGATE_MTOM_RICHIESTA = CostantiControlStation.PARAMETRO_MTOM_RICHIESTA;
	public final static String PARAMETRO_PORTE_DELEGATE_MTOM_RISPOSTA = CostantiControlStation.PARAMETRO_MTOM_RISPOSTA;
	
	public final static String PARAMETRO_PORTE_DELEGATE_OBBLIGATORIO = CostantiControlStation.PARAMETRO_OBBLIGATORIO;
	public final static String PARAMETRO_PORTE_DELEGATE_CONTENT_TYPE = CostantiControlStation.PARAMETRO_CONTENT_TYPE;
	
	public final static String PARAMETRO_PORTE_DELEGATE_APPLICA_MTOM_RICHIESTA = CostantiControlStation.PARAMETRO_APPLICA_MTOM_RICHIESTA;
	public final static String PARAMETRO_PORTE_DELEGATE_APPLICA_MTOM_RISPOSTA = CostantiControlStation.PARAMETRO_APPLICA_MTOM_RISPOSTA;
	public final static String PARAMETRO_PORTE_DELEGATE_APPLICA_MTOM = CostantiControlStation.PARAMETRO_APPLICA_MTOM_RICHIESTA;

	public final static String PARAMETRO_PORTE_DELEGATE_APPLICA_MODIFICA = CostantiControlStation.PARAMETRO_APPLICA_MODIFICA;
	
	public final static String ATTRIBUTO_PORTE_DELEGATE_PARENT = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT;
	public final static int ATTRIBUTO_PORTE_DELEGATE_PARENT_NONE = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_NONE;
	public final static int ATTRIBUTO_PORTE_DELEGATE_PARENT_SOGGETTO = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_SOGGETTO;
	public final static int ATTRIBUTO_PORTE_DELEGATE_PARENT_CONFIGURAZIONE = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_CONFIGURAZIONE;
	
	
	/* LABEL PARAMETRI */
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ID = "Id";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_STATO_PORTA = "Stato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ID_SOGGETTO = "IdSogg";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SOGGETTI = "Soggetti";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SOGGETTO_EROGATORE = "Soggetto Erogatore";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_PORTE_DELEGATE_DI = "Porte Delegate di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SERVIZIO_APPLICATIVO_DI = "Servizi Applicativi di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RUOLI_DI = "Ruoli di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY_DI = "Message-Security di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_FLOW_DI = "Message-Security request-flow di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_FLOW_DI = "Message-Security response-flow di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY_REQUEST_FLOW_DI = "Parametri Message-Security della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY_RESPONSE_FLOW_DI = "Parametri Message-Security della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_DI = "Configurazione MTOM di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_REQUEST_FLOW_DI = "MTOM request-flow di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_RESPONSE_FLOW_DI = "MTOM response-flow di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_REQUEST_FLOW_DI = "Parametri MTOM della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_RESPONSE_FLOW_DI = "Parametri MTOM della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_DI = "Correlazione Applicativa di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI = "Correlazioni Applicative per la richiesta di ";
//	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RISPOSTA_DI = "Correlazioni Applicative per la risposta di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI = "Regole di Correlazione della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONI_APPLICATIVE_RISPOSTA_DI = "Regole di Correlazione della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_NOME = "Nome";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_VALORE = "Valore";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_TIPO = "Tipo";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_DESCRIZIONE = "Descrizione";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_VALIDAZIONE_CONTENUTI = "Validazione Contenuti";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_TIPO_VALIDAZIONE = "Tipo Validazione";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_INTEGRAZIONE = "Integrazione";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_METADATI = "Metadati";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_STATELESS = "Stateless";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_LOCAL_FORWARD = "Local Forward";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SOAP_WITH_ATTACHMENTS = "SOAP With Attachments";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_GESTIONE_BODY = "Gestione Body";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_GESTIONE_MANIFEST = "Gestione Manifest";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_GESTIONE_ASINCRONA = "Gestione Asincrona";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_SIMMETRICA = "Ricevuta Simmetrica";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_ASIMMETRICA = "Ricevuta Asimmetrica";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_PATTERN = "Pattern";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SERVIZIO = "Servizio";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_TIPO_SERVIZIO = "Tipo";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_NOME_SERVIZIO = "Nome Servizio";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_VERSIONE_SERVIZIO = "Versione Servizio";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MODALITA = "Modalit&agrave;";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_AZIONE = CostantiControlStation.LABEL_PARAMETRO_AZIONE;
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_AZIONI = CostantiControlStation.LABEL_PARAMETRO_AZIONI;
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_AZIONI_DI = CostantiControlStation.LABEL_PARAMETRO_AZIONI_DI;
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_QUALSIASI_AZIONE = "Tutte le azioni del servizio";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MODALITA_IDENTIFICAZIONE = "Modalit&agrave; identificazione";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ELEMENTO_XML = "Elemento xml";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ELEMENTO_XML_BR = "Elemento xml<BR/>(Il campo vuoto indica qualsiasi elemento)";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ELEMENTO_XML_NOTE = "Il campo vuoto indica qualsiasi elemento";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RISULTATI_RICERCA = "Risultati Ricerca";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MESSAGE_SECURITY = "Message Security";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_GESTIONE_MESSAGGIO = "Trattamento Messaggio";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM = "MTOM";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_BR_RICHIESTA = "Correlazione applicativa<BR/>Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_BR_RISPOSTA = "Correlazione applicativa<BR/>Risposta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RICHIESTA = "Identificazione Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_RISPOSTA = "Identificazione Risposta";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA = "Correlazione Applicativa";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_ABILITATA = "abilitata";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CORRELAZIONE_APPLICATIVA_DISABILITATA = "disabilitata";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SERVIZI_APPLICATIVI = "Servizi Applicativi";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RUOLI = "Ruoli";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_SERVIZIO_APPLICATIVO = "Servizio Applicativo";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_GESTIONE_IDENTIFICAZIONE_FALLITA = "Identificazione fallita";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_RIUSO_ID_MESSAGGIO = "Riuso ID";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_STATO = "Stato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_FORCE_WSDL_BASED = "ForceWsdlBased";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_ABILITATO = "abilitato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MTOM_DISABILITATO = "disabilitato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_APPLICA_MTOM = CostantiControlStation.LABEL_PARAMETRO_APPLICA_MTOM;
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ACCETTA_MTOM = CostantiControlStation.LABEL_PARAMETRO_ACCETTA_MTOM;

	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CONTROLLO_ACCESSI = "Controllo Accessi";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_CONTROLLO_ACCESSI_DI = "Controllo Accessi di ";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_ABILITATO = "Abilitato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_DISABILITATO = "Disabilitato";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MAPPING_FRUIZIONE_PD_NOME_DEFAULT = "Default";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MAPPING_FRUIZIONE_PD_AZIONE_DEFAULT = "*";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MODO_CREAZIONE = "Mode";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MODO_CREAZIONE_EREDITA = "Eredita Da";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MODO_CREAZIONE_NUOVA = "Nuova";
	public final static String LABEL_PARAMETRO_PORTE_DELEGATE_MAPPING = "Configurazione";
	public static final String LABEL_FRUIZIONE_TIPO_NOME_SOGGETTO = "Fruizione {0}/{1}";
	public static final String LABEL_TIPO_NOME_SOGGETTO = "{0}/{1}";
	

	
	/* DEFAULT VALUE PARAMETRI */
	
	
	
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_NEW_ID = "0";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_REGISTER_INPUT = "register-input";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_USER_INPUT = "user-input";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_URL_BASED = "url-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_CONTENT_BASED = "content-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_INPUT_BASED = "input-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_SOAP_ACTION_BASED = "soap-action-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_WSDL_BASED = "wsdl-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_AUTENTICAZIONE =  TipoAutenticazione.SSL.getValue();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_AUTORIZZAZIONE = TipoAutorizzazione.AUTHENTICATED.getValue();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_STATELESS_DEFAULT = "default";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_STATELESS_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_STATELESS_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_LOCAL_FORWARD_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_LOCAL_FORWARD_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_BODY_NONE = "none";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_BODY_ALLEGA = "allega";  
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_BODY_SCARTA = "scarta";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_MANIFEST_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_MANIFEST_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_GEST_MANIFEST_DEFAULT = "default";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_SIMMETRICA_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_SIMMETRICA_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_ASIMMETRICA_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_RICEVUTA_ASINCRONA_ASIMMETRICA_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_VALIDAZIONE_XSD =  CostantiConfigurazione.VALIDAZIONE_CONTENUTI_APPLICATIVI_XSD.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_VALIDAZIONE_WSDL = CostantiConfigurazione.VALIDAZIONE_CONTENUTI_APPLICATIVI_WSDL.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_VALIDAZIONE_OPENSPCOOP = CostantiConfigurazione.VALIDAZIONE_CONTENUTI_APPLICATIVI_OPENSPCOOP.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_XSD_DISABILITATO = CostantiConfigurazione.STATO_CON_WARNING_DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_XSD_ABILITATO = CostantiConfigurazione.STATO_CON_WARNING_ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_XSD_WARNING_ONLY = CostantiConfigurazione.STATO_CON_WARNING_WARNING_ONLY.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_AZ_INPUT_BASED = "input-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_AZ_SOAP_ACTION_BASED = "soap-action-based";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODE_AZ_STATIC = "static";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_ESTERNO = "esterno";
	public final static String VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_INPUT_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_INPUT_BASED;
	public final static String VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_URL_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_URL_BASED;
	public final static String VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_CONTENT_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_CONTENT_BASED;
	public final static String VALUE_PARAMETRO_PORTE_DELEGATE_TIPO_MODE_CORRELAZIONE_DISABILITATO = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_DISABILITATO;

	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODO_CREAZIONE_EREDITA = "eredita";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_DELEGATE_MODO_CREAZIONE_NUOVA = "nuova";
	
  
}
