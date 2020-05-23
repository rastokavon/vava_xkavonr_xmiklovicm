package controllers;

import database.ProgramData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * MainController je hlavny spustac programu
 *
 * nacitavaju (a vytvoria) sa v nom vsetky potrebne objekty a udaje, potrebne pre chod programu
 * po vytvoreni objektov sa spusti login okno pre User
 */
public class MainController extends Application {


    protected Stage primaryStage;
    Parent rootUser;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Aplikacia bola spustena.");

        ProgramData pd = ProgramData.getInstance();
        pd.setLanguage("sk");
        pd.setPrimaryStage(primaryStage);

        setPrimaryStage(primaryStage);
        rootUser = FXMLLoader.load(UserRegistrationController.class.getResource("../GUI/userLogin.fxml"));

        Scene sceneUserLogin = new Scene(rootUser);

        primaryStage.setScene(sceneUserLogin);
        primaryStage.show();
    }

    @Override
    public void stop(){
        Logger LOG = ProgramData.getInstance().getLOG();
        LOG.log(Level.INFO, "Program uspesne ukonceny.");

        if (ProgramData.getInstance().getLoggingsFh() != null) {
            ProgramData.getInstance().getLoggingsFh().close();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
