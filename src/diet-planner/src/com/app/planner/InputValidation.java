package com.app.planner;

public class InputValidation {
    /*
    *Username should between 20 characters and 8 characters
    *Username should not be the same as the password
     */
    public static boolean usernameValidation(String userName) {
        boolean valid = true;
        System.out.println(userName.length());
        if (userName.length() > 20 || userName.length() < 4)
        {
            System.out.println("Password should be less than 20 and more than 4 characters in length."); //change
            valid = false;
        }
        return valid;
    }

    public static boolean passwordValidation(String userName, String password)
    {
        boolean valid = true;
        if (password.length() > 15 || password.length() < 8)
        {
            System.out.println("Password should be less than 15 and more than 8 characters in length.");
            valid = false;
        }
        if (password.indexOf(userName) > -1)
        {
            System.out.println("Password Should not be same as user name");
            valid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            System.out.println("Password should contain atleast one upper case alphabet");
            valid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            System.out.println("Password should contain atleast one lower case alphabet");
            valid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            System.out.println("Password should contain atleast one number.");
            valid = false;
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
        {
            System.out.println("Password should contain atleast one special character");
            valid = false;
        }
        if (valid)
        {
            System.out.println("Password is valid.");
        }
        return valid;
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




    /*
     * Password should be less than 15 and more than 8 characters in length.
     * Password should contain at least one upper case and one lower case alphabet.
     * Password should contain at least one number.
     * Password should contain at least one special character.
     *
     *
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);

        usernameValidator.usernameValidation(userName,passWord);
        System.out.println();
        passWord = "Java2011*";
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);
        usernameValidator.usernameValidation(userName,passWord);
     *
     */


