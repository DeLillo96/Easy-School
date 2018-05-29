package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Users;
import Server.Repository.AdultRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AdultTest {
    private static AdultRepository adultRepository = new AdultRepository();
    private static Adult adult;
    @BeforeAll
    static void createAdult() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("1986-05-04");
            adult = new Adult("Sandor", "Clegane", "SNDCLG92H51A730S", calendarDay, "8204710473");
        }catch(Exception e) {}
        adult.save();
    }

    @AfterAll
    static void deleteAdult() {
        adult.delete();
    }

    @Test
    void readAdult() {
        Adult readAdult = adultRepository.getAdultByFiscalCode(adult.getFiscalCode());

        String message = "Read error";
        assertEquals(adult.getName(), readAdult.getName(), message);
        assertEquals(adult.getSurname(), readAdult.getSurname(), message);
        assertEquals(adult.getFiscalCode(), readAdult.getFiscalCode(), message);
    }

    @Test
    void addDoubleAdult() {
        Adult newAdult;

        newAdult = adultRepository.getAdultByFiscalCode(adult.getFiscalCode());
        newAdult.setId(0);

        Result result = newAdult.save();

        String message = "Read error";
        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());
        if (!result.isSuccess()) newAdult.delete();
    }

    @Test
    void verifyConstraint() {
        Adult newAdult = new Adult("Rory", "McCann", adult.getFiscalCode(), new Date(), "8273058105");
        Result result = new Result();
        try {
            result = newAdult.save();
        }catch (Exception e) {
            result.setSuccess(false);
        }
        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if (!result.isSuccess()) newAdult.delete();
    }

    @Test
    void modifyAdult() {
        adult.setName("Rory");
        adult.setSurname("McCannnnnn");
        Result result = adult.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }
}
