package controllers;

import database.Company;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.ManagerCompany;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * okno, ktore sluzi pre pihlasenie spolocnosti
 * po prihlaseni sa "vstupi" do miestnosti danej spolocnosti
 * je mozne kliknut na link, ktory otvori registracne okno
 *
 * zadava sa ID miestnosti a heslo
 */
public class CompanyLoginController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
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
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/companyLogin.fxml"));

        Scene sceneCompanyLogin = new Scene(root);

        primaryStage.setScene(sceneCompanyLogin);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("login"));
        welcomeLabel.setText(rbSk.getString("companyLogin.welcome"));
        signInButton.setText(rbSk.getString("login.signInButton"));
        signAsCompanyButton.setText(rbSk.getString("login.logAsCompany"));
        signAsUserButton.setText(rbSk.getString("login.logAsUser"));
        passwordTextField.setPromptText(rbSk.getString("login.password"));
        usernameTextField.setPromptText(rbSk.getString("companyLogin.roomNumber"));
        notRegisteredButton.setText(rbSk.getString("companyLogin.notRegistered"));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("companyLogin.window"));
    }

    public void signAsCompanyButtonClicked(ActionEvent actionEvent) {
    }

    public void signAsUserButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller ulc = new UserLoginController();
        ulc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Otvorenie prihlasenia pouzivatela.");
    }

    public void signInButtonClicked(ActionEvent actionEvent) throws Exception {
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(usernameTextField.getText());
        } catch (Exception e) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.WARNING, "Cislo miestnosti musi byt typu int.");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyLogin.title"));
            alert.setContentText(rbSk.getString("userReg.missingRoomNumber"));
            alert.showAndWait();
            return;
        }
        Company company = ManagerCompany.isRegistered(roomNumber, passwordTextField.getText());
        if (company == null) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.WARNING, "Cislo miestnosti/heslo je neplatne");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyLogin.title"));
            alert.setContentText(rbSk.getString("companyLogin.text"));
            alert.showAndWait();
        } else {
            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.INFO, "Firma " + company.getName() + " bola uspesne prihlasena.");

            ProgramData.getInstance().setUser(null);
            ProgramData.getInstance().setCompany(company);
            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller crc = new CompanyMainWindowController();
            crc.startController(primaryStage);
        }
    }

    public void notRegisteredButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller crc = new CompanyRegistrationController();
        crc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Otvorenie registracie firmy.");
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na slovencinu.");
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na anglictinu.");
    }
}
