package com.dronedelivery.managementservice; // <--- ВАШ ПАКЕТ МАЄ БУТИ ТУТ

public class Drone {
    public String id;
    public DroneStatus status;
    public int batteryLevel; // від 0 до 100

    // Конструктор, який ми використовуємо в репозиторії
    public Drone(String id, DroneStatus status, int batteryLevel) {
        this.id = id;
        this.status = status;
        this.batteryLevel = batteryLevel;
    }
}