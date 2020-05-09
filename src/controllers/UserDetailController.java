package controllers;

import database.Company;
import database.Person;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import managers.ManagerPerson;

import java.util.Locale;
import java.util.ResourceBundle;

public class UserDetailController implements Controller {
    Stage primaryStage;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Person person;

    @FXML
    Label firstNameLabel;
    @FXML
    Label secondNameLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label mailLabel;
    @FXML
    Label firstNameLabelUser;
    @FXML
    Label secondNameLabelUser;
    @FXML
    Label usernameLabelUser;
    @FXML
    Label mailLabelUser;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;

        setLabels();
    }


    @FXML
    public void initialize() {

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        firstNameLabel.setText(rbSk.getString("userReg.firstName") + ":");
        secondNameLabel.setText(rbSk.getString("userReg.lastName") + ":");
        usernameLabel.setText(rbSk.getString("userReg.username") + ":");
        mailLabel.setText(rbSk.getString("reg.mail") + ":");
    }

    public void confirmButtonClicked(ActionEvent actionEvent) {
        primaryStage = (Stage) firstNameLabel.getScene().getWindow();
        primaryStage.close();
    }


    public void setLabels() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        firstNameLabelUser.setText(person.getFirstName());
        secondNameLabelUser.setText(person.getLastName());
        usernameLabelUser.setText(person.getUsername());
        mailLabelUser.setText(person.getUsername());

        primaryStage = (Stage) firstNameLabel.getScene().getWindow();
        primaryStage.setTitle(rbSk.getString("userDetail.title"));
    }
}
