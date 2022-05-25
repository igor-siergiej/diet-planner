package com.app.planner;

import java.sql.*;

public class DatabaseConnection {

    private static final String SQL_INSERT_USERS = "INSERT INTO users (email,password,salt,profiledata) VALUES (?,?,?,?)";
    private static final String SQL_INSERT_FEEDBACK = "INSERT INTO feedback (name,message,email) VALUES (?,?,?)";
    private static final String LOGIN = "SELECT password,salt FROM users WHERE email = ?";
    private static final String SQL_INSERT_SAVE = "UPDATE Users SET profiledata = ? WHERE email = ?";

    public static boolean register(String email, String password) { // call when you are creating a new login to db
        Profile profile = new Profile();
        profile.setEmail(email);
        profile.setPassword(password);
        Diary diary = new Diary();
        profile.setDiary(diary); // should we encrypt locally stored passwords?
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USERS);
            {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, securePassword);
                preparedStatement.setString(3, salt);
                preparedStatement.setString(4, profile.saveToString());
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean login(String email, String password) { // call when you are trying to verify login from db
        Connection connection = connect();
        String securePassword = "";
        String salt = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN);
            {
                preparedStatement.setString(1, email); //sets the username we are finding the password for
            }
            ResultSet resultSet = preparedStatement.executeQuery(); //get the resultSet from the query
            if (resultSet.next() == false) {
                return false; // this means no account with this email is found
            }
            securePassword = resultSet.getString("password");
            salt = resultSet.getString("salt");
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return PasswordUtils.verifyUserPassword(password, securePassword, salt);
    }

    public static Profile getProfileFromDb(String email) { // call when you are loading profile data from db
        Connection connection = connect();
        String profileData = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN);
            {
                preparedStatement.setString(1, email);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            profileData = resultSet.getString("profileData");
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        Profile profile = new Profile();
        profile.loadFromString(profileData);
        return profile;
    }

    public static boolean saveProfileToDb(String email, Profile profile) { // call when you are saving profile data to db
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_SAVE);
            {
                preparedStatement.setString(1, profile.saveToString());
                preparedStatement.setString(2, email);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    private static Connection connect() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr",
                    "tehdblxcjrcior",
                    "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8");
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static boolean sendFeedback(String name, String email, String message) {
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_FEEDBACK);
            {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, message);
                preparedStatement.setString(3, email);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}