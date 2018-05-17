package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;

public interface UserService extends BaseService {
    JSONObject login(String username, String password) throws Exception;

    JSONObject logout(String username, String password) throws Exception;
}
