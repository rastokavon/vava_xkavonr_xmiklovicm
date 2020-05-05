package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

public class CompanyLoginController {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField usernameTextFieldCompany;

    @FXML
    TextField passwordTextFieldCompany;

    public void startCompanyLoginController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/companyLogin.fxml"));

        Scene sceneCompanyLogin = new Scene(root);

        primaryStage.setScene(sceneCompanyLogin);
        primaryStage.show();
    }

    public void signAsCompanyButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void signAsUserButtonCompanyClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) passwordTextFieldCompany.getScene().getWindow();

        UserLoginController ulc = new UserLoginController();
        ulc.startUserController(primaryStage);
    }

    public void signInButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void notRegisteredButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

}
