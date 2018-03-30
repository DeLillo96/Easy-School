package Server.Test;

import Server.Entity.Bus;
import Server.Entity.Calendar;
import Server.Entity.DayTrip;
import Server.Entity.Place;
import Server.Repository.BusRepository;
import Server.Repository.CalendarRepository;
import Server.Repository.DayTripRepository;
import Server.Repository.PlaceRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DayTripTest {

    private static DayTripRepository dayTripRepository = new DayTripRepository();
    private static CalendarRepository calendarRepository = new CalendarRepository();
    private static PlaceRepository placeRepository = new PlaceRepository();
    private static BusRepository busRepository = new BusRepository();
    private static Calendar calendarOne = new Calendar(new Date());
    private static Place placeOne = new Place("Winterfell", "Stark family's home", 60);
    private static Place placeTwo = new Place("The Wall", "Extreme north", 15);
    private static Bus busOne = new Bus("StarkTours");
    private static Bus busTwo = new Bus("RenegadeTours");
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

    @Test
    void readTripByDay() {
        Set result = dayTripRepository.getDayTripByDate(calendarOne.getId());
        assertNotNull(result);
    }

    @Test
    void readTripByPlace() {
        Set resultSingle = dayTripRepository.getDayTripByPlace(placeOne.getId());
        Set resultMultiple = dayTripRepository.getDayTripByPlace(placeTwo.getId());
        assertNotNull(resultSingle);
        assertNotNull(resultMultiple);
    }

    @Test
    void readTripByBus() {
        Set result = dayTripRepository.getDayTripByBus(busTwo.getId());
        assertNotNull(result);

        tripTwo.getBuses().remove(busTwo);
        tripTwo.save();
        result = dayTripRepository.getDayTripByBus(busTwo.getId());
        assertNotNull(result);
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

}
