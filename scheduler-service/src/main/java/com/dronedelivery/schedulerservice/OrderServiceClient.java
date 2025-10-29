package com.dronedelivery.schedulerservice; // Ваш пакет

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni; // Використовуємо Uni для асинхронних викликів

@RegisterRestClient(configKey="order-service-api") // Логічне ім'я
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrderServiceClient {

    // Цей метод відповідає ендпоінту в OrderResource
    @PUT
    @Path("/{id}/status")
    Uni<Void> updateOrderStatus(@PathParam("id") String id, OrderStatus newStatus);
    // Uni<Void> означає, що ми не чекаємо тіла відповіді, лише успішний статус
}