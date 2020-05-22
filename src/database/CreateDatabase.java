package database;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CreateDatabase {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            InputStream is;
            is = new FileInputStream("etc/config.properties");
            Properties p = new Properties();
            p.load(is);

            Configuration configuration = new Configuration();
            File f = new File(p.getProperty("hibernateFile.name"));
            configuration.configure(f);

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
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
        System.out.println("successfully saved");
    }
}