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
package org.openspcoop2.web.ctrlstat.servlet.pd;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.config.ValidazioneContenutiApplicativiRichiesta;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.core.Utilities;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.ServletUtils;

/**
 * PorteDelegateValidazioneContenutiRichiestaDel
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class PorteDelegateValidazioneContenutiRichiestaDel extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Salvo il vecchio PageData
		// PageData pdold = (PageData) session.getAttribute("PageData");

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		String userLogin = ServletUtils.getUserLoginFromSession(session);	

		try {
			PorteDelegateHelper porteDelegateHelper = new PorteDelegateHelper(request, pd, session);
			
			String idPorta = porteDelegateHelper.getParameter(PorteDelegateCostanti.PARAMETRO_PORTE_DELEGATE_ID);
			// String idsogg = porteDelegateHelper.getParameter("idsogg");
			// int soggInt = Integer.parseInt(idsogg);

			PorteDelegateCore porteDelegateCore = new PorteDelegateCore();

			// Preparo il menu
			porteDelegateHelper.makeMenu();

			String objToRemove =porteDelegateHelper.getParameter(Costanti.PARAMETER_NAME_OBJECTS_FOR_REMOVE); 
			ArrayList<String> idsToRemove = Utilities.parseIdsToRemove(objToRemove);

			Long idRegola = null;

			// Prendo l'accesso registro
			PortaDelegata portaDelegata = porteDelegateCore.getPortaDelegata(Long.parseLong(idPorta));

			for (int i = 0; i < idsToRemove.size(); i++) {

				idRegola = Long.parseLong(idsToRemove.get(i));

				for (int j = 0; j < portaDelegata.getValidazioneContenutiApplicativi().sizeRichiestaList(); j++) {
					ValidazioneContenutiApplicativiRichiesta regola = portaDelegata.getValidazioneContenutiApplicativi().getRichiesta(j);
					if (regola.getId().longValue() == idRegola.longValue()) {
						portaDelegata.getValidazioneContenutiApplicativi().removeRichiesta(j);
						break;
					}
				}
			}

			porteDelegateCore.performUpdateOperation(userLogin, porteDelegateHelper.smista(), portaDelegata);

			// Preparo il menu
			porteDelegateHelper.makeMenu();
			
			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
			
			int idLista = Liste.PORTE_DELEGATE_VALIDAZIONE_CONTENUTI_RICHIESTA; 
			
			ricerca = porteDelegateHelper.checkSearchParameters(idLista, ricerca);
			
			List<ValidazioneContenutiApplicativiRichiesta> lista = porteDelegateCore.listaRichiesteValidazioneContenuti(Long.parseLong(idPorta), ricerca);
			
			porteDelegateHelper.preparePorteDelegateValidazioneContenutiRichiestaList(ricerca, lista);  
						
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForward (mapping, 
					PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_VALIDAZIONE_CONTENUTI_RICHIESTA,
					ForwardParams.DEL());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteDelegateCostanti.OBJECT_NAME_PORTE_DELEGATE_VALIDAZIONE_CONTENUTI_RICHIESTA, ForwardParams.DEL());
		} 
	}

}
