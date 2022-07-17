package com.app.config.security.customexpression;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

import com.app.repository.es.UserRepository;

public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

	private UserRepository userRepository;
	

	private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

	public CustomMethodSecurityExpressionHandler(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}

	@Override
	protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
			MethodInvocation invocation) {
		CustomMethodSecurityExpressionRoot root = new CustomMethodSecurityExpressionRoot(authentication,
				 userRepository);
		root.setPermissionEvaluator(getPermissionEvaluator());
		root.setTrustResolver(this.trustResolver);
		root.setRoleHierarchy(getRoleHierarchy());
		return root;
	}
}
