package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/notifications")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationProxyResource {

    @Inject
    @RestClient
    NotificationServiceClient notificationServiceClient;

    @GET
    public Response getAllNotifications() {
        try {
            return Response.ok(notificationServiceClient.getAllNotifications()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("{\"error\": \"Notification service unavailable: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    public Response createNotification(NotificationDTO notificationDTO) {
        try {
            NotificationDTO created = notificationServiceClient.createNotification(notificationDTO);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create notification: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotification(@PathParam("id") String id) {
        try {
            notificationServiceClient.deleteNotification(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to delete notification: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}

