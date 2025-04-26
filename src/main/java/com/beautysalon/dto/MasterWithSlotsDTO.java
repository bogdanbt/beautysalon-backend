package com.beautysalon.dto;

import java.util.List;
import java.util.Map;

public class MasterWithSlotsDTO {
    private String id;
    private String name;
    private Map<String, List<String>> schedule; // key: date, value: times

    public MasterWithSlotsDTO(String id, String name, Map<String, List<String>> schedule) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<String>> getSchedule() {
        return schedule;
    }
}
