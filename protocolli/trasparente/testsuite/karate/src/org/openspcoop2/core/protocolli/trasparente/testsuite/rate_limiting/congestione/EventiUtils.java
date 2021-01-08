package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.congestione;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Predicate;

import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.DbUtils;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Headers;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.Utils.PolicyAlias;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.slf4j.Logger;

public class EventiUtils {

	
	public static void waitForDbEvents() {
		int eventi_db_delay = Integer.valueOf(System.getProperty("eventi_db_delay"));
		int to_sleep = eventi_db_delay*1000*4;
		
		log().debug("Attendo " + to_sleep/1000 + " secondi, affinchè vengano registrati gli eventi sul db...");
		org.openspcoop2.utils.Utilities.sleep(to_sleep);
	}
	
	
	
	public static Optional<String> getEventiGestione(String transactionId) {
		String query = "select eventi_gestione from transazioni WHERE id='"+transactionId+"'";
		log().info(query);
		
		return Optional.ofNullable((String) dbUtils().readRow(query).get("eventi_gestione"));
	}
	
	
	
	public static Map<String,Object> getCredenzialiMittenteById(String evento_gestione_id) {
		String query = "select * from credenziale_mittente where id="+evento_gestione_id;
		log().info(query);
		return dbUtils().readRow(query);
	}
	
	
	
	public static Optional<Map<String,Object>> getCredenzialiMittenteByTransactionId(String transactionId) {
		return getEventiGestione(transactionId)
			.map(EventiUtils::getCredenzialiMittenteById);
	}
	
	
	
	public static boolean testCredenzialiMittenteTransazione(String transactionId, Predicate<Map<String,Object>> predicate) {
		return getCredenzialiMittenteByTransactionId(transactionId)
				.filter( predicate )
				.isPresent();
		
	}
	
	
	public static List<Map<String,Object>> getNotificheEventi(LocalDateTime dataSpedizione) {
		
		ZonedDateTime zdt = dataSpedizione.atZone(ZoneId.systemDefault());
		java.sql.Timestamp filtroData = new java.sql.Timestamp(zdt.toInstant().toEpochMilli());
		
		String query = "select * from notifiche_eventi where ora_registrazione >= ?";
		log().info(query);
		
		return dbUtils().readRows(query, filtroData);
	}
	
	
	
	private static Logger log() {
		return ConfigLoader.getLogger();
	}
	
	
	private static DbUtils dbUtils() {
		return ConfigLoader.getDbUtils();
	}



	public static boolean findEventCongestioneRisolta(List<Map<String, Object>> events) {
		return events.stream()
			.anyMatch( ev -> {
				return  ev.get("tipo").equals("ControlloTraffico_SogliaCongestione") &&
						ev.get("codice").equals("ViolazioneRisolta") &&
						ev.get("severita").equals(3);
			});
	}



	public static boolean findEventCongestioneViolata(List<Map<String, Object>> events) {
		return events.stream()
				.anyMatch( ev -> {
					return  ev.get("tipo").equals("ControlloTraffico_SogliaCongestione") &&
							ev.get("codice").equals("Violazione") &&
							ev.get("severita").equals(2) &&
							((String) ev.get("descrizione")).startsWith("E' stata rilevata una congestione del sistema in seguito al superamento della soglia del");
				});
	}



	public static boolean findEventRLViolato(List<Map<String,Object>> events, PolicyAlias policy, String idServizio, Optional<String> gruppo) {
		
		return events.stream()
		.anyMatch( ev -> {
			String id_configurazione = (String) ev.get("id_configurazione");
			boolean match = ev.get("tipo").equals("RateLimiting_PolicyAPI") &&
					ev.get("codice").equals("Violazione") &&
					ev.get("severita").equals(1) && 
					id_configurazione.contains(policy.toString()) &&
					id_configurazione.contains(idServizio);
			
			if (gruppo.isPresent()) {
				match = match && id_configurazione.contains("gruppo '"+gruppo.get()+"'");
			}
					
			return match;
		});
	
	}



	public static boolean findEventRLViolazioneRisolta(List<Map<String,Object>> events, PolicyAlias policy, String idServizio, Optional<String> gruppo) {
		
		return events.stream()
		.anyMatch( ev -> {
			String id_configurazione = (String) ev.get("id_configurazione");
			boolean match = ev.get("tipo").equals("RateLimiting_PolicyAPI") &&
					ev.get("codice").equals("ViolazioneRisolta") &&
					ev.get("severita").equals(3) && 
					id_configurazione.contains(policy.toString()) &&
					id_configurazione.contains(idServizio);
			
			if (gruppo.isPresent()) {
				match = match && id_configurazione.contains("gruppo '"+gruppo.get()+"'");
			}
					
			return match;
		});
	
	}



	public static void checkEventiCongestioneAttivaConViolazioneRL(String idServizio, LocalDateTime dataSpedizione, Optional<String> gruppo,
			Vector<HttpResponse> responses) {
				
		waitForDbEvents();
		
		//  Devo trovare tra le transazioni generate dalle richieste, almeno una transazione che abbia entrambe le violazioni
		boolean found = responses
				.stream()
				.anyMatch( r -> {
					return testCredenzialiMittenteTransazione(
							r.getHeader(Headers.TransactionId),
							evento -> {						
								log().info(evento.toString());
								String credenziale = (String) evento.get("credenziale"); 
								return credenziale.contains("##ControlloTraffico_SogliaCongestione_Violazione##") &&
										credenziale.contains("##RateLimiting_PolicyAPI_Violazione_RichiesteSimultanee##");
							});
				
				});
		assertEquals(found, true);
	
		// 		Verifico che gli eventi di violazione e risoluzione siano stati scritti sul db
		
		List<Map<String, Object>> events = getNotificheEventi(dataSpedizione);		
		log().info(events.toString());
		
		assertEquals(true, findEventCongestioneViolata(events));
		assertEquals(true, findEventCongestioneRisolta(events));
		assertEquals(true, findEventRLViolato(events, PolicyAlias.RICHIESTE_SIMULTANEE, idServizio, gruppo));
		assertEquals(true, findEventRLViolazioneRisolta(events, PolicyAlias.RICHIESTE_SIMULTANEE, idServizio, gruppo));
	}



	public static void checkEventiCongestioneAttiva(LocalDateTime dataSpedizione, Vector<HttpResponse> responses) {
	
		waitForDbEvents();
		
		//		Devo trovare tra le transazioni generate dalle richieste, almeno una transazione che abbia la violazione
		
		boolean found = responses
				.stream()
				.anyMatch( r -> {
					return testCredenzialiMittenteTransazione(
							r.getHeader(Headers.TransactionId),
							evento -> {						
								log().info(evento.toString());
								return ((String) evento.get("credenziale")).contains("##ControlloTraffico_SogliaCongestione_Violazione##"); 
							});
				
				});
		
		assertEquals(found, true);
	
		// 		Devo verificare che gli eventi di violazione e risoluzione siano stati scritti sul db
		
		List<Map<String, Object>> events = getNotificheEventi(dataSpedizione);		
		log().info(events.toString());
		
		assertEquals(true, findEventCongestioneViolata(events));
		assertEquals(true, findEventCongestioneRisolta(events));		
	}

}
