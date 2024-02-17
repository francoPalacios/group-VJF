package com.example.loginui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private DatabaseManager databaseManager;

    public LoginController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (databaseManager.validateUser(email, password)) {
                System.out.println("Login successful");
                // Perform further actions after successful login
            } else {
                System.out.println("Invalid email or password");
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
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
