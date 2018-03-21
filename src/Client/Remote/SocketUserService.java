package Client.Remote;

import Shared.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketUserService implements UserService {
    private final String service = "users";
    private Socket socket;

    public SocketUserService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public JSONObject login(String username, String password) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);
        request.put("service", service);
        request.put("function", "login");
        request.put("data", data);

        out.println(request.toString());

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(in.readLine());
    }

    @Override
    public JSONObject logout(String username, String password) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject readAll() throws RemoteException {
        return null;
    }

    @Override
    public JSONObject read(JSONObject parameters) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject save(JSONObject data) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject saveAll(JSONObject data) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject data) throws RemoteException {
        return null;
    }

    @Override
    public JSONObject deleteAll(JSONObject data) throws RemoteException {
        return null;
    }
}
