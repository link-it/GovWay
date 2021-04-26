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


package org.openspcoop2.web.ctrlstat.servlet.soggetti;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.registry.Proprieta;
import org.openspcoop2.core.registry.Soggetto;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.dao.SoggettoCtrlStat;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * SoggettiProprietaChange
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class SoggettiProprietaChange extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);
		
		try {
			SoggettiHelper soggettiHelper = new SoggettiHelper(request, pd, session);
			
			String id = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_ID);
			int idSogg = Integer.parseInt(id);
			String nomeprov = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_NOME);
			String tipoprov = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_TIPO);
			
			String protocollo = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_PROTOCOLLO);

			String userLogin = ServletUtils.getUserLoginFromSession(session);
			String nome = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTI_PROP_NOME);
			String valore = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTI_PROP_VALORE);
			
			// Preparo il menu
			soggettiHelper.makeMenu();

			SoggettiCore soggettiCore = new SoggettiCore();
			
			Soggetto soggettoRegistry = null;
			org.openspcoop2.core.config.Soggetto soggettoConfig = null;
			
			if(soggettiCore.isRegistroServiziLocale()){
				soggettoRegistry = soggettiCore.getSoggettoRegistro(idSogg);// core.getSoggettoRegistro(new
				// IDSoggetto(tipoprov,nomeprov));
			}

			soggettoConfig = soggettiCore.getSoggetto(idSogg);// core.getSoggetto(new
			// IDSoggetto(tipoprov,nomeprov));
			
			if(soggettiCore.isRegistroServiziLocale()){
				nomeprov = soggettoRegistry.getNome();
				tipoprov = soggettoRegistry.getTipo();
			}
			else{
				nomeprov = soggettoConfig.getNome();
				tipoprov = soggettoConfig.getTipo();
			}

			protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(tipoprov);

			List<Parameter> parametersServletSoggettoChange = new ArrayList<Parameter>();
			Parameter pIdSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_ID, id);
			Parameter pNomeSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_NOME, nomeprov);
			Parameter pTipoSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_TIPO, tipoprov);
			parametersServletSoggettoChange.add(pIdSoggetto);
			parametersServletSoggettoChange.add(pNomeSoggetto);
			parametersServletSoggettoChange.add(pTipoSoggetto);
			
			List<Parameter> lstParam = new ArrayList<Parameter>();
			lstParam.add(new Parameter(SoggettiCostanti.LABEL_SOGGETTI, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST));
			lstParam.add(new Parameter(soggettiHelper.getLabelNomeSoggetto(protocollo, soggettoRegistry.getTipo() , soggettoRegistry.getNome()),
							SoggettiCostanti.SERVLET_NAME_SOGGETTI_CHANGE, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])));
			if(soggettoRegistry!=null && soggettoRegistry.sizeProprietaList()>1) {
				lstParam.add(new Parameter(SoggettiCostanti.LABEL_PARAMETRO_SOGGETTI_PROPRIETA,
							SoggettiCostanti.SERVLET_NAME_SOGGETTI_PROPRIETA_LIST, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])));
			}
			
			lstParam.add(new Parameter(nome, null));

			// Se valore = null, devo visualizzare la pagina per la
			// modifica dati
			if (soggettiHelper.isEditModeInProgress()) {
				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, lstParam);

				for (int i = 0; i < soggettoRegistry.sizeProprietaList(); i++) {
					Proprieta ssp = soggettoRegistry.getProprieta(i);
					if (nome.equals(ssp.getNome())) {
						if(ssp.getValore()!=null){
							valore = ssp.getValore().toString();
						}
						break;
					}
				}

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = soggettiHelper.addSoggettoHiddenToDati(dati, id,nomeprov, tipoprov);
				
				dati = soggettiHelper.addProprietaToDati(TipoOperazione.CHANGE, soggettiHelper.getSize(), nome, valore, dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_PROPRIETA,
						ForwardParams.CHANGE());
			}

			// Controlli sui campi immessi
			boolean isOk = soggettiHelper.soggettiProprietaCheckData(TipoOperazione.CHANGE);
			if (!isOk) {
				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, lstParam);

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				dati = soggettiHelper.addSoggettoHiddenToDati(dati, id,nomeprov, tipoprov);
				
				dati = soggettiHelper.addProprietaToDati(TipoOperazione.CHANGE, soggettiHelper.getSize(), nome, valore,dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
 
				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_PROPRIETA, 
						ForwardParams.CHANGE());
			}

			// Modifico i dati della property 
			for (int i = 0; i < soggettoRegistry.sizeProprietaList(); i++) {
				Proprieta ssp = soggettoRegistry.getProprieta(i);
				if (nome.equals(ssp.getNome())) {
					soggettoRegistry.removeProprieta(i);
					break;
				}
			}
			Proprieta ssp = new Proprieta();
			ssp.setNome(nome);
			ssp.setValore(valore);
			soggettoRegistry.addProprieta(ssp);

			SoggettoCtrlStat sog = new SoggettoCtrlStat(soggettoRegistry, soggettoConfig);
			sog.setOldNomeForUpdate(nomeprov);
			sog.setOldTipoForUpdate(tipoprov);

			// eseguo l'aggiornamento
			List<Object> listOggettiDaAggiornare = SoggettiUtilities.getOggettiDaAggiornare(soggettiCore, nomeprov, nomeprov, tipoprov, tipoprov, sog);
			soggettiCore.performUpdateOperation(userLogin, soggettiHelper.smista(), listOggettiDaAggiornare.toArray());

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			int idLista = Liste.SOGGETTI_PROP;

			ricerca = soggettiHelper.checkSearchParameters(idLista, ricerca);

			List<Proprieta> lista = soggettiCore.soggettiProprietaList(Integer.parseInt(id), ricerca);

			soggettiHelper.prepareSoggettiProprietaList(soggettoRegistry, id, ricerca, lista);

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_PROPRIETA, 
					ForwardParams.CHANGE());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					SoggettiCostanti.OBJECT_NAME_SOGGETTI_PROPRIETA,
					ForwardParams.CHANGE());
		}  
	}
}
