package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BusRepository extends AbstractRepository{

    public BusRepository() {super("Bus"); }

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

    public Set<Bus> getBusByDayTrip(Integer trip_id) {
        DayTripRepository dayTripRepository = new DayTripRepository();
        DayTrip trips = dayTripRepository.getDayTripById(trip_id);
        return trips.getBuses();
    }

    public Set<Bus> getArrivalBusesByPlace(Integer placeId) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(placeId);

        return place.getArrivalBuses();
    }

    public Set<Bus> getStartBusesByPlace(Integer placeId) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(placeId);

        return place.getStartBuses();
    }

}
