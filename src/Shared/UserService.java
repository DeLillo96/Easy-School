package Shared;

import org.json.simple.JSONObject;

public interface UserService extends BaseService {
    JSONObject login(String username, String password) throws Exception;

    JSONObject logout(String username, String password) throws Exception;
}
