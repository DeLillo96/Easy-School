package Client.Remote;

import Shared.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Implements RemoteServicesManager using RMI services
 * Each method returns a specific shared interface through the lookup() method
 */
public class RMIServicesManager implements RemoteServicesManager {
    private static Registry registry = null;

    /**
     * Return a reference to registry for the localhost on assigned port 3692
     * @throws RemoteException
     */
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
    public BaseService getSupplierService() throws Exception {
        return (BaseService) registry.lookup("supplier");
    }

    @Override
    public BaseService getPediatricianService() throws Exception {
        return (BaseService) registry.lookup("pediatrician");
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
    public RelationService getRecipesService() throws Exception {
        return (RelationService) registry.lookup("recipes");
    }

    @Override
    public RelationService getParentService() throws Exception {
        return (RelationService) registry.lookup("parents");
    }

    @Override
    public RelationService getTripPlaceService() throws Exception {
        return (RelationService) registry.lookup("tripplace");
    }

    @Override
    public RelationService getSupplyingService() throws Exception {
        return (RelationService) registry.lookup("supply");
    }

    @Override
    public RelationService getChildPediatricianService() throws Exception {
        return (RelationService) registry.lookup("follow");
    }

    @Override
    public RelationService getDailyDishService() throws Exception {
        return (RelationService) registry.lookup("dailydish");
    }

    @Override
    public BaseService getDailyTripService() throws Exception {
        return (BaseService) registry.lookup("dailytrip");
    }

    @Override
    public RelationService getChildInTripService() throws Exception {
        return (RelationService) registry.lookup("childintrip");
    }

    @Override
    public BaseService getDishService() throws Exception {
        return (BaseService) registry.lookup("dish");
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
    public BaseService getStaffService() throws Exception {
        return (BaseService) registry.lookup("staff");
    }

    @Override
    public RelationService getBusTripService() throws Exception {
        return (RelationService) registry.lookup("bustrip");
    }

    @Override
    public RelationService getPlaceInTripService() throws Exception {
        return (RelationService) registry.lookup("placeintrip");
    }

    @Override
    public TernaryRelationService getChildInVehicleService() throws Exception {
        return (TernaryRelationService) registry.lookup("childinvehicle");
    }


    @Override
    public void closeConnection() throws Exception {
    }
}
