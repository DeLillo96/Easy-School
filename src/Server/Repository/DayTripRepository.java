package Server.Repository;

import Server.Entity.Calendar;
import Server.Entity.DayTrip;
import Server.Entity.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayTripRepository extends AbstractRepository {

    public DayTripRepository() {
        super("DayTrip");
    }

    public DayTrip getDayTripById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List trips = read(params);
        return trips != null && trips.size() == 1 ? (DayTrip) trips.get(0) : null;
    }

    public List<DayTrip> getDayTripByDate(Integer day_id) {
        CalendarRepository calendarRepository = new CalendarRepository();
        Calendar day = calendarRepository.getCalendarById(day_id);
        return new ArrayList(day.getDailyTrips());
    }

    public List<DayTrip> getDayTripByPlace(Integer place_id) {
        PlaceRepository placeRepository = new PlaceRepository();
        Place place = placeRepository.getPlaceById(place_id);
        return new ArrayList(place.getTrips());
    }

    public List getDailyTripByCalendar(Integer calendarId) {
        CalendarRepository calendarRepository = new CalendarRepository();
        Calendar calendar = calendarRepository.getCalendarById(calendarId);
        return new ArrayList(calendar.getDailyTrips());
    }

}
