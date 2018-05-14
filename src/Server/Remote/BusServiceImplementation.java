package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.EntityInterface;
import Server.Repository.BusRepository;
import Server.Result;
import Shared.BusService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BusServiceImplementation extends AbstractBaseService implements BusService {
    public BusServiceImplementation() throws RemoteException {
        super(new BusRepository());
    }

    @Override
    public JSONObject readUsedBusesByDayTrip(Integer dayTripId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new BusRepository()).getBusByDayTrip(dayTripId));
        if (response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("bus category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject readArrivalBusesByPlace(Integer placeId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new BusRepository()).getArrivalBusesByPlace(placeId));
        if (response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("bus category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject readStartBusesByPlace(Integer placeId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new BusRepository()).getStartBusesByPlace(placeId));
        if (response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("bus category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject setUsedBusesFromJSON(JSONObject data) throws Exception {
        return null;
    }

    @Override
    public JSONObject setArrivalBusesFromJSON(JSONObject data) throws Exception {
        return null;
    }

    @Override
    public JSONObject setStartBusesFromJSON(JSONObject data) throws Exception {
        return null;
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Bus.class);
    }
}
