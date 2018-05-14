package Client.Remote.Adapter;

import Shared.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class UserServiceAdapter extends BaseServiceAdapter implements UserService {

    public UserServiceAdapter(BufferedReader in, PrintWriter out) {
        super("users", in, out);
    }

    @Override
    public JSONObject login(String username, String password) throws Exception {
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);

        submitRequest("login", data);

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(in.readLine());
    }

    @Override
    public JSONObject logout(String username, String password) throws Exception {
        return null;
    }
}
