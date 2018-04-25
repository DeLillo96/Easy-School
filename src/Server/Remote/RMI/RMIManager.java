package Server.Remote.RMI;

import Server.Remote.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIManager {
    final private int port = 3692;

    public RMIManager() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);

        registry.rebind("user", new UserServiceImplementation());
        registry.rebind("child", new ChildrenServiceImplementation());
        registry.rebind("menu", new MenuServiceImplementation());
        registry.rebind("dish", new DishServiceImplementation());
        registry.rebind("adult", new AdultServiceImplementation());
        registry.rebind("aliment", new AlimentServiceImplementation());
        registry.rebind("bus", new BusServiceImplementation());
        registry.rebind("calendar", new CalendarServiceImplementation());
        registry.rebind("category", new CategoryServiceImplementation());
        registry.rebind("daytrip", new DayTripServiceImplementation());
        registry.rebind("eatingdisorder", new EatingDisorderServiceImplementation());
        registry.rebind("place", new PlaceServiceImplementation());
    }

    public int getPort() {
        return port;
    }
}
