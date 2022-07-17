package com.app.config.security;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.app.config.security.customexpression.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.app.persistence.model.es.user.User;
import com.app.repository.es.UserRepository;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName());

		/*if (!SecurityUtils.isAdminUser(user) && !SecurityUtils.isStandardUser(user)) {
			Map<String, Object> additionalInfo = new HashMap<>();
			additionalInfo.put("organization",
					user.getOrganizations().stream().map(o -> o.getAccountName()).collect(Collectors.toList()));
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		}*/

		return accessToken;
	}
}
