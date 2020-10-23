package org.openspcoop2.web.ctrlstat.servlet.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openspcoop2.core.allarmi.AllarmeFiltro;
import org.openspcoop2.core.allarmi.AllarmeMail;
import org.openspcoop2.core.allarmi.AllarmeParametro;
import org.openspcoop2.core.allarmi.AllarmeRaggruppamento;
import org.openspcoop2.core.allarmi.AllarmeScript;
import org.openspcoop2.core.allarmi.constants.RuoloPorta;
import org.openspcoop2.core.allarmi.constants.StatoAllarme;
import org.openspcoop2.core.allarmi.constants.TipoAllarme;
import org.openspcoop2.core.allarmi.utils.AllarmiConverterUtils;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.message.constants.ServiceBinding;
import org.openspcoop2.monitor.engine.alarm.AlarmConfigProperties;
import org.openspcoop2.monitor.engine.alarm.AlarmEngineConfig;
import org.openspcoop2.monitor.engine.alarm.utils.AllarmiUtils;
import org.openspcoop2.monitor.engine.alarm.wrapper.ConfigurazioneAllarmeBean;
import org.openspcoop2.monitor.engine.config.base.Plugin;
import org.openspcoop2.monitor.engine.config.base.constants.TipoPlugin;
import org.openspcoop2.monitor.engine.dynamic.DynamicFactory;
import org.openspcoop2.monitor.engine.dynamic.IDynamicLoader;
import org.openspcoop2.monitor.sdk.condition.Context;
import org.openspcoop2.monitor.sdk.plugins.IAlarmProcessing;
import org.openspcoop2.web.ctrlstat.core.ControlStationCore;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.GeneralHelper;
import org.openspcoop2.web.ctrlstat.servlet.pa.PorteApplicativeCore;
import org.openspcoop2.web.ctrlstat.servlet.pd.PorteDelegateCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;
import org.openspcoop2.web.lib.mvc.DataElement;
import org.openspcoop2.web.lib.mvc.ForwardParams;
import org.openspcoop2.web.lib.mvc.GeneralData;
import org.openspcoop2.web.lib.mvc.PageData;
import org.openspcoop2.web.lib.mvc.Parameter;
import org.openspcoop2.web.lib.mvc.ServletUtils;
import org.openspcoop2.web.lib.mvc.TipoOperazione;

