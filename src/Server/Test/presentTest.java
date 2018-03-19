package Server.Test;

import Server.Entity.Calendario;
import Server.Entity.Child;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class presentTest {
    private static Child child = new Child("Daenerys", "Targaryen", "DNRTRG50J15S649F", new Date(), "Dracarys");
    private static Calendario calendario = new Calendario();

    @BeforeAll
    private static void initData() {
        child.save();
        calendario.save();
    }

    @Test void signPresent() {
        calendario.getPresentChilds().add(child);
        Result result = calendario.save();

        assertTrue(result.isSuccess(), "Errore di salvataggio + " + result.getMessages().toString());
    }

    @AfterAll
    private static void deleteData() {
        calendario.delete();
        child.delete();
    }
}
