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
 * okno predstavuje hlavne menu pouzivatela
 *
 * pouzivatel vidi vsetky prispevky miestnosti a moze nimi prechadzat
 * je mozne kazdy prispevok si rozkliknut a vidiet jeho detail
 * rovnako je mozne pridat prispevok, ktory sa ulozi do danej miestnosti
 * a vidia ho ostatni pouzivatelia
 */
public class UserMainWindowController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    Button signOutButton;
    @FXML
    Button homeButton;
    @FXML
    Button usersButton;
    @FXML
    Button addPostButton;
    @FXML
    Hyperlink signedUserHiperlink;
    @FXML
    TableView<TableAllPosts> table;


    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/UserMainWindow.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        signOutButton.setText(rbSk.getString("mainPan.signOut"));
        homeButton.setText(rbSk.getString("mainPan.home"));
        usersButton.setText(rbSk.getString("mainPan.users"));
        signedUserHiperlink.setText(ProgramData.getInstance().getUser().getUsername());

        addPostButton.setText(rbSk.getString("main.addPost"));
        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("main.title"));

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
     * zobrazi sa profil pouzivatela
     *
     * @param actionEvent
     * @throws Exception
     */
    public void signedUserHiperlinkClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserInformationController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zobrazenie profilu prihlaseneho pouzivatela.");

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
     * hlavne menu pouzivatela
     *
     * @param actionEvent
     */
    public void homeButtonClicked(ActionEvent actionEvent) {
    }

    /**
     * zobrazi sa okno, kde je mozne vidiet vsetkych pouzivatelov patriacich do miestnosti
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
     * vyplni sa tabulka vsetkych prispevkov v miestnosti
     */
    public void fillTable() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        TableColumn title = new TableColumn(rbSk.getString("main.tableTitle"));
        TableColumn author = new TableColumn(rbSk.getString("main.tableAbout"));
        table.getColumns().setAll(title, author);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            List<Post> posts;
            List<TableAllPosts> tableAllPosts = new ArrayList<>();
            table.setItems(null);
            posts = ManagerPosts.getPosts(ProgramData.getInstance().getUser().getCompany());
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
        } catch (Exception e) {
        }

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
