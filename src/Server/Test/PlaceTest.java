package Server.Test;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.Place;
import Server.Repository.BusRepository;
import Server.Repository.DayTripRepository;
import Server.Repository.PlaceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlaceTest {

    private static PlaceRepository placeRepository = new PlaceRepository();
    private static DayTripRepository dayTripRepository = new DayTripRepository();
    private static BusRepository busRepository = new BusRepository();
    private static Bus busOne = new Bus("StarkTours");
    private static Bus busTwo = new Bus("RenegadeTours");
    private static DayTrip tripOne = new DayTrip("Northern Territories");
    private static DayTrip tripTwo = new DayTrip("Right to the Wall");
    private static Place placeOne = new Place("Winterfell", "Stark family's home", 60);
    private static Place placeTwo = new Place("The Wall", "Extreme north", 15);

    @BeforeAll
    static void createPlaces() {
        busOne.save();
        busTwo.save();
        placeOne.setStartBus(busOne);
        placeTwo.setStartBus(busOne);
        placeOne.setArrivalBus(busTwo);
        placeTwo.setArrivalBus(busTwo);
        placeOne.save();
        placeTwo.save();
        tripOne.getPlaces().add(placeOne);
        tripOne.getPlaces().add(placeTwo);
        tripTwo.getPlaces().add(placeTwo);
        tripOne.save();
        tripTwo.save();
    }

    @Test
    void readPlacesByCost() {
        Set resultSingle = placeRepository.getPlaceByCost(40);
        Set resultMultiple = placeRepository.getPlaceByCost(60);
        assertNotNull(resultSingle);
        assertNotNull(resultMultiple);
    }

    @Test
    void readPlacesByTrip() {
        Set result = placeRepository.getPlaceByDayTrip(tripOne.getId());
        assertNotNull(result);
    }

    @AfterAll
    static void deletePlaces() {
        placeOne.delete();
        placeTwo.delete();
        tripOne.delete();
        tripTwo.delete();
        busOne.delete();
        busTwo.delete();
    }
}