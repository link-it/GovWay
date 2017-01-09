/*
 * OpenSPCoop - Customizable API Gateway 
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
package org.openspcoop2.protocol.sdk.registry;

import java.util.List;

import org.openspcoop2.core.id.IDAccordo;
import org.openspcoop2.core.id.IDAccordoAzione;
import org.openspcoop2.core.id.IDAccordoCooperazione;
import org.openspcoop2.core.id.IDFruizione;
import org.openspcoop2.core.id.IDPortType;
import org.openspcoop2.core.id.IDPortTypeAzione;
import org.openspcoop2.core.id.IDServizio;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.AccordoCooperazione;
import org.openspcoop2.core.registry.AccordoServizioParteComune;
import org.openspcoop2.core.registry.AccordoServizioParteSpecifica;
import org.openspcoop2.core.registry.Azione;
import org.openspcoop2.core.registry.Operation;
import org.openspcoop2.core.registry.PortType;
import org.openspcoop2.core.registry.PortaDominio;
import org.openspcoop2.core.registry.Soggetto;

/**
 * IRegistryReader
 *
 * @author Poli Andrea (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 */
public interface IRegistryReader {

	// PDD
	
	public boolean existsPortaDominio(String nome);
	public PortaDominio getPortaDominio(String nome) throws RegistryNotFound;
	public List<String> findIdPorteDominio(Boolean operativo) throws RegistryNotFound;
	
	
	// SOGGETTI
	
	public boolean existsSoggettoByCodiceIPA(String codiceIPA);
	
	public boolean existsSoggetto(IDSoggetto idSoggetto);
	
	public IDSoggetto getIdSoggettoByCodiceIPA(String codiceIPA) throws RegistryNotFound;
	
	public String getCodiceIPA(IDSoggetto idSoggetto) throws RegistryNotFound;
	
	public String getDominio(IDSoggetto idSoggetto) throws RegistryNotFound;
	
	public Soggetto getSoggetto(IDSoggetto idSoggetto) throws RegistryNotFound;
	
	public List<IDSoggetto> findIdSoggetti(FiltroRicercaSoggetti filtro) throws RegistryNotFound;
		
	
	// ACCORDI PARTE COMUNE
	
	public AccordoServizioParteComune getAccordoServizioParteComune(IDAccordo idAccordo) throws RegistryNotFound;
	public AccordoServizioParteComune getAccordoServizioParteComune(IDAccordo idAccordo,boolean readAllegati) throws RegistryNotFound;
	public List<IDAccordo> findIdAccordiServizioParteComune(FiltroRicercaAccordi filtro) throws RegistryNotFound; 
	
	
	// ELEMENTI INTERNI ALL'ACCORDO PARTE COMUNE
	
	public PortType getPortType(IDPortType id) throws RegistryNotFound; 
	public List<IDPortType> findIdPortType(FiltroRicercaPortType filtro) throws RegistryNotFound;
	
	public Operation getAzionePortType(IDPortTypeAzione id) throws RegistryNotFound; 
	public List<IDPortTypeAzione> findIdAzionePortType(FiltroRicercaPortTypeAzioni filtro) throws RegistryNotFound; 
	
	public Azione getAzioneAccordo(IDAccordoAzione id) throws RegistryNotFound; 
	public List<IDAccordoAzione> findIdAzioneAccordo(FiltroRicercaAccordoAzioni filtro) throws RegistryNotFound; 
	
	
	// ACCORDI PARTE SPECIFICA
	
	public AccordoServizioParteSpecifica getAccordoServizioParteSpecifica(IDServizio idServizio) throws RegistryNotFound;
	public AccordoServizioParteSpecifica getAccordoServizioParteSpecifica(IDServizio idServizio,boolean readAllegati) throws RegistryNotFound;
	public List<IDServizio> findIdAccordiServizioParteSpecifica(FiltroRicercaServizi filtro) throws RegistryNotFound; 
	
	
	// ELEMENTI INTERNI ALL'ACCORDO PARTE SPECIFICA
	
	public List<IDFruizione> findIdFruizioni(FiltroRicercaFruizioniServizio filtro) throws RegistryNotFound; 
	
	
	// ACCORDI COOPERAZIONE
	
	public AccordoCooperazione getAccordoCooperazione(IDAccordoCooperazione idAccordo) throws RegistryNotFound;
	public AccordoCooperazione getAccordoCooperazione(IDAccordoCooperazione idAccordo,boolean readAllegati) throws RegistryNotFound;
	public List<IDAccordoCooperazione> findIdAccordiCooperazione(FiltroRicercaAccordi filtro) throws RegistryNotFound; 
	
	
}
