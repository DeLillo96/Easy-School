package Client.Remote;

import Shared.UserService;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServicesManager implements RemoteServicesManager {
    private static Registry registry = null;

    public RMIServicesManager() throws RemoteException {
        registry = LocateRegistry.getRegistry("localhost", 3692);
    }

    public UserService getUserService() throws Exception {
        if (registry == null) new RMIServicesManager();
        return (UserService) registry.lookup("users");
    }
}
