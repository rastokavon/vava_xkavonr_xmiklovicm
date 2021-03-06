package managers;

import database.Company;
import main.CreateDatabase;
import main.ProgramData;
import org.hibernate.*;
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
 * trieda predstavuje vrsvu mezdi databzou a vystupom pre pouzivatelov
 * vykonava vsetku pracu okolo Company
 *
 * obsahuje rozne metody, ktore "prekladaju" vstupy z obrazoviek do databazy
 * vie podla cisla najst dany objekt v databaze
 * podla zadanych udajov vytvorit objekt v databaze
 * modifikovat vytvoreny objekt Company v databaze
 * vygenerovat heslo pre Company
 * overovat registraciu
 */
public class ManagerCompany {

    /**
     * Funkcia vyhlada konkretnu firmu na zaklade zvoleneho ID.
     *
     * @param companyID - ID firmy
     * @return objekt firmy
     */
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
            return null;
        }

        return results.get(0);
    }

    /**
     * Funkcia zaregistruje do systemu novu firmu so zadanymi parametrami.
     * Pri chybe posle cez navratovu hodnotu spravu ulozenu v stringu.
     *
     * @param name - nazov firmy
     * @param street - ulica sidla firmy
     * @param city- mesto sidla firmy
     * @param country- krajina sidla firmy
     * @param postalCode - PSC sidla firmy
     * @param mail - mail firmy
     * @param phoneNumber - telefonicky kontakt na firmu
     * @return chybova sprava, ak nejaka chyba nastala
     */
    public static StringBuffer createCompany(String name, String street, String city,
                                             String country, String postalCode, String mail, String phoneNumber) {

        Logger LOG = ProgramData.getInstance().getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(street)) {
            LOG.log(Level.WARNING, "Nevyplnene pole ulica");
            errorBuffer.append(rbSk.getString("companyReg.missingStreet"));
            errorBuffer.append("\n");
        }
        if ("".equals(city)) {
            LOG.log(Level.WARNING, "Nevyplnene pole mesto");
            errorBuffer.append(rbSk.getString("companyReg.missingCity"));
            errorBuffer.append("\n");
        }
        if ("".equals(country)) {
            LOG.log(Level.WARNING, "Nevyplnene pole stat");
            errorBuffer.append(rbSk.getString("companyReg.missingCountry"));
            errorBuffer.append("\n");
        }
        if ("".equals(postalCode)) {
            LOG.log(Level.WARNING, "Nevyplnene pole PSC");
            errorBuffer.append(rbSk.getString("companyReg.missingPostalCode"));
            errorBuffer.append("\n");
        }
        if ("".equals(mail)) {
            LOG.log(Level.WARNING, "Nevyplnene pole mail");
            errorBuffer.append(rbSk.getString("companyReg.missingMail"));
            errorBuffer.append("\n");
        }
        if ("".equals(name)) {
            LOG.log(Level.WARNING, "Nevyplnene pole nazov firmy");
            errorBuffer.append(rbSk.getString("companyReg.missingName"));
            errorBuffer.append("\n");
        }

        if (errorBuffer.length() > 0) {
            return errorBuffer;
        }

        Session session = CreateDatabase.getSession();
        try {
            Transaction t = session.beginTransaction();

            String password = generatePassword();
            Company company = new Company(name, street, city, country, postalCode, mail, phoneNumber, password);

            session.save(company);
            t.commit();

            ProgramData.getInstance().setCurrentlyRegCompany(company);


        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nazov firmy je uz zaregistrovany");
            errorBuffer.append(rbSk.getString("companyReg.isRegistered"));
            errorBuffer.append("\n");
            return errorBuffer;
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return errorBuffer;
    }

    /**
     * Funkcia zisti, ci zadane cislo miestnosti a heslo tvoria par, teda ci je firma zaregistrovana v danej miestnosti.
     *
     * @param roomNumber - cislo miestnosti
     * @param password - heslo
     * @return firma, ak tato firma je registrovana
     */
    public static Company isRegistered(int roomNumber, String password) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Company> cr = cb.createQuery(Company.class);
        Root<Company> root = cr.from(Company.class);

        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("password"), password);
        predicates[1] = cb.equal(root.get("id"), roomNumber);
        cr.select(root).where(predicates);

        Query<Company> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Company> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    /**
     * Funkcia nastavi heslo firmy na ine zvolene (teda funkcia zmeni heslo).
     *
     * @param company - firma
     * @param password - heslo firmy
     */
    public static void changePassword(Company company, String password) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();

        company.setPassword(password);
        session.update(company);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Funkcia vygeneruje nove heslo pre registrovnu firmu. Heslo je dlhe 10 znakov a sklada sa z cisel a pismen.
     */
    public static String generatePassword() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        int lenght = 10;
        StringBuilder sb = new StringBuilder(lenght);

        for (int i = 0; i < lenght; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Funkcia modifikuje zaznam o firme. Kazda firma si moze upravit profil a tato netoda nasledne ulozi informacie
     * o novych informaciach o firme.
     *
     * @param company - firma
     * @param street - ulica sidla firmy
     * @param city- mesto sidla firmy
     * @param country- krajina sidla firmy
     * @param postalCode - PSC sidla firmy
     * @param mail - mail firmy
     * @param phoneNumber - telefonicky kontakt na firmu
     * @return chybova sprava, ak nejaka chyba nastala
     */
    public static StringBuffer updateCompany(Company company, String street, String city, String country,
                                             String postalCode, String mail, String phoneNumber) {

        Logger LOG = ProgramData.getInstance().getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(street)) {
            LOG.log(Level.WARNING, "Nevyplnene pole ulica");
            errorBuffer.append(rbSk.getString("companyReg.missingStreet"));
            errorBuffer.append("\n");
        }
        if ("".equals(city)) {
            LOG.log(Level.WARNING, "Nevyplnene pole mesto");
            errorBuffer.append(rbSk.getString("companyReg.missingCity"));
            errorBuffer.append("\n");
        }
        if ("".equals(country)) {
            LOG.log(Level.WARNING, "Nevyplnene pole stat");
            errorBuffer.append(rbSk.getString("companyReg.missingCountry"));
            errorBuffer.append("\n");
        }
        if ("".equals(postalCode)) {
            LOG.log(Level.WARNING, "Nevyplnene pole PSC");
            errorBuffer.append(rbSk.getString("companyReg.missingPostalCode"));
            errorBuffer.append("\n");
        }
        if ("".equals(mail)) {
            LOG.log(Level.WARNING, "Nevyplnene pole mail");
            errorBuffer.append(rbSk.getString("companyReg.missingMail"));
            errorBuffer.append("\n");
        }

        if (errorBuffer.length() > 0) {
            return errorBuffer;
        }

        Session session = CreateDatabase.getSession();
        try {
            Transaction t = session.beginTransaction();


            company.setStreet(street);
            company.setCity(city);
            company.setCountry(country);
            company.setPostalCode(postalCode);
            company.setPhoneNumber(phoneNumber);
            company.setMail(mail);

            session.update(company);

            t.commit();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Neocakavana chyba: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return errorBuffer;
    }
}
