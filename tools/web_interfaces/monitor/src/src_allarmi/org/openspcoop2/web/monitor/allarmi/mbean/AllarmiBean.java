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

package org.openspcoop2.web.monitor.allarmi.mbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.openspcoop2.core.allarmi.Allarme;
import org.openspcoop2.core.allarmi.AllarmeFiltro;
import org.openspcoop2.core.allarmi.AllarmeHistory;
import org.openspcoop2.core.allarmi.AllarmeMail;
import org.openspcoop2.core.allarmi.AllarmeParametro;
import org.openspcoop2.core.allarmi.AllarmeScript;
import org.openspcoop2.core.allarmi.IdAllarme;
import org.openspcoop2.core.allarmi.constants.StatoAllarme;
import org.openspcoop2.core.allarmi.constants.TipoAllarme;
import org.openspcoop2.core.allarmi.constants.TipoPeriodo;
import org.openspcoop2.core.allarmi.utils.AllarmiConverterUtils;
import org.openspcoop2.generic_project.exception.NotFoundException;
import org.openspcoop2.generic_project.exception.NotImplementedException;
import org.openspcoop2.generic_project.exception.ServiceException;
import org.openspcoop2.monitor.engine.alarm.AlarmConfigProperties;
import org.openspcoop2.monitor.engine.alarm.AlarmEngineConfig;
import org.openspcoop2.monitor.engine.alarm.AlarmImpl;
import org.openspcoop2.monitor.engine.alarm.AlarmStatusWithAck;
import org.openspcoop2.monitor.engine.config.base.Plugin;
import org.openspcoop2.monitor.engine.config.base.constants.TipoPlugin;
import org.openspcoop2.monitor.engine.dynamic.DynamicFactory;
import org.openspcoop2.monitor.engine.dynamic.IDynamicLoader;
import org.openspcoop2.monitor.engine.dynamic.IDynamicValidator;
import org.openspcoop2.monitor.sdk.alarm.IAlarm;
import org.openspcoop2.monitor.sdk.condition.Context;
import org.openspcoop2.monitor.sdk.constants.AlarmStateValues;
import org.openspcoop2.monitor.sdk.exceptions.AlarmException;
import org.openspcoop2.monitor.sdk.exceptions.ValidationException;
import org.openspcoop2.monitor.sdk.parameters.Parameter;
import org.openspcoop2.monitor.sdk.plugins.IAlarmProcessing;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.protocol.sdk.IProtocolFactory;
import org.openspcoop2.utils.beans.BeanUtils;
import org.openspcoop2.utils.regexp.RegularExpressionEngine;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.web.monitor.allarmi.bean.AllarmiContext;
import org.openspcoop2.web.monitor.allarmi.bean.ConfigurazioneAllarmeBean;
import org.openspcoop2.web.monitor.allarmi.dao.IAllarmiService;
import org.openspcoop2.web.monitor.core.core.PddMonitorProperties;
import org.openspcoop2.web.monitor.core.core.Utility;
import org.openspcoop2.web.monitor.core.dao.IService;
import org.openspcoop2.web.monitor.core.logger.LoggerManager;
import org.openspcoop2.web.monitor.core.mbean.DynamicPdDBean;
import org.openspcoop2.web.monitor.core.utils.MessageUtils;
import org.openspcoop2.web.monitor.core.validator.EmailValidator;
import org.slf4j.Logger;

