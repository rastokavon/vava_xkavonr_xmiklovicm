package controllers;

import database.Person;
import main.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * po kliknuti na daneho pouzivatela sa zobrazi male okno, kde je mozne vidiet jeho detail
 *
 * je mozne prezerat si jeho osobne udaje a pod...
 */
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
    public void startController(Stage stage) {
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

    /**
     * zavrie sa okno, ktore zobrazuje detail pouzivatela
     *
     * @param actionEvent
     */
    public void confirmButtonClicked(ActionEvent actionEvent) {
        primaryStage = (Stage) firstNameLabel.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * vyplni polia s informaciami o pouzivatelovi
     */
    public void setLabels() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));

        firstNameLabelUser.setText(person.getFirstName());
        secondNameLabelUser.setText(person.getLastName());
        usernameLabelUser.setText(person.getUsername());
        mailLabelUser.setText(person.getMail());

        primaryStage = (Stage) firstNameLabel.getScene().getWindow();
        primaryStage.setTitle(rbSk.getString("userDetail.title"));
    }
}
