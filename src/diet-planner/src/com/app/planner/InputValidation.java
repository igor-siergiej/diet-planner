package com.app.planner;

public class InputValidation {

    // TODO All of the regex expressions should probably be here as instance variables which are labelled, easier to read

    public static String usernameValidation(String userName) {
        if (userName.length() > 20 || userName.length() < 4)
        {
           return "Username should be less than 20 and more than 4 characters in length.";

        }
        return "valid";
    }
    public static String passwordValidation(String password)
    {
        if (password.length() > 15 || password.length() < 8)
        {
            return "Password should be less than 15 and more than 8 characters in length.";
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {

            return "Password should contain atleast one upper case alphabet";
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            return "Password should contain atleast one lower case alphabet";
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            return "Password should contain atleast one number.";
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
        {
            return "Password should contain atleast one special character";
        }
        return "valid";
    }

    static public String ageValidation(String age) {
        if (!age.matches("\\d*")) {
            return age.replaceAll("[^\\d]", "");
        }
        if (age.length() > 2) {
            return age.substring(0,2);
        }
        return age;
    }

    static public String portionValidation(String age) {
        if (!age.matches("\\d*")) {
            return age.replaceAll("[^\\d]", "");
        }
        if (age.length() > 4) {
            return age.substring(0,4);
        }
        return age;
    }

    static public String stringValidation(String string) {
        string = string.replaceAll("\\s+", " ");
        if (!string.matches("[a-zA-Z|\\s]")) { // is this a-Z or a space?
            return string.replaceAll("[^a-zA-Z|\\s]", "");
        }
        if (string.length() > 15) {
            return string.substring(0,15);
        }
        return string;
    }

    static public Boolean nameValidation(String name) {
        if (name.matches("[a-zA-Z|\\s]")) {
            return true;
        } else {
            return false;
        }
    }

    static public Boolean emailValidation(String email) {
        if (email.matches("^(.+)@(.+)$")) {
            return true;
        } else {
            return false;
        }
    }

    static public String weightValidation( String string) {
        if (!string.matches("\\d*")) {
            return string.replaceAll("[^\\d]", "");
        }
        if (string.length() > 3) {
            return string.substring(0,2);
        }
        return string;
    }
}


