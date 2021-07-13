package com.app.planner;

import org.postgresql.util.PSQLException;

import java.sql.*;

public class DatabaseConnection {

    private static final String SQL_INSERT = "INSERT INTO users (username, password) VALUES (?,?)";
    private static final String SQL_SELECT = "SELECT * FROM users WHERE id = ? and password = ?";

    public void displayTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr", "tehdblxcjrcior", "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8")) {
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.printf("%-30.30s  %-30.30s%n", "username", "password");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("username"), resultSet.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    public boolean register(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr", "tehdblxcjrcior", "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8")) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT); {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

        }
            preparedStatement.executeUpdate();

    } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean login(String id, String pass) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-74-60-70.eu-west-1.compute.amazonaws.com/d7p8thbvo4s0gr", "tehdblxcjrcior", "26bb184833b93b3bd5e568ddf5a93d07c3b55af476f6b0f3424f55f80d58f5d8")) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT); {

                preparedStatement.setString(1, id);
                preparedStatement.setString(2, pass);

            }
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst() ) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

}