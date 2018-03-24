package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Users;
import Server.Repository.AdultRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class AdultTest {
    private static AdultRepository adultRepository = new AdultRepository();
    private static Users user = new Users("hound", "none");
    private static Adult adult = new Adult("Sandor", "Clegane", "SNDCLG92H51A730S", new Date());

    @BeforeAll
    static void createAdult() {
        user.save();
        adult.setUser(user);
        adult.save();
    }

    @Test void readAdult() {
        Adult readAdult = adultRepository.getAdultByFiscalCode(adult.getFiscalCode());

        String message = "Read error";
        assertEquals(adult.getName(), readAdult.getName(), message);
        assertEquals(adult.getSurname(), readAdult.getSurname(), message);
        assertEquals(adult.getFiscalCode(), readAdult.getFiscalCode(), message);
    }

    @Test void readAdultByUser() {
        Adult readAdult = adultRepository.getAdultByReferencedUser(user);

        Users readUser = readAdult.getUser();

        String message = "Read error";
        assertEquals(user.getUsername(), readUser.getUsername(), message);
        assertEquals(user.getPassword(), readUser.getPassword(), message);
        assertEquals(user.getEmail(), readUser.getEmail(), message);
    }

    @Test void verifyConstraint() {
        Adult newAdult = new Adult("Rory", "McCann", adult.getFiscalCode(), new Date());
        Result result = newAdult.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if(!result.isSuccess()) newAdult.delete();
    }

    @Test void modifyAdult() {
        adult.setName("Rory");
        adult.setSurname("McCann");
        Result result = adult.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteAdult() {
        adult.delete();
        user.delete();
    }
}
