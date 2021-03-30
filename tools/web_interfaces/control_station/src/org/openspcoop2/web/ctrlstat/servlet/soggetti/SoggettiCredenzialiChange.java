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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.openspcoop2.core.registry.CredenzialiSoggetto;
import org.openspcoop2.core.registry.Soggetto;
import org.openspcoop2.core.registry.constants.CredenzialeTipo;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.certificate.ArchiveLoader;
import org.openspcoop2.utils.certificate.ArchiveType;
import org.openspcoop2.utils.certificate.Certificate;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.costanti.CostantiControlStation;
import org.openspcoop2.web.ctrlstat.dao.SoggettoCtrlStat;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.connettori.ConnettoriCostanti;
import org.openspcoop2.web.ctrlstat.servlet.pdd.PddCore;
import org.openspcoop2.web.ctrlstat.servlet.sa.ServiziApplicativiCostanti;
import org.openspcoop2.web.lib.mvc.BinaryParameter;
import org.openspcoop2.web.lib.mvc.Costanti;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.MessageType;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**
 * soggettiChange
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public final class SoggettiCredenzialiChange extends Action {

	private String editMode = null;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		// Parametri relativi al tipo operazione
		TipoOperazione tipoOperazione = TipoOperazione.CHANGE; 
		
		try {
			SoggettiHelper soggettiHelper = new SoggettiHelper(request, pd, session);
			
			String id = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_ID);
			int idSogg = Integer.parseInt(id);
			String nomeprov = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_NOME);
			String tipoprov = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_TIPO);
			
			String idCredenziale = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CREDENZIALI_ID);
			int idxCredenziale = Integer.parseInt(idCredenziale);
			
			String protocollo = soggettiHelper.getParameter(SoggettiCostanti.PARAMETRO_SOGGETTO_PROTOCOLLO);
			
			String userLogin = ServletUtils.getUserLoginFromSession(session);

			this.editMode = soggettiHelper.getParameter(Costanti.DATA_ELEMENT_EDIT_MODE_NAME);
			
			String tipoauthSoggetto = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_TIPO_AUTENTICAZIONE);
			String subjectSoggetto = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_SUBJECT);
			
			String tipoCredenzialiSSLSorgente = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL);
			if(tipoCredenzialiSSLSorgente == null) {
				tipoCredenzialiSSLSorgente = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_UPLOAD_CERTIFICATO;
			}
			String tipoCredenzialiSSLTipoArchivioS = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_TIPO_ARCHIVIO);
			org.openspcoop2.utils.certificate.ArchiveType tipoCredenzialiSSLTipoArchivio= null;
			if(tipoCredenzialiSSLTipoArchivioS == null) {
				tipoCredenzialiSSLTipoArchivio = org.openspcoop2.utils.certificate.ArchiveType.CER; 
			} else {
				tipoCredenzialiSSLTipoArchivio = org.openspcoop2.utils.certificate.ArchiveType.valueOf(tipoCredenzialiSSLTipoArchivioS);
			}
			BinaryParameter tipoCredenzialiSSLFileCertificato = soggettiHelper.getBinaryParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_FILE_CERTIFICATO);
			String tipoCredenzialiSSLFileCertificatoPassword = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_FILE_CERTIFICATO_PASSWORD);
			List<String> listaAliasEstrattiCertificato = new ArrayList<String>();
			String tipoCredenzialiSSLAliasCertificato = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO);
			if (tipoCredenzialiSSLAliasCertificato == null) {
				tipoCredenzialiSSLAliasCertificato = "";
			}
			String tipoCredenzialiSSLAliasCertificatoSubject= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_SUBJECT);
			String tipoCredenzialiSSLAliasCertificatoIssuer= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_ISSUER);
			String tipoCredenzialiSSLAliasCertificatoType= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_TYPE);
			String tipoCredenzialiSSLAliasCertificatoVersion= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_VERSION);
			String tipoCredenzialiSSLAliasCertificatoSerialNumber= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_SERIAL_NUMBER);
			String tipoCredenzialiSSLAliasCertificatoSelfSigned= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_SELF_SIGNED);
			String tipoCredenzialiSSLAliasCertificatoNotBefore= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_NOT_BEFORE);
			String tipoCredenzialiSSLAliasCertificatoNotAfter = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO_NOT_AFTER); 
			String tipoCredenzialiSSLVerificaTuttiICampi = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_VERIFICA_TUTTI_CAMPI);
			if (tipoCredenzialiSSLVerificaTuttiICampi == null) {
				tipoCredenzialiSSLVerificaTuttiICampi = Costanti.CHECK_BOX_DISABLED;
			}
			String tipoCredenzialiSSLConfigurazioneManualeSelfSigned= soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_MANUALE_SELF_SIGNED);
			if (tipoCredenzialiSSLConfigurazioneManualeSelfSigned == null) {
				tipoCredenzialiSSLConfigurazioneManualeSelfSigned = Costanti.CHECK_BOX_DISABLED;
			}
			String issuerSoggetto = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_ISSUER);

			String tipoCredenzialiSSLWizardStep = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP);
			if (tipoCredenzialiSSLWizardStep == null) {
				tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD;
			}
			String oldTipoCredenzialiSSLWizardStep = tipoCredenzialiSSLWizardStep;
			
			String promuoviCertificato = soggettiHelper.getParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_FILE_CERTIFICATO_PROMUOVI);
			if(promuoviCertificato == null) {
				promuoviCertificato = Costanti.CHECK_BOX_DISABLED;
			}
			
			String actionConfirm = soggettiHelper.getParameter(Costanti.PARAMETRO_ACTION_CONFIRM);
						
			boolean visualizzaPromuoviCertificato = idxCredenziale != 0; // CHANGE dipende dal certificato scelto (idx != 0) 
			String servletCredenzialiChange = SoggettiCostanti.SERVLET_NAME_SOGGETTI_CREDENZIALI_CHANGE;
			
