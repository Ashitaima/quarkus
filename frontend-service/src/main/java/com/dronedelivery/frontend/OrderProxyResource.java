package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/orders")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderProxyResource {

    @Inject
    @RestClient
    OrderServiceClient orderServiceClient;

    @GET
    public Response getOrders() {
        try {
            return Response.ok(orderServiceClient.getAllOrders()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("{\"error\": \"Order service unavailable: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    public Response createOrder(OrderDTO orderDTO) {
        try {
            OrderDTO created = orderServiceClient.createOrder(orderDTO);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create order: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Long id) {
        try {
            return Response.ok(orderServiceClient.getOrderById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Order not found\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateOrder(@PathParam("id") Long id, OrderDTO orderDTO) {
        try {
            OrderDTO updated = orderServiceClient.updateOrder(id, orderDTO);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to update order: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        try {
            orderServiceClient.deleteOrder(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to delete order: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}

