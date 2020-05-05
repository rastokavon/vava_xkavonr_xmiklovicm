package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;


public class UserLoginController extends Application {
    static Stage loginStage;
    static Parent rootUser;
    static Parent rootCompany;
    static Scene sceneUser;
    static Scene sceneCompany;

    @FXML
    TextField usernameTextFieldUser;

    @FXML
    PasswordField passwordTextFieldUser;

    @FXML
    TextField usernameTextFieldCompany;

    @FXML
    PasswordField passwordTextFieldCompany;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loginStage = primaryStage;

        rootUser = FXMLLoader.load(getClass().getResource("../GUI/UserLogin.fxml"));
        rootCompany = FXMLLoader.load(getClass().getResource("../GUI/CompanyLogin.fxml"));

        sceneUser = new Scene(rootUser);
        sceneCompany = new Scene(rootCompany);

        loginStage.setScene(sceneUser);
        loginStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void signAsCompanyButtonUserClicked() throws Exception {
        loginStage.setScene(sceneCompany);
    }
    public void signAsUserButtonUserClicked() {}


    public void signInButtonUserClicked() {
        System.out.println(usernameTextFieldUser.getText());
        System.out.println(passwordTextFieldUser.getText());


    }
    public void notRegisteredButtonUserClicked() throws Exception {
        UserRegistrationController.startUserRegistrationController(loginStage);

    }



    public void signAsUserButtonCompanyClicked() throws IOException {
        loginStage.setScene(sceneUser);
    }
    public void signAsCompanyButtonCompanyClicked() {}


    public void signInButtonCompanyClicked(ActionEvent actionEvent) {
        System.out.println(usernameTextFieldCompany.getText());
        System.out.println(passwordTextFieldCompany.getText());
    }

    public void notRegisteredButtonCompanyClicked() throws Exception {
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }
}

