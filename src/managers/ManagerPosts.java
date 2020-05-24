package managers;

import database.*;
import main.CreateDatabase;
import main.ProgramData;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * trieda predstavuje vrstvu, ktora prepojuje obrazovky s databazou
 * vykonava vsetky potrebne ukony pre pridavanie prispevkov z pohladu logiky programu
 *
 * vie vyberat z databazy prispevky podla roznych zadanych kriterii:
 *  tie ktore patria do miestnosti
 *  danemu pouzivatelovi
 *  vyhladavanie prispevku podla nazvu
 *
 * rovnako pri vytvarani prispevku je komunikacia s databozou prostrednictvom vrtvy ManagerPosts
 */
public class ManagerPosts {

    /**
     * Funkcia vyhlada prispevok na zaklade nadpisu prispevku (ktory je jedinecny pre kazdy prispevok) a vrati ho
     * cez navratovu hodnotu funkcie.
     *
     * @param title - nadpis
     * @return prispevok
     */
    public static Post getPost(String title) {
        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        Criteria crit = session.createCriteria(Post.class);
        crit.add(Restrictions.eq("title", title));
        List<Post> results = crit.list();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results.get(0);
    }

    /**
     * Funkcia vrati vsetky prispevky pouzivatela.
     *
     * @param person - pouzivatel
     * @return zoznam prispevkov
     */
    public static List<Post> getPosts(Person person) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        Criteria crit = session.createCriteria(Post.class);
        crit.add(Restrictions.eq("person.id", person.getId()));
        List<Post> results = crit.list();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    /**
     * Funkcia vrati zoznam vsetkych prispevkov, ktore boli napisane pouzivatelmi z danej firmy.
     *
     * @param company - firma
     * @return zoznam prispevkov
     */
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

    /**
     * Funkcia vytvori novy prispevok. V pripade neuspechu posle cez navratovu hodnotu chybovu hlasku.
     *
     * @param title - nadpis
     * @param postText - text prispevku
     * @param person - pouzivatel
     * @return chybova sprava, ak nejaka chyba nastala
     */
    public static StringBuffer createPost(String title, String postText, Person person) {
        Logger LOG = ProgramData.getInstance().getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(title)) {
            LOG.log(Level.WARNING, "Nevyplnene pole nadpis");
            errorBuffer.append(rbSk.getString("addPost.missingTitle"));
            errorBuffer.append("\n");
        }
        if ("".equals(postText)) {
            LOG.log(Level.WARNING, "Nevyplnene pole textu prispevku");
            errorBuffer.append(rbSk.getString("addPost.missingPost"));
            errorBuffer.append("\n");
        }
        if (title.length() > 40) {
            LOG.log(Level.WARNING, "Prilis dlhy nadpis");
            errorBuffer.append(rbSk.getString("addPost.longTitle"));
            errorBuffer.append("\n");
        }
        if (postText.length() > 1000) {
            LOG.log(Level.WARNING, "Prilis dlhy text prispevku");
            errorBuffer.append(rbSk.getString("addPost.longPost"));
            errorBuffer.append("\n");
        }

        Session session = CreateDatabase.getSession();

        if (errorBuffer.length() > 0) {
            return errorBuffer;
        }
        try {
            Transaction t = session.beginTransaction();
            Date date = new Date();

            session.save(new Post(title, postText, date, person));

            t.commit();

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nepodarilo sa pridat prispevok");
            errorBuffer.append(rbSk.getString("addPost.postTitleExist"));
            errorBuffer.append("\n");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return errorBuffer;
    }
}