/**     
 * ConfigurazioneAllarmiAdd
 *
 * @author Pintori Giuliano (pintori@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class ConfigurazioneAllarmiAdd extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);

		// Inizializzo PageData
		PageData pd = new PageData();

		GeneralHelper generalHelper = new GeneralHelper(session);

		// Inizializzo GeneralData
		GeneralData gd = generalHelper.initGeneralData(request);

		String userLogin = ServletUtils.getUserLoginFromSession(session);	
		
		TipoOperazione tipoOperazione = TipoOperazione.ADD;
		
		String pluginSelectedExceptionMessage = null;

		try {
			StringBuilder sbParsingError = new StringBuilder();
			
			ConfigurazioneHelper confHelper = new ConfigurazioneHelper(request, pd, session);
			
			// controllo primo accesso
			boolean first = confHelper.isFirstTimeFromHttpParameters(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_FIRST_TIME);
			
			String idAllarmeS = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_ID_ALLARME);
			
			String ruoloPortaParam = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_RUOLO_PORTA);
			RuoloPorta ruoloPorta = null;
			if(ruoloPortaParam!=null) {
				ruoloPorta = RuoloPorta.toEnumConstant(ruoloPortaParam);
			}
			String nomePorta = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_NOME_PORTA);
			ServiceBinding serviceBinding = null;
			String serviceBindingParam = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_SERVICE_BINDING);
			if(serviceBindingParam!=null && !"".equals(serviceBindingParam)) {
				serviceBinding = ServiceBinding.valueOf(serviceBindingParam);
			}
			String nomePlugin = confHelper.getParameter(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_PLUGIN);
			
			ConfigurazioneCore confCore = new ConfigurazioneCore();
			SoggettiCore soggettiCore = new SoggettiCore(confCore);
			PorteDelegateCore pdCore = new PorteDelegateCore(confCore);
			PorteApplicativeCore paCore = new PorteApplicativeCore(confCore);
			
			AlarmEngineConfig alarmEngineConfig = AlarmConfigProperties.getAlarmConfiguration(ControlStationCore.getLog(), confCore.getAllarmiConfig().getAllarmiConfigurazione());
			
			ConfigurazioneAllarmeBean allarme = new ConfigurazioneAllarmeBean();
			allarme.setEnabled(1);
			allarme.setTipoAllarme(null);
			allarme.setMail(new AllarmeMail());
			allarme.getMail().setInviaAlert(0);
			allarme.getMail().setInviaWarning(0);
			if(alarmEngineConfig.isMailAckMode()){
				allarme.getMail().setAckMode(1);
			}else{
				allarme.getMail().setAckMode(0);
			}

			allarme.setScript(new AllarmeScript());
			allarme.getScript().setInvocaAlert(0);
			allarme.getScript().setInvocaWarning(0);
			if(alarmEngineConfig.isScriptAckMode()){
				allarme.getScript().setAckMode(1);
			}else{
				allarme.getScript().setAckMode(0);
			}
			allarme.setFiltro(new AllarmeFiltro());
			allarme.setGroupBy(new AllarmeRaggruppamento());
			
			List<org.openspcoop2.monitor.sdk.parameters.Parameter<?>> parameters = confHelper.readParametriFromSession(session);
			
			List<Plugin> listaPlugin = confCore.pluginsAllarmiList();
			int numeroPluginRegistrati = listaPlugin.size();
			
			Plugin plugin = confHelper.readPluginFromSession(session);
			allarme.setPlugin(plugin);
			
			// Dati Attivazione
			String errorAttivazione = confHelper.readAllarmeFromRequest(tipoOperazione, first, allarme, alarmEngineConfig, plugin, parameters); 
			if(errorAttivazione!=null){
				confHelper.addParsingError(sbParsingError,errorAttivazione); 
			}
			
			if(ruoloPorta!=null) {
				
				String protocollo = null;
				String tipoSoggettoProprietario = null;
				String nomeSoggettoProprietario = null;
				if(RuoloPorta.DELEGATA.equals(ruoloPorta)) {
					IDPortaDelegata idPD = new IDPortaDelegata();
					idPD.setNome(nomePorta);
					PortaDelegata porta = pdCore.getPortaDelegata(idPD);
					protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(porta.getTipoSoggettoProprietario());
					// il tipo e nome serve per l'applicativo fruitore
					tipoSoggettoProprietario = porta.getTipoSoggettoProprietario();
					nomeSoggettoProprietario = porta.getNomeSoggettoProprietario();
				}
				else {
					IDPortaApplicativa idPA = new IDPortaApplicativa();
					idPA.setNome(nomePorta);
					PortaApplicativa porta = paCore.getPortaApplicativa(idPA);
					protocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(porta.getTipoSoggettoProprietario());
				}
				
				allarme.getFiltro().setEnabled(true);
				allarme.getFiltro().setProtocollo(protocollo);
				allarme.getFiltro().setRuoloPorta(ruoloPorta);
				allarme.getFiltro().setNomePorta(nomePorta);
				if(RuoloPorta.DELEGATA.equals(ruoloPorta)) {
					allarme.getFiltro().setTipoFruitore(tipoSoggettoProprietario);
					allarme.getFiltro().setNomeFruitore(nomeSoggettoProprietario);
				}
				
			}
			
			
			// Preparo il menu
			confHelper.makeMenu();
			
			String postBackElementName = confHelper.getPostBackElementName();
			if (postBackElementName != null) {
				// selezione del plugin
				if(postBackElementName.equals(ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_PLUGIN)) {
					if(nomePlugin.equals(ConfigurazioneCostanti.DEFAULT_VALUE_NESSUNO)) {
						allarme.setNome(null);
						allarme.setTipoAllarme(null);
						allarme.setPlugin(null);
						parameters=null;
						
						confHelper.removeParametriFromSession(session);
						confHelper.removePluginFromSession(session);
					} else {
						for (Plugin pluginBean : listaPlugin) {
							String key = pluginBean.getLabel() + ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_PLUGIN_NOME_SEP + pluginBean.getClassName();
							if(key.equals(nomePlugin)) {
								plugin = pluginBean;
								break;
							}
						}
						
						
						allarme.setPlugin(plugin);
						parameters=null;
						
						if(allarme.getPlugin() != null) {
							allarme.setNome(allarme.getPlugin().getLabel());
							try{
								Context context = confHelper.createAlarmContext(allarme, parameters);	
								allarme.setNome(AllarmiUtils.getNomeSuggerito(allarme.getNome(), allarme, ControlStationCore.getLog(), context));
								IDynamicLoader dl = DynamicFactory.getInstance().newDynamicLoader(TipoPlugin.ALLARME, allarme.getPlugin().getTipo(), allarme.getPlugin().getClassName(), ControlStationCore.getLog());
								IAlarmProcessing alarm = (IAlarmProcessing) dl.newInstance();
								switch (alarm.getAlarmType()) {
								case ACTIVE:
									allarme.setTipoAllarme(TipoAllarme.ATTIVO);
									break;
								case PASSIVE:
									allarme.setTipoAllarme(TipoAllarme.PASSIVO);
									break;
								}
											
								parameters = confCore.getParameters(allarme, context);
								
//								for (org.openspcoop2.monitor.sdk.parameters.Parameter<?> par : parameters) {
//									par.setValue(par.getRendering().getDefaultValue());
//								}
								
								confHelper.saveParametriIntoSession(session, parameters);
								confHelper.savePluginIntoSession(session, allarme.getPlugin());
							}catch(Exception e){
								ControlStationCore.getLog().error(e.getMessage(), e);
								allarme.setNome(null);
								allarme.setTipoAllarme(null);
								allarme.setPlugin(null);
								parameters=null;
								pluginSelectedExceptionMessage = e.getMessage();
								confHelper.removeParametriFromSession(session);
								confHelper.removePluginFromSession(session);
							}
						}
					}
				}
			}
			
			// setto la barra del titolo
			List<Parameter> lstParam  = new ArrayList<Parameter>();
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_GENERALE, ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_GENERALE));
			lstParam.add(new Parameter(ConfigurazioneCostanti.LABEL_CONFIGURAZIONE_ALLARMI, ConfigurazioneCostanti.SERVLET_NAME_CONFIGURAZIONE_ALLARMI_LIST));
			lstParam.add(ServletUtils.getParameterAggiungi());
			
			// Se tipo = null, devo visualizzare la pagina per l'inserimento
			// dati
			if (confHelper.isEditModeInProgress()) {
				ServletUtils.setPageDataTitle(pd, lstParam);
				
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				confHelper.addAllarmeToDati(dati, tipoOperazione, allarme, alarmEngineConfig, listaPlugin, parameters, ruoloPorta, nomePorta, serviceBinding); 
				
				// Set First is false
				confHelper.addToDatiFirstTimeDisabled(dati,ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_FIRST_TIME);
				
				if(pluginSelectedExceptionMessage != null) {
					pd.setMessage(pluginSelectedExceptionMessage);
				}
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);

				return ServletUtils.getStrutsForwardEditModeInProgress(mapping, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_ALLARMI, ForwardParams.ADD());
			}
				
			// Controlli sui campi immessi
			boolean isOk = confHelper.allarmeCheckData(TipoOperazione.ADD, null, allarme, numeroPluginRegistrati, parameters); 
			if (!isOk) {
				ServletUtils.setPageDataTitle(pd, lstParam);
				
				// preparo i campi
				Vector<DataElement> dati = new Vector<DataElement>();
				dati.addElement(ServletUtils.getDataElementForEditModeFinished());
				
				confHelper.addAllarmeToDati(dati, tipoOperazione, allarme, alarmEngineConfig, listaPlugin, parameters, ruoloPorta, nomePorta, serviceBinding);
				
				// Set First is false
				confHelper.addToDatiFirstTimeDisabled(dati,ConfigurazioneCostanti.PARAMETRO_CONFIGURAZIONE_ALLARMI_FIRST_TIME);
				
				pd.setDati(dati);

				ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
				
				return ServletUtils.getStrutsForwardEditModeCheckError(mapping, 
						ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_ALLARMI, 
						ForwardParams.ADD());
			}
				
			// salvataggio dei parametri
			for (org.openspcoop2.monitor.sdk.parameters.Parameter<?> par : parameters) {
				boolean found = false;
				for (AllarmeParametro parDB : allarme.getAllarmeParametroList()) {
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
					allarme.addAllarmeParametro(parDB);
				}
			}
			
			// imposto lo stato di default per l'allarme:
			allarme.setStato(AllarmiConverterUtils.toIntegerValue(StatoAllarme.OK));
			allarme.setStatoPrecedente(AllarmiConverterUtils.toIntegerValue(StatoAllarme.OK));
			allarme.setLasttimestampCreate(new Date());
			allarme.setAcknowledged(Integer.valueOf(0));
			
			// insert sul db
			confCore.performCreateOperation(userLogin, confHelper.smista(), allarme);
				
			// Preparo la lista
			int idLista = Liste.CONFIGURAZIONE_ALLARMI;
			
			Search ricerca = (Search) ServletUtils.getSearchObjectFromSession(session, Search.class);
			
			ricerca = confHelper.checkSearchParameters(idLista, ricerca);

			List<ConfigurazioneAllarmeBean> lista = confCore.allarmiList(ricerca); 
			
			confHelper.prepareAllarmiList(ricerca, lista);
			
			// salvo l'oggetto ricerca nella sessione
			ServletUtils.setSearchObjectIntoSession(session, ricerca);
			ServletUtils.setGeneralAndPageDataIntoSession(session, gd, pd);
			
			// Forward control to the specified success URI
			return ServletUtils.getStrutsForwardEditModeFinished(mapping, ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_ALLARMI, ForwardParams.ADD());
		} catch (Exception e) {
			return ServletUtils.getStrutsForwardError(ControlStationCore.getLog(), e, pd, session, gd, mapping, 
					ConfigurazioneCostanti.OBJECT_NAME_CONFIGURAZIONE_ALLARMI, ForwardParams.ADD());
		}  
	}
}			