//			Integer numeroCertificati = null;
			String toCall = SoggettiCostanti.SERVLET_NAME_SOGGETTI_CREDENZIALI_CHANGE;
			
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
			
			CredenzialiSoggetto credenziali = null;
			CredenzialiSoggetto oldCredenziali = null;
			if (soggettoRegistry != null) {
//				numeroCertificati = ip.sizeCredenzialiList();
				if(soggettoRegistry.sizeCredenzialiList()>0) {
					oldCredenziali = soggettoRegistry.getCredenziali(idxCredenziale);
					credenziali = soggettoRegistry.getCredenziali(idxCredenziale);
				}
			}

			protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(tipoprov);
			
			List<Parameter> parametersServletSoggettoChange = new ArrayList<Parameter>();
			Parameter pIdSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_ID, id);
			Parameter pNomeSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_NOME, nomeprov);
			Parameter pTipoSoggetto = new Parameter(SoggettiCostanti.PARAMETRO_SOGGETTO_TIPO, tipoprov);
			parametersServletSoggettoChange.add(pIdSoggetto);
			parametersServletSoggettoChange.add(pNomeSoggetto);
			parametersServletSoggettoChange.add(pTipoSoggetto);
			List<Parameter> parametersServletCredenzialiChange = new ArrayList<Parameter>();
			Parameter pIdCredenziale = new Parameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CREDENZIALI_ID, idCredenziale);
			parametersServletCredenzialiChange.add(pIdCredenziale);
			parametersServletCredenzialiChange.addAll(parametersServletSoggettoChange);

			PddCore pddCore = new PddCore(soggettiCore);

			String nomePddGestioneLocale = null;
			if(pddCore.isGestionePddAbilitata(soggettiHelper)==false){
				nomePddGestioneLocale = pddCore.getNomePddOperativa();
				if(nomePddGestioneLocale==null) {
					throw new Exception("Non è stata rilevata una pdd di tipologia 'operativo'");
				}
			}


			String postBackElementName = soggettiHelper.getPostBackElementName();
			String labelButtonSalva = Costanti.LABEL_MONITOR_BUTTON_INVIA;

			boolean visualizzaConfirm = false;
			// Controllo se ho modificato il protocollo, ricalcolo il default della versione del protocollo
			if(postBackElementName != null ){
				// tipo di configurazione SSL
				if(postBackElementName.equalsIgnoreCase(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL) || 
						postBackElementName.equalsIgnoreCase(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_FILE_CERTIFICATO_LINK_MODIFICA)) {
					listaAliasEstrattiCertificato = new ArrayList<String>();
					tipoCredenzialiSSLTipoArchivio = ArchiveType.CER;
					tipoCredenzialiSSLVerificaTuttiICampi = Costanti.CHECK_BOX_DISABLED;
					tipoCredenzialiSSLAliasCertificato = "";
					tipoCredenzialiSSLAliasCertificatoSubject= "";
					tipoCredenzialiSSLAliasCertificatoIssuer= "";
					tipoCredenzialiSSLAliasCertificatoType= "";
					tipoCredenzialiSSLAliasCertificatoVersion= "";
					tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
					tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
					tipoCredenzialiSSLAliasCertificatoNotBefore= "";
					tipoCredenzialiSSLAliasCertificatoNotAfter = "";
					soggettiHelper.deleteBinaryParameters(tipoCredenzialiSSLFileCertificato); 
					tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CARICA_CERTIFICATO;

					if(tipoCredenzialiSSLSorgente.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_CONFIGURAZIONE_MANUALE)) {
						subjectSoggetto = "";
						issuerSoggetto = "";
						tipoCredenzialiSSLConfigurazioneManualeSelfSigned = Costanti.CHECK_BOX_DISABLED;
						tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD;
					}
				}

				// cambio il tipo archivio butto via il vecchio certificato				
				if(postBackElementName.equalsIgnoreCase(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_TIPO_ARCHIVIO)) {
					soggettiHelper.deleteBinaryParameters(tipoCredenzialiSSLFileCertificato); 
					tipoCredenzialiSSLAliasCertificato = "";
					tipoCredenzialiSSLAliasCertificatoSubject= "";
					tipoCredenzialiSSLAliasCertificatoIssuer= "";
					tipoCredenzialiSSLAliasCertificatoType= "";
					tipoCredenzialiSSLAliasCertificatoVersion= "";
					tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
					tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
					tipoCredenzialiSSLAliasCertificatoNotBefore= "";
					tipoCredenzialiSSLAliasCertificatoNotAfter = "";
					listaAliasEstrattiCertificato = new ArrayList<String>();
					tipoCredenzialiSSLVerificaTuttiICampi = Costanti.CHECK_BOX_DISABLED;
					tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CARICA_CERTIFICATO;
				}

				// selezione alias
				if(postBackElementName.equalsIgnoreCase(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO)) {
					if(StringUtils.isNotEmpty(tipoCredenzialiSSLAliasCertificato)) {
						tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK;
					} else {
						tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ALIAS_NON_SCELTO;
					}
					tipoCredenzialiSSLAliasCertificatoSubject= "";
					tipoCredenzialiSSLAliasCertificatoIssuer= "";
					tipoCredenzialiSSLAliasCertificatoType= "";
					tipoCredenzialiSSLAliasCertificatoVersion= "";
					tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
					tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
					tipoCredenzialiSSLAliasCertificatoNotBefore= "";
					tipoCredenzialiSSLAliasCertificatoNotAfter = "";
				}
				
				// mostro la modale di conferma
				if(postBackElementName.equalsIgnoreCase(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_FILE_CERTIFICATO_LINK_PROMUOVI)) {
					visualizzaConfirm = true;
				}
			}
			
			// ho schiacciato uno dei due tasti della modale
			if(actionConfirm != null ) {
				if(actionConfirm.equals(Costanti.PARAMETRO_ACTION_CONFIRM_VALUE_NO)) {
					tipoauthSoggetto = null; // Imposto tipoauthSoggetto = null cosi mi viene visualizzata nuovamente la form completa.
				}
				tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD; // reset dell'eventuale wizard
				oldTipoCredenzialiSSLWizardStep = tipoCredenzialiSSLWizardStep;
			}
			
			boolean checkWizard = false;
			if(tipoauthSoggetto != null && tipoauthSoggetto.equals(ConnettoriCostanti.AUTENTICAZIONE_TIPO_SSL)) {
				
				boolean changeTipoDominio = false;
				if(SoggettiCostanti.PARAMETRO_SOGGETTO_DOMINIO.equalsIgnoreCase(postBackElementName)) {
					changeTipoDominio = true;
				}
				
				if(!changeTipoDominio && tipoCredenzialiSSLSorgente.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_UPLOAD_CERTIFICATO)) {
					if(tipoCredenzialiSSLFileCertificato.getValue() != null && tipoCredenzialiSSLFileCertificato.getValue().length > 0) {
						tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ALIAS_NON_SCELTO;
						if(!tipoCredenzialiSSLTipoArchivio.equals(ArchiveType.CER)) {
							if(StringUtils.isNotEmpty(tipoCredenzialiSSLFileCertificatoPassword)) {
								try {
									listaAliasEstrattiCertificato = ArchiveLoader.readAliases(tipoCredenzialiSSLTipoArchivio, tipoCredenzialiSSLFileCertificato.getValue(), tipoCredenzialiSSLFileCertificatoPassword);
									Collections.sort(listaAliasEstrattiCertificato);
	
									//se ho un solo alias lo imposto
									if(!listaAliasEstrattiCertificato.isEmpty() && listaAliasEstrattiCertificato.size() == 1) {
										tipoCredenzialiSSLAliasCertificato = listaAliasEstrattiCertificato.get(0);
									}
									
									// ho appena caricato il file devo solo far vedere la form senza segnalare il messaggio di errore
									if(oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CARICA_CERTIFICATO)
											||oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_PASSWORD_NON_PRESENTE) 
											|| oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ERRORE_LETTURA_CERTIFICATO)) {
										checkWizard = true;
										if(listaAliasEstrattiCertificato.size() > 1) {
											pd.setMessage("Il file caricato contiene pi&ugrave; certificati, selezionare un'"+ConnettoriCostanti.LABEL_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_ALIAS_CERTIFICATO, MessageType.INFO);
										}  
									}
								}catch(UtilsException e) {
									pd.setMessage("Il Certificato selezionato non &egrave; valido: "+e.getMessage());
									tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ERRORE_LETTURA_CERTIFICATO;
									tipoCredenzialiSSLAliasCertificato = "";
									tipoCredenzialiSSLAliasCertificatoSubject= "";
									tipoCredenzialiSSLAliasCertificatoIssuer= "";
									tipoCredenzialiSSLAliasCertificatoType= "";
									tipoCredenzialiSSLAliasCertificatoVersion= "";
									tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
									tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
									tipoCredenzialiSSLAliasCertificatoNotBefore= "";
									tipoCredenzialiSSLAliasCertificatoNotAfter = "";
								}
	
								if(!listaAliasEstrattiCertificato.isEmpty() && StringUtils.isNotEmpty(tipoCredenzialiSSLAliasCertificato)) {
									try {
										Certificate cSelezionato = ArchiveLoader.load(tipoCredenzialiSSLTipoArchivio, tipoCredenzialiSSLFileCertificato.getValue(), tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLFileCertificatoPassword);
										tipoCredenzialiSSLAliasCertificatoIssuer = cSelezionato.getCertificate().getIssuer().getNameNormalized();
										tipoCredenzialiSSLAliasCertificatoSubject = cSelezionato.getCertificate().getSubject().getNameNormalized();
										tipoCredenzialiSSLAliasCertificatoSelfSigned = cSelezionato.getCertificate().isSelfSigned() ? CostantiControlStation.LABEL_SI : CostantiControlStation.LABEL_NO;
										tipoCredenzialiSSLAliasCertificatoSerialNumber = cSelezionato.getCertificate().getSerialNumber() + "";
										tipoCredenzialiSSLAliasCertificatoType = cSelezionato.getCertificate().getType();
										tipoCredenzialiSSLAliasCertificatoVersion = cSelezionato.getCertificate().getVersion() + "";
										tipoCredenzialiSSLAliasCertificatoNotBefore = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotBefore());
										tipoCredenzialiSSLAliasCertificatoNotAfter = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotAfter());
										tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK;
									}catch(UtilsException e) {
										pd.setMessage("Il Certificato selezionato non &egrave; valido: "+e.getMessage());
										tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ERRORE_LETTURA_CERTIFICATO;
										tipoCredenzialiSSLAliasCertificato = "";
										tipoCredenzialiSSLAliasCertificatoSubject= "";
										tipoCredenzialiSSLAliasCertificatoIssuer= "";
										tipoCredenzialiSSLAliasCertificatoType= "";
										tipoCredenzialiSSLAliasCertificatoVersion= "";
										tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
										tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
										tipoCredenzialiSSLAliasCertificatoNotBefore= "";
										tipoCredenzialiSSLAliasCertificatoNotAfter = "";
									}
								} 
							} else {
								tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_PASSWORD_NON_PRESENTE;
							}
						} else {
							try {
								Certificate cSelezionato = ArchiveLoader.load(tipoCredenzialiSSLFileCertificato.getValue());
								tipoCredenzialiSSLAliasCertificatoIssuer = cSelezionato.getCertificate().getIssuer().getNameNormalized();
								tipoCredenzialiSSLAliasCertificatoSubject = cSelezionato.getCertificate().getSubject().getNameNormalized();
								tipoCredenzialiSSLAliasCertificatoSelfSigned = cSelezionato.getCertificate().isSelfSigned() ? CostantiControlStation.LABEL_SI : CostantiControlStation.LABEL_NO;
								tipoCredenzialiSSLAliasCertificatoSerialNumber = cSelezionato.getCertificate().getSerialNumber() + "";
								tipoCredenzialiSSLAliasCertificatoType = cSelezionato.getCertificate().getType();
								tipoCredenzialiSSLAliasCertificatoVersion = cSelezionato.getCertificate().getVersion() + "";
								tipoCredenzialiSSLAliasCertificatoNotBefore = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotBefore());
								tipoCredenzialiSSLAliasCertificatoNotAfter = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotAfter());
								
								// dalla seconda volta che passo, posso salvare, la prima mostro il recap del certificato estratto
								
								if(oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK_TIPO_CER)||
										oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK)) {
									tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK;
								} else {
									tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CERTIFICATO_OK_TIPO_CER;
									checkWizard = true;
								}
							}catch(UtilsException e) {
								pd.setMessage("Il Certificato selezionato non &egrave; valido: "+e.getMessage());
								tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ERRORE_LETTURA_CERTIFICATO;
								tipoCredenzialiSSLAliasCertificato = "";
								tipoCredenzialiSSLAliasCertificatoSubject= "";
								tipoCredenzialiSSLAliasCertificatoIssuer= "";
								tipoCredenzialiSSLAliasCertificatoType= "";
								tipoCredenzialiSSLAliasCertificatoVersion= "";
								tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
								tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
								tipoCredenzialiSSLAliasCertificatoNotBefore= "";
								tipoCredenzialiSSLAliasCertificatoNotAfter = "";
							}
						}
					} else {
						tipoCredenzialiSSLWizardStep = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CARICA_CERTIFICATO;
					}
				}
				
				if(StringUtils.isNotEmpty(tipoCredenzialiSSLWizardStep) && ( tipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_CARICA_CERTIFICATO)
						||tipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_PASSWORD_NON_PRESENTE) 
						|| tipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_WIZARD_STEP_ERRORE_LETTURA_CERTIFICATO)
						)) {
					if(tipoCredenzialiSSLSorgente.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_UPLOAD_CERTIFICATO)) {
						labelButtonSalva = ConnettoriCostanti.LABEL_BUTTON_INVIA_CARICA_CERTIFICATO;
					}
				}
				else { 
					labelButtonSalva = Costanti.LABEL_MONITOR_BUTTON_INVIA;
				}
			} else {
				labelButtonSalva = Costanti.LABEL_MONITOR_BUTTON_INVIA;
			}
			
			if(postBackElementName == null && oldTipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD))
				tipoCredenzialiSSLWizardStep  = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD;
			
			if(labelButtonSalva!= null) {
				pd.setLabelBottoneInvia(labelButtonSalva);
			}
			
			String labelNomeSoggetto = soggettiHelper.getLabelNomeSoggetto(protocollo, tipoprov , nomeprov);
			Parameter nomeCredenziale = new Parameter(soggettiHelper.getLabelCredenzialeCertificato(oldCredenziali.getCnSubject()), null);
			
			if(visualizzaConfirm) {
				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, 
						new Parameter(SoggettiCostanti.LABEL_SOGGETTI, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST),
						new Parameter(labelNomeSoggetto,
						SoggettiCostanti.SERVLET_NAME_SOGGETTI_CHANGE, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						new Parameter(ConnettoriCostanti.LABEL_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_CERTIFICATI, 
								SoggettiCostanti.SERVLET_NAME_SOGGETTI_CREDENZIALI_LIST, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						nomeCredenziale
						);
				
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());

				dati = soggettiHelper.addSoggettoHiddenToDati(dati, id,nomeprov, tipoprov);
				
				dati = soggettiHelper.addCredenzialiCertificatiToDati(dati, tipoOperazione, idCredenziale, tipoauthSoggetto, subjectSoggetto, toCall, 
						tipoCredenzialiSSLSorgente, tipoCredenzialiSSLTipoArchivio, 
						tipoCredenzialiSSLFileCertificato, tipoCredenzialiSSLFileCertificatoPassword, listaAliasEstrattiCertificato, 
						tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLAliasCertificatoSubject, tipoCredenzialiSSLAliasCertificatoIssuer,
						tipoCredenzialiSSLAliasCertificatoType, tipoCredenzialiSSLAliasCertificatoVersion, tipoCredenzialiSSLAliasCertificatoSerialNumber, 
						tipoCredenzialiSSLAliasCertificatoSelfSigned, tipoCredenzialiSSLAliasCertificatoNotBefore, tipoCredenzialiSSLAliasCertificatoNotAfter,
						tipoCredenzialiSSLVerificaTuttiICampi, tipoCredenzialiSSLConfigurazioneManualeSelfSigned, issuerSoggetto, tipoCredenzialiSSLWizardStep, 
						promuoviCertificato,
						visualizzaPromuoviCertificato, servletCredenzialiChange, parametersServletCredenzialiChange);
				
				dati = soggettiHelper.addCredenzialiCertificatiToDati(dati, tipoOperazione, idCredenziale, tipoauthSoggetto, subjectSoggetto, toCall, 
						tipoCredenzialiSSLSorgente, tipoCredenzialiSSLTipoArchivio, 
						tipoCredenzialiSSLFileCertificato, tipoCredenzialiSSLFileCertificatoPassword, listaAliasEstrattiCertificato, 
						tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLAliasCertificatoSubject, tipoCredenzialiSSLAliasCertificatoIssuer,
						tipoCredenzialiSSLAliasCertificatoType, tipoCredenzialiSSLAliasCertificatoVersion, tipoCredenzialiSSLAliasCertificatoSerialNumber, 
						tipoCredenzialiSSLAliasCertificatoSelfSigned, tipoCredenzialiSSLAliasCertificatoNotBefore, tipoCredenzialiSSLAliasCertificatoNotAfter,
						tipoCredenzialiSSLVerificaTuttiICampi, tipoCredenzialiSSLConfigurazioneManualeSelfSigned, issuerSoggetto, tipoCredenzialiSSLWizardStep, 
						promuoviCertificato,
						visualizzaPromuoviCertificato, servletCredenzialiChange, parametersServletCredenzialiChange);
				
				pd.setDati(dati);
				
				String pre = Costanti.HTML_MODAL_SPAN_PREFIX;
				String post = Costanti.HTML_MODAL_SPAN_SUFFIX;
				
				String msg = "Vuoi promuovere il certificato visualizzato come principale per il Soggetto {0}?";

				pd.setMessage(pre + MessageFormat.format(msg, labelNomeSoggetto) + post, Costanti.MESSAGE_TYPE_CONFIRM);

				String[][] bottoni = { 
						{ Costanti.LABEL_MONITOR_BUTTON_ANNULLA, 
							Costanti.LABEL_MONITOR_BUTTON_ANNULLA_CONFERMA_PREFIX +
							Costanti.LABEL_MONITOR_BUTTON_ANNULLA_CONFERMA_SUFFIX

						},
						{ Costanti.LABEL_MONITOR_BUTTON_CONFERMA,
							Costanti.LABEL_MONITOR_BUTTON_ESEGUI_OPERAZIONE_CONFERMA_PREFIX +
							Costanti.LABEL_MONITOR_BUTTON_ESEGUI_OPERAZIONE_CONFERMA_SUFFIX }};

				pd.setBottoni(bottoni );
				
				// disabilito la form
				pd.disableEditMode();

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
				
				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_CREDENZIALI, ForwardParams.CHANGE());
			}
			
			// Se nomehid = null, devo visualizzare la pagina per la modifica dati
			if(ServletUtils.isEditModeInProgress(this.editMode) || checkWizard){

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, 
						new Parameter(SoggettiCostanti.LABEL_SOGGETTI, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST),
						new Parameter(labelNomeSoggetto,
						SoggettiCostanti.SERVLET_NAME_SOGGETTI_CHANGE, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						new Parameter(ConnettoriCostanti.LABEL_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_CERTIFICATI, 
								SoggettiCostanti.SERVLET_NAME_SOGGETTI_CREDENZIALI_LIST, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						nomeCredenziale
						);
				
				if (tipoauthSoggetto == null){
					if (credenziali != null){
						if(credenziali.getTipo()!=null)
							tipoauthSoggetto = credenziali.getTipo().toString();
						
						if(credenziali.getCertificate() != null) {
							tipoCredenzialiSSLFileCertificato.setValue(credenziali.getCertificate());
							tipoCredenzialiSSLSorgente = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_UPLOAD_CERTIFICATO;
							tipoCredenzialiSSLVerificaTuttiICampi = credenziali.isCertificateStrictVerification() ? Costanti.CHECK_BOX_ENABLED :Costanti.CHECK_BOX_DISABLED;
							
							try {
								Certificate cSelezionato = ArchiveLoader.load(tipoCredenzialiSSLFileCertificato.getValue());
								tipoCredenzialiSSLAliasCertificatoIssuer = cSelezionato.getCertificate().getIssuer().getNameNormalized();
								tipoCredenzialiSSLAliasCertificatoSubject = cSelezionato.getCertificate().getSubject().getNameNormalized();
								tipoCredenzialiSSLAliasCertificatoSelfSigned = cSelezionato.getCertificate().isSelfSigned() ? CostantiControlStation.LABEL_SI : CostantiControlStation.LABEL_NO;
								tipoCredenzialiSSLAliasCertificatoSerialNumber = cSelezionato.getCertificate().getSerialNumber() + "";
								tipoCredenzialiSSLAliasCertificatoType = cSelezionato.getCertificate().getType();
								tipoCredenzialiSSLAliasCertificatoVersion = cSelezionato.getCertificate().getVersion() + "";
								tipoCredenzialiSSLAliasCertificatoNotBefore = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotBefore());
								tipoCredenzialiSSLAliasCertificatoNotAfter = soggettiHelper.getSdfCredenziali().format(cSelezionato.getCertificate().getNotAfter());
							}catch(UtilsException e) {
								pd.setMessage("Il Certificato selezionato non &egrave; valido: "+e.getMessage());
								tipoCredenzialiSSLAliasCertificato = "";
								tipoCredenzialiSSLAliasCertificatoSubject= "";
								tipoCredenzialiSSLAliasCertificatoIssuer= "";
								tipoCredenzialiSSLAliasCertificatoType= "";
								tipoCredenzialiSSLAliasCertificatoVersion= "";
								tipoCredenzialiSSLAliasCertificatoSerialNumber= "";
								tipoCredenzialiSSLAliasCertificatoSelfSigned= "";
								tipoCredenzialiSSLAliasCertificatoNotBefore= "";
								tipoCredenzialiSSLAliasCertificatoNotAfter = "";
							}
						} else {
							subjectSoggetto = credenziali.getSubject();
							issuerSoggetto = credenziali.getIssuer();
							tipoCredenzialiSSLSorgente = ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_CONFIGURAZIONE_MANUALE;
							tipoCredenzialiSSLConfigurazioneManualeSelfSigned = ( subjectSoggetto != null && subjectSoggetto.equals(issuerSoggetto)) ? Costanti.CHECK_BOX_ENABLED :Costanti.CHECK_BOX_DISABLED;
						}
					}
				}
				if (tipoauthSoggetto == null) {
					tipoauthSoggetto = ConnettoriCostanti.DEFAULT_AUTENTICAZIONE_TIPO;
				}

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati.addElement(ServletUtils.getDataElementForEditModeFinished());


				dati = soggettiHelper.addSoggettoHiddenToDati(dati, id,nomeprov, tipoprov);
				
				dati = soggettiHelper.addCredenzialiCertificatiToDati(dati, tipoOperazione, idCredenziale, tipoauthSoggetto, subjectSoggetto, toCall, 
						tipoCredenzialiSSLSorgente, tipoCredenzialiSSLTipoArchivio, 
						tipoCredenzialiSSLFileCertificato, tipoCredenzialiSSLFileCertificatoPassword, listaAliasEstrattiCertificato, 
						tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLAliasCertificatoSubject, tipoCredenzialiSSLAliasCertificatoIssuer,
						tipoCredenzialiSSLAliasCertificatoType, tipoCredenzialiSSLAliasCertificatoVersion, tipoCredenzialiSSLAliasCertificatoSerialNumber, 
						tipoCredenzialiSSLAliasCertificatoSelfSigned, tipoCredenzialiSSLAliasCertificatoNotBefore, tipoCredenzialiSSLAliasCertificatoNotAfter,
						tipoCredenzialiSSLVerificaTuttiICampi, tipoCredenzialiSSLConfigurazioneManualeSelfSigned, issuerSoggetto, tipoCredenzialiSSLWizardStep, 
						promuoviCertificato,
						visualizzaPromuoviCertificato, servletCredenzialiChange, parametersServletCredenzialiChange);
			
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_CREDENZIALI, ForwardParams.CHANGE());

			}

			// Controlli sui campi immessi
			boolean isOk = soggettiHelper.soggettiCredenzialiCertificatiCheckData(tipoOperazione, id, soggettoRegistry, idxCredenziale);

			if (!isOk) {

				// setto la barra del titolo
				ServletUtils.setPageDataTitle(pd, 
						new Parameter(SoggettiCostanti.LABEL_SOGGETTI, SoggettiCostanti.SERVLET_NAME_SOGGETTI_LIST),
						new Parameter(labelNomeSoggetto,
						SoggettiCostanti.SERVLET_NAME_SOGGETTI_CHANGE, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						new Parameter(ConnettoriCostanti.LABEL_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_CERTIFICATI, 
								SoggettiCostanti.SERVLET_NAME_SOGGETTI_CREDENZIALI_LIST, parametersServletSoggettoChange.toArray(new Parameter[parametersServletSoggettoChange.size()])),
						nomeCredenziale
						);

				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();

				dati = soggettiHelper.addSoggettoHiddenToDati(dati, id,nomeprov, tipoprov);
				
				dati = soggettiHelper.addCredenzialiCertificatiToDati(dati, tipoOperazione, idCredenziale, tipoauthSoggetto, subjectSoggetto, toCall, 
						tipoCredenzialiSSLSorgente, tipoCredenzialiSSLTipoArchivio, 
						tipoCredenzialiSSLFileCertificato, tipoCredenzialiSSLFileCertificatoPassword, listaAliasEstrattiCertificato, 
						tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLAliasCertificatoSubject, tipoCredenzialiSSLAliasCertificatoIssuer,
						tipoCredenzialiSSLAliasCertificatoType, tipoCredenzialiSSLAliasCertificatoVersion, tipoCredenzialiSSLAliasCertificatoSerialNumber, 
						tipoCredenzialiSSLAliasCertificatoSelfSigned, tipoCredenzialiSSLAliasCertificatoNotBefore, tipoCredenzialiSSLAliasCertificatoNotAfter,
						tipoCredenzialiSSLVerificaTuttiICampi, tipoCredenzialiSSLConfigurazioneManualeSelfSigned, issuerSoggetto, tipoCredenzialiSSLWizardStep, 
						promuoviCertificato,
						visualizzaPromuoviCertificato, servletCredenzialiChange, parametersServletCredenzialiChange);

				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_CREDENZIALI, ForwardParams.CHANGE());

			}

			// Aggiorno credenziali
			//credenziali = new CredenzialiSoggetto();
			credenziali.setTipo(CredenzialeTipo.toEnumConstant(tipoauthSoggetto));
			credenziali.setUser("");
			credenziali.setPassword("");
			
			credenziali.setAppId(false);
			
			if(ConnettoriCostanti.AUTENTICAZIONE_TIPO_SSL.equals(tipoauthSoggetto)) {
				if(tipoCredenzialiSSLSorgente.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_UPLOAD_CERTIFICATO)) {
					Certificate cSelezionato = null;
					
					// il certificato non e' cambiato 
					if(tipoCredenzialiSSLWizardStep.equals(ConnettoriCostanti.VALUE_PARAMETRO_CREDENZIALI_AUTENTICAZIONE_CONFIGURAZIONE_SSL_NO_WIZARD)) {
						cSelezionato = ArchiveLoader.load(oldCredenziali.getCertificate());
					} else {
						if(tipoCredenzialiSSLTipoArchivio.equals(ArchiveType.CER)) {
							cSelezionato = ArchiveLoader.load(tipoCredenzialiSSLFileCertificato.getValue());
						}else {
							cSelezionato = ArchiveLoader.load(tipoCredenzialiSSLTipoArchivio, tipoCredenzialiSSLFileCertificato.getValue(), tipoCredenzialiSSLAliasCertificato, tipoCredenzialiSSLFileCertificatoPassword);
						}
					}
					
					credenziali.setCnIssuer(cSelezionato.getCertificate().getIssuer().getCN());
					credenziali.setCnSubject(cSelezionato.getCertificate().getSubject().getCN()); 
					credenziali.setCertificate(cSelezionato.getCertificate().getCertificate().getEncoded());
					credenziali.setCertificateStrictVerification(ServletUtils.isCheckBoxEnabled(tipoCredenzialiSSLVerificaTuttiICampi));
				} else { // configurazione manuale
					credenziali.setSubject(subjectSoggetto);
					if(ServletUtils.isCheckBoxEnabled(tipoCredenzialiSSLConfigurazioneManualeSelfSigned)) {
						credenziali.setIssuer(subjectSoggetto);
					} else {
						credenziali.setIssuer(issuerSoggetto);
					}
				}
			}
			
			if(actionConfirm != null ) {
				if(actionConfirm.equals(Costanti.PARAMETRO_ACTION_CONFIRM_VALUE_OK)) {
					// switch tra le posizioni
					// Rimuovo la credenziale principale dalla posizione 0
					CredenzialiSoggetto exCredenzialiPrincipali = soggettoRegistry.getCredenzialiList().remove(0);
					// imposto l'attuale credenziale in posizione 0
					soggettoRegistry.getCredenzialiList().add(0, credenziali); // promozione = set in posizione 0
					// assegno la posizione attuale all'ex credenziale principale
					soggettoRegistry.getCredenzialiList().set(idxCredenziale, exCredenzialiPrincipali);
				}
			}

			SoggettoCtrlStat sog = new SoggettoCtrlStat(soggettoRegistry, soggettoConfig);
			sog.setOldNomeForUpdate(nomeprov);
			sog.setOldTipoForUpdate(tipoprov);

			// eseguo l'aggiornamento
			List<Object> listOggettiDaAggiornare = SoggettiUtilities.getOggettiDaAggiornare(soggettiCore, nomeprov, nomeprov, tipoprov, tipoprov, sog);
			soggettiCore.performUpdateOperation(userLogin, soggettiHelper.smista(), listOggettiDaAggiornare.toArray());

			soggettiHelper.deleteBinaryParameters(tipoCredenzialiSSLFileCertificato); 
			
			soggettoRegistry = soggettiCore.getSoggettoRegistro(idSogg);
			
			soggettiHelper.prepareSoggettiCredenzialiList(soggettoRegistry, id);
				
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

			return ServletUtils.getStrutsForwardEditModeFinished(mapping, SoggettiCostanti.OBJECT_NAME_SOGGETTI_CREDENZIALI, ForwardParams.CHANGE());

		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					SoggettiCostanti.OBJECT_NAME_SOGGETTI_CREDENZIALI, ForwardParams.CHANGE());
		} 
	}
}
