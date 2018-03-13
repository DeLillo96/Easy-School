package Server.Test;

import Server.Entity.Child;
import Server.Repository.ChildRepository;
import Server.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ChildTest {

    @Test
    public void childRepositoryTest() {
        ChildRepository childRepository = new ChildRepository();

        assertNotNull(childRepository.read(), "la lettura deve essere una lista");
        assertNotNull(childRepository.getChildByFiscalCode(""), "non può essere nullo");
        assertNotNull(childRepository.getChildById(1), "non può essere nullo");
    }

    @Test
    @DisplayName("The John Snow Test")
    public void childEntityTest() {
        ChildRepository childRepository = new ChildRepository();
        Child john = new Child("John", "Snow", new Date(), "lui è solo soletto");

        Result result = john.save();
        assertTrue(result.isSuccess(), "Errore di salvataggio: " + result.getMessages().toString());

        john = childRepository.getChildById( john.getId() );
        assertNotNull(john, "Errore di lettura su DB");

        assertEquals("John", john.getNome(), "errore di lettura dei dati");
        assertEquals("Snow", john.getCognome(), "errore di lettura dei dati");
        assertNull(john.getCodiceFiscale(), "errore di lettura dei dati");
        assertEquals("lui è solo soletto", john.getContatti(), "errore di lettura dei dati");

        result = john.delete();
        assertTrue(result.isSuccess(), "Errore di cancellazione: " + result.getMessages().toString());
    }
}
