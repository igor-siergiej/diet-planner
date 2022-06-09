package com.app.planner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringValidationTest {

    @Test
    public void usernameLengthTest() {
        assertEquals("Username should be less than 20 and more than 4 characters in length.", StringValidation.usernameValidation("techdivetechdivetechdivetechdivetechdivetechdive"),"Username should be less than 20 characters");
        assertEquals("valid", StringValidation.usernameValidation("techdive"),"Username should be less than 20 characters");
    }

    @Test
    public void passwordLengthTest() {
        assertEquals("Password should be less than 15 and more than 8 characters in length.", StringValidation.passwordValidation("java"),"Password should be less than 15 and more than 8 characters in length.");
        assertEquals("valid", StringValidation.passwordValidation("!Javajava2021"),"Password should be less than 15 and more than 8 characters in length.");
    }

    @Test
    public void passwordUpperCaseTest() {
        assertEquals("Password should contain atleast one upper case alphabet", StringValidation.passwordValidation("techdive1"),"Password should contain atleast one upper case alphabet");
        assertEquals("valid", StringValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one upper case alphabet");
    }

    @Test
    public void passwordLowerCaseTest() {
        assertEquals("Password should contain atleast one lower case alphabet", StringValidation.passwordValidation("TECHDIVE1"),"Password should contain atleast one lower case alphabet");
        assertEquals("valid", StringValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one lower case alphabet");
    }

    @Test
    public void passwordNumberTest() {
        assertEquals("Password should contain atleast one number.", StringValidation.passwordValidation("Techdive"),"Password should contain atleast one number.");
        assertEquals("valid", StringValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one number.");
    }

    @Test
    public void passwordSpecialCharacterTest() {
        assertEquals("Password should contain atleast one special character", StringValidation.passwordValidation("Techdive1"),"Password should contain atleast one special character");
        assertEquals("valid", StringValidation.passwordValidation("!Javajava2021"),"Password should contain atleast one number.");
    }

    @Test
    public void emailTest() { //should use assertFalse and assertTrue
        assertEquals(true, StringValidation.emailValidation("test@gmail.com"),"Correct email format");
        assertEquals(false, StringValidation.emailValidation(""), "Empty string should be an invalid email");
        assertEquals(false, StringValidation.emailValidation("test[gmail.com"), "should be invalid email format");
        assertEquals(false, StringValidation.emailValidation("test@gmal.com."), "trailing dots should be invalid");
    }

    //TODO MORE VALIDATION TESTS
}
