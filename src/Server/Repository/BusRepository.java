package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.DayTrip;

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

    public Set<Bus> getBusByDayTrip(Integer trip_id) {
        DayTripRepository dayTripRepository = new DayTripRepository();
        DayTrip trips = dayTripRepository.getDayTripById(trip_id);
        return trips.getBuses();
    }

}
