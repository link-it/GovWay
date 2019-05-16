/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import org.openspcoop2.core.config.TrasformazioneRegola;
import org.openspcoop2.core.config.TrasformazioneRegolaApplicabilitaRichiesta;
import org.openspcoop2.core.config.TrasformazioneRegolaRichiesta;
import org.openspcoop2.core.config.Trasformazioni;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.mapping.MappingErogazionePortaApplicativa;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.core.registry.beans.AccordoServizioParteComuneSintetico;
import org.openspcoop2.core.registry.driver.IDServizioFactory;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.apc.AccordiServizioParteComuneCore;
import org.openspcoop2.web.ctrlstat.servlet.aps.AccordiServizioParteSpecificaCore;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * PorteApplicativeTrasformazioniAdd
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class PorteApplicativeTrasformazioniAdd extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		String userLogin = ServletUtils.getUserLoginFromSession(session);	
		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		Integer parentPA = ServletUtils.getIntegerAttributeFromSession(PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT, session);
		if(parentPA == null) parentPA = PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE;

		try {
			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String idPorta = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			int idInt = Integer.parseInt(idPorta);
			String idsogg = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO);
			String idAsps = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS);
			if(idAsps == null) 
				idAsps = "";
			
			
			String nome = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_NOME);
			String azioniAllTmp = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_APPLICABILITA_AZIONI_ALL);
			boolean azioniAll = azioniAllTmp==null || "".equals(azioniAllTmp) || CostantiControlStation.PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_APPLICABILITA_AZIONI_ALL_VALUE_TRUE.equals(azioniAllTmp);
			String [] azioni = porteApplicativeHelper.getParameterValues(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_APPLICABILITA_AZIONI);
			String pattern = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_APPLICABILITA_PATTERN);
			String contentType = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_APPLICABILITA_CT);
			
			

			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			AccordiServizioParteSpecificaCore apsCore = new AccordiServizioParteSpecificaCore(porteApplicativeCore);
			AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore(porteApplicativeCore);
			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			// Prendo nome della porta applicativa
			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(idInt);
			String nomePorta = pa.getNome();
			
			
			MappingErogazionePortaApplicativa mappingAssociatoPorta = porteApplicativeCore.getMappingErogazionePortaApplicativa(pa);
			
			String[] azioniDisponibiliList = null;
			String[] azioniDisponibiliLabelList = null;
			
			
			
			
			List<String> azioniAssociatePorta = new ArrayList<>();
			if(pa.getAzione() != null && pa.getAzione().getAzioneDelegataList() != null)
				azioniAssociatePorta.addAll(pa.getAzione().getAzioneDelegataList());

			AccordoServizioParteSpecifica asps = apsCore.getAccordoServizioParteSpecifica(Integer.parseInt(idAsps));
			AccordoServizioParteComuneSintetico apc = apcCore.getAccordoServizioSintetico(asps.getIdAccordo()); 
			Map<String,String> azioniAccordo = porteApplicativeCore.getAzioniConLabel(asps, apc, false, true, new ArrayList<String>());
			
			if(azioniAccordo!=null && azioniAccordo.size()>0) {
				// porte ridefinite
				if(!mappingAssociatoPorta.isDefault()) {
					
					azioniDisponibiliList = new String[azioniAssociatePorta.size()];
					azioniDisponibiliLabelList = new String[azioniAssociatePorta.size()];
					int i = 0;
					for (String string : azioniAccordo.keySet()) {
						if(azioniAssociatePorta.contains(string)) {
							azioniDisponibiliList[i] = string;
							azioniDisponibiliLabelList[i] = azioniAccordo.get(string);
							i++;
						}
					}
				} else { // elenco azioni non associate a nessun mapping
					IDServizio idServizio2 = IDServizioFactory.getInstance().getIDServizioFromAccordo(asps); 
					List<MappingErogazionePortaApplicativa> listaMappingErogazione = apsCore.mappingServiziPorteAppList(idServizio2,asps.getId(), null);
					List<String> azioniOccupate = new ArrayList<>();
					int listaMappingErogazioneSize = listaMappingErogazione != null ? listaMappingErogazione.size() : 0;
					if(listaMappingErogazioneSize > 0) {
						for (int i = 0; i < listaMappingErogazione.size(); i++) {
							MappingErogazionePortaApplicativa mappingErogazionePortaApplicativa = listaMappingErogazione.get(i);
							// colleziono le azioni gia' configurate
							PortaApplicativa portaApplicativa = porteApplicativeCore.getPortaApplicativa(mappingErogazionePortaApplicativa.getIdPortaApplicativa());
							if(portaApplicativa.getAzione() != null && portaApplicativa.getAzione().getAzioneDelegataList() != null)
								azioniOccupate.addAll(portaApplicativa.getAzione().getAzioneDelegataList());
						}
					}
					Map<String,String> azioniAccordoDisponibili = porteApplicativeCore.getAzioniConLabel(asps, apc, false, true, azioniOccupate);
					
					azioniDisponibiliList = new String[azioniAccordoDisponibili.size()];
					azioniDisponibiliLabelList = new String[azioniAccordoDisponibili.size()];
					int i = 0;
					for (String string : azioniAccordoDisponibili.keySet()) {
						azioniDisponibiliList[i] = string;
						azioniDisponibiliLabelList[i] = azioniAccordoDisponibili.get(string);
						i++;
					}
				}
			}
			

			List<Parameter> lstParam = porteApplicativeHelper.getTitoloPA(parentPA, idsogg, idAsps);

			String labelPerPorta = null;
			if(parentPA!=null && (parentPA.intValue() == PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_CONFIGURAZIONE)) {
				labelPerPorta = porteApplicativeCore.getLabelRegolaMappingErogazionePortaApplicativa(
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_DI,
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI,
						pa);
			}
			else {
				labelPerPorta = PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_DI+nomePorta;
			}

			lstParam.add(new Parameter(labelPerPorta,
					PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_LIST,
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, idPorta),
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO, idsogg),
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS, idAsps)
					));
			lstParam.add(ServletUtils.getParameterAggiungi());

			ServletUtils.setPageDataTitle(pd, lstParam);

			// Se nomehid = null, devo visualizzare la pagina per l'inserimento
			// dati
			if (porteApplicativeHelper.isEditModeInProgress()) {
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				dati = porteApplicativeHelper.addTrasformazioneToDatiOpAdd(dati, pa, nome, azioniAll, azioniDisponibiliList, azioniDisponibiliLabelList, azioni, pattern, contentType,
						apc.getServiceBinding(),false);
				
				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.ADD, idPorta, idsogg, idPorta,idAsps,  dati);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI,	ForwardParams.ADD());
			}
			
			// quando un parametro viene inviato come vuoto, sul db viene messo null, gestisco il caso
			String azioniAsString = azioni != null ? StringUtils.join(Arrays.asList(azioni), ",") : "";
			String patternDBCheck = StringUtils.isNotEmpty(pattern) ? pattern : null;
			String contentTypeDBCheck = StringUtils.isNotEmpty(contentType) ? contentType : null;
			String azioniDBCheck = StringUtils.isNotEmpty(azioniAsString) ? azioniAsString : null;
			TrasformazioneRegola trasformazioneDBCheck_criteri = porteApplicativeCore.getTrasformazione(Long.parseLong(idPorta), azioniDBCheck, patternDBCheck, contentTypeDBCheck, 
					null, null, true);
			TrasformazioneRegola trasformazioneDBCheck_nome = porteApplicativeCore.getTrasformazione(Long.parseLong(idPorta), nome);
			
			boolean isOk = porteApplicativeHelper.trasformazioniCheckData(TipoOperazione.ADD, Long.parseLong(idPorta), nome, trasformazioneDBCheck_criteri, trasformazioneDBCheck_nome, null);
			if (!isOk) {

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				dati = porteApplicativeHelper.addTrasformazioneToDatiOpAdd(dati, pa, nome, azioniAll, azioniDisponibiliList, azioniDisponibiliLabelList, azioni, pattern, contentType,
						apc.getServiceBinding(),false);
				
				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.ADD, idPorta, idsogg, idPorta,idAsps,  dati);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI,	ForwardParams.ADD());
			}
			
			// salvataggio nuova regola
			
			if(pa.getTrasformazioni() == null) {
				pa.setTrasformazioni(new Trasformazioni());
			}
			
			// calcolo prossima posizione
			int posizione = 1;
			for (TrasformazioneRegola check : pa.getTrasformazioni().getRegolaList()) {
				if(check.getPosizione()>=posizione) {
					posizione = check.getPosizione()+1;
				}
			}
			
			TrasformazioneRegola regola = new TrasformazioneRegola();
			regola.setPosizione(posizione);
			regola.setNome(nome);
			TrasformazioneRegolaApplicabilitaRichiesta applicabilita = new TrasformazioneRegolaApplicabilitaRichiesta();
			
			applicabilita.setPattern(pattern);
			if(contentType != null) {
				applicabilita.getContentTypeList().addAll(Arrays.asList(contentType.split(",")));
			}
			
			if(azioni != null && azioni.length > 0) {
				for (String azione : azioni) {
					applicabilita.addAzione(azione);
				}
			}
			
			regola.setApplicabilita(applicabilita);
			regola.setRichiesta(new TrasformazioneRegolaRichiesta());
			pa.getTrasformazioni().addRegola(regola);
			
			porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), pa);
			
			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
			
			int idLista = Liste.PORTE_APPLICATIVE_TRASFORMAZIONI; 
			
			ricerca = porteApplicativeHelper.checkSearchParameters(idLista, ricerca);
			
			List<TrasformazioneRegola> lista = porteApplicativeCore.porteAppTrasformazioniList(Long.parseLong(idPorta), ricerca);
			
			porteApplicativeHelper.preparePorteAppTrasformazioniRegolaList(nomePorta, ricerca, lista);
						
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI, ForwardParams.ADD());


		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI, ForwardParams.ADD());
		} 
	}
}