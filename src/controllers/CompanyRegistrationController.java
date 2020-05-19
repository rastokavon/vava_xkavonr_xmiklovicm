package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import managers.ManagerCompany;
import database.ProgramData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyRegistrationController implements Controller {
    Stage primaryStage;
    Parent root;

    @FXML
    TextField nameTextField;
    @FXML
    TextField streetTextField;
    @FXML
    TextField cityTextField;
    @FXML
    TextField countryTextField;
    @FXML
    TextField postalCodeTextField;
    @FXML
    TextField mailTextField;
    @FXML
    TextField phoneNumberTextField;
    @FXML
    Hyperlink backButton;
    @FXML
    Button signUpButton;
    @FXML
    Label welcomeLabel;
    @FXML
    Label obligatoryLabel;
    @FXML
    Label nameLabel;
    @FXML
    Label addressLabel;
    @FXML
    Label contactLabel;

    @Override
    public void startController(Stage stage) throws Exception {
        primaryStage = stage;
        root = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/CompanyRegistration.fxml"));

        Scene sceneUserRegistration = new Scene(root);

        primaryStage.setScene(sceneUserRegistration);
        primaryStage.show();
    }

    @FXML
    public void initialize() {
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle, Locale.forLanguageTag("reg"));
        welcomeLabel.setText(rbSk.getString("companyReg.welcome"));
        nameTextField.setPromptText(rbSk.getString("companyReg.nameField"));
        streetTextField.setPromptText(rbSk.getString("companyReg.street"));
        cityTextField.setPromptText(rbSk.getString("companyReg.city"));
        countryTextField.setPromptText(rbSk.getString("companyReg.country"));
        postalCodeTextField.setPromptText(rbSk.getString("companyReg.postalCode"));
        mailTextField.setPromptText(rbSk.getString("reg.mail"));
        phoneNumberTextField.setPromptText(rbSk.getString("reg.phoneNumber"));
        nameLabel.setText(rbSk.getString("companyReg.nameLabel"));
        obligatoryLabel.setText(rbSk.getString("reg.obligatory"));
        addressLabel.setText(rbSk.getString("companyReg.address"));
        contactLabel.setText(rbSk.getString("companyReg.contact"));
        backButton.setText(rbSk.getString("reg.back"));
        signUpButton.setText(rbSk.getString("reg.signUp"));

        primaryStage = ProgramData.getInstance().getPrimaryStage();
        primaryStage.setTitle(rbSk.getString("companyReg.window"));

        try {
            generatePDF();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new CompanyLoginController();
        clc.startController(primaryStage);
    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerCompany.createCompany(nameTextField.getText(), streetTextField.getText(), cityTextField.getText(),
                countryTextField.getText(), postalCodeTextField.getText(), mailTextField.getText(),
                phoneNumberTextField.getText());

        if (message.length() == 0) {

            primaryStage = ProgramData.getInstance().getPrimaryStage();
            Controller clc = new CompanyLoginController();
            clc.startController(primaryStage);

            String bundle1 = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk1 = ResourceBundle.getBundle(bundle1 + "_popup", Locale.forLanguageTag("info"));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk1.getString("companyReg.title"));
            alert.setHeaderText(rbSk1.getString("companyReg.roomNumber") + "                 " +
                    String.valueOf(ProgramData.getInstance().getCurrentlyRegCompany().getId()) + "\n\n" + rbSk1.getString("companyReg.password") +
                    "            " + ProgramData.getInstance().getCurrentlyRegCompany().getPassword());
            alert.setContentText(rbSk1.getString("companyReg.question"));

            ButtonType yesButton = new ButtonType(rbSk1.getString("companyReg.yes"));
            ButtonType noButton = new ButtonType(rbSk1.getString("companyReg.no"));

            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton){
                generatePDF();
            } else if (result.get() == noButton) {
            }


        } else {
            String bundle = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rbSk.getString("companyReg.title"));
            alert.setContentText(String.valueOf(message) + "    ");
            alert.showAndWait();
        }
    }

    public void slovakFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("sk");
        initialize();
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();
    }

    public void generatePDF() throws DocumentException, FileNotFoundException {

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF file(*.pdf)", "*.pdf"));

        File file = chooser.showSaveDialog(ProgramData.getInstance().getPrimaryStage());

        if (file == null) {
            return;
        }

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Paragraph welcomeText = new Paragraph();
        welcomeText.add("   Tuto bude pekny text\ns privitanim!");
        document.add(welcomeText);

        document.close();
    }
}
