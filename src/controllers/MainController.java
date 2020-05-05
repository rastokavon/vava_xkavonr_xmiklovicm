package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class MainController extends Application {


    protected Stage primaryStage;
    Parent rootUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        rootUser = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userLogin.fxml"));

        Scene sceneUserLogin = new Scene(rootUser);

        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


}
