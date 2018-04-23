package Client.Remote;

import Shared.BaseService;
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
        return (UserService) registry.lookup("users");
    }

    @Override
    public BaseService getChildrenService() throws Exception {
        return (BaseService) registry.lookup("children");
    }

    @Override
    public BaseService getMenuService() throws Exception {
        return (BaseService) registry.lookup("menu");
    }

    @Override
    public BaseService getDishService() throws Exception {
        return (BaseService) registry.lookup("dish");
    }

    @Override
    public void closeConnection() throws Exception {}
}
