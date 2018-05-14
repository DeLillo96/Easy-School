package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusRepository extends AbstractRepository {

    public BusRepository() {
        super("Bus");
    }

    public Bus getBusById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List buses = read(params);
        return buses != null && buses.size() == 1 ? (Bus) buses.get(0) : null;
    }

    public Bus getBusByLicensePlate(String licensePlate) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("licensePlate", licensePlate);
        List buses = read(params);
        return buses != null && buses.size() == 1 ? (Bus) buses.get(0) : null;
    }

    public List<Bus> getBusByDayTrip(Integer trip_id) {
        DayTripRepository dayTripRepository = new DayTripRepository();
        DayTrip trips = dayTripRepository.getDayTripById(trip_id);
        return new ArrayList(trips.getBuses());
    }

    public List<Bus> getArrivalBusesByPlace(Integer placeId) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(placeId);

        return new ArrayList(place.getArrivalBuses());
    }

    public List<Bus> getStartBusesByPlace(Integer placeId) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(placeId);

        return new ArrayList(place.getStartBuses());
    }
}
