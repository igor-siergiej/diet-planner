package com.app.planner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidationTest {

    @Test
    public void usernameLengthTest() {
        assertEquals("Username should be less than 20 and more than 4 characters in length.",InputValidation.usernameValidation("techdivetechdivetechdivetechdivetechdivetechdive"),"Username should be less than 20 characters");
        assertEquals("valid",InputValidation.usernameValidation("techdive"),"Username should be less than 20 characters");
    }

    @Test
    public void passwordLengthTest() {
        assertEquals("Password should be less than 15 and more than 8 characters in length.",InputValidation.passwordValidation("java"),"Password should be less than 15 and more than 8 characters in length.");
        assertEquals("valid",InputValidation.passwordValidation("!Javajava2021"),"Password should be less than 15 and more than 8 characters in length.");
    }

    @Test
    public void passwordUpperCaseTest() {
        assertEquals("Password should contain atleast one upper case alphabet",InputValidation.passwordValidation("techdive1"),"Password should contain atleast one upper case alphabet");
        assertEquals("valid",InputValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one upper case alphabet");
    }

    @Test
    public void passwordLowerCaseTest() {
        assertEquals("Password should contain atleast one lower case alphabet",InputValidation.passwordValidation("TECHDIVE1"),"Password should contain atleast one lower case alphabet");
        assertEquals("valid",InputValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one lower case alphabet");
    }

    @Test
    public void passwordNumberTest() {
        assertEquals("Password should contain atleast one number.",InputValidation.passwordValidation("Techdive"),"Password should contain atleast one number.");
        assertEquals("valid",InputValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one number.");
    }

    @Test
    public void passwordSpecialCharacterTest() {
        assertEquals("Password should contain atleast one special character",InputValidation.passwordValidation("Techdive1"),"Password should contain atleast one special character");
        assertEquals("valid",InputValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one number.");
    }

    //TODO AGE VALIDATION TESTS
}
