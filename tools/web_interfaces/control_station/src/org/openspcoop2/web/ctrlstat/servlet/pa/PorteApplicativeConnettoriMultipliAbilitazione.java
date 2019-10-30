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


package org.openspcoop2.web.ctrlstat.servlet.pa;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaServizioApplicativo;
import org.openspcoop2.core.config.PortaApplicativaServizioApplicativoConnettore;
import org.openspcoop2.core.config.constants.StatoFunzionalita;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.MessageType;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.ServletUtils;

/**
 * PorteApplicativeConnettoriMultipliAbilitazione
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class PorteApplicativeConnettoriMultipliAbilitazione extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		// prelevo il flag che mi dice da quale pagina ho acceduto la sezione delle porte delegate
		Integer parentPA = ServletUtils.getIntegerAttributeFromSession(PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT, session);
		if(parentPA == null) parentPA = PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE;

		try {
			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String changeAbilitato = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ABILITA);
			String nomePorta = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_NOME_PORTA);
			String idPorta = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			String nomeSAConnettore = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_CONNETTORI_MULTIPLI_NOME_SA);
			
			String idConnTab = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_ID_CONN_TAB);
			if(StringUtils.isNotEmpty(idConnTab)) {
				ServletUtils.setObjectIntoSession(session, idConnTab, CostantiControlStation.PARAMETRO_ID_CONN_TAB);
			}
			
			String fromAPIPageInfo = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_API_PAGE_INFO);
			boolean fromApi = Costanti.CHECK_BOX_ENABLED_TRUE.equalsIgnoreCase(fromAPIPageInfo);
			
			String actionConferma = porteApplicativeHelper.getParameter(Costanti.PARAMETRO_ACTION_CONFIRM);
			
			// check su oldNomePD
			PageData pdOld =  ServletUtils.getPageDataFromSession(session);
			String oldNomePA = pdOld.getHidden(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_OLD_NOME_PA);
			oldNomePA = (((oldNomePA != null) && !oldNomePA.equals("")) ? oldNomePA : nomePorta);
			
			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();

			// Prendo la porta applicativa
			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(Integer.parseInt(idPorta));
			
			PortaApplicativaServizioApplicativo oldPaSA = null;
			for (PortaApplicativaServizioApplicativo paSATmp : pa.getServizioApplicativoList()) {
				if(paSATmp.getNome().equals(nomeSAConnettore)) {
					oldPaSA = paSATmp;					
				}
			}
			
			// in progress segnalo l'azione che si sta effettuando
			if(actionConferma == null) {
				String messaggio = porteApplicativeHelper.getMessaggioConfermaModificaRegolaStatoConnettoreMultiplo(fromApi, oldPaSA, ServletUtils.isCheckBoxEnabled(changeAbilitato), true,true);

				pd.setMessage(messaggio, MessageType.CONFIRM);
				
				String[][] bottoni = { 
						{ Costanti.LABEL_MONITOR_BUTTON_ANNULLA, 
							Costanti.LABEL_MONITOR_BUTTON_ANNULLA_CONFERMA_PREFIX +
							Costanti.LABEL_MONITOR_BUTTON_ANNULLA_CONFERMA_SUFFIX
							
						},
						{ Costanti.LABEL_MONITOR_BUTTON_CONFERMA,
							Costanti.LABEL_MONITOR_BUTTON_ESEGUI_OPERAZIONE_CONFERMA_PREFIX +
							Costanti.LABEL_MONITOR_BUTTON_ESEGUI_OPERAZIONE_CONFERMA_SUFFIX }};

				pd.setBottoni(bottoni );
			} 

			// se ho confermato effettuo la modifica altrimenti torno direttamente alla lista
			if(actionConferma != null && actionConferma.equals(Costanti.PARAMETRO_ACTION_CONFIRM_VALUE_OK)) {
				// Prendo la porta applicativa
				pa = porteApplicativeCore.getPortaApplicativa(Integer.parseInt(idPorta));
				
				PortaApplicativaServizioApplicativo paSA = null;
				for (PortaApplicativaServizioApplicativo paSATmp : pa.getServizioApplicativoList()) {
					if(paSATmp.getNome().equals(nomeSAConnettore)) {
						paSA = paSATmp;					
					}
				}
				
				PortaApplicativaServizioApplicativoConnettore datiConnettore = paSA.getDatiConnettore();
				
				if(datiConnettore == null) { // succede solo se e' la prima volta che modifico la configurazione di default
					datiConnettore = new PortaApplicativaServizioApplicativoConnettore();
					datiConnettore.setNome(CostantiControlStation.LABEL_DEFAULT);
				}
				
				paSA.setDatiConnettore(datiConnettore);
				
//				// Modifico i dati della porta applicativa nel db
//				pa.setNome(nomePorta);
//				IDPortaApplicativa oldIDPortaApplicativaForUpdate = new IDPortaApplicativa();
//				oldIDPortaApplicativaForUpdate.setNome(oldNomePA);
//				pa.setOldIDPortaApplicativaForUpdate(oldIDPortaApplicativaForUpdate);
				
				 // cambio solo la modalita'
	            if(ServletUtils.isCheckBoxEnabled(changeAbilitato)) {
	            	datiConnettore.setStato(StatoFunzionalita.ABILITATO);
	            }
	            else{
	            	datiConnettore.setStato(StatoFunzionalita.DISABILITATO);
	            }
				
				String userLogin = ServletUtils.getUserLoginFromSession(session);
	
				porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), pa);
			}
			
			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
	
			int idLista = Liste.PORTE_APPLICATIVE_CONNETTORI_MULTIPLI;
	
			ricerca = porteApplicativeHelper.checkSearchParameters(idLista, ricerca);
	
			PortaApplicativa portaApplicativa = porteApplicativeCore.getPortaApplicativa(Integer.parseInt(idPorta));
			
			List<PortaApplicativaServizioApplicativo> lista = portaApplicativa.getServizioApplicativoList();
			
			// filtro
	
			porteApplicativeHelper.preparePorteAppConnettoriMultipliList(nomePorta, ricerca, lista);
	
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONNETTORI_MULTIPLI_ABILITAZIONE, ForwardParams.OTHER(""));
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONNETTORI_MULTIPLI_ABILITAZIONE,
					ForwardParams.OTHER(""));

		} 
	}
}
