package com.sundeinfo.security.utility;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:config/token.properties"})
public class TokenResource {

    @Value("${token.requestheader}")
    private String requestheader;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private String expiration;

    @Value("${token.tokenheader}")
    private String tokenheader;

    public String getRequestheader() {
        return requestheader;
    }

    public void setRequestheader(String requestheader) {
        this.requestheader = requestheader;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getTokenheader() {
        return tokenheader;
    }

    public void setTokenheader(String tokenheader) {
        this.tokenheader = tokenheader;
    }
}


