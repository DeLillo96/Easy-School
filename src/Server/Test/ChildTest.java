package Server.Test;

import Server.Entity.Child;
import Server.Entity.Staff;
import Server.Entity.Trip;
import Server.Repository.ChildRepository;
import Server.Repository.TripRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChildTest {
    private static ChildRepository childRepository = new ChildRepository();
    private static TripRepository tripRepository = new TripRepository();
    private static Child child;
    private static Trip trip = new Trip("Trip to the Wall");

    @BeforeAll
    static void createChild() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("2006-05-04");
            child = new Child("Jon", "Snow", "SNWJHN96T27V730G", calendarDay);
        }catch(Exception e) {}
        child.save();
        trip.save();
        trip.getChildInTrip().add(child);
        Result result = trip.save();
    }

    @AfterAll
    static void deleteChild() {
        trip.delete();
        child.delete();
    }

    @Test
    void readChild() {
        Child readChild = childRepository.getChildByFiscalCode(child.getFiscalCode());

        String message = "Read error";
        assertEquals(child.getName(), readChild.getName(), message);
        assertEquals(child.getSurname(), readChild.getSurname(), message);
        assertEquals(child.getBirthDate(), readChild.getBirthDate(), message);
        assertEquals(child.getBirthDate(), readChild.getBirthDate(), message);
    }

    @Test
    void verifyConstraint() {
        Child newChild = new Child("Rory", "McCann", child.getFiscalCode(), new Date());
        Result result = new Result();
        try {
            result = newChild.save();
        }catch (Exception e) {
            result.setSuccess(false);
        }
        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if (!result.isSuccess()) newChild.delete();
    }

    @Test
    void readChildInTrip(){
        List result = childRepository.getChildInTrip(trip.getId());
        assertTrue(result.size() > 0);
    }

    @Test
    void readCountChildInTrip(){
        Integer result = childRepository.getCountChildInTrip(trip.getId());
        assertTrue(result > 0);
    }

    @Test
    void modifyChild() {
        child.setSurname("Targarien");
        Result result = child.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }
}
