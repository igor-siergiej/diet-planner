package com.app.planner;

import java.sql.*;

public class DatabaseConnection {

    private static final String SQL_INSERT = "INSERT INTO users (username,password,salt,profiledata) VALUES (?,?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_INSERT_SAVE = "UPDATE Users SET profiledata = ? WHERE username = ?";

    public static boolean register(String username, String password) { // call when you are creating a new login to db
        Profile profile = new Profile();
        profile.setProfileName(username);
        Diary diary = new Diary();
        profile.setDiary(diary);
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password,salt);
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT); {
                preparedStatement.setString(1, username);
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

    public static boolean login(String username, String password) { // call when you are trying to verify login from db
        Connection connection = connect();
        String securePassword = "";
        String salt = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT); {
                preparedStatement.setString(1, username);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            securePassword = resultSet.getString("password");
            salt = resultSet.getString("salt");
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        if (PasswordUtils.verifyUserPassword(password,securePassword,salt)) {
            return true;
        } else {
            return false;
        }
    }

    public static Profile getProfileFromDb(String username) { // call when you are loading profile data from db
        Connection connection = connect();
        String profileData = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT); {
                preparedStatement.setString(1, username);
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

    public static boolean saveProfileToDb(String username,Profile profile) { // call when you are saving profile data to db
        Connection connection = connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_SAVE); {
                preparedStatement.setString(1, profile.saveToString());
                preparedStatement.setString(2, username);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } return true;
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
}