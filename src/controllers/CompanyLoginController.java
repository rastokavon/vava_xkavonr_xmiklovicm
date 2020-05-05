package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CompanyLoginController {
    Parent rootCompany;
    Scene sceneCompany;

    public void init(Stage primaryStage) throws IOException {
        rootCompany = FXMLLoader.load(getClass().getResource("../GUI/CompanyLogin.fxml"));
        sceneCompany = new Scene(rootCompany);
    }

    public void signAsCompanyButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void signAsUserButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void signInButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void notRegisteredButtonCompanyClicked(ActionEvent actionEvent) {
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }
}
