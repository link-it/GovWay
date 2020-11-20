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
}