package org.openspcoop2.web.monitor.allarmi.bean;

import org.openspcoop2.monitor.engine.alarm.utils.AllarmiConfig;
import org.openspcoop2.web.monitor.core.core.PddMonitorProperties;

/**
 * AllarmiMonitorConfig
 *
 * @author Andrea Poli (apoli@link.it)
 * @author pintori
 * @author $Author$
 * @version $Rev$, $Date$

 *
 */
public class AllarmiMonitorConfig implements AllarmiConfig{

	private String allarmiConfigurazione;
	private String allarmiActiveServiceUrl;
	private String allarmiActiveServiceUrl_SuffixStartAlarm;
	private String allarmiActiveServiceUrl_SuffixStopAlarm;
	private String allarmiActiveServiceUrl_SuffixReStartAlarm;
	private String allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm;
	private String allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm;
	private String allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm;
	private String allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm;
	private String allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm;
	private boolean allarmiConsultazioneModificaStatoAbilitata;
	private boolean allarmiAssociazioneAcknowledgedStatoAllarme;
	private boolean allarmiNotificaMailVisualizzazioneCompleta;
	private boolean allarmiMonitoraggioEsternoVisualizzazioneCompleta;
	private boolean allarmiConsultazioneSezioneNotificaMailReadOnly;
	private boolean allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly;
	private boolean allarmiConsultazioneParametriReadOnly;

	public AllarmiMonitorConfig (PddMonitorProperties monitorProperties) throws Exception {
		this.allarmiConfigurazione = monitorProperties.getAllarmiConfigurazione();
		this.allarmiActiveServiceUrl = monitorProperties.getAllarmiActiveServiceUrl();
		this.allarmiActiveServiceUrl_SuffixStartAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixStartAlarm();
		this.allarmiActiveServiceUrl_SuffixStopAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixStopAlarm();
		this.allarmiActiveServiceUrl_SuffixReStartAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixReStartAlarm();
		this.allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixUpdateStateOkAlarm();
		this.allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm();
		this.allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm();
		this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm();
		this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm = monitorProperties.getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm();
		this.allarmiConsultazioneModificaStatoAbilitata = monitorProperties.isAllarmiConsultazioneModificaStatoAbilitata();
		this.allarmiAssociazioneAcknowledgedStatoAllarme = monitorProperties.isAllarmiAssociazioneAcknowledgedStatoAllarme();
		this.allarmiNotificaMailVisualizzazioneCompleta = monitorProperties.isAllarmiNotificaMailVisualizzazioneCompleta();
		this.allarmiMonitoraggioEsternoVisualizzazioneCompleta = monitorProperties.isAllarmiMonitoraggioEsternoVisualizzazioneCompleta();
		this.allarmiConsultazioneSezioneNotificaMailReadOnly = monitorProperties.isAllarmiConsultazioneSezioneNotificaMailReadOnly();
		this.allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly = monitorProperties.isAllarmiConsultazioneSezioneMonitoraggioEsternoReadOnly();
		this.allarmiConsultazioneParametriReadOnly = monitorProperties.isAllarmiConsultazioneParametriReadOnly();
		
	}

	@Override
	public String getAllarmiConfigurazione() {
		return this.allarmiConfigurazione;
	}

	public void setAllarmiConfigurazione(String allarmiConfigurazione) {
		this.allarmiConfigurazione = allarmiConfigurazione;
	}

	@Override
	public String getAllarmiActiveServiceUrl() {
		return this.allarmiActiveServiceUrl;
	}

