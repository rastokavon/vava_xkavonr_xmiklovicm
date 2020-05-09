package controllers;

import database.Person;
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
import managers.ManagerPosts;

import java.util.Locale;
import java.util.ResourceBundle;


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
    TableView<Post> table;


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
        ResourceBundle rbSk =	ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uMainPan"));

        signOutButton.setText(rbSk.getString("mainPan.signOut"));
        homeButton.setText(rbSk.getString("mainPan.home"));
        usersButton.setText(rbSk.getString("mainPan.users"));
        signedUserHiperlink.setText(ProgramData.getInstance().getUser().getUsername());

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

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userInfo.title"));
        fillTable();
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();
    }


    public void signedUserHiperlinkClicked(ActionEvent actionEvent) throws Exception {
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

    public void usersButtonClicked(ActionEvent actionEvent) {
    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller cpc = new ChangePasswordController();
        cpc.startController(primaryStage);
    }

    public void modifyButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = new Stage();

        Controller mpc = new ModifyPersonController();
        mpc.startController(primaryStage);
    }

    public void fillTable() {
        //String bundle = ProgramData.getInstance().getLanguage();
        //ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("mainCom"));


        TableColumn titles = new TableColumn("Titles");
        TableColumn dates = new TableColumn("Dates");
        table.getColumns().setAll(titles, dates);

        titles.setCellValueFactory(new PropertyValueFactory<Post, String>("title"));
        dates.setCellValueFactory(new PropertyValueFactory<Post, String>("date"));

        try {
            final ObservableList<Post> posts;
            table.setItems(null);
            posts = FXCollections.observableArrayList(ManagerPosts.getPosts(ProgramData.getInstance().getUser()));
            table.setItems(posts);
        } catch (Exception e) {}

    }
}


