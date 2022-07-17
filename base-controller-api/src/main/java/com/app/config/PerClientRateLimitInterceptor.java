package com.app.config;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.app.config.security.customexpression.SecurityUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;

public class PerClientRateLimitInterceptor implements HandlerInterceptor {

	private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
	private long capacity;
	private long tokens;

	public PerClientRateLimitInterceptor(long capacity, long tokens) {
		this.capacity = capacity;
		this.tokens = tokens;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String email = SecurityUtils.getAuthenticatedEmail();
		if (StringUtils.isEmpty(email)) {
			return true;
		}

		Bucket requestBucket = this.buckets.computeIfAbsent(email, key -> standardBucket());

		ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
		if (probe.isConsumed()) {
			response.addHeader("X-Rate-Limit-Remaining", Long.toString(probe.getRemainingTokens()));
			return true;
		}

		response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
		response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
				Long.toString(TimeUnit.NANOSECONDS.toMillis(probe.getNanosToWaitForRefill())));

		return false;
	}

	private Bucket standardBucket() {
		return Bucket4j.builder()
				.addLimit(Bandwidth.classic(capacity, Refill.intervally(tokens, Duration.ofMinutes(1)))).build();
	}

}
