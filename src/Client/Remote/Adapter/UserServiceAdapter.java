package Client.Remote.Adapter;

import Shared.UserService;
import org.json.simple.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * User services adapter (login and logout functions)
 */
public class UserServiceAdapter extends BaseServiceAdapter implements UserService {

    public UserServiceAdapter(ObjectInputStream in, ObjectOutputStream out) {
        super("users", in, out);
    }

    @Override
    public JSONObject login(String username, String password) throws Exception {
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);

        submitRequest("login", data);

        return (JSONObject) in.readObject();
    }

    @Override
    public JSONObject logout(String username, String password) throws Exception {
        return null;
    }
}
