package com.andreea.app.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Clasa folosita pentru a trimite detaliile despre login in body-ul request-ului
 * campuri: username - String folosit pentru register pentru username-ul utilizatorului
 * email - String folosit pentru register pentru email-ul utilizatorului
 * password - String pentru parola
 * isAdmin - boolean care arata daca utilizatorul are drepturi de administrator sau nu
 *
 * NotBlank - adnotare care verifica sa nu fie un string gol sau null
 * Size - adnotare care impune limita de lungime pe stringuri
 * Email - adnotare pentru validarea emailului
 */
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    private boolean isAdmin;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

