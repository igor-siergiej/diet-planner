package com.app.planner;

import org.postgresql.util.PSQLException;

import java.sql.*;

public class DatabaseConnection {

    private static final String SQL_INSERT = "INSERT INTO users (id, password) VALUES (?,?)";
    private static final String SQL_SELECT = "SELECT * FROM users WHERE id = ? and password = ?";

    public void displayTable() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "")) {
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.printf("%-30.30s  %-30.30s%n", "id", "password");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                System.out.printf("%-30.30s  %-30.30s%n", resultSet.getString("id"), resultSet.getString("password"));
            }

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    public boolean register(String id, String pass) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "")) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT); {

                preparedStatement.setString(1, id);
                preparedStatement.setString(2, pass);

        }
            preparedStatement.executeUpdate();

    } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean login(String id, String pass) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "")) {
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