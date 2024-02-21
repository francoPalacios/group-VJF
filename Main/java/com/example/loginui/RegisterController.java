package com.example.loginui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    private DatabaseManager databaseManager;

    public RegisterController() {
        databaseManager = new DatabaseManager();
    }

    @FXML
    void initialize() {
        registerButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            User newUser = new User(email, password);

            if (databaseManager.addUser(newUser)) {
                System.out.println("User added successfully");
                closeRegisterWindow();
            } else {
                System.out.println("Failed to add user");
            }
        });
    }

    private void closeRegisterWindow() {
        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.close();
    }
}
