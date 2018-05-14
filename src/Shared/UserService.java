package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;

public interface UserService extends Remote {
    JSONObject login(String username, String password) throws Exception;

    JSONObject logout(String username, String password) throws Exception;

    JSONObject readAll() throws Exception;

    JSONObject read(JSONObject parameters) throws Exception;

    JSONObject save(JSONObject data) throws Exception;

    JSONObject saveAll(JSONObject data) throws Exception;

    JSONObject delete(JSONObject data) throws Exception;

    JSONObject deleteAll(JSONObject data) throws Exception;
}
