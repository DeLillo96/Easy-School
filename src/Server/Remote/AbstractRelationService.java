package Server.Remote;

import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public abstract class AbstractRelationService extends UnicastRemoteObject implements RelationService {

    public AbstractRelationService() throws RemoteException {

    }

    @Override
    public JSONObject assign(Integer rightId, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public JSONObject leftRead(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }
}
