package com.dronedelivery.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DroneDTO {

    public Long id;

    public String model;

    public String status;

    @JsonProperty("batteryLevel")
    public Integer batteryLevel;

    public Double latitude;

    public Double longitude;

    public DroneDTO() {
    }

    public DroneDTO(Long id, String model, String status, Integer batteryLevel, Double latitude, Double longitude) {
        this.id = id;
        this.model = model;
        this.status = status;
        this.batteryLevel = batteryLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

