package com.andreea.app.auth;

import com.andreea.app.models.Role;

/**
 * Clasa folosita pentru raspunsul de succes al login-ului, continand
 * accessToken - token-ul propriu-zis,
 * tokenType - tipul acestuia,
 * role - rolul utilizatorului
 */
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
//    private Role role;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
//        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}
