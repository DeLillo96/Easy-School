package Client.Remote;

import Shared.BaseService;
import Shared.DishService;
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
        return (UserService) registry.lookup("user");
    }

    @Override
    public BaseService getChildrenService() throws Exception {
        return (BaseService) registry.lookup("child");
    }

    @Override
    public BaseService getMenuService() throws Exception {
        return (BaseService) registry.lookup("menu");
    }

    @Override
    public DishService getDishService() throws Exception {
        return (DishService) registry.lookup("dish");
    }

    @Override
    public BaseService getAdultService() throws Exception {
        return (BaseService) registry.lookup("adult");
    }

    @Override
    public BaseService getEatingDisorderService() throws Exception {
        return (BaseService) registry.lookup("eatingdisorder");
    }

    @Override
    public void closeConnection() throws Exception {}
}
