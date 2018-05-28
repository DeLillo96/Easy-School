package Server.Remote;

import Server.Entity.Child;
import Server.Entity.ChildInVehicle;
import Server.Entity.Trip;
import Server.Repository.BusRepository;
import Server.Repository.ChildInVehicleRepository;
import Server.Repository.ChildRepository;
import Server.Repository.TripRepository;
import Server.Result;
import Shared.TernaryRelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChildInVehicleServiceImplementation extends UnicastRemoteObject implements TernaryRelationService {

    ChildInVehicleRepository childInVehicleRepository;
    ChildRepository childRepository;
    BusRepository busRepository;
    TripRepository tripRepository;

    public ChildInVehicleServiceImplementation() throws RemoteException {
        childInVehicleRepository = new ChildInVehicleRepository();
        childRepository = new ChildRepository();
        busRepository = new BusRepository();
        tripRepository = new TripRepository();
    }

    @Override
    public JSONObject assign(Integer rightId, Integer center, List<Integer> leftIds) throws Exception {
        ChildInVehicle childInVehicle = new ChildInVehicle();
        Child child = childRepository.getChildById(center);
        Trip trip = tripRepository.getTripById(rightId);
        childInVehicle.setChild(child);
        childInVehicle.setTrip(trip);
        for (Integer leftId: leftIds) {
            childInVehicle.getVehicles().add(busRepository.getBusById(leftId));
        }
        return childInVehicle.save().toJson();
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer center, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer rightId, Integer centerId) throws Exception {
        return null;
    }

    @Override
    public JSONObject leftRead(Integer leftId, Integer centerId) throws Exception {
        List response = childInVehicleRepository.getChildInVehicleRepository(leftId, centerId);
        return new Result(true, response).toJson();
    }

    @Override
    public JSONObject centerRead(Integer leftId, Integer rightId) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(Integer rightId, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(List<Integer> rightIds, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject deAssign(Integer rightId, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public JSONObject leftRead(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }
}
