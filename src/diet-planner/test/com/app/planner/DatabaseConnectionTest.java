package com.app.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    String username = "test";
    String password = "testpass";
    String encryptedPassword = "1E6fd8MHqGla85xFKylnXUseFTJEZmfbinKK1OEu8Q0=";
    String salt = "f2MD9N5m5g4ymOUyOuzkiaDhpBRFAR";

    @Test
    void register() {
        assertEquals(false,DatabaseConnection.register(username,password),"Should not be able to register an account with a username that already exists");
    }

    @Test
    void login() {
        assertEquals(true,DatabaseConnection.login(username,password),"Should be able to login using correct creditentials");
    }
}