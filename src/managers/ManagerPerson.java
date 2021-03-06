package managers;

import database.Company;
import main.CreateDatabase;
import database.Person;
import main.ProgramData;
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


/**
 * trieda predstavuje vrstvu medzi vstupmi pouzivatela z obrazoviek a databazou
 * vykonava vsetku potrebnu pracu okolo entity Person
 *
 * podla vstupov od pouzivatela vie vytvorit novy objekt (registracia)
 * vymazat dany objekt (spravovatel miestnosti)
 * updateovat hodnoty
 * vyhodnocuje rozne potrebne veci, ktore mozu vzniknut pri registracii (napriklad rovnake meno,
 * pouzivatel uz je registrovany)
 * vracia zoznam uzivatelov pre danu miestnost
 */
public class ManagerPerson {

    /**
     * Funkcia vrati boolean hodnotu podla toho, ci je zadane pouzivatelske meno uz pouzivane alebo nie.
     *
     * @param username - pouzivatelske meno
     * @return boolean hodnota
     */
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

    /**
     * Funkcia zisti, ci zadane meno a heslo tvoria par, teda ci existuje pouzivatel so zadanymi udajmi.
     *
     * @param username - pouzivatelske meno
     * @param password - heslo
     * @return objekt pouzivatela
     */
    public static Person isRegistered(String username, String password) {

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

    /**
     * Funkcia zmeni heslo na nove heslo, ktore pouzivatel zada.
     *
     * @param person - pouzivatel
     * @param password - heslo
     */
    public static void changePassword(Person person, String password) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();

        person.setPassword(password);
        session.update(person);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Funkcia vrati zoznam pouzivatelov zaregistrovanych v danej miestnosti. Parameter name je nastaveny defaultne
     * na "", avsak po vyhladani konkretneho meno alebo casti mena funkcia vrati iba danych pouzivatelov, ktori
     * splnaju kriteria.
     *
     * @param name - pouzivatelske meno
     * @param roomId - cislo miestnosti
     * @return zoznam pouzivatelov
     */
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

    /**
     * Funkcia vymaze pouzivatela zo systemu na zaklade zadaneho pouzivatelskeho mena.
     *
     * @param username - pouzivatelske meno
     */
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

    /**
     * Funkcia zaregistruje pouzivatela do systemu. Ak to nie je mozne, metoda cez navratovu hodnotu posle spravu
     * ulozenu v stringu.
     *
     * @param firstName - krstne meno
     * @param lastName - priezvisko
     * @param username - pouzivatelske meno
     * @param password - heslo
     * @param mail - mailova adresa
     * @param phoneNumber - telefonne cislo
     * @param companyID - cislo miestnosti
     * @return chybova sprava, ak nejaka chyba nastala
     */
    public static StringBuffer createPerson(String firstName, String lastName, String username,
                                            String password, String mail, String phoneNumber, int companyID) {
        Logger LOG = ProgramData.getInstance().getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(firstName)) {
            LOG.log(Level.WARNING, "Nevyplnene pole meno");
            errorBuffer.append(rbSk.getString("userReg.missingFirstName"));
            errorBuffer.append("\n");
        }
        if ("".equals(lastName)) {
            LOG.log(Level.WARNING, "Nevyplnene pole priezvisko");
            errorBuffer.append(rbSk.getString("userReg.missingLastName"));
            errorBuffer.append("\n");
        }
        if ("".equals(username)) {
            LOG.log(Level.WARNING, "Nevyplnene pole prihlasovacie meno");
            errorBuffer.append(rbSk.getString("userReg.missingUsername"));
            errorBuffer.append("\n");
        }
        if ("".equals(password)) {
            LOG.log(Level.WARNING, "Nevyplnene pole heslo");
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

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nepodarilo sa pridat zaznam");
            errorBuffer.append(rbSk.getString("userReg.cantInsert"));
            errorBuffer.append("\n");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return errorBuffer;
    }

    /**
     * Funkcia pozmeni informacie o zadanom pouzivatelovi a ulozi ich do databazy pouzivatelov.
     *
     * @param person - pouzivatel
     * @param firstName - krstne meno
     * @param surname priezvisko
     * @param mail - mailova adresa
     * @param phoneNumber - telefonne cislo
     * @return chybova sprava, ak nejaka chyba nastala
     */
    public static StringBuffer updatePerson(Person person, String firstName, String surname,
                                            String mail, String phoneNumber) {
        Logger LOG = ProgramData.getInstance().getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(firstName)) {
            LOG.log(Level.WARNING, "Nevyplnene pole krstne meno");
            errorBuffer.append(rbSk.getString("userReg.missingFirstName"));
            errorBuffer.append("\n");
        }
        if ("".equals(surname)) {
            LOG.log(Level.WARNING, "Nevyplnene pole priezvisko");
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
