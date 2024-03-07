package com.example.loginui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.example.loginui/hello-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
