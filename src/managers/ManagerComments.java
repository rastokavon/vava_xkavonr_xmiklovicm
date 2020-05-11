package managers;

import database.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagerComments {

    public static List<Comment> getComments(Post post) {

        Session session = CreateDatabase.getSession();
        session.beginTransaction();
        Criteria crit = session.createCriteria(Comment.class);
        crit.add(Restrictions.eq("post", post));
        List<Comment> results = crit.list();
        session.getTransaction().commit();
        session.close();

        if (results.isEmpty()) {
            return null;
        }

        return results;
    }

    public static StringBuffer createComment(String commentText, Post post, Person person) {
        Logger LOG = ProgramData.getLOG();
        String bundle = ProgramData.getInstance().getLanguage();
        ResourceBundle rbSk = ResourceBundle.getBundle(bundle + "_popup", Locale.forLanguageTag("error"));
        StringBuffer errorBuffer = new StringBuffer("");

        if ("".equals(commentText)) {
            LOG.log(Level.INFO, "Nevyplnene pole textu komentaru");
            errorBuffer.append(rbSk.getString("addComment.missingText"));
            errorBuffer.append("\n");
        }
        if (commentText.length() > 1000) {
            LOG.log(Level.INFO, "Prilis dlhy text komentaru");
            errorBuffer.append(rbSk.getString("addComment.longComment"));
            errorBuffer.append("\n");
        }

        Session session = CreateDatabase.getSession();

        if (errorBuffer.length() > 0) {
            return errorBuffer;
        }
        try {
            Transaction t = session.beginTransaction();
            Date date = new Date();

            session.save(new Comment(commentText, date, post, person));

            t.commit();

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nepodarilo sa pridat komentar");
            errorBuffer.append("Co sa to dokazilo?");
            errorBuffer.append("\n");
        } finally {
            session.close();
        }
        return errorBuffer;
    }
}
