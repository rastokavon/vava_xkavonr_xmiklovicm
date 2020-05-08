package controllers;

import database.Company;
import database.Person;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managers.ManagerCompany;
import managers.ManagerPerson;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePasswordController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    PasswordField oldPasswordTextField;
    @FXML
    PasswordField newPasswordTextField;
    @FXML
    PasswordField repeatNewPasswordTextField;
    @FXML
    Label newPasswordLabel;
    @FXML
    Label repeatNewPasswordLabel;
    @FXML
    Label oldPasswordLabel;
    @FXML
    Label changePasswordLabel;
    @FXML
    Button confirmButton;
    @FXML
    Hyperlink backButton;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = new Stage();
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/ChangePassword.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_mainCom", Locale.forLanguageTag("changeP"));

        primaryStage.setTitle(rbSk.getString("changePass.title"));
        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }
    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_mainCom", Locale.forLanguageTag("changeP"));

        newPasswordLabel.setText(rbSk.getString("changePass.newPassLabel"));
        repeatNewPasswordLabel.setText(rbSk.getString("changePass.repeatNewPassLabel"));
        oldPasswordLabel.setText(rbSk.getString("changePass.oldPassLabel"));
        newPasswordTextField.setPromptText(rbSk.getString("changePass.newPassField"));
        oldPasswordTextField.setPromptText(rbSk.getString("changePass.oldPassField"));
        repeatNewPasswordTextField.setPromptText(rbSk.getString("changePass.repeatNewPassField"));
        changePasswordLabel.setText(rbSk.getString("changePass.mainLabel"));
        confirmButton.setText(rbSk.getString("changePass.confirmButton"));
        backButton.setText(rbSk.getString("changePass.backButton"));

    }

    public void backButtonClicked(ActionEvent actionEvent) {
        primaryStage = (Stage) changePasswordLabel.getScene().getWindow();
        primaryStage.close();
    }

    public void confirmButtonClicked(ActionEvent actionEvent) {
        Person user = ProgramData.getInstance().getUser();
        Company company = ProgramData.getInstance().getCompany();

        if (user == null) {
            if (company.getPassword().equals(oldPasswordTextField.getText())) {
                if (newPasswordTextField.getText().equals(repeatNewPasswordTextField.getText()) &&
                        !newPasswordTextField.getText().equals("")) {

                    ManagerCompany.changePassword(company, newPasswordTextField.getText());
                    primaryStage = (Stage) changePasswordLabel.getScene().getWindow();
                    primaryStage.close();

                    String bundle = ProgramData.getInstance().getLanguage();
                    ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rbSk.getString("changePass.title"));
                    alert.setContentText(rbSk.getString("changePass.text"));
                    alert.showAndWait();
                } else {
                    String bundle = ProgramData.getInstance().getLanguage();
                    ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

                    Logger LOG = ProgramData.getLOG();
                    LOG.log(Level.WARNING, "Nove hesla sa nezhoduju");


                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rbSk.getString("changePass.title"));
                    alert.setContentText(rbSk.getString("changePass.unmatchingPass"));
                    alert.showAndWait();
                }
            } else {
                String bundle = ProgramData.getInstance().getLanguage();
                ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

                Logger LOG = ProgramData.getLOG();
                LOG.log(Level.WARNING, "Stare heslo je neplatne");


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rbSk.getString("changePass.title"));
                alert.setContentText(rbSk.getString("changePass.wrongPass"));
                alert.showAndWait();
            }
        }
        else {
            if (user.getPassword().equals(oldPasswordTextField.getText())) {
                if (newPasswordTextField.getText().equals(repeatNewPasswordTextField.getText()) &&
                        !newPasswordTextField.getText().equals("")) {

                    ManagerPerson.changePassword(user, newPasswordTextField.getText());
                    primaryStage = (Stage) changePasswordLabel.getScene().getWindow();
                    primaryStage.close();

                    String bundle = ProgramData.getInstance().getLanguage();
                    ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(rbSk.getString("changePass.title"));
                    alert.setContentText(rbSk.getString("changePass.text"));
                    alert.showAndWait();
                } else {
                    String bundle = ProgramData.getInstance().getLanguage();
                    ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

                    Logger LOG = ProgramData.getLOG();
                    LOG.log(Level.WARNING, "Nove hesla sa nezhoduju");


                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(rbSk.getString("changePass.title"));
                    alert.setContentText(rbSk.getString("changePass.unmatchingPass"));
                    alert.showAndWait();
                }
            } else {
                String bundle = ProgramData.getInstance().getLanguage();
                ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

                Logger LOG = ProgramData.getLOG();
                LOG.log(Level.WARNING, "Stare heslo je neplatne");


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rbSk.getString("changePass.title"));
                alert.setContentText(rbSk.getString("changePass.wrongPass"));
                alert.showAndWait();
            }
        }
    }
}
