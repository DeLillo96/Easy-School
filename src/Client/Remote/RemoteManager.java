package Client.Remote;

public class RemoteManager {
    private static RemoteManager instance;
    private static RemoteServicesManager remoteServicesManager;

    /**
     * Method used to generate a single instance of a RemoteManager object (Singleton class)
     * @return instance of RemoteManager
     */
    public static RemoteManager getInstance() {
        if (instance == null) {
            instance = new RemoteManager();
        }
        return instance;
    }

    /**
     * Method used to get the type of remote services previously assigned to RemoteManager
     * @return type of remote services to assign to RemoteManager
     */
    public RemoteServicesManager getRemoteServicesManager() {
        return remoteServicesManager;
    }

    /**
     * Method used to set the type of remote services (Socket/RMI) that will be used by the RemoteManager, according to
     * defined input remoteServicesManager
     * @param remoteServicesManager type of remote services assigned to RemoteManager
     */
    public void setService(RemoteServicesManager remoteServicesManager) {
        RemoteManager.remoteServicesManager = remoteServicesManager;
    }

    /**
     * Closes the connection between client and server
     */
    public void closeServices() {
        try {
            remoteServicesManager.closeConnection();
        } catch (Exception ignored) {
        }
    }
}
