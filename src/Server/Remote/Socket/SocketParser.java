package Server.Remote.Socket;

import Server.Remote.*;
import Server.Result;
import Shared.UserService;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = adultService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = adultService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = alimentService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = alimentService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = calendarService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = calendarService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("daytrip")) {
                    TripServiceImplementation dayTripService = new TripServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dayTripService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dayTripService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dayTripService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dishService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dishService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("eatingdisorder")) {
                    EatingDisorderServiceImplementation eatingDisorderService = new EatingDisorderServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = eatingDisorderService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = eatingDisorderService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = eatingDisorderService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(eatingDisorderService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(eatingDisorderService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }else if(json.get("function").equals("assignallergy")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.assignAllergy((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("assignintolerance")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = eatingDisorderService.assignIntolerance((Integer) data.get("rightId"), (Integer) data.get("leftId"));

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeService.delete(data);

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
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = staffService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = staffService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("firstdish")) {
                    FirstDishServiceImplementation firstDishService = new FirstDishServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = firstDishService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = firstDishService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = firstDishService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("seconddish")) {
                    SecondDishServiceImplementation secondDishService = new SecondDishServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = secondDishService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = secondDishService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = secondDishService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("sidedish")) {
                    SideDishServiceImplementation sideDishService = new SideDishServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sideDishService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sideDishService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sideDishService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("sweetdish")) {
                    SweetDishServiceImplementation sweetDishService = new SweetDishServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sweetDishService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sweetDishService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = sweetDishService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("dailytrip")) {
                    DailyTripServiceImplementation dailyTripService = new DailyTripServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyTripService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyTripService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyTripService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("pediatrician")) {
                    PediatricianServiceImplementation pediatricianService = new PediatricianServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = pediatricianService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = pediatricianService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = pediatricianService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("supplier")) {
                    SupplierServiceImplementation supplierService = new SupplierServiceImplementation();

                    if (json.get("function").equals("read")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplierService.read(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("save")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplierService.save(data);

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("delete")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplierService.delete(data);

                        out.writeObject(result);
                        out.flush();
                    }
                }else if (json.get("service").equals("bustrip")) {
                    BusTripServiceImplementation busTripService = new BusTripServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = busTripService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = busTripService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = busTripService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busTripService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busTripService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = busTripService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(busTripService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(busTripService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("follow")) {
                    ChildPediatricianServiceImplementation childPediatricianService = new ChildPediatricianServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = childPediatricianService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = childPediatricianService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = childPediatricianService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childPediatricianService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childPediatricianService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childPediatricianService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(childPediatricianService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(childPediatricianService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("childintrip")) {
                    ChildrenInTripServiceImplementation childrenInTripService = new ChildrenInTripServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = childrenInTripService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = childrenInTripService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = childrenInTripService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenInTripService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenInTripService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = childrenInTripService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(childrenInTripService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(childrenInTripService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("dailydish")) {
                    DailyDishServiceImplementation dailyDishService = new DailyDishServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = dailyDishService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = dailyDishService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = dailyDishService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyDishService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyDishService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = dailyDishService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(dailyDishService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(dailyDishService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("parents")) {
                    ParentServiceImplementation parentService = new ParentServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = parentService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = parentService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = parentService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = parentService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = parentService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = parentService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(parentService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(parentService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("placeintrip")) {
                    PlaceInTripServiceImplementation placeInTripService = new PlaceInTripServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = placeInTripService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = placeInTripService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = placeInTripService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeInTripService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeInTripService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = placeInTripService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(placeInTripService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(placeInTripService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("supply")) {
                    SupplyingServiceImplementation supplyingService = new SupplyingServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = supplyingService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = supplyingService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = supplyingService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplyingService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplyingService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = supplyingService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(supplyingService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(supplyingService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("recipes")) {
                    RecipesServiceImplementation recipesService = new RecipesServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = recipesService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = recipesService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = recipesService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = recipesService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = recipesService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = recipesService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(recipesService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(recipesService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("tripplace")) {
                    TripPlaceServiceImplementation tripPlaceService = new TripPlaceServiceImplementation();

                    if (json.get("function").equals("assign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        if((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof Integer)) {
                            result = tripPlaceService.assign((Integer) data.get("rightId"), (Integer) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof Integer) && (data.get("leftId") instanceof List)) {
                            result = tripPlaceService.assign((Integer) data.get("rightId"), (List<Integer>) data.get("leftId"));
                        }else if ((data.get("rightId") instanceof List) && (data.get("leftId") instanceof Integer)) {
                            result = tripPlaceService.assign((List<Integer>) data.get("rightId"), (Integer) data.get("leftId"));
                        }

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("deassign")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = tripPlaceService.deAssign((Integer) data.get("rightId"), (Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = tripPlaceService.rightRead((Integer) data.get("rightId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("leftread")) {
                        JSONObject data = (JSONObject) json.get("data");
                        result = tripPlaceService.leftRead((Integer) data.get("leftId"));

                        out.writeObject(result);
                        out.flush();
                    }else if(json.get("function").equals("rightcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(tripPlaceService.rightCount((Integer) data.get("rightId")));
                        out.flush();
                    }else if(json.get("function").equals("leftcount")) {
                        JSONObject data = (JSONObject) json.get("data");

                        out.writeObject(tripPlaceService.leftCount((Integer) data.get("leftId")));
                        out.flush();
                    }
                }else if (json.get("service").equals("main")) {
                    if (json.get("function").equals("exit")) {
                        socket.close();
                    }
                }
            } catch (Exception e) {
                Result result = new Result(e.getMessage(), false);

                try {
                    out.writeObject(result.toJson());
                    out.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
