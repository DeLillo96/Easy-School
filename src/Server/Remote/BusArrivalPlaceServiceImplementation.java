package Server.Remote;

import Server.Entity.Bus;
import Server.Entity.Place;
import Server.Repository.BusRepository;
import Server.Repository.PlaceRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class BusArrivalPlaceServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected PlaceRepository placeRepository;
    protected BusRepository busRepository;

    public BusArrivalPlaceServiceImplementation() throws RemoteException {
        placeRepository = new PlaceRepository();
        busRepository = new BusRepository();
    }

    @Override
    public JSONObject assign(Integer busId, Integer placeId) throws Exception {
        Bus bus = busRepository.getBusById(busId);
        Place place = placeRepository.getPlaceById(placeId);

        for (Place starting : bus.getDestinationPlaces()) {
            if (starting.getId().equals(placeId)) {
                Result result = new Result();
                result.addData(bus);
                return result.toJson();
            }
        }

        place.getArrivalBuses().add(bus);
        return place.save().toJson();
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
    public JSONObject deAssign(Integer busId, Integer placeId) throws Exception {
        Place place = placeRepository.getPlaceById(placeId);

        for (Bus bus : place.getArrivalBuses()) {
            if (bus.getId().equals(busId)) {
                place.getArrivalBuses().remove(bus);
                return place.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(place);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer placeId) throws Exception {
        List list = busRepository.getArrivalBusesByPlace(placeId);
        return new Result(true, list).toJson();
    }

    @Override
    public JSONObject rightRead(Integer busId) throws Exception {
        List list = placeRepository.getPlacebyDestinationBuses(busId);
        return new Result(true, list).toJson();
    }
}
