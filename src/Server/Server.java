package Server;

import Server.Controller.UserControllerImplementation;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private static int port = 1099;

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.WARNING);

        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("users", new UserControllerImplementation());

            System.out.println("Server listening on port: " + port);
        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            SessionManager.destroySessionFactory();
        }

    }
}