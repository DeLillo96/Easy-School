package Server.Repository;

import Server.Entity.Users;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UsersRepository extends AbstractRepository {

    public UsersRepository() { super("Users"); }

    @Override
    protected Session prepareFilter(Session session, HashMap<String, Object> params) {
        Filter filter = session.enableFilter("usersfilter");
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry param = (Map.Entry)it.next();
            filter.setParameter((String) param.getKey(), param.getValue());
            it.remove();
        }
        return session;
    }

    public Users getUserById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            return session.get(Users.class, id);
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
