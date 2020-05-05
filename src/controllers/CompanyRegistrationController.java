package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CompanyRegistrationController {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField nameTextField;
    @FXML
    TextField streetTextField;
    @FXML
    TextField cityTextField;
    @FXML
    TextField countryTextField;
    @FXML
    TextField postalCodeTextField;
    @FXML
    TextField mailTextField;
    @FXML
    TextField phoneNumberTextField;

    public void startCompanyRegistrationController(Stage stage) throws Exception {
        primaryStage = stage;
        System.out.println(10);
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/companyRegistration.fxml"));
        System.out.println(10);
        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) cityTextField.getScene().getWindow();

        CompanyLoginController clc = new CompanyLoginController();
        clc.startCompanyLoginController(primaryStage);
    }

    public void signUpButtonClicked(ActionEvent actionEvent) {
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }
}
