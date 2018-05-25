package Server.Repository;

import Server.Entity.Child;
import Server.Entity.Trip;
import Server.Entity.Calendar;

import java.util.*;

public class TripRepository extends AbstractRepository {

    public TripRepository() {
        super("Trip");
    }

    public Trip getTripById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List dailyTrips = read(params);

        return dailyTrips != null && dailyTrips.size() == 1 ? (Trip) dailyTrips.get(0) : null;
    }

    public Calendar getCalendarOfTrip(Integer dailyTripId) {
        Trip dailyTrip = getTripById(dailyTripId);
        return dailyTrip.getCalendar();
    }

    public List<Child> getTripByChildId(Integer childId) {
        ChildRepository childRepository = new ChildRepository();
        Child child = childRepository.getChildById(childId);

        return new ArrayList(child.getTrips());
    }
}