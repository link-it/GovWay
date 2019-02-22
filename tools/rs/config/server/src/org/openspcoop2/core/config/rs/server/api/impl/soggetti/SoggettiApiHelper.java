package org.openspcoop2.core.config.rs.server.api.impl.soggetti;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.openspcoop2.core.commons.Liste;
import org.openspcoop2.core.config.driver.DriverConfigurazioneException;
import org.openspcoop2.core.config.rs.server.api.impl.HttpRequestWrapper;
import org.openspcoop2.core.config.rs.server.api.impl.Helper;
import org.openspcoop2.core.config.rs.server.model.AuthenticationHttpBasic;
import org.openspcoop2.core.config.rs.server.model.AuthenticationHttps;
import org.openspcoop2.core.config.rs.server.model.AuthenticationPrincipal;
import org.openspcoop2.core.config.rs.server.model.DominioEnum;
import org.openspcoop2.core.config.rs.server.model.Soggetto;
import org.openspcoop2.core.config.rs.server.model.SoggettoItem;
import org.openspcoop2.core.constants.CostantiDB;
import org.openspcoop2.core.id.IDSoggetto;
import org.openspcoop2.core.registry.Connettore;
import org.openspcoop2.core.registry.CredenzialiSoggetto;
import org.openspcoop2.core.registry.RuoliSoggetto;
import org.openspcoop2.core.registry.RuoloSoggetto;
import org.openspcoop2.core.registry.constants.PddTipologia;
import org.openspcoop2.core.registry.constants.RuoloContesto;
import org.openspcoop2.core.registry.constants.RuoloTipologia;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziException;
import org.openspcoop2.core.registry.driver.DriverRegistroServiziNotFound;
import org.openspcoop2.core.registry.driver.FiltroRicercaRuoli;
import org.openspcoop2.protocol.engine.ProtocolFactoryManager;
import org.openspcoop2.utils.jaxrs.fault.FaultCode;
import org.openspcoop2.web.ctrlstat.core.Search;
import org.openspcoop2.web.ctrlstat.dao.PdDControlStation;
import org.openspcoop2.web.ctrlstat.driver.DriverControlStationException;
import org.openspcoop2.web.ctrlstat.servlet.connettori.ConnettoriCostanti;
import org.openspcoop2.web.ctrlstat.servlet.pdd.PddCore;
import org.openspcoop2.web.ctrlstat.servlet.soggetti.SoggettiCore;

public class SoggettiApiHelper {

