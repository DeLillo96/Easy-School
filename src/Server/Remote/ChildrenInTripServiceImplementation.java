package Server.Remote;

import Server.Entity.Trip;
import Server.Entity.Child;
import Server.Repository.TripRepository;
import Server.Repository.ChildRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ChildrenInTripServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected ChildRepository childRepository;
    protected TripRepository tripRepository;

    public ChildrenInTripServiceImplementation() throws RemoteException {
        childRepository = new ChildRepository();
        tripRepository = new TripRepository();
    }

    @Override
    public JSONObject assign(Integer childId, Integer tripId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);

        for (Child ChildInTrip : trip.getChildInTrip()) {
            if (ChildInTrip.getId().equals(childId)) {
                Result result = new Result();
                result.addData(trip);
                return result.toJson();
            }
        }

        trip.getChildInTrip().add(childRepository.getChildById(childId));
        return trip.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
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
    public JSONObject deAssign(Integer childId, Integer tripId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);

        for (Child ChildInTrip : trip.getChildInTrip()) {
            if (ChildInTrip.getId().equals(childId)) {
                trip.getChildInTrip().remove(ChildInTrip);
                return trip.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(trip);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer childId) throws Exception {
        List list = tripRepository.getTripByChildId(childId);
        return new Result(true, list).toJson();
    }

    @Override
    public JSONObject rightRead(Integer tripId) throws Exception {
        List list = childRepository.getChildInTrip(tripId);
        return new Result(true, list).toJson();
    }
}
