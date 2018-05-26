package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;

/**
 * Interface containing base services
 */
public interface BaseService extends Remote {

    /**
     * Reads every single tuple in the assigned repository
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject readAll() throws Exception;

    /**
     * Reads every single tuple in the assigned repository which satisfy given input parameters
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject read(JSONObject parameters) throws Exception;

    /**
     * Prepares a single entity save operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject save(JSONObject data) throws Exception;

    /**
     * Prepares multiple entities save operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject saveAll(JSONObject data) throws Exception;

    /**
     * Prepares a single entity delete operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject delete(JSONObject data) throws Exception;

    /**
     * Prepares multiple entities delete operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject deleteAll(JSONObject data) throws Exception;
}
