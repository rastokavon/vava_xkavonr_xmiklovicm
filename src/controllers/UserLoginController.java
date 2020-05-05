package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;


public class UserLoginController extends Application {
    static Stage loginStage;
    static Parent rootUser;
    static Parent rootCompany;
    static Scene sceneUser;
    static Scene sceneCompany;
    @FXML
    static TextField usernameTextFieldUser;

    @FXML
    static Hyperlink notRegisteredButtonUser;

    @FXML
    static PasswordField passwordTextFieldUser;


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





    @FXML
    public void signInButtonUserClicked() {
        System.out.println("cicka");
        System.out.println(usernameTextFieldUser.getText());
        System.out.println("cicka");


    }
    @FXML
    public void notRegisteredButtonUserClicked(ActionEvent event) {

    }








    public void signAsUserButtonCompanyClicked() throws IOException {
        loginStage.setScene(sceneUser);
    }
    public void signAsCompanyButtonCompanyClicked() {}


    public void signInButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void notRegisteredButtonCompanyClicked(ActionEvent actionEvent) {
    }
}

