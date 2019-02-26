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
import org.openspcoop2.core.config.TrasformazioneRegolaApplicabilitaRisposta;
import org.openspcoop2.core.config.TrasformazioneRegolaRisposta;
import org.openspcoop2.core.config.TrasformazioneSoapRisposta;
import org.openspcoop2.core.config.Trasformazioni;
import org.openspcoop2.core.registry.AccordoServizioParteComune;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.apc.AccordiServizioParteComuneCore;
import org.openspcoop2.web.ctrlstat.servlet.aps.AccordiServizioParteSpecificaCore;
import org.openspcoop2.web.lib.mvc.BinaryParameter;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * PorteApplicativeTrasformazioniRispostaChange
 * 
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class PorteApplicativeTrasformazioniRispostaChange extends Action {

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
			
			
			String returnCode = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_STATUS);
			
			String statusMin = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_STATUS_MIN);
			if(statusMin == null)
				statusMin = "";
			String statusMax = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_STATUS_MAX);
			if(statusMax == null)
				statusMax = "";
			String pattern = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_PATTERN);
			String contentType = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_CT);
			
			String idTrasformazioneS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE);
			long idTrasformazione = Long.parseLong(idTrasformazioneS);
			
			String idTrasformazioneRispostaS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE_RISPOSTA);
			long idTrasformazioneRisposta = Long.parseLong(idTrasformazioneRispostaS);
			
			String trasformazioneContenutoRispostaAbilitatoS  = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_CONVERSIONE_ENABLED);
			boolean trasformazioneContenutoRispostaAbilitato = trasformazioneContenutoRispostaAbilitatoS != null ? ServletUtils.isCheckBoxEnabled(trasformazioneContenutoRispostaAbilitatoS) : false;
			String trasformazioneContenutoRispostaTipoS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_CONVERSIONE_TIPO);
			org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione trasformazioneContenutoRispostaTipo = 
					trasformazioneContenutoRispostaTipoS != null ? org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.toEnumConstant(trasformazioneContenutoRispostaTipoS) : 
						org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
			BinaryParameter trasformazioneContenutoRispostaTemplate = porteApplicativeHelper.getBinaryParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_CONVERSIONE_TEMPLATE);
			String trasformazioneContenutoRispostaContentType = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_CONTENT_TYPE);
			String trasformazioneRispostaSoapAbilitatoS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_TRANSFORMATION); 
			boolean trasformazioneRispostaSoapAbilitato = trasformazioneRispostaSoapAbilitatoS != null ? ServletUtils.isCheckBoxEnabled(trasformazioneRispostaSoapAbilitatoS) : false;
			String trasformazioneRispostaSoapEnvelope = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_ENVELOPE);
			String trasformazioneRispostaSoapEnvelopeTipoS = porteApplicativeHelper.getParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_ENVELOPE_TIPO);
			org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione trasformazioneRispostaSoapEnvelopeTipo =
					trasformazioneRispostaSoapEnvelopeTipoS != null ? org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.toEnumConstant(trasformazioneRispostaSoapEnvelopeTipoS) : 
						org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
			BinaryParameter trasformazioneRispostaSoapEnvelopeTemplate = porteApplicativeHelper.getBinaryParameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_ENVELOPE_TEMPLATE);

			PorteApplicativeCore porteApplicativeCore = new PorteApplicativeCore();
			AccordiServizioParteSpecificaCore apsCore = new AccordiServizioParteSpecificaCore(porteApplicativeCore);
			AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore(porteApplicativeCore);
			// Preparo il menu
			porteApplicativeHelper.makeMenu();

			// Prendo nome della porta applicativa
			PortaApplicativa pa = porteApplicativeCore.getPortaApplicativa(idInt);
			String nomePorta = pa.getNome();
			
			AccordoServizioParteSpecifica asps = apsCore.getAccordoServizioParteSpecifica(Integer.parseInt(idAsps));
			AccordoServizioParteComune apc = apcCore.getAccordoServizio(asps.getIdAccordo()); 
			ServiceBinding serviceBindingMessage = apcCore.toMessageServiceBinding(apc.getServiceBinding());
			
			Trasformazioni trasformazioni = pa.getTrasformazioni();
			TrasformazioneRegola oldRegola = null;
			TrasformazioneRegolaRisposta oldRisposta = null;
			for (TrasformazioneRegola reg : trasformazioni.getRegolaList()) {
				if(reg.getId().longValue() == idTrasformazione) {
					oldRegola = reg;
					break;
				}
			}
			
			for (int j = 0; j < oldRegola.sizeRispostaList(); j++) {
				TrasformazioneRegolaRisposta risposta = oldRegola.getRisposta(j);
				if (risposta.getId().longValue() == idTrasformazioneRisposta) {
					oldRisposta = risposta;
					break;
				}
			}
			
			
			boolean trasformazioneContenutoRichiestaAbilitato = false;
			boolean trasformazioneRichiestaRestAbilitato = false;
			if(oldRegola.getRichiesta() != null) {
				trasformazioneContenutoRichiestaAbilitato = oldRegola.getRichiesta().getConversione();
				trasformazioneRichiestaRestAbilitato = oldRegola.getRichiesta().getTrasformazioneRest() != null;
			}
			
			// TODO
			String nomeRisposta = "Modifica Risposta"; // oldRisposta.getApplicabilita().getNome();
			String nomeTrasformazione = "Modifica Trasformazione" ; // regola.getApplicabilita().getNome();
			Parameter pIdTrasformazione = new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE, idTrasformazione+"");
			
			String postBackElementName = porteApplicativeHelper.getPostBackElementName();
			
			if (postBackElementName != null) {
				if(postBackElementName.equals(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_APPLICABILITA_STATUS)) {
					statusMin = "";
					statusMax = "";
				}
				
				if(postBackElementName.equals(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_CONVERSIONE_ENABLED)) {
					trasformazioneContenutoRispostaTipo = org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
					trasformazioneContenutoRispostaContentType = "";
					trasformazioneRispostaSoapAbilitato = false;
					trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_DISABILITATO;
					trasformazioneRispostaSoapEnvelopeTipo = org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
					
					porteApplicativeHelper.deleteBinaryParameters(trasformazioneContenutoRispostaTemplate, trasformazioneRispostaSoapEnvelopeTemplate);
				}
				
				if(postBackElementName.equals(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_TRANSFORMATION)) {
					porteApplicativeHelper.deleteBinaryParameters(trasformazioneRispostaSoapEnvelopeTemplate);
					if(trasformazioneRispostaSoapAbilitato) {
						trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_DISABILITATO;
						trasformazioneRispostaSoapEnvelopeTipo = org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
					} 
				}
				
				if(postBackElementName.equals(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_SOAP_ENVELOPE)) {
					porteApplicativeHelper.deleteBinaryParameters(trasformazioneRispostaSoapEnvelopeTemplate);
					if(trasformazioneRispostaSoapEnvelope.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_AS_ATTACHMENT)) {
						trasformazioneRispostaSoapEnvelopeTipo = org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY; 
					}
				}
			}
			
			// parametri visualizzazione link
			String servletTrasformazioniRispostaHeadersList = PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_HEADER_LIST;
			int numeroTrasformazioniRispostaHeaders = oldRisposta.sizeHeaderList();
			
			List<Parameter> parametriInvocazioneServletTrasformazioniRispostaHeaders = new ArrayList<Parameter>();
			parametriInvocazioneServletTrasformazioniRispostaHeaders.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, idPorta));
			parametriInvocazioneServletTrasformazioniRispostaHeaders.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO, idsogg));
			parametriInvocazioneServletTrasformazioniRispostaHeaders.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS, idAsps));
			parametriInvocazioneServletTrasformazioniRispostaHeaders.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE, idTrasformazioneS));
			parametriInvocazioneServletTrasformazioniRispostaHeaders.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_TRASFORMAZIONE_RISPOSTA, idTrasformazioneRispostaS));
			

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
			
			lstParam.add(new Parameter(nomeTrasformazione, PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_CHANGE, 
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, idPorta),
					new Parameter( PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO, idsogg),
					new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS, idAsps), pIdTrasformazione));
			
			String labelPag = PorteApplicativeCostanti.LABEL_PARAMETRO_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTE;
			
			List<Parameter> parametriInvocazioneServletTrasformazioniRisposta = new ArrayList<Parameter>();
			parametriInvocazioneServletTrasformazioniRisposta.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID, idPorta));
			parametriInvocazioneServletTrasformazioniRisposta.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_SOGGETTO, idsogg));
			parametriInvocazioneServletTrasformazioniRisposta.add(new Parameter(PorteApplicativeCostanti.PARAMETRO_PORTE_APPLICATIVE_ID_ASPS, idAsps));
			parametriInvocazioneServletTrasformazioniRisposta.add(pIdTrasformazione);
			
			lstParam.add(new Parameter(labelPag,PorteApplicativeCostanti.SERVLET_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA_LIST,parametriInvocazioneServletTrasformazioniRisposta));
			
			lstParam.add(new Parameter(nomeRisposta, null));

			ServletUtils.setPageDataTitle(pd, lstParam);
			
			// Se nomehid = null, devo visualizzare la pagina per l'inserimento
			// dati
			if (porteApplicativeHelper.isEditModeInProgress()) {
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				// primo accesso
				if(returnCode == null) {
					TrasformazioneRegolaApplicabilitaRisposta applicabilita = oldRisposta.getApplicabilita();
					
					Integer statusMinInteger = applicabilita != null ? applicabilita.getReturnCodeMin() : null;
					Integer statusMaxInteger = applicabilita != null ? applicabilita.getReturnCodeMax() : null;
					
					if(statusMinInteger != null) {
						statusMin = statusMinInteger +"";
					}
					
					if(statusMaxInteger != null) {
						statusMax = statusMaxInteger +"";
					}
					
					// se e' stato salvato il valore 0 lo tratto come null
					if(statusMinInteger != null && statusMinInteger.intValue() <= 0) {
						statusMinInteger = null;
					}
					
					if(statusMaxInteger != null && statusMaxInteger.intValue() <= 0) {
						statusMaxInteger = null;
					}
					
					// Intervallo
					if(statusMinInteger != null && statusMaxInteger != null) {
						if(statusMaxInteger.longValue() == statusMinInteger.longValue()) // esatto
							returnCode = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_ESATTO;
						else 
							returnCode = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_INTERVALLO;
					} else if(statusMinInteger != null && statusMaxInteger == null) { // definito solo l'estremo inferiore
						returnCode = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_INTERVALLO;
					} else if(statusMinInteger == null && statusMaxInteger != null) { // definito solo l'estremo superiore
						returnCode = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_INTERVALLO;
					} else { //entrambi null 
						returnCode = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_QUALSIASI;
					}
					
					if(applicabilita != null) {
						pattern = applicabilita.getPattern();
						contentType = applicabilita.getContentTypeList() != null ? StringUtils.join(applicabilita.getContentTypeList(), ",") : "";  
					}	
					
					// dati contenuto
					trasformazioneContenutoRispostaAbilitato = oldRisposta.getConversione();
					trasformazioneContenutoRispostaTipo = StringUtils.isNotEmpty(oldRisposta.getConversioneTipo()) ? 
							org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.toEnumConstant(oldRisposta.getConversioneTipo()) : org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
					trasformazioneContenutoRispostaTemplate.setValue(oldRisposta.getConversioneTemplate());
					trasformazioneContenutoRispostaContentType = StringUtils.isNotEmpty(oldRisposta.getContentType()) ? oldRisposta.getContentType() : "";
					
					if(oldRisposta.getTrasformazioneSoap() == null) {
						trasformazioneRispostaSoapAbilitato = false;
						trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_DISABILITATO;
						trasformazioneRispostaSoapEnvelopeTipo = org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
					} else {
						trasformazioneRispostaSoapAbilitato = true;
						
						if(oldRisposta.getTrasformazioneSoap().isEnvelope()) {
							if(oldRisposta.getTrasformazioneSoap().isEnvelopeAsAttachment()) {
								trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_AS_ATTACHMENT;
							} else {
								trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_AS_BODY;
							}
						} else {
							trasformazioneRispostaSoapEnvelope = CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_DISABILITATO;
						}
						
						trasformazioneRispostaSoapEnvelopeTipo = StringUtils.isNotEmpty(oldRisposta.getTrasformazioneSoap().getEnvelopeBodyConversioneTipo()) ? 
								org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.toEnumConstant(oldRisposta.getTrasformazioneSoap().getEnvelopeBodyConversioneTipo())
								: org.openspcoop2.pdd.core.trasformazioni.TipoTrasformazione.EMPTY;
						trasformazioneRispostaSoapEnvelopeTemplate.setValue(oldRisposta.getTrasformazioneSoap().getEnvelopeBodyConversioneTemplate()); 
					}
				}

				dati = porteApplicativeHelper.addTrasformazioneRispostaToDati(TipoOperazione.CHANGE, dati, idTrasformazioneS, idTrasformazioneRispostaS, 
						returnCode, statusMin, statusMax, pattern, contentType, servletTrasformazioniRispostaHeadersList, parametriInvocazioneServletTrasformazioniRispostaHeaders, numeroTrasformazioniRispostaHeaders, 
						trasformazioneContenutoRichiestaAbilitato, trasformazioneRichiestaRestAbilitato, 
						trasformazioneContenutoRispostaAbilitato, trasformazioneContenutoRispostaTipo, trasformazioneContenutoRispostaTemplate, trasformazioneContenutoRispostaContentType, 
						serviceBindingMessage, trasformazioneRispostaSoapAbilitato, trasformazioneRispostaSoapEnvelope, trasformazioneRispostaSoapEnvelopeTipo, trasformazioneRispostaSoapEnvelopeTemplate);
				
				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.CHANGE, idPorta, idsogg, idPorta,idAsps,  dati);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA,	ForwardParams.CHANGE());
			}
			
				
			boolean isOk = porteApplicativeHelper.trasformazioniRispostaCheckData(TipoOperazione.CHANGE, oldRegola, oldRisposta);
			
			if(isOk) {
				// quando un parametro viene inviato come vuoto, sul db viene messo null, gestisco il caso
				Integer statusMinDBCheck = StringUtils.isNotEmpty(statusMin) ? Integer.parseInt(statusMin) : null;
				Integer statusMaxDBCheck = StringUtils.isNotEmpty(statusMax) ? Integer.parseInt(statusMax) : null;
				if(returnCode.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_ESATTO))
					statusMaxDBCheck = statusMinDBCheck;
				String patternDBCheck = StringUtils.isNotEmpty(pattern) ? pattern : null;
				String contentTypeDBCheck = StringUtils.isNotEmpty(contentType) ? contentType : null;
				TrasformazioneRegolaRisposta regolaRispostaDBCheck = porteApplicativeCore.getTrasformazioneRisposta(Long.parseLong(idPorta), idTrasformazione, statusMinDBCheck, statusMaxDBCheck, patternDBCheck, contentTypeDBCheck);
			
				// controllo che le modifiche ai parametri non coincidano con altre regole gia' presenti
				if(regolaRispostaDBCheck != null && regolaRispostaDBCheck.getId().longValue() != oldRisposta.getId().longValue()) {
					pd.setMessage("Le modifiche ai parametri di applicabilit&agrave; effettuate violano il vincolo di univocit&agrave; di una Trasformazione.");
					isOk = false;
				}
			}
			
			if (!isOk) {

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				dati = porteApplicativeHelper.addTrasformazioneRispostaToDati(TipoOperazione.CHANGE, dati, idTrasformazioneS, idTrasformazioneRispostaS, 
						returnCode, statusMin, statusMax, pattern, contentType, servletTrasformazioniRispostaHeadersList, parametriInvocazioneServletTrasformazioniRispostaHeaders, numeroTrasformazioniRispostaHeaders, 
						trasformazioneContenutoRichiestaAbilitato, trasformazioneRichiestaRestAbilitato, 
						trasformazioneContenutoRispostaAbilitato, trasformazioneContenutoRispostaTipo, trasformazioneContenutoRispostaTemplate, trasformazioneContenutoRispostaContentType, 
						serviceBindingMessage, trasformazioneRispostaSoapAbilitato, trasformazioneRispostaSoapEnvelope, trasformazioneRispostaSoapEnvelopeTipo, trasformazioneRispostaSoapEnvelopeTemplate);
				
				dati = porteApplicativeHelper.addHiddenFieldsToDati(TipoOperazione.CHANGE, idPorta, idsogg, idPorta,idAsps,  dati);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA,	ForwardParams.CHANGE());
			}
			
			// aggiorno la regola
			TrasformazioneRegola regola = null;
			for (TrasformazioneRegola reg : trasformazioni.getRegolaList()) {
				if(reg.getId().longValue() == idTrasformazione) {
					regola = reg;
					break;
				}
			}
			
			TrasformazioneRegolaRisposta rispostaDaAggiorare = null;
			for (int j = 0; j < regola.sizeRispostaList(); j++) {
				TrasformazioneRegolaRisposta risposta = regola.getRisposta(j);
				if (risposta.getId().longValue() == idTrasformazioneRisposta) {
					rispostaDaAggiorare = risposta;
					break;
				}
			}
			
			if(rispostaDaAggiorare.getApplicabilita() == null)
				rispostaDaAggiorare.setApplicabilita(new TrasformazioneRegolaApplicabilitaRisposta());

			if(returnCode.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_QUALSIASI)) {
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMin(null);
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMax(null);
			} else if(returnCode.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_RESPONSE_CACHING_CONFIGURAZIONE_REGOLA_RETURN_CODE_ESATTO)) {
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMin(StringUtils.isNotEmpty(statusMin) ? Integer.parseInt(statusMin) : null);
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMax(StringUtils.isNotEmpty(statusMin) ? Integer.parseInt(statusMin) : null);
			} else { // intervallo
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMin(StringUtils.isNotEmpty(statusMin) ? Integer.parseInt(statusMin) : null);
				rispostaDaAggiorare.getApplicabilita().setReturnCodeMax(StringUtils.isNotEmpty(statusMax) ? Integer.parseInt(statusMax) : null);
			}
			
			rispostaDaAggiorare.getApplicabilita().setPattern(pattern);
			rispostaDaAggiorare.getApplicabilita().getContentTypeList().clear();
			if(contentType != null) {
				rispostaDaAggiorare.getApplicabilita().getContentTypeList().addAll(Arrays.asList(contentType.split(",")));
			}
			
			rispostaDaAggiorare.setConversione(trasformazioneContenutoRispostaAbilitato);
			
			if(trasformazioneContenutoRispostaAbilitato) {
				if(trasformazioneContenutoRispostaTipo.isTemplateRequired()) {
					// 	se e' stato aggiornato il template lo sovrascrivo
					if(trasformazioneContenutoRispostaTemplate.getValue() != null && trasformazioneContenutoRispostaTemplate.getValue().length  > 0)
						rispostaDaAggiorare.setConversioneTemplate(trasformazioneContenutoRispostaTemplate.getValue());
				}else {
					rispostaDaAggiorare.setConversioneTemplate(null);
				}
				rispostaDaAggiorare.setContentType(trasformazioneContenutoRispostaContentType);
				
				rispostaDaAggiorare.setConversioneTipo(trasformazioneContenutoRispostaTipo.getValue());
				
				if(trasformazioneRispostaSoapAbilitato) {
					if(rispostaDaAggiorare.getTrasformazioneSoap() == null)
						rispostaDaAggiorare.setTrasformazioneSoap(new TrasformazioneSoapRisposta());
					
					// ct null se trasformazione soap abilitata
					rispostaDaAggiorare.setContentType(null);
					
					if(trasformazioneRispostaSoapEnvelope.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_AS_ATTACHMENT)) { // attachment
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelope(true);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeAsAttachment(true);
						
						if(trasformazioneRispostaSoapEnvelopeTipo.isTemplateRequired()) {
							// 	se e' stato aggiornato il template lo sovrascrivo
							if(trasformazioneRispostaSoapEnvelopeTemplate.getValue() != null && trasformazioneRispostaSoapEnvelopeTemplate.getValue().length  > 0)
								rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTemplate(trasformazioneRispostaSoapEnvelopeTemplate.getValue());
						} else {
							rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTemplate(null);
						}
						
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTipo(trasformazioneRispostaSoapEnvelopeTipo.getValue());
					} else if(trasformazioneRispostaSoapEnvelope.equals(CostantiControlStation.VALUE_PARAMETRO_CONFIGURAZIONE_TRASFORMAZIONI_SOAP_ENVELOPE_AS_BODY)) { // body
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelope(true);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeAsAttachment(false);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTemplate(null);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTipo(null);
					} else  { // disabilitato
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelope(false);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeAsAttachment(false);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTemplate(null);
						rispostaDaAggiorare.getTrasformazioneSoap().setEnvelopeBodyConversioneTipo(null);
					}
						
				} else {
					rispostaDaAggiorare.setTrasformazioneSoap(null);
				}
			} else {
				rispostaDaAggiorare.setConversioneTemplate(null);
				rispostaDaAggiorare.setContentType(null);
				rispostaDaAggiorare.setConversioneTipo(null);
				rispostaDaAggiorare.setTrasformazioneSoap(null);
			}
			
			porteApplicativeCore.performUpdateOperation(userLogin, porteApplicativeHelper.smista(), pa);
			
			// ricaricare id trasformazione
			pa = porteApplicativeCore.getPortaApplicativa(Long.parseLong(idPorta));

			String patternDBCheck = (regola.getApplicabilita() != null && StringUtils.isNotEmpty(regola.getApplicabilita().getPattern())) ? regola.getApplicabilita().getPattern() : null;
			String contentTypeAsString = (regola.getApplicabilita() != null &&regola.getApplicabilita().getContentTypeList() != null) ? StringUtils.join(regola.getApplicabilita().getContentTypeList(), ",") : "";
			String contentTypeDBCheck = StringUtils.isNotEmpty(contentTypeAsString) ? contentTypeAsString : null;
			String azioniAsString = (regola.getApplicabilita() != null && regola.getApplicabilita().getAzioneList() != null) ? StringUtils.join(regola.getApplicabilita().getAzioneList(), ",") : "";
			String azioniDBCheck = StringUtils.isNotEmpty(azioniAsString) ? azioniAsString : null;
			TrasformazioneRegola trasformazioneAggiornata = porteApplicativeCore.getTrasformazione(pa.getId(), azioniDBCheck, patternDBCheck, contentTypeDBCheck);
			
			
			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
			
			int idLista = Liste.PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTE; 
			
			ricerca = porteApplicativeHelper.checkSearchParameters(idLista, ricerca);

			List<TrasformazioneRegolaRisposta> lista = porteApplicativeCore.porteAppTrasformazioniRispostaList(Long.parseLong(idPorta), trasformazioneAggiornata.getId(), ricerca);
			
			porteApplicativeHelper.preparePorteAppTrasformazioniRispostaList(nomePorta, trasformazioneAggiornata.getId(), ricerca, lista); 
						
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA,	ForwardParams.CHANGE());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					PorteApplicativeCostanti.OBJECT_NAME_PORTE_APPLICATIVE_TRASFORMAZIONI_RISPOSTA, 
					ForwardParams.CHANGE());
		}
	}
}
