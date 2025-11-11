package com.dronedelivery.frontend;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "management-api")
@Path("/drones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DroneServiceClient {

    @GET
    List<DroneDTO> getAllDrones();

    @GET
    @Path("/{id}")
    DroneDTO getDroneById(@PathParam("id") Long id);

    @POST
    DroneDTO createDrone(DroneDTO drone);

    @PUT
    @Path("/{id}")
    DroneDTO updateDrone(@PathParam("id") Long id, DroneDTO drone);

    @DELETE
    @Path("/{id}")
    void deleteDrone(@PathParam("id") Long id);
}

