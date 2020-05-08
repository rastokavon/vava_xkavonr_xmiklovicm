package controllers;

import database.Company;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import managers.ManagerCompany;
import managers.ManagerPerson;

import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyPersonController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    Hyperlink backButton;
    @FXML
    Label welcomeLabel;
    @FXML
    Label nameLabel;
    @FXML
    Label contactLabel;
    @FXML
    Button confirmButton;
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField surnameTextField;
    @FXML
    TextField mailTextField;
    @FXML
    TextField phoneNumberTextField;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(ModifyCompanyController.class.getResource("../GUI/ModifyPerson.fxml"));
        Scene scene = new Scene(root);

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        primaryStage.setTitle(rbSk.getString("personModify.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        welcomeLabel.setText(rbSk.getString("personModify.mainLabel"));
        nameLabel.setText(rbSk.getString("personModify.name"));
        contactLabel.setText(rbSk.getString("companyReg.contact"));

        firstNameTextField.setPromptText(rbSk.getString("personModify.firstName"));
        surnameTextField.setPromptText(rbSk.getString("personModify.secondName"));
        mailTextField.setPromptText(rbSk.getString("reg.mail"));
        phoneNumberTextField.setPromptText(rbSk.getString("reg.phoneNumber"));

        firstNameTextField.setText(ProgramData.getInstance().getUser().getFirstName());
        surnameTextField.setText(ProgramData.getInstance().getUser().getLastName());
        mailTextField.setText(ProgramData.getInstance().getUser().getMail());
        phoneNumberTextField.setText(ProgramData.getInstance().getUser().getPhoneNumber());

        backButton.setText(rbSk.getString("companyModify.back"));
        confirmButton.setText(rbSk.getString("companyModify.confirm"));
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) contactLabel.getScene().getWindow();
        primaryStage.close();

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        Controller crc = new UserInformationController();
        crc.startController(primaryStage);
    }

    public void confirmButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerPerson.updatePerson(ProgramData.getInstance().getUser(),
                firstNameTextField.getText(), surnameTextField.getText(), mailTextField.getText(),
                phoneNumberTextField.getText());

        if (message.length() == 0) {
            primaryStage = (Stage) contactLabel.getScene().getWindow();
            primaryStage.close();

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller clc = new UserInformationController();
            clc.startController(primaryStage);

        }
        else {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyModify.title"));
            alert.setContentText(String.valueOf(message));
            alert.showAndWait();
        }
    }
}
