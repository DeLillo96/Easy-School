package Server.Test;

import Server.Entity.Bus;
import Server.Entity.Calendar;
import Server.Entity.DayTrip;
import Server.Entity.Place;
import Server.Repository.DayTripRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DayTripTest {
    private static DayTripRepository dayTripRepository = new DayTripRepository();
    private static Calendar calendarOne = new Calendar(new Date());
    private static Place placeOne = new Place("Winterfell", "Stark family's home", 60);
    private static Place placeTwo = new Place("The Wall", "Extreme north", 15);
    private static Bus busOne = new Bus("AA111BB", "StarkTours");
    private static Bus busTwo = new Bus("CC222DD", "RenegadeTours");
    private static DayTrip tripOne = new DayTrip("Northern Territories");
    private static DayTrip tripTwo = new DayTrip("Right to the Wall");

    @BeforeAll
    static void createTrips() {
        calendarOne.save();
        placeOne.save();
        placeTwo.save();
        busOne.save();
        busTwo.save();
        tripOne.setDay(calendarOne);
        tripTwo.setDay(calendarOne);
        tripOne.getPlaces().add(placeOne);
        tripOne.getPlaces().add(placeTwo);
        tripTwo.getPlaces().add(placeTwo);
        tripOne.getBuses().add(busOne);
        tripTwo.getBuses().add(busTwo);
        tripOne.save();
        tripTwo.save();
    }

    @AfterAll
    static void deleteTrips() {
        tripOne.delete();
        tripTwo.delete();
        busOne.delete();
        busTwo.delete();
        placeOne.delete();
        placeTwo.delete();
        calendarOne.delete();
    }

    @Test
    void readTripByDay() {
        List result = dayTripRepository.getDayTripByDate(calendarOne.getId());
        assertNotNull(result);
    }

    @Test
    void readTripByPlace() {
        List resultSingle = dayTripRepository.getDayTripByPlace(placeOne.getId());
        assertNotNull(resultSingle);
    }

    @Test
    void readMultipleTripByPlace() {
        List resultMultiple = dayTripRepository.getDayTripByPlace(placeTwo.getId());
        assertNotNull(resultMultiple);
    }

    @Test
    void readTripByBus() {
        List result = dayTripRepository.getDayTripByBus(busTwo.getId());
        assertNotNull(result);
    }

}
