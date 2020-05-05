package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;


public class UserLoginController {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    public void startUserController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userLogin.fxml"));

        Scene sceneUserLogin = new Scene(root);


        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    public void signAsCompanyButtonClicked() throws Exception {
        primaryStage = (Stage) passwordTextField.getScene().getWindow();

        CompanyLoginController clc = new CompanyLoginController();
        clc.startCompanyLoginController(primaryStage);
    }

    public void signAsUserButtonClicked() {}


    public void signInButtonClicked() {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());


    }
    public void notRegisteredButtonClicked() throws Exception {
        primaryStage = (Stage) passwordTextField.getScene().getWindow();
        UserRegistrationController urc = new UserRegistrationController();
        urc.startUserRegistrationController(primaryStage);
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        System.out.println(10);
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }
}

