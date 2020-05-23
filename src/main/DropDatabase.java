package main;

import org.hibernate.*;


/**
 *
 */
public class DropDatabase {
    public static void main(final String[] args) throws Exception {
        Session session = CreateDatabase.getSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            //session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            session.createSQLQuery("DROP TABLE [IF EXISTS] comment").executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();


//            LOG.log(Level.SEVERE, "Nazov firmy je uz zaregistrovany");
//            errorBuffer.append(rbSk.getString("companyReg.isRegistered"));
//            errorBuffer.append("\n");
        } finally {
            session.close();
        }
    }
}
