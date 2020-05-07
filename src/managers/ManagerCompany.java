package managers;

import database.Company;
import database.CreateDatabase;
import database.ProgramData;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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

    public static boolean createCompany(String name, String street, String city,
                                     String country, String postalCode, String mail, String phoneNumber) {

        Logger LOG = ProgramData.getLOG();

        if ("".equals(name)) {
            LOG.log(Level.INFO, "Nevyplnene pole nazov firmy");
            return false;
        }
        if ("".equals(street)) {
            LOG.log(Level.INFO, "Nevyplnene pole ulica");
            return false;
        }
        if ("".equals(city)) {
            LOG.log(Level.INFO, "Nevyplnene pole mesto");
            return false;
        }
        if ("".equals(country)) {
            LOG.log(Level.INFO, "Nevyplnene pole stat");
            return false;
        }
        if ("".equals(postalCode)) {
            LOG.log(Level.INFO, "Nevyplnene pole PSC");
            return false;
        }
        if ("".equals(mail)) {
            LOG.log(Level.INFO, "Nevyplnene pole mail");
            return false;
        }

        Session session = CreateDatabase.getSession();
        try {
            Transaction t = session.beginTransaction();

            session.save(new Company(name, street, city, country, postalCode, mail, phoneNumber));

            t.commit();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Nazov firmy je uz zaregistrovany");
            return false;
        }finally {
            session.close();
        }

        return true;
    }
}
