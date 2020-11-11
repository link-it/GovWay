package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.openspcoop2.utils.UtilsException;
import org.openspcoop2.utils.transport.http.HttpRequest;
import org.openspcoop2.utils.transport.http.HttpRequestMethod;
import org.openspcoop2.utils.transport.http.HttpResponse;
import org.openspcoop2.utils.transport.http.HttpUtilities;

public class RateLimitingTest {

	private Vector<HttpResponse> makeParallelRequests(HttpRequest request, int count) throws InterruptedException {

		final Vector<HttpResponse> responses = new Vector<>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(count);

		for (int i = 0; i < count; i++) {
			executor.execute(() -> {
				try {
					System.out.println("Effettuo la richiesta..");
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
	public void testRichiestePerMinuto() throws InterruptedException {
		System.out.println("Test richieste per minuto");
		final int maxRequests = 5;
		
		if (!"false".equals(System.getProperty("wait"))) {
			Calendar now = Calendar.getInstance();
			int to_wait = (61 - now.get(Calendar.SECOND)) *1000;
			System.out.println("Aspetto " + to_wait/1000 + " secondi per lo scoccare del minuto..");
			java.lang.Thread.sleep(to_wait);
		}
		
		
		HttpRequest request = new HttpRequest();
		request.setContentType("application/json");
		request.setMethod(HttpRequestMethod.GET);
		request.setUrl("http://localhost:8080/govway/SoggettoInternoTest/RLRichiestePerMinuto/v1/resource");

		Vector<HttpResponse> responses = makeParallelRequests(request, maxRequests + 1);

		// Tutte le richieste devono avere lo header X-RateLimit-Reset impostato ad un numero
		// Tutte le richieste devono avere lo header X-RateLimit-Limit
		responses.forEach(r -> { 			
				assertTrue( Integer.valueOf(r.getHeader("X-RateLimit-Reset")) != null);
				assertNotEquals(null,r.getHeader("X-RateLimit-Limit"));
			});

		// Tutte le richieste tranne 1 devono restituire 200
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxRequests);

		// La richiesta fallita deve avere status code 429
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);
		assertEquals("0", failedResponse.getHeader("X-RateLimit-Reset"));
		assertEquals("LimitExcedeed", failedResponse.getHeader("GovWay-Transaction-ErrorType"));
		assertEquals("HTTP/1.1 429 Too Many Requests", failedResponse.getHeader("ReturnCode"));
		assertNotEquals(null, failedResponse.getHeader("Retry-After"));

		// Lo header X-RateLimit-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxRequests-1
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader("X-RateLimit-Remaining"))).collect(Collectors.toList());
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
		request.setUrl("http://localhost:8080/govway/SoggettoInternoTest/ApiTrasparenteTest/v1/resource");
		Vector<HttpResponse> responses = makeParallelRequests(request, maxConcurrentRequests + 1);

		// Tutte le richieste tranne 1 devono restituire 200
		assertTrue(responses.stream().filter(r -> r.getResultHTTPOperation() == 200).count() == maxConcurrentRequests);

		// Tutte le richieste devono avere lo header
		// GovWay-RateLimit-ConcurrentRequest-Limit=10
		responses.forEach(r -> assertTrue(
				r.getHeader("GovWay-RateLimit-ConcurrentRequest-Limit").equals(String.valueOf(maxConcurrentRequests))));

		// La richiesta fallita deve avere status code 429
		HttpResponse failedResponse = responses.stream().filter(r -> r.getResultHTTPOperation() == 429).findAny()
				.orElse(null);
		assertTrue(failedResponse != null);

		assertEquals("0", failedResponse.getHeader("GovWay-RateLimit-ConcurrentRequest-Remaining"));
		assertEquals("TooManyRequests", failedResponse.getHeader("GovWay-Transaction-ErrorType"));
		assertEquals("HTTP/1.1 429 Too Many Requests", failedResponse.getHeader("ReturnCode"));
		assertNotEquals(null, failedResponse.getHeader("Retry-After"));

		// Lo header GovWay-RateLimit-ConcurrentRequest-Remaining deve assumere tutti i
		// i valori possibili da 0 a maxConcurrentRequests-1
		List<Integer> counters = responses.stream()
				.map(resp -> Integer.parseInt(resp.getHeader("GovWay-RateLimit-ConcurrentRequest-Remaining")))
				.collect(Collectors.toList());

		assertTrue(IntStream.range(0, maxConcurrentRequests).allMatch(v -> counters.contains(v)));

		System.out.println("Threads eseguiti!");
	}

	/*
	 * public void testParallel() throws Exception {
	 * 
	 * Config config = new Config();
	 * 
	 * //Set verbosity level - 4 and above prints response // content, 3 and above
	 * prints information on headers, // 2 and above prints response codes (404,
	 * 200, etc.), //1 and above prints warnings and info. config.setVerbosity(4);
	 * 
	 * // Enable the HTTP KeepAlive feature, i.e., perform multiple requests within
	 * one HTTP session. // Default is no KeepAlive //
	 * config.setKeepAlive(Boolean.parseBoolean(Client.getProperty(reader,
	 * "openspcoop2.keepAlive", true)));
	 * 
	 * // Concurrency while performing the benchmarking session. // The default is
	 * to just use a single thread/client. // TODO: Usa una property
	 * config.setThreads(11);
	 * 
	 * // Number of requests to perform for the benchmarking session. // The default
	 * is to just perform a single request which usually leads to non-representative
	 * benchmarking results. config.setRequests(1);
	 * 
	 * // config.setDurationSec(Integer.parseInt(timeSec)); TODO: e qui?
	 * 
	 * config.setContentType("application/json");
	 * 
	 * config.setMethod("GET");
	 * 
	 * config.setUrl(new URL(
	 * "http://localhost:8080/govway/SoggettoInternoTest/ApiTrasparenteTest/v1/resource?sleep=1000"
	 * ));
	 * 
	 * List<Integer> acceptedReturnCodes = new ArrayList<Integer>();
	 * acceptedReturnCodes.add(200);
	 * 
	 * config.setAcceptedReturnCode(acceptedReturnCodes);
	 * 
	 * HttpBenchmark httpBenchmark = new HttpBenchmark(config);
	 * 
	 * Results results = httpBenchmark.doExecute();
	 * 
	 * System.out.println(results.toString());
	 * 
	 * assertTrue(results.getFailureCount() == 1);
	 * assertTrue(results.getSuccessCount() == 10); }
	 */

}