package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/notifications")
@Authenticated
public class NotificationProxyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotifications() {
        // Mock data for now - will be replaced with actual service call
        String mockNotifications = """
            [
                {
                    "id": "NOTIF-001",
                    "title": "Delivery Completed",
                    "message": "Order #ORD-003 has been successfully delivered to customer Bob Johnson",
                    "type": "success",
                    "timestamp": "2025-11-03T12:15:00"
                },
                {
                    "id": "NOTIF-002",
                    "title": "Drone Maintenance Required",
                    "message": "Drone DRONE-003 needs maintenance. Battery level critically low.",
                    "type": "warning",
                    "timestamp": "2025-11-03T11:45:00"
                },
                {
                    "id": "NOTIF-003",
                    "title": "New Order Received",
                    "message": "New delivery order #ORD-004 has been assigned to Drone DRONE-001",
                    "type": "info",
                    "timestamp": "2025-11-03T11:30:00"
                },
                {
                    "id": "NOTIF-004",
                    "title": "Delivery Delayed",
                    "message": "Order #ORD-002 is delayed due to weather conditions",
                    "type": "warning",
                    "timestamp": "2025-11-03T10:20:00"
                },
                {
                    "id": "NOTIF-005",
                    "title": "System Update",
                    "message": "All systems are operational. Drone fleet is ready for operations.",
                    "type": "success",
                    "timestamp": "2025-11-03T09:00:00"
                }
            ]
            """;
        return Response.ok(mockNotifications).build();
    }
}

