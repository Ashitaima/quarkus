package com.dronedelivery.managementservice;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/drones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DroneResource {

    @GET
    public Response getAllDrones() {
        List<Drone> drones = Drone.listAll();
        return Response.ok(drones).build();
    }

    @GET
    @Path("/{id}")
    public Response getDroneById(@PathParam("id") Long id) {
        Drone drone = Drone.findById(id);
        if (drone != null) {
            return Response.ok(drone).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Drone not found\"}")
                    .build();
        }
    }

    @POST
    @Transactional
    public Response createDrone(Drone drone) {
        drone.persist();
        return Response.status(Response.Status.CREATED).entity(drone).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateDrone(@PathParam("id") Long id, Drone updatedDrone) {
        Drone drone = Drone.findById(id);
        if (drone != null) {
            drone.model = updatedDrone.model;
            drone.status = updatedDrone.status;
            drone.batteryLevel = updatedDrone.batteryLevel;
            drone.latitude = updatedDrone.latitude;
            drone.longitude = updatedDrone.longitude;
            drone.persist();
            return Response.ok(drone).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Drone not found\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteDrone(@PathParam("id") Long id) {
        boolean deleted = Drone.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Drone not found\"}")
                    .build();
        }
    }

    @GET
    @Path("/status/{status}")
    public Response getDronesByStatus(@PathParam("status") DroneStatus status) {
        List<Drone> drones = Drone.findByStatus(status);
        return Response.ok(drones).build();
    }
}

