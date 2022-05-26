package com.app.planner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;

public class InputValidation {

    private final static int PASSWORD_MIN_CHAR = 8;
    private final static int PASSWORD_MAX_CHAR = 15;
    private final static String UPPER_CASE_REGEX = "(.*[A-Z].*)";
    private final static String LOWER_CASE_REGEX = "(.*[a-z].*)";
    private final static String NUMBER_REGEX = "(.*[0-9].*)";
    private final static String SPECIAL_CHAR_REGEX = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
    private final static int USERNAME_MIN_CHAR = 4;
    private final static int USERNAME_MAX_CHAR = 20;

    public static String usernameValidation(String userName) {
        if (userName.length() > USERNAME_MAX_CHAR || userName.length() < USERNAME_MIN_CHAR) {
            return "Username should be less than 20 and more than 4 characters in length.";
        }
        return "valid";
    }

    public static String passwordValidation(String password) {
        if (password.length() > PASSWORD_MAX_CHAR || password.length() < PASSWORD_MIN_CHAR) {
            return "Password should be less than 15 and more than 8 characters in length.";
        }

        if (!password.matches(UPPER_CASE_REGEX)) {
            return "Password should contain at least one upper case alphabet";
        }

        if (!password.matches(LOWER_CASE_REGEX)) {
            return "Password should contain at least one lower case alphabet";
        }

        if (!password.matches(NUMBER_REGEX)) {
            return "Password should contain at least one number.";
        }

        if (!password.matches(SPECIAL_CHAR_REGEX)) {
            return "Password should contain at least one special character";
        }
        return "valid";
    }

    public static float getPasswordStrength(String password) {
        float passwordStrengthValue = 0;

        if (password.length() < PASSWORD_MAX_CHAR) {
            passwordStrengthValue += 0.166;
        }
        if ( password.length() > PASSWORD_MIN_CHAR) {
            passwordStrengthValue += 0.166;
        }

        if (password.matches(UPPER_CASE_REGEX)) {
            passwordStrengthValue += 0.166;
        }

        if (password.matches(LOWER_CASE_REGEX)) {
            passwordStrengthValue += 0.166;
        }

        if (password.matches(NUMBER_REGEX)) {
            passwordStrengthValue += 0.166;
        }

        if (password.matches(SPECIAL_CHAR_REGEX)) {
            passwordStrengthValue += 0.166;
        }
        return passwordStrengthValue;
    }

    public static boolean isStringWithinCharLimit (String string) {
        return (string.length() > 8 && string.length() < 16);
    }

    public static boolean containsUpperCase (String string) {
        return (string.matches(UPPER_CASE_REGEX));
    }

    public static boolean contrainsLowerCase (String string) {
        return (string.matches(LOWER_CASE_REGEX));
    }

    public static boolean containsNumber (String string){
        return (string.matches(NUMBER_REGEX));
    }

    public static boolean containsSpecialChar (String string) {
        return (string.matches(SPECIAL_CHAR_REGEX));
    }

    static public String ageValidation(String age) {
        if (!age.matches("\\d*")) {
            return age.replaceAll("[^\\d]", "");
        }
        if (age.length() > 2) {
            return age.substring(0, 2);
        }
        return age;
    }

    static public String portionValidation(String age) {
        if (!age.matches("\\d*")) {
            return age.replaceAll("[^\\d]", "");
        }
        if (age.length() > 4) {
            return age.substring(0, 4);
        }
        return age;
    }

    static public String stringValidation(String string) {
        string = string.replaceAll("\\s+", " ");
        if (!string.matches("[a-zA-Z|\\s]")) { // is this a-Z or a space?
            return string.replaceAll("[^a-zA-Z|\\s]", "");
        }
        if (string.length() > 15) {
            return string.substring(0, 15);
        }
        return string;
    }

    static public Boolean nameValidation(String name) {
        return !name.matches("[a-zA-Z|\\s]");
    }

    static public Boolean emailValidation(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }



    static public String weightValidation(String string) {
        if (!string.matches("\\d*")) {
            return string.replaceAll("[^\\d]", "");
        }
        if (string.length() > 3) {
            return string.substring(0, 2);
        }
        return string;
    }
}


