package Client.Remote;

import Client.Remote.Adapter.BaseServiceAdapter;
import Client.Remote.Adapter.EatingDisorderServiceAdapter;
import Client.Remote.Adapter.RelationServiceAdapter;
import Client.Remote.Adapter.UserServiceAdapter;
import Shared.*;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Implements RemoteServicesManager using Socket services
 * Each method returns a specific adapted of the required shared interface
 * Each adapter contains a specific string indicating the type of satisfied service, an input BufferedReader and
 * an output PrintWriter
 */
public class SocketServicesManager implements RemoteServicesManager {
    ObjectOutputStream out;
    ObjectInputStream in;
    private Socket socket;

    public SocketServicesManager() throws IOException {
        this.socket = new Socket("localhost", 9374);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
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
    public BaseService getSupplierService() throws Exception {
        return new BaseServiceAdapter("supplier", in, out);
    }

    @Override
    public BaseService getPediatricianService() throws Exception {
        return new BaseServiceAdapter("pediatrician", in, out);
    }

    @Override
    public EatingDisorderService getEatingDisorderService() throws Exception {
        return new EatingDisorderServiceAdapter("eatingdisorder", in, out);
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
        return new RelationServiceAdapter("recipes", in, out);
    }

    @Override
    public RelationService getParentService() throws Exception {
        return new RelationServiceAdapter("parents", in, out);
    }

    @Override
    public RelationService getTripPlaceService() throws Exception {
        return new RelationServiceAdapter("tripplace", in, out);
    }

    @Override
    public RelationService getSupplyingService() throws Exception {
        return new RelationServiceAdapter("supply", in, out);
    }

    @Override
    public RelationService getChildPediatricianService() throws Exception {
        return new RelationServiceAdapter("follow", in, out);
    }

    @Override
    public RelationService getDailyDishService() throws Exception {
        return new RelationServiceAdapter("dailydish", in, out);
    }

    @Override
    public BaseService getDailyTripService() throws Exception {
        return new BaseServiceAdapter("dailytrip", in, out);
    }

    @Override
    public RelationService getChildInTripService() throws Exception {
        return new RelationServiceAdapter("childintrip", in, out);
    }

    @Override
    public BaseService getDishService() throws Exception {
        return new BaseServiceAdapter("dish", in, out);
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
        return new RelationServiceAdapter("bustrip", in, out);
    }

    @Override
    public RelationService getPlaceInTripService() throws Exception {
        return new RelationServiceAdapter("placeintrip", in, out);
    }

    @Override
    public void closeConnection() throws IOException {
        JSONObject request = new JSONObject();
        request.put("service", "main");
        request.put("function", "exit");

        out.writeObject(request.toString());
    }


}