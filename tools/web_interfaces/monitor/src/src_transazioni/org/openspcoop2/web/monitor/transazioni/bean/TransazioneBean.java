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
package org.openspcoop2.web.monitor.transazioni.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.commons.search.IdAccordoServizioParteComune;
import org.openspcoop2.core.commons.search.IdSoggetto;
import org.openspcoop2.core.commons.search.Resource;
import org.openspcoop2.core.commons.search.dao.IResourceServiceSearch;
import org.openspcoop2.core.config.constants.TipoAutenticazione;
import org.openspcoop2.core.constants.CostantiDB;
import org.openspcoop2.core.id.IDAccordo;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.driver.IDAccordoFactory;
import org.openspcoop2.core.transazioni.Transazione;
import org.openspcoop2.core.transazioni.constants.PddRuolo;
import org.openspcoop2.core.transazioni.constants.TipoAPI;
import org.openspcoop2.core.transazioni.utils.TempiElaborazione;
import org.openspcoop2.core.transazioni.utils.TempiElaborazioneUtils;
import org.openspcoop2.generic_project.exception.MultipleResultException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.generic_project.expression.IPaginatedExpression;
import org.openspcoop2.message.constants.MessageType;
import org.openspcoop2.monitor.engine.condition.EsitoUtils;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.engine.utils.NamingUtils;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.protocol.sdk.constants.EsitoTransazioneName;
import org.openspcoop2.protocol.utils.EsitiProperties;
import org.openspcoop2.protocol.utils.PorteNamingUtils;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.beans.BlackListElement;
import org.openspcoop2.utils.certificate.CertificateUtils;
import org.openspcoop2.utils.certificate.PrincipalType;
import org.openspcoop2.utils.json.JSONUtils;
import org.openspcoop2.web.monitor.core.core.Utils;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.utils.BeanUtils;
import org.openspcoop2.web.monitor.core.utils.MessageManager;
import org.openspcoop2.web.monitor.transazioni.constants.TransazioniCostanti;
import org.openspcoop2.web.monitor.transazioni.dao.TransazioniService;
import org.slf4j.Logger;

/**
 * TransazioneBean
 * 
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 *
 */
public class TransazioneBean extends Transazione{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long latenzaTotale = null;
	private Long latenzaServizio = null;
	private Long latenzaPorta = null;

	private java.lang.String trasportoMittenteLabel = null;
	private java.lang.String tipoTrasportoMittenteLabel = null;
	private java.lang.String tokenIssuerLabel = null;
	private java.lang.String tokenClientIdLabel = null;
	private java.lang.String tokenSubjectLabel = null;
	private java.lang.String tokenUsernameLabel = null;
	private java.lang.String tokenMailLabel = null;
	private java.lang.String eventiLabel = null;
	private java.lang.String gruppiLabel = null;
	private java.lang.String operazioneLabel;

	private String soggettoPddMonitor;
	
	public TransazioneBean() {
		super();
	}

	public TransazioneBean(Transazione transazione, String soggettoPddMonitor){
		
		this.soggettoPddMonitor = soggettoPddMonitor;
		
		List<BlackListElement> metodiEsclusi = new ArrayList<BlackListElement>(
				0);
		metodiEsclusi.add(new BlackListElement("setLatenzaTotale", Long.class));
		metodiEsclusi.add(new BlackListElement("setLatenzaServizio", Long.class));
		metodiEsclusi.add(new BlackListElement("setLatenzaPorta", Long.class));
		metodiEsclusi.add(new BlackListElement("setTrasportoMittenteLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTipoTrasportoMittenteLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTokenIssuerLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTokenClientIdLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTokenSubjectLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTokenUsernameLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setTokenMailLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setEventiLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setGruppiLabel", String.class));
		metodiEsclusi.add(new BlackListElement("setOperazioneLabel", String.class));

		BeanUtils.copy(this, transazione, metodiEsclusi);
	}

	public Long getLatenzaTotale() {
		if(this.latenzaTotale == null){
			if(this.dataUscitaRisposta != null && this.dataIngressoRichiesta != null){
				this.latenzaTotale = this.dataUscitaRisposta.getTime() - this.dataIngressoRichiesta.getTime();
			}
		}

		if(this.latenzaTotale == null)
			return -1L;

		return this.latenzaTotale;
	}

