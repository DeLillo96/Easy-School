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
        Adult readAdult = adultRepository.getAdultByFiscalCode(adult.getCodiceFiscale());

        String message = "Read error";
        assertEquals(adult.getNome(), readAdult.getNome(), message);
        assertEquals(adult.getCognome(), readAdult.getCognome(), message);
        assertEquals(adult.getCodiceFiscale(), readAdult.getCodiceFiscale(), message);
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
        Adult newAdult = new Adult("Rory", "McCann", adult.getCodiceFiscale(), new Date());
        Result result = newAdult.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if(!result.isSuccess()) newAdult.delete();
    }

    @Test void modifyAdult() {
        adult.setNome("Rory");
        adult.setCognome("McCann");
        Result result = adult.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteAdult() {
        adult.delete();
        user.delete();
    }
}
