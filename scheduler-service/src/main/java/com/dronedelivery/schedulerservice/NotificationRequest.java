package com.dronedelivery.schedulerservice; // Ваш пакет

public class NotificationRequest {
    public String customerId;
    public String message;

    // Конструктор для зручності
    public NotificationRequest(String customerId, String message) {
        this.customerId = customerId;
        this.message = message;
    }
}