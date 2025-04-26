package com.beautysalon.dto;

public class PublicMasterDTO {
    private String id;
    private String name;

    public PublicMasterDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public PublicMasterDTO(com.beautysalon.model.Master m) {
        this.id = m.getId();
        this.name = m.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
