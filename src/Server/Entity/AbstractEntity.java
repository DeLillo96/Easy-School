package Server.Entity;

import Server.Result;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public abstract class AbstractEntity implements EntityInterface {
    protected String tableName;
    protected SessionFactory sessionFactory;

    public AbstractEntity() {
        setSessionFactory();
    }

    @Override
    public Result save() {
        beforeSave();

        Result result = new Result();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {

            if (primaryKeysAreValid()) {
                session.update(this);
            } else {
                session.update(this);
            }

            session.update(this);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();

            result.setSuccess(false);
            result.addMessage(e.getMessage());
        } finally {
            session.close();
        }

        afterSave();

        return result;
    }

    @Override
    public Result delete() {
        beforeDelete();

        Result result = new Result();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            if (primaryKeysAreValid()) {
                session.delete(this);
            } else throw new Exception("Primary Keys are not valid");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();

            result.setSuccess(false);
            result.addMessage(e.getMessage());
        } finally {
            session.close();
        }

        afterDelete();

        return result;
    }

    protected void beforeSave() { }
    protected void afterSave() { }
    protected void beforeDelete() { }
    protected void afterDelete() { }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
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

    protected abstract boolean primaryKeysAreValid();
}
