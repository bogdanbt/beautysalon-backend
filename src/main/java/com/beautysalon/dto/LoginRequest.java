package com.beautysalon.dto;

public class LoginRequest {
    // Этот класс используется, когда пользователь логинится
    // Он получает только email и пароль (имя не нужно)

    private String email;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
