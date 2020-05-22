package database;

import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static java.lang.System.exit;


/**
 * v triede ProgramData je aplikovany navrhovy vzor Singleton
 *
 * v triede su ulozene vsetky pouzivane instancie objektov programu
 *
 * obsahuje veci potrebne pre chod programu a casto sa ku nej pristupuje pri roznych volaniach
 * od pouzivatela
 *
 * po prihlaseni sa do tejto instancie triedy nahra dany prihlaseny pouzivatel a rovanako aj miestnost,
 * do ktorej pouzivatel patri
 *
 * podla aktualne zadaneho jazyka pouzivatelom obsahuje informaciu, o ktory jazyk sa jedna
 *
 * rovnako pri chode programu obsahuje instanciu okna, ktora sa vzdy vypyta pri zmene sceny
 *
 * obsahuje aj LOG file, do ktoreho sa vypisuju rozne necakane stavy
 */
public class ProgramData {
    private static ProgramData instance = null;
    private String language;
    private Stage primaryStage;
    private Company company;
    private Company currentlyRegCompany;
    private Person user;
    private Post post = null;

    private final Logger LOG = Logger.getLogger(ProgramData.class.getName());

    private FileHandler loggingsFh;

    {
        try {
            LOG.setUseParentHandlers(false);

            InputStream is;
            is = new FileInputStream("etc/config.properties");
            Properties p = new Properties();
            p.load(is);

            setLoggingsFh(new FileHandler(p.getProperty("logFile.name"),
                    Integer.parseInt(p.getProperty("logFile.maxSize")),
                    Integer.parseInt(p.getProperty("logFile.count")), true));
            LOG.addHandler(getLoggingsFh());
            SimpleFormatter sf = new SimpleFormatter();
            getLoggingsFh().setFormatter(sf);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    private ProgramData() {
    }

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

    public Logger getLOG() {
        return LOG;
    }

    public FileHandler getLoggingsFh() {
        return loggingsFh;
    }

    public void setLoggingsFh(FileHandler loggingsFh) {
        this.loggingsFh = loggingsFh;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Company getCurrentlyRegCompany() {
        return currentlyRegCompany;
    }

    public void setCurrentlyRegCompany(Company currentlyRegCompany) {
        this.currentlyRegCompany = currentlyRegCompany;
    }
}
