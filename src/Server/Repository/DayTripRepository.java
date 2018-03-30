package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.Calendar;
import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DayTripRepository extends AbstractRepository {

    public DayTripRepository() { super("DayTrip"); }

    public DayTrip getDayTripById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List trips = read(params);
        return trips!= null && trips.size() == 1 ? (DayTrip) trips.get(0) : null;
    }

    public Set<DayTrip> getDayTripByDate(Integer day_id) {
        CalendarRepository calendarRepository = new CalendarRepository();
        Calendar day = calendarRepository.getCalendarById(day_id);
        return day.getTrips();
    }

    public Set<DayTrip> getDayTripByPlace(Integer place_id) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(place_id);
        return place.getTrips();
    }

    public Set<DayTrip> getDayTripByBus(Integer bus_id) {
        BusRepository busRepository = new BusRepository();
        Bus bus = busRepository.getBusById(bus_id);
        return bus.getTrips();
    }

}
