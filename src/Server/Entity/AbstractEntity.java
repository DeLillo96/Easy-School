package Server.Entity;

import Server.Result;
import Server.SessionManager;
import org.hibernate.Session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        result.addData(this);

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

    protected boolean validateString(String str, String regx) {
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    protected String nameCorrector(String str) {
       str = deleteMultipleSpaces(str);
       str = capitalizeFully(str);
       str = deleteFinalSpace(str);
       return str;
    }

    protected String deleteMultipleSpaces(String str) {
        while(str.contains("  ")) {
            str = str.replace("  ", " ");
        }
        return str;
    }

    protected String deleteFinalSpace(String str) {
        if(str.charAt(str.length()-1)==' ') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }

    protected String capitalizeFully(String str) {
        StringBuilder sb = new StringBuilder();
        boolean cnl = true;
        for (char c : str.toCharArray()) {
            if (cnl && Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
                cnl = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
            if (Character.isWhitespace(c)) {
                cnl = true;
            }
        }
        return sb.toString();
    }

    protected void beforeSave() {
    }

    protected void afterSave() {
    }

    protected void beforeDelete() {
    }

    protected void afterDelete() {
    }
}
