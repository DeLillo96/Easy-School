package Server.Repository;

import Server.Entity.EntityInterface;
import Server.Result;
import Server.SessionManager;
import org.hibernate.Filter;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Abstract class, each repository is linked to a specific DB table, implements read operations and instantiate save
 * operations
 */
public abstract class AbstractRepository implements Repository {
    protected String tableName;

    public AbstractRepository() {
        this("");
    }

    public AbstractRepository(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public List read() {
        return read(null);
    }

    @Override
    public List read(HashMap<String, Object> filters) {
        return read("FROM " + tableName, filters);
    }

    @Override
    public List read(String sql, HashMap<String, Object> filters) {
        Session session = null;

        try {
            session = SessionManager.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (filters != null) {
            prepareFilter(filters, session);
        }

        session.beginTransaction();
        List list = null;
        try {
            list = session.createQuery(sql).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public Result save(List<EntityInterface> list) {
        Result result = new Result();
        Result probe;
        for (EntityInterface entity : list) {
            probe = entity.save();
            if (!probe.isSuccess()) {
                result.setSuccess(false);
                result.addMessages(probe.getMessages());
            }
        }
        return result;
    }

    /**
     * Method used in read operations, prepares a reading session with specified parameters
     * @param filters (Specified read filters)
     * @param session (Hibernate session)
     */
    protected void prepareFilter(HashMap<String, Object> filters, Session session) {
        Iterator it = filters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry param = (Map.Entry) it.next();

            Filter filter = session.enableFilter((String) param.getKey());
            filter.setParameter((String) param.getKey(), param.getValue());

            it.remove();
        }
    }
}
