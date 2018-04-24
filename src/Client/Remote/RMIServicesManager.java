package Client.Remote;

import Shared.AdultService;
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
    public AdultService getAdultService() throws Exception {
        return (AdultService) registry.lookup("adults");
    }

    @Override
    public BaseService getAlimentService() throws Exception {
        return (BaseService) registry.lookup("aliments");
    }

    @Override
    public BaseService getBusService() throws Exception {
        return (BaseService) registry.lookup("buses");
    }

    @Override
    public BaseService getCalendarService() throws Exception {
        return (BaseService) registry.lookup("calendars");
    }

    @Override
    public BaseService getCategoryService() throws Exception {
        return (BaseService) registry.lookup("categories");
    }

    @Override
    public BaseService getDayTripService() throws Exception {
        return (BaseService) registry.lookup("daytrips");
    }

    @Override
    public BaseService getDishService() throws Exception {
        return (BaseService) registry.lookup("dishes");
    }

    @Override
    public BaseService getEatingDisorderService() throws Exception {
        return (BaseService) registry.lookup("eatingDisorders");
    }

    @Override
    public BaseService getMenuService() throws Exception {
        return (BaseService) registry.lookup("menus");
    }

    @Override
    public BaseService getPlaceService() throws Exception {
        return (BaseService) registry.lookup("places");
    }

    @Override
    public void closeConnection() throws Exception {}
}
