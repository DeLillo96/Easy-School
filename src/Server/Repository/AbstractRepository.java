package Server.Repository;

import Server.Entity.EntityInterface;
import Server.Result;
import Server.SessionManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository implements Repository{
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
    public List read(HashMap<String, HashMap<String, Object>> filters) {
        Session session = null;

        try {
            session = SessionManager.getSessionFactory().openSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(filters != null) {
            for (Map.Entry filtersEntry : filters.entrySet()) {
                HashMap<String, Object> params = (HashMap<String, Object>) filtersEntry.getValue();
                if(params != null) {
                    Filter filter = session.enableFilter((String) filtersEntry.getKey());
                    prepareFilter(filter, params);
                }
            }
        }

        Transaction tx = session.beginTransaction();
        List list = null;
        try {
            list = session.createQuery("FROM " + tableName).list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public List read(String filterName, HashMap<String, Object> params) {
        HashMap<String, HashMap<String, Object>> filters = null;

        if(filterName != null && params != null) {
            filters = new HashMap<>();
            filters.put(filterName, params);
        }
        return read(filters);
    }

    @Override
    public Result save(List<EntityInterface> list) {
        Result result = new Result();
        Result probe;
        for (EntityInterface entity : list) {
            probe = entity.save();
            if( !probe.isSuccess() ) {
                result.setSuccess(false);
                result.addMessages(probe.getMessages());
            }
        }
        return result;
    }

    protected void prepareFilter(Filter filter, HashMap<String, Object> params) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry param = (Map.Entry)it.next();
            filter.setParameter((String) param.getKey(), param.getValue());
            it.remove();
        }
    }
}
