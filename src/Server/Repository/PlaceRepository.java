package Server.Repository;

import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaceRepository extends AbstractRepository {

    public PlaceRepository() { super("Place"); }

    public Place getPlaceById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List places = read(params);
        return places != null && places.size() == 1 ? (Place) places.get(0) : null;
    }

    public Set<Place> getPlaceByCost(Integer cost) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("cost", cost);
        List places = read(params);
        return new HashSet<Place>(places);
    }

    public Set<Place> getPlaceByDayTrip(Integer trip_id) {
        DayTripRepository dayTripRepository = new DayTripRepository();
        DayTrip trips = dayTripRepository.getDayTripById(trip_id);
        Set<Place> places = trips.getPlaces();
        return places;

    }

}
