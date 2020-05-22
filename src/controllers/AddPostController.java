package controllers;

import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.ManagerPosts;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * okno, ktore sluzi na pridanie prispevku
 *
 * zadava sa nadpis
 * a samotny text prispevku, ktory sa po odkliknuti buttonu addPostButton prida do databazy
 *
 * pri pridavani sa kontroluje niekolko veci, napriklad ci ma prispevok/nadpis nejaky text, dlzka textu...
 */
public class AddPostController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    Button addPostButton;
    @FXML
    Button signOutButton;
    @FXML
    Button homeButton;
    @FXML
    Button usersButton;

    @FXML
    Hyperlink signedUserHyperlink;

    @FXML
    TextArea postTextArea;

    @FXML
    TextField titleTextField;

    @FXML
    Label titleLabel;
    @FXML
    Label postLabel;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(AddPostController.class.getResource("../GUI/AddPost.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =  ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        signOutButton.setText(rbSk.getString("mainPan.signOut"));
        homeButton.setText(rbSk.getString("mainPan.home"));
        usersButton.setText(rbSk.getString("mainPan.users"));
        signedUserHyperlink.setText(ProgramData.getInstance().getUser().getUsername());

        titleLabel.setText(rbSk.getString("userAddPost.title"));
        postLabel.setText(rbSk.getString("userAddPost.post"));

        titleTextField.setPromptText(rbSk.getString("userAddPost.titlePrompt"));
        postTextArea.setPromptText(rbSk.getString("userAddPost.postPrompt"));

        addPostButton.setText(rbSk.getString("userAddPost.addButton"));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userAddPost.windowTitle"));
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

    public void signedUserHiperlinkClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserInformationController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zobrazenie profilu prihlaseneho pouzivatela.");
    }

    public void signOutButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserLoginController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel bol odhlaseny.");
    }

    public void homeButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserMainWindowController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel sa nachadza v hlavnom menu.");
    }

    public void usersButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller urmc = new UserRoomMembers();
        urmc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zobrazenie vsetkych pouzivatelov v miestnosti.");
    }

    public void addPostButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerPosts.createPost(titleTextField.getText(), postTextArea.getText(),
                ProgramData.getInstance().getUser());

        if (message.length() == 0) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller umvc = new UserMainWindowController();
            umvc.startController(primaryStage);


            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.INFO, "Pouzivatel " + ProgramData.getInstance().getUser().getUsername()
                    + " pridal prispevok.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk.getString("addPost.title"));
            alert.setContentText(rbSk.getString("addPost.text"));
            alert.showAndWait();
        } else {
            String bundle1 = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk1 = ResourceBundle.getBundle(bundle1
                    + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk1.getString("addPost.title"));
            alert.setContentText(String.valueOf(message) + "    ");
            alert.showAndWait();
        }
    }
}
