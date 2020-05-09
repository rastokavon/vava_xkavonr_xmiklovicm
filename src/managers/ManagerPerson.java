package managers;

import database.Company;
import database.CreateDatabase;
import database.Person;
import database.ProgramData;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerPerson {

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

    public static Person isRegistered(String username, String password){

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("password"), password);
        predicates[1] = cb.equal(root.get("username"), username);
        cr.select(root).where(predicates);

        Query<Person> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Person> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }


    public static void changePassword(Person person, String password) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();

        person.setPassword(password);
        session.update(person);
        session.getTransaction().commit();
        session.close();
    }

    public static List<Person> getUsers(String name, int roomId) {

        name = "%" + name + "%";

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        Criteria crit = session.createCriteria(Person.class);
        crit.add(Restrictions.like("username", name));
        crit.add(Restrictions.eq("company.id", roomId));
        List<Person> results = crit.list();

        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }
        return results;

    }

    public static void deleteUser(String username) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);

        cr.select(root).where(cb.equal(root.get("username"), username));

        Query<Person> query = session.createQuery(cr);
        List<Person> results = query.getResultList();
        if (!results.isEmpty()) {
            session.delete(results.get(0));
        }
        session.getTransaction().commit();
        session.close();
    }

    public static Person getUser(String username) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> cr = cb.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);

        cr.select(root).where(cb.equal(root.get("username"), username));

        Query<Person> query = session.createQuery(cr);
        List<Person> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    public static StringBuffer createPerson(String firstName, String lastName, String username,
                                       String password, String mail, String phoneNumber, int companyID) {
        Logger LOG = ProgramData.getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(firstName)) {
            LOG.log(Level.INFO, "Nevyplnene pole meno");
            errorBuffer.append(rbSk.getString("userReg.missingFirstName"));
            errorBuffer.append("\n");
        }
        if ("".equals(lastName)) {
            LOG.log(Level.INFO, "Nevyplnene pole priezvisko");
            errorBuffer.append(rbSk.getString("userReg.missingLastName"));
            errorBuffer.append("\n");
        }
        if ("".equals(username)) {
            LOG.log(Level.INFO, "Nevyplnene pole prihlasovacie meno");
            errorBuffer.append(rbSk.getString("userReg.missingUsername"));
            errorBuffer.append("\n");
        }
        if ("".equals(password)) {
            LOG.log(Level.INFO, "Nevyplnene pole heslo");
            errorBuffer.append(rbSk.getString("userReg.missingPassword"));
            errorBuffer.append("\n");
        }

        Session session = CreateDatabase.getSession();

        try {
            Transaction t = session.beginTransaction();

            Company company = ManagerCompany.getCompanyFromID(companyID);
            if (company == null) {
                LOG.log(Level.WARNING, "Cislo miestnosti je neplatne");
                errorBuffer.append(rbSk.getString("userReg.wrongRoomNumber"));
                errorBuffer.append("\n");
            }
            if (isUsedUsername(username)) {
                LOG.log(Level.WARNING, "Pouzivatelske meno je pouzivane");
                errorBuffer.append(rbSk.getString("userReg.usedUsername"));
                errorBuffer.append("\n");
            }

            session.save(new Person(firstName, lastName, username, password, mail, phoneNumber, company));

            t.commit();

        } catch(Exception e) {
            LOG.log(Level.SEVERE, "Nepodarilo sa pridat zaznam");
            errorBuffer.append(rbSk.getString("userReg.cantInsert"));
            errorBuffer.append("\n");
        } finally {
            session.close();
        }
        return errorBuffer;
    }
    public static StringBuffer updatePerson(Person person, String firstName, String surname,
                                            String mail, String phoneNumber) {
        Logger LOG = ProgramData.getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(firstName)) {
            LOG.log(Level.INFO, "Nevyplnene pole krstne meno");
            errorBuffer.append(rbSk.getString("userReg.missingFirstName"));
            errorBuffer.append("\n");
        }
        if ("".equals(surname)) {
            LOG.log(Level.INFO, "Nevyplnene pole priezvisko");
            errorBuffer.append(rbSk.getString("userReg.missingLastName"));
            errorBuffer.append("\n");
        }
        if (errorBuffer.length() > 0) {
            return errorBuffer;
        }

        Session session = CreateDatabase.getSession();
        Transaction t = session.beginTransaction();

        person.setFirstName(firstName);
        person.setLastName(surname);
        person.setMail(mail);
        person.setPhoneNumber(phoneNumber);

        session.update(person);

        t.commit();
        session.close();

        return errorBuffer;
    }
}
