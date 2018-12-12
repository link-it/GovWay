/*
 * GovWay - A customizable API Gateway 
 * http://www.govway.org
 *
 * from the Link.it OpenSPCoop project codebase
 * 
 * Copyright (c) 2005-2018 Link.it srl (http://link.it). 
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

package org.openspcoop2.web.ctrlstat.servlet.sa;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openspcoop2.core.commons.ErrorsHandlerCostant;
import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.ServizioApplicativo;
import org.openspcoop2.core.config.driver.DriverConfigurazioneNotFound;
import org.openspcoop2.core.id.IDServizioApplicativo;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.Soggetto;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziNotFound;
import org.openspcoop2.protocol.engine.utils.DBOggettiInUsoUtils;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.servlet.pdd.PddCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;

/**
 * ServiziApplicativiUtilities
 * 
 * @author Andrea Poli (apoli@link.it)
 * @author $Author$
 * @version $Rev$, $Date$
 * 
 */
public class ServiziApplicativiUtilities {

	public static void deleteServizioApplicativo(ServizioApplicativo sa, String userLogin, ServiziApplicativiCore saCore, ServiziApplicativiHelper saHelper, StringBuffer inUsoMessage, String newLine) throws Exception {
		
		IDServizioApplicativo idServizioApplicativo = new IDServizioApplicativo();
		idServizioApplicativo.setNome(sa.getNome());
		idServizioApplicativo.setIdSoggettoProprietario(new IDSoggetto(sa.getTipoSoggettoProprietario(), sa.getNomeSoggettoProprietario()));
		
		// Controllo che il sil non sia in uso
		Map<ErrorsHandlerCostant, List<String>> whereIsInUso = new Hashtable<ErrorsHandlerCostant, List<String>>();
		boolean normalizeObjectIds = !saHelper.isModalitaCompleta();
		boolean saInUso  = saCore.isServizioApplicativoInUso(idServizioApplicativo, whereIsInUso, saCore.isRegistroServiziLocale(), normalizeObjectIds);
		
		if (saInUso) {
			
			inUsoMessage.append(DBOggettiInUsoUtils.toString(idServizioApplicativo, whereIsInUso, true, newLine,normalizeObjectIds));
			inUsoMessage.append(newLine);

		} else {

			// Elimino il sil
			saCore.performDeleteOperation(userLogin, saHelper.smista(), sa);
		}
		
	}
	
