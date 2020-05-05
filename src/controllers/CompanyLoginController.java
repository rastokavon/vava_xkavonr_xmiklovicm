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
    TextField usernameTextField;

    @FXML
    TextField passwordTextField;

    public void startCompanyLoginController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/companyLogin.fxml"));

        Scene sceneCompanyLogin = new Scene(root);

        primaryStage.setScene(sceneCompanyLogin);
        primaryStage.show();
    }

    public void signAsCompanyButtonClicked(ActionEvent actionEvent) {
    }

    public void signAsUserButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) passwordTextField.getScene().getWindow();

        UserLoginController ulc = new UserLoginController();
        ulc.startUserController(primaryStage);
    }

    public void signInButtonClicked(ActionEvent actionEvent) {
    }

    public void notRegisteredButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) passwordTextField.getScene().getWindow();

        CompanyRegistrationController crc = new CompanyRegistrationController();
        crc.startCompanyRegistrationController(primaryStage);
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        System.out.println(100);
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

}
