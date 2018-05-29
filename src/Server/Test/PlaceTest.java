package Server.Test;

import Server.Entity.Bus;
import Server.Entity.Trip;
import Server.Entity.Place;
import Server.Repository.PlaceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlaceTest {

    private static PlaceRepository placeRepository = new PlaceRepository();
    private static Bus busOne = new Bus("AA111BB", "StarkTours");
    private static Bus busTwo = new Bus("CC222DD", "RenegadeTours");
    private static Trip tripOne = new Trip("Northern Territories");
    private static Trip tripTwo = new Trip("Right to the Wall");
    private static Place placeOne = new Place("Winterfell", "Stark family's home", 60);
    private static Place placeTwo = new Place("The Wall", "Extreme north", 15);

    @BeforeAll
    static void createPlaces() {
        busOne.save();
        busTwo.save();
        placeOne.save();
        placeTwo.save();
        tripOne.getPlaces().add(placeOne);
        tripOne.getPlaces().add(placeTwo);
        tripTwo.getPlaces().add(placeTwo);
        tripOne.save();
        tripTwo.save();
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

    @Test
    void readSinglePlacesByCost() {
        List resultSingle = placeRepository.getPlaceByCost(40); //WTF!?
        assertNotNull(resultSingle);
    }

    @Test
    void readMultiplePlacesByCost() {
        List resultMultiple = placeRepository.getPlaceByCost(60); //WTF!?
        assertNotNull(resultMultiple);
    }

    @Test
    void readPlacesByTrip() {
        List result = placeRepository.getPlaceByTrip(tripOne.getId());
        assertNotNull(result);
    }
}