	public static ServiziApplicativiGeneralInfo getGeneralInfo(boolean useIdSogg, String provider, List<String> listaTipiProtocollo,
			ServiziApplicativiCore saCore, ServiziApplicativiHelper saHelper, String superUser, boolean singlePdD) throws Exception {
		
		SoggettiCore soggettiCore = new SoggettiCore(saCore);
		PddCore pddCore = new PddCore(saCore);
		
		String[] soggettiList = null;
		String[] soggettiListLabel = null;
		
		String tipoProtocollo = null;
		String tipoENomeSoggetto = "";
		if(useIdSogg) {
			org.openspcoop2.core.config.Soggetto soggetto = soggettiCore.getSoggetto(Long.parseLong(provider)); 
			if(tipoProtocollo == null){
				tipoProtocollo = soggettiCore.getProtocolloAssociatoTipoSoggetto(soggetto.getTipo());
			}
			tipoENomeSoggetto = saHelper.getLabelNomeSoggetto(tipoProtocollo, soggetto.getTipo() , soggetto.getNome());
			
			soggettiList = new String[1];
			soggettiList[0] = soggetto.getId().toString();
			soggettiListLabel = new String[1];
			soggettiListLabel[0] =saHelper.getLabelNomeSoggetto(tipoProtocollo, soggetto.getTipo() , soggetto.getNome());
		}
		else {
			tipoProtocollo = saHelper.getParameter(ServiziApplicativiCostanti.PARAMETRO_SERVIZI_APPLICATIVI_PROTOCOLLO);
			if(tipoProtocollo == null){
				tipoProtocollo = saCore.getProtocolloDefault(saHelper.getSession(), listaTipiProtocollo);
			}
		
			List<String> tipiSoggettiCompatibiliGestitiProtocollo = soggettiCore.getTipiSoggettiGestitiProtocollo(tipoProtocollo);
			long providerTmp = -1;
			
			Search searchSoggetti = new Search(true);
			saHelper.setFilterSelectedProtocol(searchSoggetti, Liste.SOGGETTI);
			
			if(saCore.isRegistroServiziLocale()){
				List<Soggetto> list = null;
				if(saHelper.isSoggettoMultitenantSelezionato()) {
					IDSoggetto idSoggettoSelezionato = soggettiCore.convertSoggettoSelezionatoToID(saHelper.getSoggettoMultitenantSelezionato());
					list = new ArrayList<>();
					try {
						list.add(soggettiCore.getSoggettoRegistro(idSoggettoSelezionato));
					}catch(DriverRegistroServiziNotFound notFound) {}
				}
				else {
					if(saCore.isVisioneOggettiGlobale(superUser)){
						list = soggettiCore.soggettiRegistroList(null,searchSoggetti);
					}else{
						list = soggettiCore.soggettiRegistroList(superUser,searchSoggetti); 
					}
				}
				
				// Filtro per pdd esterne
				if(singlePdD){
					List<Soggetto> listFiltrata = new ArrayList<Soggetto>();
					for (Soggetto soggetto : list) {
						boolean pddEsterna = pddCore.isPddEsterna(soggetto.getPortaDominio());
						if(!pddEsterna){
							listFiltrata.add(soggetto);
						}
					}
					list = listFiltrata;
				}
				if (list.size() > 0) {
					List<Soggetto> listFiltrataCompatibileProtocollo =  new ArrayList<Soggetto>();
					for (Soggetto soggetto : list) {
						if(tipiSoggettiCompatibiliGestitiProtocollo.contains(soggetto.getTipo())){
							listFiltrataCompatibileProtocollo.add(soggetto);
							if(providerTmp < 0)
								providerTmp = soggetto.getId();
						}
					}
					if (listFiltrataCompatibileProtocollo.size() > 0) {
						soggettiList = new String[listFiltrataCompatibileProtocollo.size()];
						soggettiListLabel = new String[listFiltrataCompatibileProtocollo.size()];
						int i = 0;
						for (Soggetto soggetto : listFiltrataCompatibileProtocollo) {
							soggettiList[i] = soggetto.getId().toString();
							soggettiListLabel[i] =saHelper.getLabelNomeSoggetto(tipoProtocollo, soggetto.getTipo() , soggetto.getNome());
							i++;
						}
					}
				}
			}
			else{
				List<org.openspcoop2.core.config.Soggetto> list = null;
				if(saHelper.isSoggettoMultitenantSelezionato()) {
					IDSoggetto idSoggettoSelezionato = soggettiCore.convertSoggettoSelezionatoToID(saHelper.getSoggettoMultitenantSelezionato());
					list = new ArrayList<>();
					try {
						list.add(soggettiCore.getSoggetto(idSoggettoSelezionato));
					}catch(DriverConfigurazioneNotFound notFound) {}
				}
				else {
					if(saCore.isVisioneOggettiGlobale(superUser)){
						list = soggettiCore.soggettiList(null,searchSoggetti);
					}else{
						list = soggettiCore.soggettiList(superUser,searchSoggetti); 
					}
				}
				
				List<org.openspcoop2.core.config.Soggetto> listFiltrataCompatibileProtocollo =  new ArrayList<org.openspcoop2.core.config.Soggetto>();
				for (org.openspcoop2.core.config.Soggetto soggetto : list) {
					if(tipiSoggettiCompatibiliGestitiProtocollo.contains(soggetto.getTipo())){
						listFiltrataCompatibileProtocollo.add(soggetto);
						if(providerTmp < 0)
							providerTmp = soggetto.getId();
					}
				}
				
				if (listFiltrataCompatibileProtocollo.size() > 0) {
					soggettiList = new String[listFiltrataCompatibileProtocollo.size()];
					soggettiListLabel = new String[listFiltrataCompatibileProtocollo.size()];
					int i = 0;
					for (org.openspcoop2.core.config.Soggetto soggetto : listFiltrataCompatibileProtocollo) {
						soggettiList[i] = soggetto.getId().toString();
						soggettiListLabel[i] =saHelper.getLabelNomeSoggetto(tipoProtocollo, soggetto.getTipo() , soggetto.getNome());
						i++;
					}
				}
			}
			
			if(provider == null){
				provider = providerTmp +"";
			}
		}
		
		ServiziApplicativiGeneralInfo saGeneralInfo = new ServiziApplicativiGeneralInfo();
		saGeneralInfo.setSoggettiList(soggettiList);
		saGeneralInfo.setSoggettiListLabel(soggettiListLabel);
		saGeneralInfo.setTipoENomeSoggetto(tipoENomeSoggetto);
		saGeneralInfo.setTipoProtocollo(tipoProtocollo);
		saGeneralInfo.setProvider(provider);
		return saGeneralInfo;
	}
}