package com.dronedelivery.frontend;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api/management")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ManagementProxyResource {

    @Inject
    @RestClient
    DroneServiceClient droneServiceClient;

    @GET
    @Path("/drones")
    public Response getDrones() {
        try {
            return Response.ok(droneServiceClient.getAllDrones()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("{\"error\": \"Management service unavailable: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/drones/{id}")
    public Response getDroneById(@PathParam("id") Long id) {
        try {
            return Response.ok(droneServiceClient.getDroneById(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Drone not found\"}")
                    .build();
        }
    }

    @POST
    @Path("/drones")
    public Response createDrone(DroneDTO droneDTO) {
        try {
            DroneDTO created = droneServiceClient.createDrone(droneDTO);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create drone: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/drones/{id}")
    public Response updateDrone(@PathParam("id") Long id, DroneDTO droneDTO) {
        try {
            DroneDTO updated = droneServiceClient.updateDrone(id, droneDTO);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to update drone: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/drones/{id}")
    public Response deleteDrone(@PathParam("id") Long id) {
        try {
            droneServiceClient.deleteDrone(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to delete drone: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}

