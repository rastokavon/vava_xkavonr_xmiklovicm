package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CreateEntity {


    public static Session getSession() throws HibernateException {
        SessionFactory ourSessionFactory;
        Configuration configuration = new Configuration();
        configuration.configure();
        ourSessionFactory = configuration.buildSessionFactory();

        return ourSessionFactory.openSession();
    }


    public static void createPerson(String firstName, String lastName, String username,
                                    String password, String mail, String phoneNumber, int company) {
        Session session = getSession();
        Transaction t = session.beginTransaction();

        //session.save(new Person(firstName, lastName, username, password, mail, phoneNumber, getCompany(company)));

        t.commit();
        session.close();

        System.out.println("Successfully saved new person...");
    }
    public static void createCompany(String name, String street, String city,
                                     String country, String postalCode, String mail, String phoneNumber) {
        System.out.println("piko...");

        Session session = getSession();
        Transaction t = session.beginTransaction();
        System.out.println("piko...");

        session.save(new Company(name, street, city, country, postalCode, mail, phoneNumber));
        System.out.println("Successfully saved new company...");

        t.commit();
        System.out.println("piko...");

        session.close();

        System.out.println("Successfully saved new company...");

    }
}
