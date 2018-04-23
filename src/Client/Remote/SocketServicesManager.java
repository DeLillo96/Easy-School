package Client.Remote;

import Client.Remote.Adapter.BaseServiceAdapter;
import Client.Remote.Adapter.UserServiceAdapter;
import Shared.BaseService;
import Shared.UserService;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketServicesManager implements RemoteServicesManager {
    private Socket socket;
    BufferedReader in;
    PrintWriter out;

    public SocketServicesManager() throws IOException {
        this.socket = new Socket("localhost", 9374);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public UserService getUserService() {
        return new UserServiceAdapter(in, out);
    }

    @Override
    public BaseService getChildrenService() {
        return new BaseServiceAdapter("children", in, out);
    }

    @Override
    public BaseService getMenuService() throws Exception {
        return new BaseServiceAdapter("menu", in, out);
    }

    @Override
    public BaseService getDishService() throws Exception {
        return new BaseServiceAdapter("dish", in, out);
    }

    @Override
    public void closeConnection() {
        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.println(request.toString());
    }


}