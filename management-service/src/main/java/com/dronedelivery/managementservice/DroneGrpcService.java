package com.dronedelivery.managementservice; // Або ваш пакет, де лежить DroneRepository

import com.dronedelivery.management.grpc.*; // Імпорт згенерованих класів
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni; // Uni - це реактивний тип Quarkus
import jakarta.inject.Inject; // Для ін'єкції репозиторію

import java.util.Optional;

@GrpcService // Позначає цей клас як gRPC сервіс
public class DroneGrpcService implements DroneService { // Імплементує згенерований інтерфейс

    @Inject // Впроваджуємо наш фейковий репозиторій
    DroneRepository droneRepository;

    @Override
    public Uni<DroneResponse> findAvailableDrone(FindDroneRequest request) {
        System.out.println("gRPC: Отримано запит на пошук вільного дрона...");

        // Шукаємо дрона в репозиторії
        Optional<Drone> freeDroneOpt = droneRepository.findFreeDrone();

        if (freeDroneOpt.isPresent()) {
            Drone drone = freeDroneOpt.get();

            // Змінюємо його статус (імітація "бронювання")
            droneRepository.updateStatus(drone.id, DroneStatus.DELIVERING);

            System.out.println("gRPC: Знайдено дрон: " + drone.id);

            // Створюємо відповідь
            DroneResponse response = DroneResponse.newBuilder()
                    .setId(drone.id)
                    .setBatteryLevel(drone.batteryLevel)
                    .build();

            // Повертаємо відповідь у реактивній обгортці Uni
            return Uni.createFrom().item(response);
        } else {
            // Якщо дрон не знайдено, ми повинні повернути помилку
            System.out.println("gRPC: Вільних дронів не знайдено.");

            // gRPC повідомляє про помилки через status
            return Uni.createFrom().failure(new io.grpc.StatusRuntimeException(
                    io.grpc.Status.NOT_FOUND.withDescription("Немає вільних дронів")
            ));
        }
    }
}