package Server.Repository;

import Server.Entity.Users;

import java.util.HashMap;
import java.util.List;

public class UsersRepository extends AbstractRepository {

    public UsersRepository() { super("Users"); }

    public Users getUserById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List users = read("byId", params);
        return (Users) users.get(0);
    }

    public boolean login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        List boh = read("login", params);
        return (boh != null && boh.size() == 0);
    }
}
