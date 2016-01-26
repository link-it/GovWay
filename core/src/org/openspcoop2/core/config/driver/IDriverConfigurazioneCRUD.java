/*
 * OpenSPCoop v2 - Customizable SOAP Message Broker 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2016 Link.it srl (http://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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


package org.openspcoop2.core.config.driver;

import org.openspcoop2.core.config.AccessoRegistro;
import org.openspcoop2.core.config.Configurazione;
import org.openspcoop2.core.config.GestioneErrore;
import org.openspcoop2.core.config.PortaApplicativa;
import org.openspcoop2.core.config.PortaDelegata;
import org.openspcoop2.core.config.RoutingTable;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.Soggetto;
import org.openspcoop2.core.config.StatoServiziPdd;
import org.openspcoop2.core.config.SystemProperties;
import org.openspcoop2.core.id.IDPortaApplicativa;
import org.openspcoop2.core.id.IDPortaApplicativaByNome;
import org.openspcoop2.core.id.IDPortaDelegata;
import org.openspcoop2.core.id.IDServizioApplicativo;
import org.openspcoop2.core.id.IDSoggetto;

/**
 * Interfaccia per la gestione CRUD di oggetti presenti in una configurazione 
 * di OpenSPCoop. I driver che implementano l'interfaccia 
 * sono attualmente:
 * <ul>
 * <li> {@link org.openspcoop2.core.config.driver.db.DriverConfigurazioneDB}, interroga un registro dei servizi Relazionale.
 * </ul>
 *
 *
 * @author Poli Andrea (apoli@link.it)
 * @author Stefano Corallo (corallo@link.it)
 * @author Lorenzo Nardi (nardi@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IDriverConfigurazioneCRUD {

	
	// SOGGETTI
	
	/**
	 * Crea un nuovo Soggetto
	 * @param soggetto
	 * @throws DriverConfigurazioneException
	 */
	public void createSoggetto(Soggetto soggetto) throws DriverConfigurazioneException;
	
	/**
     * Verifica l'esistenza di un soggetto registrato.
     *
     * @param idSoggetto id del soggetto da verificare
     * @return true se il soggetto esiste, false altrimenti
	 * @throws DriverRegistroServiziException
     */    
	public boolean existsSoggetto(IDSoggetto idSoggetto) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna un Soggetto 
	 * @param soggetto
	 * @throws DriverConfigurazioneException
	 */
	public void updateSoggetto(org.openspcoop2.core.config.Soggetto soggetto) throws DriverConfigurazioneException;
	
	/**
	 * Cancella un Soggetto
	 * @param soggetto
	 * @throws DriverConfigurazioneException
	 */
	public void deleteSoggetto(org.openspcoop2.core.config.Soggetto soggetto) throws DriverConfigurazioneException;





	
	
	// PORTA DELEGATA
	
	/**
	 * Crea un porta delegata
	 * 
	 * @param aPD
	 * @throws DriverConfigurazioneException
	 */
	public void createPortaDelegata(PortaDelegata aPD) throws DriverConfigurazioneException;	
	/**
     * Verifica l'esistenza di una porta delegata.
     *
     * @param idPD id della porta delegata da verificare
     * @return true se la porta delegata esiste, false altrimenti
	 * @throws DriverRegistroServiziException 
     */    
	public boolean existsPortaDelegata(IDPortaDelegata idPD) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna un porta delegata
	 * 
	 * @param aPD
	 * @throws DriverConfigurazioneException
	 */
	public void updatePortaDelegata(PortaDelegata aPD) throws DriverConfigurazioneException;
	
	/**
	 * Elimina un porta delegata
	 * 
	 * @param aPD
	 * @throws DriverConfigurazioneException
	 */
	public void deletePortaDelegata(PortaDelegata aPD) throws DriverConfigurazioneException;

	
	
	
	
	
	
	
	// PORTA APPLICATIVA
	
	/**
	 * Crea una porta applicativa
	 * 
	 * @param aPA
	 * @throws DriverConfigurazioneException
	 */
	public void createPortaApplicativa(PortaApplicativa aPA) throws DriverConfigurazioneException;
	
	/**
     * Verifica l'esistenza di una porta applicativa.
     * se l'azione e' specificata ma non vi sono porte applicative che mathcano i criteri allora
     * effettua la ricerca senza tener conto dell'azione. Per una ricerca piu precisa utilizzare 
     * existsPortaApplicativa(IDPortaApplicativa idPA,boolean ricercaPuntuale) con il parametro ricercaPuntuale a true
     * @param idPA id della porta applicativa da verificare
     * @return true se la porta applicativa esiste, false altrimenti
	 * @throws DriverRegistroServiziException 
     */    
	public boolean existsPortaApplicativa(IDPortaApplicativa idPA) throws DriverConfigurazioneException;
    /**
     * Verifica l'esistenza di una porta applicativa.
     * E' possibile specificare se si vuole effettuare una ricerca puntuale in tal caso
     * se l'azione e' specificata ma non vi sono porte applicative che mathcano i criteri verra restituito false
     * @param idPA id della porta applicativa da verificare
     * @param ricercaPuntuale ricercaPuntuale
     * @return true se la porta applicativa esiste, false altrimenti
	 * @throws DriverRegistroServiziException 
     */
	public boolean existsPortaApplicativa(IDPortaApplicativa idPA,boolean ricercaPuntuale) throws DriverConfigurazioneException;
    /**
     * Verifica l'esistenza di una porta applicativa in cui il soggetto erogatore e' un soggetto virtuale
     * @param idPA
     * @return true se la porta applicativa esiste, false altrimenti
     * @throws DriverConfigurazioneException
     */
	public boolean existsPortaApplicativaVirtuale(IDPortaApplicativa idPA,IDSoggetto soggettoVirtuale) throws DriverConfigurazioneException;
    /**
     * Verifica l'esistenza di una porta applicativa in cui il soggetto erogatore e' un soggetto virtuale
     * @param idPA
     * @param ricercaPuntuale ricercaPuntuale
     * @return true se la porta applicativa esiste, false altrimenti
     * @throws DriverConfigurazioneException
     */
	public boolean existsPortaApplicativaVirtuale(IDPortaApplicativa idPA,IDSoggetto soggettoVirtuale,boolean ricercaPuntuale) throws DriverConfigurazioneException;

	public boolean existsPortaApplicativa(IDPortaApplicativaByNome idPA) throws DriverConfigurazioneException;
	public boolean existsPortaApplicativa(String nomePorta,IDSoggetto soggettoProprietario) throws DriverConfigurazioneException;
    
	/**
	 * Aggiorna una porta applicativa
	 * 
	 * @param aPA
	 * @throws DriverConfigurazioneException
	 */
	public void updatePortaApplicativa(PortaApplicativa aPA) throws DriverConfigurazioneException;
	
	/**
	 * Elimina una porta applicativa
	 * 
	 * @param aPA
	 * @throws DriverConfigurazioneException
	 */
	public void deletePortaApplicativa(PortaApplicativa aPA) throws DriverConfigurazioneException;
	
	
	
	
	
	
	// SERVIZIO APPLICATIVO
	
	/**
	 * Crea un servizio applicativo
	 * 
	 * @param aSA
	 * @throws DriverConfigurazioneException
	 */
	public void createServizioApplicativo(ServizioApplicativo aSA) throws DriverConfigurazioneException;
	
	/**
     * Verifica l'esistenza di un servizio applicativo.
     *
     * @param idSoggetto id del soggetto proprietario
     * @param nomeServizioApplicativo nome del servizio applicativo
     * @return true se il servizio applicativo esiste, false altrimenti
	 * @throws DriverRegistroServiziException
     */    
	public boolean existsServizioApplicativo(IDSoggetto idSoggetto,String nomeServizioApplicativo) throws DriverConfigurazioneException;
    
    /**
     * Verifica l'esistenza di un servizio applicativo.
     *
     * @param idServizioApplicativo id del servizio applicativo
     * @return true se il servizio applicativo esiste, false altrimenti
	 * @throws DriverRegistroServiziException
     */    
	public boolean existsServizioApplicativo(IDServizioApplicativo idServizioApplicativo) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna un servizio applicativo
	 * 
	 * @param aSA
	 * @throws DriverConfigurazioneException
	 */
	public void updateServizioApplicativo(ServizioApplicativo aSA) throws DriverConfigurazioneException;
	
	/**
	 * Elimina un servizio applicativo
	 * 
	 * @param aSA
	 * @throws DriverConfigurazioneException
	 */
	public void deleteServizioApplicativo(ServizioApplicativo aSA) throws DriverConfigurazioneException;
	
	
	
	
	
	
	// ROUTING TABLE
	
	/**
	 * Crea una Routing Table
	 * 
	 * @param routingTable
	 * @throws DriverConfigurazioneException
	 */
	public void createRoutingTable(RoutingTable routingTable) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna una Routing Table
	 * 
	 * @param routingTable
	 * @throws DriverConfigurazioneException
	 */
	public void updateRoutingTable(RoutingTable routingTable) throws DriverConfigurazioneException;
	
	/**
	 * Elimina una Routing Table
	 * 
	 * @param routingTable
	 * @throws DriverConfigurazioneException
	 */
	public void deleteRoutingTable(RoutingTable routingTable) throws DriverConfigurazioneException;
	
	
	
	
	
	
	// ACCESSO REGISTRO
	
	/**
	 * Crea le informazioni per l'accesso ai registri
	 * 
	 * @param registro
	 * @throws DriverConfigurazioneException
	 */
	public void createAccessoRegistro (AccessoRegistro registro) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna le informazioni per l'accesso ai registri
	 * 
	 * @param registro
	 * @throws DriverConfigurazioneException
	 */
	public void updateAccessoRegistro (AccessoRegistro registro) throws DriverConfigurazioneException;
	
	/**
	 * Elimina le informazioni per l'accesso ai registri
	 * 
	 * @param registro
	 * @throws DriverConfigurazioneException
	 */
	public void deleteAccessoRegistro (AccessoRegistro registro) throws DriverConfigurazioneException;
	
	
	
	
	
	
	// GESTIONE ERRORE
	
	/**
	 * Crea le informazioni per la gestione dell'errore per il ComponenteCooperazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void createGestioneErroreComponenteCooperazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna le informazioni per la gestione dell'errore per il ComponenteCooperazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void updateGestioneErroreComponenteCooperazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	/**
	 * Elimina le informazioni per la gestione dell'errore per il ComponenteCooperazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void deleteGestioneErroreComponenteCooperazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	/**
	 * Crea le informazioni per la gestione dell'errore per il ComponenteIntegrazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void createGestioneErroreComponenteIntegrazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna le informazioni per la gestione dell'errore per il ComponenteIntegrazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void updateGestioneErroreComponenteIntegrazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	/**
	 * Elimina le informazioni per la gestione dell'errore per il ComponenteIntegrazione
	 * 
	 * @param gestione
	 * @throws DriverConfigurazioneException
	 */
	public void deleteGestioneErroreComponenteIntegrazione(GestioneErrore gestione) throws DriverConfigurazioneException;
	
	
	
	
	
	
	/**
	 * Crea le informazioni sui servizi attivi della PdD
	 * 
	 * @param servizi
	 * @throws DriverConfigurazioneException
	 */
	public void createStatoServiziPdD(StatoServiziPdd servizi) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna le informazioni sui servizi attivi della PdD
	 * 
	 * @param servizi
	 * @throws DriverConfigurazioneException
	 */
	public void updateStatoServiziPdD(StatoServiziPdd servizi) throws DriverConfigurazioneException;
	
	/**
	 * Elimina le informazioni sui servizi attivi della PdD
	 * 
	 * @param servizi
	 * @throws DriverConfigurazioneException
	 */
	public void deleteStatoServiziPdD(StatoServiziPdd servizi) throws DriverConfigurazioneException;
	
	
	
	
	
	
	
	
	
	/**
	 * Crea le informazioni sulle proprieta' di sistema utilizzate dalla PdD
	 * 
	 * @param systemProperties
	 * @throws DriverConfigurazioneException
	 */
	public void createSystemPropertiesPdD(SystemProperties systemProperties) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna le informazioni sulle proprieta' di sistema utilizzate dalla PdD
	 * 
	 * @param systemProperties
	 * @throws DriverConfigurazioneException
	 */
	public void updateSystemPropertiesPdD(SystemProperties systemProperties) throws DriverConfigurazioneException;
	
	/**
	 * Elimina le informazioni sulle proprieta' di sistema utilizzate dalla PdD
	 * 
	 * @param systemProperties
	 * @throws DriverConfigurazioneException
	 */
	public void deleteSystemPropertiesPdD(SystemProperties systemProperties) throws DriverConfigurazioneException;
	
	
	
	
	
	
	
	
	// CONFIGURAZIONE GENERALE
	
	/**
	 * Crea una configurazione generale
	 * 
	 * @param configurazione
	 * @throws DriverConfigurazioneException
	 */
	public void createConfigurazione(Configurazione configurazione) throws DriverConfigurazioneException;
	
	/**
	 * Aggiorna una configurazione generale
	 * 
	 * @param configurazione
	 * @throws DriverConfigurazioneException
	 */
	public void updateConfigurazione(Configurazione configurazione) throws DriverConfigurazioneException;
	
	/**
	 * Elimina una configurazione generale
	 * 
	 * @param configurazione
	 * @throws DriverConfigurazioneException
	 */
	public void deleteConfigurazione(Configurazione configurazione) throws DriverConfigurazioneException;
	
	
	//RESET
	public void reset() throws DriverConfigurazioneException;
	
	//RESET
	public void reset(boolean resetConfigurazione) throws DriverConfigurazioneException;
	
}
