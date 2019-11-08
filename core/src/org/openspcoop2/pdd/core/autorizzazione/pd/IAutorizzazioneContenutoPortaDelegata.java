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



package org.openspcoop2.pdd.core.autorizzazione.pd;

import org.openspcoop2.message.OpenSPCoop2Message;
import org.openspcoop2.pdd.core.autorizzazione.AutorizzazioneException;
import org.openspcoop2.pdd.core.autorizzazione.IAutorizzazioneContenuto;

/**
 * Interfaccia che definisce un processo di autorizzazione per servizi applicativi che invocano richieste delegate.
 *
 * @author Andrea Poli <apoli@link.it>
 * @author $Author$
 * @version $Rev$, $Date$
 */	

public interface IAutorizzazioneContenutoPortaDelegata extends IAutorizzazioneContenuto {


    /**
     * Avvia il processo di autorizzazione.
     *
     * @param datiInvocazione Dati di Invocazione
     * @param msg Messaggio Applicativo
     * @return Esito dell'autorizzazione.
     * 
     */
    public EsitoAutorizzazionePortaDelegata process(DatiInvocazionePortaDelegata datiInvocazione,OpenSPCoop2Message msg) throws AutorizzazioneException;
    
    /**
     * Permette di personalizzare la chiave utilizzata per salvare il risultato nella cache
     * 
     * @param datiInvocazione Dati di invocazione
     * @return Suffisso che viene aggiunto alla chiave
     */
    public String getSuffixKeyAuthorizationResultInCache(DatiInvocazionePortaDelegata datiInvocazione,OpenSPCoop2Message msg);
    
}

