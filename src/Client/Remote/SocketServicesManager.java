package Client.Remote;

import Client.Remote.Services.BaseServiceAdapter;
import Client.Remote.Services.UserServiceAdapter;
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
    public BaseService getAdultService() {
        return new BaseServiceAdapter("adults", in, out);
    }

    @Override
    public BaseService getAlimentService() {
        return new BaseServiceAdapter("aliments", in, out);
    }

    @Override
    public BaseService getBusService() {
        return new BaseServiceAdapter("buses", in, out);
    }

    @Override
    public BaseService getCalendarService() {
        return new BaseServiceAdapter("calendars", in, out);
    }

    @Override
    public BaseService getCategoryService() {
        return new BaseServiceAdapter("categories", in, out);
    }

    @Override
    public BaseService getDayTripService() {
        return new BaseServiceAdapter("daytrips", in, out);
    }

    @Override
    public BaseService getDishService() {
        return new BaseServiceAdapter("dishes", in, out);
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