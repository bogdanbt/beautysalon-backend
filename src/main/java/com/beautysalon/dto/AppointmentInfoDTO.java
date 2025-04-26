package com.beautysalon.dto;

public class AppointmentInfoDTO {
    private String id;
    private String date;
    private String time;
    private String serviceName;
    private String masterName;
    private String clientEmail;

    public AppointmentInfoDTO(String id, String serviceName, String masterName, String date, String time, String clientEmail) {
        this.id = id;
        this.serviceName = serviceName;
        this.masterName = masterName;
        this.date = date;
        this.time = time;
        this.clientEmail = clientEmail;
    }

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getClientEmail() {
        return clientEmail;
    }
}
