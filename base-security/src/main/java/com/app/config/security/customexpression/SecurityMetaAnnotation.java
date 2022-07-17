package com.app.config.security.customexpression;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author jfernandez
 *
 * ROLES:
 * ADMIN_USER, STANDARD_USER, ORGANIZATION_ADMIN_USER, ORGANIZATION_STANDARD_USER, CATALOG_USER, INVENTORY_USER,
 * ORDERS_USER, REPORTS_USER
 */
public class SecurityMetaAnnotation {
	
	@Target({ElementType.METHOD, ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('STANDARD_USER') or hasAuthority('ADMIN_USER')")
	public @interface SuperAdminAuth {
		
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("hasAuthority('STANDARD_USER') or hasAuthority('ADMIN_USER') or hasAuthority('ORGANIZATION_ADMIN_USER') or hasAuthority('ORGANIZATION_STANDARD_USER')")
	public @interface AdminAuth {
		
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("isHimSelf(#userId)")
	public @interface HimselfAuth {
		
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("isHimSelf(#userId) or hasAuthority('STANDARD_USER') or hasAuthority('ADMIN_USER') or hasAuthority('ORGANIZATION_ADMIN_USER') or hasAuthority('ORGANIZATION_STANDARD_USER')")
	public @interface HimselfAndAdminsAuth {
		
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("validateHierarchyRole(#user.roles) and containedSellers(#user.organizations)")
	public @interface ValidateOrganizationsAndRoles {
		
	}
	
}