	public static final void overrideAuthParams(Soggetto soggetto, HttpRequestWrapper wrap) {
		wrap.overrideParameter(
				ConnettoriCostanti.PARAMETRO_CREDENZIALI_TIPO_AUTENTICAZIONE, 
				Helper.tipoAuthFromModalitaAccesso.get(soggetto.getModalitaAccesso())
		);
		
		switch(soggetto.getModalitaAccesso()) {
		case HTTP_BASIC: {
			AuthenticationHttpBasic c = (AuthenticationHttpBasic) soggetto.getCredenziali();
			wrap.overrideParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_USERNAME, c.getUsername());
			wrap.overrideParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_PASSWORD, c.getPassword());
			break;
		}
		case HTTPS: {
			AuthenticationHttps c = (AuthenticationHttps) soggetto.getCredenziali();
			wrap.overrideParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_SUBJECT, c.getSubject());
			break;
		}
		case PRINCIPAL: {
			AuthenticationPrincipal c = (AuthenticationPrincipal) soggetto.getCredenziali();
			wrap.overrideParameter(ConnettoriCostanti.PARAMETRO_CREDENZIALI_AUTENTICAZIONE_PRINCIPAL, c.getUserid());
			break;
		}
		}
	}

	// In queste logiche non considero:
	//		+ isSinglePdd ( che è sempre true, serve a gestire più govway remoti per mezzo di una pdd operativa, in tal caso ogni soggetto deve averne una)
	
	// La tipologia (erogatore\fruitore) viene specificata solo nel caso in cui il soggetto sia esterno e serve a stabilire
	// le informazioni di autenticazione nel caso esso sia un fruitore, noi consideriamo tutti fruitori.
	
	
	public static void updateSoggetto(Soggetto s, org.openspcoop2.core.registry.Soggetto ret, SoggettiEnv env) throws DriverRegistroServiziNotFound, DriverRegistroServiziException, DriverControlStationException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		ret.setNome(s.getNome());
		ret.setDescrizione(s.getDescrizione());		
	
		// Un soggetto esterno abbiamo detto che può averla, ma durante la creazione essa non viene specificata perciò è lasciata a null.
		// Un soggetto Interno DEVE avere una porta di dominio di tipo operativo, e gliene viene assegnata la prima trovata.
		if (s.getDominio() == DominioEnum.INTERNO) {
	
			// TODO: Questo controllo va migliorato, per essere precisi dobbiamo controllare che non stiamo registrando un soggetto interno
			// con un nome diverso da quelli presenti nel db, quindi se non ci sono soggetti interni nel db dobbiamo poterlo creare.
			if(!env.multitenant)
					throw FaultCode.RICHIESTA_NON_VALIDA.toException("Per registrare un nuovo soggetto interno passare alla modalità multitenant");
			
			String nome_pdd = env.pddCore.pddList(null, new Search(true)).stream()	
					.filter(pdd -> PddTipologia.OPERATIVO.toString().equals(pdd.getTipo()))
					.map( pdd -> pdd.getNome())
					.findFirst().orElse("");
			
			if (nome_pdd.length() == 0) {
				throw FaultCode.RICHIESTA_NON_VALIDA.toException("Nessuna porta operativa da associare al soggetto interno");
			}
			ret.setPortaDominio(nome_pdd);
		}
		
		if (env.soggettiCore.isSupportatoAutenticazioneSoggetti(env.tipo_protocollo)) {
			CredenzialiSoggetto credenziali = Helper.apiCredenzialiToGovwayCred(
						s.getCredenziali(),
						s.getModalitaAccesso(),
						CredenzialiSoggetto.class,
						org.openspcoop2.core.registry.constants.CredenzialeTipo.class
			);		
			ret.setCredenziali(credenziali);
		}
	
	}
	
	public static final org.openspcoop2.core.registry.Soggetto soggettoApiToRegistro(Soggetto s, SoggettiEnv env) 
			throws DriverRegistroServiziNotFound, DriverRegistroServiziException, DriverControlStationException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		final org.openspcoop2.core.registry.Soggetto ret = new org.openspcoop2.core.registry.Soggetto();
		updateSoggetto(s, ret, env);
		
		final String protocollo = env.tipo_protocollo;
		final IDSoggetto idSoggetto = new IDSoggetto(protocollo,s.getNome());

		ret.setVersioneProtocollo(env.soggettiCore.getVersioneDefaultProtocollo(protocollo));
		ret.setIdentificativoPorta(env.soggettiCore.getIdentificativoPortaDefault(protocollo, idSoggetto));
		ret.setCodiceIpa(env.soggettiCore.getCodiceIPADefault(protocollo, idSoggetto, false));
		ret.setSuperUser(env.userLogin);
		ret.setTipo(env.tipo_soggetto);
		
		// I possibili ruoli da assegnare sono quelli con contesto porta_applicativa (erogazione) e tipologia interna.
		FiltroRicercaRuoli filtroRuoli = new FiltroRicercaRuoli();
		filtroRuoli.setContesto(RuoloContesto.PORTA_APPLICATIVA);
		filtroRuoli.setTipologia(RuoloTipologia.INTERNO);
		List<String> ruoliAmmessi = env.soggettiCore.getAllRuoli(filtroRuoli);
		
		RuoliSoggetto ruoliSoggetto = new RuoliSoggetto();
		s.getRuoli().forEach( rname -> {
			if (!ruoliAmmessi.contains(rname))
				throw new RuntimeException("Non esiste alcun ruolo con nome " + rname + " da associare al soggetto.");
			RuoloSoggetto rs = new RuoloSoggetto();
			rs.setNome(rname);
			ruoliSoggetto.addRuolo(rs);
		});
		
		ret.setRuoli(ruoliSoggetto);
		
		// Di base privato è false, ha a che fare con una checkbox che spunterà fuori solo in modalità completa e non in quella avanzata.
		ret.setPrivato(false);		
				
		final Connettore connettore = new Connettore();
		connettore.setTipo(CostantiDB.CONNETTORE_TIPO_DISABILITATO);
		ret.setConnettore(connettore);
						
		return ret;
	}
	
	public static final Soggetto soggettoRegistroToApi(org.openspcoop2.core.registry.Soggetto s, PddCore pddCore, SoggettiCore soggettiCore) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, DriverRegistroServiziException, DriverConfigurazioneException {
		Soggetto ret = new Soggetto();
		ret.setNome(s.getNome());
		ret.setDescrizione(s.getDescrizione());
		
		CredenzialiSoggetto soggCred = s.getCredenziali();
		ret.setCredenziali(Helper.govwayCredenzialiToApi(
				soggCred,
				CredenzialiSoggetto.class,
				org.openspcoop2.core.registry.constants.CredenzialeTipo.class
			));
		ret.setModalitaAccesso(Helper.credenzialiToModalita(ret.getCredenziali()));
		
		ret.setDominio(DominioEnum.ESTERNO);
		try {
			if (s.getPortaDominio() != null) {
				PdDControlStation p = pddCore.getPdDControlStation(s.getPortaDominio());
				if (PddTipologia.OPERATIVO.toString().equals(p.getTipo()))
					ret.setDominio(DominioEnum.INTERNO);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		ret.setRuoli(soggettiCore.soggettiRuoliList(s.getId(), new Search()));
	
		return ret;
	}
	
	//String getTipoAuth
	
	public static final org.openspcoop2.core.config.Soggetto soggettoRegistroToConfig(org.openspcoop2.core.registry.Soggetto s, boolean isRouter) {
		org.openspcoop2.core.config.Soggetto ret = new org.openspcoop2.core.config.Soggetto();
		
		ret.setNome(s.getNome());
		ret.setTipo(s.getTipo());
		ret.setDescrizione(s.getDescrizione());
		ret.setIdentificativoPorta(s.getIdentificativoPorta());
		ret.setRouter(isRouter);
		ret.setSuperUser(s.getSuperUser());
		
		
		return ret;
	}


	public static SoggettoItem soggettoRegistroToItem(org.openspcoop2.core.registry.Soggetto s, PddCore pddCore, SoggettiCore soggettiCore) {
		SoggettoItem ret = new SoggettoItem();
		ret.setNome(s.getNome());
		ret.setDominio(DominioEnum.ESTERNO);
		
		try {
			
			if (s.getPortaDominio() != null) {
				PdDControlStation p = pddCore.getPdDControlStation(s.getPortaDominio());
				if (PddTipologia.OPERATIVO.toString().equals(p.getTipo()))
					ret.setDominio(DominioEnum.INTERNO);
			}
			
			// Recupero il numero di ruoli.
			Search searchForCount = new Search(true,1);
			soggettiCore.soggettiRuoliList(s.getId(),searchForCount);
			int numRuoli = searchForCount.getNumEntries(Liste.SOGGETTI_RUOLI);
			ret.setCountRuoli(numRuoli);
	
			String tipo_protocollo = ProtocolFactoryManager.getInstance().getProtocolByOrganizationType(s.getTipo());
			ret.setProfilo(Helper.profiloFromTipoProtocollo.get(tipo_protocollo));
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return ret;
	}

	

}