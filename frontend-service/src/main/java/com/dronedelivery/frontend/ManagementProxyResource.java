package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/management")
@Authenticated
public class ManagementProxyResource {

    @GET
    @Path("/drones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrones() {
        // Mock data for now - will be replaced with actual service call
        String mockDrones = """
            [
                {
                    "id": "DRONE-001",
                    "model": "DJI Mavic",
                    "status": "AVAILABLE",
                    "latitude": 50.4501,
                    "longitude": 30.5234,
                    "batteryLevel": 95
                },
                {
                    "id": "DRONE-002",
                    "model": "DJI Phantom",
                    "status": "BUSY",
                    "latitude": 50.4521,
                    "longitude": 30.5254,
                    "batteryLevel": 75
                },
                {
                    "id": "DRONE-003",
                    "model": "Parrot Anafi",
                    "status": "MAINTENANCE",
                    "latitude": 50.4481,
                    "longitude": 30.5214,
                    "batteryLevel": 20
                }
            ]
            """;
        return Response.ok(mockDrones).build();
    }
}

