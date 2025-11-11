package com.dronedelivery.notificationservice;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import java.time.LocalDateTime;

@MongoEntity(collection = "notifications")
public class Notification extends PanacheMongoEntity {

    public String type;        // success, warning, info, error
    public String title;
    public String message;
    public LocalDateTime timestamp;

    public Notification() {}

    public Notification(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Метод для пошуку сповіщень по типу
    public static java.util.List<Notification> findByType(String type) {
        return list("type", type);
    }

    // Метод для пошуку останніх сповіщень
    public static java.util.List<Notification> findLatest(int limit) {
        return findAll().page(0, limit).list();
    }
}

