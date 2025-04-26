package com.beautysalon.dto;
// Класс лежит в папке dto (Data Transfer Object)

public class RegisterRequest {
    // Этот класс используется для передачи данных при регистрации
    // Он не связан с базой — просто обрабатывает тело запроса от клиента

    private String name;       // Имя пользователя (например, "Anna")
    private String email;      // Почта (например, "anna@mail.com")
    private String password;   // Пароль (в виде строки)

    public RegisterRequest() {}
    // Пустой конструктор нужен, чтобы Spring мог автоматически создать объект

    public RegisterRequest(String name, String email, String password) {
        // Конструктор, чтобы вручную создать объект с полями
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Геттеры и сеттеры — чтобы можно было получить и установить значения

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
