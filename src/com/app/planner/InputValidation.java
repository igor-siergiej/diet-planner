package com.app.planner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InputValidation {

    private final static int PASSWORD_MIN_CHAR = 8;
    private final static int PASSWORD_MAX_CHAR = 15;
    private final static String UPPER_CASE_REGEX = "(.*[A-Z].*)";
    private final static String LOWER_CASE_REGEX = "(.*[a-z].*)";
    private final static String NUMBER_REGEX = "(.*[0-9].*)";
    private final static String SPECIAL_CHAR_REGEX = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
    private final static int USERNAME_MIN_CHAR = 4;
    private final static int USERNAME_MAX_CHAR = 20;
    private final static int MAX_AGE_DIGITS = 2;
    private final static int MAX_WEIGHT_DIGITS = 3;
    private final static int MAX_HEIGHT_DIGITS = 3;
    private final static int MAX_PORTION_DIGITS = 4;

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

        if (password.length() < PASSWORD_MAX_CHAR && password.length() > PASSWORD_MIN_CHAR) {
            passwordStrengthValue += 0.2;
        }

        if (password.matches(UPPER_CASE_REGEX)) {
            passwordStrengthValue += 0.2;
        }

        if (password.matches(LOWER_CASE_REGEX)) {
            passwordStrengthValue += 0.2;
        }

        if (password.matches(NUMBER_REGEX)) {
            passwordStrengthValue += 0.2;
        }

        if (password.matches(SPECIAL_CHAR_REGEX)) {
            passwordStrengthValue += 0.2;
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

    private static String integerValidation(String input , int numOfDigits) {
        if (!input.matches("\\d*")) {
            return input.replaceAll("[^\\d]", "");
        }
        if (input.equals("0") || input.equals("00") || input.equals("000") || input.equals("0000")) {
            return "";
        }
        if (input.length() > numOfDigits) {
            return input.substring(0, numOfDigits);
        }
        return input;
    }
    static public String ageValidation(String age) {
        return integerValidation(age, MAX_AGE_DIGITS);
    }

    static public String heightValidation(String height) {
        return integerValidation(height, MAX_HEIGHT_DIGITS);
    }

    static public String weightValidation(String weight) {
        return integerValidation(weight, MAX_WEIGHT_DIGITS);
    }

    static public String portionValidation(String portion) {
        return integerValidation(portion, MAX_PORTION_DIGITS);
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
}


