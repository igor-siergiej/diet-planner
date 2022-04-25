package com.app.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {
    String password = "testpass";
    String encryptedPassword = "1E6fd8MHqGla85xFKylnXUseFTJEZmfbinKK1OEu8Q0=";
    String salt = "f2MD9N5m5g4ymOUyOuzkiaDhpBRFAR";

    @Test
    void testSaltLength() {
        int TestLength = 50;
        assertEquals(TestLength,PasswordUtils.getSalt(TestLength).length(),"getSalt should create String of desired length");
    }

    @Test
    void verifyUserPassword() {
        assertEquals(true,PasswordUtils.verifyUserPassword(password,encryptedPassword,salt),"decryption using salt should work");
        assertEquals(false,PasswordUtils.verifyUserPassword("password",encryptedPassword,salt),"decryption using salt on a different password should not match");
    }
}