package com.beautysalon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    private String userId;     // Кто записался
    private String serviceId;  // На какую услугу
    private String masterId;   // К какому мастеру

    private LocalDate date;    // Дата приёма
    private LocalTime time;    // Время приёма

    public Appointment() {}

    public Appointment(String userId, String serviceId, String masterId, LocalDate date, LocalTime time) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.masterId = masterId;
        this.date = date;
        this.time = time;
    }

    // --- Геттеры и Сеттеры ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMasterId() {
        return masterId;
    }

    public void setMasterId(String masterId) {
        this.masterId = masterId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
