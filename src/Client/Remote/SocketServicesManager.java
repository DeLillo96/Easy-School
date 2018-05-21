package Client.Remote;

import Client.Remote.Adapter.BaseServiceAdapter;
import Client.Remote.Adapter.UserServiceAdapter;
import Shared.*;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketServicesManager implements RemoteServicesManager {
    BufferedReader in;
    PrintWriter out;
    private Socket socket;

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
    public BaseService getAdultService() throws Exception {
        return new BaseServiceAdapter("adult", in, out);
    }

    @Override
    public EatingDisorderService getEatingDisorderService() throws Exception {
        return (EatingDisorderService) new BaseServiceAdapter("eatingdisorder", in, out);
    }

    @Override
    public BaseService getBusService() {
        return new BaseServiceAdapter("bus", in, out);
    }

    @Override
    public BaseService getAlimentService() {
        return new BaseServiceAdapter("aliment", in, out);
    }

    @Override
    public BaseService getCalendarService() {
        return new BaseServiceAdapter("calendar", in, out);
    }

    @Override
    public BaseService getDayTripService() {
        return new BaseServiceAdapter("daytrip", in, out);
    }

    @Override
    public BaseService getPlaceService() {
        return new BaseServiceAdapter("places", in, out);
    }

    @Override
    public AssignService getRecipesService() throws Exception {
        //todo ERRORACCIO!
        return (AssignService) new BaseServiceAdapter("recipes", in, out);
    }

    @Override
    public AssignService getParentService() throws Exception {
        return (AssignService) new BaseServiceAdapter("parents", in, out);
    }

    @Override
    public AssignService getBusStartingPlaceService() throws Exception {
        return (AssignService) new BaseServiceAdapter("busstartplace", in, out);
    }

    @Override
    public AssignService getBusArrivalPlaceService() throws Exception {
        return (AssignService) new BaseServiceAdapter("busarrivalplace", in, out);
    }

    @Override
    public AssignService getTripPlaceService() throws Exception {
        return (AssignService) new BaseServiceAdapter("tripplace", in, out);
    }

    @Override
    public DailyMenuAssignService getDailyMenuService() throws Exception {
        return (DailyMenuAssignService) new BaseServiceAdapter("dailymenu", in, out);
    }

    @Override
    public AssignService getDailyTripService() throws Exception {
        return (AssignService) new BaseServiceAdapter("dailytrip", in, out);
    }

    @Override
    public BaseService getFirstDishService() throws Exception {
        return new BaseServiceAdapter("firstdish", in, out);
    }

    @Override
    public BaseService getSecondDishService() throws Exception {
        return new BaseServiceAdapter("seconddish", in, out);
    }

    @Override
    public BaseService getSideDishService() throws Exception {
        return new BaseServiceAdapter("sidedish", in, out);
    }

    @Override
    public BaseService getSweetDishService() throws Exception {
        return new BaseServiceAdapter("sweetdish", in, out);
    }

    @Override
    public BaseService getStaffService() throws Exception {
        return new BaseServiceAdapter("staff", in, out);
    }

    @Override
    public void closeConnection() {
        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.println(request.toString());
    }


}