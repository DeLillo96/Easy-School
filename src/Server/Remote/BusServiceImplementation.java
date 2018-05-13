package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.EntityInterface;
import Server.Entity.Place;
import Server.Repository.AdultRepository;
import Server.Repository.BusRepository;
import Server.Repository.DayTripRepository;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.BaseService;
import Shared.BusService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BusServiceImplementation extends AbstractBaseService implements BusService {
    public BusServiceImplementation() throws RemoteException {
        super(new BusRepository());
    }

    @Override
    public JSONObject readUsedBusesByDayTrip(Integer dayTripId) throws Exception {
        Result result = new Result();

        List response = new ArrayList((new BusRepository()).getBusByDayTrip(dayTripId));
        if(response != null) {
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
        if(response != null) {
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
        if(response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("bus category are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject setUsedBusesFromJSON(JSONObject data) throws Exception {

        Result result = new Result();
        boolean flag = false;
        DayTrip mainTrip = (new DayTripRepository()).getDayTripById((Integer) data.get("tripId"));
        Bus busToAdd = (new BusRepository()).getBusById((Integer) data.get("busId"));
        boolean check = (boolean) data.get("check");
        Set<Bus> actualBuses = (new BusRepository()).getBusByDayTrip(mainTrip.getId());
        Set<Bus> busesToRemove = new HashSet<>();
        for(Bus a:actualBuses) {
            if(a.getId()==(busToAdd.getId())) {
                if(check) {
                    result.setSuccess(true);
                    result.addMessage("DB was already updated");
                    return result.toJson();
                }else {
                    flag = true;
                    busesToRemove.add(a);
                }
            }
        }
        if(flag) {
            actualBuses.removeAll(busesToRemove);
            mainTrip.setBuses(actualBuses);
            result = mainTrip.save();
            return result.toJson();
        }else if(check)
        {
            actualBuses.add(busToAdd);
            mainTrip.setBuses(actualBuses);
            result = mainTrip.save();
            return result.toJson();
        }

        result.setSuccess(true);
        result.addMessage("DB was already updated");
        return result.toJson();
    }

    @Override
    public JSONObject setArrivalBusesFromJSON(JSONObject data) throws Exception {

        Result result = new Result();
        boolean flag = false;
        Place mainPlace = (new PlaceRepository()).getPlaceById((Integer) data.get("placeId"));
        Bus busToAdd = (new BusRepository()).getBusById((Integer) data.get("busId"));
        boolean check = (boolean) data.get("check");
        Set<Bus> actualArrivalBuses = (new BusRepository()).getArrivalBusesByPlace(mainPlace.getId());
        Set<Bus> busToRemove = new HashSet<>();
        for(Bus a:actualArrivalBuses) {
            if(a.getId()==(busToAdd.getId())) {
                if(check) {
                    result.setSuccess(true);
                    result.addMessage("DB was already updated");
                    return result.toJson();
                }else {
                    flag = true;
                    busToRemove.add(a);
                }
            }
        }
        if(flag) {
            actualArrivalBuses.removeAll(busToRemove);
            mainPlace.setArrivalBuses(actualArrivalBuses);
            result = mainPlace.save();
            return result.toJson();
        }else if(check)
        {
            actualArrivalBuses.add(busToAdd);
            mainPlace.setArrivalBuses(actualArrivalBuses);
            result = mainPlace.save();
            return result.toJson();
        }

        result.setSuccess(true);
        result.addMessage("DB was already updated");
        return result.toJson();
    }

    @Override
    public JSONObject setStartBusesFromJSON(JSONObject data) throws Exception {

        Result result = new Result();
        boolean flag = false;
        Place mainPlace = (new PlaceRepository()).getPlaceById((Integer) data.get("placeId"));
        Bus busToAdd = (new BusRepository()).getBusById((Integer) data.get("busId"));
        boolean check = (boolean) data.get("check");
        Set<Bus> actualStartBuses = (new BusRepository()).getStartBusesByPlace(mainPlace.getId());
        Set<Bus> busToRemove = new HashSet<>();
        for(Bus a:actualStartBuses) {
            if(a.getId()==(busToAdd.getId())) {
                if(check) {
                    result.setSuccess(true);
                    result.addMessage("DB was already updated");
                    return result.toJson();
                }else {
                    flag = true;
                    busToRemove.add(a);
                }
            }
        }
        if(flag) {
            actualStartBuses.removeAll(busToRemove);
            mainPlace.setStartBuses(actualStartBuses);
            result = mainPlace.save();
            return result.toJson();
        }else if(check)
        {
            actualStartBuses.add(busToAdd);
            mainPlace.setStartBuses(actualStartBuses);
            result = mainPlace.save();
            return result.toJson();
        }

        result.setSuccess(true);
        result.addMessage("DB was already updated");
        return result.toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Bus.class);
    }
}
