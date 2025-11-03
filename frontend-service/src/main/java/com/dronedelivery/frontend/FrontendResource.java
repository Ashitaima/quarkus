package com.dronedelivery.frontend;

import io.quarkus.qute.Template;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class FrontendResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    Template index;

    @Inject
    Template drones;

    @Inject
    Template orders;

    @Inject
    Template notifications;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public String index() {
        return index
                .data("userName", securityIdentity.getPrincipal().getName())
                .data("roles", securityIdentity.getRoles())
                .render();
    }

    @GET
    @Path("/drones")
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public String drones() {
        return drones
                .data("userName", securityIdentity.getPrincipal().getName())
                .data("roles", securityIdentity.getRoles())
                .render();
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public String orders() {
        return orders
                .data("userName", securityIdentity.getPrincipal().getName())
                .data("roles", securityIdentity.getRoles())
                .render();
    }

    @GET
    @Path("/notifications")
    @Produces(MediaType.TEXT_HTML)
    @Authenticated
    public String notifications() {
        return notifications
                .data("userName", securityIdentity.getPrincipal().getName())
                .data("roles", securityIdentity.getRoles())
                .render();
    }
}

