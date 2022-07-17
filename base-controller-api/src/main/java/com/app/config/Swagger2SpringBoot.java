package com.app.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.util.ApiConstants;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

@Configuration
public class Swagger2SpringBoot {

	@Value("${security.jwt.client-id}")
	private String clientId;
	@Value("${security.jwt.client-secret}")
	private String clientSecret;
	@Value("${security.jwt.token-url}")
	private String tokenUrl;
	@Value("${app.version}")
	private String appVersion;

	@Bean
	public Docket api() {

		List<ResponseMessage> list = new java.util.ArrayList<>();

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant(ApiConstants.API + "/**")).build()
				.securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
				.useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
				.globalResponseMessage(RequestMethod.POST, list);

	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));

		List<GrantType> grantTypes = new ArrayList<>();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(tokenUrl);

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**")).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(clientSecret).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User Client").description("INKTEL")
				.contact(new Contact("Julian Roman", "https://www.julianRomanco.com", null)).version(appVersion)
				.build();
	}

}
