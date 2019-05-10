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

package org.openspcoop2.pdd.timers;


import org.openspcoop2.core.statistiche.constants.TipoIntervalloStatistico;
import org.openspcoop2.pdd.config.OpenSPCoop2Properties;
import org.openspcoop2.pdd.core.CostantiPdD;
import org.openspcoop2.pdd.logger.MsgDiagnosticiProperties;
import org.openspcoop2.pdd.logger.MsgDiagnostico;
import org.openspcoop2.pdd.logger.OpenSPCoop2Logger;
import org.openspcoop2.pdd.services.OpenSPCoop2Startup;
import org.openspcoop2.utils.Utilities;
import org.slf4j.Logger;


/**     
 * TimerStatisticheThread
 *
 * @author Poli Andrea (poli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class TimerStatisticheThread extends Thread{

	public static final String ID_MODULO = "TimerStatistiche";
	
	/**
	 * Timeout che definisce la cadenza di avvio di questo timer. 
	 */
	private long timeout = 10; // ogni 10 secondi avvio il Thread
	
	/** Tipo di Intervallo Statistico */
	private TipoIntervalloStatistico tipoStatistica;
	
	/** Logger utilizzato per debug. */
	private Logger logTimer = null;
	private MsgDiagnostico msgDiag = null;

	/** OpenSPCoop2Properties */
	private OpenSPCoop2Properties op2Properties = null;
	
	
    // VARIABILE PER STOP
	private boolean stop = false;
	
	public boolean isStop() {
		return this.stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	
	
	/** Costruttore */
	public TimerStatisticheThread(long timeout, TipoIntervalloStatistico tipo) throws Exception{
	
		// Aspetto inizializzazione di OpenSPCoop (aspetto mezzo minuto e poi segnalo errore)
		int attesa = 90;
		int secondi = 0;
		while( (OpenSPCoop2Startup.initialize==false) && (secondi<attesa) ){
			Utilities.sleep(1000);
			secondi++;
		}
		if(secondi>= 90){
			throw new TimerException("Riscontrata inizializzazione OpenSPCoop non effettuata");
		}   

		this.logTimer = OpenSPCoop2Logger.getLoggerOpenSPCoopTimers();
		
		try {
			this.msgDiag = MsgDiagnostico.newInstance(ID_MODULO);
			this.msgDiag.setPrefixMsgPersonalizzati(MsgDiagnosticiProperties.MSG_DIAG_TIMER_STATISTICHE);
			this.msgDiag.addKeyword(CostantiPdD.KEY_TIMER, "Timer"+tipo.getValue());
		} catch (Exception e) {
			String msgErrore = "Riscontrato Errore durante l'inizializzazione del MsgDiagnostico";
			this.logTimer.error(msgErrore,e);
			throw new TimerException(msgErrore,e);
		}

		this.msgDiag.logPersonalizzato("avvioInCorso");
		this.logTimer.info(this.msgDiag.getMessaggio_replaceKeywords("avvioInCorso"));
		
		try {
			this.op2Properties = OpenSPCoop2Properties.getInstance();
		} catch (Exception e) {
			this.msgDiag.logErroreGenerico(e,"InizializzazioneTimer");
			String msgErrore = "Riscontrato errore durante l'inizializzazione del Reader delle Properties di OpenSPCoop: "+e.getMessage();
			this.logTimer.error(msgErrore,e);
			throw new TimerException(msgErrore,e);
		}

		this.tipoStatistica = tipo;
		
		this.timeout = timeout;
		String sec = "secondi";
		if(this.timeout == 1)
			sec = "secondo";
		this.msgDiag.addKeyword(CostantiPdD.KEY_TIMEOUT, this.timeout+" "+sec);
		
		this.msgDiag.logPersonalizzato("avvioEffettuato");
		this.logTimer.info(this.msgDiag.getMessaggio_replaceKeywords("avvioEffettuato"));
	}
	
	/**
	 * Metodo che fa partire il Thread. 
	 *
	 */
	@Override
	public void run(){
		
		while(this.stop == false){
			
			try{
				// Prendo la gestione
				TimerStatisticheLib timerStatistiche = 
					new TimerStatisticheLib(this.tipoStatistica,this.msgDiag,this.logTimer,this.op2Properties);
				
				timerStatistiche.check();
				
			}catch(Exception e){
				this.msgDiag.logErroreGenerico(e,"TimerStatisticheLib.check()");
				this.logTimer.error("Errore generale: "+e.getMessage(),e);
			}finally{
			}
			
					
			// CheckInterval
			if(this.stop==false){
				int i=0;
				while(i<this.timeout){
					Utilities.sleep(1000);		
					if(this.stop){
						break; // thread terminato, non lo devo far piu' dormire
					}
					i++;
				}
			}
		} 
		
		
	}
	
}
