package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Entity.Users;
import Server.Repository.AdultRepository;
import Server.Repository.ChildRepository;
import Server.Repository.UsersRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AdultTest {

    private static UsersRepository userRepository = new UsersRepository();
    private static Users user = new Users("username", "password", "mail");
    private static AdultRepository adultRepository = new AdultRepository();
    private static Adult adult = new Adult("Giovanni", "Neve", "NVGVNN96T27V730G", new Date());

    @BeforeAll
    static void createAdult() {
        user.save();
        adult.save();
    }

    @Test void readAdult() {
        adult.setUser(user);
        adult.save();
        Adult readAdult = adultRepository.getAdultByFiscalCode(adult.getCodiceFiscale());

        assertEquals(adult.getCodiceFiscale(), readAdult.getCodiceFiscale(), "Errore di codice fiscale");
        assertEquals(adult.getNome(), readAdult.getNome(), "Errore di nome");

        readAdult = adultRepository.getAdultByReferencedUser(user);

        Users prova = readAdult.getUser();

        assertEquals(user.getUsername(), prova.getUsername(), "Errore di utente");
    }

    @Test void verifyConstraint() {
        Adult secondFather = new Adult("Ajeje", "Brazzorf", "NVGVNN96T27V730G", new Date());
        Result result = secondFather.save();
        assertFalse(result.isSuccess(), "Ajeje Brazzorf is the new Giovanni Neve");
    }

    @Test void modifyAdult() {
        adult.setNome("John");
        adult.setCognome("Snow");
        Result modify = adult.save();


        assertTrue(modify.isSuccess(), "Giovanni Neve non si può modificare, MAI");
    }

    @AfterAll
    static void deleteAdult() {
        adult.delete();
        user.delete();
    }

    /*@Test
    public void adultEntityTest() {
        AdultRepository adultRepository = new AdultRepository();
        Adult father = new Adult("Giovanni", "Neve", "GIOVANEVE976XD", new Date());

        UsersRepository userRep = new UsersRepository();
        Users user = userRep.getUserById(6);
        father.setUser(new UsersRepository().getUserById(1));

        Result result = father.save();
        assertTrue(result.isSuccess(), "Errore di salvataggio: " + result.getMessages().toString());

        father = adultRepository.getAdultById( father.getId() );
        assertNotNull(father, "Errore di lettura su DB");

        assertEquals("Giovanni", father.getNome(), "errore di lettura dei dati");
        assertEquals("Neve", father.getCognome(), "errore di lettura dei dati");
        assertEquals("GIOVANEVE976XD", father.getCodiceFiscale(), "errore di lettura dei dati");
        assertEquals("fdilillo", father.getUser().getUsername(), "non è suo padre");

        result = father.delete();
        assertTrue(result.isSuccess(), "Errore di cancellazione: " + result.getMessages().toString());
    }*/
}
