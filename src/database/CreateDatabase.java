package database;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;



/**
 * tato trieda sluzi len na overenie spojenia s databazou
 *
 * vytvori sa pristup a nasledne sa zatvori, ak vsetko prebehlo v poriadku, tak do
 * konzoly sa vypise sprava
 *
 * rovnako ak by bola do databazy pridavana nejaka entita, po jej namapovani sa spusti
 * tato trieda, ktora novej entite vytvori tabulku v databaze
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CreateDatabase {
    private static final SessionFactory ourSessionFactory;

    static {
        InputStream is = null;
        try {
            is = new FileInputStream("etc/config.properties");
            Properties p = new Properties();
            p.load(is);

            Configuration configuration = new Configuration();
            File f = new File(p.getProperty("hibernateFile.name"));
            configuration.configure(f);

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();

        Transaction t = session.beginTransaction();
        t.commit();

        session.close();
        System.out.println("successfully");
    }
}