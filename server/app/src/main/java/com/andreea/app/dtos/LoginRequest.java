package com.andreea.app.dtos;

import javax.validation.constraints.NotBlank;

/**
 * Clasa folosita pentru a trimite detaliile despre login in body-ul request-ului
 * campuri: usernameOrEmail - String folosit pentru login, poate sa fie ori username-ul, ori email-ul utilizatorului
 *          password - String pentru parola
 * NotBlank - adnotare care verifica sa nu fie un string gol sau null
 */
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
