package Server.Entity;

import Server.SessionManager;
import Server.Result;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractEntity implements EntityInterface {
    @Override
    public Result save() {
        beforeSave();

        Result result = new Result();
        Session session = SessionManager.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            if (primaryKeysAreValid()) {
                session.update(this);
            } else {
                session.persist(this);
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
        Session session = SessionManager.getSessionFactory().openSession();
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

    protected abstract boolean primaryKeysAreValid();
}
