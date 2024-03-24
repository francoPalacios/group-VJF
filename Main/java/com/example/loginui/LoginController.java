//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.loginui;

import java.awt.Component;
import java.io.IOException;

import com.example.Data.DataSingleton;
import com.example.Data.DatabaseManager;
import com.example.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

//import javax.swing.*;
//import javax.swing.JOptionPane;

public class LoginController extends Component {
    private Stage mainStage;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    private final DatabaseManager databaseManager = new DatabaseManager();

    private int userId;

    public LoginController() {
    }

    @FXML
    void initialize() {
        this.mainStage = Main.getMainStage();
        this.loginButton.setOnAction((event) -> {
            String email = this.emailField.getText();
            String password = this.passwordField.getText();
            Alert alert;
            if (!email.isEmpty() && !password.isEmpty()) {
                if (this.databaseManager.validateUser(email, password)) {
                    System.out.println("Login successful");

                    Alert confirm = new Alert(AlertType.INFORMATION, "Login Successful");
                    confirm.showAndWait();

                    openDashboardWindow(databaseManager.getUserId(email,password));
                    //Stage loginStage = (Stage)this.loginButton.getScene().getWindow();
                    //loginStage.close();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: Please Fill In All Fields");
                alert.showAndWait();
            }
        });
        this.registerButton.setOnAction((event) -> {
            this.openRegisterWindow();
        });
    }

    public void openDashboardWindow(int userId) {
        DataSingleton data = DataSingleton.getInstance();
        data.setUserId(userId);
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com.example.dashboardui/Dashboard.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Dashboard");
            mainStage.setResizable(false);
            mainStage.show();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
    }


    private void openRegisterWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com.example.loginui/Register.fxml"));
            Parent root = loader.load();
            mainStage.setScene(new Scene(root));
            mainStage.setTitle("Register");
            mainStage.setResizable(false);
            mainStage.show();
            //Stage loginStage = (Stage)this.loginButton.getScene().getWindow();
            //loginStage.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }
}
