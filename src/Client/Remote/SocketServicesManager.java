package Client.Remote;

import Client.Remote.Adapter.BaseServiceAdapter;
import Client.Remote.Adapter.UserServiceAdapter;
import Shared.AdultService;
import Shared.BaseService;
import Shared.DishService;
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
        return new BaseServiceAdapter("child", in, out);
    }

    @Override
    public BaseService getMenuService() throws Exception {
        return new BaseServiceAdapter("menu", in, out);
    }

    @Override
    public DishService getDishService() throws Exception {
        //TODO DishServiceAdapter
        return (DishService) new BaseServiceAdapter("dish", in, out);
    }

    @Override
    public AdultService getAdultService() throws Exception {
        //TODO AdultServiceAdapter
        return (AdultService) new BaseServiceAdapter("adult", in, out);
    }

    @Override
    public BaseService getEatingDisorderService() throws Exception {
        return new BaseServiceAdapter("eatingdisorder", in, out);
    }

    @Override
    public BaseService getAlimentService() {
        return new BaseServiceAdapter("aliment", in, out);
    }

    @Override
    public BaseService getBusService() {
        return new BaseServiceAdapter("bus", in, out);
    }

    @Override
    public BaseService getCalendarService() {
        return new BaseServiceAdapter("calendar", in, out);
    }

    @Override
    public BaseService getCategoryService() {
        return new BaseServiceAdapter("category", in, out);
    }

    @Override
    public BaseService getDayTripService() {
        return new BaseServiceAdapter("daytrip", in, out);
    }

    @Override
    public BaseService getEatingDisorderService() { return new BaseServiceAdapter("eatingDisorders", in, out); }

    @Override
    public BaseService getMenuService() { return new BaseServiceAdapter("menus", in, out); }

    @Override
    public BaseService getPlaceService() { return new BaseServiceAdapter("places", in, out); }

    @Override
    public void closeConnection() {
        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.println(request.toString());
    }


}