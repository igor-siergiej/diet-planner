package com.app.planner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InputValidation {

    // TODO All of the regex expressions should probably be here as instance variables which are labelled, easier to read

    public static String usernameValidation(String userName) {
        if (userName.length() > 20 || userName.length() < 4) {
            return "Username should be less than 20 and more than 4 characters in length.";

        }
        return "valid";
    }

    public static String passwordValidation(String password) {
        if (password.length() > 15 || password.length() < 8) {
            return "Password should be less than 15 and more than 8 characters in length.";
        }

        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            return "Password should contain at least one upper case alphabet";
        }

        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            return "Password should contain at least one lower case alphabet";
        }

        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers)) {
            return "Password should contain at least one number.";
        }

        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars)) {
            return "Password should contain at least one special character";
        }
        return "valid";
    }

    public static float getPasswordStrength(String password) {
        float passwordStrengthValue = 0;

        if (password.length() < 15 && password.length() > 7) {
            passwordStrengthValue += 0.2;
        }
        String upperCaseChars = "(.*[A-Z].*)";

        if (password.matches(upperCaseChars)) {
            passwordStrengthValue += 0.2;
        }

        String lowerCaseChars = "(.*[a-z].*)";
        if (password.matches(lowerCaseChars)) {
            passwordStrengthValue += 0.2;
        }

        String numbers = "(.*[0-9].*)";
        if (password.matches(numbers)) {
            passwordStrengthValue += 0.2;
        }

        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (password.matches(specialChars)) {
            passwordStrengthValue += 0.2;
        }
        return passwordStrengthValue;
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


