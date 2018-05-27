package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.Place;
import Server.Entity.Trip;

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

    public List getPlaceByTrip(Integer tripId) {
        return read(
                "select\n" +
                    "  P,\n" +
                    "  case when\n" +
                    "    P.id in (" +
                    "       select PT.id from " +
                    "           Trip T join T.places PT " +
                    "               where T.id = " + tripId +
                    "       ) then true\n" +
                    "  else false\n" +
                    "    end\n" +
                    "from Place P", null);
    }

    public List getPlaceInTrip(Integer tripId) {
        TripRepository dayTripRepository = new TripRepository();
        Trip trips = dayTripRepository.getTripById(tripId);
        Set<Place> places = trips.getPlaces();
        return new ArrayList<>(places);
    }

    public Integer getCountPlaceInTrip(Integer tripId) {
        List response = read(
            "       select count(PT) from " +
                "           Trip T join T.places PT " +
                "               where T.id = " + tripId +
                "       group by T.id", null);
        //todo usare la size della lista normale.
        return response != null && response.size() > 0 ? Integer.parseInt(String.valueOf(response.get(0))) : 0;
    }

    public List getBusesFromToPlaces(Integer fromId, Integer toId) {
        return read(
                "select sb\n" +
                "   from Place p\n" +
                "   join p.startBuses sb\n" +
                "   where p.id = " + fromId + " and sb.id in (\n" +
                "       select p.id\n" +
                "       from Place p\n" +
                "           join p.arrivalBuses sb\n" +
                "           where p.id = " + toId + ")", null);
    }
}
