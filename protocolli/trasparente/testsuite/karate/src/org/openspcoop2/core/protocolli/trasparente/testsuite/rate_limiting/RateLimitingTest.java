package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.benchmark.Config;
import org.apache.http.benchmark.HttpBenchmark;
import org.apache.http.benchmark.Results;
import org.junit.Test;

public class RateLimitingTest {
    
    @Test
    public void testParallel() throws Exception {
       
    	Config config = new Config();
    	
    	//Set verbosity level - 4 and above prints response
        // content, 3 and above prints information on headers,
        // 2 and above prints response codes (404, 200, etc.),
        //1 and above prints warnings and info.
        config.setVerbosity(4);
        
        // Enable the HTTP KeepAlive feature, i.e., perform multiple requests within one HTTP session. 
        // Default is no KeepAlive
        // config.setKeepAlive(Boolean.parseBoolean(Client.getProperty(reader, "openspcoop2.keepAlive", true)));
        
        // Concurrency while performing the benchmarking session. 
        // The default is to just use a single thread/client.
        // TODO: Usa una property
        config.setThreads(11);

        //  Number of requests to perform for the benchmarking session. 
        // The default is to just perform a single request which usually leads to non-representative benchmarking results.
        config.setRequests(1);
        
        // config.setDurationSec(Integer.parseInt(timeSec)); TODO: e qui?
        
    	config.setContentType("application/json");
    	
    	config.setMethod("GET");
    	
    	config.setUrl(new URL("http://localhost:8080/govway/SoggettoInternoTest/ApiTrasparenteTest/v1/resource?sleep=1000"));
    	
    	List<Integer> acceptedReturnCodes = new ArrayList<Integer>();
    	acceptedReturnCodes.add(200);
    	
    	config.setAcceptedReturnCode(acceptedReturnCodes);
    	
    	HttpBenchmark httpBenchmark = new HttpBenchmark(config);
    	
    	Results results = httpBenchmark.doExecute();
    	
    	System.out.println(results.toString());
    	
        assertTrue(results.getFailureCount() == 1);
        assertTrue(results.getSuccessCount() == 10);
    }
    
}