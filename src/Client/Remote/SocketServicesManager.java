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

/**
 * Implements RemoteServicesManager using Socket services
 * Each method returns a specific adapted of the required shared interface
 * Each adapter contains a specific string indicating the type of satisfied service, an input BufferedReader and
 * an output PrintWriter
 */
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
    public RelationService getRecipesService() throws Exception {
        //todo ERRORACCIO!
        return (RelationService) new BaseServiceAdapter("recipes", in, out);
    }

    @Override
    public RelationService getParentService() throws Exception {
        return (RelationService) new BaseServiceAdapter("parents", in, out);
    }

    @Override
    public RelationService getBusStartingPlaceService() throws Exception {
        return (RelationService) new BaseServiceAdapter("busstartplace", in, out);
    }

    @Override
    public RelationService getBusArrivalPlaceService() throws Exception {
        return (RelationService) new BaseServiceAdapter("busarrivalplace", in, out);
    }

    @Override
    public RelationService getTripPlaceService() throws Exception {
        return (RelationService) new BaseServiceAdapter("tripplace", in, out);
    }

    @Override
    public RelationService getDailyMenuService() throws Exception {
        return (RelationService) new BaseServiceAdapter("dailymenu", in, out);
    }

    @Override
    public BaseService getDailyTripService() throws Exception {
        return new BaseServiceAdapter("dailytrip", in, out);
    }

    @Override
    public RelationService getChildInTripService() throws Exception {
        return (RelationService) new BaseServiceAdapter("childintrip", in, out);
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
    public RelationService getBusTripService() throws Exception {
        return (RelationService) new BaseServiceAdapter("bustrip", in, out);
    }

    @Override
    public RelationService getPlaceInTripService() throws Exception {
        return (RelationService) new BaseServiceAdapter("placeintrip", in, out);
    }

    @Override
    public void closeConnection() {
        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.println(request.toString());
    }


}