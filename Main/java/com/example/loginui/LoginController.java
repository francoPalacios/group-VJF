package com.example.loginui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private final DatabaseManager databaseManager;

    public LoginController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("Error: Please Fill In All Fields");
                alExiAlert.showAndWait();
                return;
            }

            if (databaseManager.validateUser(email, password)) {
                System.out.println("Login successful");
                // Perform further actions after successful login
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        });

        registerButton.setOnAction(event -> {
            // Open the register window
            openRegisterWindow();
        });
    }

    private void openRegisterWindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.loginui/Register.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.setResizable(false);
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
