package com.dronedelivery.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationDTO {

    public String id;

    public String type;

    public String title;

    public String message;

    public String timestamp;

    public NotificationDTO() {
    }

    public NotificationDTO(String id, String type, String title, String message, String timestamp) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
    }
}

