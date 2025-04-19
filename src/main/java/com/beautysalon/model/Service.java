package com.beautysalon.model;
// package — это папка, в которой лежит файл. Помогает организовать проект по папкам

import org.springframework.data.annotation.Id;
// импорт аннотации @Id — помечает поле, как главный ID в MongoDB

import org.springframework.data.mongodb.core.mapping.Document;
// импорт аннотации @Document — говорит, что этот класс будет храниться в MongoDB как коллекция

@Document(collection = "services")
// Эта аннотация говорит Spring: "сохраняй эти объекты в коллекцию services в MongoDB"

public class Service {
    // Это сам класс. Описывает одну услугу (например, "Маникюр")

    @Id
    private String id;
    // Это уникальный идентификатор услуги. MongoDB сам его создаёт

    private String name;
    // Название услуги (например, "Стрижка")

    private String description;
    // Описание услуги (например, "Классическая женская стрижка")

    private int duration;
    // Продолжительность услуги в минутах (например, 45)

    private double price;
    // Цена услуги (например, 40.0)

    private String category;
    // Категория (например, "Волосы", "Ногти", "Косметология")

    public Service() {}
    // Пустой конструктор. Нужен Spring'у, чтобы создавать объекты автоматически

    public Service(String name, String description, int duration, double price, String category) {
        // Конструктор, который сразу задаёт все поля
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.category = category;
    }

    // --- Геттеры и сеттеры ---
    // Геттер — метод, чтобы получить значение поля
    // Сеттер — метод, чтобы изменить значение поля

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
