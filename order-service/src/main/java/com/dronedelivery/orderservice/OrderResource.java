package com.dronedelivery.orderservice;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @GET
    public Response getAllOrders() {
        List<Order> orders = orderRepository.listAll();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Long id) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            return Response.ok(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Замовлення з ID " + id + " не знайдено\"}")
                    .build();
        }
    }

    @POST
    @Transactional
    public Response createOrder(Order order) {
        orderRepository.persist(order);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateOrder(@PathParam("id") Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id);
        if (order != null) {
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setDestination(updatedOrder.getDestination());
            order.setWeight(updatedOrder.getWeight());
            order.setStatus(updatedOrder.getStatus());
            orderRepository.persist(order);
            return Response.ok(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Замовлення з ID " + id + " не знайдено\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    public Response updateOrderStatus(@PathParam("id") Long id, OrderStatus newStatus) {
        System.out.println("REST (OrderService): Отримано запит на зміну статусу для " + id + " на " + newStatus);

        Order order = orderRepository.findById(id);

        if (order != null) {
            order.setStatus(newStatus);
            orderRepository.persist(order);
            return Response.ok(order).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Замовлення з ID " + id + " не знайдено\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteOrder(@PathParam("id") Long id) {
        boolean deleted = orderRepository.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Замовлення з ID " + id + " не знайдено\"}")
                    .build();
        }
    }

    @GET
    @Path("/customer/{customerId}")
    public Response getOrdersByCustomer(@PathParam("customerId") String customerId) {
        List<Order> orders = orderRepository.findByCustomer(customerId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/status/{status}")
    public Response getOrdersByStatus(@PathParam("status") OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/active")
    public Response getActiveOrders() {
        List<Order> orders = orderRepository.findActiveOrders();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/customer/{customerId}/count")
    public Response countCustomerOrders(@PathParam("customerId") String customerId) {
        long count = orderRepository.countByCustomer(customerId);
        return Response.ok("{\"count\": " + count + "}").build();
    }

    @GET
    @Path("/search")
    public Response searchByDestination(@QueryParam("destination") String destination) {
        if (destination == null || destination.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Параметр 'destination' обов'язковий\"}")
                    .build();
        }
        List<Order> orders = orderRepository.findByDestinationContaining(destination);
        return Response.ok(orders).build();
    }
}