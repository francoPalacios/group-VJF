package com.example.dashboardui;

import com.example.DataSingleton;
import com.example.loginui.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DashboardController {

    DatabaseManager Dbmanage = new DatabaseManager();


    private int userId;
    @FXML
    private Button addBudgetButton;

    @FXML
    private Button ExpenseButton;
    @FXML
    private Button TransactionButton;
    @FXML
    private Button GraphsChartsButton;
    @FXML
    private Button SettingsButton;

    @FXML
    void initialize() {
        DataSingleton data = DataSingleton.getInstance();
        DatabaseManager databaseManager = new DatabaseManager();
        if (!databaseManager.isBudgetExisting(data.getUserId())){
            ExpenseButton.setVisible(false);
            TransactionButton.setVisible(false);
            GraphsChartsButton.setVisible(false);
            SettingsButton.setVisible(false);
        }else if(databaseManager.isBudgetExisting(data.getUserId())){
                ExpenseButton.setVisible(true);
                TransactionButton.setVisible(true);
                GraphsChartsButton.setVisible(true);
                SettingsButton.setVisible(true);
        }
        addBudgetButton.setOnAction(event -> openAddBudgetWindow(data.getUserId()));


  /*  if (!(data.isHasBudget())) {
        ExpenseButton.setVisible(false);
        TransactionButton.setVisible(false);
        GraphsChartsButton.setVisible(false);
        SettingsButton.setVisible(false);
    } else if(data.isHasBudget()) {
            ExpenseButton.setVisible(true);
            TransactionButton.setVisible(false);
            GraphsChartsButton.setVisible(false);
            SettingsButton.setVisible(false);
        } else if(data.isHasBudget() && !data.isHasExpense()) {
        ExpenseButton.setVisible(true);
            TransactionButton.setVisible(true);
            GraphsChartsButton.setVisible(true);
            SettingsButton.setVisible(true);


    }*/



    }

    public void refresh() {
       ExpenseButton.setVisible(true);
    }

    private void openAddBudgetWindow(int userId) {
        try {
            // Create BudgetController instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.dashboardui/addbudget.fxml"));
            Parent root = loader.load();
            BudgetController controller = loader.getController();
            controller.setUserId(userId);
            DataSingleton data = DataSingleton.getInstance();
         //   data.setUserId(userId);
            data.setController(this);

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
}