	public void setLatenzaTotale(Long latenzaTotale) {
		this.latenzaTotale = latenzaTotale;
	}

	public Long getLatenzaServizio() {
		if(this.latenzaServizio == null){
			if(this.dataUscitaRichiesta != null && this.dataIngressoRisposta != null){
				this.latenzaServizio = this.dataIngressoRisposta.getTime() - this.dataUscitaRichiesta.getTime();
			}
		}

		if(this.latenzaServizio == null)
			return -1L;

		return this.latenzaServizio;
	}

	public void setLatenzaServizio(Long latenzaServizio) {
		this.latenzaServizio = latenzaServizio;
	}

	public Long getLatenzaPorta() {
		if(this.latenzaPorta == null){
			if(this.getLatenzaTotale() != null && this.getLatenzaTotale()>=0)
				if(this.getLatenzaServizio() != null && this.getLatenzaServizio()>=0)
					this.latenzaPorta = this.getLatenzaTotale().longValue() - this.getLatenzaServizio().longValue();
				else 
					this.latenzaPorta = this.getLatenzaTotale();

		}

		if(this.latenzaPorta == null)
			return -1L;

		return this.latenzaPorta;
	}

	public void setLatenzaPorta(Long latenzaPorta) {
		this.latenzaPorta = latenzaPorta;
	}

