package com.example.dashboardui;

import com.example.DataSingleton;
import com.example.loginui.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.IOException;

public class DashboardController {

    public Button settingsButton;
    DatabaseManager Dbmanage = new DatabaseManager();


    private int userId;
    @FXML
    private Button addBudgetButton;

    @FXML
    void initialize() {
        DataSingleton data = DataSingleton.getInstance();
        addBudgetButton.setOnAction(event -> openAddBudgetWindow(data.getUserId()));

        settingsButton.setOnAction(event -> openSettingsWindow(data.getUserId()));


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



    private void openSettingsWindow(int userId) {
        try {
            // Create settings instance with userId
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.dashboardui/Settings.fxml"));
            Parent root = loader.load();
            SettingsController controller = loader.getController();

            // Show the stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Settings");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