/**
 * AllarmiBean 
 *
 * @author Giuliano Pintori (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class AllarmiBean extends
DynamicPdDBean<ConfigurazioneAllarmeBean, Integer, IService<ConfigurazioneAllarmeBean, Long>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private static Logger log = LoggerManager.getPddMonitorCoreLogger();

	private transient List<Parameter<?>> parameters;

	//private AllarmiSearchForm search;

	private ConfigurazioneAllarmeBean allarme;

	private String ack;

	private boolean allarmiConsultazioneModificaStatoAbilitata;
	private boolean allarmiAssociazioneAcknowledgedStatoAllarme;
	private boolean allarmiNotificaMailVisualizzazioneCompleta;
	private boolean allarmiMonitoraggioEsternoVisualizzazioneCompleta;
	private boolean allarmiConsultazioneSezioneNotificaMailReadOnly;
	private boolean allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly;
	private boolean allarmiConsultazioneSezioneParametriReadOnly;

	private boolean modificatoInformazioniHistory = false;
	private boolean modificatoStato = false;
	private boolean modificatoAckwoldegment = false;
	private StatoAllarme modificatoInformazioniHistory_statoAllarme = null;
	private String modificatoInformazioniHistory_dettaglioAllarme = null;
	private Integer modificatoInformazioniHistory_ackwoldegmentAllarme = null;
	private Integer modificatoInformazioniHistory_abilitatoAllarme = null;
	private StatoAllarme statoAllarmePrimaModifica = null;
	
	private AlarmEngineConfig alarmEngineConfig;

	private String sorgente = null;
	
	private boolean showFilter = true;
	private boolean showGroupBy = true;
	
	
	public boolean isShowFilter() throws Exception {
		if(this.allarme==null || this.allarme.getTipo()==null){
			return false; // all'inizio deve prima essere scelto il plugin
		}
		
		this.showFilter = ((IAllarmiService)this.service).isUsableFilter(this.allarme);
		
		return this.showFilter;
	}

	public void setShowFilter(boolean showFilter) {
		this.showFilter = showFilter;
	}

	public boolean isShowGroupBy() throws Exception {
		if(this.allarme==null || this.allarme.getTipo()==null){
			return false; // all'inizio deve prima essere scelto il plugin
		}
		
		this.showGroupBy = ((IAllarmiService)this.service).isUsableGroupBy(this.allarme);
		
		return this.showGroupBy;
	}

	public void setShowGroupBy(boolean showGroupBy) {
		this.showGroupBy = showGroupBy;
	}
	
	
	public AllarmiBean() {
		super();
		try {
			this.allarmiConsultazioneModificaStatoAbilitata = PddMonitorProperties.getInstance(log).isAllarmiConsultazioneModificaStatoAbilitata();
			this.allarmiAssociazioneAcknowledgedStatoAllarme = PddMonitorProperties.getInstance(log).isAllarmiAssociazioneAcknowledgedStatoAllarme();
			this.allarmiNotificaMailVisualizzazioneCompleta = PddMonitorProperties.getInstance(log).isAllarmiNotificaMailVisualizzazioneCompleta();
			this.allarmiMonitoraggioEsternoVisualizzazioneCompleta = PddMonitorProperties.getInstance(log).isAllarmiMonitoraggioEsternoVisualizzazioneCompleta();
			this.allarmiConsultazioneSezioneNotificaMailReadOnly = PddMonitorProperties.getInstance(log).isAllarmiConsultazioneSezioneNotificaMailReadOnly();
			this.allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly = PddMonitorProperties.getInstance(log).isAllarmiConsultazioneSezioneMonitoraggioEsternoReadOnly();
			this.allarmiConsultazioneSezioneParametriReadOnly = PddMonitorProperties.getInstance(log).isAllarmiConsultazioneParametriReadOnly();
			
			this.alarmEngineConfig = AlarmConfigProperties.getAlarmConfiguration(log, PddMonitorProperties.getInstance(log).getAllarmiConfigurazione());
			
		} catch (Throwable e) {
			AllarmiBean.log.error(e.getMessage(), e);
		}

	}
	

	private Exception pluginSelectedException;
	private String nomeSuggerito;
	
	public String getNomeSuggerito(){
		if(this.nomeSuggerito==null){
			if(this.allarme.getId()!=null && this.allarme.getId()>0) {
				// siamo in modifica, il nome non si cambia
				return this.allarme.getNome();
			}
			if(this.allarme.getPlugin()!=null){
				String name = _buildName();
				this.allarme.setNome(name);
				
			}
			return this.nomeSuggerito;
		}
		else{
			if(this.allarme!=null && this.allarme.getNome()!=null){
				if(this.allarme.getNome().equals(this.nomeSuggerito)){
					// rilcalcolo
					String name = _buildName();
					this.allarme.setNome(name);
				}
				return this.allarme.getNome();
			}
		}
		return null;
	}
	
	public void setNomeSuggerito(String nome){
		this.allarme.setNome(nome);
	}
	
	private String _buildName(){
		String nome = this.allarme.getPlugin().getLabel();
		StringBuffer bf = new StringBuffer();
		
		String [] tmp = nome.split(" ");
		for (int i = 0; i < tmp.length; i++) {
			String t = tmp[i].trim();
			if(t==null || t.length()<1){
				continue;
			}
			if(Character.isDigit(t.charAt(0))){
				bf.append(t);
			}
			else{
				bf.append((t.charAt(0)+"").toUpperCase());
				if(t.length()>1){
					bf.append(t.substring(1, t.length()));
				}
			}
		}
		
		// Ci viene Concatenato anche il Filtro
		AllarmeFiltro configurazioneFiltro = this.allarme.getFiltro();
		if(this._getTipoNomeMittente(configurazioneFiltro)!=null){
			bf.append("_M-");
			bf.append(this._getTipoNomeMittente(configurazioneFiltro));
		}
		if(this._getTipoNomeDestinatario(configurazioneFiltro)!=null){
			bf.append("_D-");
			bf.append(this._getTipoNomeDestinatario(configurazioneFiltro));
		}
		if(this._getTipoNomeServizio(configurazioneFiltro)!=null){
			bf.append("_S-");
			bf.append(this._getTipoNomeServizio(configurazioneFiltro));
		}
		if(this._getAzione(configurazioneFiltro)!=null){
			bf.append("_A-");
			bf.append(this._getAzione(configurazioneFiltro));
		}
		
		nome = bf.toString();
		
		String p = "";
		String s = "";
		try{
			if(this.allarme!=null && this.allarme.getPlugin()!=null && this.allarme.getPlugin().getClassName()!=null){
				IDynamicLoader dl = DynamicFactory.getInstance().newDynamicLoader(TipoPlugin.ALLARME, this.allarme.getTipo(), this.allarme.getPlugin().getClassName(), log);
				IAlarmProcessing alarm = (IAlarmProcessing) dl.newInstance();
				Context context = new AllarmiContext(this);
				p = alarm.getAutomaticPrefixName(context);
				//System.out.println("P ["+p+"]");
				s = alarm.getAutomaticSuffixName(context);
				//System.out.println("S ["+s+"]");
			}
				
		}catch(Exception e){
			AllarmiBean.log.error(e.getMessage(), e);
		}
		
		if(p==null){
			p = "";
		}
		if(s==null){
			s = "";
		}
		
		this.nomeSuggerito = p+nome+s;
		return this.nomeSuggerito;
	}
		
	public void pluginSelected(ActionEvent ae){
		this.pluginSelectedException =  null;
		this.nomeSuggerito = null;
		if(this.allarme.getPlugin()!=null){
			this.allarme.setNome(this.allarme.getPlugin().getLabel());
			try{
				IDynamicLoader dl = DynamicFactory.getInstance().newDynamicLoader(TipoPlugin.ALLARME, this.allarme.getTipo(), this.allarme.getPlugin().getClassName(), log);
				IAlarmProcessing alarm = (IAlarmProcessing) dl.newInstance();
				switch (alarm.getAlarmType()) {
				case ACTIVE:
					this.allarme.setTipoAllarme(TipoAllarme.ATTIVO);
					break;
				case PASSIVE:
					this.allarme.setTipoAllarme(TipoAllarme.PASSIVO);
					break;
				}
				this.parameters=null;

				this.getParameters();  // Per forzare l'aggiornamento del showFilter del nuovo plugin selezionato
				if(this.showFilter==false) {
					// devo annullare le eventuali selezioni
					//this.resetConfigurazioneFiltro();
				}
				
			}catch(Exception e){
				AllarmiBean.log.error(e.getMessage(), e);
				this.allarme.setNome(null);
				this.allarme.setTipoAllarme(null);
				this.allarme.setPlugin(null);
				this.parameters=null;
				this.pluginSelectedException = e;
			}
		}
		else{
			this.allarme.setNome(null);
			this.allarme.setTipoAllarme(null);
			this.parameters=null;
		}
	}

	public String getPluginSelectedException(){
		if(this.pluginSelectedException!=null && (this.allarme==null || this.allarme.getId()==null || this.allarme.getId()<=0)){
			return this.pluginSelectedException.getMessage();
		}
		return null;
	}
	
	public String getDescrizione(){

		if(this.allarme.getPlugin()!=null){
			return this.allarme.getPlugin().getDescrizione();
		}
		return "-";
	}
		
	public String getSorgente() {
		return this.sorgente;
	}

	public void setSorgente(String sorgente) {
		this.sorgente = sorgente;
	}
	
	public Plugin getPlugin() {
		return this.allarme.getPlugin();
	}

	public void setPlugin(Plugin infoPlugin) {
		this.allarme.setPlugin(infoPlugin);
	}
	
	private long sizePlugins() throws Exception{
		return  ((IAllarmiService)this.service).plugins().size();
	}
	
	public List<SelectItem> getPlugins() throws Exception {
		ArrayList<SelectItem> list = new ArrayList<SelectItem>();
		
		List<Plugin> p = ((IAllarmiService)this.service).plugins();
		Hashtable<String, Plugin> plugins = new Hashtable<String, Plugin>();
		List<String> keys = new ArrayList<String>();
		if(p!=null && p.size()>0){
			for (Plugin plugin : p) {
				String key = plugin.getLabel() + "_" + plugin.getClassName();
				keys.add(key); // possono esistere più plugin con lo stesso label
				
				plugins.put(key, plugin);
			}	
			
			// ordino
			Collections.sort(keys);
			
			for (String key : keys) {
				Plugin infoPlugin = plugins.get(key);
				
				list.add(new SelectItem(infoPlugin, infoPlugin.getLabel()));
			}
			
		}
		
		return list;
	}
	
	public boolean isAllarmiConsultazioneModificaStatoAbilitata() {
		return this.allarmiConsultazioneModificaStatoAbilitata;
	}
	
	public boolean isAllarmiAssociazioneAcknowledgedStatoAllarme() {
		return this.allarmiAssociazioneAcknowledgedStatoAllarme;
	}
	
	public boolean isAllarmiNotificaMailVisualizzazioneCompleta() {
		return this.allarmiNotificaMailVisualizzazioneCompleta;
	}

	public boolean isAllarmiMonitoraggioEsternoVisualizzazioneCompleta() {
		return this.allarmiMonitoraggioEsternoVisualizzazioneCompleta;
	}

	public boolean isAllarmiConsultazioneSezioneNotificaMailReadOnly() {
		return this.allarmiConsultazioneSezioneNotificaMailReadOnly;
	}

	public boolean isAllarmiConsultazioneSezioneMonitoraggioEsternoReadOnly() {
		return this.allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly;
	}
	
	public boolean isAllarmiConsultazioneSezioneParametriReadOnly() {
		return this.allarmiConsultazioneSezioneParametriReadOnly;
	}

	public String getLabelStato(){
		return getLabelStato(AllarmiConverterUtils.toStatoAllarme(this.allarme.getStato()));
	}
	public String getLabelPrecedenteStato(){
		return getLabelStato(AllarmiConverterUtils.toStatoAllarme(this.allarme.getStatoPrecedente()));
	}

	public static String getLabelStato(StatoAllarme stato){
		if(stato!=null){
			switch (stato) {
			case OK:
				return "Ok";
			case WARNING:
				return "Warning";
			case ERROR:
				return "Error";
			}
		}
		return null;
	}
	
	public ConfigurazioneAllarmeBean getAllarme() {
		if (this.allarme == null){
			this.allarme = new ConfigurazioneAllarmeBean();

			//Configurazione default
			this.allarme.setEnabled(1);
			
			this.allarme.setTipoAllarme(null);

			this.allarme.setMail(new AllarmeMail());
			this.allarme.getMail().setInviaAlert(0);
			this.allarme.getMail().setInviaWarning(0);
			if(this.alarmEngineConfig.isMailAckMode()){
				this.allarme.getMail().setAckMode(1);
			}else{
				this.allarme.getMail().setAckMode(0);
			}

			this.allarme.setScript(new AllarmeScript());
			this.allarme.getScript().setInvocaAlert(0);
			this.allarme.getScript().setInvocaWarning(0);
			if(this.alarmEngineConfig.isScriptAckMode()){
				this.allarme.getScript().setAckMode(1);
			}else{
				this.allarme.getScript().setAckMode(0);
			}
			
			this.pluginSelectedException =  null;
			this.nomeSuggerito = null;
			
			this.parameters=null;
			
			this.modificatoInformazioniHistory = false;
			this.modificatoStato = false;
			this.modificatoAckwoldegment = false;
			this.modificatoInformazioniHistory_statoAllarme = null;
			this.modificatoInformazioniHistory_dettaglioAllarme = null;
			this.modificatoInformazioniHistory_ackwoldegmentAllarme = null;
			this.modificatoInformazioniHistory_abilitatoAllarme = null;
		}

		return this.allarme;
	}

	
	public void setModificaStato(int value){
		
		StatoAllarme nuovoStato = AllarmiConverterUtils.toStatoAllarme(value);
		if(this.modificatoInformazioniHistory_statoAllarme!=null){
			if(this.modificatoInformazioniHistory_statoAllarme.equals(nuovoStato)==false){
				this.modificatoInformazioniHistory = true;
				this.modificatoStato = true;
				
				// azzero ack
				this.setModificaAcknowledged(0);
			}
		}
		this.modificatoInformazioniHistory_statoAllarme = nuovoStato;
		this.allarme.setStato(value);
	}
	public int getModificaStato(){
		if(this.allarme.getStato()!=null){
			return this.allarme.getStato();
		}
		return -1;
	}
	
	public void setModificaDettaglioStato(String nuovoDettaglio){
		if(this.modificatoInformazioniHistory_dettaglioAllarme!=null){
			if(this.modificatoInformazioniHistory_dettaglioAllarme.equals(nuovoDettaglio)==false){
				this.modificatoInformazioniHistory = true;
			}
		}
		this.modificatoInformazioniHistory_dettaglioAllarme = nuovoDettaglio;
		this.allarme.setDettaglioStato(nuovoDettaglio);
	}
	public String getModificaDettaglioStato(){
		if(this.allarme.getStato()!=null){
			return this.allarme.getDettaglioStato();
		}
		return null;
	}
	
	public void setModificaAcknowledged(int nuovoAck){
		if(this.modificatoInformazioniHistory_ackwoldegmentAllarme!=null){
			if(this.modificatoInformazioniHistory_ackwoldegmentAllarme != nuovoAck){
				this.modificatoInformazioniHistory = true;
				this.modificatoAckwoldegment = true;
			}
		}
		this.modificatoInformazioniHistory_ackwoldegmentAllarme = nuovoAck;
		this.allarme.setAcknowledged(nuovoAck);
	}
	public int getModificaAcknowledged(){
		return this.allarme.getAcknowledged();
	}
	
	public void setModificaAbilitato(int nuovoEnabled){
		if(this.allarme!=null && this.allarme.getId()>0){
			if(this.modificatoInformazioniHistory_abilitatoAllarme!=null){
				if(this.modificatoInformazioniHistory_abilitatoAllarme != nuovoEnabled){
					this.modificatoInformazioniHistory = true;
				}
			}
			this.modificatoInformazioniHistory_abilitatoAllarme = nuovoEnabled;
		}
		this.allarme.setEnabled(nuovoEnabled);
	}
	public int getModificaAbilitato(){
		return this.allarme.getEnabled();
	}
	
	
	public void setAllarme(ConfigurazioneAllarmeBean allarme) {
		this.allarme = allarme;
		this.parameters = null;

		if(allarme.getId()!=null && allarme.getId()>0){
			this.modificatoInformazioniHistory = false;
			this.modificatoStato = false;
			this.modificatoAckwoldegment = false;
			this.modificatoInformazioniHistory_statoAllarme = AllarmiConverterUtils.toStatoAllarme(allarme.getStato());
			this.modificatoInformazioniHistory_dettaglioAllarme = allarme.getDettaglioStato();
			this.modificatoInformazioniHistory_ackwoldegmentAllarme = allarme.getAcknowledged();
			this.modificatoInformazioniHistory_abilitatoAllarme = allarme.getEnabled();
			this.statoAllarmePrimaModifica = AllarmiConverterUtils.toStatoAllarme(allarme.getStato());
		}

	}

	//	public void setSearch(AllarmiSearchForm search) {
	//		this.search = search;
	//	}

	//	@Override
	//	public AllarmiSearchForm getSearch() {
	//		return this.search;
	//	}


	public List<String> getStatusFilterValues() {

		ArrayList<String> f = null;
		if (f == null) {
			f = new ArrayList<String>();
			f.add("All");
			f.add("Non Disabilitato");
			f.add("Ok");
			f.add("Warn");
			f.add("Error");
			f.add("Disabilitato");
		}

		return f;
	}
	
	public List<String> getStatusFilterValuesWithAck() {

		ArrayList<String> f = null;
		if (f == null) {
			f = new ArrayList<String>();
			f.add("All");
			f.add("Non Disabilitato");
			f.add("Ok");
			f.add("Warn");
			f.add("Warn (Acknowledged)");
			f.add("Warn (Unacknowledged)");
			f.add("Error");
			f.add("Error (Acknowledged)");
			f.add("Error (Unacknowledged)");
			f.add("Disabilitato");
		}

		return f;
	}
	
	public List<String> getAcknowledgedFilterValues() {

		ArrayList<String> f = null;
		if (f == null) {
			f = new ArrayList<String>();
			f.add("All");
			f.add("Si");
			f.add("No");
		}

		return f;
	}

	public String getTotOk() {
		Long t = this.getTot("Ok",null);//this.getTot(0);
		return t != null ? t.toString() : "0";
	}

	public String getTotWarn() {
		Long t = this.getTot("Warn",null);//this.getTot(1);
		return t != null ? t.toString() : "0";
	}
	
	public String getTotWarnNoAck() {
		Long t = this.getTot("Warn",0);//this.getTot(1);
		return t != null ? t.toString() : "0";
	}
	
	public String getTotWarnAck() {
		Long t = this.getTot("Warn",1);//this.getTot(1);
		return t != null ? t.toString() : "0";
	}

	public String getTotError() {
		Long t = this.getTot("Error",null);//this.getTot(2);
		return t != null ? t.toString() : "0";
	}
	
	public String getTotErrorNoAck() {
		Long t = this.getTot("Error",0);//this.getTot(2);
		return t != null ? t.toString() : "0";
	}
	
	public String getTotErrorAck() {
		Long t = this.getTot("Error",1);//this.getTot(2);
		return t != null ? t.toString() : "0";
	}

	public String getTotDisabilitati() {
		Long t = this.getTot("Disabilitato",null);//this.getTot(2);
		return t != null ? t.toString() : "0";
	}

	private Long getTot(String stato, Integer acknowledged) {
		try {

			return ((IAllarmiService)this.service).getCountAllarmiByStato(stato,acknowledged);

		} catch (Exception e) {
			AllarmiBean.log.error(e.getMessage(), e);
		}

		return 0L;
	}

	public String getTipoPeriodo(){
		if(this.allarme.getTipoPeriodo()!=null){
			TipoPeriodo tp = AllarmiConverterUtils.toTipoPeriodo(this.allarme.getTipoPeriodo());
			switch (tp) {
			case M:
				return "Minuti";
			case H:
				return "Ore";
			case G:
				return "Giorni";
			}
		}
		// Default
		return "Ore";
	}

	public void setTipoPeriodo(String p){
		if("Minuti".equals(p)){
			this.allarme.setTipoPeriodo(AllarmiConverterUtils.toValue(TipoPeriodo.M));
		}
		else if("Giorni".equals(p)){
			this.allarme.setTipoPeriodo(AllarmiConverterUtils.toValue(TipoPeriodo.G));
		}
		else{
			this.allarme.setTipoPeriodo(AllarmiConverterUtils.toValue(TipoPeriodo.H));
		}
	}

	public List<Parameter<?>> getParameters() {

		if (this.parameters != null)
			return this.parameters;

		if(this.allarme==null || this.allarme.getTipo()==null){
			return null;
		}
		
		try{
			Context context = new AllarmiContext(this);
			this.parameters = ((IAllarmiService)this.service).instanceParameters(this.allarme,context);
			for (AllarmeParametro parDB : this.allarme.getAllarmeParametroList()) {
				for (Parameter<?> par : this.parameters) {
					if(parDB.getIdParametro().equals(par.getId())){
						par.setValueAsString(parDB.getValore());
						break;
					}
				}
			}
		}catch(Exception e){
			MessageUtils.addErrorMsg("Si e' verificato un errore. " + e.getMessage());
			//return null;
		}

		return this.parameters;
	}

	public String dettaglioAllarme(){
		//		if(this.allarme != null){
		//			try{
		//				this.parameters = ((IAllarmiService)this.service).getParametersByAllarmId(	this.allarme.getId(), this.allarme.getClassName());
		//			}catch(Exception e){
		//				MessageUtils.addErrorMsg("Si e' verificato un errore. " + e.getMessage());
		//				//return null;
		//			}
		//		}

		return "allarme";
	}
	
	public String dettaglioHistoryAllarme(){
		
		return this.sorgente;
	}

	public String salva() {
		Context context = null;
		try {
						
			
			/* ******** CONTROLLO SUL FILTRO *************** */
			
			boolean isFiltroValido = controllaTipiIndicatiNelFiltro(this.allarme.getFiltro());

			if (!isFiltroValido) {
				MessageUtils.addErrorMsg("I tipi indicati per i campi Fruitore (Soggetto), Erogatore (Soggetto) e Servizio non sono compatibili.");
				return null;
			}

			/* ******** CONTROLLO PLUGIN *************** */
			
			if(this.allarme.getPlugin()==null){
				if(this.sizePlugins()<=0){
					log.debug("Non risultano registrati plugins di allarmi");
					MessageUtils.addErrorMsg("Non risultano registrati plugins per gli allarmi. Prima di poter creare un allarme personalizzato è necessario registrare almeno un plugin tramite la sezione 'Registro'");
				}
				else{
					log.debug("Non è stato selezionato un plugin");
					MessageUtils.addErrorMsg("Selezionare un plugin");
				}
				return null;
			}
			
			/* ******** ELEMENTI OBBLIGATORI *************** */
			if(this.allarme.getTipoAllarme()==null || "".equals(this.allarme.getTipoAllarme())){
				log.debug("Non è stato indicato il tipo di allarme (è stato selezionato un plugin?)");
				MessageUtils.addErrorMsg("Non è stato indicato il tipo di allarme (è stato selezionato un plugin?)");
				return null;
			}
			if(this.allarme.getNome()==null || "".equals(this.allarme.getNome())){
				log.debug("Non è stato indicato un nome identificativo dell'allarme");
				MessageUtils.addErrorMsg("Non è stato indicato un nome identificativo dell'allarme");
				return null;
			}
			if (!RegularExpressionEngine.isMatch(this.allarme.getNome(),"^[\\-/_A-Za-z0-9]*$")) {
				String msg = "Il nome dell'allarme dev'essere formato solo da caratteri, cifre, '_' , '-' e '/'";
				log.debug(msg);
				MessageUtils.addErrorMsg(msg);
				return null;
			}
			if(this.allarme.getTipoAllarme().equals(TipoAllarme.ATTIVO)){
				if(this.allarme.getPeriodo()==null){
					log.debug("Non è stata indicata la frequenza di attivazione per il controllo dello stato dell'allarme");
					MessageUtils.addErrorMsg("Non è stata indicata la frequenza di attivazione per il controllo dello stato dell'allarme");
					return null;
				}
				if(this.allarme.getPeriodo()<=0){
					log.debug("Indicare una frequenza di attivazione (maggiore di 0) per il controllo dello stato dell'allarme");
					MessageUtils.addErrorMsg("Indicare una frequenza di attivazione (maggiore di 0) per il controllo dello stato dell'allarme");
					return null;
				}
			}
			if(this.allarme.getMail()!=null && this.allarme.getMail().getInviaAlert()!=null && this.allarme.getMail().getInviaAlert()==1){
				if(this.allarme.getMail().getDestinatari()==null || "".equals(this.allarme.getMail().getDestinatari())){
					log.debug("Almeno un indirizzo e-mail è obbligatorio");
					MessageUtils.addErrorMsg("Almeno un indirizzo e-mail è obbligatorio");
					return null;
				}
				String [] tmp = this.allarme.getMail().getDestinatari().split(",");
				for (int i = 0; i < tmp.length; i++) {
					if(RegularExpressionEngine.isMatch(tmp[i].trim(), EmailValidator.EMAIL_PATTERN)==false){
						log.debug("L'indirizzo e-mail fornito ["+tmp[i].trim()+"] non risulta valido");
						MessageUtils.addErrorMsg("L'indirizzo e-mail fornito ["+tmp[i].trim()+"] non risulta valido");
						return null;
					}
				}
			}
						
			
			
			
			boolean isAdd = this.allarme.getId() == -1;

			AllarmiBean.log.debug("Salvataggio Allarme in corso... ["+(isAdd ? "ADD" : "UPDATE")+"]");
			ConfigurazioneAllarmeBean oldConfigurazioneAllarme = null;

			/* ******** CONTROLLO SUL NOME *************** */
			
			try {
				//if (isAdd)
				// Servirà anche per l'update
				oldConfigurazioneAllarme = ((IAllarmiService)this.service).getAllarme(this.allarme.getNome());

				// ho trovato un allarme con lo stesso nome di quello che sto
				// aggiungendo
				// segnalo il problema.
				if (isAdd && oldConfigurazioneAllarme != null) {
					MessageUtils
					.addErrorMsg("Esiste gia' un allarme con questo nome.");
					return null;
				}
			} catch (NotFoundException e) {
				// do nothing
				AllarmiBean.log.debug("Allarme non presente");
			} catch (ServiceException e) {
				AllarmiBean.log
				.debug("Errore durante l'esecuzione del controllo di esistenza dell'allarme");
				MessageUtils
				.addErrorMsg("Si e' verificato un errore. Riprovare.");
				return null;
			}
			
			/* ******** VALIDAZIONE E SALVATAGGIO PARAMETRI *************** */
			
			IDynamicValidator v = DynamicFactory.getInstance().newDynamicValidator(TipoPlugin.ALLARME, this.allarme.getTipo(), this.allarme.getPlugin().getClassName(),AllarmiBean.log);
			
			context = new AllarmiContext(this);
			
			// validazione parametri
			v.validate(context);
			
			// slavataggio parametri
			if(this.parameters != null){
				for (Parameter<?> par : this.parameters) {
					boolean found = false;
					for (AllarmeParametro parDB : this.allarme.getAllarmeParametroList()) {
						if(parDB.getIdParametro().equals(par.getId())){
							parDB.setValore(par.getValueAsString());
							found = true;
							break;
						}
					}
					if(!found){
						AllarmeParametro parDB = new AllarmeParametro();
						parDB.setIdParametro(par.getId());
						parDB.setValore(par.getValueAsString());
						this.allarme.addAllarmeParametro(parDB);
					}
				}
			}
			
			/* ******** GESTIONE NOTIFICA CAMBIO DI STATO *************** */
			
			boolean historyCreatoTramiteNotificaCambioStato = false;
			if(!isAdd && this.modificatoStato){
				this.notifyChangeState();
				historyCreatoTramiteNotificaCambioStato = true;
			}
			
			
			/* ******** SALVO ALLARME *************** */
			
			((IAllarmiService)this.service).store(this.allarme);

			
			/* ******** GESTIONE HISTORY *************** */
			
			if(!isAdd && this.modificatoInformazioniHistory && !historyCreatoTramiteNotificaCambioStato){
				
				// registro la modifica
				this.addHistory();
			}
			
	
			
			/* ******** GESTIONE AVVIO THREAD NEL CASO DI ATTIVO *************** */
			
			this.notifyStateActiveThread(isAdd,oldConfigurazioneAllarme);
			
			
			
			MessageUtils.addInfoMsg("Allarme salvato con successo.");

		}  catch (ValidationException e) {

			MessageUtils.addErrorMsg(e.getMessage());

			Map<String, String> errors = e.getErrors();
			if (errors != null) {
				Set<String> keys = errors.keySet();

				for (String key : keys) {

					Parameter<?> sp = context.getParameter(key);

					String errorMsg = errors.get(key);

					// recupero il label del parametro
					String label = sp != null ? sp.getRendering().getLabel() : key;

					MessageUtils.addErrorMsg(label + ": " + errorMsg);

				}
			}
			return null;
		} catch (Exception e) {
			MessageUtils.addErrorMsg("Si e' verificato un errore. Riprovare.");
			AllarmiBean.log.error(e.getMessage(), e);
		}

		return "success";
	}

	private void addHistory() throws ServiceException, NotImplementedException{
		// registro la modifica
		AllarmeHistory history = new AllarmeHistory();
		history.setEnabled(this.allarme.getEnabled());
		history.setAcknowledged(this.allarme.getAcknowledged());
		history.setDettaglioStato(this.allarme.getDettaglioStato());
		IdAllarme idConfigurazioneAllarme = new IdAllarme();
		idConfigurazioneAllarme.setNome(this.allarme.getNome());
		history.setIdAllarme(idConfigurazioneAllarme);
		history.setStato(this.allarme.getStato());
		// aggiornato con il default history.setTimestampUpdate(new Date());
		history.setUtente(Utility.getLoggedUtente().getLogin());
		((IAllarmiService)this.service).addHistory(history);
	}
	
	private void notifyStateActiveThread(boolean isAdd, ConfigurazioneAllarmeBean oldAllarmePrimaModifica) throws Exception{
		
		if(TipoAllarme.PASSIVO.equals(this.allarme.getTipoAllarme())){
			// NOTA: il tipo di allarme non è modificabile.
			AllarmiBean.log.debug("Allarme ["+this.allarme.getNome()+"] è passivo. Non viene attivato alcun thread");
			return;
		}
		
		PddMonitorProperties prop = PddMonitorProperties.getInstance(log);
		String prefixUrl = prop.getAllarmiActiveServiceUrl();
		if(prefixUrl.endsWith("/")==false){
			prefixUrl = prefixUrl + "/";
		}
		prefixUrl = prefixUrl + this.allarme.getNome() + "?";
		List<String> urls = new ArrayList<String>();
		if(isAdd){
			if(this.allarme.getEnabled()==1){
				// invoco servlet start
				urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixStartAlarm());
			}
		}
		else{
			// Se è stato modificato solo lo stato non richiede un stop/restart 
			boolean equals = false;
			StringBuilder bfDiff = null;
			if(oldAllarmePrimaModifica!=null){
				
				List<String> fieldEsclusi = new ArrayList<String>();
				fieldEsclusi.add("id");
				if(this.modificatoStato){
					fieldEsclusi.add("stato");
					fieldEsclusi.add("_value_stato");
					fieldEsclusi.add("precedenteStato");
					fieldEsclusi.add("_value_precedenteStato");
					fieldEsclusi.add("dettaglioStato");
				}
				if(this.modificatoAckwoldegment){
					fieldEsclusi.add("acknowledged");
				}
				
				// i metodi equals e diff non funzionano in caso di extends
				
				Allarme old = new Allarme();
				BeanUtils.copy(old, oldAllarmePrimaModifica, null);
				
				Allarme attuale = new Allarme();
				BeanUtils.copy(attuale, this.allarme, null);
				
				equals = attuale.equals(old, fieldEsclusi);
				if(!equals){
					bfDiff = new StringBuilder();
					attuale.diff(old, bfDiff, false, fieldEsclusi);
				}
			}
			if(equals){			
				if(this.modificatoStato){
					StatoAllarme statoAllarme = AllarmiConverterUtils.toStatoAllarme(this.allarme.getStato());
					switch (statoAllarme) {
					case OK:
						urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixUpdateStateOkAlarm());
						break;
					case WARNING:
						urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm());
						break;
					case ERROR:
						urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm());
						break;
					}
				}
				if(this.modificatoAckwoldegment){
					if(this.allarme.getAcknowledged()==1){
						urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm());
					}
					else{
						urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm());
					}
				}
				//else{
				// non è cambiato nulla
				//}
			}
			else{
				if(bfDiff!=null){	
					AllarmiBean.log.debug("Rilevata modifica, diff: "+bfDiff.toString());
				}
				
				if(this.allarme.getEnabled()==0){
					// invoco servlet stop
					urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixStopAlarm());
				}
				else{
					// invoco servlet restart
					urls.add(prefixUrl+prop.getAllarmiActiveServiceUrl_SuffixReStartAlarm());
				}
			}
		}
		this.sendToAllarmi(urls);
	}

	private void sendToAllarmi(List<String> urls) throws Exception{
		if(urls!=null && urls.size()>0){
			for (String url : urls) {
				AllarmiBean.log.debug("Invoke ["+url+"] ...");
				HttpResponse response = HttpUtilities.getHTTPResponse(url);
				if(response.getContent()!=null){
					AllarmiBean.log.debug("Invoked ["+url+"] Status["+response.getResultHTTPOperation()+"] Message["+new String(response.getContent())+"]");	
				}
				else{
					AllarmiBean.log.debug("Invoked ["+url+"] Status["+response.getResultHTTPOperation()+"]");
				}
				if(response.getResultHTTPOperation()>202){
					throw new Exception("Error occurs during invoke url["+url+"] Status["+response.getResultHTTPOperation()+"] Message["+new String(response.getContent())+"]");	
				}	
			}
		}
	}
	
	@Override
	public String delete(){
		
		try{
		
			List<String> urls = new ArrayList<String>();
			Iterator<ConfigurazioneAllarmeBean> it = this.selectedIds.keySet().iterator();
			while (it.hasNext()) {
				ConfigurazioneAllarmeBean elem = it.next();
				if(this.selectedIds.get(elem).booleanValue()){
					
					if(TipoAllarme.PASSIVO.equals(elem.getTipoAllarme())){
						// NOTA: il tipo di allarme non è modificabile.
						AllarmiBean.log.debug("Allarme ["+elem.getNome()+"] è passivo. Non viene effettuata alcuna rimozione su Allarmi.war");
						continue;
					}
					if(elem.getEnabled()==0){
						// NOTA: il tipo di allarme non è modificabile.
						AllarmiBean.log.debug("Allarme ["+elem.getNome()+"] è disabilitato. Non viene effettuata alcuna rimozione su Allarmi.war");
						continue;
					}
					
					PddMonitorProperties prop = PddMonitorProperties.getInstance(log);
					String prefixUrl = prop.getAllarmiActiveServiceUrl();
					if(prefixUrl.endsWith("/")==false){
						prefixUrl = prefixUrl + "/";
					}
					prefixUrl = prefixUrl + elem.getNome() + "?";
					urls.add(prefixUrl + prop.getAllarmiActiveServiceUrl_SuffixStopAlarm());
				}
			}
			this.sendToAllarmi(urls);
		
			super.delete();
			
		}catch(Exception e){
			AllarmiBean.log.error(e.getMessage(),e);
			MessageUtils.addErrorMsg("Rilevato errori durante l'eliminazione degli allarmi selezionati");
		}
		
		return null;
	}
	
	private void notifyChangeState() throws AlarmException{
		IAlarm alarm = ((IAllarmiService)this.service).getAlarm(this.allarme.getNome());
		AlarmStatusWithAck alarmStatus = new AlarmStatusWithAck();
		StatoAllarme statoAllarme = AllarmiConverterUtils.toStatoAllarme(this.allarme.getStato());
		switch (statoAllarme) {
		case OK:
			alarmStatus.setStatus(AlarmStateValues.OK);	
			break;
		case WARNING:
			alarmStatus.setStatus(AlarmStateValues.WARNING);	
			break;
		case ERROR:
			alarmStatus.setStatus(AlarmStateValues.ERROR);	
			break;
		}
		alarmStatus.setDetail(this.allarme.getDettaglioStato());
		if(this.allarme.getAcknowledged()==1){
			alarmStatus.setAck(true);
		}
		else{
			alarmStatus.setAck(false);
		}
		if(alarm instanceof AlarmImpl){
			((AlarmImpl)alarm).setUsername(Utility.getLoggedUtente().getLogin());
		}
		alarm.changeStatus(alarmStatus);
		AllarmiBean.log.debug("Notificato cambio di stato all'allarme con nome ["+this.allarme.getNome()+"]");
		if(this.statoAllarmePrimaModifica!=null){
			this.allarme.setStatoPrecedente(AllarmiConverterUtils.toIntegerValue(this.statoAllarmePrimaModifica));
		}
	}
	
	/***
	 * 
	 * Restituisce true se i tipi indicati sono compatibili, false altrimenti.
	 * 
	 * @param filtro
	 * @return
	 */
	private boolean controllaTipiIndicatiNelFiltro( AllarmeFiltro filtro) throws Exception{

		String tipoMittente = filtro.getTipoFruitore();
		String tipoDestinatario = filtro.getTipoErogatore();
		String tipoServizio = filtro.getTipoServizio();

		List<String> protocolloMittente = new ArrayList<String>();
		List<String> protocolloDestinatario = new ArrayList<String>();
		List<String> protocolloServizio = new ArrayList<String>();


		try{

			// 1. Carico i tipi disponibili per mittente, destinatario e servizio, se uno dei tre non e' stato scelto ('*') la lista risultera' vuota

			if(!tipoDestinatario.equals("*") ){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByOrganizationType(tipoDestinatario);
				protocolloDestinatario.add(protocolFactory.getProtocol());
				//				tipiDisponibiliDestinatario = protocolFactory.createProtocolConfiguration().getTipiSoggetti();
			}

			if(!tipoMittente.equals("*")){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByOrganizationType(tipoMittente);
				protocolloMittente.add(protocolFactory.getProtocol());
				//				tipiDisponibiliMittente = protocolFactory.createProtocolConfiguration().getTipiSoggetti();
			}

			if(!tipoServizio.equals("*")){
				IProtocolFactory<?> protocolFactory = ProtocolFactoryManager.getInstance().getProtocolFactoryByServiceType(tipoServizio);
				protocolloServizio.add(protocolFactory.getProtocol());
				//				tipiDisponibiliServizio = protocolFactory.createProtocolConfiguration().getTipiServizi();
			}

			// Inserisco tutti i tipi trovati in una mappa, se i tre tipi sono compatibili, l'elenco delle chiavi coincide con la dimesione delle liste di quelli settati.
			Map<String, String> mappaProtocolli = new HashMap<String, String>();
			if(protocolloServizio != null && protocolloServizio.size() > 0)
				for (String tipo : protocolloServizio) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}

			if(protocolloMittente != null && protocolloMittente.size() > 0)
				for (String tipo : protocolloMittente) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}

			if(protocolloDestinatario != null && protocolloDestinatario.size() > 0)
				for (String tipo : protocolloDestinatario) {
					if(!mappaProtocolli.containsKey(tipo))
						mappaProtocolli.put(tipo, tipo);
				}	

			// controllo di validita  
			if(protocolloDestinatario.size() > 0 && protocolloDestinatario.size() != mappaProtocolli.keySet().size())
				return false;

			if(protocolloMittente.size() > 0 && protocolloMittente.size() != mappaProtocolli.keySet().size())
				return false;

			if(protocolloServizio.size() > 0 && protocolloServizio.size() != mappaProtocolli.keySet().size())
				return false;

			return true;
		}catch(Exception e){
			throw e;
		}
	}

	public void toggleAck(ActionEvent ae) {
		try {
//			if (this.ack == null)
//				this.allarme.setAcknowledged(0);
//			else
//				this.allarme.setAcknowledged(Boolean.parseBoolean(this.ack) ? 1
//						: 0);
			
			if (this.ack == null)
				this.setModificaAcknowledged(0);
			else
				this.setModificaAcknowledged(Boolean.parseBoolean(this.ack) ? 1
						: 0);

			((IAllarmiService)this.service).store(this.allarme);
			
			// registro la modifica
			this.addHistory();
			
			// notifico avvio thread o stop
			this.notifyStateActiveThread(false,this.allarme);
			
		} catch (Exception e) {
			AllarmiBean.log.error(e.getMessage(), e);
		}
	}

	public String toggleAbilitato() {
		try {
			int val = this.allarme.getEnabled();
			if(val==0){
				this.allarme.setEnabled(1);
			}
			else{
				this.allarme.setEnabled(0);
			}
			
			((IAllarmiService)this.service).store(this.allarme);

			// registro la modifica
			this.addHistory();
			
			// notifico avvio thread o stop
			this.notifyStateActiveThread(false,null);

		} catch (Exception e) {
			AllarmiBean.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String getAck() {
		return this.ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	/**
	 * Listener eseguito prima di aggiungere un nuovo allarme
	 * 
	 * @param ae
	 */
	@Override
	public void addNewListener(ActionEvent ae) {
		this.allarme = null;
	}


	public String getTipoNomeMittenteForConverter() {
		String v = this.getTipoNomeMittente();
		if(v==null){
			return "*";
		}
		return v;
	}
	public String getTipoNomeMittente() {
		return this._getTipoNomeMittente(this.allarme.getFiltro());
	}
	private String _getTipoNomeMittente(AllarmeFiltro configurazioneFiltro) {
		if (configurazioneFiltro != null
				&& StringUtils.isNotEmpty(configurazioneFiltro
						.getNomeFruitore())
						&& !"*".equals(configurazioneFiltro.getNomeFruitore())) {
			String res = configurazioneFiltro.getTipoFruitore() + "/"
					+ configurazioneFiltro.getNomeFruitore();
			return res;
		}
		return null;
	}


	public String getTipoNomeDestinatarioForConverter() {
		String v = this.getTipoNomeDestinatario();
		if(v==null){
			return "*";
		}
		return v;
	}
	public String getTipoNomeDestinatario() {
		return this._getTipoNomeDestinatario(this.allarme.getFiltro());
	}
	private String _getTipoNomeDestinatario(AllarmeFiltro configurazioneFiltro) {
		if (configurazioneFiltro != null
				&& StringUtils.isNotEmpty(configurazioneFiltro
						.getNomeErogatore())
						&& !"*".equals(configurazioneFiltro.getNomeErogatore())) {
			String res = configurazioneFiltro.getTipoErogatore() + "/"
					+ configurazioneFiltro.getNomeErogatore();
			return res;
		}
		return null;
	}


	public String getTipoNomeServizioForConverter() {
		String v = this.getTipoNomeServizio();
		if(v==null){
			return "*";
		}
		return v;
	}
	public String getTipoNomeServizio() {
		return this._getTipoNomeServizio(this.allarme.getFiltro());
	}
	private String _getTipoNomeServizio(AllarmeFiltro configurazioneFiltro) {
		if (configurazioneFiltro != null
				&& StringUtils.isNotEmpty(configurazioneFiltro.getNomeServizio())
				&& !"*".equals(configurazioneFiltro.getNomeServizio())) {
			String res = configurazioneFiltro.getTipoServizio() + "/" + configurazioneFiltro.getNomeServizio();
			return res;
		}
		return null;
	}
	
	public String getAzione() {
		return this._getAzione(this.allarme.getFiltro());
	}
	private String _getAzione(AllarmeFiltro configurazioneFiltro) {
		if (configurazioneFiltro != null && StringUtils.isNotEmpty(configurazioneFiltro.getAzione())
				&& !"*".equals(configurazioneFiltro.getAzione())) {
			String res = configurazioneFiltro.getAzione();
			return res;
		}
		return null;
	}
	

}
