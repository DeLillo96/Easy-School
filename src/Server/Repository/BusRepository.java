package Server.Repository;

import Server.Entity.Bus;
import Server.Entity.Place;
import Server.Entity.Trip;

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

    public List getBusesByTrip(Integer tripId) {
        return read(
                "select\n" +
                "  V,\n" +
                "  case when\n" +
                "    V.id in (" +
                "       select V.id from " +
                "           Trip T join T.vehicles V " +
                "               where T.id = " + tripId +
                "       ) then true\n" +
                "  else false\n" +
                "    end\n" +
                "from Bus V", null);
    }
}
