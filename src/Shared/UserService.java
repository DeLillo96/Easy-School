package Shared;

import org.json.simple.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    JSONObject login(String username, String password) throws Exception;
    JSONObject logout(String username, String password) throws RemoteException;

    JSONObject readAll() throws RemoteException;
    JSONObject read(JSONObject parameters) throws RemoteException;

    JSONObject save(JSONObject data) throws RemoteException;
    JSONObject saveAll(JSONObject data) throws RemoteException;

    JSONObject delete(JSONObject data) throws RemoteException;
    JSONObject deleteAll(JSONObject data) throws RemoteException;
}
