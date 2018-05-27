package Server;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class which handles Hibernate's session
 */
public class SessionManager {

    private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("Server/hibernate.cfg.xml").build();
    private static SessionFactory sessionFactory = null;
    private static SessionManager instance = null;

    private SessionManager() {
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() throws Exception {
        if (instance == null) {
            instance = new SessionManager();
        }
        if (sessionFactory == null) {
            throw new Exception("Connection Error!");
        }
        return sessionFactory;
    }

    public static void destroySessionFactory() {
        StandardServiceRegistryBuilder.destroy(registry);
        sessionFactory = null;
        instance = null;
    }
}
