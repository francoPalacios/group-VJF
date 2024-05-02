package com.example.dashboardui;
import com.example.DataSingleton;
import com.example.loginui.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class SettingsController {
    @FXML
    private Button logout;
    @FXML
    private Button deleteProfile;
    @FXML
    private Button mainMenu;

    private int userId;

    public SettingsController() {
        userId = DataSingleton.getInstance().getUserId();
    }

    @FXML
    void initialize() {
        // You can initialize anything here if needed
    }

    @FXML
    void handleLogout(ActionEvent event) {
        closeAllWindows();

    }

    @FXML
     void handleDeleteProfile(ActionEvent event){
        deleteProfile();

    }


    private void closeAllWindows() {
        List<Stage> openStages = Stage.getWindows()
                .stream()
                .filter(Window::isShowing)
                .map(window -> (Stage) window)
                .collect(Collectors.toList());


        openStages.forEach((window) -> {
            if (window.getTitle().equals("Dashboard")) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com.example.loginui/hello-view.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.err.println("ERROR LOADING LOGIN");
                }
                window.setScene(new Scene(root));
                window.setTitle("Login");
                window.setResizable(false);
                window.show();
            } else {
                window.close();
            }
        });

    }




    private void deleteProfile(){
        if(DatabaseManager.deleteUser(userId)) {
            // Logout of the system
            System.out.println("logged out");
            closeAllWindows();
        }

    }

}


