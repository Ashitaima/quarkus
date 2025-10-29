package com.dronedelivery.orderservice; // Ваш пакет

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @GET // Метод для отримання всіх замовлень (для тестування)
    public Response getAllOrders() {
        return Response.ok(orderRepository.findAll()).build();
    }

    @PUT
    @Path("/{id}/status") // Шлях: /orders/some-id/status
    public Response updateOrderStatus(@PathParam("id") String id, OrderStatus newStatus) {
        // @PathParam("id") бере 'id' з URL
        // 'newStatus' Quarkus автоматично візьме з тіла запиту (JSON)

        System.out.println("REST (OrderService): Отримано запит на зміну статусу для " + id + " на " + newStatus);

        Optional<Order> updatedOrder = orderRepository.updateStatus(id, newStatus);

        if (updatedOrder.isPresent()) {
            return Response.ok(updatedOrder.get()).build(); // Повертаємо оновлене замовлення
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Замовлення з ID " + id + " не знайдено")
                    .build();
        }
    }
}