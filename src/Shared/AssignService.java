package Shared;

import org.json.simple.JSONObject;
import java.rmi.Remote;

public interface AssignService extends Remote {
    JSONObject assign(Integer rightId, Integer leftId) throws Exception;
    JSONObject deAssign(Integer rightId, Integer leftId) throws Exception;
    JSONObject rightRead(Integer rightId) throws Exception;
    JSONObject leftRead(Integer leftId) throws Exception;
}
