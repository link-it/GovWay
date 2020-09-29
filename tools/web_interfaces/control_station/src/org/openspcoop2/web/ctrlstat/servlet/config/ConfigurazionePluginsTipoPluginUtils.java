package org.openspcoop2.web.ctrlstat.servlet.config;

import java.util.ArrayList;
import java.util.List;

import org.openspcoop2.monitor.engine.config.base.constants.TipoPlugin;

public class ConfigurazionePluginsTipoPluginUtils {

	public static List<String> getLabelsTipoPlugin() {
		List<String> valori = new ArrayList<>();
		
		TipoPlugin[] values = TipoPlugin.values();
		
		for (TipoPlugin value : values) {
			switch (value) {
			case ALLARME:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_ALLARME);
				break;
			case AUTENTICAZIONE:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_AUTENTICAZIONE);
				break;
			case AUTORIZZAZIONE:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_AUTORIZZAZIONE);
				break;
			case AUTORIZZAZIONE_CONTENUTI:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_AUTORIZZAZIONE_CONTENUTI);
				break;
			case BEHAVIOUR:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_BEHAVIOUR);
				break;
			case CONNETTORE:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_CONNETTORE);
				break;
			case INTEGRAZIONE:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_INTEGRAZIONE);
				break;
			case MESSAGE_HANDLER:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_MESSAGE_HANDLER);
				break;
			case RATE_LIMITING:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_RATE_LIMITING);
				break;
//			case RICERCA:
//				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_RICERCA);
//				break;
			case SERVICE_HANDLER:
				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_SERVICE_HANDLER);
				break;
//			case STATISTICA:
//				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_STATISTICA);
//				break;
//			case TRANSAZIONE:
//				valori.add(ConfigurazioneCostanti.LABEL_PARAMETRO_CONFIGURAZIONE_PLUGINS_ARCHIVI_CLASSI_PLUGIN_SELEZIONATE_TRANSAZIONE);
//				break;
			}
		}
		
		return valori;
	}
	
	public static List<String> getValuesTipoPlugin() {
		List<String> valori = new ArrayList<>();
		
		TipoPlugin[] values = TipoPlugin.values();
		
		for (TipoPlugin value : values) {
			switch (value) {
			case ALLARME:
			case AUTENTICAZIONE:
			case AUTORIZZAZIONE:
			case AUTORIZZAZIONE_CONTENUTI:
			case BEHAVIOUR:
			case CONNETTORE:
			case INTEGRAZIONE:
			case MESSAGE_HANDLER:
			case RATE_LIMITING:
//			case RICERCA:
			case SERVICE_HANDLER:
//			case STATISTICA:
//			case TRANSAZIONE:
				valori.add(value.toString());
				break;
			}
		}
		
		return valori;
	}
}
