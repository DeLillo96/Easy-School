package Server.Controller;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientController extends Remote {
    JSONObject login(String username, String password) throws RemoteException;
    JSONObject logout(String username, String password) throws RemoteException;
}
