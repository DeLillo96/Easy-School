package Server.Remote.Socket;

import Server.Remote.*;
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
        while(!interrupted() && !socket.isClosed()) {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String request = in.readLine();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(request);

                JSONObject result = null;

                if (json.get("service").equals("user")) {
                    UserService userService = new UserServiceImplementation();

                    if(json.get("function").equals("login")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = userService.login((String) data.get("username"), (String) data.get("password"));

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("child")) {
                    ChildrenServiceImplementation childrenService = new ChildrenServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("adult")) {
                    AdultServiceImplementation adultService = new AdultServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = adultService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("aliment")) {
                    AlimentServiceImplementation alimentService = new AlimentServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = alimentService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("bus")) {
                    BusServiceImplementation busService = new BusServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = busService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("calendar")) {
                    CalendarServiceImplementation calendarService = new CalendarServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = calendarService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("category")) {
                    CategoryServiceImplementation categoryService = new CategoryServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = categoryService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("daytrips")) {
                    DayTripServiceImplementation dayTripService = new DayTripServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = dayTripService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("dishes")) {
                    DishServiceImplementation dishService = new DishServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = dishService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("eatingDisorders")) {
                    EatingDisorderServiceImplementation eatingDisorderService = new EatingDisorderServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.read(data);

                        out.println(result.toString());
                    }
                }

                if(json.get("service").equals("menus")) {
                    MenuServiceImplementation menuService = new MenuServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = menuService.read(data);

                        out.println(result.toString());
                    }
                }

                if(json.get("service").equals("places")) {
                    PlaceServiceImplementation placeService = new PlaceServiceImplementation();

                    if(json.get("function").equals("read")){
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeService.read(data);

                        out.println(result.toString());
                    }
                }

                if (json.get("service").equals("main")) {
                    if (json.get("function").equals("exit")) {
                        socket.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
