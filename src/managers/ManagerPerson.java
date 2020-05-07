package managers;

import database.Company;
import database.CreateDatabase;
import database.Person;
import database.ProgramData;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ManagerPerson {
    static final Logger LOG = Logger.getLogger(ManagerCompany.class.getName());

    public static boolean isUsedUsername(String username) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);

        cr.select(root).where(cb.equal(root.get("username"), username));

        Query<Person> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Person> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return false;
        }

        return true;

    }

    public static boolean createPerson(String firstName, String lastName, String username,
                                       String password, String mail, String phoneNumber, int companyID) {
        Logger LOG = ProgramData.getLOG();
        FileHandler fh = ProgramData.getLoggingsFh();
        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);
        
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

            Company company = ManagerCompany.getCompanyFromID(companyID);
            if (company == null) {
                LOG.log(Level.SEVERE, "Cislo miestnosti je neplatne");
                return false;
            }
            if (isUsedUsername(username)) {
                LOG.log(Level.SEVERE, "Pouzivatelske meno je pouzivane");
                return false;
            }

            session.save(new Person(firstName, lastName, username, password, mail, phoneNumber, company));

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
}