	public void setAllarmiActiveServiceUrl(String allarmiActiveServiceUrl) {
		this.allarmiActiveServiceUrl = allarmiActiveServiceUrl;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixStartAlarm() {
		return this.allarmiActiveServiceUrl_SuffixStartAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixStartAlarm(String allarmiActiveServiceUrl_SuffixStartAlarm) {
		this.allarmiActiveServiceUrl_SuffixStartAlarm = allarmiActiveServiceUrl_SuffixStartAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixStopAlarm() {
		return this.allarmiActiveServiceUrl_SuffixStopAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixStopAlarm(String allarmiActiveServiceUrl_SuffixStopAlarm) {
		this.allarmiActiveServiceUrl_SuffixStopAlarm = allarmiActiveServiceUrl_SuffixStopAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixReStartAlarm() {
		return this.allarmiActiveServiceUrl_SuffixReStartAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixReStartAlarm(String allarmiActiveServiceUrl_SuffixReStartAlarm) {
		this.allarmiActiveServiceUrl_SuffixReStartAlarm = allarmiActiveServiceUrl_SuffixReStartAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixUpdateStateOkAlarm() {
		return this.allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixUpdateStateOkAlarm(
			String allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm) {
		this.allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm = allarmiActiveServiceUrl_SuffixUpdateStateOkAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm() {
		return this.allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm(
			String allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm) {
		this.allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm = allarmiActiveServiceUrl_SuffixUpdateStateWarningAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm() {
		return this.allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm(
			String allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm) {
		this.allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm = allarmiActiveServiceUrl_SuffixUpdateStateErrorAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm() {
		return this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm(
			String allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm) {
		this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm = allarmiActiveServiceUrl_SuffixUpdateAcknoledgementEnabledAlarm;
	}

	@Override
	public String getAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm() {
		return this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm;
	}

	public void setAllarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm(
			String allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm) {
		this.allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm = allarmiActiveServiceUrl_SuffixUpdateAcknoledgementDisabledAlarm;
	}

	@Override
	public boolean isAllarmiConsultazioneModificaStatoAbilitata() {
		return this.allarmiConsultazioneModificaStatoAbilitata;
	}

	public void setAllarmiConsultazioneModificaStatoAbilitata(boolean allarmiConsultazioneModificaStatoAbilitata) {
		this.allarmiConsultazioneModificaStatoAbilitata = allarmiConsultazioneModificaStatoAbilitata;
	}

	@Override
	public boolean isAllarmiAssociazioneAcknowledgedStatoAllarme() {
		return this.allarmiAssociazioneAcknowledgedStatoAllarme;
	}

	public void setAllarmiAssociazioneAcknowledgedStatoAllarme(boolean allarmiAssociazioneAcknowledgedStatoAllarme) {
		this.allarmiAssociazioneAcknowledgedStatoAllarme = allarmiAssociazioneAcknowledgedStatoAllarme;
	}

	@Override
	public boolean isAllarmiNotificaMailVisualizzazioneCompleta() {
		return this.allarmiNotificaMailVisualizzazioneCompleta;
	}

	public void setAllarmiNotificaMailVisualizzazioneCompleta(boolean allarmiNotificaMailVisualizzazioneCompleta) {
		this.allarmiNotificaMailVisualizzazioneCompleta = allarmiNotificaMailVisualizzazioneCompleta;
	}

	@Override
	public boolean isAllarmiMonitoraggioEsternoVisualizzazioneCompleta() {
		return this.allarmiMonitoraggioEsternoVisualizzazioneCompleta;
	}

	public void setAllarmiMonitoraggioEsternoVisualizzazioneCompleta(
			boolean allarmiMonitoraggioEsternoVisualizzazioneCompleta) {
		this.allarmiMonitoraggioEsternoVisualizzazioneCompleta = allarmiMonitoraggioEsternoVisualizzazioneCompleta;
	}

	@Override
	public boolean isAllarmiConsultazioneSezioneNotificaMailReadOnly() {
		return this.allarmiConsultazioneSezioneNotificaMailReadOnly;
	}

	public void setAllarmiConsultazioneSezioneNotificaMailReadOnly(
			boolean allarmiConsultazioneSezioneNotificaMailReadOnly) {
		this.allarmiConsultazioneSezioneNotificaMailReadOnly = allarmiConsultazioneSezioneNotificaMailReadOnly;
	}

	@Override
	public boolean isAllarmiConsultazioneSezioneMonitoraggioEsternoReadOnly() {
		return this.allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly;
	}

	public void setAllarmiConsultazioneSezioneMonitoraggioEsternoReadOnly(
			boolean allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly) {
		this.allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly = allarmiConsultazioneSezioneMonitoraggioEsternoReadOnly;
	}

	@Override
	public boolean isAllarmiConsultazioneParametriReadOnly() {
		return this.allarmiConsultazioneParametriReadOnly;
	}

	public void setAllarmiConsultazioneParametriReadOnly(boolean allarmiConsultazioneParametriReadOnly) {
		this.allarmiConsultazioneParametriReadOnly = allarmiConsultazioneParametriReadOnly;
	}


}
