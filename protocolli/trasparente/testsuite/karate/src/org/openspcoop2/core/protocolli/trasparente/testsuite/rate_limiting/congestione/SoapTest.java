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

package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.congestione;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.SoapBodies;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
/**
 * 
 * @author Francesco Scarlato {scarlato@link.it}
 *
 */
public class SoapTest extends ConfigLoader {

	public static final int sogliaCongestione = Integer.valueOf(System.getProperty("soglia_congestione"));
	
	@Test
	public void congestioneAttivaErogazione() {
		congestioneAttiva(System.getProperty("govway_base_path") + "/SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	
	@Test
	public void congestioneAttivaFruizione() {
		congestioneAttiva(System.getProperty("govway_base_path") + "/out/SoggettoInternoTestFruitore/SoggettoInternoTest/NumeroRichiesteSoap/v1");
	}
	
	
	/**
	 * Controlla che il sistema entri effettivamente in congestione.
	 * 
	 */
	
	public void congestioneAttiva(String url) {
		String body = SoapBodies.get(PolicyAlias.NO_POLICY);
		
		LocalDateTime dataSpedizione = LocalDateTime.now();		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/soap+xml");
		request.setMethod(HttpRequestMethod.POST);
		request.setUrl(url);
		request.setContent(body.getBytes());
		
		Vector<HttpResponse> responses = Utils.makeParallelRequests(request, sogliaCongestione+1);
		
		checkEventi(dataSpedizione, responses);
		
	}


	public static void checkEventi(LocalDateTime dataSpedizione, Vector<HttpResponse> responses) {
		// Recupero gli eventi
		int eventi_db_delay = Integer.valueOf(System.getProperty("eventi_db_delay"));
		int to_sleep = eventi_db_delay*1000*4;
		
		logRateLimiting.debug("Attendo " + to_sleep/1000 + " secondi, affinchè vengano registrati gli eventi sul db...");
		org.openspcoop2.utils.Utilities.sleep(to_sleep);

		
		//		Devo trovare tra le transazioni generate dalle richieste, almeno una transazione che abbia la violazione
		
		boolean found = false;
		for (var r : responses) {
			String query = "select eventi_gestione from transazioni WHERE id='"+r.getHeader(Headers.TransactionId)+"'";
			logRateLimiting.info(query);
			String eventi_gestione = (String) dbUtils.readRow(query).get("eventi_gestione");
			
			if (StringUtils.isEmpty(eventi_gestione)) {
				continue;
			}
			
			query = "select * from credenziale_mittente where id="+eventi_gestione;
			logRateLimiting.info(query);
			Map<String, Object> evento = dbUtils.readRow(query);
			
			logRateLimiting.info(evento.toString());
			found = found || ((String) evento.get("credenziale")).contains("##ControlloTraffico_SogliaCongestione_Violazione##");
		}
		
		assertEquals(found, true);
		
		String query = "select * from notifiche_eventi where ora_registrazione >= '"+ dataSpedizione +"'";
		logRateLimiting.info(query);
		
		List<Map<String, Object>> events = dbUtils.readRows(query);
		
		logRateLimiting.info(events.toString());
		boolean sogliaViolata = events.stream()
				.anyMatch( ev -> {
					return  ev.get("tipo").equals("ControlloTraffico_SogliaCongestione") &&
							ev.get("codice").equals("Violazione") &&
							ev.get("severita").equals(2) &&
							((String) ev.get("descrizione")).startsWith("E' stata rilevata una congestione del sistema in seguito al superamento della soglia del");
				});
		
		boolean sogliaRisolta = events.stream()
			.anyMatch( ev -> {
				return  ev.get("tipo").equals("ControlloTraffico_SogliaCongestione") &&
						ev.get("codice").equals("ViolazioneRisolta") &&
						ev.get("severita").equals(3);
			});
		
		assertEquals(true, sogliaRisolta);
		assertEquals(true, sogliaViolata);		
	}
	

	

}
