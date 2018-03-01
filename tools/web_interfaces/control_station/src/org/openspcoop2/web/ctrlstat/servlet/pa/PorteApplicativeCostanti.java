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
package org.openspcoop2.web.ctrlstat.servlet.pa;

import java.util.Vector;

import org.openspcoop2.core.config.constants.CostantiConfigurazione;
import org.openspcoop2.core.config.constants.PortaApplicativaAzioneIdentificazione;
import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.config.constants.TipoAutorizzazione;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.lib.mvc.ForwardParams;

/**
 * PorteApplicativeCostanti
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PorteApplicativeCostanti {

	/* OBJECT NAME */
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE = "porteApplicative";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY = "porteApplicativeWS";
	public final static ForwardParams TIPO_OPERAZIONE_MESSAGE_SECURITY = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST = "porteApplicativeWSRequest";
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE = "porteApplicativeWSResponse";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO = "porteApplicativeServizioApplicativo";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_RUOLI = "porteApplicativeRuoli";
	
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA = "porteApplicativeCorrelazioneApplicativa";
	public final static ForwardParams TIPO_OPERAZIONE_CORRELAZIONE_APPLICATIVA = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST = "porteApplicativeCorrelazioneApplicativaRequest";
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE = "porteApplicativeCorrelazioneApplicativaResponse";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO = "porteApplicativeProprietaProtocollo";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MTOM = "porteApplicativeMTOM";
	public final static ForwardParams TIPO_OPERAZIONE_MTOM = ForwardParams.OTHER("");
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MTOM_REQUEST = "porteApplicativeMTOMRequest";
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE = "porteApplicativeMTOMResponse";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_EXTENDED = "porteApplicativeExtended";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI = "porteApplicativeControlloAccessi";
	public final static ForwardParams TIPO_OPERAZIONE_CONTROLLO_ACCESSI = ForwardParams.OTHER("");
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_AZIONE = "porteApplicativeAzione";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO = "porteApplicativeSoggetto";
	
	public final static String OBJECT_NAME_PORTE_APPLICATIVE_VALIDAZIONE_CONTENUTI = "porteApplicativeValidazioneContenuti";
	public final static ForwardParams TIPO_OPERAZIONE_VALIDAZIONE_CONTENUTI = ForwardParams.OTHER("");
	
	/* SERVLET NAME */
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY+".do";

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_RUOLI+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_RUOLI+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_RUOLI+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_RUOLI = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_RUOLI.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_RUOLI.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_RUOLI.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA+".do";
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RESPONSE_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_PROPRIETA_PROTOCOLLO_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM+".do";

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_REQUEST+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_REQUEST+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_REQUEST+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_REQUEST+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_MTOM_REQUEST = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_REQUEST.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_REQUEST_LIST);
	}

	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_MTOM_RESPONSE = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_MTOM_RESPONSE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_MTOM_RESPONSE_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_EXTENDED+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_CHANGE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_EXTENDED+"Change.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_EXTENDED+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_EXTENDED+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_EXTENDED = new Vector<String>();
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_EXTENDED.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_EXTENDED.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_CHANGE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_EXTENDED.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_EXTENDED.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_EXTENDED_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI+".do";
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_AZIONE+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_AZIONE+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_AZIONE+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_AZIONE = new Vector<String>();
	
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_AZIONE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_AZIONE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_AZIONE.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AZIONE_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_ADD = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO+"Add.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_DELETE = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO+"Del.do";
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_LIST = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_SOGGETTO+"List.do";
	public final static Vector<String> SERVLET_PORTE_APPLICATIVE_SOGGETTO = new Vector<String>();
	
	static{
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SOGGETTO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_ADD);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SOGGETTO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_DELETE);
		PorteApplicativeCostanti.SERVLET_PORTE_APPLICATIVE_SOGGETTO.add(PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_LIST);
	}
	
	public final static String SERVLET_NAME_PORTE_APPLICATIVE_VALIDAZIONE_CONTENUTI = PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_VALIDAZIONE_CONTENUTI+".do";

	/* LABEL GENERALI */
	
	public final static String LABEL_PORTE_APPLICATIVE = "Porte Applicative";
	public final static String LABEL_PORTE_APPLICATIVE_RISULTATI_RICERCA = "Risultati ricerca";

	public final static String LABEL_PA_MENU_VISUALE_AGGREGATA = "Porte Applicative";
	
	public final static String LABEL_PARAMETRO_TITOLO_PORTE_APPLICATIVE_DATI_INVOCAZIONE = "Dati Invocazione";
	public final static String LABEL_PARAMETRO_TITOLO_PORTE_APPLICATIVE_DATI_GENERALI = "Dati Generali";
	public final static String LABEL_PARAMETRO_TITOLO_PORTE_APPLICATIVE_DATI_SERVIZIO = "Dati Servizio";
	public final static String LABEL_COLUMN_PORTE_APPLICATIVE_STATO_PORTA = "Abilitato";
	
	/* PARAMETRI */
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_ID = CostantiControlStation.PARAMETRO_ID;
	public final static String PARAMETRO_PORTE_APPLICATIVE_STATO_PORTA = "statoPorta";
	public final static String PARAMETRO_PORTE_APPLICATIVE_NOME_PORTA = CostantiControlStation.PARAMETRO_NOME_PORTA;
	public final static String PARAMETRO_PORTE_APPLICATIVE_ID_PORTA = CostantiControlStation.PARAMETRO_ID_PORTA;
	public final static String PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO = CostantiControlStation.PARAMETRO_ID_SOGGETTO;
	public final static String PARAMETRO_PORTE_APPLICATIVE_ID_ASPS = CostantiControlStation.PARAMETRO_ID_ASPS;
	public final static String PARAMETRO_PORTE_APPLICATIVE_PROVIDER = CostantiControlStation.PARAMETRO_PROVIDER;
	public final static String PARAMETRO_PORTE_APPLICATIVE_ID_CORRELAZIONE_APPLICATIVA =  CostantiControlStation.PARAMETRO_ID_CORRELAZIONE;
	public final static String PARAMETRO_PORTE_APPLICATIVE_TIPO_SOGGETTO = "tipoprov";
	public final static String PARAMETRO_PORTE_APPLICATIVE_NOME_SOGGETTO = "nomeprov";
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_CONFIGURAZIONE_DATI_INVOCAZIONE = "configurazioneDatiInvocazione";
	public final static String PARAMETRO_PORTE_APPLICATIVE_CONFIGURAZIONE_ALTRO = "configurazioneAltro";
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE = CostantiControlStation.PARAMETRO_MODE_CORRELAZIONE_APPLICATIVA;
	public final static String PARAMETRO_PORTE_APPLICATIVE_ELEMENTO_XML = CostantiControlStation.PARAMETRO_ELEMENTO_XML;
	public final static String PARAMETRO_PORTE_APPLICATIVE_GESTIONE_IDENTIFICAZIONE_FALLITA = "gif";
	public final static String PARAMETRO_PORTE_APPLICATIVE_PATTERN = CostantiControlStation.PARAMETRO_PATTERN;
	public final static String PARAMETRO_PORTE_APPLICATIVE_RIUSO_ID_MESSAGGIO = "riusoIdMessaggio";
	public final static String PARAMETRO_PORTE_APPLICATIVE_VALORE = "valore";
	public final static String PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY = "messageSecurity";
	public final static String PARAMETRO_PORTE_APPLICATIVE_NOME = "nome";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO = "servizioApplicativo";
	public final static String PARAMETRO_PORTE_APPLICATIVE_RICEVUTA_ASINCRONA_SIMMETRICA = "ricsim";
	public final static String PARAMETRO_PORTE_APPLICATIVE_RICEVUTA_ASINCRONA_ASIMMETRICA = "ricasim";
	public final static String PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI = CostantiControlStation.PARAMETRO_AUTORIZZAZIONE_CONTENUTI;
	public final static String PARAMETRO_PORTE_APPLICATIVE_TIPO_VALIDAZIONE = CostantiControlStation.PARAMETRO_PORTE_TIPO_VALIDAZIONE;
	public final static String PARAMETRO_PORTE_APPLICATIVE_XSD = CostantiControlStation.PARAMETRO_PORTE_XSD;
	public final static String PARAMETRO_PORTE_APPLICATIVE_GESTIONE_MANIFEST = "gestManifest";
	public final static String PARAMETRO_PORTE_APPLICATIVE_GESTIONE_BODY = "gestBody";
	public final static String PARAMETRO_PORTE_APPLICATIVE_LOCAL_FORWARD = "localForward";
	public final static String PARAMETRO_PORTE_APPLICATIVE_STATELESS = "stateless";
	public final static String PARAMETRO_PORTE_APPLICATIVE_BEHAVIOUR = "behaviour";
	public final static String PARAMETRO_PORTE_APPLICATIVE_DESCRIZIONE = "descr";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SOGGETTO_VIRTUALE = "soggvirt";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SERVIZIO = "servizio";
	public final static String PARAMETRO_PORTE_APPLICATIVE_AZIONE = CostantiControlStation.PARAMETRO_AZIONE;
	public final static String PARAMETRO_PORTE_APPLICATIVE_AZIONI = CostantiControlStation.PARAMETRO_AZIONI;
	public final static String PARAMETRO_PORTE_APPLICATIVE_OLD_NOME_PA = "oldNomePA";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SCADENZA_CORRELAZIONE_APPLICATIVA = "scadcorr";
	public final static String PARAMETRO_PORTE_APPLICATIVE_INTEGRAZIONE = "integrazione";
	public final static String PARAMETRO_PORTE_APPLICATIVE_IDENTIFICATIVO_MESSAGGIO = "idMessaggio";
	public final static String PARAMETRO_PORTE_APPLICATIVE_IDENTIFICAZIONE = "identificazione";
	public final static String PARAMETRO_PORTE_APPLICATIVE_NOME_PORTA_DELEGANTE = "nomePortaDelegante";
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_CREAZIONE = "modeCreazione";
	public final static String PARAMETRO_PORTE_APPLICATIVE_MAPPING = "mapping";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SOGGETTO = CostantiControlStation.PARAMETRO_SOGGETTO;
	public final static String PARAMETRO_PORTE_APPLICATIVE_FORCE_WSDL_BASED = "forceWsdlBased";
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_AZIONE = "modeaz";
	public final static String PARAMETRO_PORTE_APPLICATIVE_AZIONE_ID = "azid";
	public final static String PARAMETRO_PORTE_APPLICATIVE_SERVICE_BINDING = CostantiControlStation.PARAMETRO_SERVICE_BINDING;
	
	public final static String ATTRIBUTO_PORTE_APPLICATIVE_PARENT = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT;
	public final static int ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_NONE;
	public final static int ATTRIBUTO_PORTE_APPLICATIVE_PARENT_SOGGETTO = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_SOGGETTO;
	public final static int ATTRIBUTO_PORTE_APPLICATIVE_PARENT_CONFIGURAZIONE = CostantiControlStation.ATTRIBUTO_CONFIGURAZIONE_PARENT_CONFIGURAZIONE;
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_MTOM_RICHIESTA = CostantiControlStation.PARAMETRO_MTOM_RICHIESTA;
	public final static String PARAMETRO_PORTE_APPLICATIVE_MTOM_RISPOSTA = CostantiControlStation.PARAMETRO_MTOM_RISPOSTA;
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_OBBLIGATORIO = CostantiControlStation.PARAMETRO_OBBLIGATORIO;
	public final static String PARAMETRO_PORTE_APPLICATIVE_CONTENT_TYPE = CostantiControlStation.PARAMETRO_CONTENT_TYPE;
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_APPLICA_MTOM_RICHIESTA = CostantiControlStation.PARAMETRO_APPLICA_MTOM_RICHIESTA;
	public final static String PARAMETRO_PORTE_APPLICATIVE_APPLICA_MTOM_RISPOSTA = CostantiControlStation.PARAMETRO_APPLICA_MTOM_RISPOSTA;
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_APPLICA_MTOM = CostantiControlStation.PARAMETRO_PORTE_APPLICA_MTOM;
	public final static String PARAMETRO_PORTE_APPLICATIVE_APPLICA_MODIFICA = CostantiControlStation.PARAMETRO_APPLICA_MODIFICA;

	/* LABEL PARAMETRI */
	
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ID = "Id";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_STATO_PORTA = "Stato";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO = "IdSogg";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ID_CORRELAZIONE_APPLICATIVA = "idCorr";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTI = "Soggetti";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZIO = "Servizio";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_AZIONE_MODALITA = "Modalità Identificazione Azione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_AZIONE = CostantiControlStation.LABEL_PARAMETRO_AZIONE;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_AZIONI = CostantiControlStation.LABEL_PARAMETRO_AZIONI;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_AZIONI_CONFIG_DI = CostantiControlStation.LABEL_PARAMETRO_AZIONI_CONFIG_DI;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_NOME = "Nome";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_DATI_INVOCAZIONE = "Dati Invocazione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_URL_INVOCAZIONE = "URL";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CONFIGURAZIONE = "Configurazione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_OPZIONI_AVANZATE = "Opzioni Avanzate";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_QUALSIASI_AZIONE = "Tutte le azioni del servizio";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_DESCRIZIONE = "Descrizione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZI_APPLICATIVI_EROGATORI = "Applicativi Erogatori";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_EROGATORE = "Applicativo Erogatore";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_VALORE = "Valore";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_PATTERN = "Pattern";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO = "Applicativo Erogatore";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZI_APPLICATIVI = "Applicativi Erogatori";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CONNETTORE = "Connettore";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RUOLI = "Ruoli";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_PROTOCOL_PROPERTIES = "Propriet&agrave; Protocollo";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_PROTOCOL_PROPERTIES_CONFIG_DI = "Propriet&agrave; Protocollo di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_PORTE_APPLICATIVE_DI = "Porte Applicative di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_CONFIG_DI = "Applicativi Erogatori di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_CONFIG = "Applicativi Erogatori";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTO_CONFIG_DI = "Soggetti di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTO_CONFIG = "Soggetti";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RUOLI_CONFIG_DI = "Ruoli di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RUOLI_CONFIG = "Ruoli";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_GESTIONE_MESSAGGIO = "Trattamento Messaggio";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY = "Message Security";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY_CONFIG_DI = "Message-Security di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_FLOW_DI = "Message-Security request-flow di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_FLOW_DI = "Message-Security response-flow di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY_REQUEST_FLOW_DI = "Parametri Message-Security della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MESSAGE_SECURITY_RESPONSE_FLOW_DI = "Parametri Message-Security della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM = "MTOM";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_CONFIG_DI = "Configurazione MTOM di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_REQUEST_FLOW_DI = "MTOM request-flow di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_RESPONSE_FLOW_DI = "MTOM response-flow di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_REQUEST_FLOW_DI = "Parametri MTOM della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_RESPONSE_FLOW_DI = "Parametri MTOM della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONI_APPLICATIVE_CONFIG_DI = "Correlazione Applicativa di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI = "Correlazioni Applicative Richiesta di ";
//	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONI_APPLICATIVE_RISPOSTA_DI = "Correlazioni Applicative per la risposta di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONI_APPLICATIVE_RICHIESTA_DI = "Regole di Correlazione della Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONI_APPLICATIVE_RISPOSTA_DI = "Regole di Correlazione della Risposta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_GESTIONE_IDENTIFICAZIONE_FALLITA = "Identificazione fallita";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODALITA_IDENTIFICAZIONE = "Modalit&agrave; identificazione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ELEMENTO_XML = "Elemento xml";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ELEMENTO_XML_BR = "Elemento xml<BR/>(Il campo vuoto indica qualsiasi elemento)";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ELEMENTO_XML_NOTE = "Il campo vuoto indica qualsiasi elemento";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI = "Autorizzazione Contenuti";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_VALIDAZIONE_CONTENUTI = CostantiControlStation.LABEL_PARAMETRO_PORTE_VALIDAZIONE_CONTENUTI;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_VALIDAZIONE_CONTENUTI_CONFIG_DI = CostantiControlStation.LABEL_PARAMETRO_PORTE_VALIDAZIONE_CONTENUTI_CONFIG_DI;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_TIPO_VALIDAZIONE = CostantiControlStation.LABEL_PARAMETRO_PORTE_TIPO_VALIDAZIONE;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_INTEGRAZIONE = "Integrazione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_METADATI = "Metadati";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_STATELESS = "Stateless";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_BEHAVIOUR = "Behaviour";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOAP_WITH_ATTACHMENTS = "SOAP With Attachments";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_GESTIONE_BODY = "Gestione Body";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_GESTIONE_MANIFEST = "Gestione Manifest";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_GESTIONE_ASINCRONA = "Gestione Asincrona";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RICEVUTA_ASINCRONA_SIMMETRICA = "Ricevuta Simmetrica";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RICEVUTA_ASINCRONA_ASIMMETRICA = "Ricevuta Asimmetrica";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTO_VIRTUALE = "Soggetto Virtuale";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_KEYWORD ="Keyword";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_BR_RICHIESTA = "Correlazione applicativa<BR/>Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_BR_RISPOSTA = "Correlazione applicativa<BR/>Risposta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RICHIESTA = "Identificazione Richiesta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_RISPOSTA = "Identificazione Risposta";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA = "Correlazione Applicativa";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_ABILITATA = "abilitata";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CORRELAZIONE_APPLICATIVA_DISABILITATA = "disabilitata";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_SOGGETTO = CostantiControlStation.LABEL_PARAMETRO_SOGGETTO;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_ABILITATO = "abilitato";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MTOM_DISABILITATO = "disabilitato";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_RISULTATI_RICERCA = "Risultati Ricerca";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_STATO = CostantiControlStation.LABEL_PARAMETRO_PORTE_STATO;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_TIPO = CostantiControlStation.LABEL_PARAMETRO_PORTE_TIPO;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_APPLICA_MTOM = CostantiControlStation.LABEL_PARAMETRO_APPLICA_MTOM;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ACCETTA_MTOM = CostantiControlStation.LABEL_PARAMETRO_PORTE_ACCETTA_MTOM;
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CONTROLLO_ACCESSI = "Controllo Accessi";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_CONTROLLO_ACCESSI_CONFIG_DI = "Controllo Accessi di ";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_ABILITATO = "Abilitato";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_DISABILITATO = "Disabilitato";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MAPPING_EROGAZIONE_PA_NOME_DEFAULT = "Default";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MAPPING_EROGAZIONE_PA_AZIONE_DEFAULT = "*";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODO_CREAZIONE = "Mode";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODO_CREAZIONE_EREDITA = "Eredita Da";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODO_CREAZIONE_NUOVA = "Nuova";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MAPPING = "Configurazione";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_FORCE_WSDL_BASED = "Force Interface";
	
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_REGISTER_INPUT = "register-input";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_HEADER_BASED = "header-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_URL_BASED = "url-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_CONTENT_BASED = "content-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_INPUT_BASED = "input-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_SOAP_ACTION_BASED = "soap-action-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_WSDL_BASED = "interface-based";
	public final static String LABEL_PARAMETRO_PORTE_APPLICATIVE_MODE_PROTOCOL_BASED = "protocol-based";
	
	/* DEFAULT VALUE PARAMETRI */
	
	public final static String VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_MODE_CORRELAZIONE_INPUT_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_INPUT_BASED;
	public final static String VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_MODE_CORRELAZIONE_URL_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_URL_BASED;
	public final static String VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_MODE_CORRELAZIONE_CONTENT_BASED = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_CONTENT_BASED;
	public final static String VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_MODE_CORRELAZIONE_DISABILITATO = CostantiControlStation.VALUE_PARAMETRO_MODE_CORRELAZIONE_DISABILITATO;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_VALIDAZIONE_DISABILITATO = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_VALIDAZIONE_DISABILITATO;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_VALIDAZIONE_ABILITATO = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_VALIDAZIONE_ABILITATO;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_VALIDAZIONE_WARNING_ONLY = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_VALIDAZIONE_WARNING_ONLY;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_VALIDAZIONE_XSD =  CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_TIPO_VALIDAZIONE_XSD;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_VALIDAZIONE_INTERFACE = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_TIPO_VALIDAZIONE_INTERFACE;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_TIPO_VALIDAZIONE_OPENSPCOOP = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_TIPO_VALIDAZIONE_OPENSPCOOP;
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_STATELESS_DEFAULT = "default";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_STATELESS_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_STATELESS_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_LOCAL_FORWARD_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_LOCAL_FORWARD_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_BODY_NONE = "none";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_BODY_ALLEGA = "allega";  
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_BODY_SCARTA = "scarta";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_MANIFEST_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_MANIFEST_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_GEST_MANIFEST_DEFAULT = "default";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_DISABILITATO = CostantiConfigurazione.DISABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_ABILITATO = CostantiConfigurazione.ABILITATO.toString();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_AUTENTICAZIONE =  TipoAutenticazione.SSL.getValue();
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE = TipoAutorizzazione.AUTHENTICATED.getValue();
	
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_REGISTER_INPUT = PortaApplicativaAzioneIdentificazione.STATIC.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_HEADER_BASED = PortaApplicativaAzioneIdentificazione.HEADER_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_URL_BASED = PortaApplicativaAzioneIdentificazione.URL_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_CONTENT_BASED = PortaApplicativaAzioneIdentificazione.CONTENT_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_INPUT_BASED = PortaApplicativaAzioneIdentificazione.INPUT_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_SOAP_ACTION_BASED = PortaApplicativaAzioneIdentificazione.SOAP_ACTION_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_WSDL_BASED = PortaApplicativaAzioneIdentificazione.INTERFACE_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_PROTOCOL_BASED = PortaApplicativaAzioneIdentificazione.PROTOCOL_BASED.toString();
	public final static String PARAMETRO_PORTE_APPLICATIVE_MODE_DELEGATED_BY = PortaApplicativaAzioneIdentificazione.DELEGATED_BY.toString();
	
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_MODO_CREAZIONE_EREDITA = "eredita";
	public final static String DEFAULT_VALUE_PARAMETRO_PORTE_APPLICATIVE_MODO_CREAZIONE_NUOVA = "nuova";
	
	/* MESSAGGI ERRORE */
	public static final String MESSAGGIO_ERRRORE_DATI_INCOMPLETI_E_NECESSARIO_INDICARE_XX = "Dati incompleti. E' necessario indicare: {0}"; 
	public static final String MESSAGGIO_ERRORE_IL_SOGGETTO_VIRTUALE_DEVE_ESSERE_SCELTO_TRA_QUELLI_DEFINITI_NEL_PANNELLO_SOGGETTI = "Il Soggetto virtuale deve essere scelto tra quelli definiti nel pannello Soggetti";
	public static final String MESSAGGIO_ERRORE_NON_INSERIRE_NE_SPAZI_NE_NEL_CAMPO_BEHAVIOUR = "Non inserire ne spazi ne ',' nel campo 'behaviour'";
	public static final String MESSAGGIO_ERRORE_NON_INSERIRE_SPAZI_ALL_INIZIO_O_ALLA_FINE_DEI_VALORI = "Non inserire spazi all'inizio o alla fine dei valori";
	public static final String MESSAGGIO_ERRORE_LA_PROPRIETA_DI_MESSAGE_SECURITY_XX_E_GIA_STATO_ASSOCIATA_ALLA_PORTA_APPLICATIVA_YY = "La propriet&agrave; di message-security {0} &egrave; gi&agrave; stata associata alla porta applicativa {1}";
	public static final String MESSAGGIO_ERRORE_NON_INSERIRE_SPAZI_NEI_NOMI = "Non inserire spazi nei nomi";
	public static final String MESSAGGIO_ERRORE_LA_PROPERTY_XX_E_GIA_STATA_ASSOCIATA_ALLA_PORTA_APPLICATIVA_YY = "La property {0} &egrave; gi&agrave; stata associata alla porta applicativa {1}";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_E_NECESSARIO_INDICARE_XX = "Dati incompleti. &Egrave; necessario indicare: {0}";
	public static final String MESSAGGIO_ERRORE_IL_SOGGETTO_XX_EGRAVE_GIA_STATO_ASSOCIATO_ALLA_PORTA_APPLICATIVA_YY = "Il Soggetto ''{0}'' &egrave; gi&agrave; stato associato alla porta applicativa {1}";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_E_NECESSARIO_INDICARE_UN_SOGGETTO = "Dati incompleti. &Egrave; necessario indicare un Soggetto";
	public static final String MESSAGGIO_ERRORE_IL_SERVIZIO_APPLICATIVO_XX_E_GIA_STATO_ASSOCIATO_ALLA_PORTA_APPLICATIVA_YY = "Il Servizio Applicativo {0} &egrave; gi&agrave; stato associato alla porta applicativa {1}";
	public static final String MESSAGGIO_ERRORE_IL_SERVIZIO_APPLICATIVO_DEV_ESSERE_SCELTO_TRA_QUELLI_DEFINITI_NEL_PANNELLO_SERVIZI_APPLICATIVI_ED_ASSOCIATI_AL_SOGGETTO_XX = "Il Servizio Applicativo dev''essere scelto tra quelli definiti nel pannello Servizi Applicativi ed associati al soggetto {0}";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_E_NECESSARIO_INDICARE_UN_SERVIZIO_APPLICATIVO = "Dati incompleti. &Egrave; necessario indicare un Servizio Applicativo";
	public static final String MESSAGGIO_ERRORE_NON_E_POSSIBILE_MODIFICARE_IL_TIPO_DI_AUTENTICAZIONE_DA_XX_A_YY_POICHÈ_RISULTANO_ASSOCIATI_AL_SERVIZIO_DEI_FRUITORI_CON_CREDENZIALI_NON_COMPATIBILI_NELLA_MODALITA_DI_ACCESSO_CON_IL_NUOVO_TIPO_DI_AUTENTICAZIONE = "Non &egrave; possibile modificare il tipo di autenticazione da [{0}] a [{1}], poichè risultano associati al servizio dei fruitori con credenziali non compatibili, nella modalit&agrave; di accesso, con il nuovo tipo di autenticazione";
	public static final String MESSAGGIO_ERRORE_INDICARE_UN_NOME_PER_L_AUTORIZZAZIONE_XX = "Indicare un nome per l'autorizzazione ''{0}''";
	public static final String MESSAGGIO_ERRORE_INDICARE_UN_NOME_PER_L_AUTENTICAZIONE_XX = "Indicare un nome per l'autenticazione ''{0}''";
	public static final String MESSAGGIO_ERRORE_MODE_AZIONE_DEV_ESSERE_USER_INPUT_REGISTER_INPUT_URL_BASED_CONTENT_BASED_INPUT_BASED_SOAP_ACTION_BASED_PROTOCOL_BASED_O_WSDL_BASED = "Mode Azione dev'essere user-input, register-input, url-based, content-based, input-based, soap-action-based, protocol-based o interface-based";
	public static final String MESSAGGIO_ERRORE_NON_INSERIRE_SPAZI_NEI_CAMPI_DI_TESTO = "Non inserire spazi nei campi di testo";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_E_NECESSARIO_INDICARE_PATTERN_AZIONE = "Dati incompleti. &Egrave; necessario indicare: Pattern azione";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_NON_E_STATA_TROVATA_NESSUNA_AZIONE_ASSOCIATA_AL_SERVIZIO_SCEGLIERE_UNA_DELLE_ALTRE_MODALITA = "Dati incompleti. Non &egrave; stata trovata nessuna azione associata al servizio. Scegliere una delle altre modalit&agrave;";
	public static final String MESSAGGIO_ERRORE_DATI_INCOMPLETI_NON_E_STATA_TROVATA_NESSUNA_MODALITA_AZIONE = "Dati incompleti. Non &egrave; stata trovata nessuna Modalit&agrave; Azione";
	public static final String MESSAGGIO_ERRORE_ESISTE_GIA_UNA_PORTA_APPLICATIVA_CON_NOME_XX = "Esiste gi&agrave; una Porta Applicativa con nome [{0}]";
	public static final String MESSAGGIO_ERRORE_ESISTE_GIA_UNA_PORTA_APPLICATIVA_PER_IL_SERVIZIO_XX_CON_AZIONE_YY_EROGATO_DAL_SOGGETTO_ZZ = "Esiste gi&agrave; una Porta Applicativa per il Servizio [{0}] con Azione [{1}] erogato dal Soggetto{2} [{3}]";
	public static final String MESSAGGIO_ERRORE_ESISTE_GIA_UNA_PORTA_APPLICATIVA_XX_APPARTENENTE_AL_SOGGETTO_YY = "Esiste gi&agrave; una Porta Applicativa [{0}] appartenente al Soggetto [{1}]";


}
