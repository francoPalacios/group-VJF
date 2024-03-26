package JVF.Data;

import JVF.Finances.Expense;
import JVF.loginui.User;
import JVF.Finances.Budget;

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
            String sql = "INSERT INTO Budget (user_id, budget_type, Amount, budget_startdate, budget_enddate, Recurrence, budget_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, budget.getuserID());
            statement.setString(2, budget.getBudgetType());
            statement.setDouble(3, budget.getbudgetincome());
            statement.setDate(4, Date.valueOf(budget.getbudgetstartdate()));
            statement.setDate(5, Date.valueOf(budget.getbudgetenddate()));
            statement.setString(6,budget.getRecurrence());
            statement.setInt(7, budget.setBudgetID(budget.getBudgetType()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isBudgetName(String budgetname) {
        try {
            String query = "SELECT * FROM Budget WHERE budget_type= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, budgetname);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If resultSet.next() is true, it means email exists
        } catch (SQLException e) {
            System.out.println("Error checking budgetname existence: " + e.getMessage());
        }
        return false;
    }

    // Retrieve user ID along with login validation
    public int getUserId(String email, String password) {
        try {
            String query = "SELECT user_id FROM User WHERE Email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if user ID is not found or login fails
    }

    public boolean addExpense(Expense expense) {
        try {
            String sql = "INSERT INTO Expense (user_id, Amount, Recurrence, Expense_type, Description, budget_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, expense.getuserID());
            statement.setDouble(2, expense.getAmount());
            statement.setString(3, expense.getRecurrence());
            statement.setString(4, expense.getBudgetType());
            statement.setString(5, expense.getDescription());
            statement.setInt(6, expense.setBudgetID(expense.getBudgetType()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
