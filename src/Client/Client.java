package Client;

import Server.Controller.UserController;
import org.json.simple.JSONObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            UserController userController = (UserController) registry.lookup("users");
            JSONObject result = userController.login("admin", "admin");
            System.out.println("response: " + result.toString());

            JSONObject params = new JSONObject();
            params.put("username", "admin");
            result = userController.read(params);
            System.out.println("response: " + result.toString());

        } catch (Exception e) {

            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
