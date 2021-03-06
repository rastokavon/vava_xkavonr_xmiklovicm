package controllers;

import database.Person;
import main.ProgramData;
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


/**
 * prve okno, ktore sa zobrazi po spusteni aplikacie
 * je mozne zadat prihlasovacie udaje pouzivatela a otvori sa jeho hlavne menu
 *
 * je mozne sa dostat do inych prihlaseni/registracii ci uz uzivatelov, miestnosti
 *
 * pri prihlasovani sa komunikuje s databazou a vyhladava sa dany pouzivatel podla zadanych udajov
 */
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

        Scene sceneUserLogin = new Scene(root);

        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("login"));
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

    /**
     * zmena prihlasenia pre spolocnost
     *
     * @throws Exception
     */
    public void signAsCompanyButtonClicked() throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new CompanyLoginController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Otvorenie prihlasenia firmy.");
    }

    public void signAsUserButtonClicked() {
    }

    /**
     * pouzivatel sa prihlasuje, po zadani korektnych udajov sa otvori hlavne menu
     *
     * @throws Exception
     */
    public void signInButtonClicked() throws Exception {
        Person person = ManagerPerson.isRegistered(usernameTextField.getText(), passwordTextField.getText());
        if (person == null) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("warning"));

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.WARNING, "Prihlasovacie meno/heslo je neplatne");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyLogin.title"));
            alert.setContentText(rbSk.getString("userLogin.text"));
            alert.showAndWait();
        } else {
            ProgramData.getInstance().setUser(person);
            ProgramData.getInstance().setCompany(null);

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.INFO, "Pouzivatel " + person.getUsername() + " bol uspesne prihlaseny.");

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller crc = new UserMainWindowController();
            crc.startController(primaryStage);
        }
    }

    /**
     * ak pouzivatel nie je registrovany, tak po kliknuti sa otvori okno pre registraciu
     *
     * @throws Exception
     */
    public void notRegisteredButtonClicked() throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller urc = new UserRegistrationController();
        urc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Otvorenie registracie pouzivatela.");
    }

    /**
     * zmena jazyka na Slovencinu
     *
     * @param mouseEvent
     */
    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na slovencinu.");
    }

    /**
     * zmena jazyka na Anglictinu
     *
     * @param mouseEvent
     */
    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na anglictinu.");
    }
}

