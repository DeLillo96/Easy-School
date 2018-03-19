package Server.Entity;

import Server.SessionManager;
import Server.Result;
import org.hibernate.Session;

public abstract class AbstractEntity implements EntityInterface {
    @Override
    public Result save() {
        beforeSave();

        Session session;
        try {
            session = SessionManager.getSessionFactory().openSession();
        } catch (Exception e) {
            return new Result(e.getMessage(), false);
        }

        session.beginTransaction();
        Result result = new Result();

        try {
            session.saveOrUpdate(this);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();

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

        Session session;
        try {
            session = SessionManager.getSessionFactory().openSession();
        } catch (Exception e) {
            return new Result(e.getMessage(), false);
        }

        session.beginTransaction();
        Result result = new Result();

        try {
            session.delete(this);

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();

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
}
