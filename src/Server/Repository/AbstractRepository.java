package Server.Repository;

import Server.Entity.EntityInterface;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractRepository implements Repository{
    protected String tableName;
    protected SessionFactory sessionFactory;

    public AbstractRepository() {
        this("");
    }

    public AbstractRepository(String tableName) {
        this.tableName = tableName;
        setSessionFactory();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private void setSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("Server/hibernate.cfg.xml").build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public List readAll() {
        return read(null);
    }

    @Override
    public List read(HashMap<String, Object> params) {
        Session session = sessionFactory.openSession();
        if(params != null) prepareFilter(session, params);

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
    public void save(List<EntityInterface> list) {
        for (EntityInterface entity : list) {
            entity.save();
        }
    }

    protected abstract Session prepareFilter(Session session, HashMap<String, Object> params);
}
