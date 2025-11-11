package com.dronedelivery.managementservice;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "drone")
public class Drone extends PanacheEntity {

    public String model;

    @Enumerated(EnumType.STRING)
    public DroneStatus status;

    public Integer batteryLevel; // від 0 до 100

    public Double latitude;
    public Double longitude;

    // Порожній конструктор (потрібен для JPA)
    public Drone() {}

    // Конструктор для створення нових дронів
    public Drone(String model, DroneStatus status, Integer batteryLevel, Double latitude, Double longitude) {
        this.model = model;
        this.status = status;
        this.batteryLevel = batteryLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Метод для пошуку вільного дрона
    public static Drone findFreeDrone() {
        return find("status = ?1 and batteryLevel > ?2", DroneStatus.AVAILABLE, 30)
                .firstResult();
    }

    // Метод для пошуку дронів за статусом
    public static java.util.List<Drone> findByStatus(DroneStatus status) {
        return list("status", status);
    }
}