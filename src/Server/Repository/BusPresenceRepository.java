package Server.Repository;

import Server.Entity.BusPresence;

import java.util.HashMap;
import java.util.List;

public class BusPresenceRepository extends AbstractRepository {

    public BusPresenceRepository() {
        super("BusPresence");
    }

    public BusPresence getBusPresenceById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List buses = read(params);
        return buses != null && buses.size() == 1 ? (BusPresence) buses.get(0) : null;
    }
}
