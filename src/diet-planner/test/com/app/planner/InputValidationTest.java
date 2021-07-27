package com.app.planner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTest {

    String userName = "techdive";
    String userName1 = "techdivetechdivetechdivetechdivetechdivetechdive";
    String passWord = "java2011";

    @Test
    public void usernameLengthTest() {
        assertEquals(true,InputValidation.usernameValidation(userName1),"Username should be less than 20 characters");
    }
}
