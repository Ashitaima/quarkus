package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/orders")
@Authenticated
public class OrderProxyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        // Mock data for now - will be replaced with actual service call
        String mockOrders = """
            [
                {
                    "id": "ORD-001",
                    "customerName": "John Doe",
                    "destination": "123 Main St, Kyiv",
                    "weight": 2.5,
                    "status": "PENDING",
                    "createdAt": "2025-11-03T10:30:00"
                },
                {
                    "id": "ORD-002",
                    "customerName": "Jane Smith",
                    "destination": "456 Oak Ave, Kyiv",
                    "weight": 1.8,
                    "status": "PROCESSING",
                    "createdAt": "2025-11-03T11:15:00"
                },
                {
                    "id": "ORD-003",
                    "customerName": "Bob Johnson",
                    "destination": "789 Pine Rd, Kyiv",
                    "weight": 3.2,
                    "status": "DELIVERED",
                    "createdAt": "2025-11-03T09:45:00"
                }
            ]
            """;
        return Response.ok(mockOrders).build();
    }
}

