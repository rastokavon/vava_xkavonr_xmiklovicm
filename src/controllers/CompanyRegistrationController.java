package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import managers.ManagerCompany;
import main.ProgramData;
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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * okno, kde sa zadavaju udaje o firme, ktora spravuje miestnost
 *
 * po zadani udajov a odkliknuti tlacidla pre registraciu sa kontroluje vhodnost zadanych udajov (ci su vsetky
 * polia vyplnene, do pola s cislami sa nezadal string a pod...)
 *
 * po schvaleni udajov aplikaciou sa ukaze okno uspesnej registracie a je mozne ulozit udaje o miestnosti
 * (teda Company) do externeho suboru PDF
 */
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
    }

    public void backButtonClicked(ActionEvent actionEvent) throws Exception {
        primaryStage = ProgramData.getInstance().getPrimaryStage();

        Controller clc = new CompanyLoginController();
        clc.startController(primaryStage);

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Navrat do hlavneho menu prihlasenia firmy.");
    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws Exception {
        StringBuffer message = ManagerCompany.createCompany(nameTextField.getText(), streetTextField.getText(), cityTextField.getText(),
                countryTextField.getText(), postalCodeTextField.getText(), mailTextField.getText(),
                phoneNumberTextField.getText());

        if (message.length() == 0) {

            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.INFO, "Registrovana nova firma: "
                    + ProgramData.getInstance().getCurrentlyRegCompany().getName());

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

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na slovencinu.");
    }

    public void britishFlagClicked(MouseEvent mouseEvent) {
        ProgramData.getInstance().setLanguage("en");
        initialize();

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Zmeneny jazyk na anglictinu.");
    }

    public void generatePDF() {

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF file(*.pdf)", "*.pdf"));

        File file = chooser.showSaveDialog(ProgramData.getInstance().getPrimaryStage());

        if (file == null) {
            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.SEVERE, "Nedokaze vygenerovat PDF");

            return;
        }

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();
            document.setMargins(100, 100, 100, 100);

            Paragraph welcomeTitle = new Paragraph("Registration information",
                    new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD));
            welcomeTitle.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(welcomeTitle);
            document.add(new Paragraph("\n"));

            String text = "Hello " + ProgramData.getInstance().getCurrentlyRegCompany().getName() + "!\n\n    Thank you " +
                    "for buying our product. KAMIMARA is highly recommended electronic communication system.\n    " +
                    "Share your room number with your friends, employees, customers to communicate easily and of course " +
                    "privately! If you are curious,don't understand or don't know something, feel free to post your " +
                    "question and wait for answers of other people in the room. You can help each other, communicate or " +
                    "just chatting.\n    Thank you again and enjoy your private session!\n\nKAMIMARA\n\n";
            Paragraph welcomeText = new Paragraph(text, new Font(Font.FontFamily.HELVETICA, 12));
            welcomeText.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(welcomeText);

            Paragraph infoTitle = new Paragraph("Information about company:\n",
                    new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD));

            infoTitle.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(infoTitle);

            document.add(new Chunk("Name: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Chunk(ProgramData.getInstance().getCurrentlyRegCompany().getName() + "\n"));

            document.add(new Chunk("Address: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Chunk(ProgramData.getInstance().getCurrentlyRegCompany().getStreet() + ", " +
                    ProgramData.getInstance().getCurrentlyRegCompany().getPostalCode() + " " +
                    ProgramData.getInstance().getCurrentlyRegCompany().getCity() + " (" +
                    ProgramData.getInstance().getCurrentlyRegCompany().getCountry() + ")\n"));

            document.add(new Chunk("Mail: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Chunk(ProgramData.getInstance().getCurrentlyRegCompany().getMail() + "\n"));

            document.add(new Chunk("Phone number: ", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            if ("".equals(ProgramData.getInstance().getCurrentlyRegCompany().getPhoneNumber())) {
                document.add(new Chunk("-unknown-" + "\n\n\n"));
            } else {
                document.add(new Chunk(ProgramData.getInstance().getCurrentlyRegCompany().getPhoneNumber() + "\n\n\n"));
            }

            document.add(new Chunk("Room number: ", new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD)));
            document.add(new Chunk(String.valueOf(ProgramData.getInstance().getCurrentlyRegCompany().getId()) +
                    "\n", new Font(Font.FontFamily.HELVETICA, 15)));

            document.add(new Chunk("New password: ", new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD)));
            document.add(new Chunk(ProgramData.getInstance().getCurrentlyRegCompany().getPassword(),
                    new Font(Font.FontFamily.HELVETICA, 15)));

        }catch (DocumentException e) {
            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.SEVERE, "Nedokaze vygenerovat PDF:", e);
        } catch (FileNotFoundException e) {
            Logger LOG = ProgramData.getInstance().getLOG();
            LOG.log(Level.SEVERE, "Nedokaze vygenerovat PDF:", e);
        } finally {
            if (document != null) {
                document.close();
            }
        }

        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Vygenerovanie PDF.");
    }
}
