package com.dronedelivery.frontend;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "order-api")
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OrderServiceClient {

    @GET
    List<OrderDTO> getAllOrders();

    @GET
    @Path("/{id}")
    OrderDTO getOrderById(@PathParam("id") Long id);

    @POST
    OrderDTO createOrder(OrderDTO order);

    @PUT
    @Path("/{id}")
    OrderDTO updateOrder(@PathParam("id") Long id, OrderDTO order);

    @DELETE
    @Path("/{id}")
    void deleteOrder(@PathParam("id") Long id);
}

