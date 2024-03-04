//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.loginui;

import java.awt.Component;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class LoginController extends Component {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private final DatabaseManager databaseManager = new DatabaseManager();

    public LoginController() {
    }

    @FXML
    void initialize() {
        this.loginButton.setOnAction((event) -> {
            String email = this.emailField.getText();
            String password = this.passwordField.getText();
            Alert alert;
            if (!email.isEmpty() && !password.isEmpty()) {
                if (this.databaseManager.validateUser(email, password)) {
                    System.out.println("Login successful");
                    JOptionPane.showMessageDialog(this, "Login Successful", "Success", 1);
                    this.openBudgetwindow();
                    Stage loginStage = (Stage)this.loginButton.getScene().getWindow();
                    loginStage.close();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText((String)null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText((String)null);
                alert.setContentText("Error: Please Fill In All Fields");
                alert.showAndWait();
            }
        });
        this.registerButton.setOnAction((event) -> {
            this.openRegisterWindow();
        });
    }

    private void openRegisterWindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com.example.loginui/Register.fxml"));
            Parent root = (Parent)loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Register");
            stage.setResizable(false);
            stage.show();
            Stage loginStage = (Stage)this.loginButton.getScene().getWindow();
            loginStage.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private void openBudgetwindow() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com.example.loginui/addbudget.fxml"));
            Parent root = (Parent)loader.load();
            stage.setScene(new Scene(root));
            stage.setTitle("Budget");
            stage.setResizable(false);
            stage.show();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }
}
