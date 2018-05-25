package Server.Remote;

import Server.Entity.Trip;
import Server.Entity.Place;
import Server.Repository.TripRepository;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TripPlaceServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected PlaceRepository placeRepository;
    protected TripRepository tripRepository;

    public TripPlaceServiceImplementation() throws RemoteException {
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
    public JSONObject assign(List rightIds, Integer leftId) throws Exception {
        return null;
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
    public JSONObject leftRead(Integer placeId) throws Exception {
        //List list = tripRepository.getTripByPlace(placeId);
        //return new Result(true, list).toJson();
        return null;
    }

    @Override
    public JSONObject rightRead(Integer tripId) throws Exception {
        List list = placeRepository.getPlaceByTrip(tripId);
        return new Result(true, list).toJson();
    }
}
