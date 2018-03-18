package Client;

import Shared.UserController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControllerManager {
    private static Registry registry = null;

    private ControllerManager() throws RemoteException {
        registry = LocateRegistry.getRegistry("localhost");
    }

    public static UserController getUserController() throws Exception {
        if (registry == null) new ControllerManager();
        return (UserController) registry.lookup("users");
    }
}
