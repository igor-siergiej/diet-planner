package com.app.planner;

import java.sql.*;

public class DatabaseConnection {

    private static final String SQL_INSERT = "INSERT INTO users (username, password,salt) VALUES (?,?,?)";
    private static final String SQL_SELECT = "SELECT * FROM users WHERE username = ?";

    public static boolean register(String username, String password) {
        String salt = PasswordUtils.getSalt(30);
        String securePassword = PasswordUtils.generateSecurePassword(password,salt);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr", "tehdblxcjrcior", "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8")) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT); {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, securePassword);
                preparedStatement.setString(3, salt);

        }
            preparedStatement.executeUpdate();

    } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean login(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr", "tehdblxcjrcior", "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8")) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT); {

                preparedStatement.setString(1, username);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String securePassword = resultSet.getString("password");
            String salt = resultSet.getString("salt");

            if (PasswordUtils.verifyUserPassword(password,securePassword,salt)) {
                System.out.println("logged in");
            } else {
                System.out.println("wrong login");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

}