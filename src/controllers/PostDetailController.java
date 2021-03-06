package controllers;

import database.Comment;
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
import managers.ManagerComments;
import main.TableAllPosts;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * okno ukazuje cele znenie prispevku aj s jeho nadpisom, autorom a komentami
 *
 * takyto prisevok je mozne komentovat, prezerat si jeho komenty
 */
public class PostDetailController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    Hyperlink signedUserHyperlink;
    @FXML
    Hyperlink userPostedHyperlink;

    @FXML
    Button signOutButton;
    @FXML
    Button homeButton;
    @FXML
    Button usersButton;
    @FXML
    Button addCommentButton;

    @FXML
    Label titleLabel;
    @FXML
    Label dateLabel;
    @FXML
    Label commentsLabel;

    @FXML
    TableView commentsTable;

    @FXML
    TextArea postTextAre;
    @FXML
    TextArea commentTextArea;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(PostDetailController.class.getResource("../GUI/PostWindow.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =  ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");


        signOutButton.setText(rbSk.getString("mainPan.signOut"));
        homeButton.setText(rbSk.getString("mainPan.home"));
        usersButton.setText(rbSk.getString("mainPan.users"));
        signedUserHyperlink.setText(ProgramData.getInstance().getUser().getUsername());

        commentsLabel.setText(rbSk.getString("userPost.comments"));
        addCommentButton.setText(rbSk.getString("userPost.commentButton"));
        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userPost.windowTitle"));

        userPostedHyperlink.setText(ProgramData.getInstance().getPost().getPerson().getUsername());
        dateLabel.setText(formatter.format(ProgramData.getInstance().getPost().getDate()));
        titleLabel.setText(ProgramData.getInstance().getPost().getTitle());
        postTextAre.setText(ProgramData.getInstance().getPost().getText());
        commentTextArea.setText("");

        fillCommentsTable();
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
    public void signedUserHyperlinkClicked(ActionEvent actionEvent) throws Exception {
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
     * zobrazi sa povodna domovska stranka (hlavne menu pouzivatela)
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
     * zobrazi sa zoznam vsetkych pouzivatelov v miestnosti
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
     * zobrazi sa detail pouzivatela, ktory pridal prispevok
     *
     * @param actionEvent
     * @throws Exception
     */
    public void userPostedHyperlinkClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(UserDetailController.class.getResource("../GUI/UserDetail.fxml"));
        Parent root = null;
        root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        UserDetailController udc = loader.getController();
        udc.setPerson(ProgramData.getInstance().getPost().getPerson());
        udc.startController(primaryStage);

        primaryStage.show();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zobrazenie informacii o pouzivatelovi "
                + ProgramData.getInstance().getPost().getPerson().getUsername());
    }

    /**
     * vyplni tabulku komentov patriacich prispevku
     */
    public void fillCommentsTable() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk =  ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        TableColumn text = new TableColumn("Title");
        TableColumn author = new TableColumn("About");
        commentsTable.getColumns().setAll(text, author);

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            List<Comment> comments;
            List<TableAllPosts> tableAllComments = new ArrayList<>();
            commentsTable.setItems(null);
            comments = ManagerComments.getComments(ProgramData.getInstance().getPost());
            if (comments != null) {

                for (Comment c : comments) {
                    TableAllPosts tap = new TableAllPosts();
                    tap.setDateName(rbSk.getString("main.added") + formatter.format(c.getDate()) +
                            "\n" + rbSk.getString("main.user") + c.getPerson().getUsername());
                    tap.setTitle(c.getText());
                    tableAllComments.add(tap);
                }
            }
            ObservableList<TableAllPosts> tableComments = FXCollections.observableArrayList(tableAllComments);
            text.setCellValueFactory(new PropertyValueFactory<TableAllPosts, String>("title"));
            author.setCellValueFactory(new PropertyValueFactory<TableAllPosts, String>("dateName"));
            commentsTable.setItems(tableComments);
            commentsTable.getSortOrder().add(author);
        } catch (Exception e) {
        }

    }

    /**
     * prida sa komentar (ak nejaky text je vyplneny v policku pre komentovanie)
     *
     * @param actionEvent
     * @throws Exception
     */
    public void addCommentButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerComments.createComment(commentTextArea.getText(),
                ProgramData.getInstance().getPost(), ProgramData.getInstance().getUser());

        if (message.length() == 0) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.INFO, "Pouzivatel " + ProgramData.getInstance().getUser().getUsername()
                    + " pridal komentar na prispevok: " + ProgramData.getInstance().getPost().getTitle());

            initialize();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk.getString("addComment.title"));
            alert.setContentText(rbSk.getString("addComment.text"));
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
