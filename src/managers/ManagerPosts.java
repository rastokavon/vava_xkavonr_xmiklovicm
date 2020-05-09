package managers;

import database.Company;
import database.CreateDatabase;
import database.Person;
import database.Post;
import javafx.geometry.Pos;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ManagerPosts {

    public static List<Post> getPosts(Person person) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> cr = cb.createQuery(Post.class);
        Root<Post> root = cr.from(Post.class);

        cr.select(root).where(cb.equal(root.get("person"), person));

        Query<Post> query = session.createQuery(cr);
        List<Post> results = query.getResultList();
        session.getTransaction().commit();
        session.close();

        System.out.println("Preslo to");
        if (results.isEmpty()) {
            return null;
        }
        return results;

    }
}
