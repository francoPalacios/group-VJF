package com.example.loginui;

import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://dbcis18.clcw0sciasjn.us-east-2.rds.amazonaws.com:3306/DBCIS18",
                    "root", "cis12345");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate user credentials
    public boolean validateUser(String email, String password) {

        try {
            String query = "SELECT * FROM User WHERE Email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // If a row is found, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add new user to the database
    public boolean addUser(User user) {
        try {
            String query = "INSERT INTO User (Email, password, Fn, Ln) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExists(String email) {
        try {
            String query = "SELECT * FROM User WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If resultSet.next() is true, it means email exists
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
        }
        return false;
}
    public boolean addbudget(Budget budget) {
        try {
            String sql = "INSERT INTO Budget (user_id, budget_name, income, income_startdate, income_enddate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, 1);
            statement.setInt(1, budget.getuserID());
            statement.setString(2, budget.getbudgetname());
            statement.setDouble(3, budget.getbudgetincome());
            statement.setDate(4, Date.valueOf(budget.getbudgetstartdate()));
            statement.setDate(5, Date.valueOf(budget.getbudgetenddate()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isBudgetName(String budgetname) {
        try {
            String query = "SELECT * FROM Budget WHERE budget_name= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, budgetname);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If resultSet.next() is true, it means email exists
        } catch (SQLException e) {
            System.out.println("Error checking budgetname existence: " + e.getMessage());
        }
        return false;
    }

}
