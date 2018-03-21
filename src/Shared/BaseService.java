package Shared;

import org.json.simple.JSONObject;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BaseService extends Remote {
    JSONObject readAll() throws RemoteException;
    JSONObject read(JSONObject parameters) throws RemoteException;

    JSONObject save(JSONObject data) throws RemoteException;
    JSONObject saveAll(JSONObject data) throws RemoteException;

    JSONObject delete(JSONObject data) throws RemoteException;
    JSONObject deleteAll(JSONObject data) throws RemoteException;
}
