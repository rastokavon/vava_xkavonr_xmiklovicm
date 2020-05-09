package managers;

import database.Company;
import database.CreateDatabase;
import database.Person;
import database.Post;
import javafx.geometry.Pos;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ManagerPosts {

    public static List<Post> getPosts(Person person) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        Criteria crit = session.createCriteria(Post.class);
        crit.add(Restrictions.eq("person.id", person.getId()));
        List<Post> results = crit.list();
        if (!results.isEmpty()) {
            results.get(0).getPerson().getFirstName();
        }
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public static List<Post> getPosts(Company company) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();

        List<Person> users = ManagerPerson.getUsers("", company.getId());
        List<Post> posts = new ArrayList<Post>();

        for (Person p : users) {
            List<Post> tmpPosts = ManagerPosts.getPosts(p);
            if (tmpPosts == null) continue;
            for (Post pt : tmpPosts) {
                posts.add(pt);
            }
        }

        session.getTransaction().commit();
        session.close();

        if (posts.isEmpty()) {
            return null;
        }
        return posts;
    }


}
