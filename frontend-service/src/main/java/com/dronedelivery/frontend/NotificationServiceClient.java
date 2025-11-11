package com.dronedelivery.frontend;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "notification-api")
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface NotificationServiceClient {

    @GET
    List<NotificationDTO> getAllNotifications();

    @GET
    @Path("/{id}")
    NotificationDTO getNotificationById(@PathParam("id") String id);

    @POST
    NotificationDTO createNotification(NotificationDTO notification);

    @DELETE
    @Path("/{id}")
    void deleteNotification(@PathParam("id") String id);
}

