package com.example.dashboardui;

import com.example.Data.DataSingleton;
import com.example.Finances.BudgetController;
import com.example.Data.DatabaseManager;
import com.example.Finances.ExpensesController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    DatabaseManager Dbmanage = new DatabaseManager();


    private int userId;
    @FXML
    private Button addBudgetButton;
    @FXML
    private Button addExpenseButton;

    @FXML
    void initialize() {
        DataSingleton data = DataSingleton.getInstance();
        addBudgetButton.setOnAction(event -> openAddBudgetWindow(data.getUserId()));
        addExpenseButton.setOnAction(event -> openAddExpensesWindow(data.getUserId()));
    }

    private void openAddBudgetWindow(int userId) {
        try {
            // Create BudgetController instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.dashboardui/addbudget.fxml"));
            Parent root = loader.load();
            BudgetController controller = loader.getController();
            controller.setUserId(userId);

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Budget");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAddExpensesWindow(int userId) {
        try {
            // Create ExpenseController instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.dashboardui/addexpense.fxml"));
            Parent root = loader.load();
            ExpensesController controller = loader.getController();
            controller.setUserId(userId);

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Expense");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
