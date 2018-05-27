package Server.Remote;

import Server.Entity.Place;
import Server.Entity.Trip;
import Server.Repository.PlaceRepository;
import Server.Repository.TripRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PlaceInTripServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected PlaceRepository placeRepository;
    protected TripRepository tripRepository;

    public PlaceInTripServiceImplementation() throws RemoteException {
        placeRepository = new PlaceRepository();
        tripRepository = new TripRepository();
    }

    @Override
    public JSONObject assign(Integer tripId, Integer placeId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);
        Place place = placeRepository.getPlaceById(placeId);

        for (Place starting : trip.getPlaces()) {
            if (starting.getId().equals(placeId)) {
                Result result = new Result();
                result.addData(trip);
                return result.toJson();
            }
        }

        trip.getPlaces().add(place);
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
    public JSONObject deAssign(Integer tripId, Integer placeId) throws Exception {
        Trip trip = tripRepository.getTripById(tripId);

        for (Place place : trip.getPlaces()) {
            if (place.getId().equals(placeId)) {
                trip.getPlaces().remove(place);
                return trip.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(trip);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer tripId) throws Exception {
        List list = placeRepository.getPlaceInTrip(tripId);
        return new Result(true, list).toJson();
    }

    @Override
    public Integer rightCount(Integer tripId) throws Exception {
        return placeRepository.getCountPlaceInTrip(tripId);
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer placeId) throws Exception {
        return null;
    }
}
