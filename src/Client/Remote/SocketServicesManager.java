package Client.Remote;

import Shared.UserService;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketServicesManager implements RemoteServicesManager {
    private Socket socket;

    public SocketServicesManager() throws IOException {
        this.socket = new Socket("localhost", 9374);
    }

    @Override
    public UserService getUserService() {
        return new SocketUserService(socket);
    }

    @Override
    public void closeConnection() throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.println(request.toString());
    }


}