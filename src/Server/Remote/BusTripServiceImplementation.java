package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.Trip;
import Server.Repository.BusRepository;
import Server.Repository.TripRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BusTripServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected BusRepository busRepository;
    protected TripRepository tripRepository;

    public BusTripServiceImplementation() throws RemoteException {
        busRepository = new BusRepository();
        tripRepository = new TripRepository();
    }

    @Override
    public JSONObject assign(Integer tripId, Integer busId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);
        Bus bus = busRepository.getBusById(busId);

        for (Bus starting : trip.getVehicles()) {
            if (starting.getId().equals(busId)) {
                Result result = new Result();
                result.addData(trip);
                return result.toJson();
            }
        }

        trip.getVehicles().add(bus);
        return trip.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List leftIds) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception {
        Result response = new Result();
        for (Integer rightId : rightIds) {
            JSONObject result = assign(rightId, leftId);
            if(!(Boolean) result.get("success")) {
                response.setSuccess(false);
                response.addMessage(result.get("messages").toString());
            }
        }
        return response.toJson();
    }

    @Override
    public JSONObject deAssign(Integer tripId, Integer busId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);

        for (Bus bus : trip.getVehicles()) {
            if (bus.getId().equals(busId)) {
                trip.getVehicles().remove(bus);
                return trip.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(trip);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer tripId) throws Exception {
        List list = busRepository.getBusesByTrip(tripId);
        return new Result(true, list).toJson();
    }

    @Override
    public Integer rightCount(Integer tripId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer busId) throws Exception {
        return null;
    }
}
