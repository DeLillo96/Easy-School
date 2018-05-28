package Shared;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * Interface containing basic relation services
 */
public interface TernaryRelationService extends RelationService {

    /**
     * Prepares oneToOne assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assign(Integer rightId, Integer center, List<Integer> leftId) throws Exception;

    /**
     * Prepares assignation remove operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject deAssign(Integer rightId, Integer center, Integer leftId) throws Exception;

    /**
     * Prepares right join operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject rightRead(Integer rightId, Integer centerId) throws Exception;

    /**
     * Prepares left join operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject leftRead(Integer leftId, Integer centerId) throws Exception;

    JSONObject centerRead(Integer leftId, Integer rightId) throws Exception;
}
