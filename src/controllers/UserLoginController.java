package controllers;

import database.ProgramData;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class UserLoginController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;
    @FXML
    Button signAsCompanyButton;
    @FXML
    Button signAsUserButton;
    @FXML
    Button signInButton;
    @FXML
    Hyperlink notRegisteredButton;
    @FXML
    Label welcomeLabel;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userLogin.fxml"));

        //setLanguage();

        Scene sceneUserLogin = new Scene(root);

        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }
    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =	ResourceBundle.getBundle(bundle, Locale.forLanguageTag("login"));
        welcomeLabel.setText(rbSk.getString("userLogin.welcome"));
        signInButton.setText(rbSk.getString("login.signInButton"));
        signAsCompanyButton.setText(rbSk.getString("login.logAsCompany"));
        signAsUserButton.setText(rbSk.getString("login.logAsUser"));
        passwordTextField.setPromptText(rbSk.getString("login.password"));
        usernameTextField.setPromptText(rbSk.getString("userLogin.username"));
        notRegisteredButton.setText(rbSk.getString("userLogin.notRegistered"));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userLogin.window"));
    }

    public void signAsCompanyButtonClicked() throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new CompanyLoginController();
        clc.startController(primaryStage);
    }

    public void signAsUserButtonClicked() {}


    public void signInButtonClicked() {
        System.out.println(usernameTextField.getText());
        System.out.println(passwordTextField.getText());


    }
    public void notRegisteredButtonClicked() throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller urc = new UserRegistrationController();
        urc.startController(primaryStage);
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

