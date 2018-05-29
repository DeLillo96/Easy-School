package Server.Test;

import Server.Entity.Trip;
import Server.Repository.BusRepository;
import Server.Repository.TripRepository;
import Server.Result;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripTest {
    private static TripRepository tripRepository = new TripRepository();
    private static Trip trip = new Trip("Trip to the wall");

    @BeforeAll
    static void createTrip() {
        trip.save();
    }

    @Test
    void getBusesByTrip() {
        List buses = new BusRepository().getBusesByTrip(trip.getId());
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
