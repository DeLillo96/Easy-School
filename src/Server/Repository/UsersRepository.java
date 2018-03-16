package Server.Repository;

import Server.Entity.EntityInterface;
import Server.Entity.Users;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class UsersRepository extends AbstractRepository {

    public UsersRepository() { super("Users"); }

    public Users getUserById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List users = read(params);
        if(users != null && users.size() == 1)
            return (Users) users.get(0);
        else
            return null;
    }

    public Users getUserByUsername(String username) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        List users = read(params);
        if(users != null && users.size() == 1)
            return (Users) users.get(0);
        else
            return null;
    }

    public boolean login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        List user = read(params);
        return (user != null && user.size() == 1);
    }
}
