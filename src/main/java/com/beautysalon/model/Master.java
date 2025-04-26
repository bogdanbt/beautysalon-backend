package com.beautysalon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "masters")
// Модель мастера, хранится в MongoDB в коллекции "masters"
public class Master {

    @Id
    private String id; // ID мастера (в коллекции masters)

    private String name; // имя мастера

    private String userId; // связь с User (логин/пароль/роль "master")

    private List<String> serviceIds; // какие услуги выполняет

    private boolean active; // работает сейчас или нет

    private boolean onVacation; // в отпуске?

    private Map<String, List<String>> schedule;
    // Расписание: "Mon" → ["10:00", "11:00"]

    public Master() {}

    public Master(String name, String userId, List<String> serviceIds,
                  boolean active, boolean onVacation, Map<String, List<String>> schedule) {
        this.name = name;
        this.userId = userId;
        this.serviceIds = serviceIds;
        this.active = active;
        this.onVacation = onVacation;
        this.schedule = schedule;
    }

    // Getters and Setters

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public List<String> getServiceIds() { return serviceIds; }

    public void setServiceIds(List<String> serviceIds) { this.serviceIds = serviceIds; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public boolean isOnVacation() { return onVacation; }

    public void setOnVacation(boolean onVacation) { this.onVacation = onVacation; }

    public Map<String, List<String>> getSchedule() { return schedule; }

    public void setSchedule(Map<String, List<String>> schedule) { this.schedule = schedule; }
}
