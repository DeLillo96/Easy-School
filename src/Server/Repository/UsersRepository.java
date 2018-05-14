package Server.Repository;

import Server.Entity.Users;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

public class UsersRepository extends AbstractRepository {

    public UsersRepository() {
        super("Users");
    }

    public Users getUserById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List users = read(params);

        return users != null && users.size() == 1 ? (Users) users.get(0) : null;
    }

    public Users getUserByUsername(String username) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        List users = read(params);

        return users != null && users.size() == 1 ? (Users) users.get(0) : null;
    }

    public boolean login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            params.put("password", Users.hashPassword(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }

        List user = read(params);
        return (user != null && user.size() == 1);
    }
}
