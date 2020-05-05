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

public class UserRegistrationController {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField mailTextField;
    @FXML
    TextField phoneNumberTextField;
    
    public void startUserRegistrationController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userRegistration.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }
    
    
    public void signInButtonClicked(ActionEvent actionEvent) {
    }

    public void backButtonClicked() throws Exception {
        primaryStage = (Stage) passwordTextField.getScene().getWindow();

        UserLoginController ulc = new UserLoginController();
        ulc.startUserController(primaryStage);
    }

    public void signUpButtonClicked(ActionEvent actionEvent) {
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
    }
}
