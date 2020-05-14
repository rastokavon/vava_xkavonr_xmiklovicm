package controllers;

import database.Company;
import database.Person;
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
import managers.ManagerCompany;
import managers.ManagerPerson;
import managers.ManagerPosts;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserRoomMembers implements Controller {
    @FXML
    Hyperlink signedUserHiperlink;

    @FXML
    Button signOutButton;
    @FXML
    Button homeButton;
    @FXML
    Button usersButton;

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
    Label nameCompanyLabel;
    @FXML
    Label usersNumberLabel;
    @FXML
    Label roomIdLabel;
    @FXML
    Label allPostsLabel;
    @FXML
    Label roomLabel;

    @FXML
    TextField searchTextField;

    @FXML
    TableView usersTable;

    Stage primaryStage;
    Parent root;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRoomMembers.class.getResource("../GUI/UserRoomMembers.fxml"));

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

        Company company = ProgramData.getInstance().getUser().getCompany();

        roomLabel.setText(rbSk.getString("userRoom.roomLabel"));
        searchTextField.setPromptText(rbSk.getString("userRoom.search"));
        nameCompanyLabel.setText(company.getName());
        streetLabel.setText(company.getStreet());
        cityLabel.setText(company.getCity());
        countryLabel.setText(company.getCountry());
        postalCodeLabel.setText(company.getPostalCode());
        mailLabel.setText(ProgramData.getInstance().getUser().getMail());
        phoneNumberLabel.setText(ProgramData.getInstance().getUser().getPhoneNumber());
        usersNumberLabel.setText(rbSk.getString("userRoom.number") +
                ManagerPerson.getUsers("", company.getId()).size());
        if (ManagerPosts.getPosts(company) != null) {
            allPostsLabel.setText(rbSk.getString("userRoom.posts") +
                    ManagerPosts.getPosts(company).size());
        } else {
            allPostsLabel.setText(rbSk.getString("userRoom.posts") +
                    " 0");
        }

        roomIdLabel.setText(rbSk.getString("userRoom.id") + String.valueOf(company.getId()));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("userRoom.title"));
        usersTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                Person person = (Person) usersTable.getSelectionModel().getSelectedItem();

                if (person != null) {
                    primaryStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(UserDetailController.class.getResource("../GUI/UserDetail.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {}
                    Scene scene = new Scene(root);

                    primaryStage.setScene(scene);

                    UserDetailController udc = loader.getController();
                    udc.setPerson(person);
                    try {
                        udc.startController(primaryStage);
                    } catch (Exception e) {}
                    primaryStage.show();
                }
            }
        });

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

    public void usersButtonClicked(ActionEvent actionEvent) {
    }

    public void magnifierClicked(MouseEvent mouseEvent) {
        fillTable();
    }

    public void fillTable() {
        int roomNumber = ProgramData.getInstance().getUser().getCompany().getId();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("mainCom"));

        TableColumn firstName = new TableColumn(rbSk.getString("companyMain.fnColumn"));
        TableColumn lastName = new TableColumn(rbSk.getString("companyMain.lnColumn"));
        TableColumn username = new TableColumn(rbSk.getString("companyMain.unColumn"));
        usersTable.getColumns().setAll(firstName, lastName, username);

        firstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        username.setCellValueFactory(new PropertyValueFactory<Person, String>("username"));
        try {
            final ObservableList<Person> users;
            usersTable.setItems(null);
            users = FXCollections.observableArrayList(ManagerPerson.getUsers(searchTextField.getText(), roomNumber));
            usersTable.setItems(users);
        } catch (Exception e) {
        }

    }
}
