package com.beautysalon.dto;

public class JwtResponse {
    // Этот класс используется, чтобы вернуть токен клиенту после логина
    // Только одно поле — сам токен

    private String token;

    public JwtResponse() {}

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
