package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PlaceRepository extends AbstractRepository {

    public PlaceRepository() {
        super("Place");
    }

    public Place getPlaceById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List places = read(params);
        return places != null && places.size() == 1 ? (Place) places.get(0) : null;
    }

    public List<Place> getPlaceByCost(Integer cost) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("cost", cost);
        List places = read(params);
        return places;
    }

    public List<Place> getPlaceByDayTrip(Integer tripId) {
        DayTripRepository dayTripRepository = new DayTripRepository();
        DayTrip trips = dayTripRepository.getDayTripById(tripId);
        Set<Place> places = trips.getPlaces();
        return new ArrayList<Place>(places);

    }

    public List<Place> getPlacebyStartingBuses(Integer busId) {
        BusRepository busRepository = new BusRepository();
        Bus bus = busRepository.getBusById(busId);

        return new ArrayList<Place>(bus.getStartPlaces());
    }

    public List<Place> getPlacebyDestinationBuses(Integer busId) {
        BusRepository busRepository = new BusRepository();
        Bus bus = busRepository.getBusById(busId);

        return new ArrayList<Place>(bus.getDestinationPlaces());
    }

}
