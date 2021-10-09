package com.app.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {



    String username = "testProfile";
    String password = "testPassword!1";
    String encryptedPassword = "1Yafd/ugsdJeq7MjOr9r1nAtl2buwzdnNErSoopCiT0=";
    String salt = "fC8AY9I2ZnOozANZ5hyEdPbqqmLei0";

    @Test
    void register() {
        assertEquals(false,DatabaseConnection.register(username,password),"Should not be able to register an account with a username that already exists");
    }

    @Test
    void login() {
        assertEquals(true,DatabaseConnection.login(username,password),"Should be able to login using correct creditentials");
    }
}