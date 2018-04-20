package Server.Remote.RMI;

import Server.Remote.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIManager {
    final private int port = 3692;

    public RMIManager() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);

        registry.rebind("users", new UserServiceImplementation());
        registry.rebind("children", new ChildrenServiceImplementation());
        registry.rebind("adults", new AdultServiceImplementation());
        registry.rebind("aliments", new AlimentServiceImplementation());
        registry.rebind("buses", new BusServiceImplementation());
        registry.rebind("calendars", new CalendarServiceImplementation());
        registry.rebind("categories", new CategoryServiceImplementation());
        registry.rebind("daytrips", new DayTripServiceImplementation());
        registry.rebind("dishes", new DishServiceImplementation());
        registry.rebind("eatingDisorders", new EatingDisorderServiceImplementation());
        registry.rebind("menus", new MenuServiceImplementation());
        registry.rebind("places", new PlaceServiceImplementation());
    }

    public int getPort() {
        return port;
    }
}
