package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;
import java.util.List;

/**
 * Interface containing basic relation services
 */
public interface RelationService extends Remote {

    /**
     * Prepares oneToOne assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assign(Integer rightId, Integer leftId) throws Exception;

    /**
     * Prepares oneToMany assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception;

    /**
     * Prepares manyToMany assignation operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception;

    /**
     * Prepares assignation remove operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject deAssign(Integer rightId, Integer leftId) throws Exception;

    /**
     * Prepares right join operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject rightRead(Integer rightId) throws Exception;

    /**
     * Prepares left join operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject leftRead(Integer leftId) throws Exception;

    Integer rightCount(Integer rightId) throws Exception;

    Integer leftCount(Integer leftId) throws Exception;
}
