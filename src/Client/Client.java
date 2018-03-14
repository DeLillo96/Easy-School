package Client;

import Server.Controller.ClientController;
import org.json.simple.JSONObject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            ClientController clientController = (ClientController) registry.lookup("users/login");

            JSONObject params = new JSONObject();
            params.put("username", "admin");
            JSONObject result = clientController.login("admin", "admin");
            System.out.println("response: " + result.toString());

        } catch (Exception e) {

            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
