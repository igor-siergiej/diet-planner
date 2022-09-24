package com.app.planner;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class StringValidation {

    public final static int PASSWORD_MIN_CHAR = 8;
    public final static int PASSWORD_MAX_CHAR = 15;
    public final static String UPPER_CASE_REGEX = "(.*[A-Z].*)";
    public final static String LOWER_CASE_REGEX = "(.*[a-z].*)";
    public final static String NUMBER_REGEX = "(.*[0-9].*)";
    public final static String SPECIAL_CHAR_REGEX = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
    public final static int USERNAME_MIN_CHAR = 4;
    public final static int USERNAME_MAX_CHAR = 20;
    public final static int MAX_AGE_DIGITS = 2;
    public final static int MAX_WEIGHT_DIGITS = 4;
    public final static int MAX_HEIGHT_DIGITS = 3;
    public final static int MAX_PORTION_DIGITS = 4;
    public final static int MIN_AGE = 14;
    public final static int MAX_AGE = 90;
    public final static int MIN_HEIGHT = 140;
    public final static int MAX_HEIGHT = 230;
    public final static int MIN_WEIGHT = 40;
    public final static int MAX_WEIGHT = 200;
    public final static String RETURN_STRING = "Correct!";

    public static boolean ageValidation(String age) {
        if (!age.equals("")) {
            int integer = Integer.valueOf(age);
            return integer >= MIN_AGE && integer <= MAX_AGE;
        }
        return false;
    }

    public static boolean heightValidation(String height) {
        if (!height.equals("")) {
            int integer = Integer.valueOf(height);
            return integer >= MIN_HEIGHT && integer <= MAX_HEIGHT;
        }
        return false;
    }

    public static boolean weightValidation(String weight) {
        if (!weight.equals("")) {
            int integer = Integer.valueOf(weight);
            return integer >= MIN_WEIGHT && integer <= MAX_WEIGHT;
        }
        return false;
    }

    public static String portionValidation(String portion) {
        return integerValidation(portion, MAX_PORTION_DIGITS);
    }

    public static boolean containsUpperCase(String string) {
        return (string.matches(UPPER_CASE_REGEX));
    }

    public static boolean contrainsLowerCase(String string) {
        return (string.matches(LOWER_CASE_REGEX));
    }

    public static boolean containsNumber(String string) {
        return (string.matches(NUMBER_REGEX));
    }

    public static boolean containsSpecialChar(String string) {
        return (string.matches(SPECIAL_CHAR_REGEX));
    }

    public static String usernameValidation(String userName) {
        if (userName.length() > USERNAME_MAX_CHAR || userName.length() < USERNAME_MIN_CHAR) {
            return "Username should be less than 20 and more than 4 characters in length.";
        }
        if ((nameValidation(userName))) {
            return "Username should not contain any special characters";
        }
        return RETURN_STRING;
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
        return RETURN_STRING;
    }

    static public String emailValidation(String email) {
        boolean result = true;
        String returnString = "";
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        if (result) {
            returnString = RETURN_STRING;
        } else {
            returnString = "Please enter a valid email";
        }
        return returnString;
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

    public static boolean isStringWithinCharLimit(String string) {
        return (string.length() > 8 && string.length() < 16);
    }

    // TODO check if this is really needed
    public static String stringValidation(String string) {
        string = string.replaceAll("\\s+", " ");
        if (!string.matches("[a-zA-Z|\\s]")) { // is this a-Z or a space?
            return string.replaceAll("[^a-zA-Z|\\s]", "");
        }
        if (string.length() > 15) {
            return string.substring(0, 15);
        }
        return string;
    }

    private static Boolean nameValidation(String name) {
        return name.matches("[a-zA-Z|\\s]");
    }

    public static String integerValidation(String input, int numOfDigits) {
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

    public static String floatValidation(String input, int numOfDigits) {
        if (!input.matches("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)")) {
            return input.replaceAll("[^\\d]", "");
        }
        /*if (input.equals("0") || input.equals("00") || input.equals("000") || input.equals("0000")) {
            return "";
        }*/
        if (input.length() > numOfDigits) {
            return input.substring(0, numOfDigits);
        }
        return input;
    }
}


