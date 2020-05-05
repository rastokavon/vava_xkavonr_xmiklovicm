package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserRegistrationController {
    static Stage primaryStage;
    static Parent root;
    static Scene sceneUserRegistration;

    
    public static void startUserRegistrationController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/UserRegistration.fxml"));

        sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
    }
    
    
    public void signInButtonClicked(ActionEvent actionEvent) {
    }
}
