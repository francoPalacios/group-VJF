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

public class RegisterController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;
    @FXML
    private Button backToLoginButton;

    private final DatabaseManager databaseManager;

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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("User Added");
                alert.setHeaderText(null);
                alert.setContentText("User Added Successfully");
                alert.showAndWait();
                openLoginWindow();
            } else {
                System.out.println("Failed to add user");
            }
        });

        backToLoginButton.setOnAction(event -> openLoginWindow());
    }

    private void openLoginWindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.loginui/hello-view.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();

            // Close the login window
            Stage loginStage = (Stage) registerButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
