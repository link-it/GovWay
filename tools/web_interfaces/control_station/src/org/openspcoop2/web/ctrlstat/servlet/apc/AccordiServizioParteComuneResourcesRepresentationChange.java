/*
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
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


package org.openspcoop2.web.ctrlstat.servlet.apc;

import java.util.ArrayList;
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
import org.openspcoop2.core.registry.AccordoServizioParteComune;
import org.openspcoop2.core.registry.IdSoggetto;
import org.openspcoop2.core.registry.Resource;
import org.openspcoop2.core.registry.ResourceRepresentation;
import org.openspcoop2.core.registry.ResourceRepresentationJson;
import org.openspcoop2.core.registry.ResourceRepresentationXml;
import org.openspcoop2.core.registry.ResourceRequest;
import org.openspcoop2.core.registry.ResourceResponse;
import org.openspcoop2.core.registry.constants.RepresentationType;
import org.openspcoop2.core.registry.constants.RepresentationXmlType;
import org.openspcoop2.core.registry.constants.StatiAccordo;
import org.openspcoop2.core.registry.driver.IDAccordoFactory;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * AccordiServizioParteComuneResourcesRepresentationChange
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author: pintori $
 * @version $Rev: 12608 $, $Date: 2017-01-18 16:42:09 +0100(mer, 18 gen 2017) $
 * 
 */
public final class AccordiServizioParteComuneResourcesRepresentationChange extends Action {

	private IProtocolFactory<?> protocolFactory= null;
	private String editMode = null;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		// Inizializzo GeneralData
		GeneralHelper generalHelper = new GeneralHelper(session);

		GeneralData gd = generalHelper.initGeneralData(request);

		String userLogin = ServletUtils.getUserLoginFromSession(session);

		IDAccordoFactory idAccordoFactory = IDAccordoFactory.getInstance();

		// Parametri relativi al tipo operazione
		TipoOperazione tipoOp = TipoOperazione.CHANGE;

