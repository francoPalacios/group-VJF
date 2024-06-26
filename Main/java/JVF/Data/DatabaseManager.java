package JVF.Data;

import JVF.Finances.*;
import JVF.loginui.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String sql = "INSERT INTO Budget (user_id, budget_type, Amount, budget_startdate, budget_enddate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, 1);
            statement.setInt(1, budget.getuserID());
            statement.setString(2, budget.getBudgetName());
            statement.setDouble(3, budget.getBudgetincome());
            statement.setDate(4, Date.valueOf(budget.getBudgetstartdate()));
            statement.setDate(5, Date.valueOf(budget.getBudgetenddate()));
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isBudgetName(int userid, String budgetname) {
        try {
            String query = "SELECT * FROM Budget WHERE user_id=? AND budget_type= ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userid);
            statement.setString(2, budgetname);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If resultSet.next() is true, it means email exists
        } catch (SQLException e) {
            System.out.println("Error checking budgetname existence: " + e.getMessage());
        }
        return false;
    }

    public boolean isBudgetFunding(int userid, int budgetid, int fundingid) {
        try {
            String query = "SELECT * FROM Budget_Funding WHERE user_id= ? AND budget_id=? AND FundingGroup_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userid);
            statement.setInt(2, budgetid);
            statement.setInt(3, fundingid);
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
            String sql = "INSERT INTO Expense (user_id, FoundingGroup_id, Amount, Receipt, Expense_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, 1);
            statement.setInt(1, expense.getuserID());
            statement.setInt(2, expense.getFGID());
            statement.setDouble(3, expense.getAmount());
            statement.setString(4, expense.getReciept());
            statement.setDate(5, Date.valueOf(expense.getExpDate()));


            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFundingGroup(FundingGroup fundingGroup) {
        try {
            String sql = "INSERT INTO FundingGroup (Fundinggroup_name, Fundinggroup_description) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, 1);
            statement.setString(1, fundingGroup.getFGName());
            statement.setString(2, fundingGroup.getFGDes());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addBudgetFunding(BudgetFunding budgetFunding) {
        try {
            String sql = "INSERT INTO Budget_Funding (user_id, budget_id, FundingGroup_id, cash) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, budgetFunding.getUser_id());
            statement.setInt(2, budgetFunding.getBudget_id());
            statement.setInt(3, budgetFunding.getFundingGroup_id());
            statement.setDouble(4, budgetFunding.getAmount());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public ObservableMap<String, Integer> getbudgetType(int user_id) {
        //List<String> data = new ArrayList<>();
        ObservableMap<String, Integer> data = FXCollections.observableHashMap();
        try {
            String sql = "SELECT budget_type, budget_id FROM Budget Where user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String key = resultSet.getString(1);
                Integer value = resultSet.getInt(2);
                //data.add(value);
                data.put(key, value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public Map<Integer, LocalDate[]> getFundingGroupDates(int user_id) {
        Map<Integer, LocalDate[]> data = new HashMap<>();
        try {
            String sql = "SELECT f.FundingGroup_id, b.budget_startdate, b.budget_enddate " +
                    "FROM Budget b " +
                    "JOIN Budget_Funding bf ON b.budget_id = bf.budget_id " +
                    "JOIN FundingGroup f ON bf.FundingGroup_id = f.FundingGroup_id " +
                    "WHERE b.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int fundingGroupId = resultSet.getInt("FundingGroup_id");
                LocalDate[] dates = {
                        resultSet.getDate("budget_startdate").toLocalDate(),
                        resultSet.getDate("budget_enddate").toLocalDate()
                };
                data.put(fundingGroupId, dates);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public ObservableMap<String, Integer> getAllocatedFundingNames(int user_id) {
        ObservableMap<String, Integer> data = FXCollections.observableHashMap();
        try {
            String sql = "SELECT fg.Fundinggroup_name, fg.FundingGroup_id " +
                    "FROM FundingGroup fg " +
                    "JOIN Budget_Funding bf ON fg.FundingGroup_id = bf.FundingGroup_id " +
                    "JOIN Budget b ON bf.budget_id = b.budget_id " +
                    "WHERE b.user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String key = resultSet.getString("Fundinggroup_name");
                Integer value = resultSet.getInt("FundingGroup_id");
                data.put(key, value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ObservableMap<String, Integer> getFundingGroupName() {
        ObservableMap<String, Integer> data = FXCollections.observableHashMap();
        try {
            String sql = "SELECT Fundinggroup_name, FundingGroup_id FROM FundingGroup";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String key = resultSet.getString(1);
                Integer value = resultSet.getInt(2);
                data.put(key, value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public double getCash(int fgID, String budgetDate, int usrID) {
        try {
            String query = """
                    SELECT bf.cash FROM Budget b INNER JOIN Budget_Funding bf ON b.budget_id = bf.budget_id
                    WHERE bf.user_id = ? AND bf.FundingGroup_id = ?
                    AND b.budget_startdate <= ? AND b.budget_enddate >= ?""";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usrID);
            preparedStatement.setInt(2, fgID);
            preparedStatement.setString(3, budgetDate);
            preparedStatement.setString(4, budgetDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getCashleft(int fundingGroupId, String budgetDate, int usrID) {
        try {
            // Assume connection is your database connection object
            PreparedStatement stmt = connection.prepareStatement("""
                    SELECT bf.cash_left FROM Budget b
                    INNER JOIN Budget_Funding bf ON b.budget_id = bf.budget_id
                    WHERE bf.fundingGroup_id = ? AND bf.user_id = ? AND
                    b.budget_startdate <= ? AND b.budget_enddate >= ?""");
            stmt.setInt(1, fundingGroupId);
            stmt.setInt(2, usrID);
            stmt.setString(3, budgetDate);
            stmt.setString(4, budgetDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("cash_left");
            } else {
                return 0.0; // Or handle the case when no result is found
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            return 0.0; // Return a default value or handle the exception as needed
        }
    }


    public double getCashforbudgetcheck(int usrID, int BudgetID) {
        try {
            String query = "SELECT SUM(cash) AS cash FROM Budget_Funding Where user_id = ? AND budget_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usrID);
            preparedStatement.setInt(2, BudgetID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getBudgetamount(int usrID, int BudgetID) {
        try {
            // Assume connection is your database connection object
            PreparedStatement stmt = connection.prepareStatement("""
            SELECT Amount FROM Budget WHERE user_id = ? AND budget_id = ?""");
            stmt.setInt(1, usrID);
            stmt.setInt(2, BudgetID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Amount");
            } else {
                return 0.0; // Or handle the case when no result is found
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            return 0.0; // Return a default value or handle the exception as needed
        }
    }

    public boolean Budgetcheck(int userid) {

        try {
            String query = "SELECT * FROM Budget WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If a row is found, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean BudgetFundingcheck(int userid) {

        try {
            String query = "SELECT * FROM Budget_Funding WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // If a row is found, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setcashleft(int fgID, int usrID, double cash) {
        try {
            String sql = "UPDATE Budget_Funding SET cash_left = ? WHERE user_id = ? AND FundingGroup_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, cash);
            statement.setInt(2, usrID);
            statement.setInt(3, fgID);

            int rowsInserted = statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(Connection connection, int userId) {
        try {
            String query = "DELETE FROM User WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBudget(Budget budget) {
        try {
            String sql = "UPDATE Budget SET budget_type = ?, Amount = ?, budget_startdate = ?, budget_enddate = ? WHERE user_id = ? AND budget_type = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, budget.getBudgetName());
            statement.setDouble(2, budget.getBudgetincome());
            statement.setDate(3, Date.valueOf(budget.getBudgetstartdate()));
            statement.setDate(4, Date.valueOf(budget.getBudgetenddate()));
            statement.setInt(5, budget.getuserID());
            statement.setString(6, budget.getBudgetName()); // Assuming budget_type is unique per user
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Budget> getAllBudgets(int userId) {
        List<Budget> budgets = new ArrayList<>();
        try {
            String sql = "SELECT budget_id, budget_type, Amount, budget_startdate, budget_enddate FROM Budget WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int budgetId = resultSet.getInt("budget_id"); // Assuming you have a budget_id column
                String budgetName = resultSet.getString("budget_type");
                double budgetIncome = resultSet.getDouble("Amount");
                LocalDate startDate = resultSet.getDate("budget_startdate").toLocalDate();
                LocalDate endDate = resultSet.getDate("budget_enddate").toLocalDate();
                Budget budget = new Budget(userId, budgetName, budgetIncome, startDate, endDate);
                budget.setBudgetId(budgetId);
                budgets.add(budget);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return budgets;
    }

}
