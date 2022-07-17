package com.app.config.security;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.app.config.security.customexpression.SecurityUtils;
import com.app.repository.es.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.app.config.security.customexpression.OAuth2AuthenticationUser;
import com.app.persistence.model.es.user.Role;
import com.app.persistence.model.es.user.User;
//import com.app.persistence.model.es.seller.UserSeller;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	// @Value("${security.security-realm}")
	// private String securityRealm;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic()
		// .realmName(securityRealm).and().csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic().and().csrf()
				.disable();

	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		converter.setAccessTokenConverter(getAuthenticationAccessTokenConverter());
		return converter;
	}

	private DefaultAccessTokenConverter getAuthenticationAccessTokenConverter() {
		return new DefaultAccessTokenConverter() {
			@SuppressWarnings("unchecked")
			@Override
			public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
				OAuth2Authentication authentication = (OAuth2Authentication) super.extractAuthentication(map);

				OAuth2AuthenticationUser authenticationUser = new OAuth2AuthenticationUser(
						authentication.getOAuth2Request(), authentication.getUserAuthentication());

				User user = new User();
				user.setEmail(map.get("user_name") != null ? map.get("user_name").toString() : null);

				/*List<Role> roles = map.get("authorities") != null ? ((List<String>) map.get("authorities")).stream()
						.map(this::buildRole).collect(Collectors.toList()) : Collections.<Role> emptyList();
				user.setRoles(roles);

				if (SecurityUtils.isAdminUser(user) || SecurityUtils.isStandardUser(user)) {
					user.setOrganizations(userRepository.findByEmail(user.getEmail()).getOrganizations());
				} else {
					List<UserSeller> organizations = map.get("organization") != null
							? ((List<String>) map.get("organization")).stream().map(this::buildOrganization)
							.collect(Collectors.toList())
							: Collections.<UserSeller> emptyList();
					user.setOrganizations(organizations);
				}*/

 				authenticationUser.setUser(user);

				return authenticationUser;
			}

			private Role buildRole(String roleName) {
				Role role = new Role();
				role.setRoleName(roleName);

				return role;
			}

			/*private UserSeller buildOrganization(String accountName) {
				UserSeller seller = new UserSeller();
				seller.setAccountName(accountName);
				return seller;
			}*/
		};
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary // Making this primary to avoid any accidental duplication with
	// another token service instance of the same name
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
