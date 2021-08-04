package com.app.planner;

public class InputValidation {

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
}


