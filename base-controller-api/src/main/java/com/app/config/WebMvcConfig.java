package com.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.util.ApiConstants;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${rate-limit.capacity}")
	private long rateLimitCapacity;
	@Value("${rate-limit.tokens}")
	private long rateLimitTokens;

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PerClientRateLimitInterceptor(rateLimitCapacity, rateLimitTokens))
				.addPathPatterns(ApiConstants.API_V1 + "/**");
	}
}