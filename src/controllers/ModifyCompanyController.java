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

import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyCompanyController implements Controller {
    Stage primaryStage;
    Parent root;

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
    @FXML
    Hyperlink backButton;
    @FXML
    Label welcomeLabel;
    @FXML
    Label addressLabel;
    @FXML
    Label contactLabel;
    @FXML
    Button confirmButton;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(ModifyCompanyController.class.getResource("../GUI/ModifyCompany.fxml"));

        Scene scene = new Scene(root);

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        primaryStage.setTitle(rbSk.getString("companyModify.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize() {

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        welcomeLabel.setText(rbSk.getString("companyModify.mainLabel"));
        streetTextField.setPromptText(rbSk.getString("companyReg.street"));
        cityTextField.setPromptText(rbSk.getString("companyReg.city"));
        countryTextField.setPromptText(rbSk.getString("companyReg.country"));
        postalCodeTextField.setPromptText(rbSk.getString("companyReg.postalCode"));
        mailTextField.setPromptText(rbSk.getString("reg.mail"));
        phoneNumberTextField.setPromptText(rbSk.getString("reg.phoneNumber"));
        addressLabel.setText(rbSk.getString("companyReg.address"));
        contactLabel.setText(rbSk.getString("companyReg.contact"));
        backButton.setText(rbSk.getString("companyModify.back"));
        confirmButton.setText(rbSk.getString("companyModify.confirm"));
        Company company = ProgramData.getInstance().getCompany();
        streetTextField.setText(company.getStreet());
        cityTextField.setText(company.getCity());
        countryTextField.setText(company.getCountry());
        postalCodeTextField.setText(company.getPostalCode());
        mailTextField.setText(company.getMail());
        phoneNumberTextField.setText(company.getPhoneNumber());
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = (Stage) contactLabel.getScene().getWindow();
        primaryStage.close();

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        Controller crc = new CompanyMainWindowController();
        crc.startController(primaryStage);
    }

    public void confirmButtonClicked(ActionEvent actionEvent) throws Exception {
        Company company = ProgramData.getInstance().getCompany();
        StringBuffer message = ManagerCompany.updateCompany(company, streetTextField.getText(), cityTextField.getText(),
                countryTextField.getText(), postalCodeTextField.getText(), mailTextField.getText(),
                phoneNumberTextField.getText());

        if (message.length() == 0) {
            primaryStage = (Stage) contactLabel.getScene().getWindow();
            primaryStage.close();

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller clc = new CompanyMainWindowController();
            clc.startController(primaryStage);

        } else {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyModify.title"));
            alert.setContentText(String.valueOf(message));
            alert.showAndWait();
        }
    }
}
