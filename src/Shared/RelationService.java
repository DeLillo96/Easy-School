package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.util.List;

public interface RelationService extends Remote {
    JSONObject assign(Integer rightId, Integer leftId) throws Exception;

    JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception;

    JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception;

    JSONObject deAssign(Integer rightId, Integer leftId) throws Exception;

    JSONObject rightRead(Integer rightId) throws Exception;

    JSONObject leftRead(Integer leftId) throws Exception;
}
