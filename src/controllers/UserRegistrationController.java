package controllers;

import managers.ManagerCompany;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.ManagerPerson;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRegistrationController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField roomNumberTextField;
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
    @FXML
    Label welcomeLabel;
    @FXML
    Hyperlink backButton;
    @FXML
    Button signUpButton;
    @FXML
    Label obligatoryLabel;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userRegistration.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =	ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));
        welcomeLabel.setText(rbSk.getString("userReg.welcome"));
        firstNameTextField.setPromptText(rbSk.getString("userReg.firstName"));
        lastNameTextField.setPromptText(rbSk.getString("userReg.lastName"));
        usernameTextField.setPromptText(rbSk.getString("userReg.username"));
        passwordTextField.setPromptText(rbSk.getString("userReg.password"));
        roomNumberTextField.setPromptText(rbSk.getString("userReg.roomNumber"));
        mailTextField.setPromptText(rbSk.getString("reg.mail"));
        phoneNumberTextField.setPromptText(rbSk.getString("reg.phoneNumber"));
        obligatoryLabel.setText(rbSk.getString("reg.obligatory"));
        backButton.setText(rbSk.getString("reg.back"));
        signUpButton.setText(rbSk.getString("reg.signUp"));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userReg.window"));
    }

    public void backButtonClicked() throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller ulc = new UserLoginController();
        ulc.startController(primaryStage);
    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws Exception {
        Logger LOG = ProgramData.getLOG();

        try {
            Integer.parseInt(roomNumberTextField.getText());
        } catch (Exception e) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));

            LOG.log(Level.WARNING, "Room number musi byt typu int.");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("userReg.title"));
            alert.setContentText(rbSk.getString("userReg.missingRoomNumber"));
            alert.showAndWait();
            return;
        }
        StringBuffer message = ManagerPerson.createPerson(firstNameTextField.getText(), lastNameTextField.getText(),
                usernameTextField.getText(),passwordTextField.getText(), mailTextField.getText(),
                phoneNumberTextField.getText(),Integer.parseInt(roomNumberTextField.getText()));
        if (message.length() == 0) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller ulc = new UserLoginController();
            ulc.startController(primaryStage);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk.getString("companyReg.title"));
            alert.setContentText(rbSk.getString("userReg.text"));
            alert.showAndWait();
        } else {
            String bundle1 = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk1 = ResourceBundle.getBundle(bundle1 + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk1.getString("userReg.title"));
            alert.setContentText(String.valueOf(message) + "    ");
            alert.showAndWait();
        }
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();
    }
}
