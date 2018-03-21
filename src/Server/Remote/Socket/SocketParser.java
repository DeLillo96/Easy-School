package Server.Remote.Socket;

import Server.Remote.UserServiceImplementation;
import Shared.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketParser extends Thread {
    final private Socket socket;

    public SocketParser(Socket socket) {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        while(!interrupted() || socket.isConnected()) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request = in.readLine();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(request);

                JSONObject result = null;

                if (json.get("service").equals("users")) {
                    UserService userService = new UserServiceImplementation();

                    if(json.get("function").equals("login")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = userService.login((String) data.get("username"), (String) data.get("password"));
                    }
                }

                if (result != null) {
                    out.println(result.toString());
                } else {
                    out.println("nuuuu");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
