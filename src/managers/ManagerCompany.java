package managers;

import database.Company;
import database.CreateDatabase;
import database.ProgramData;
import javafx.scene.control.Alert;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerCompany {

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

    public static Integer getCompanyIDFromName(String comapanyName) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Company> cr = cb.createQuery(Company.class);
        Root<Company> root = cr.from(Company.class);

        cr.select(root).where(cb.equal(root.get("name"), comapanyName));

        Query<Company> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Company> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0).getId();
    }

    public static StringBuffer createCompany(String name, String street, String city,
                                     String country, String postalCode, String mail, String phoneNumber) {

        Logger LOG = ProgramData.getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(street)) {
            LOG.log(Level.INFO, "Nevyplnene pole ulica");
            errorBuffer.append(rbSk.getString("companyReg.missingStreet"));
            errorBuffer.append("\n");
        }
        if ("".equals(city)) {
            LOG.log(Level.INFO, "Nevyplnene pole mesto");
            errorBuffer.append(rbSk.getString("companyReg.missingCity"));
            errorBuffer.append("\n");
        }
        if ("".equals(country)) {
            LOG.log(Level.INFO, "Nevyplnene pole stat");
            errorBuffer.append(rbSk.getString("companyReg.missingCountry"));
            errorBuffer.append("\n");
        }
        if ("".equals(postalCode)) {
            LOG.log(Level.INFO, "Nevyplnene pole PSC");
            errorBuffer.append(rbSk.getString("companyReg.missingPostalCode"));
            errorBuffer.append("\n");
        }
        if ("".equals(mail)) {
            LOG.log(Level.INFO, "Nevyplnene pole mail");
            errorBuffer.append(rbSk.getString("companyReg.missingMail"));
            errorBuffer.append("\n");
        }
        if ("".equals(name)) {
            LOG.log(Level.INFO, "Nevyplnene pole nazov firmy");
            errorBuffer.append(rbSk.getString("companyReg.missingName"));
            errorBuffer.append("\n");
            return errorBuffer;
        }

        Session session = CreateDatabase.getSession();
        try {
            Transaction t = session.beginTransaction();

            session.save(new Company(name, street, city, country, postalCode, mail, phoneNumber));
            String bundle1 = ProgramData.getInstance().getLanguage();
            ResourceBundle rbSk1 = ResourceBundle.getBundle(bundle1 + "_popup", Locale.forLanguageTag("info"));
            t.commit();

            System.out.println(rbSk1.getString("companyReg.title"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rbSk1.getString("companyReg.title"));
            alert.setContentText(rbSk1.getString("companyReg.text") + "                 " +
                    String.valueOf(getCompanyIDFromName(name)));
            alert.showAndWait();

        } catch (Exception e) {
            LOG.log(Level.WARNING, "Nazov firmy je uz zaregistrovany");
            errorBuffer.append(rbSk.getString("companyReg.isRegistered"));
            errorBuffer.append("\n");
            return errorBuffer;
        }finally {
            session.close();
        }

        return errorBuffer;
    }
}
