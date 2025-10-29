package com.dronedelivery.notificationservice; // Ваш пакет

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notify")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @POST // Ми приймаємо дані, тому це POST
    public Response sendNotification(NotificationRequest request) {
        // Quarkus автоматично перетворить тіло JSON-запиту на об'єкт NotificationRequest

        System.out.println("=============================================");
        System.out.println("REST (NotificationService): ВІДПРАВКА СПОВІЩЕННЯ");
        System.out.println("Клієнту: " + request.customerId);
        System.out.println("Повідомлення: " + request.message);
        System.out.println("=============================================");

        return Response.noContent().build();
    }
}