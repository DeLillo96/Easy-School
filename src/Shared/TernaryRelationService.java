package Shared;

import org.json.simple.JSONObject;

import java.util.List;

public interface TernaryRelationService extends RelationService {
    JSONObject assign(Integer leftId, Integer rightId, List centerId) throws Exception;

    JSONObject deAssign(Integer leftId, Integer rightId, List centerId) throws Exception;

    JSONObject centerRead(Integer leftId, Integer rightId) throws Exception;
}
