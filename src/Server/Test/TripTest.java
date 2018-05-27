package Server.Test;

import Server.Repository.BusRepository;
import Server.Repository.TripRepository;
import Server.Result;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripTest {
    private TripRepository tripRepository = new TripRepository();

    @Test
    void readQueryTest() {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("day", 2);
        List result = tripRepository.read(filters);
        assertTrue(result.size() > 0, "read from filter error");

        JSONObject response = new Result(true, result).toJson();
        assertNotNull(response, "parse error");
    }

    @Test
    void getBusesByTrip() {
        List buses = new BusRepository().getBusesByTrip(20);
        assertNotNull(buses);
    }

    @Test
    void readNotQueryTest() {
        List result = tripRepository.read();
        assertTrue(result.size() > 0, "read all error");

        JSONObject response = new Result(true, result).toJson();
        assertNotNull(response, "parse error");
    }
}
