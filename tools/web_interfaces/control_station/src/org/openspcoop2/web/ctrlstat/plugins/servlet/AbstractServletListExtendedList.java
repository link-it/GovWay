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


package org.openspcoop2.web.ctrlstat.plugins.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.plugins.ExtendedList;
import org.openspcoop2.web.ctrlstat.plugins.IExtendedListServlet;
import org.openspcoop2.web.ctrlstat.servlet.ConsoleHelper;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * AbstractServletListExcendedList
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public abstract class AbstractServletListExtendedList extends AbstractServletListUtilities {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);
		
		try {
			ConsoleHelper consoleHelper = this.getConsoleHelper(request, pd, session);

			ControlStationCore consoleCore = this.getConsoleCore();
			
			IExtendedListServlet extendedServlet = this.getExtendedServlet(consoleHelper);
			
			Object object = this.getObject(consoleHelper);
			
			// Preparo il menu
			consoleHelper.makeMenu();

			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			int idLista = this.getIdList();

			ricerca = consoleHelper.checkSearchParameters(idLista, ricerca);

			int limit = ricerca.getPageSize(idLista);
			int offset = ricerca.getIndexIniziale(idLista);
			String search = (org.openspcoop2.core.constants.Costanti.SESSION_ATTRIBUTE_VALUE_RICERCA_UNDEFINED.equals(ricerca.getSearchString(idLista)) ? "" : ricerca.getSearchString(idLista));
			ExtendedList extendedList = extendedServlet.extendedBeanList(TipoOperazione.LIST, consoleHelper, consoleCore,
					object, limit, offset, search);
			ricerca.setNumEntries(idLista,extendedList.getSize());
			
			this.prepareList(TipoOperazione.LIST, consoleHelper, ricerca, object, extendedServlet, extendedList.getExtendedBean(), ControlStationCore.getLog(),
					this.getUrlExtendedFather(consoleHelper));

			// salvo l'oggetto ricerca nella sessione
			ServletUtils.setSearchObjectIntoSession(session, ricerca);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			// Forward control to the specified success URI
		 	return ServletUtils.getStrutsForward(mapping, this.getObjectName(),
		 			ForwardParams.LIST());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					this.getObjectName(), 
					ForwardParams.LIST());
		}  
	}
	
	
}
