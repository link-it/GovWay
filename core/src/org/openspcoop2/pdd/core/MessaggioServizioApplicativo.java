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
package org.openspcoop2.pdd.core;

import java.util.Date;

/**
 * MessaggioServizioApplicativo
 *
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class MessaggioServizioApplicativo {

	private String idTransazione;
	private String idMessaggio;
	private String servizioApplicativo;
	private boolean sbustamentoSoap;
	private boolean sbustamentoInformazioniProtocollo;
	private String nomePorta;
	
	private boolean attesaEsitoTransazioneCapostipite;
	private Date dataPresaInConsegna;
	private String clusterIdPresaInConsegna;
	
	public boolean isAttesaEsitoTransazioneCapostipite() {
		return this.attesaEsitoTransazioneCapostipite;
	}
	public void setAttesaEsitoTransazioneCapostipite(boolean attesaEsitoTransazioneCapostipite) {
		this.attesaEsitoTransazioneCapostipite = attesaEsitoTransazioneCapostipite;
	}
	public Date getDataPresaInConsegna() {
		return this.dataPresaInConsegna;
	}
	public void setDataPresaInConsegna(Date dataPresaInConsegna) {
		this.dataPresaInConsegna = dataPresaInConsegna;
	}
	public String getClusterIdPresaInConsegna() {
		return this.clusterIdPresaInConsegna;
	}
	public void setClusterIdPresaInConsegna(String clusterIdPresaInConsegna) {
		this.clusterIdPresaInConsegna = clusterIdPresaInConsegna;
	}

	public String getNomePorta() {
		return this.nomePorta;
	}
	public void setNomePorta(String nomePorta) {
		this.nomePorta = nomePorta;
	}
	public String getIdTransazione() {
		return this.idTransazione;
	}
	public void setIdTransazione(String idTransazione) {
		this.idTransazione = idTransazione;
	}
	public String getIdMessaggio() {
		return this.idMessaggio;
	}
	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	public String getServizioApplicativo() {
		return this.servizioApplicativo;
	}
	public void setServizioApplicativo(String servizioApplicativo) {
		this.servizioApplicativo = servizioApplicativo;
	}
	public boolean isSbustamentoSoap() {
		return this.sbustamentoSoap;
	}
	public void setSbustamentoSoap(boolean sbustamentoSoap) {
		this.sbustamentoSoap = sbustamentoSoap;
	}
	public boolean isSbustamentoInformazioniProtocollo() {
		return this.sbustamentoInformazioniProtocollo;
	}
	public void setSbustamentoInformazioniProtocollo(
			boolean sbustamentoInformazioniProtocollo) {
		this.sbustamentoInformazioniProtocollo = sbustamentoInformazioniProtocollo;
	}
}
