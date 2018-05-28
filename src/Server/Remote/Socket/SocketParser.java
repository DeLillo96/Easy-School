package Server.Remote.Socket;

import Server.Remote.*;
import Shared.UserService;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Class which uses an input BufferedReader and an output PrintWriter to read service requests from the assigned client,
 * executes the requested operations and sends back a Result object back to the client
 */
public class SocketParser extends Thread {
    final private Socket socket;

    public SocketParser(Socket socket) {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!interrupted() && !socket.isClosed()) {
            try {

                JSONObject json = (JSONObject) in.readObject();

                JSONObject result = null;

                if (json.get("service").equals("users")) {
                    UserService userService = new UserServiceImplementation();

                    if (json.get("function").equals("login")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = userService.login((String) data.get("username"), (String) data.get("password"));

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("child")) {
                    ChildrenServiceImplementation childrenService = new ChildrenServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("adult")) {
                    AdultServiceImplementation adultService = new AdultServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = adultService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("aliment")) {
                    AlimentServiceImplementation alimentService = new AlimentServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = alimentService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("bus")) {
                    BusServiceImplementation busService = new BusServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("calendar")) {
                    CalendarServiceImplementation calendarService = new CalendarServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = calendarService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("daytrips")) {
                    TripServiceImplementation dayTripService = new TripServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dayTripService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("dish")) {
                    DishServiceImplementation dishService = new DishServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dishService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("eatingDisorders")) {
                    EatingDisorderServiceImplementation eatingDisorderService = new EatingDisorderServiceImplementation();

                    if (json.get("function").equals("Rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.rightRead((Integer) data.get("childId"));

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("places")) {
                    PlaceServiceImplementation placeService = new PlaceServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("staff")) {
                    StaffServiceImplementation staffService = new StaffServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = staffService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("main")) {
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
