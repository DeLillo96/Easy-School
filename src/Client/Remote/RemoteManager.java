package Client.Remote;

public class RemoteManager {
    private static RemoteManager instance;
    private static RemoteServicesManager remoteServicesManager;

    public RemoteServicesManager getRemoteServicesManager() {
        return remoteServicesManager;
    }

    public void setService(RemoteServicesManager remoteServicesManager) {
        RemoteManager.remoteServicesManager = remoteServicesManager;
    }

    public static RemoteManager getInstance() {
        if(instance == null) {
            instance = new RemoteManager();
        }
        return instance;
    }
}
