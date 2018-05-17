package Client.Remote;

import Shared.*;

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
    public BaseService getAdultService() throws Exception {
        return (BaseService) registry.lookup("adult");
    }

    @Override
    public EatingDisorderService getEatingDisorderService() throws Exception {
        return (EatingDisorderService) registry.lookup("eatingdisorder");
    }

    @Override
    public BaseService getAlimentService() throws Exception {
        return (BaseService) registry.lookup("aliment");
    }

    @Override
    public BaseService getBusService() throws Exception {
        return (BaseService) registry.lookup("bus");
    }

    @Override
    public BaseService getCalendarService() throws Exception {
        return (BaseService) registry.lookup("calendar");
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
    public AssignService getRecipesService() throws Exception {
        return (AssignService) registry.lookup("recipes");
    }

    @Override
    public AssignService getParentService() throws Exception {
        return (AssignService) registry.lookup("parents");
    }

    @Override
    public AssignService getBusStartingPlaceService() throws Exception {
        return (AssignService) registry.lookup("busstartingplace");
    }

    @Override
    public AssignService getBusArrivalPlaceService() throws Exception {
        return (AssignService) registry.lookup("busdestinationplace");
    }

    @Override
    public AssignService getTripPlaceService() throws Exception {
        return (AssignService) registry.lookup("tripplace");
    }

    @Override
    public BaseService getFirstDishService() throws Exception {
        return (BaseService) registry.lookup("firstdish");
    }

    @Override
    public BaseService getSecondDishService() throws Exception {
        return (BaseService) registry.lookup("seconddish");
    }

    @Override
    public BaseService getSideDishService() throws Exception {
        return (BaseService) registry.lookup("sidedish");
    }

    @Override
    public BaseService getSweetDishService() throws Exception {
        return (BaseService) registry.lookup("sweetdish");
    }

    @Override
    public void closeConnection() throws Exception {
    }
}
