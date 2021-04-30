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


package org.openspcoop2.web.ctrlstat.servlet.sa;

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
import org.openspcoop2.core.config.Proprieta;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.id.IDServizioApplicativo;
import org.openspcoop2.core.id.IDSoggetto;
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
 * ServiziApplicativiProprietaDel
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class ServiziApplicativiProprietaDel extends Action {

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

 

		try {
			ServiziApplicativiHelper saHelper = new ServiziApplicativiHelper(request, pd, session);
			// prelevo il flag che mi dice da quale pagina ho acceduto la sezione
			Integer parentSA = ServletUtils.getIntegerAttributeFromSession(ServiziApplicativiCostanti.ATTRIBUTO_SERVIZI_APPLICATIVI_PARENT, session);
			if(parentSA == null) parentSA = ServiziApplicativiCostanti.ATTRIBUTO_SERVIZI_APPLICATIVI_PARENT_NONE;
			Boolean useIdSogg = parentSA == ServiziApplicativiCostanti.ATTRIBUTO_SERVIZI_APPLICATIVI_PARENT_SOGGETTO;
			
			parentSA = useIdSogg ? ServiziApplicativiCostanti.ATTRIBUTO_SERVIZI_APPLICATIVI_PARENT_SOGGETTO : ServiziApplicativiCostanti.ATTRIBUTO_SERVIZI_APPLICATIVI_PARENT_NONE;
			
			String id = saHelper.getParameter(ServiziApplicativiCostanti.PARAMETRO_SERVIZI_APPLICATIVI_ID);
			int idServizioApplicativo = Integer.parseInt(id);
			String objToRemove = saHelper.getParameter(Costanti.PARAMETER_NAME_OBJECTS_FOR_REMOVE);
			ArrayList<String> idsToRemove = Utilities.parseIdsToRemove(objToRemove);
			
			ServiziApplicativiCore saCore = new ServiziApplicativiCore();
			
			ServizioApplicativo sa = saCore.getServizioApplicativo(idServizioApplicativo);
			String oldNome = sa.getNome();
			IDSoggetto oldIdSoggetto = new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario());
			IDServizioApplicativo oldIdServizioApplicativo = new IDServizioApplicativo();
			oldIdServizioApplicativo.setIdSoggettoProprietario(oldIdSoggetto);
			oldIdServizioApplicativo.setNome(oldNome);

			String nome = "";

			for (int i = 0; i < idsToRemove.size(); i++) {

				// DataElement de = (DataElement) ((Vector<?>) pdold.getDati()
				// .elementAt(idToRemove[i])).elementAt(0);
				// nome = de.getValue();
				nome = idsToRemove.get(i);

				for (int j = 0; j < sa.sizeProprietaList(); j++) {
					Proprieta ssp = sa.getProprieta(j);
					if (nome.equals(ssp.getNome())) {
						sa.removeProprieta(j);
						break;
					}
				}
			}

			String userLogin = ServletUtils.getUserLoginFromSession(session);
			
			saCore.performUpdateOperation(userLogin, saHelper.smista(), sa);

			// Preparo il menu
			saHelper.makeMenu();

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			int idLista = Liste.SERVIZI_APPLICATIVI_PROP;

			ricerca = saHelper.checkSearchParameters(idLista, ricerca);

			List<Proprieta> lista = saCore.serviziApplicativiProprietaList(Integer.parseInt(id), ricerca);

			saHelper.prepareServiziApplicativiProprietaList(sa, ricerca, lista);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForward (mapping, ServiziApplicativiCostanti.OBJECT_NAME_SERVIZI_APPLICATIVI_PROPRIETA, 
					ForwardParams.DEL());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					ServiziApplicativiCostanti.OBJECT_NAME_SERVIZI_APPLICATIVI_PROPRIETA,
					ForwardParams.DEL());
		}  
	}
}