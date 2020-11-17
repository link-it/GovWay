package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.openspcoop2.core.protocolli.trasparente.testsuite.DbUtils;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.TransportUtils;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;

public class Utils {
	
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
	
	/**
	 * Resetta i counters delle policy di rate limiting di tutti i gruppi della erogazione
	 * 
	 */
	static void resetAllCountersErogazione(DbUtils dbUtils, String erogatore, String erogazione) throws Exception {
		List<String> idPolicies = dbUtils.getAllPoliciesIdErogazione(erogatore, erogazione);
		
		idPolicies.forEach( idPolicy -> {
			Map<String,String> queryParams = Map.of(
					"resourceName", "ControlloTraffico",
					"methodName", "resetPolicyCounters",
					"paramValue", idPolicy
				);
			String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
			System.out.println("Resetto la policy di rate limiting sulla url: " + jmxUrl );
			try {
				HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}
	
	
	/**
	 * Resetta i counters delle policy di rate limiting di tutti i gruppi della erogazione
	 * 
	 */
	static void resetAllCountersFruizione(DbUtils dbUtils, String fruitore, String erogatore, String fruizione) throws Exception {
		List<String> idPolicies = dbUtils.getAllPoliciesIdFruizione(fruitore, erogatore, fruizione);
		
		idPolicies.forEach( idPolicy -> {
			Map<String,String> queryParams = Map.of(
					"resourceName", "ControlloTraffico",
					"methodName", "resetPolicyCounters",
					"paramValue", idPolicy
				);
			String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
			System.out.println("Resetto la policy di rate limiting sulla url: " + jmxUrl );
			try {
				HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});
	}


	
	/**
	 *  Resetta il counter delle policy di rate limiting per una erogazione con un solo gruppo
	 *
	 */
	static void resetCountersErogazione(DbUtils dbUtils, String erogatore, String erogazione) throws UtilsException, HttpUtilsException {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "resetPolicyCounters",
				"paramValue", dbUtils.getPolicyIdErogazione(erogatore, erogazione)
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		System.out.println("Resetto la policy di rate limiting sulla url: " + jmxUrl );
		HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
	}

	/**
	 *  Resetta il counter delle policy di rate limiting per una fruizione con un solo gruppo
	 *
	 */
	static void resetCountersFruizione(DbUtils dbUtils, String fruitore, String erogatore, String erogazione) throws UtilsException, HttpUtilsException {
		Map<String,String> queryParams = Map.of(
				"resourceName", "ControlloTraffico",
				"methodName", "resetPolicyCounters",
				"paramValue", dbUtils.getPolicyIdFruizione(fruitore, erogatore, erogazione)
			);
		String jmxUrl = TransportUtils.buildLocationWithURLBasedParameter(queryParams, System.getProperty("govway_base_path") + "/check");
		System.out.println("Resetto la policy di rate limiting sulla url: " + jmxUrl );
		HttpUtilities.check(jmxUrl, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
	}

	static void waitForNewMinute() throws InterruptedException {
		if (!"false".equals(System.getProperty("wait"))) {
			Calendar now = Calendar.getInstance();
			int to_wait = (63 - now.get(Calendar.SECOND)) *1000;
			System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			java.lang.Thread.sleep(to_wait);
		}
	}
	

}
