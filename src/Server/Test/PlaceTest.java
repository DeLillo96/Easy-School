package Server.Test;

import Server.Entity.Bus;
import Server.Entity.DayTrip;
import Server.Entity.Place;
import Server.Repository.PlaceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlaceTest {

    private static PlaceRepository placeRepository = new PlaceRepository();
    private static Bus busOne = new Bus("AA111BB","StarkTours");
    private static Bus busTwo = new Bus("CC222DD","RenegadeTours");
    private static DayTrip tripOne = new DayTrip("Northern Territories");
    private static DayTrip tripTwo = new DayTrip("Right to the Wall");
    private static Place placeOne = new Place("Winterfell", "Stark family's home", 60);
    private static Place placeTwo = new Place("The Wall", "Extreme north", 15);

    @BeforeAll
    static void createPlaces() {
        busOne.save();
        busTwo.save();
        placeOne.getStartBuses().add(busOne);
        placeTwo.getStartBuses().add(busOne);
        placeOne.getArrivalBuses().add(busTwo);
        placeTwo.getArrivalBuses().add(busTwo);
        placeOne.save();
        placeTwo.save();
        tripOne.getPlaces().add(placeOne);
        tripOne.getPlaces().add(placeTwo);
        tripTwo.getPlaces().add(placeTwo);
        tripOne.save();
        tripTwo.save();
    }

    @Test
    void readSinglePlacesByCost() {
        Set resultSingle = placeRepository.getPlaceByCost(40); //WTF!?
        assertNotNull(resultSingle);
    }

    @Test
    void readMultiplePlacesByCost() {
        Set resultMultiple = placeRepository.getPlaceByCost(60); //WTF!?
        assertNotNull(resultMultiple);
    }

    @Test
    void readPlacesByTrip() {
        Set result = placeRepository.getPlaceByDayTrip(tripOne.getId());
        assertNotNull(result);
    }

    //todo test di modifica

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