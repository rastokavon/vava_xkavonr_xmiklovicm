package database;

import controllers.MainController;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CreateEntity {

    public static Company getCompanyFromID(Integer companyID) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Company> cr = cb.createQuery(Company.class);
        Root<Company> root = cr.from(Company.class);
        cr.select(root);
        cr.select(root).where(cb.gt(root.get("id"), companyID));

        Query<Company> query = session.createQuery(cr);
        query.setMaxResults(1);
        List<Company> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        System.out.println(results.isEmpty());

        return results.get(0);
    }

    public static void createPerson(String firstName, String lastName, String username,
                                    String password, String mail, String phoneNumber, int company) {
        Session session = CreateDatabase.getSession();
        Transaction t = session.beginTransaction();

        session.save(new Person(firstName, lastName, username, password, mail, phoneNumber, getCompanyFromID(company)));

        t.commit();
        session.close();

        System.out.println("Successfully saved new person...");
    }
    public static void createCompany(String name, String street, String city,
                                     String country, String postalCode, String mail, String phoneNumber) {

        Session session = CreateDatabase.getSession();
        Transaction t = session.beginTransaction();

        session.save(new Company(name, street, city, country, postalCode, mail, phoneNumber));

        t.commit();

        session.close();
        System.out.println("Successfully saved new company...");
    }
}
