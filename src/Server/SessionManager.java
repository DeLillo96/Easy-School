package Server;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionManager {

    private static SessionFactory sessionFactory = null;
    private static SessionManager instance = null;
    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("Server/hibernate.cfg.xml").build();;

    private SessionManager() {
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }


    public static SessionFactory getSessionFactory() {
        if (instance ==  null) {
            instance = new SessionManager();
        }
        return sessionFactory;
    }

    public static void destroySessionFactory() {
        StandardServiceRegistryBuilder.destroy(registry);
        sessionFactory = null;
        instance = null;
    }
}
