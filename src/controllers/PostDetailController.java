package controllers;

import database.Comment;
import database.Post;
import database.ProgramData;
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
import managers.ManagerPosts;
import tables.TableAllPosts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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

        fillCommentsTable();
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();
    }

    public void signedUserHyperlinkClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserInformationController();
        clc.startController(primaryStage);
    }

    public void signOutButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserLoginController();
        clc.startController(primaryStage);
    }

    public void homeButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new UserMainWindowController();
        clc.startController(primaryStage);
    }

    public void usersButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller urmc = new UserRoomMembers();
        urmc.startController(primaryStage);
    }

    public void userPostedHyperlinkClicked(ActionEvent actionEvent) {
        primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(UserDetailController.class.getResource("../GUI/UserDetail.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {}
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        UserDetailController udc = loader.getController();
        udc.setPerson(ProgramData.getInstance().getPost().getPerson());
        try {
            udc.startController(primaryStage);
        } catch (Exception e) {}
        primaryStage.show();
    }

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

    public void addCommentButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerComments.createComment(commentTextArea.getText(),
                ProgramData.getInstance().getPost(), ProgramData.getInstance().getUser());

        if (message.length() == 0) {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("info"));

            initialize();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk.getString("addComment.title"));
            alert.setContentText(rbSk.getString("addComment.text"));
            alert.showAndWait();
        } else {
            String bundle1 = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk1 = ResourceBundle.getBundle(bundle1 + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk1.getString("addPost.title"));
            alert.setContentText(String.valueOf(message) + "    ");
            alert.showAndWait();
        }
    }
}
