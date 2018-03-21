package Server;

import Server.Remote.RMI.RMIManager;
import Server.Remote.Socket.SocketManager;

public class Server {
    public static void main(String[] args) throws Exception {
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.WARNING);

        RMIManager rmiManager = new RMIManager();
        System.out.println("Server listening on port: " + rmiManager.getPort());

        SocketManager socketSocketManager = new SocketManager();
        System.out.println("Server listening on port: " + socketSocketManager.getPort());
    }
}