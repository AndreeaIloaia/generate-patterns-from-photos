package com.andreea.app.dtos;

/**
 * Clasa creata pentru a ajuta la returnarea unui raspuns de catre endpoint-uri
 * params: success - true, daca returneaza raspunsul dorit
 *                  - false, altfel
 *          message - mesajul raspunsului, respectiv erorii
 */
public class ApiResponse {
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
