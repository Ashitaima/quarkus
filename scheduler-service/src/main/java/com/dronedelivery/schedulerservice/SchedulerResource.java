package com.dronedelivery.schedulerservice; // Ваш пакет

// Імпорти gRPC (вже були)
import com.dronedelivery.management.grpc.DroneService;
import com.dronedelivery.management.grpc.FindDroneRequest;
import io.quarkus.grpc.GrpcClient;

// Нові імпорти REST-клієнтів
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Імпорти для обробки логіки (вже були)
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject; // Використовуйте цей Inject
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/schedule")
public class SchedulerResource {

    // 1. Ін'єкція gRPC клієнта (вже було)
    @GrpcClient("drone-service")
    DroneService droneService;

    // 2. НОВЕ: Ін'єкція REST-клієнта для OrderService
    @Inject
    @RestClient // Позначаємо, що це REST-клієнт
            OrderServiceClient orderServiceClient;

    // 3. НОВЕ: Ін'єкція REST-клієнта для NotificationService
    @Inject
    @RestClient
    NotificationServiceClient notificationServiceClient;


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> scheduleDelivery(@QueryParam("orderId") String orderId) {

        System.out.println("\nScheduler: Отримано запит на планування замовлення " + orderId);
        System.out.println("Scheduler: 1. Звертаюсь до management-service (gRPC)...");

        // Створюємо gRPC запит
        FindDroneRequest request = FindDroneRequest.newBuilder().build();

        // 4. ОНОВЛЕНА ЛОГІКА: Ми об'єднуємо кілька асинхронних викликів
        return droneService.findAvailableDrone(request)
                .onItem().transformToUni(droneResponse -> {
                    // Цей код виконається, якщо gRPC виклик УСПІШНИЙ (дрон знайдено)
                    System.out.println("Scheduler: 2. Дрон знайдено: " + droneResponse.getId());

                    // 5. НОВИЙ КРОК: Створюємо запити для REST-викликів

                    // Створюємо запит на оновлення статусу замовлення
                    Uni<Void> updateOrderUni = orderServiceClient.updateOrderStatus(orderId, OrderStatus.SCHEDULED);

                    // Створюємо запит на сповіщення
                    String message = "Ваше замовлення " + orderId + " заплановано. Дрон " + droneResponse.getId() + " вже в дорозі.";
                    NotificationRequest notification = new NotificationRequest("customer-id-placeholder", message); // (поки що фейковий customerId)
                    Uni<Void> notifyUni = notificationServiceClient.sendNotification(notification);

                    System.out.println("Scheduler: 3. Звертаюсь до order-service та notification-service (REST)...");

                    // 6. НОВИЙ КРОК: Об'єднуємо обидва REST-виклики
                    // Uni.combine().all().unis() ... .discardItems()
                    // Це дозволяє виконати обидва виклики паралельно
                    // і продовжити, лише коли ОБИДВА завершаться успішно
                    return Uni.combine().all().unis(updateOrderUni, notifyUni).discardItems()
                            .onItem().transform(v -> {
                                // Цей код виконається, коли ОБИДВА REST-виклики успішні
                                String result = "Успіх! Замовлення " + orderId + " призначено дрону: " + droneResponse.getId() + " (статус оновлено, сповіщення надіслано).";
                                System.out.println("Scheduler: 4. " + result);
                                return result;
                            })
                            .onFailure().recoverWithItem(restFailure -> {
                                // Якщо один з REST-викликів впаде
                                String error = "Помилка REST: " + restFailure.getMessage();
                                System.err.println("Scheduler: " + error);
                                return error;
                            });
                })
                .onFailure().recoverWithItem(grpcFailure -> {
                    // Цей код виконається, якщо gRPC виклик повернув ПОМИЛКУ (дрон НЕ знайдено)
                    String error = "Не вдалося запланувати (gRPC): " + grpcFailure.getMessage();
                    System.err.println("Scheduler: " + error);
                    return error;
                });
    }
}