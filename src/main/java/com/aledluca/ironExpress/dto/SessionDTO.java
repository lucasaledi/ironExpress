package com.aledluca.ironExpress.dto;

public class SessionDTO {
    private String token;
    private String message;

    public SessionDTO() {
    }

    public SessionDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
