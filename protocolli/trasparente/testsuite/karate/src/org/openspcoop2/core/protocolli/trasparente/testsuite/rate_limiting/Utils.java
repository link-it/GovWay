package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Utils {
	
	public enum TipoPolicy {
		RICHIESTE_MINUTO("RichiestePerMinuto"),
		RICHIESTE_ORARIE("RichiesteOrarie"),
		RICHIESTE_GIORNALIERE("RichiesteGiornaliere"),
		RICHIESTE_SIMULTANEE("RichiesteSimultanee");
		
		public final String value;
		
		TipoPolicy(String value) {
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
	
	
	/**
	 * Esege `count` richieste `request` parallele 
	 * 
	 */
	public static Vector<HttpResponse> makeParallelRequests(HttpRequest request, int count) throws InterruptedException {

		final Vector<HttpResponse> responses = new Vector<>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);

		for (int i = 0; i < count; i++) {
			executor.execute(() -> {
				try {
					System.out.println(request.getMethod() + " " + request.getUrl());
					responses.add(HttpUtilities.httpInvoke(request));
					System.out.println("Richiesta effettuata..");
				} catch (UtilsException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			});
		}
		
		executor.shutdown();
		executor.awaitTermination(20, TimeUnit.SECONDS);
		
		System.out.println("RESPONSES: ");
		responses.forEach(r -> {
			System.out.println("statusCode: " + r.getResultHTTPOperation());
			System.out.println("headers: " + r.getHeaders());
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
	
	
	static void resetCounters(List<String> idPolicies) {
		idPolicies.forEach( idPolicy -> {
			try {
				resetCounters(idPolicy);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	
	static void resetCounters(String idPolicy) throws UtilsException, HttpUtilsException {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "resetPolicyCounters",
				"paramValue", idPolicy
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		System.out.println("Resetto la policy di rate limiting sulla url: " + jmxUrl );
		HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
	}
	
	
	static String getPolicy(String idPolicy) {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "getPolicy",
				"paramValue", idPolicy
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		System.out.println("Ottengo le informazioni sullo stato della policy: " + jmxUrl );
		try {
			return new String(HttpUtilities.getHTTPResponse(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password")).getContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * Sospende il thread corrente finchè non è scoccato il prossimo minuto
	 * 
	 * @throws InterruptedException
	 */
	static void waitForNewMinute() throws InterruptedException {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		int to_wait = (63 - now.get(Calendar.SECOND)) *1000;
		System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
		java.lang.Thread.sleep(to_wait);		
	}
	
	/**
	 * Sospende il thread corrente finchè non è scoccata la prossima ora.
	 * NOTA: il timer viene attivato solo se mancano meno di due minuti allo scoccare dell'ora.
	 * 
	 * @throws InterruptedException
	 */
	static void waitForNewHour() throws InterruptedException {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		int remaining = 60 - now.get(Calendar.MINUTE); 
		if (remaining <= 2) {
			int to_wait = (remaining+1) * 60 * 1000;
			System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			java.lang.Thread.sleep(to_wait);
		}					
	}
	
	/**
	 * Sospende il thread corrente finchè non è scoccata il prossimo giorno
	 * NOTA: il timer viene attivato solo se mancano meno di due minuti allo scoccare del giorno
	 * 
	 * @throws InterruptedException
	 */
	static void waitForNewDay() throws InterruptedException {
		if ("false".equals(System.getProperty("wait"))) {
			return;
		}
		
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.HOUR_OF_DAY) == 23) {
			int remaining = 60 - now.get(Calendar.MINUTE); 
			if (remaining <= 2) {
				int to_wait = (remaining+1) * 60 * 1000;
				System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
				java.lang.Thread.sleep(to_wait);
			}							
		}			
	}


	public static void checkPreConditionsNumeroRichieste(List<String> idPolicies) {
		idPolicies.forEach( id -> checkPreConditionsNumeroRichieste(id));
	}

	
	public static void checkPreConditionsNumeroRichieste(String idPolicy)  {
		String jmxPolicyInfo = getPolicy(idPolicy);
		System.out.println(jmxPolicyInfo);
		
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
				
		int remainingChecks = Integer.valueOf(System.getProperty("rl_post_conditions_retry"));
		
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				System.out.println(jmxPolicyInfo);
				
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



	public static void checkPostConditionsRichiesteSimultanee(String idPolicy, int maxRequests) throws UtilsException {
		
		int remainingChecks = Integer.valueOf(System.getProperty("rl_post_conditions_retry"));
		
		while(true) {
			try {
				String jmxPolicyInfo = getPolicy(idPolicy);
				System.out.println(jmxPolicyInfo);
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
		String jmxPolicyInfo = getPolicy(idPolicy);
		System.out.println(jmxPolicyInfo);
		
		// Se non sono mai state fatte richieste che attivano la policy, ottengo questa
		// risposta, e le precondizioni sono soddisfatte
		if (jmxPolicyInfo.equals("Informazioni sulla Policy non disponibili; non sono ancora transitate richieste che soddisfano i criteri di filtro impostati")) {
			return;
		}
		
		RichiesteSimultaneePolicyInfo polInfo = new RichiesteSimultaneePolicyInfo(jmxPolicyInfo);
		assertEquals(Integer.valueOf(0), polInfo.richiesteAttive);
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
	

}
