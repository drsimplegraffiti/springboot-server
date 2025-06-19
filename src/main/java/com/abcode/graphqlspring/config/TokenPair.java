package com.abcode.graphqlspring.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenPair extends com.abcode.graphqlspring.auth.TokenPair {
    private String accessToken;
    private String refreshToken;


    public TokenPair() {
    }

    public TokenPair(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