		try {
			AccordiServizioParteComuneCore apcCore = new AccordiServizioParteComuneCore();

			SoggettiCore soggettiCore = new SoggettiCore(apcCore);
			AccordiServizioParteComuneHelper apcHelper = new AccordiServizioParteComuneHelper(request, pd, session);

			this.editMode = apcHelper.getParameter(Costanti.DATA_ELEMENT_EDIT_MODE_NAME);
			String id = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID);
			int idInt = Integer.parseInt(id);
			String nomeRisorsa = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME);
			if (nomeRisorsa == null) {
				nomeRisorsa = "";
			}
			String statusS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_RESPONSE_STATUS);
			Integer status = null;
			try {
				if(statusS!=null)
					status = Integer.parseInt(statusS);
			} catch(Exception e) {}
			String isReq = request.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCE_REQUEST);
			boolean isRequest = ServletUtils.isCheckBoxEnabled(isReq);
			
			String descr = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_DESCRIZIONE);
			if (descr == null) {
				descr = "";
			}

			String messageProcessorS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_MESSAGE_TYPE);
			MessageType messageType = (StringUtils.isNotEmpty(messageProcessorS) && !messageProcessorS.equals(AccordiServizioParteComuneCostanti.DEFAULT_VALUE_PARAMETRO_APC_MESSAGE_TYPE_DEFAULT)) ? MessageType.valueOf(messageProcessorS) : null;
			String mediaType = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_MEDIA_TYPE);
			String tipoS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_TIPO);
			RepresentationType tipo = StringUtils.isNotEmpty(tipoS) ? RepresentationType.toEnumConstant(tipoS) : null;
			String nome = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_NOME);
			String tipoJson = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_JSON_TYPE);
			String namespaceXml = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_XML_NAMESPACE);
			String nomeXml = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_XML_NAME);
			String xmlTypeS = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_REPRESENTATION_XML_TIPO);
			RepresentationXmlType xmlType =  StringUtils.isNotEmpty(xmlTypeS) ? RepresentationXmlType.toEnumConstant(xmlTypeS) : null;
			String tipoAccordo = apcHelper.getParameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO);
			if("".equals(tipoAccordo))
				tipoAccordo = null;

			// Preparo il menu
			apcHelper.makeMenu();

			// Prendo il nome
			AccordoServizioParteComune as = apcCore.getAccordoServizio(new Long(idInt));
			String uriAS = idAccordoFactory.getUriFromAccordo(as);

			String protocollo = null;
			//calcolo del protocollo implementato dall'accordo
			if(as != null){
				IdSoggetto soggettoReferente = as.getSoggettoReferente();
				String tipoSoggettoReferente = soggettoReferente.getTipo();
				protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(tipoSoggettoReferente);
			} else {
				protocollo = apcCore.getProtocolloDefault();
			}
			
			ServiceBinding serviceBinding;
			//calcolo del serviceBinding dall'accordo
			if(as != null){
				serviceBinding = apcCore.toMessageServiceBinding(as.getServiceBinding());
			} else {
				serviceBinding = apcCore.getDefaultServiceBinding(this.protocolFactory);
			}
			
			this.protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByName(protocollo);

			Resource risorsa = null;
			for (int j = 0; j < as.sizeResourceList(); j++) {
				risorsa = as.getResource(j);
				if (nomeRisorsa.equals(risorsa.getNome())) {
					break;
				}
			}
			
			Long idResponse = null;
			Long idResource = null;
			ResourceRequest resourceRequest = null;
			ResourceResponse resourceResponse = null;
			ResourceRepresentation resourceRepresentationOLD = null;
			List<ResourceRepresentation> representationList = null;
			if(isRequest) {
				resourceRequest = risorsa.getRequest();
				idResource = risorsa.getId();
				if(resourceRequest != null)
					representationList = resourceRequest.getRepresentationList();
			} else {
				if(risorsa.getResponseList() != null) {
					for (int i = 0; i < risorsa.getResponseList().size(); i++) {
						resourceResponse = risorsa.getResponse(i);
						if (resourceResponse.getStatus() == status) {
							idResponse = resourceResponse.getId();
							break;
						}
					}
					if(resourceResponse != null)
						representationList = resourceResponse.getRepresentationList();
				}
			}
			
			if(representationList != null && representationList.size() > 0) {
				for (ResourceRepresentation resourceRepresentation : representationList) {
					if(resourceRepresentation.getMediaType().equals(mediaType)) {
						resourceRepresentationOLD = resourceRepresentation;
						break;
					}
				}
			}
			
			
			String postBackElementName = apcHelper.getParameter(Costanti.POSTBACK_ELEMENT_NAME);

			// se ho fatto una postback tengo i dati che ho inserito e non leggo quelli
			if(postBackElementName != null ){
				resourceRepresentationOLD = null;
			}
			
			List<Parameter> listaLinkPageDataTitle = new ArrayList<Parameter>();
			
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneUtilities.getTerminologiaAccordoServizio(tipoAccordo),null));
			listaLinkPageDataTitle.add(new Parameter(Costanti.PAGE_DATA_TITLE_LABEL_ELENCO, 
					AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_LIST+"?"+
							AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getName()+"="+
							AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getValue()));
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_RISORSE + " di " + uriAS, 
								AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_LIST,
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID, id),
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO, tipoAccordo),
								new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_NOME, as.getNome())));
			String labelOwner = nomeRisorsa;
			if(!isRequest) {
				listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_RISPOSTE + " di " + nomeRisorsa, 
						AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_RISPOSTE_LIST+"?"+
								AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID+"="+id+"&"+
								AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME+"="+nomeRisorsa+"&"+
								AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getName()+"="+
								AccordiServizioParteComuneUtilities.getParametroAccordoServizio(tipoAccordo).getValue()));
				labelOwner = statusS;
			} 
			
			listaLinkPageDataTitle.add(new Parameter(AccordiServizioParteComuneCostanti.LABEL_REPRESENTATION + " di " + labelOwner, 
					AccordiServizioParteComuneCostanti.SERVLET_NAME_APC_RESOURCES_REPRESENTATIONS_LIST,
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_ID, id),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_TIPO_ACCORDO, tipoAccordo),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_NOME, uriAS),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCE_REQUEST, isRequest+""),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_RESPONSE_STATUS, statusS),
					new Parameter(AccordiServizioParteComuneCostanti.PARAMETRO_APC_RESOURCES_NOME, nomeRisorsa)));
			
			listaLinkPageDataTitle.add(new Parameter(mediaType, null));

			// Se idhid = null, devo visualizzare la pagina per la
			// modifica dati
			if(ServletUtils.isEditModeInProgress(this.editMode)){

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd,listaLinkPageDataTitle); 

				// Prendo i dati dell'accordo
				if(resourceRepresentationOLD != null){
					descr = resourceRepresentationOLD.getDescrizione();
					nome = resourceRepresentationOLD.getNome();
					messageType = apcCore.toMessageMessageType(resourceRepresentationOLD.getMessageType());
					tipo = resourceRepresentationOLD.getRepresentationType();
					switch (tipo) {
					case JSON:
						ResourceRepresentationJson json = resourceRepresentationOLD.getJson();
						tipoJson = json.getTipo();
						break;
					case XML:
					default:
						ResourceRepresentationXml xml = resourceRepresentationOLD.getXml();
						namespaceXml = xml.getNamespace();
						nomeXml = xml.getNome();
						xmlType = xml.getXmlType();
						break;
					}
				}
				
				// nel caso di postback di un elemento che era json 
				if(xmlType == null)
					xmlType = RepresentationXmlType.ELEMENT;

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = apcHelper.addAccordiResourceRepresentationToDati(tipoOp, dati, id, as.getStatoPackage(),tipoAccordo,protocollo, 
						this.protocolFactory,serviceBinding, nomeRisorsa, isRequest, statusS, mediaType, nome, descr, messageType, tipo, tipoJson, nomeXml, namespaceXml, xmlType);
				pd.setDati(dati);

				if(apcCore.isShowGestioneWorkflowStatoDocumenti() && StatiAccordo.finale.toString().equals(as.getStatoPackage())){
					pd.disableEditMode();
				}

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_REPRESENTATIONS, ForwardParams.CHANGE());

			}
			
			// Controlli sui campi immessi
			boolean isOk = apcHelper.accordiResourceRepresentationCheckData(tipoOp, id, nomeRisorsa, nomeRisorsa, isRequest, statusS, mediaType, nome, descr, messageType, tipo, tipoJson, nomeXml, namespaceXml, xmlType, idResource,idResponse);

			if (!isOk) {

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd,listaLinkPageDataTitle); 

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = apcHelper.addAccordiResourceRepresentationToDati(tipoOp, dati, id, as.getStatoPackage(),tipoAccordo,protocollo, 
						this.protocolFactory,serviceBinding, nomeRisorsa, isRequest, statusS, mediaType, nome, descr, messageType, tipo, tipoJson, nomeXml, namespaceXml, xmlType);
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_REPRESENTATIONS, ForwardParams.CHANGE());
			}
			
			// Inserisco la risorsa nel db
			ResourceRepresentation newRepresentation = new ResourceRepresentation();
			newRepresentation.setMessageType(apcCore.fromMessageMessageType(messageType));
			newRepresentation.setDescrizione(descr);

			newRepresentation.setNome(nome);
			newRepresentation.setMediaType(mediaType);
			newRepresentation.setRepresentationType(tipo); 
			
			switch (tipo) {
			case JSON:
				ResourceRepresentationJson json = new ResourceRepresentationJson();
				json.setTipo(tipoJson);
				newRepresentation.setJson(json);
				break;
			case XML:
				ResourceRepresentationXml xml = new ResourceRepresentationXml();
				xml.setXmlType(xmlType);
				xml.setNome(nomeXml);
				xml.setNamespace(namespaceXml);
				newRepresentation.setXml(xml);
				break;
			default:
				break;
			}
			int idx = -1;			
			if(representationList != null && representationList.size() > 0) {
				for (int i = 0; i < representationList.size(); i++) {
					ResourceRepresentation resourceRepresentation = representationList.get(i);
					if(resourceRepresentation.getMediaType().equals(mediaType)) {
						idx = i;
						break;
					}
				}
				if(idx > -1) {
					representationList.remove(idx);
					representationList.add(idx, newRepresentation);
				}
			}

			// effettuo le operazioni
			apcCore.performUpdateOperation(userLogin, apcHelper.smista(), as);
			
			// Devo rileggere l'accordo dal db, perche' altrimenti
			// manca l'id dei nuovi port-type
			as = apcCore.getAccordoServizio(new Long(idInt));

			// Preparo la lista
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);

			risorsa = null;
			for (int j = 0; j < as.sizeResourceList(); j++) {
				risorsa = as.getResource(j);
				if (nomeRisorsa.equals(risorsa.getNome())) {
					break;
				}
			}
			
			idResponse = null;
			idResource = null;
			resourceRequest = null;
			resourceResponse = null;
			if(isRequest) {
				resourceRequest = risorsa.getRequest();
				idResource = risorsa.getId();
			} else {
				if(risorsa.getResponseList() != null) {
					for (int i = 0; i < risorsa.getResponseList().size(); i++) {
						resourceResponse = risorsa.getResponse(i);
						if (resourceResponse.getStatus() == status) {
							idResponse = resourceResponse.getId();
							break;
						}
					}
				}
			}

			List<ResourceRepresentation> lista = apcCore.accordiResourceRepresentationsList(idResource, isRequest, idResponse, ricerca);

			apcHelper.prepareAccordiResourcesRepresentationsList(id, as, lista, ricerca, tipoAccordo, isRequest, nomeRisorsa, idResource, resourceRequest, resourceResponse);


			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeFinished(mapping, AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_REPRESENTATIONS, ForwardParams.CHANGE());

		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					AccordiServizioParteComuneCostanti.OBJECT_NAME_APC_RESOURCES_REPRESENTATIONS, ForwardParams.CHANGE());
		} 
	}
}
