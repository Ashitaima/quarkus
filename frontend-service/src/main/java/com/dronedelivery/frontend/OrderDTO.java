package com.dronedelivery.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDTO {

    public Long id;

    @JsonProperty("customerName")
    public String customerName;

    public String destination;

    public Double weight;

    public String status;

    @JsonProperty("createdAt")
    public String createdAt;

    // Default constructor for Jackson
    public OrderDTO() {
    }

    public OrderDTO(Long id, String customerName, String destination, Double weight, String status, String createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.destination = destination;
        this.weight = weight;
        this.status = status;
        this.createdAt = createdAt;
    }
}

