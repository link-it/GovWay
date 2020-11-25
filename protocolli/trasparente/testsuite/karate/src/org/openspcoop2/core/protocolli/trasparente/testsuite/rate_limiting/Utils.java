package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste.NumeroRichiestePolicyInfo;
import org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting.numero_richieste.RichiesteSimultaneePolicyInfo;
import org.openspcoop2.utils.LoggerWrapperFactory;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Utils {
	
	public enum PolicyAlias {
		RICHIESTE_SIMULTANEE("RichiesteSimultanee"), 
		MINUTO("Minuto"),	
		ORARIO("Orario"),
		GIORNALIERO("Giornaliero");
		
		public final String value;
		
		PolicyAlias(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return this.value;
		}
	}
	
	private static final DocumentBuilder docBuilder;
	static {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = dbf.newDocumentBuilder();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Logger logRateLimiting = ConfigLoader.getLogger();

	
	/**
	 * Esege `count` richieste `request` parallele 
	 * 
	 */
	public static Vector<HttpResponse> makeParallelRequests(HttpRequest request, int count) throws InterruptedException {
		logRateLimiting = ConfigLoader.getLogger();

		final Vector<HttpResponse> responses = new Vector<>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);

		for (int i = 0; i < count; i++) {
			executor.execute(() -> {
				try {
					logRateLimiting.info(request.getMethod() + " " + request.getUrl());
					responses.add(HttpUtilities.httpInvoke(request));
					logRateLimiting.info("Richiesta effettuata..");
				} catch (UtilsException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});
		}
		
		executor.shutdown();
		executor.awaitTermination(20, TimeUnit.SECONDS);
		
		logRateLimiting.info("RESPONSES: ");
		responses.forEach(r -> {
			logRateLimiting.info("statusCode: " + r.getResultHTTPOperation());
			logRateLimiting.info("headers: " + r.getHeaders());
		});

		return responses;
	}
	
	
	public static Vector<HttpResponse> makeSequentialRequests(HttpRequest request, int count) {
		final Vector<HttpResponse> responses = new Vector<>();

		for(int i=0; i<count;i++) {
			logRateLimiting.info(request.getMethod() + " " + request.getUrl());
			try {
				responses.add(HttpUtilities.httpInvoke(request));
				logRateLimiting.info("Richiesta effettuata..");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}		
		}
		
		logRateLimiting.info("RESPONSES: ");
		responses.forEach(r -> {
			logRateLimiting.info("statusCode: " + r.getResultHTTPOperation());
			logRateLimiting.info("headers: " + r.getHeaders());
		});
		
		return responses;		
	}
	
	public static Element buildXmlElement(byte[] content) {
		InputStream is = new ByteArrayInputStream(content);
		Document doc;
		try {
			doc = docBuilder.parse(is);
			return doc.getDocumentElement();
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void resetCounters(List<String> idPolicies) {
		idPolicies.forEach( idPolicy -> {
			try {
				resetCounters(idPolicy);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	
	public static void resetCounters(String idPolicy) throws UtilsException, HttpUtilsException {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "resetPolicyCounters",
				"paramValue", idPolicy
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		logRateLimiting.info("Resetto la policy di rate limiting sulla url: " + jmxUrl );
		HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
	}
	
	
	public static String getPolicy(String idPolicy) {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "getPolicy",
				"paramValue", idPolicy
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		logRateLimiting.info("Ottengo le informazioni sullo stato della policy: " + jmxUrl );
		try {
			return new String(HttpUtilities.getHTTPResponse(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password")).getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void toggleErrorDisclosure(boolean enabled) {
	
		String value = "false";
		if (enabled) {
			value = "true";
		}
		
		List<Map<String, String>> requestsParams = List.of(
				Map.of(
						"resourceName", "ConfigurazionePdD",
						"attributeName", "transactionErrorForceSpecificTypeInternalResponseError",
						"attributeBooleanValue", value
					),
				Map.of(
						"resourceName", "ConfigurazionePdD",
						"attributeName", "transactionErrorForceSpecificTypeBadResponse",
						"attributeBooleanValue", value
					),
				Map.of(
						"resourceName", "ConfigurazionePdD",
						"attributeName", "transactionErrorForceSpecificDetails",
						"attributeBooleanValue", value
					)
				);
				
		requestsParams.forEach( queryParams -> {
			String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
			logRateLimiting.info("Imposto la error disclosure: " + jmxUrl );
				
			try {
				HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
		
	
	public static Integer getThreadsAttiviGovWay() {
		
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"attributeName", "threadsAttivi"
			);
		
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		logRateLimiting.info("Ottengo le informazioni sul numero dei threads attivi: " + jmxUrl );
		try {
			return Integer.valueOf(
					new String(HttpUtilities.getHTTPResponse(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password")).getContent())
				);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}

	
	/**
	 * Sospende il thread corrente finchè non è scoccato il prossimo minuto
	 * 
	 * @throws InterruptedException
	 */
	public static void waitForNewMinute() {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		int remaining = 60 - now.get(Calendar.SECOND);
		if (remaining <= 20) {
			int to_wait = (63 - now.get(Calendar.SECOND)) *1000;
			logRateLimiting.info("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			org.openspcoop2.utils.Utilities.sleep(to_wait);
		}				
	}
	
	/**
	 * Sospende il thread corrente finchè non è scoccata la prossima ora.
	 * NOTA: il timer viene attivato solo se mancano meno di due minuti allo scoccare dell'ora.
	 * 
	 * @throws InterruptedException
	 */
	public static void waitForNewHour() throws InterruptedException {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		int remaining = 60 - now.get(Calendar.MINUTE); 
		if (remaining <= 2) {
			int to_wait = (remaining+1) * 60 * 1000;
			logRateLimiting.info("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			java.lang.Thread.sleep(to_wait);
		}					
	}
	
	/**
	 * Sospende il thread corrente finchè non è scoccata il prossimo giorno
	 * NOTA: il timer viene attivato solo se mancano meno di due minuti allo scoccare del giorno
	 * 
	 * @throws InterruptedException
	 */
	public static void waitForNewDay() throws InterruptedException {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.HOUR_OF_DAY) == 23) {
			int remaining = 60 - now.get(Calendar.MINUTE); 
			if (remaining <= 2) {
				int to_wait = (remaining+1) * 60 * 1000;
				logRateLimiting.info("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
				java.lang.Thread.sleep(to_wait);
			}							
		}			
	}

	public static void waitForZeroGovWayThreads() {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		
		while(true) {
			try {
				Integer threadAttivi = getThreadsAttiviGovWay();
				assertEquals(Integer.valueOf(0), threadAttivi);
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		}
		
	}

	public static void checkPreConditionsNumeroRichieste(List<String> idPolicies) {
		idPolicies.forEach( id -> checkPreConditionsNumeroRichieste(id));
	}

	
	public static void checkPreConditionsNumeroRichieste(String idPolicy)  {
		String jmxPolicyInfo = getPolicy(idPolicy);
		logRateLimiting.info(jmxPolicyInfo);
		
		// Se non sono mai state fatte richieste che attivano la policy, ottengo questa
		// risposta, e le precondizioni sono soddisfatte
		
		if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
			return;
		}
		
		NumeroRichiestePolicyInfo polInfo = new NumeroRichiestePolicyInfo(jmxPolicyInfo);
		
		assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
		assertEquals(Integer.valueOf(0), polInfo.richiesteConteggiate);
		assertEquals(Integer.valueOf(0), polInfo.richiesteBloccate);
	}


	public static void checkPostConditionsNumeroRichieste(List<String> idPolicies, int maxRequests) {
		idPolicies.forEach( id -> checkPostConditionsNumeroRichieste(id, maxRequests));
	}

	
	public static void checkPostConditionsNumeroRichieste(String idPolicy, int maxRequests) {
				
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				logRateLimiting.info(jmxPolicyInfo);
				
				NumeroRichiestePolicyInfo polInfo = new NumeroRichiestePolicyInfo(jmxPolicyInfo);
			
				assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
				assertEquals(Integer.valueOf(maxRequests), polInfo.richiesteConteggiate);
				assertEquals(Integer.valueOf(1), polInfo.richiesteBloccate);
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		}
	}

	
	public static void checkPostConditionsRichiesteSimultanee(List<String> idPolicies) {
		idPolicies.forEach( id -> checkPostConditionsRichiesteSimultanee(id));
	}


	public static void checkPostConditionsRichiesteSimultanee(String idPolicy) {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}
				
				logRateLimiting.info(jmxPolicyInfo);
				RichiesteSimultaneePolicyInfo polInfo = new RichiesteSimultaneePolicyInfo(jmxPolicyInfo);
				assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
		
	}


	public static void checkPreConditionsRichiesteSimultanee(List<String> idPolicies) {
		idPolicies.forEach( id -> checkPreConditionsRichiesteSimultanee(id));
	}

	
	public static void checkPreConditionsRichiesteSimultanee(String idPolicy) {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}
				
				logRateLimiting.info(jmxPolicyInfo);
				RichiesteSimultaneePolicyInfo polInfo = new RichiesteSimultaneePolicyInfo(jmxPolicyInfo);
				assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}


	public static void checkXLimitWindows(String header, Integer currentValue, Map<Integer, Integer> windowMap) {
		String[] values = header.split(",");
		assertEquals(currentValue, Integer.valueOf(values[0]));
		for(int i=1;i<values.length;i++) {
			String[] window = values[i].trim().split(";");
			String windowSize = window[1].split("=")[1];
			assertEquals(
					windowMap.get(Integer.valueOf(windowSize)),
					Integer.valueOf(window[0])
				);
		}
		
		
	}


	/**
	 * Parsa le informazioni sulla policy ottenute tramite jmx in una map di propietà. 
	 * 
	 * @param idPolicy
	 * @return
	 */
	public static Map<String,String> parsePolicy(String jmxPolicyInfo) {
		Map<String, String> ret = new HashMap<>();
		
		String[] lines = jmxPolicyInfo.split(System.lineSeparator());
		for (String l : lines) {
			l = l.strip();
			String[] keyValue = l.split(":");
			if(keyValue.length == 2) {
				ret.put(keyValue[0].strip(), keyValue[1].strip());
			}
		}
		return ret;
	}


	public static void waitForZeroActiveRequests(String idPolicy, int richiesteConteggiate) {
		Logger logRateLimiting = LoggerWrapperFactory.getLogger("testsuite.rate_limiting");
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_check_policy_conditions_retry"));
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
					break;
				}				
				logRateLimiting.info(jmxPolicyInfo);
				Map<String, String> policyValues = parsePolicy(jmxPolicyInfo);
				assertEquals("0", policyValues.get("Richieste Attive"));
				assertEquals(Integer.valueOf(richiesteConteggiate), Integer.valueOf(policyValues.get("Numero Richieste Conteggiate")));
	
				break;
			} catch (AssertionError e) {
				if(remainingChecks == 0) {
					throw e;
				}
				remainingChecks--;
				org.openspcoop2.utils.Utilities.sleep(500);
			}
		} 
	}


	public static void checkXLimitHeader(String header, int maxLimit) {
		// La configurazione di govway potrebbe utilizzare le window anche se nel test la proprietà
		// è disabilitata. Scriviamo un test che sia indipendente da questa cosa, e che controlli
		// in ogni caso il primo valore.
		
		String limit = header.split(",")[0].trim();
		assertEquals(String.valueOf(maxLimit),limit);
	}
	

}
