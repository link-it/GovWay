package org.openspcoop2.core.protocolli.trasparente.testsuite.rate_limiting;

public class Headers {
	public static final String RateLimitLimit = "X-RateLimit-Limit";
	public static final String RateLimitReset = "X-RateLimit-Reset";
	public static final String RateLimitRemaining = "X-RateLimit-Remaining";
	public static final String ReturnCode = "ReturnCode";
	public static final String RetryAfter = "Retry-After";
	public static final String ConcurrentRequestsLimit = "GovWay-RateLimit-ConcurrentRequest-Limit";
	public static final String ConcurrentRequestsRemaining = "GovWay-RateLimit-ConcurrentRequest-Remaining";
	public static final String GovWayTransactionErrorType = "GovWay-Transaction-ErrorType";
	
	public static final String RateLimitTimeResponseQuotaReset = "GovWay-RateLimit-TimeResponseQuota-Reset";
	public static final String RateLimitTimeResponseQuotaLimit = "GovWay-RateLimit-TimeResponseQuota-Limit";
	public static final String RateLimitTimeResponseQuotaRemaining =  "GovWay-RateLimit-TimeResponseQuota-Remaining";
		
	public static final String BandWidthQuotaReset = "GovWay-RateLimit-BandwithQuota-Reset";
	public static final String BandWidthQuotaLimit = "GovWay-RateLimit-BandwithQuota-Limit";
	public static final String BandWidthQuotaRemaining = "GovWay-RateLimit-BandwithQuota-Remaining";
	
	public static final String RequestSuccesfulReset = "GovWay-RateLimit-RequestSuccessful-Reset";
	public static final String RequestSuccesfulLimit = "GovWay-RateLimit-RequestSuccessful-Limit";
	public static final String RequestSuccesfulRemaining = "GovWay-RateLimit-RequestSuccessful-Remaining";
	
}