package com.app.config.security.customexpression;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import com.app.persistence.model.es.user.User;

public class OAuth2AuthenticationUser extends OAuth2Authentication {
	private static final long serialVersionUID = 6985733453281053612L;

	private User user;

	public OAuth2AuthenticationUser(OAuth2Request storedRequest, Authentication userAuthentication) {
		super(storedRequest, userAuthentication);
	}

	public User getUser() {
		return user;
    }

	public void setUser(User user) {
		this.user = user;
    }
}
