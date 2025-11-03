package com.dronedelivery.frontend;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class FrontendResourceTest {

    @Test
    public void testIndexEndpointRedirectsToAuth() {
        // Without authentication, should redirect to Keycloak
        given()
            .when().get("/")
            .then()
            .statusCode(302); // Redirect to login
    }

    @Test
    public void testApiEndpointsRequireAuth() {
        given()
            .when().get("/api/management/drones")
            .then()
            .statusCode(401); // Unauthorized without token
    }
}

