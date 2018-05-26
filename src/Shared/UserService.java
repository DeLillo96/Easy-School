package Shared;

import org.json.simple.JSONObject;

/**
 * Interface containing user services
 */
public interface UserService extends BaseService {

    /**
     * Prepares login operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject login(String username, String password) throws Exception;

    /**
     * Prepares logout operation
     * @return (Result object, can be true or false and contains a message)
     */
    JSONObject logout(String username, String password) throws Exception;
}
