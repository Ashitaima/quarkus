package com.dronedelivery.managementservice; // <--- ВАШ ПАКЕТ МАЄ БУТИ ТУТ

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@ApplicationScoped
public class DroneRepository {

    private List<Drone> drones = new ArrayList<>();

    public DroneRepository() {
        // Додамо парк фейкових дронів
        drones.add(new Drone("drone-alpha", DroneStatus.FREE, 100));
        drones.add(new Drone("drone-beta", DroneStatus.FREE, 85));
        drones.add(new Drone("drone-gamma", DroneStatus.CHARGING, 20));
    }

    public List<Drone> findAll() {
        return drones;
    }

    // Метод для пошуку вільного дрона
    public Optional<Drone> findFreeDrone() {
        return drones.stream()
                .filter(d -> d.status == DroneStatus.FREE && d.batteryLevel > 30)
                .findFirst();
    }

    // Метод для оновлення статусу дрона
    public Optional<Drone> updateStatus(String id, DroneStatus newStatus) {
        Optional<Drone> droneOpt = drones.stream().filter(d -> d.id.equals(id)).findFirst();
        droneOpt.ifPresent(drone -> drone.status = newStatus);
        return droneOpt;
    }
}