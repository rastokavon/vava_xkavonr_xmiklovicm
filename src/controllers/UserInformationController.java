package controllers;

import database.Post;
import main.ProgramData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import managers.ManagerPosts;
import tables.TableAllPosts;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * okno predstavuje informacie aktualne prihlaseneho pouzivatela
 *
 * je mozne vidiet svoje osobne udaje, ktore je mozne pozmenit
 * rovnako je mozne zmenit heslo
 *
 * pouzivatel vidi vsetky svoje pridane prispevky a moze si ich rozkliknut, teda vidiet ich detail
 */
public class UserInformationController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    Button signOutButton;
    @FXML
    Button homeButton;
    @FXML
    Button usersButton;
    @FXML
    Hyperlink signedUserHiperlink;

    @FXML
    Label mainLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label firstNameLabel;
    @FXML
    Label surnameLabel;
    @FXML
    Label streetLabel;
    @FXML
    Label cityLabel;
    @FXML
    Label countryLabel;
    @FXML
    Label postalCodeLabel;
    @FXML
    Label mailLabel;
    @FXML
    Label phoneNumberLabel;

    @FXML
    Button modifyButton;
    @FXML
    Button changePasswordButton;
    @FXML
    Button addPostButton;

    @FXML
    TableView<TableAllPosts> table;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/UserInformation.fxml"));

        Scene sceneUserLogin = new Scene(root);

        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =  ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        signOutButton.setText(rbSk.getString("mainPan.signOut"));
        homeButton.setText(rbSk.getString("mainPan.home"));
        usersButton.setText(rbSk.getString("mainPan.users"));
        signedUserHiperlink.setText(ProgramData.getInstance().getUser().getUsername());

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userInfo.title"));
        mainLabel.setText(rbSk.getString("userInfo.question"));
        addPostButton.setText(rbSk.getString("main.addPost"));

        rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("mainCom"));

        usernameLabel.setText(ProgramData.getInstance().getUser().getUsername());
        firstNameLabel.setText(ProgramData.getInstance().getUser().getFirstName());
        surnameLabel.setText(ProgramData.getInstance().getUser().getLastName());
        streetLabel.setText("street");
        cityLabel.setText("city");
        countryLabel.setText("country");
        postalCodeLabel.setText("91943");
        mailLabel.setText(ProgramData.getInstance().getUser().getMail());
        phoneNumberLabel.setText(ProgramData.getInstance().getUser().getPhoneNumber());

        modifyButton.setText(rbSk.getString("companyMain.modifyInfo"));
        changePasswordButton.setText(rbSk.getString("companyMain.changePassword"));

        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                TableAllPosts post = (TableAllPosts) table.getSelectionModel().getSelectedItem();

                if (post != null) {
                    primaryStage = ProgramData.getInstance().getPrimaryStage();

                    Controller pdc = new PostDetailController();
                    try {
                        ProgramData.getInstance().setPost(ManagerPosts.getPost(post.getTitle()));
                        pdc.startController(primaryStage);
                    } catch (Exception e) {}
                }
            }
        });
        fillTable();
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

    /**
     * zobrazi sa profil prihlaseneho pouzivatela
     *
     * @param actionEvent
     * @throws Exception
     */
    public void signedUserHiperlinkClicked(ActionEvent actionEvent) throws Exception {
    }

    /**
     * odhlasenie
     *
     * @param actionEvent
     * @throws Exception
     */
    public void signOutButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserLoginController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel bol odhlaseny.");
    }

    /**
     * zobrazi sa hlavne menu pouzivatela
     *
     * @param actionEvent
     * @throws Exception
     */
    public void homeButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserMainWindowController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel sa nachadza v hlavnom menu.");
    }

    /**
     * okno so zoznamov vsetkych pouzivatelov patriacich do miestnosti
     *
     * @param actionEvent
     * @throws Exception
     */
    public void usersButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller urmc = new UserRoomMembers();
        urmc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zobrazenie vsetkych pouzivatelov v miestnosti.");
    }

    /**
     * otvori sa okno, pre zmenu hesla
     *
     * @param actionEvent
     * @throws Exception
     */
    public void changePasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller cpc = new ChangePasswordController();
        cpc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel " + ProgramData.getInstance().getUser().getUsername()
                + " vybral moznost zmeny hesla.");
    }

    /**
     * otvori sa okno pre zmenu udajov
     *
     * @param actionEvent
     * @throws Exception
     */
    public void modifyButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = new Stage();

        Controller mpc = new ModifyPersonController();
        mpc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel " + ProgramData.getInstance().getUser().getUsername()
                + " vybral moznost zmeny profilu.");
    }

    /**
     * vyplni sa tabulka s pouzivatelmi patriacimi do miestnosti
     */
    public void fillTable() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));


        TableColumn title = new TableColumn("Titles");
        TableColumn author = new TableColumn("Dates");
        table.getColumns().setAll(title, author);

        title.setCellValueFactory(new PropertyValueFactory<Post, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Post, String>("date"));

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            List<Post> posts;
            List<TableAllPosts> tableAllPosts = new ArrayList<>();
            table.setItems(null);
            posts = ManagerPosts.getPosts(ProgramData.getInstance().getUser());
            for (Post p : posts) {
                TableAllPosts tap = new TableAllPosts();
                tap.setDateName(rbSk.getString("main.added") + (formatter.format(p.getDate())) +  "\n" +
                        rbSk.getString("main.user") + p.getPerson().getUsername());
                tap.setTitle(p.getTitle());
                tableAllPosts.add(tap);
            }
            ObservableList<TableAllPosts> tablePosts = FXCollections.observableArrayList(tableAllPosts);
            title.setCellValueFactory(new PropertyValueFactory<TableAllPosts, String>("title"));
            author.setCellValueFactory(new PropertyValueFactory<TableAllPosts, String>("dateName"));
            table.setItems(tablePosts);
            table.getSortOrder().add(author);
        } catch (Exception e) {}



    }

    /**
     * otvori sa okno pre pridanie prispevku
     *
     * @param actionEvent
     * @throws Exception
     */
    public void addPostButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller apc = new AddPostController();
        apc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Pouzivatel " + ProgramData.getInstance().getUser().getUsername()
                + " otvoril okno pridania prispevku.");
    }
}


