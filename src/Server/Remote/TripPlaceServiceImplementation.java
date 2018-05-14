package Server.Remote;

import Server.Entity.DayTrip;
import Server.Entity.Place;
import Server.Repository.DayTripRepository;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.AssignService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TripPlaceServiceImplementation extends UnicastRemoteObject implements AssignService {
    protected PlaceRepository placeRepository;
    protected DayTripRepository dayTripRepository;

    public TripPlaceServiceImplementation() throws RemoteException {
        placeRepository = new PlaceRepository();
        dayTripRepository = new DayTripRepository();
    }

    @Override
    public JSONObject assign(Integer dayTripId, Integer placeId) throws Exception {
        DayTrip dayTrip = dayTripRepository.getDayTripById(dayTripId);
        Place place = placeRepository.getPlaceById(placeId);

        for (Place starting : dayTrip.getPlaces()) {
            if (starting.getId().equals(placeId)) {
                Result result = new Result();
                result.addData(dayTrip);
                return result.toJson();
            }
        }

        dayTrip.getPlaces().add(place);
        return dayTrip.save().toJson();
    }

    @Override
    public JSONObject deAssign(Integer dayTripId, Integer placeId) throws Exception {
        DayTrip dayTrip = dayTripRepository.getDayTripById(dayTripId);

        for (Place place : dayTrip.getPlaces()) {
            if (place.getId().equals(placeId)) {
                dayTrip.getPlaces().remove(place);
                return dayTrip.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(dayTrip);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer placeId) throws Exception {
        List list = dayTripRepository.getDayTripByPlace(placeId);
        return new Result(true, list).toJson();
    }

    @Override
    public JSONObject rightRead(Integer dayTripId) throws Exception {
        List list = placeRepository.getPlaceByDayTrip(dayTripId);
        return new Result(true, list).toJson();
    }
}
