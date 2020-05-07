package database;

import javafx.stage.Stage;
import managers.ManagerCompany;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ProgramData {

    private static ProgramData instance = null;

    private String language;

    private Stage primaryStage;

    private int idLogged;

    private static final Logger LOG = Logger.getLogger(ManagerCompany.class.getName());

    private static FileHandler loggingsFh;

    {
        try {
            loggingsFh = new FileHandler("loggings.log", true);
            LOG.addHandler(loggingsFh);
            SimpleFormatter sf = new SimpleFormatter();
            loggingsFh.setFormatter(sf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ProgramData() {}

    public static ProgramData getInstance() {
        if (instance == null) {
            instance = new ProgramData();
        }
        return instance;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static Logger getLOG() {
        return LOG;
    }

    public static FileHandler getLoggingsFh() {
        return loggingsFh;
    }

    public int getIdLogged() {
        return idLogged;
    }

    public void setIdLogged(int idLogged) {
        this.idLogged = idLogged;
    }
}
