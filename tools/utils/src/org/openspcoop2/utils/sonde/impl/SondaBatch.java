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


package org.openspcoop2.utils.sonde.impl;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openspcoop2.utils.TipiDatabase;
import org.openspcoop2.utils.sonde.ParametriSonda;
import org.openspcoop2.utils.sonde.Sonda;
import org.openspcoop2.utils.sonde.SondaException;

/**
 * Classe di implementazione della Sonda per i batch
 * 
 *
 * @author Bussu Giovanni (bussu@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class SondaBatch extends Sonda {

	/**
	 * Costruttore per la classe SondaBatch
	 * @param param parametri costruttivi della sonda
	 * @throws Exception
	 */
	public SondaBatch(ParametriSonda param) {
		super(param);
	}

	@Override
	public StatoSonda getStatoSonda(){


		Date now = new Date();
		Date data_warn = new Date(now.getTime() - super.getParam().getSogliaWarn());
		Date data_err = new Date(now.getTime() - super.getParam().getSogliaError());

		StatoSonda statoSonda = new StatoSonda();
		boolean esito_batch = Boolean.valueOf((String) super.getParam().getDatiCheck().get("esito_batch"));

		Long dataUltimoBatchLong = null;
		if(super.getParam().getDatiCheck().containsKey("data_ultimo_batch")) {
			try {
				
				dataUltimoBatchLong = (Long) super.getParam().getDatiCheck().get("data_ultimo_batch");
			} catch(NumberFormatException e) {
				e.printStackTrace();
			}
		}

		if(dataUltimoBatchLong == null) {
			statoSonda.setStato(2);
			statoSonda.setDescrizione("Il batch "+super.getParam().getNome()+" risulta non essere eseguito.");
			return statoSonda;
		}

		Date data_ultimo_batch = new Date(dataUltimoBatchLong);

		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		String dataUltimoBatchString = format.format(data_ultimo_batch);
		
		if(esito_batch) {

			//Valuto l'eventuale superamento delle soglie e calcolo lo stato
			if(data_ultimo_batch.before(data_err)) {
				String errorDateFormatted = format.format(data_err);
				statoSonda.setStato(2);
				statoSonda.setDescrizione("Il batch "+super.getParam().getNome()+" risulta non essere eseguito dal "+dataUltimoBatchString+". Data di error ("+errorDateFormatted+") superata.");
			} else if(data_ultimo_batch.before(data_warn)) {
				String warnDateFormatted = format.format(data_warn);
				statoSonda.setStato(1);
				statoSonda.setDescrizione("Il batch "+super.getParam().getNome()+" risulta non essere eseguito dal "+dataUltimoBatchString+". Data di warn ("+warnDateFormatted+") superata.");
			} else {
				statoSonda.setStato(0);
				statoSonda.setDescrizione("Batch "+super.getParam().getNome()+" eseguito con successo il "+dataUltimoBatchString+".");
			}
	 
			return statoSonda;
		} else {
			Integer interazioniFallite = (Integer) super.getParam().getDatiCheck().get("interazioni_fallite");

			statoSonda.setStato(2);
			statoSonda.setDescrizione("Il batch "+super.getParam().getNome()+" risulta fallire dal "+dataUltimoBatchString+" (fallite "+interazioniFallite+" iterazioni). Descrizione dell'ultimo errore:" + (String)super.getParam().getDatiCheck().get("descrizione_errore"));
			return statoSonda;
		}
	}

	/**
	 * @param esito_batch true se l'esito e' positivo, false altrimenti
	 * @param data_ultimo_batch ultima data di esecuzione del batch 
	 * @param descrizioneErrore eventuale descrizione dell'errore (in caso di esito negativo)
	 * @param connection connessione per il DB
	 * @param tipoDatabase tipo database
	 * @return lo stato attuale della sonda
	 * @throws SondaException
	 */
	public StatoSonda aggiornaStatoSonda(boolean esito_batch, Date data_ultimo_batch, String descrizioneErrore, Connection connection, TipiDatabase tipoDatabase) throws SondaException {
		// inserisce i dati nel properties
		
		super.getParam().getDatiCheck().put("data_ultimo_batch", data_ultimo_batch.getTime());
		super.getParam().getDatiCheck().put("esito_batch", String.valueOf(esito_batch));
		if(!esito_batch) {
			if(super.getParam().getDatiCheck().containsKey("interazioni_fallite")) {
				Integer interazioniFallite = (Integer) super.getParam().getDatiCheck().get("interazioni_fallite");
				super.getParam().getDatiCheck().put("interazioni_fallite", interazioniFallite+1);
			} else {
				super.getParam().getDatiCheck().put("interazioni_fallite", 1);
			}
			
			super.getParam().getDatiCheck().put("descrizione_errore", descrizioneErrore);
		} else {
			if(super.getParam().getDatiCheck().containsKey("descrizione_errore"))
				super.getParam().getDatiCheck().remove("descrizione_errore");
			if(super.getParam().getDatiCheck().containsKey("interazioni_fallite"))
				super.getParam().getDatiCheck().remove("interazioni_fallite");
		}
		return updateSonda(connection, tipoDatabase);
	}

}
