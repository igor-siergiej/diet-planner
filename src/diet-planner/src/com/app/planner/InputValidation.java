package com.app.planner;

class UsernameValidator
{
    public UsernameValidator()
    {
        super();
    }
    public static void main(String[] args)
    {
        UsernameValidator usernameValidator = new UsernameValidator();

        String userName = "techdive";
        String passWord = "java2011";
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);

        usernameValidator.usernameValidation(userName,passWord);
        System.out.println();
        passWord = "Java2011*";
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);
        usernameValidator.usernameValidation(userName,passWord);
    }

    /*
    *Username should between 20 characters and 8 characters
    *Username should not be the same as the password
     */

    public void usernameValidation(String password, String userName)
    {
        boolean valid = true;
        if (userName.length() > 20 || password.length() < 4)
        {
            System.out.println("Password should be less than 20 and more than 4 characters in length.");
            valid = false;
        }
        if (valid)
        {
            System.out.println("Password is valid.");
        }
    }
}
class PasswordValidator
{
    public PasswordValidator()
    {
        super();
    }
    public static void main(String[] args)
    {
        PasswordValidator passwordValidator = new PasswordValidator();

        String userName = "techdive";
        String passWord = "java2011";
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);

        passwordValidator.passwordValidation(userName,passWord);
        System.out.println();
        passWord = "Java2011*";
        System.out.println("Input : UserName "+userName+" PassWord -> "+passWord);
        passwordValidator.passwordValidation(userName,passWord);
    }

    /*
     * Password should be less than 15 and more than 8 characters in length.
     * Password should contain at least one upper case and one lower case alphabet.
     * Password should contain at least one number.
     * Password should contain at least one special character.
     */

    public void passwordValidation(String userName, String password)
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
    }
}