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
public class CreateDatabase {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

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
        System.out.println("successfully");
    }
}