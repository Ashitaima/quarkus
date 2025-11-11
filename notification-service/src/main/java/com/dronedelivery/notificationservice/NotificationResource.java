package com.dronedelivery.notificationservice;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @GET
    public Response getAllNotifications() {
        List<Notification> notifications = Notification.listAll();
        return Response.ok(notifications).build();
    }

    @GET
    @Path("/{id}")
    public Response getNotificationById(@PathParam("id") String id) {
        Notification notification = Notification.findById(new org.bson.types.ObjectId(id));
        if (notification != null) {
            return Response.ok(notification).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Notification not found\"}")
                    .build();
        }
    }

    @POST
    public Response createNotification(Notification notification) {
        if (notification.timestamp == null) {
            notification.timestamp = java.time.LocalDateTime.now();
        }
        notification.persist();
        return Response.status(Response.Status.CREATED).entity(notification).build();
    }

    @GET
    @Path("/type/{type}")
    public Response getNotificationsByType(@PathParam("type") String type) {
        List<Notification> notifications = Notification.findByType(type);
        return Response.ok(notifications).build();
    }

    @GET
    @Path("/latest/{limit}")
    public Response getLatestNotifications(@PathParam("limit") int limit) {
        List<Notification> notifications = Notification.findLatest(limit);
        return Response.ok(notifications).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteNotification(@PathParam("id") String id) {
        boolean deleted = Notification.deleteById(new org.bson.types.ObjectId(id));
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Notification not found\"}")
                    .build();
        }
    }
}