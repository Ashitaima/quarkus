package com.dronedelivery.schedulerservice; // Ваш пакет

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey="notification-service-api") // Логічне ім'я
@Path("/notify")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface NotificationServiceClient {

    // Цей метод відповідає ендпоінту в NotificationResource
    @POST
    Uni<Void> sendNotification(NotificationRequest request);
}