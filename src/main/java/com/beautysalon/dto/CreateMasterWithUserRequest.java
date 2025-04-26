package com.beautysalon.dto;

import java.util.List;
import java.util.Map;

public class CreateMasterWithUserRequest {
    public String name;
    public String email;
    public String password;

    public List<String> serviceIds;
    public boolean active;
    public boolean onVacation;

    // Ключ — день недели ("Mon", "Tue", ...), значения — часы (["10:00", "11:00"])
    public Map<String, List<String>> schedule;
}
