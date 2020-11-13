package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openspcoop2.core.protocolli.trasparente.testsuite.ConfigLoader;
import org.openspcoop2.core.protocolli.trasparente.testsuite.DbUtils;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;
import org.openspcoop2.utils.transport.http.HttpUtilsException;

public class RateLimitingTest extends ConfigLoader {
	
	private static DbUtils dbUtils;

	
	public static class Headers {
		public static final String RateLimitReset = "X-RateLimit-Reset";
		public static final String RateLimitRemaining = "X-RateLimit-Remaining";
		public static final String ReturnCode = "ReturnCode";
		public static final String RetryAfter = "Retry-After";
		public static final String ConcurrentRequestsLimit = "GovWay-RateLimit-ConcurrentRequest-Limit";
		public static final String ConcurrentRequestsRemaining = "GovWay-RateLimit-ConcurrentRequest-Remaining";
		public static final String GovWayTransactionErrorType = "GovWay-Transaction-ErrorType";
	}
	
	public static class HeaderValues {
		public static final String LimitExceeded = "LimitExceeded";
		public static final String ReturnCodeTooManyRequests = "HTTP/1.1 429 Too Many Requests";
		public static final String TooManyRequests = "TooManyRequests";		 
	}
		
	@BeforeClass
	public static void setupDbUtils() {
		var dbConfig = Map.of(
				"username", System.getProperty("db_username"),
				"password", System.getProperty("db_password"),
				"url", System.getProperty("db_url"),
				"driverClassName", System.getProperty("db_driverClassName")
			);
		dbUtils = new DbUtils(dbConfig);
	}
	

	private Vector<HttpResponse> makeParallelRequests(HttpRequest request, int count) throws InterruptedException {

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
	
	

	@Test
	public void testRichiestePerMinuto() throws InterruptedException, UtilsException, HttpUtilsException {
		System.out.println("Test richieste per minuto");
		final int maxRequests = 5;
		
		// Resetto la policy di RL
		String jmx_url = System.getProperty("govway_base_path") + 
				"/check?resourceName=ControlloTraffico&methodName=resetPolicyCounters&paramValue=" + dbUtils.getPolicyIdErogazione("SoggettoInternoTest", "RLRichiestePerMinuto");
		HttpUtilities.check(jmx_url, System.getProperty("jmx_username"), System.getProperty("jmx_password"));
		
		// Aspetto lo scoccare del minuto
		if (!"false".equals(System.getProperty("wait"))) {
			Calendar now = Calendar.getInstance();
			int to_wait = (63 - now.get(Calendar.SECOND)) *1000;
			System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			java.lang.Thread.sleep(to_wait);
		}
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl( System.getProperty("govway_base_path") + "/SoggettoInternoTest/RLRichiestePerMinuto/v1/resource");
						
		Vector<HttpResponse> responses = makeParallelRequests(request, maxRequests + 1);

		// Tutte le richieste devono avere lo header X-RateLimit-Reset impostato ad un numero
		// Tutte le richieste devono avere lo header X-RateLimit-Limit
		responses.forEach(r -> { 			
				assertTrue( Integer.valueOf(r.getHeader(Headers.RateLimitReset)) != null);
				// assertNotEquals(null,r.getHeader("X-RateLimit-Limit")); TODO: Debugga con andrea e vedi perchè non te lo genera
			});

		
		// Tutte le richieste tranne 1 devono restituire 200
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxRequests);

		
		// La richiesta fallita deve avere status code 429
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);
		assertEquals("0", failedResponse.getHeader(Headers.RateLimitRemaining));
		assertEquals(HeaderValues.LimitExceeded, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));

		// Lo header X-RateLimit-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxRequests-1
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader(Headers.RateLimitRemaining))).collect(Collectors.toList());
		assertTrue(IntStream.range(0, maxRequests).allMatch(v -> counters.contains(v)));
	}

	@Test
	public void testParallellV2() throws UtilsException, InterruptedException {
		// TODO: Testa anche la fruizione quando andrea ha risolto le segnalazioni nelle
		// mail
		final int maxConcurrentRequests = 10;

		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl(System.getProperty("govway_base_path") + "/SoggettoInternoTest/ApiTrasparenteTest/v1/resource");
		Vector<HttpResponse> responses = makeParallelRequests(request, maxConcurrentRequests + 1);

		// Tutte le richieste tranne 1 devono restituire 200
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxConcurrentRequests);

		// Tutte le richieste devono avere lo header GovWay-RateLimit-ConcurrentRequest-Limit=10
		responses.forEach(r -> assertTrue(
				r.getHeader(Headers.ConcurrentRequestsLimit).equals(String.valueOf(maxConcurrentRequests))));

		// La richiesta fallita deve avere status code 429
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);

		assertEquals("0", failedResponse.getHeader(Headers.ConcurrentRequestsRemaining));
		assertEquals(HeaderValues.TooManyRequests, failedResponse.getHeader(Headers.GovWayTransactionErrorType));
		assertEquals(HeaderValues.ReturnCodeTooManyRequests, failedResponse.getHeader(Headers.ReturnCode));
		assertNotEquals(null, failedResponse.getHeader(Headers.RetryAfter));

		// Lo header GovWay-RateLimit-ConcurrentRequest-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxConcurrentRequests-1
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader(Headers.ConcurrentRequestsRemaining)))
				.collect(Collectors.toList());

		assertTrue(IntStream.range(0, maxConcurrentRequests).allMatch(v -> counters.contains(v)));

		System.out.println("Threads eseguiti!");
	}

	
}
