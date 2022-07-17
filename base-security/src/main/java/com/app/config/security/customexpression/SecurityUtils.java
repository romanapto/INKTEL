package com.app.config.security.customexpression;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.app.persistence.model.es.user.Role;
import com.app.persistence.model.es.user.User;
//import com.app.persistence.model.es.seller.UserSeller;

public interface SecurityUtils {

	String ADMIN_USER = "ADMIN_USER";
	String STANDARD_USER = "STANDARD_USER";
	String ORGANIZATION_ADMIN_USER = "ORGANIZATION_ADMIN_USER";
	String ORGANIZATION_STANDARD_USER = "ORGANIZATION_STANDARD_USER";

	static boolean isAdminUser(User user) {
		return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(ADMIN_USER));
	}

	static boolean isStandardUser(User user) {
		return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(STANDARD_USER));
	}

	static boolean isAdminOrStandardUser(User user) {
		return isAdminUser(user) || isStandardUser(user);
	}

	static boolean isOrganizationAdminUser(User user) {
		return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(ORGANIZATION_ADMIN_USER));
	}

	static boolean isOrganizationStandardUser(User user) {
		return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(ORGANIZATION_STANDARD_USER));
	}

	static boolean isOrganizationAdminOrStandardUser(User user) {
		return isOrganizationAdminUser(user) || isOrganizationStandardUser(user);
	}

	static String getAuthenticatedEmail() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	static User getAuthenticatedUser() {
		OAuth2AuthenticationUser authentication = (OAuth2AuthenticationUser) SecurityContextHolder.getContext()
				.getAuthentication();
		return (authentication != null && authentication.getUser() != null ? authentication.getUser() : new User());
	}

	/*static List<String> getAuthenticatedOrganizationAccounts() {
		return getAuthenticatedUser().getOrganizations().stream().map(o -> o.getAccountName())
				.collect(Collectors.toList());
	}*/

	/*static List<String> getAuthenticatedOrganizationAccounts(String filterAccountName) {
		List<String> accountNames = getAuthenticatedOrganizationAccounts();
		if (StringUtils.isNotEmpty(filterAccountName) && accountNames.contains(filterAccountName)) {
			accountNames.clear();
			accountNames.add(filterAccountName);
		}
		return accountNames;
	}*/

	/*static List<String> getAuthenticatedOrganizationAccounts(List<String> filterAccountsName) {
		List<String> accountNames = getAuthenticatedOrganizationAccounts();
		if (CollectionUtils.isEmpty(filterAccountsName)) {
			return accountNames;
		} else {
			return accountNames.stream().distinct().filter(filterAccountsName::contains).collect(Collectors.toList());
		}
	}*/

	/*static String getAuthenticatedOrganizationAccount() {
		return getAuthenticatedOrganizationAccounts().get(0);
	}*/

//	static boolean isContainedOrganizations(Set<Organization> toEvaluate, Set<Organization> scope) {
//		return toEvaluate != null && toEvaluate.stream()
//				.allMatch(org -> scope.stream()
//						.anyMatch(specOrg -> specOrg.getAccountName().equals(org.getAccountName())));
//	}

	/*static boolean isContainedSellers(List<UserSeller> toEvaluate, List<UserSeller> scope) {
		return toEvaluate != null && toEvaluate.stream().allMatch(
				org -> scope.stream().anyMatch(specOrg -> specOrg.getAccountName().equals(org.getAccountName())));
	}*/

	static boolean isContainedOrganizations(List<String> toEvaluate, List<String> scope) {
		return toEvaluate != null && toEvaluate.stream().allMatch(
				orgAccountName -> scope.stream().anyMatch(specAccountName -> specAccountName.equals(orgAccountName)));
	}

	static void shouldNotContainsAdminRole(User user) {
		boolean containsAdminRole = user.getRoles().stream().map(Role::getRoleName).anyMatch(ADMIN_USER::equals);
		if (containsAdminRole) {
			throw new AccessDeniedException("Add ADMIN_USER Denied");
		}
	}

	/*static boolean isOrganizationMember(String accountName) {
		return getAuthenticatedUser().getOrganizations().stream().anyMatch(o -> o.getAccountName().equals(accountName));
	}*/

}
