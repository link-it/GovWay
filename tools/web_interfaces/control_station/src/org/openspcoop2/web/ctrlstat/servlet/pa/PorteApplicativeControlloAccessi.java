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
package org.openspcoop2.web.ctrlstat.servlet.pa;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
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
import org.openspcoop2.core.config.AutorizzazioneRuoli;
import org.openspcoop2.core.config.AutorizzazioneScope;
import org.openspcoop2.core.config.GenericProperties;
import org.openspcoop2.core.config.GestioneToken;
import org.openspcoop2.core.config.GestioneTokenAutenticazione;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneServiziApplicativi;
import org.openspcoop2.core.config.PortaApplicativaAutorizzazioneServizioApplicativo;
import org.openspcoop2.core.config.Proprieta;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.constants.RuoloTipoMatch;
import org.openspcoop2.core.config.constants.ScopeTipoMatch;
import org.openspcoop2.core.config.constants.StatoFunzionalita;
import org.openspcoop2.core.config.constants.StatoFunzionalitaConWarning;
import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.config.constants.TipoAutenticazionePrincipal;
import org.openspcoop2.core.config.constants.TipoAutorizzazione;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.core.registry.beans.AccordoServizioParteComuneSintetico;
import org.openspcoop2.core.registry.constants.RuoloTipologia;
import org.openspcoop2.core.registry.driver.IDAccordoFactory;
import org.openspcoop2.core.registry.driver.IDServizioFactory;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.pdd.core.autorizzazione.CostantiAutorizzazione;
import org.openspcoop2.utils.properties.PropertiesUtilities;
import org.openspcoop2.web.ctrlstat.core.AutorizzazioneUtilities;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.apc.AccordiServizioParteComuneCore;
import org.openspcoop2.web.ctrlstat.servlet.aps.AccordiServizioParteSpecificaCore;
import org.openspcoop2.web.ctrlstat.servlet.config.ConfigurazioneCore;
import org.openspcoop2.web.ctrlstat.servlet.config.ConfigurazioneCostanti;
import org.openspcoop2.web.ctrlstat.servlet.sa.ServiziApplicativiCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;
import org.openspcoop2.web.lib.mvc.BinaryParameter;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/***
 * 
 * PorteApplicativeControlloAccessi
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PorteApplicativeControlloAccessi extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		boolean isPortaDelegata = false;

		// prelevo il flag che mi dice da quale pagina ho acceduto la sezione delle porte delegate
		Integer parentPA = ServletUtils.getIntegerAttributeFromSession(PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT, session);
		if(parentPA == null) parentPA = PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_NONE;

		try {
			Boolean contaListe = ServletUtils.getContaListeFromSession(session);
			Boolean confPers = (Boolean) session.getAttribute(CostantiControlStation.SESSION_PARAMETRO_GESTIONE_CONFIGURAZIONI_PERSONALIZZATE);

			PorteApplicativeHelper porteApplicativeHelper = new PorteApplicativeHelper(request, pd, session);
			String id = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID);
			int idInt = Integer.parseInt(id);
			String idsogg = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO);
			int soggInt = Integer.parseInt(idsogg);
			String idAsps = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS);
			if(idAsps == null) 
				idAsps = "";

			String autenticazione = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE );
			String autenticazioneOpzionale = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_OPZIONALE );
			String autenticazionePrincipalTipo = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_PRINCIPAL_TIPO);
			TipoAutenticazionePrincipal autenticazionePrincipal = TipoAutenticazionePrincipal.toEnumConstant(autenticazionePrincipalTipo, false);
			List<String> autenticazioneParametroList = porteApplicativeHelper.convertFromDataElementValue_parametroAutenticazioneList(autenticazione, autenticazionePrincipal);
			String autenticazioneCustom = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM );
			String autorizzazione = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE);
			String autorizzazioneCustom = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_CUSTOM);

			String autorizzazioneAutenticati = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_AUTENTICAZIONE);
			String autorizzazioneRuoli = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_RUOLI);
			String autorizzazioneRuoliTipologia = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_RUOLO_TIPOLOGIA);
			String ruoloMatch = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_RUOLO_MATCH);

			String autorizzazioneContenuti = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI);
			String autorizzazioneContenutiStato = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI_STATO);
			String autorizzazioneContenutiProperties = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI_PROPERTIES);

			String applicaModificaS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_APPLICA_MODIFICA);
			boolean applicaModifica = ServletUtils.isCheckBoxEnabled(applicaModificaS);

			String gestioneToken = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN);
			String gestioneTokenPolicy = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_POLICY);
			String gestioneTokenOpzionale = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_OPZIONALE);
			String gestioneTokenValidazioneInput = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_VALIDAZIONE_INPUT);
			String gestioneTokenIntrospection = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_INTROSPECTION);
			String gestioneTokenUserInfo = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_USERINFO);
			String gestioneTokenTokenForward = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_GESTIONE_TOKEN_TOKEN_FORWARD);

			String autenticazioneTokenIssuer = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_TOKEN_ISSUER);
			String autenticazioneTokenClientId = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_TOKEN_CLIENT_ID);
			String autenticazioneTokenSubject = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_TOKEN_SUBJECT);
			String autenticazioneTokenUsername = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_TOKEN_USERNAME);
			String autenticazioneTokenEMail = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE_TOKEN_MAIL);

			String autorizzazione_token = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_TOKEN);
			String autorizzazione_tokenOptions = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_TOKEN_OPTIONS);
			String autorizzazioneScope = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_SCOPE);
			String autorizzazioneScopeMatch = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_SCOPE_MATCH);

			BinaryParameter allegatoXacmlPolicy = porteApplicativeHelper.getBinaryParameter(CostantiControlStation.PARAMETRO_DOCUMENTO_SICUREZZA_XACML_POLICY);

			String idTab = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_ID_TAB);
			if(!porteApplicativeHelper.isModalitaCompleta() && StringUtils.isNotEmpty(idTab)) {
				ServletUtils.setObjectIntoSession(session, idTab, CostantiControlStation.PARAMETRO_ID_TAB);
			}
			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			// Prendo il nome della porta
			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			AccordiServizioParteSpecificaCore apsCore = new AccordiServizioParteSpecificaCore(porteApplicativeCore);
			SoggettiCore soggettiCore = new SoggettiCore(porteApplicativeCore);
			ConfigurazioneCore confCore = new ConfigurazioneCore(porteApplicativeCore);
			AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore(porteApplicativeCore);
			ServiziApplicativiCore saCore = new ServiziApplicativiCore(porteApplicativeCore);

			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(idInt);
			String idporta = pa.getNome();

			List<String> ruoli = new ArrayList<>();
			if(pa!=null && pa.getRuoli()!=null && pa.getRuoli().sizeRuoloList()>0){
				for (int i = 0; i < pa.getRuoli().sizeRuoloList(); i++) {
					ruoli.add(pa.getRuoli().getRuolo(i).getNome());
				}
			}

			int numRuoli = 0;
			if(pa.getRuoli()!=null){
				numRuoli = pa.getRuoli().sizeRuoloList();
			}
			int numScope = 0;
			if(pa.getScope()!=null){
				numScope = pa.getScope().sizeScopeList();
			}

			int numAutenticazioneCustomPropertiesList = pa.sizeProprietaAutenticazioneList();
			int numAutorizzazioneCustomPropertiesList = pa.sizeProprietaAutorizzazioneList();
			int numAutorizzazioneContenutiCustomPropertiesList = pa.sizeProprietaAutorizzazioneContenutoList();
			String oldAutorizzazioneContenuto = pa.getAutorizzazioneContenuto() ;
			String oldAutorizzazioneContenutoStato = StatoFunzionalita.DISABILITATO.getValue();
			
			boolean old_autenticazione_custom = pa.getAutenticazione() != null && !TipoAutenticazione.getValues().contains(pa.getAutenticazione());
			boolean old_autorizzazione_contenuti_custom = false;
			if(oldAutorizzazioneContenuto != null) {
				if(oldAutorizzazioneContenuto.equals(CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN)) {
					oldAutorizzazioneContenutoStato = StatoFunzionalita.ABILITATO.getValue();
				} else { // custom
					old_autorizzazione_contenuti_custom = true;
					oldAutorizzazioneContenutoStato = CostantiControlStation.VALUE_PARAMETRO_PORTE_CONTROLLO_ACCESSI_AUTORIZZAZIONE_CONTENUTI_STATO_CUSTOM;
				}
			}
			
			IDServizio idServizio = IDServizioFactory.getInstance().getIDServizioFromValues(pa.getServizio().getTipo(), pa.getServizio().getNome(), 
					pa.getTipoSoggettoProprietario(), pa.getNomeSoggettoProprietario(), 
					pa.getServizio().getVersione());
			AccordoServizioParteSpecifica asps = apsCore.getServizio(idServizio,false);
			AccordoServizioParteComuneSintetico aspc = apcCore.getAccordoServizioSintetico(porteApplicativeHelper.getIDAccordoFromUri(asps.getAccordoServizioParteComune()));
			ServiceBinding serviceBinding = porteApplicativeCore.toMessageServiceBinding(aspc.getServiceBinding()); 
			
			Search searchForCount = new Search(true,1);
			porteApplicativeCore.porteAppSoggettoList(idInt, searchForCount);
			int sizeSoggettiPA = searchForCount.getNumEntries(Liste.PORTE_APPLICATIVE_SOGGETTO);

			Search searchForCountSAAutorizzati = new Search(true,1);
			porteApplicativeCore.porteAppServiziApplicativiAutorizzatiList(idInt, searchForCountSAAutorizzati);
			int numErogazioneApplicativiAutenticati = searchForCountSAAutorizzati.getNumEntries(Liste.PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_AUTORIZZATO);

			// Prendo nome, tipo e pdd del soggetto
			String tipoSoggettoProprietario = null;
			if(porteApplicativeCore.isRegistroServiziLocale()){
				org.openspcoop2.core.registry.Soggetto soggetto = soggettiCore.getSoggettoRegistro(soggInt);
				tipoSoggettoProprietario = soggetto.getTipo();
			}
			else{
				org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(soggInt);
				tipoSoggettoProprietario = soggetto.getTipo();
			}

			String protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(tipoSoggettoProprietario);
			boolean isSupportatoAutenticazione = soggettiCore.isSupportatoAutenticazioneSoggetti(protocollo);
			
			boolean forceAutenticato = false; 
			boolean forceHttps = false;
			boolean forceDisableOptional = false;
			if(porteApplicativeHelper.isProfiloModIPA(protocollo)) {
				forceAutenticato = true; // in modI ci vuole sempre autenticazione https sull'erogazione (cambia l'opzionalita' o meno)
				forceHttps = forceAutenticato;
				forceDisableOptional = porteApplicativeHelper.forceHttpsClientProfiloModiPA(IDAccordoFactory.getInstance().getIDAccordoFromUri(asps.getAccordoServizioParteComune()), asps.getPortType());
			}
			
			List<Parameter> lstParam = porteApplicativeHelper.getTitoloPA(parentPA, idsogg, idAsps);

			String labelPerPorta = null;
			if(parentPA!=null && (parentPA.intValue() == PorteApplicativeCostanti.ATTRIBUTO_PORTE_APPLICATIVE_PARENT_CONFIGURAZIONE)) {
				labelPerPorta = porteApplicativeCore.getLabelRegolaMappingErogazionePortaApplicativa(
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_CONTROLLO_ACCESSI_CONFIG_DI,
						PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_CONTROLLO_ACCESSI,
						pa);
			}
			else {
				labelPerPorta = PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_CONTROLLO_ACCESSI_CONFIG_DI+idporta;
			}

			lstParam.add(new Parameter(labelPerPorta,  null));

			// setto la barra del titolo
			ServletUtils.setPageDataTitle(pd, lstParam);


			Parameter[] urlParmsAutorizzazioneAutenticati = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneAutenticatiParam= new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SOGGETTO_LIST, urlParmsAutorizzazioneAutenticati);
			String urlAutorizzazioneAutenticati = urlAutorizzazioneAutenticatiParam.getValue();

			Parameter[] urlParmsAutorizzazioneErogazioneApplicativiAutenticati = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneErogazioneApplicativiAutenticatiParam= new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_AUTORIZZATO_LIST, urlParmsAutorizzazioneErogazioneApplicativiAutenticati);
			String urlAutorizzazioneErogazioneApplicativiAutenticati = urlAutorizzazioneErogazioneApplicativiAutenticatiParam.getValue();

			Parameter[] urlParmsAutorizzazioneRuoli = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneRuoliParam = new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_RUOLI_LIST , urlParmsAutorizzazioneRuoli);
			String urlAutorizzazioneRuoli = urlAutorizzazioneRuoliParam.getValue();

			Parameter[] urlParmsAutorizzazioneScope = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneScopeParam = new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_SCOPE_LIST , urlParmsAutorizzazioneScope); 
			String urlAutorizzazioneScope = urlAutorizzazioneScopeParam.getValue();
			
			Parameter[] urlParmsAutenticazioneCustomProperties = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutenticazioneCustomPropertiesParam = new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AUTENTICAZIONE_CUSTOM_PROPERTIES_LIST , urlParmsAutenticazioneCustomProperties); 
			String urlAutenticazioneCustomProperties = urlAutenticazioneCustomPropertiesParam.getValue();

			Parameter[] urlParmsAutorizzazioneCustomProperties = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneCustomPropertiesParam = new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AUTORIZZAZIONE_CUSTOM_PROPERTIES_LIST , urlParmsAutorizzazioneCustomProperties); 
			String urlAutorizzazioneCustomProperties = urlAutorizzazioneCustomPropertiesParam.getValue();
			
			Parameter[] urlParmsAutorizzazioneContenutiCustomProperties = { 
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID,id)	,
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO,idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS,idAsps) };
			Parameter urlAutorizzazioneContenutiCustomPropertiesParam = new Parameter("", PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI_CUSTOM_PROPERTIES_LIST , urlParmsAutorizzazioneContenutiCustomProperties); 
			String urlAutorizzazioneContenutiCustomPropertiesList = urlAutorizzazioneContenutiCustomPropertiesParam.getValue();

			String servletChiamante = PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI;

			List<GenericProperties> gestorePolicyTokenList = confCore.gestorePolicyTokenList(null, ConfigurazioneCostanti.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_TIPOLOGIA_GESTIONE_POLICY_TOKEN, null);
			String [] policyLabels = new String[gestorePolicyTokenList.size() + 1];
			String [] policyValues = new String[gestorePolicyTokenList.size() + 1];

			policyLabels[0] = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;
			policyValues[0] = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;

			for (int i = 0; i < gestorePolicyTokenList.size(); i++) {
				GenericProperties genericProperties = gestorePolicyTokenList.get(i);
				policyLabels[(i+1)] = genericProperties.getNome();
				policyValues[(i+1)] = genericProperties.getNome();
			}

			// La XACML Policy, se definita nella porta delegata può solo essere cambiata, non annullata.
			if(allegatoXacmlPolicy!=null && allegatoXacmlPolicy.getValue()==null) {
				if(pa.getXacmlPolicy()!=null && !"".equals(pa.getXacmlPolicy())) {
					allegatoXacmlPolicy.setValue(pa.getXacmlPolicy().getBytes());
				}
			}
			
			
			// postback
			String postBackElementName = porteApplicativeHelper.getPostBackElementName();
			if(postBackElementName != null) {
				if(postBackElementName.equals(CostantiControlStation.PARAMETRO_PORTE_AUTENTICAZIONE)) {
					if(autenticazione.equals(CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM)) {
						autenticazioneCustom = "";
					}
				}
				
				if(postBackElementName.equals(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_AUTORIZZAZIONE_CONTENUTI_STATO)) {
					if(autorizzazioneContenutiStato.equals(StatoFunzionalita.DISABILITATO.getValue()) || autorizzazioneContenutiStato.equals(CostantiControlStation.VALUE_PARAMETRO_PORTE_CONTROLLO_ACCESSI_AUTORIZZAZIONE_CONTENUTI_STATO_CUSTOM)) {
						autorizzazioneContenuti = "";
					}
					if(autorizzazioneContenutiStato.equals(StatoFunzionalita.ABILITATO.getValue())) {
						autorizzazioneContenuti = CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN;
					}
				}
			}

			if(	porteApplicativeHelper.isEditModeInProgress() && !applicaModifica){

				if (autenticazione == null) {
					autenticazione = pa.getAutenticazione();
					if (autenticazione != null &&
							!TipoAutenticazione.getValues().contains(autenticazione)) {
						autenticazioneCustom = autenticazione;
						autenticazione = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM;
					}

					autenticazionePrincipal = porteApplicativeCore.getTipoAutenticazionePrincipal(pa.getProprietaAutenticazioneList());
					autenticazioneParametroList = porteApplicativeCore.getParametroAutenticazione(autenticazione, pa.getProprietaAutenticazioneList());
				}
				if(autenticazioneOpzionale==null){
					autenticazioneOpzionale = "";
					if(pa.getAutenticazioneOpzionale()!=null){
						if (pa.getAutenticazioneOpzionale().equals(StatoFunzionalita.ABILITATO)) {
							autenticazioneOpzionale = Costanti.CHECK_BOX_ENABLED;
						}
					}
				}
				if (autorizzazione == null) {
					if (pa.getAutorizzazione() != null &&
							!TipoAutorizzazione.getAllValues().contains(pa.getAutorizzazione())) {
						autorizzazioneCustom = pa.getAutorizzazione();
						autorizzazione = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTORIZZAZIONE_CUSTOM;
					}
					else{
						autorizzazione = AutorizzazioneUtilities.convertToStato(pa.getAutorizzazione());
						if(TipoAutorizzazione.isAuthenticationRequired(pa.getAutorizzazione()))
							autorizzazioneAutenticati = Costanti.CHECK_BOX_ENABLED;
						if(TipoAutorizzazione.isRolesRequired(pa.getAutorizzazione()))
							autorizzazioneRuoli = Costanti.CHECK_BOX_ENABLED;
						autorizzazioneRuoliTipologia = AutorizzazioneUtilities.convertToRuoloTipologia(pa.getAutorizzazione()).getValue();
					}
				}

				if (ruoloMatch == null) {
					if(pa.getRuoli()!=null && pa.getRuoli().getMatch()!=null){
						ruoloMatch = pa.getRuoli().getMatch().getValue();
					}
				}

				if(autorizzazioneContenutiStato==null){
					autorizzazioneContenuti = pa.getAutorizzazioneContenuto();
					
					if(autorizzazioneContenuti == null || "".equals(autorizzazioneContenuti)) {
						autorizzazioneContenutiStato = StatoFunzionalita.DISABILITATO.getValue();
					} else if(autorizzazioneContenuti.equals(CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN)) {
						autorizzazioneContenutiStato = StatoFunzionalita.ABILITATO.getValue();
						List<Proprieta> proprietaAutorizzazioneContenutoList = pa.getProprietaAutorizzazioneContenutoList();
						StringBuilder sb = new StringBuilder();
						for (Proprieta proprieta : proprietaAutorizzazioneContenutoList) {
							if(sb.length() >0)
								sb.append("\n");
							
							sb.append(proprieta.getNome()).append("=").append(proprieta.getValore()); 
						}						
						
						autorizzazioneContenutiProperties = sb.toString();
					} else { // custom
						autorizzazioneContenutiStato = CostantiControlStation.VALUE_PARAMETRO_PORTE_CONTROLLO_ACCESSI_AUTORIZZAZIONE_CONTENUTI_STATO_CUSTOM;
					}
				}

				if(gestioneToken == null) {
					if(pa.getGestioneToken() != null) {
						gestioneTokenPolicy = pa.getGestioneToken().getPolicy();
						if(gestioneTokenPolicy == null) {
							gestioneToken = StatoFunzionalita.DISABILITATO.getValue();
							gestioneTokenPolicy = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;
						} else {
							gestioneToken = StatoFunzionalita.ABILITATO.getValue();
						}

						StatoFunzionalita tokenOpzionale = pa.getGestioneToken().getTokenOpzionale();
						if(tokenOpzionale == null) {
							gestioneTokenOpzionale = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_OPZIONALE;
						}else { 
							gestioneTokenOpzionale = tokenOpzionale.getValue();
						}

						StatoFunzionalitaConWarning validazione = pa.getGestioneToken().getValidazione();
						if(validazione == null) {
							gestioneTokenValidazioneInput = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_VALIDAZIONE_INPUT;
						}else { 
							gestioneTokenValidazioneInput = validazione.getValue();
						}

						StatoFunzionalitaConWarning introspection = pa.getGestioneToken().getIntrospection();
						if(introspection == null) {
							gestioneTokenIntrospection = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_INTROSPECTION;
						}else { 
							gestioneTokenIntrospection = introspection.getValue();
						}

						StatoFunzionalitaConWarning userinfo = pa.getGestioneToken().getUserInfo();
						if(userinfo == null) {
							gestioneTokenUserInfo = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_USER_INFO;
						}else { 
							gestioneTokenUserInfo = userinfo.getValue();
						}

						StatoFunzionalita tokenForward = pa.getGestioneToken().getForward();
						if(tokenForward == null) {
							gestioneTokenTokenForward = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_TOKEN_FORWARD;
						}else { 
							gestioneTokenTokenForward = tokenForward.getValue();
						}

						autorizzazione_tokenOptions = pa.getGestioneToken().getOptions();
						if((autorizzazione_tokenOptions!=null && !"".equals(autorizzazione_tokenOptions))) {
							autorizzazione_token = Costanti.CHECK_BOX_ENABLED;
						}
						else {
							autorizzazione_token = Costanti.CHECK_BOX_DISABLED;
						}

						if(pa.getGestioneToken().getAutenticazione() != null) {

							StatoFunzionalita issuer = pa.getGestioneToken().getAutenticazione().getIssuer();
							if(issuer == null) {
								autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
							}else { 
								autenticazioneTokenIssuer = issuer.getValue();
							}

							StatoFunzionalita clientId = pa.getGestioneToken().getAutenticazione().getClientId();
							if(clientId == null) {
								autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
							}else { 
								autenticazioneTokenClientId = clientId.getValue();
							}

							StatoFunzionalita subject = pa.getGestioneToken().getAutenticazione().getSubject();
							if(subject == null) {
								autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
							}else { 
								autenticazioneTokenSubject = subject.getValue();
							}

							StatoFunzionalita username = pa.getGestioneToken().getAutenticazione().getUsername();
							if(username == null) {
								autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
							}else { 
								autenticazioneTokenUsername = username.getValue();
							}

							StatoFunzionalita mailTmp = pa.getGestioneToken().getAutenticazione().getEmail();
							if(mailTmp == null) {
								autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
							}else { 
								autenticazioneTokenEMail = mailTmp.getValue();
							}

						}
						else {
							autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
							autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
							autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
							autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
							autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
						}
					}
					else {
						gestioneToken = StatoFunzionalita.DISABILITATO.getValue();
						gestioneTokenPolicy = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;
						gestioneTokenOpzionale = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_OPZIONALE;

						gestioneTokenValidazioneInput = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_VALIDAZIONE_INPUT;
						gestioneTokenIntrospection = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_INTROSPECTION;
						gestioneTokenUserInfo = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_USER_INFO;
						gestioneTokenTokenForward = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_TOKEN_FORWARD;

						autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
						autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
						autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
						autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
						autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
					}
				}

				if(autorizzazioneScope == null) {
					if(pa.getScope() != null) {
						autorizzazioneScope =  pa.getScope().getStato().equals(StatoFunzionalita.ABILITATO) ? Costanti.CHECK_BOX_ENABLED : ""; 
					} else {
						autorizzazioneScope = "";
					}
				}

				if(autorizzazioneScopeMatch == null) {
					if(pa.getScope()!=null && pa.getScope().getMatch()!=null){
						autorizzazioneScopeMatch = pa.getScope().getMatch().getValue();
					}
				}

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				porteApplicativeHelper.controlloAccessiGestioneToken(dati, TipoOperazione.OTHER, gestioneToken, policyLabels, policyValues, 
						gestioneTokenPolicy, gestioneTokenOpzionale, 
						gestioneTokenValidazioneInput, gestioneTokenIntrospection, gestioneTokenUserInfo, gestioneTokenTokenForward, pa,protocollo,false);

				porteApplicativeHelper.controlloAccessiAutenticazione(dati, TipoOperazione.OTHER, servletChiamante,pa,protocollo,
						autenticazione, autenticazioneCustom, autenticazioneOpzionale, autenticazionePrincipal, autenticazioneParametroList, confPers, isSupportatoAutenticazione,false,
						gestioneToken, gestioneTokenPolicy, autenticazioneTokenIssuer, autenticazioneTokenClientId, autenticazioneTokenSubject, autenticazioneTokenUsername, autenticazioneTokenEMail,
						old_autenticazione_custom, urlAutenticazioneCustomProperties, numAutenticazioneCustomPropertiesList,
						forceHttps, forceDisableOptional);

				// Tipo operazione = CHANGE per evitare di aggiungere if, questa e' a tutti gli effetti una servlet di CHANGE
				porteApplicativeHelper.controlloAccessiAutorizzazione(dati, TipoOperazione.CHANGE, servletChiamante,pa,protocollo,
						autenticazione, autenticazioneCustom,
						autorizzazione, autorizzazioneCustom, 
						autorizzazioneAutenticati, urlAutorizzazioneAutenticati, sizeSoggettiPA, null, null,
						autorizzazioneRuoli,  urlAutorizzazioneRuoli, numRuoli, null, 
						autorizzazioneRuoliTipologia, ruoloMatch,
						confPers, isSupportatoAutenticazione, contaListe, false, false,autorizzazioneScope,urlAutorizzazioneScope,numScope,null,autorizzazioneScopeMatch,
						gestioneToken, gestioneTokenPolicy, autorizzazione_token, autorizzazione_tokenOptions,allegatoXacmlPolicy,
						urlAutorizzazioneErogazioneApplicativiAutenticati, numErogazioneApplicativiAutenticati,
						urlAutorizzazioneCustomProperties, numAutorizzazioneCustomPropertiesList);

				porteApplicativeHelper.controlloAccessiAutorizzazioneContenuti(dati, TipoOperazione.OTHER, false, autorizzazioneContenutiStato, autorizzazioneContenuti, autorizzazioneContenutiProperties, serviceBinding,
						old_autorizzazione_contenuti_custom, urlAutorizzazioneContenutiCustomPropertiesList, numAutorizzazioneContenutiCustomPropertiesList,
						confPers); 

				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.OTHER,id, idsogg, null,idAsps, dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping,
						PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI, ForwardParams.OTHER(""));
			}

			// Controlli sui campi immessi
			boolean isOk = porteApplicativeHelper.controlloAccessiCheck(TipoOperazione.OTHER, autenticazione, autenticazioneOpzionale, autenticazionePrincipal, autenticazioneParametroList, 
					autorizzazione, autorizzazioneAutenticati, autorizzazioneRuoli, 
					autorizzazioneRuoliTipologia, ruoloMatch, 
					isSupportatoAutenticazione, isPortaDelegata, pa, ruoli,gestioneToken, gestioneTokenPolicy, 
					gestioneTokenValidazioneInput, gestioneTokenIntrospection, gestioneTokenUserInfo, gestioneTokenTokenForward,
					autorizzazione_token,autorizzazione_tokenOptions,
					autorizzazioneScope,autorizzazioneScopeMatch,allegatoXacmlPolicy,
					autorizzazioneContenutiStato, autorizzazioneContenuti, autorizzazioneContenutiProperties,
					protocollo);

			if(isOk) {

				if(porteApplicativeHelper.isProfiloModIPA(protocollo)) {
					
					String statoAutorizzazioneModiPA = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_MODIPA_STATO);
					
					if(StatoFunzionalita.ABILITATO.getValue().equals(statoAutorizzazioneModiPA)) {
					
						String idSoggettoToAdd = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SOGGETTO);
						
						String idSAToAdd = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_AUTORIZZATO);
						
						int sizeSAautorizzati = 0;
						if(pa.getServiziApplicativiAutorizzati()!=null) {
							sizeSAautorizzati = pa.getServiziApplicativiAutorizzati().sizeServizioApplicativoList();
						}
						
						if( 
								sizeSAautorizzati<=0
								&&
								(
										(idSoggettoToAdd==null || "".equals(idSoggettoToAdd))
										||
										(idSAToAdd==null || "".equals(idSAToAdd))
								)
								
								) {
							isOk = false;
							pd.setMessage("Per abilitare l'autorizzazione deve essere registrato almeno un applicativo con configurazione '"+org.openspcoop2.protocol.engine.constants.Costanti.MODIPA_PROTOCOL_LABEL+"' per la sicurezza del messaggio");
						}
					}
					
				}

			}
			
			if (!isOk) {
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				porteApplicativeHelper.controlloAccessiGestioneToken(dati, TipoOperazione.OTHER, gestioneToken, policyLabels, policyValues, 
						gestioneTokenPolicy, gestioneTokenOpzionale, gestioneTokenValidazioneInput, gestioneTokenIntrospection, gestioneTokenUserInfo, gestioneTokenTokenForward, pa,protocollo,false);

				porteApplicativeHelper.controlloAccessiAutenticazione(dati, TipoOperazione.OTHER, servletChiamante,pa,protocollo,
						autenticazione, autenticazioneCustom, autenticazioneOpzionale, autenticazionePrincipal, autenticazioneParametroList, confPers, isSupportatoAutenticazione,false,
						gestioneToken, gestioneTokenPolicy, autenticazioneTokenIssuer, autenticazioneTokenClientId, autenticazioneTokenSubject, autenticazioneTokenUsername, autenticazioneTokenEMail,
						old_autenticazione_custom, urlAutenticazioneCustomProperties, numAutenticazioneCustomPropertiesList,
						forceHttps, forceDisableOptional);
				
				// Tipo operazione = CHANGE per evitare di aggiungere if, questa e' a tutti gli effetti una servlet di CHANGE
				porteApplicativeHelper.controlloAccessiAutorizzazione(dati, TipoOperazione.CHANGE, servletChiamante,pa,protocollo,
						autenticazione, autenticazioneCustom,
						autorizzazione, autorizzazioneCustom, 
						autorizzazioneAutenticati, urlAutorizzazioneAutenticati, sizeSoggettiPA, null, null,
						autorizzazioneRuoli,  urlAutorizzazioneRuoli, numRuoli, null, 
						autorizzazioneRuoliTipologia, ruoloMatch,
						confPers, isSupportatoAutenticazione, contaListe, false, false,
						autorizzazioneScope,urlAutorizzazioneScope,numScope,null,autorizzazioneScopeMatch,
						gestioneToken, gestioneTokenPolicy, autorizzazione_token, autorizzazione_tokenOptions,allegatoXacmlPolicy,
						urlAutorizzazioneErogazioneApplicativiAutenticati, numErogazioneApplicativiAutenticati,
						urlAutorizzazioneCustomProperties, numAutorizzazioneCustomPropertiesList);

				porteApplicativeHelper.controlloAccessiAutorizzazioneContenuti(dati, TipoOperazione.OTHER, false, autorizzazioneContenutiStato, autorizzazioneContenuti, autorizzazioneContenutiProperties, serviceBinding,
						old_autorizzazione_contenuti_custom, urlAutorizzazioneContenutiCustomPropertiesList, numAutorizzazioneContenutiCustomPropertiesList,
						confPers); 

				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.OTHER,id, idsogg, null,idAsps, dati);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping,
						PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI,
						ForwardParams.OTHER(""));
			}


			if (autenticazione == null || !autenticazione.equals(CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM))
				pa.setAutenticazione(autenticazione);
			else {
				pa.setAutenticazione(autenticazioneCustom);
				
				if(!old_autenticazione_custom)
					pa.getProprietaAutenticazioneList().clear();
			}
			if(autenticazioneOpzionale != null){
				if(ServletUtils.isCheckBoxEnabled(autenticazioneOpzionale))
					pa.setAutenticazioneOpzionale(StatoFunzionalita.ABILITATO);
				else 
					pa.setAutenticazioneOpzionale(StatoFunzionalita.DISABILITATO);
			} else 
				pa.setAutenticazioneOpzionale(null);
			
			if (autenticazione == null || !autenticazione.equals(CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM)) {
				pa.getProprietaAutenticazioneList().clear();
				List<Proprieta> proprietaAutenticazione = porteApplicativeCore.convertToAutenticazioneProprieta(autenticazione, autenticazionePrincipal, autenticazioneParametroList);
				if(proprietaAutenticazione!=null && !proprietaAutenticazione.isEmpty()) {
					pa.getProprietaAutenticazioneList().addAll(proprietaAutenticazione);
				}
			}

			if (autorizzazione == null || !autorizzazione.equals(CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTORIZZAZIONE_CUSTOM)) {
				pa.setAutorizzazione(AutorizzazioneUtilities.convertToTipoAutorizzazioneAsString(autorizzazione, 
						ServletUtils.isCheckBoxEnabled(autorizzazioneAutenticati), 
						ServletUtils.isCheckBoxEnabled(autorizzazioneRuoli),
						ServletUtils.isCheckBoxEnabled(autorizzazioneScope),
						autorizzazione_tokenOptions,
						RuoloTipologia.toEnumConstant(autorizzazioneRuoliTipologia)));
				pa.getProprietaAutorizzazioneList().clear();
			}else {
				pa.setAutorizzazione(autorizzazioneCustom);
			}

			if(autorizzazione != null && autorizzazione.equals(AutorizzazioneUtilities.STATO_XACML_POLICY) && allegatoXacmlPolicy.getValue() != null) {
				pa.setXacmlPolicy(new String(allegatoXacmlPolicy.getValue()));
			} else {
				pa.setXacmlPolicy(null);
			}

			if(ruoloMatch!=null && !"".equals(ruoloMatch)){
				RuoloTipoMatch tipoRuoloMatch = RuoloTipoMatch.toEnumConstant(ruoloMatch);
				if(tipoRuoloMatch!=null){
					if(pa.getRuoli()==null){
						pa.setRuoli(new AutorizzazioneRuoli());
					}
					pa.getRuoli().setMatch(tipoRuoloMatch);
				}
			}

			if(ServletUtils.isCheckBoxEnabled(autorizzazioneScope )) {
				if(pa.getScope() == null)
					pa.setScope(new AutorizzazioneScope());

				pa.getScope().setStato(StatoFunzionalita.ABILITATO); 
			}
			else {
				pa.setScope(null);
			}
			if(autorizzazioneScopeMatch!=null && !"".equals(autorizzazioneScopeMatch)){
				ScopeTipoMatch scopeTipoMatch = ScopeTipoMatch.toEnumConstant(autorizzazioneScopeMatch);
				if(scopeTipoMatch!=null){
					if(pa.getScope()==null){
						pa.setScope(new AutorizzazioneScope());
					}
					pa.getScope().setMatch(scopeTipoMatch);
				}
			}

			if(autorizzazioneContenutiStato.equals(StatoFunzionalita.DISABILITATO.getValue())) {
				pa.setAutorizzazioneContenuto(null);
				pa.getProprietaAutorizzazioneContenutoList().clear();
			} else if(autorizzazioneContenutiStato.equals(StatoFunzionalita.ABILITATO.getValue())) {
				pa.setAutorizzazioneContenuto(CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN);
				pa.getProprietaAutorizzazioneContenutoList().clear();
				Properties convertTextToProperties = PropertiesUtilities.convertTextToProperties(autorizzazioneContenutiProperties);
				
				Enumeration<Object> keys = convertTextToProperties.keys();
				while (keys.hasMoreElements()) {
					String nome = (String) keys.nextElement();
					String valore = convertTextToProperties.getProperty(nome);
					Proprieta proprietaAutorizzazioneContenuto = new Proprieta();
					proprietaAutorizzazioneContenuto.setNome(nome);
					proprietaAutorizzazioneContenuto.setValore(valore);
					pa.addProprietaAutorizzazioneContenuto(proprietaAutorizzazioneContenuto);
				}
			} else {
				pa.setAutorizzazioneContenuto(autorizzazioneContenuti);
				if(!autorizzazioneContenutiStato.equals(oldAutorizzazioneContenutoStato))
					pa.getProprietaAutorizzazioneContenutoList().clear();
			}

			if(pa.getGestioneToken() == null)
				pa.setGestioneToken(new GestioneToken());

			if(gestioneToken.equals(StatoFunzionalita.ABILITATO.getValue())) {
				pa.getGestioneToken().setPolicy(gestioneTokenPolicy);
				if(ServletUtils.isCheckBoxEnabled(gestioneTokenOpzionale)) {
					pa.getGestioneToken().setTokenOpzionale(StatoFunzionalita.ABILITATO);
				}
				else {
					pa.getGestioneToken().setTokenOpzionale(StatoFunzionalita.DISABILITATO);
				}
				pa.getGestioneToken().setValidazione(StatoFunzionalitaConWarning.toEnumConstant(gestioneTokenValidazioneInput));
				pa.getGestioneToken().setIntrospection(StatoFunzionalitaConWarning.toEnumConstant(gestioneTokenIntrospection));
				pa.getGestioneToken().setUserInfo(StatoFunzionalitaConWarning.toEnumConstant(gestioneTokenUserInfo));
				pa.getGestioneToken().setForward(StatoFunzionalita.toEnumConstant(gestioneTokenTokenForward)); 
				pa.getGestioneToken().setOptions(autorizzazione_tokenOptions);
				if(pa.getGestioneToken().getAutenticazione()==null) {
					pa.getGestioneToken().setAutenticazione(new GestioneTokenAutenticazione());
				}
				pa.getGestioneToken().getAutenticazione().setIssuer(ServletUtils.isCheckBoxEnabled(autenticazioneTokenIssuer) ? StatoFunzionalita.ABILITATO : StatoFunzionalita.toEnumConstant(autenticazioneTokenIssuer)); 
				pa.getGestioneToken().getAutenticazione().setClientId(ServletUtils.isCheckBoxEnabled(autenticazioneTokenClientId) ? StatoFunzionalita.ABILITATO : StatoFunzionalita.toEnumConstant(autenticazioneTokenClientId)); 
				pa.getGestioneToken().getAutenticazione().setSubject(ServletUtils.isCheckBoxEnabled(autenticazioneTokenSubject) ? StatoFunzionalita.ABILITATO : StatoFunzionalita.toEnumConstant(autenticazioneTokenSubject)); 
				pa.getGestioneToken().getAutenticazione().setUsername(ServletUtils.isCheckBoxEnabled(autenticazioneTokenUsername) ? StatoFunzionalita.ABILITATO : StatoFunzionalita.toEnumConstant(autenticazioneTokenUsername)); 
				pa.getGestioneToken().getAutenticazione().setEmail(ServletUtils.isCheckBoxEnabled(autenticazioneTokenEMail) ? StatoFunzionalita.ABILITATO : StatoFunzionalita.toEnumConstant(autenticazioneTokenEMail)); 
			} else {
				pa.getGestioneToken().setPolicy(null);
				pa.getGestioneToken().setTokenOpzionale(StatoFunzionalita.DISABILITATO); 
				pa.getGestioneToken().setValidazione(StatoFunzionalitaConWarning.DISABILITATO);
				pa.getGestioneToken().setIntrospection(StatoFunzionalitaConWarning.DISABILITATO);
				pa.getGestioneToken().setUserInfo(StatoFunzionalitaConWarning.DISABILITATO);
				pa.getGestioneToken().setForward(StatoFunzionalita.DISABILITATO); 
				pa.getGestioneToken().setOptions(null);
				if(pa.getGestioneToken().getAutenticazione()!=null) {
					pa.getGestioneToken().setAutenticazione(null);
				}
			}

			if(porteApplicativeHelper.isProfiloModIPA(protocollo)) {
				
				String statoAutorizzazioneModiPA = porteApplicativeHelper.getParameter(CostantiControlStation.PARAMETRO_PORTE_AUTORIZZAZIONE_MODIPA_STATO);
				
				if(StatoFunzionalita.ABILITATO.getValue().equals(statoAutorizzazioneModiPA)) {
				
					int sizeSAautorizzati = 0;
					if(pa.getServiziApplicativiAutorizzati()!=null) {
						sizeSAautorizzati = pa.getServiziApplicativiAutorizzati().sizeServizioApplicativoList();
					}
					
					if(sizeSAautorizzati<=0) {
					
						String idSoggettoToAdd = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SOGGETTO);
						
						String idSAToAdd = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_SERVIZIO_APPLICATIVO_AUTORIZZATO);
						
						//decodifica soggetto scelto
						String tipoSoggettoScelto = null;
						String nomeSoggettoScelto = null;
						String nomeSAScelto = null;
						if(idSoggettoToAdd != null) {
							long soggettoToAddInt = Long.parseLong(idSoggettoToAdd);
							
							if(porteApplicativeCore.isRegistroServiziLocale()){
								org.openspcoop2.core.registry.Soggetto soggetto = soggettiCore.getSoggettoRegistro(soggettoToAddInt);
								tipoSoggettoScelto = soggetto.getTipo();
								nomeSoggettoScelto = soggetto.getNome();
							}
							else{
								org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(soggettoToAddInt);
								tipoSoggettoScelto = soggetto.getTipo();
								nomeSoggettoScelto = soggetto.getNome();
							}
							
							if(idSAToAdd != null) {
								long idSAToAddInt = Long.parseLong(idSAToAdd);
								ServizioApplicativo servizioApplicativo = saCore.getServizioApplicativo(idSAToAddInt);
								nomeSAScelto = servizioApplicativo.getNome();
							}
						}
						
						// Inserisco il servizioApplicativo nel db
						PortaApplicativaAutorizzazioneServizioApplicativo paSaAutorizzato = new PortaApplicativaAutorizzazioneServizioApplicativo();
						paSaAutorizzato.setNome(nomeSAScelto);
						paSaAutorizzato.setTipoSoggettoProprietario(tipoSoggettoScelto);
						paSaAutorizzato.setNomeSoggettoProprietario(nomeSoggettoScelto);
							
						PortaApplicativaAutorizzazioneServiziApplicativi saList = pa.getServiziApplicativiAutorizzati();
						if(saList != null)
							saList.addServizioApplicativo(paSaAutorizzato);
						else {
							saList = new PortaApplicativaAutorizzazioneServiziApplicativi();
							saList.addServizioApplicativo(paSaAutorizzato);
							pa.setServiziApplicativiAutorizzati(saList);
						}
						
						numErogazioneApplicativiAutenticati++;
						
					}
					
				}
				else {
					if(pa.getServiziApplicativiAutorizzati()!=null) {
						while(pa.getServiziApplicativiAutorizzati().sizeServizioApplicativoList()>0) {
							pa.getServiziApplicativiAutorizzati().removeServizioApplicativo(0);
						}
					}
				}
			}
			
			String userLogin = ServletUtils.getUserLoginFromSession(session);

			porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), pa);

			// cancello i file temporanei
			porteApplicativeHelper.deleteBinaryParameters(allegatoXacmlPolicy);

			// preparo i campi
			Vector<DataElement> dati = new Vector<DataElement>();

			pa = porteApplicativeCore.getPortaApplicativa(idInt);
			idporta = pa.getNome();

			ruoli = new ArrayList<>();
			if(pa!=null && pa.getRuoli()!=null && pa.getRuoli().sizeRuoloList()>0){
				for (int i = 0; i < pa.getRuoli().sizeRuoloList(); i++) {
					ruoli.add(pa.getRuoli().getRuolo(i).getNome());
				}
			}

			numRuoli = 0;
			if(pa.getRuoli()!=null){
				numRuoli = pa.getRuoli().sizeRuoloList();
			}

			numAutenticazioneCustomPropertiesList = pa.sizeProprietaAutenticazioneList();
			numAutorizzazioneCustomPropertiesList = pa.sizeProprietaAutorizzazioneList();
			numAutorizzazioneContenutiCustomPropertiesList = pa.sizeProprietaAutorizzazioneContenutoList();
			old_autorizzazione_contenuti_custom = pa.getAutorizzazioneContenuto() != null && !pa.getAutorizzazioneContenuto().equals(CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN);
			old_autenticazione_custom = pa.getAutenticazione() != null && !TipoAutenticazione.getValues().contains(pa.getAutenticazione());
			
			if (autenticazione == null) {
				autenticazione = pa.getAutenticazione();
				if (autenticazione != null &&
						!TipoAutenticazione.getValues().contains(autenticazione)) {
					autenticazioneCustom = autenticazione;
					autenticazione = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTENTICAZIONE_CUSTOM;
				}

				autenticazionePrincipal = porteApplicativeCore.getTipoAutenticazionePrincipal(pa.getProprietaAutenticazioneList());
				autenticazioneParametroList = porteApplicativeCore.getParametroAutenticazione(autenticazione, pa.getProprietaAutenticazioneList());
			}
			if(autenticazioneOpzionale==null){
				autenticazioneOpzionale = "";
				if(pa.getAutenticazioneOpzionale()!=null){
					if (pa.getAutenticazioneOpzionale().equals(StatoFunzionalita.ABILITATO)) {
						autenticazioneOpzionale = Costanti.CHECK_BOX_ENABLED;
					}
				}
			}
			if (autorizzazione == null) {
				if (pa.getAutorizzazione() != null &&
						!TipoAutorizzazione.getAllValues().contains(pa.getAutorizzazione())) {
					autorizzazioneCustom = pa.getAutorizzazione();
					autorizzazione = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_PORTE_AUTORIZZAZIONE_CUSTOM;
				}
				else{
					autorizzazione = AutorizzazioneUtilities.convertToStato(pa.getAutorizzazione());
					if(TipoAutorizzazione.isAuthenticationRequired(pa.getAutorizzazione()))
						autorizzazioneAutenticati = Costanti.CHECK_BOX_ENABLED;
					if(TipoAutorizzazione.isRolesRequired(pa.getAutorizzazione()))
						autorizzazioneRuoli = Costanti.CHECK_BOX_ENABLED;
					autorizzazioneRuoliTipologia = AutorizzazioneUtilities.convertToRuoloTipologia(pa.getAutorizzazione()).getValue();
				}
			}

			if (ruoloMatch == null) {
				if(pa.getRuoli()!=null && pa.getRuoli().getMatch()!=null){
					ruoloMatch = pa.getRuoli().getMatch().getValue();
				}
			}

			autorizzazioneContenuti = pa.getAutorizzazioneContenuto();
			
			if(autorizzazioneContenuti == null) {
				autorizzazioneContenutiStato = StatoFunzionalita.DISABILITATO.getValue();
			} else if(autorizzazioneContenuti.equals(CostantiAutorizzazione.AUTORIZZAZIONE_CONTENUTO_BUILT_IN)) {
				autorizzazioneContenutiStato = StatoFunzionalita.ABILITATO.getValue();
				List<Proprieta> proprietaAutorizzazioneContenutoList = pa.getProprietaAutorizzazioneContenutoList();
				StringBuilder sb = new StringBuilder();
				for (Proprieta proprieta : proprietaAutorizzazioneContenutoList) {
					if(sb.length() >0)
						sb.append("\n");
					
					sb.append(proprieta.getNome()).append("=").append(proprieta.getValore()); 
				}						
				
				autorizzazioneContenutiProperties = sb.toString();
			} else { // custom
				autorizzazioneContenutiStato = CostantiControlStation.VALUE_PARAMETRO_PORTE_CONTROLLO_ACCESSI_AUTORIZZAZIONE_CONTENUTI_STATO_CUSTOM;
			}

			if(pa.getGestioneToken() != null) {
				gestioneTokenPolicy = pa.getGestioneToken().getPolicy();
				if(gestioneTokenPolicy == null) {
					gestioneToken = StatoFunzionalita.DISABILITATO.getValue();
					gestioneTokenPolicy = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;
				} else {
					gestioneToken = StatoFunzionalita.ABILITATO.getValue();
				}

				StatoFunzionalita tokenOpzionale = pa.getGestioneToken().getTokenOpzionale();
				if(tokenOpzionale == null) {
					gestioneTokenOpzionale = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_OPZIONALE;
				}else { 
					gestioneTokenOpzionale = tokenOpzionale.getValue();
				}

				StatoFunzionalitaConWarning validazione = pa.getGestioneToken().getValidazione();
				if(validazione == null) {
					gestioneTokenValidazioneInput = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_VALIDAZIONE_INPUT;
				}else { 
					gestioneTokenValidazioneInput = validazione.getValue();
				}

				StatoFunzionalitaConWarning introspection = pa.getGestioneToken().getIntrospection();
				if(introspection == null) {
					gestioneTokenIntrospection = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_INTROSPECTION;
				}else { 
					gestioneTokenIntrospection = introspection.getValue();
				}

				StatoFunzionalitaConWarning userinfo = pa.getGestioneToken().getUserInfo();
				if(userinfo == null) {
					gestioneTokenUserInfo = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_USER_INFO;
				}else { 
					gestioneTokenUserInfo = userinfo.getValue();
				}

				StatoFunzionalita tokenForward = pa.getGestioneToken().getForward();
				if(tokenForward == null) {
					gestioneTokenTokenForward = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_TOKEN_FORWARD;
				}else { 
					gestioneTokenTokenForward = tokenForward.getValue();
				}

				autorizzazione_tokenOptions = pa.getGestioneToken().getOptions();

				if(pa.getGestioneToken().getAutenticazione() != null) {

					StatoFunzionalita issuer = pa.getGestioneToken().getAutenticazione().getIssuer();
					if(issuer == null) {
						autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
					}else { 
						autenticazioneTokenIssuer = issuer.getValue();
					}

					StatoFunzionalita clientId = pa.getGestioneToken().getAutenticazione().getClientId();
					if(clientId == null) {
						autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
					}else { 
						autenticazioneTokenClientId = clientId.getValue();
					}

					StatoFunzionalita subject = pa.getGestioneToken().getAutenticazione().getSubject();
					if(subject == null) {
						autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
					}else { 
						autenticazioneTokenSubject = subject.getValue();
					}

					StatoFunzionalita username = pa.getGestioneToken().getAutenticazione().getUsername();
					if(username == null) {
						autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
					}else { 
						autenticazioneTokenUsername = username.getValue();
					}

					StatoFunzionalita mailTmp = pa.getGestioneToken().getAutenticazione().getEmail();
					if(mailTmp == null) {
						autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
					}else { 
						autenticazioneTokenEMail = mailTmp.getValue();
					}

				}
				else {
					autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
					autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
					autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
					autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
					autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
				}
			}
			else {
				gestioneToken = StatoFunzionalita.DISABILITATO.getValue();
				gestioneTokenPolicy = CostantiControlStation.DEFAULT_VALUE_NON_SELEZIONATO;
				gestioneTokenOpzionale = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_OPZIONALE;

				gestioneTokenValidazioneInput = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_VALIDAZIONE_INPUT;
				gestioneTokenIntrospection = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_INTROSPECTION;
				gestioneTokenUserInfo = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_USER_INFO;
				gestioneTokenTokenForward = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_TOKEN_FORWARD;

				autenticazioneTokenIssuer = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_ISSUER;
				autenticazioneTokenClientId = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_CLIENT_ID;
				autenticazioneTokenSubject = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_SUBJECT;
				autenticazioneTokenUsername = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_USERNAME;
				autenticazioneTokenEMail = CostantiControlStation.DEFAULT_VALUE_PARAMETRO_CONFIGURAZIONE_GESTORE_POLICY_TOKEN_AUTENTICAZIONE_EMAIL;
			}

			if(autorizzazioneScope == null) {
				if(pa.getScope() != null) {
					autorizzazioneScope =  pa.getScope().getStato().equals(StatoFunzionalita.ABILITATO) ? Costanti.CHECK_BOX_ENABLED : ""; 
				} else {
					autorizzazioneScope = "";
				}
			}

			if(autorizzazioneScopeMatch == null) {
				if(pa.getScope()!=null && pa.getScope().getMatch()!=null){
					autorizzazioneScopeMatch = pa.getScope().getMatch().getValue();
				}
			}

			porteApplicativeHelper.controlloAccessiGestioneToken(dati, TipoOperazione.OTHER, gestioneToken, policyLabels, policyValues, 
					gestioneTokenPolicy, gestioneTokenOpzionale, 
					gestioneTokenValidazioneInput, gestioneTokenIntrospection, gestioneTokenUserInfo, gestioneTokenTokenForward, pa,protocollo,false);

			porteApplicativeHelper.controlloAccessiAutenticazione(dati, TipoOperazione.OTHER, servletChiamante,pa,protocollo,
					autenticazione, autenticazioneCustom, autenticazioneOpzionale, autenticazionePrincipal, autenticazioneParametroList,confPers, isSupportatoAutenticazione,false,
					gestioneToken, gestioneTokenPolicy, autenticazioneTokenIssuer, autenticazioneTokenClientId, autenticazioneTokenSubject, autenticazioneTokenUsername, autenticazioneTokenEMail,
					old_autenticazione_custom, urlAutenticazioneCustomProperties, numAutenticazioneCustomPropertiesList,
					forceHttps, forceDisableOptional);
			
			// Tipo operazione = CHANGE per evitare di aggiungere if, questa e' a tutti gli effetti una servlet di CHANGE
			porteApplicativeHelper.controlloAccessiAutorizzazione(dati, TipoOperazione.CHANGE, servletChiamante,pa,protocollo,
					autenticazione, autenticazioneCustom,
					autorizzazione, autorizzazioneCustom, 
					autorizzazioneAutenticati, urlAutorizzazioneAutenticati, sizeSoggettiPA, null, null,
					autorizzazioneRuoli,  urlAutorizzazioneRuoli, numRuoli, null, 
					autorizzazioneRuoliTipologia, ruoloMatch,
					confPers, isSupportatoAutenticazione, contaListe, false, false
					,autorizzazioneScope,urlAutorizzazioneScope,numScope,null,autorizzazioneScopeMatch,
					gestioneToken, gestioneTokenPolicy, autorizzazione_token, autorizzazione_tokenOptions,allegatoXacmlPolicy,
					urlAutorizzazioneErogazioneApplicativiAutenticati, numErogazioneApplicativiAutenticati,
					urlAutorizzazioneCustomProperties, numAutorizzazioneCustomPropertiesList);

			porteApplicativeHelper.controlloAccessiAutorizzazioneContenuti(dati, TipoOperazione.OTHER, false, autorizzazioneContenutiStato, autorizzazioneContenuti, autorizzazioneContenutiProperties, serviceBinding,
					old_autorizzazione_contenuti_custom, urlAutorizzazioneContenutiCustomPropertiesList, numAutorizzazioneContenutiCustomPropertiesList,
					confPers); 

			dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.OTHER,id, idsogg, null, idAsps, dati);

			pd.setDati(dati);

			pd.setMessage(CostantiControlStation.LABEL_AGGIORNAMENTO_EFFETTUATO_CON_SUCCESSO, Costanti.MESSAGE_TYPE_INFO);
			//pd.disableEditMode();
			dati.addElement(ServletUtils.getDataElementForEditModeFinished());

			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI, 
					ForwardParams.OTHER(""));

		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_CONTROLLO_ACCESSI , 
					ForwardParams.OTHER(""));
		} 
	}

}
