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


public class CompanyMainWindowController implements Controller {
    Stage primaryStage;
    Parent root;
    Company company;

    @FXML
    Label nameLabel;
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
    Hyperlink signOutHyperlink;
    @FXML
    Button modifyButton;
    @FXML
    Label roomLabel;
    @FXML
    TreeTableView usersTable;
    @FXML
    TextField searchTextField;
    @FXML
    Button changePasswordButton;


    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/CompanyMainWindow.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }

    public void setCompany(Company company) {
        this.company = company;
        System.out.println(company.getName());
    }

    @FXML
    public void initialize() {

        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("mainCom"));

        roomLabel.setText(rbSk.getString("companyMain.roomLabel"));
        searchTextField.setPromptText(rbSk.getString("companyMain.searchField"));
        signOutHyperlink.setText(rbSk.getString("companyMain.signOut"));
        modifyButton.setText(rbSk.getString("companyMain.modifyInfo"));
        changePasswordButton.setText(rbSk.getString("companyMain.changePassword"));
        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("companyMain.window"));
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();
    }

    public void signOutHyperlinkClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new CompanyLoginController();
        clc.startController(primaryStage);
    }

    public void magnifierClicked(MouseEvent mouseEvent) {
    }


    public void modifyButtonClicked(ActionEvent actionEvent) {
    }

    public void changePasswordButtonClicked(ActionEvent actionEvent) {
    }
}
