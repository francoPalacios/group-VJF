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

    private Stage mainStage;
    public TextField firstNameField;
    public TextField lastNameField;
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
        this.mainStage = Main.getMainStage();
        registerButton.setOnAction(event -> {

            String email = emailField.getText();
            String password = passwordField.getText();
            String firstname = firstNameField.getText();
            String lastname = lastNameField.getText();

            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("Error: Please Fill In All Fields");
                alExiAlert.showAndWait();
                return;
            }

            // Check if the email already exists in the database
            if (databaseManager.isEmailExists(email)) {
                Alert alExiAlert = new Alert(Alert.AlertType.ERROR);
                alExiAlert.setTitle("Error");
                alExiAlert.setHeaderText(null);
                alExiAlert.setContentText("Email Is Already In Use");
                alExiAlert.showAndWait();
            } else {

                User newUser = new User(email, password, firstname, lastname);

                if (databaseManager.addUser(newUser)) {
                    Alert addedAlert = new Alert(Alert.AlertType.INFORMATION);
                    addedAlert.setTitle("User Added");
                    addedAlert.setHeaderText(null);
                    addedAlert.setContentText("User Added Successfully");
                    addedAlert.showAndWait();
                    openLoginWindow();
                } else {
                    System.out.println("Failed to add user");
                }
            }});

            backToLoginButton.setOnAction(event -> openLoginWindow());
    }

    private void openLoginWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.loginui/hello-view.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Login");
            mainStage.setResizable(false);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
