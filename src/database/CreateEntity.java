package database;

import controllers.MainController;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateEntity {
    static final Logger LOG = Logger.getLogger(CreateEntity.class.getName());

    public static Company getCompanyFromID(Integer companyID) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Company> cr = cb.createQuery(Company.class);
        Root<Company> root = cr.from(Company.class);

        cr.select(root).where(cb.equal(root.get("id"), companyID));

        Query<Company> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Company> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            LOG.log(Level.WARNING, "Take cislo miestnosti neexistuje");
            return new Company();
        }

        return results.get(0);

    }

    public static boolean createPerson(String firstName, String lastName, String username,
                                    String password, String mail, String phoneNumber, int company) {

        if ("".equals(firstName)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole meno");
            return false;
        }
        if ("".equals(lastName)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole priezvisko");
            return false;
        }
        if ("".equals(username)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole prihlasovacie meno");
            return false;
        }
        if ("".equals(password)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole heslo");
            return false;
        }

        Session session = CreateDatabase.getSession();

        try {
            Transaction t = session.beginTransaction();

            session.save(new Person(firstName, lastName, username, password, mail, phoneNumber, getCompanyFromID(company)));

            t.commit();

            System.out.println("Successfully saved new person...");
        } catch(Exception e) {
            LOG.log(Level.SEVERE, "Nepodarilo sa pridat zaznam");
            return false;
        } finally {
            session.close();
        }
        return true;
    }
    public static boolean createCompany(String name, String street, String city,
                                     String country, String postalCode, String mail, String phoneNumber) {

        if ("".equals(name)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole nazov firmy");
            return false;
        }
        if ("".equals(street)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole ulica");
            return false;
        }
        if ("".equals(city)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole mesto");
            return false;
        }
        if ("".equals(country)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole stat");
            return false;
        }
        if ("".equals(postalCode)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole PSC");
            return false;
        }
        if ("".equals(mail)) {
            LOG.log(Level.SEVERE, "Nevyplnene pole mail");
            return false;
        }

        Session session = CreateDatabase.getSession();
        try {
            Transaction t = session.beginTransaction();

            session.save(new Company(name, street, city, country, postalCode, mail, phoneNumber));

            t.commit();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nazov firmy je uz zaregistrovany");
            return false;
        }

        session.close();
        return true;
    }
}
