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
    TextField usernameTextFieldUser;

    @FXML
    PasswordField passwordTextFieldUser;

    public void startUserController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userLogin.fxml"));

        Scene sceneUserLogin = new Scene(root);


        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    public void signAsCompanyButtonUserClicked() throws Exception {
        primaryStage = (Stage) passwordTextFieldUser.getScene().getWindow();

        CompanyLoginController clc = new CompanyLoginController();
        clc.startCompanyLoginController(primaryStage);
    }

    public void signAsUserButtonUserClicked() {}


    public void signInButtonUserClicked() {
        System.out.println(usernameTextFieldUser.getText());
        System.out.println(passwordTextFieldUser.getText());


    }
    public void notRegisteredButtonUserClicked() throws Exception {
        primaryStage = (Stage) passwordTextFieldUser.getScene().getWindow();
        UserRegistrationController urc = new UserRegistrationController();
        urc.startUserRegistrationController(primaryStage);
    }

    public void signAsCompanyButtonCompanyClicked() {}


    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }
}