	public String getEsitoStyleClass(){

		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.getProtocollo());
			String name = esitiProperties.getEsitoName(this.getEsito());
			EsitoTransazioneName esitoName = EsitoTransazioneName.convertoTo(name);
			boolean casoSuccesso = esitiProperties.getEsitiCodeOk().contains(this.getEsito());
			if(EsitoTransazioneName.ERRORE_APPLICATIVO.equals(esitoName)){
				//return "icon-alert-orange";
				return "icon-alert-yellow";
			}
			else if(casoSuccesso){
				return "icon-verified-green";
			}
			else{
				return "icon-alert-red";
			}
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return "icon-ko";
		}

	}

	public boolean isEsitoOk(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.getProtocollo());
			boolean res = esitiProperties.getEsitiCodeOk_senzaFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public boolean isEsitoFaultApplicativo(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.getProtocollo());
			boolean res = esitiProperties.getEsitiCodeFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}
	public boolean isEsitoKo(){	
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.getProtocollo());
			boolean res = esitiProperties.getEsitiCodeKo_senzaFaultApplicativo().contains(this.getEsito());
			//System.out.println("isEsitoOk:"+res+" (esitoChecked:"+this.getEsito()+")");
			return res;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo del layout dell'esito ["+this.getEsito()+"]: "+e.getMessage(),e);
			return false;
		}
	}

	public java.lang.String getEsitoLabel() {
		try{
			EsitoUtils esitoUtils = new EsitoUtils(LoggerManager.getPddMonitorCoreLogger(), this.protocollo);
			return esitoUtils.getEsitoLabelFromValue(this.esito, false);
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo della label per l'esito ["+this.esito+"]: "+e.getMessage(),e);
			return "Conversione non riuscita";
		}
	}
	
	public java.lang.String getEsitoLabelSyntetic() {
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.protocollo);
			return esitiProperties.getEsitoLabelSyntetic(this.esito);
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo della label per l'esito ["+this.esito+"]: "+e.getMessage(),e);
			return "Conversione non riuscita";
		}
	}
	
	public java.lang.String getEsitoLabelDescription() {
		try{
			EsitiProperties esitiProperties = EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.protocollo);
			return getEsitoLabel() + " - " + esitiProperties.getEsitoDescription(this.esito);
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo della label per l'esito ["+this.esito+"]: "+e.getMessage(),e);
			return "Conversione non riuscita";
		}
	}
	
	public boolean isShowContesto(){
		try{
			return EsitiProperties.getInstance(LoggerManager.getPddMonitorCoreLogger(),this.getProtocollo()).getEsitiTransactionContextCode().size()>1;
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo dei contesti: "+e.getMessage(),e);
			return false;
		}
	}

	public java.lang.String getEsitoContestoLabel() {
		try{
			EsitoUtils esitoUtils = new EsitoUtils(LoggerManager.getPddMonitorCoreLogger(), this.protocollo);
			return esitoUtils.getEsitoContestoLabelFromValue(this.esitoContesto);
		}catch(Exception e){
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il calcolo della label per il contesto esito ["+this.esitoContesto+"]: "+e.getMessage(),e);
			return "Conversione non riuscita";
		}
	}
	
	
	public String getFaultCooperazionePretty(){
		String f = super.getFaultCooperazione();
		String toRet = null;
		if(f !=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
				messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
			}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(f));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = Utils.prettifyXml(f);
				break;
			}
		}

		if(toRet == null)
			toRet = f != null ? f : "";

		return toRet;
	}

	public boolean isVisualizzaFaultCooperazione(){
		boolean visualizzaMessaggio = true;
		String f = super.getFaultCooperazione();

		if(f == null)
			return false;

		StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
		String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
		if(errore!= null)
			return false;

		//			MessageType messageType= MessageType.XML;
		//			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
		//				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
		//			}

		//			switch (messageType) {
		//			case BINARY:
		//			case MIME_MULTIPART:
		//				// questi due casi dovrebbero essere gestiti sopra 
		//				break;	
		//			case JSON:
		//				JSONUtils jsonUtils = JSONUtils.getInstance(true);
		//				try {
		//					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
		//				} catch (UtilsException e) {
		//				}
		//				break;
		//			case SOAP_11:
		//			case SOAP_12:
		//			case XML:
		//			default:
		//				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
		//				break;
		//			}

		return visualizzaMessaggio;
	}

	public String getBrushFaultCooperazione() {
		String toRet = null;
		String f = super.getFaultCooperazione();
		if(f!=null) {
			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(super.getFormatoFaultCooperazione())) {
				messageType = MessageType.valueOf(super.getFormatoFaultCooperazione());
			}

			switch (messageType) {
			case JSON:
				toRet = "json";
				break;
			case BINARY:
			case MIME_MULTIPART:
				// per ora restituisco il default
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = "xml";
				break;
			}
		}

		return toRet;
	}

	public String getErroreVisualizzaFaultCooperazione(){
		String f = super.getFaultCooperazione();
		if(f!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			return errore;
		}

		return null;
	}

	public String getFaultIntegrazionePretty(){
		String f = super.getFaultIntegrazione();
		String toRet = null;
		if(f !=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			if(errore!= null)
				return "";

			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(super.getFormatoFaultIntegrazione())) {
				messageType = MessageType.valueOf(super.getFormatoFaultIntegrazione());
			}

			switch (messageType) {
			case BINARY:
			case MIME_MULTIPART:
				// questi due casi dovrebbero essere gestiti sopra 
				break;	
			case JSON:
				JSONUtils jsonUtils = JSONUtils.getInstance(true);
				try {
					toRet = jsonUtils.toString(jsonUtils.getAsNode(f));
				} catch (UtilsException e) {
				}
				break;
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = Utils.prettifyXml(f);
				break;
			}
		}

		if(toRet == null)
			toRet = f != null ? f : "";

		return toRet;
	}

	public boolean isVisualizzaFaultIntegrazione(){
		boolean visualizzaMessaggio = true;
		String f = super.getFaultIntegrazione();

		if(f == null)
			return false;

		StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
		String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
		if(errore!= null)
			return false;

		//			MessageType messageType= MessageType.XML;
		//			if(StringUtils.isNotEmpty(this.dumpMessaggio.getFormatoMessaggio())) {
		//				messageType = MessageType.valueOf(this.dumpMessaggio.getFormatoMessaggio());
		//			}

		//			switch (messageType) {
		//			case BINARY:
		//			case MIME_MULTIPART:
		//				// questi due casi dovrebbero essere gestiti sopra 
		//				break;	
		//			case JSON:
		//				JSONUtils jsonUtils = JSONUtils.getInstance(true);
		//				try {
		//					toRet = jsonUtils.toString(jsonUtils.getAsNode(this.dumpMessaggio.getBody()));
		//				} catch (UtilsException e) {
		//				}
		//				break;
		//			case SOAP_11:
		//			case SOAP_12:
		//			case XML:
		//			default:
		//				toRet = Utils.prettifyXml(this.dumpMessaggio.getBody());
		//				break;
		//			}

		return visualizzaMessaggio;
	}

	public String getBrushFaultIntegrazione() {
		String toRet = null;
		String f = super.getFaultIntegrazione();
		if(f!=null) {
			MessageType messageType= MessageType.XML;
			if(StringUtils.isNotEmpty(super.getFormatoFaultIntegrazione())) {
				messageType = MessageType.valueOf(super.getFormatoFaultIntegrazione());
			}

			switch (messageType) {
			case JSON:
				toRet = "json";
				break;
			case BINARY:
			case MIME_MULTIPART:
				// per ora restituisco il default
			case SOAP_11:
			case SOAP_12:
			case XML:
			default:
				toRet = "xml";
				break;
			}
		}

		return toRet;
	}

	public String getErroreVisualizzaFaultIntegrazione(){
		String f = super.getFaultIntegrazione();
		if(f!=null) {
			StringBuffer contenutoDocumentoStringBuffer = new StringBuffer();
			String errore = Utils.getTestoVisualizzabile(f.getBytes(),contenutoDocumentoStringBuffer);
			return errore;
		}

		return null;
	}

	@Override
	public String getNomePorta(){
		String nomePorta = super.getNomePorta();
		if(nomePorta!=null && nomePorta.startsWith("null_") && (nomePorta.length()>"null_".length())){
			return nomePorta.substring("null_".length());
		}
		else{
			return nomePorta;
		}
	}

	public String getSoggettoFruitore() throws Exception { 
		if(StringUtils.isNotEmpty(this.getNomeSoggettoFruitore())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getTipoSoggettoFruitore(), this.getNomeSoggettoFruitore());
		}

		return "";
	}

	public String getSoggettoErogatore() throws Exception { 
		if(StringUtils.isNotEmpty(this.getNomeSoggettoErogatore())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getTipoSoggettoErogatore(), this.getNomeSoggettoErogatore());
		}

		return "";
	}

	public String getSoggettoPdd() throws Exception { 
		if(StringUtils.isNotEmpty(this.getPddNomeSoggetto())) {
			return NamingUtils.getLabelSoggetto(this.getProtocollo(), this.getPddTipoSoggetto(), this.getPddNomeSoggetto());
		}

		return "";
	}

	public String getProtocolloLabel() throws Exception{
		return NamingUtils.getLabelProtocollo(this.getProtocollo()); 
	}


	public String getServizio() throws Exception{
		if(StringUtils.isNotEmpty(this.getNomeServizio())) {
			return NamingUtils.getLabelAccordoServizioParteSpecificaSenzaErogatore(this.getProtocollo(), this.getTipoServizio(), this.getNomeServizio(), this.getVersioneServizio());
		}
		return "";
	}

	public String getPortaLabel() throws Exception{
		IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByName(this.getProtocollo());
		PorteNamingUtils n = new PorteNamingUtils(protocolFactory);
		switch(this.getPddRuolo()) {
		case APPLICATIVA:
			return n.normalizePA(this.getNomePorta());
		case DELEGATA:
			return n.normalizePD(this.getNomePorta());
		case INTEGRATION_MANAGER:
		case ROUTER:
		default:
			return this.getNomePorta();
		}
	}

	public java.lang.String getTrasportoMittenteLabel() {
		return this.trasportoMittenteLabel;
	}

	public void setTrasportoMittenteLabel(java.lang.String trasportoMittenteLabel) {
		this.trasportoMittenteLabel = trasportoMittenteLabel;
	}

	public java.lang.String getTipoTrasportoMittenteLabel() {
		return this.tipoTrasportoMittenteLabel;
	}

	public void setTipoTrasportoMittenteLabel(java.lang.String tipoTrasportoMittenteLabel) {
		this.tipoTrasportoMittenteLabel = tipoTrasportoMittenteLabel;
	}
	
	public java.lang.String getTokenIssuerLabel() {
		return this.tokenIssuerLabel;
	}

	public void setTokenIssuerLabel(java.lang.String tokenIssuerLabel) {
		this.tokenIssuerLabel = tokenIssuerLabel;
	}

	public java.lang.String getTokenClientIdLabel() {
		return this.tokenClientIdLabel;
	}

	public void setTokenClientIdLabel(java.lang.String tokenClientIdLabel) {
		this.tokenClientIdLabel = tokenClientIdLabel;
	}

	public java.lang.String getTokenSubjectLabel() {
		return this.tokenSubjectLabel;
	}

	public void setTokenSubjectLabel(java.lang.String tokenSubjectLabel) {
		this.tokenSubjectLabel = tokenSubjectLabel;
	}

	public java.lang.String getTokenUsernameLabel() {
		return this.tokenUsernameLabel;
	}

	public void setTokenUsernameLabel(java.lang.String tokenUsernameLabel) {
		this.tokenUsernameLabel = tokenUsernameLabel;
	}

	public java.lang.String getTokenMailLabel() {
		return this.tokenMailLabel;
	}

	public void setTokenMailLabel(java.lang.String tokenMailLabel) {
		this.tokenMailLabel = tokenMailLabel;
	}

	
	
	
	@Override
	public String getEventiGestione() {
		return this.eventiLabel;
	}
	public String getEventiGestioneRawValue() {
		return this.eventiGestione;
	}
	
	public String getEventiGestioneHTML(){
		String tmp = this.getEventiGestione();
		if(tmp!=null){
			tmp = tmp.trim();
			if(tmp.contains(",")){
				String [] split = tmp.split(",");
				if(split!=null && split.length>0){
					StringBuffer bf = new StringBuffer();
					for (int i = 0; i < split.length; i++) {
						if(bf.length()>0){
							bf.append("<BR/>");
						}
						bf.append(split[i].trim());
					}
					return bf.toString();
				}
				else{
					return tmp;
				}
			}
			else{
				return tmp;
			}
		}
		else{
			return null;
		}
	}
	
	public java.lang.String getEventiLabel() {
		return this.eventiLabel;
	}

	public void setEventiLabel(java.lang.String eventiLabel) {
		this.eventiLabel = eventiLabel;
	}

	
	@Override
	public String getGruppi() {
		return this.gruppiLabel;
	}
	public String getGruppiRawValue() {
		return this.gruppi;
	}
	
	public String getGruppiHTML(){
		String tmp = this.getGruppi();
		if(tmp!=null){
			tmp = tmp.trim();
			if(tmp.contains(",")){
				String [] split = tmp.split(",");
				if(split!=null && split.length>0){
					StringBuffer bf = new StringBuffer();
					for (int i = 0; i < split.length; i++) {
						if(bf.length()>0){
							bf.append("<BR/>");
						}
						bf.append(split[i].trim());
					}
					return bf.toString();
				}
				else{
					return tmp;
				}
			}
			else{
				return tmp;
			}
		}
		else{
			return null;
		}
	}
	
	public java.lang.String getGruppiLabel() {
		return this.gruppiLabel;
	}

	public void setGruppiLabel(java.lang.String gruppiLabel) {
		this.gruppiLabel = gruppiLabel;
	}
	
	
	
	
	public TempiElaborazioneBean getTempiElaborazioneObject() {
		String tempiElaborazione = this.getTempiElaborazione();
		try {
			TempiElaborazione tempi = TempiElaborazioneUtils.convertFromDBValue(tempiElaborazione);
			if(tempi!=null) {
				return new TempiElaborazioneBean(tempi);
			}
			else {
				return null;
			}
		}catch(Exception e) {
			LoggerManager.getPddMonitorCoreLogger().error("Errore durante il processamento dei tempi di elaborazione ["+tempiElaborazione+"]",e);
			return null;
		}
	}
	
	public String getTipoApiLabel() {
		if(TipoAPI.REST.getValoreAsInt() == this.getTipoApi()) {
			return "Rest";
		}
		else if(TipoAPI.SOAP.getValoreAsInt() == this.getTipoApi()) {
			return "Soap";
		}
		else {
			return "";
		}
	}
	
	public boolean isShowProfiloCollaborazione() {
		return TipoAPI.SOAP.getValoreAsInt() == this.getTipoApi();
	}
	
	public String getPddRuoloToolTip() {
		PddRuolo pddRuolo = this.getPddRuolo();
		if(pddRuolo!=null) {
			switch (pddRuolo) {
			case DELEGATA:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_DELEGATA_LABEL_KEY);
			case APPLICATIVA:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_APPLICATIVA_LABEL_KEY);
			case ROUTER:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_ROUTER_LABEL_KEY);
			case INTEGRATION_MANAGER:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_IM_LABEL_KEY);
			}
		}
		return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_ROUTER_LABEL_KEY);
	}
	
	public String getPddRuoloImage() {
		PddRuolo pddRuolo = this.getPddRuolo();
		if(pddRuolo!=null) {
			switch (pddRuolo) {
			case DELEGATA:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_DELEGATA_ICON_KEY);
			case APPLICATIVA:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_APPLICATIVA_ICON_KEY);
			case ROUTER:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_ROUTER_ICON_KEY);
			case INTEGRATION_MANAGER:
				return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_IM_ICON_KEY);
			}
		}
		return MessageManager.getInstance().getMessage(TransazioniCostanti.TRANSAZIONI_ELENCO_RUOLO_PDD_ROUTER_ICON_KEY);
	}
	
	public void normalizeRichiedenteInfo(Transazione t, TransazioneBean transazioneBean, TransazioniService transazioniService) throws ServiceException, MultipleResultException, NotImplementedException {
		
		// 1) Username del Token
		String sTokenUsername = getTokenUsername();
		if(StringUtils.isNotEmpty(sTokenUsername)) {
			transazioniService.normalizeInfoTransazioniFromCredenzialiMittenteTokenUsername(transazioneBean, t);
			return;
		}
		
		// 2) Subject/Issuer del Token
		String sTokenSubject = getTokenSubject();
		if(StringUtils.isNotEmpty(sTokenSubject)) {
			transazioniService.normalizeInfoTransazioniFromCredenzialiMittenteTokenSubject(transazioneBean, t);
			String sTokenIssuer = getTokenIssuer();
			if(StringUtils.isNotEmpty(sTokenIssuer)) {
				transazioniService.normalizeInfoTransazioniFromCredenzialiMittenteTokenIssuer(transazioneBean, t);
			}
			return;
		}
		
		// 3) Applicativo Fruitore
		String sApplicativoFruitore = getServizioApplicativoFruitore();
		if(StringUtils.isNotEmpty(sApplicativoFruitore)) {
			return;
		}
		
		// 4) Credenziali dell'autenticazione di trasporto
		String sTrasportoMittente = getTrasportoMittente();
		if(StringUtils.isNotEmpty(sTrasportoMittente)) {
			transazioniService.normalizeInfoTransazioniFromCredenzialiMittenteTrasporto(transazioneBean, t);
			return;
		}
		
		// 5) Client ID, per il caso di ClientCredential
		String sTokenClientId = getTokenClientId();
		if(StringUtils.isNotEmpty(sTokenClientId)) {
			transazioniService.normalizeInfoTransazioniFromCredenzialiMittenteTokenClientID(transazioneBean, t);
			return;
		}

	}
	
	public String getRichiedente() {
				
		// 1) Username del Token
		String sTokenUsername = getTokenUsernameLabel();
		if(StringUtils.isNotEmpty(sTokenUsername)) {
			return sTokenUsername;
		}
		
		// 2) Subject/Issuer del Token
		String sTokenSubject = getTokenSubjectLabel();
		if(StringUtils.isNotEmpty(sTokenSubject)) {
			
			String sTokenIssuer = getTokenIssuerLabel();
			if(StringUtils.isNotEmpty(sTokenIssuer)) {
				return sTokenSubject + org.openspcoop2.web.monitor.core.constants.Costanti.LABEL_DOMINIO + sTokenIssuer;
			}
			else {
				return sTokenSubject;
			}
		}
		
		// 3) Applicativo Fruitore
		String sApplicativoFruitore = getServizioApplicativoFruitore();
		if(StringUtils.isNotEmpty(sApplicativoFruitore)) {
			return sApplicativoFruitore;
		}
		
		// 4) Credenziali dell'autenticazione di trasporto
		// volutamente uso l'id autenticato.
		// se l'api è pubblica non deve essere visualizzata questa informazione!
		String sTrasportoMittente = getTrasportoMittenteLabel();
		String sTipoTrasportoMittente = getTipoTrasportoMittenteLabel();
		if(StringUtils.isNotEmpty(sTrasportoMittente) && StringUtils.isNotEmpty(sTipoTrasportoMittente)) {
			if(sTipoTrasportoMittente.endsWith("_"+TipoAutenticazione.SSL.getValue())) {
				try {
					Hashtable<String, List<String>> l = CertificateUtils.getPrincipalIntoHashtable(sTrasportoMittente, PrincipalType.subject);
					if(l!=null && !l.isEmpty()) {
						List<String> cnList = l.get("CN");
						if(cnList==null || cnList.isEmpty()) {
							cnList = l.get("cn");
						}
						if(cnList==null || cnList.isEmpty()) {
							cnList = l.get("Cn");
						}
						if(cnList==null || cnList.isEmpty()) {
							cnList = l.get("cN");
						}						
						if(cnList!=null && cnList.size()>0) {
							StringBuffer bfList = new StringBuffer();
							for (String s : cnList) {
								if(bfList.length()>0) {
									bfList.append(", ");
								}
								bfList.append(s);
							}
							return bfList.toString();
						}
					}
					return sTrasportoMittente;
				}catch(Throwable t) {	
					return sTrasportoMittente;
				}
			}
			else {
				return sTrasportoMittente;
			}
		}
		
		// 5) Client ID, per il caso di ClientCredential
		String sTokenClientId = getTokenClientIdLabel();
		if(StringUtils.isNotEmpty(sTokenClientId)) {
			return sTokenClientId;
		}
		
		return null;
		
	}
	
	public String getLabelRichiedenteConFruitore() throws Exception {
		StringBuffer bf = new StringBuffer();
		
		String richiedente = getRichiedente();
		if(StringUtils.isNotEmpty(richiedente)) {
			bf.append(richiedente);	
		}
		
		
		String sFruitore = getSoggettoFruitore();
		if(StringUtils.isNotEmpty(sFruitore)) {
		
			boolean addFruitore = true;
			
			if(org.openspcoop2.core.transazioni.constants.PddRuolo.DELEGATA.equals(this.getPddRuolo())) {
				if(this.soggettoPddMonitor!=null && StringUtils.isNotEmpty(this.getTipoSoggettoFruitore()) && StringUtils.isNotEmpty(this.getNomeSoggettoFruitore())) {
					IDSoggetto idSoggettoFruitore = new IDSoggetto(this.getTipoSoggettoFruitore(), this.getNomeSoggettoFruitore());
					addFruitore = !this.soggettoPddMonitor.equals(idSoggettoFruitore.toString());
				}
			}
			
			if(addFruitore) {
				if(bf.length()>0) {
					bf.append(org.openspcoop2.web.monitor.core.constants.Costanti.LABEL_DOMINIO);
				}
				
				bf.append(sFruitore);	
			}
		}
		
		return bf.toString();
	}
	
	public String getLabelAPIConErogatore() throws Exception {
		StringBuffer bf = new StringBuffer();
		
		String api = getServizio();
		if(StringUtils.isNotEmpty(api)) {
			bf.append(api);	
		}
		else {
			return "API non individuata";
		}
		
		
		String sErogatore = getSoggettoErogatore();
		if(StringUtils.isNotEmpty(sErogatore)) {
		
			boolean addErogatore = true;
			
			if(org.openspcoop2.core.transazioni.constants.PddRuolo.APPLICATIVA.equals(this.getPddRuolo())) {
				if(this.soggettoPddMonitor!=null && StringUtils.isNotEmpty(this.getTipoSoggettoErogatore()) && StringUtils.isNotEmpty(this.getNomeSoggettoErogatore())) {
					IDSoggetto idSoggettoErogatore = new IDSoggetto(this.getTipoSoggettoErogatore(), this.getNomeSoggettoErogatore());
					addErogatore = !this.soggettoPddMonitor.equals(idSoggettoErogatore.toString());
				}
			}
			
			if(addErogatore) {
				
				if(bf.length()>0) {
					if(api.contains(" ")) {
						String [] split = api.split(" ");
						if(split!=null && split.length==2) {
							bf = new StringBuffer();
							bf.append(split[0]);
							bf.append(org.openspcoop2.web.monitor.core.constants.Costanti.LABEL_DOMINIO);
							bf.append(sErogatore);
							bf.append(" ");
							bf.append(split[1]);
						}
						else {
							bf.append(org.openspcoop2.web.monitor.core.constants.Costanti.LABEL_DOMINIO);
							bf.append(sErogatore);	
						}
					}
					else {
						bf.append(org.openspcoop2.web.monitor.core.constants.Costanti.LABEL_DOMINIO);
						bf.append(sErogatore);	
					}
				}
				else {
					bf.append(sErogatore);	
				}
			}
		}
		
		return bf.toString();
	}
	
	public java.lang.String getTipoOperazioneLabel() {
		if(TipoAPI.REST.getValoreAsInt() == this.getTipoApi()) {
			return "Risorsa";
		}
		else {
			return "Azione";
		}
	}
	
	public java.lang.String getOperazioneLabel() {
		return this.operazioneLabel;
	}

	public void setOperazioneLabel(java.lang.String operazioneLabel) {
		this.operazioneLabel = operazioneLabel;
	}
	
	public void normalizeOperazioneInfo(org.openspcoop2.core.commons.search.dao.IServiceManager service, Logger log) {
		
		if(TipoAPI.REST.getValoreAsInt() == this.getTipoApi()) {
			
			String uriAPI = this.getUriAccordoServizio();
			String op = getAzione();
			try {
			
				if(StringUtils.isNotEmpty(uriAPI) && StringUtils.isNotEmpty(op)) {
					IDAccordo idAccordo = IDAccordoFactory.getInstance().getIDAccordoFromUri(uriAPI);
					IdAccordoServizioParteComune idAPI = new IdAccordoServizioParteComune();
					idAPI.setIdSoggetto(new IdSoggetto());
					idAPI.getIdSoggetto().setTipo(idAccordo.getSoggettoReferente().getTipo());
					idAPI.getIdSoggetto().setNome(idAccordo.getSoggettoReferente().getNome());
					idAPI.setVersione(idAccordo.getVersione());
					idAPI.setNome(idAccordo.getNome());
					
					
					IResourceServiceSearch resourceServiceSearch = service.getResourceServiceSearch();
					IPaginatedExpression pagExpr = resourceServiceSearch.newPaginatedExpression();
					pagExpr.equals(Resource.model().NOME, op).
						and().
						equals(Resource.model().ID_ACCORDO_SERVIZIO_PARTE_COMUNE.NOME, idAccordo.getNome()).
						equals(Resource.model().ID_ACCORDO_SERVIZIO_PARTE_COMUNE.VERSIONE, idAccordo.getVersione()).
						equals(Resource.model().ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO.TIPO, idAccordo.getSoggettoReferente().getTipo()).
						equals(Resource.model().ID_ACCORDO_SERVIZIO_PARTE_COMUNE.ID_SOGGETTO.NOME, idAccordo.getSoggettoReferente().getNome());
					List<Map<String,Object>> l = service.getResourceServiceSearch().select(pagExpr, Resource.model().HTTP_METHOD, Resource.model().PATH);
					if(l!=null && l.size()==1) {
						Map<String,Object> map = l.get(0);
						String method = (String) map.get(Resource.model().HTTP_METHOD.getFieldName());
						String path = (String) map.get(Resource.model().PATH.getFieldName());
						//System.out.println("LETTO ["+method+"] ["+path+"]");
						StringBuffer bf = new StringBuffer();
						if(!CostantiDB.API_RESOURCE_HTTP_METHOD_ALL_VALUE.equals(method)) {
							bf.append(method);
							bf.append(" ");
						}
						if(!CostantiDB.API_RESOURCE_PATH_ALL_VALUE.equals(path)) {
							bf.append(path);
							this.operazioneLabel = bf.toString();
							return ;
						}
					}
				}
				
			}catch(Exception e) {
				log.error("Normalizzazione operazione '"+op+"' per api '"+uriAPI+"' non riuscita: "+e.getMessage(),e);
			}
			
		}
		
		this.operazioneLabel = getAzione();
	}
	
	public String getLabelOperazioneConGestioneNonPresenza() throws Exception {
		
		String op = getOperazioneLabel();
		if(StringUtils.isNotEmpty(op)) {
			
			return this.getOperazioneLabel();
			
		}
		else {
			return "-";
		}
		
	}

}
