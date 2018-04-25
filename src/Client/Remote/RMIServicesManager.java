package Client.Remote;

import Shared.AdultService;
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
    public AdultService getAdultService() throws Exception {
        return (AdultService) registry.lookup("adult");
    }

    @Override
    public BaseService getEatingDisorderService() throws Exception {
        return (BaseService) registry.lookup("eatingdisorder");
    }

    @Override
    public BaseService getAlimentService() throws Exception {
        return (BaseService) registry.lookup("aliment");
    }

    @Override
    public BaseService getBusService() throws Exception {
        return (BaseService) registry.lookup("buse");
    }

    @Override
    public BaseService getCalendarService() throws Exception {
        return (BaseService) registry.lookup("calendar");
    }

    @Override
    public BaseService getCategoryService() throws Exception {
        return (BaseService) registry.lookup("category");
    }

    @Override
    public BaseService getDayTripService() throws Exception {
        return (BaseService) registry.lookup("daytrip");
    }

    @Override
    public BaseService getPlaceService() throws Exception {
        return (BaseService) registry.lookup("place");
    }

    @Override
    public void closeConnection() throws Exception {}
}
