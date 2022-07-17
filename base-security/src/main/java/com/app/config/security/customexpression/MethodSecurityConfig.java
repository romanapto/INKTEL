package com.app.config.security.customexpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.app.repository.es.UserRepository;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler(
				userRepository);
		return expressionHandler;
	}
}