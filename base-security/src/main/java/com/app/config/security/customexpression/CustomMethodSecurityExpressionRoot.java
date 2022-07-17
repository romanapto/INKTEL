package com.app.config.security.customexpression;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpClientErrorException;

import com.app.persistence.model.es.user.Role;
import com.app.persistence.model.es.user.User;
import com.app.repository.es.UserRepository;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
		implements MethodSecurityExpressionOperations {

	private UserRepository userRepository;
	

	private Object filterObject;
	private Object returnObject;
	private Object target;

	public CustomMethodSecurityExpressionRoot(Authentication authentication, 
			UserRepository userRepository) {
		super(authentication);
		this.userRepository = userRepository;
		
	}

	public boolean isHimSelf(String userId) {
		User logged = userRepository.findByEmail(SecurityUtils.getAuthenticatedEmail());
		User toEvaluate = userRepository.findById(userId)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		return toEvaluate.getId().equals(logged.getId());
	}
        
      public boolean validateHierarchyRole(Set<Role> list) {
		User loggedUser = getLoggedUser();
		List<Role> loggedRoles = loggedUser.getRoles();
		if (loggedRoles.stream().anyMatch(roleContaining(SecurityUtils.ADMIN_USER))) {
			return true;
		} else if (loggedRoles.stream().anyMatch(roleContaining(SecurityUtils.ORGANIZATION_ADMIN_USER))) {
			return list.stream().noneMatch(
					roleContaining(SecurityUtils.ADMIN_USER).or(roleContaining(SecurityUtils.STANDARD_USER)));
		} else {
			return list.size() <= loggedRoles.size()
					&& list.stream().anyMatch(r -> loggedRoles.stream().anyMatch(roleContaining(r.getRoleName())));
		}
	}

	private Predicate<Role> roleContaining(String adminUser) {
		return r -> adminUser.equals(r.getRoleName());
	}

	private User getLoggedUser() {
		return SecurityUtils.getAuthenticatedUser();
	}

	@Override
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}

	@Override
	public Object getFilterObject() {
		return filterObject;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	@Override
	public Object getReturnObject() {
		return returnObject;
	}

	void setThis(Object target) {
		this.target = target;
	}

	@Override
	public Object getThis() {
		return target;
	}
}
