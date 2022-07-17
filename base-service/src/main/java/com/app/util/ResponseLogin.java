package com.app.util;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ResponseLogin {

    @JsonAlias("access_token")
    private String accessToken;

    public ResponseLogin() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
