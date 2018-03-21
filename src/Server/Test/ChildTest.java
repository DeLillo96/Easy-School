package Server.Test;

import Server.Entity.Child;
import Server.Repository.ChildRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChildTest {
    private static ChildRepository childRepository = new ChildRepository();
    private static Child child = new Child("Jon", "Snow", "SNWJHN96T27V730G", new Date(), "A raven to the Barrier");

    @BeforeAll
    static void createChild() {
        child.save();
    }

    @Test void readChild() {
        Child readChild = childRepository.getChildByFiscalCode(child.getCodiceFiscale());

        String message = "Read error";
        assertEquals(child.getNome(), readChild.getNome(), message);
        assertEquals(child.getCognome(), readChild.getCognome(), message);
        assertEquals(child.getNascita(), readChild.getNascita(), message);
        assertEquals(child.getContatti(), readChild.getContatti(), message);
    }

    @Test void verifyConstraint() {
        Child newChild = new Child(
                "impostore",
                "impostore",
                child.getCodiceFiscale(),
                new Date(),
                "nessuno");
        Result result = newChild.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());
        if(!result.isSuccess()) newChild.delete();

    }

    @Test void modifyChild() {
        child.setCognome("Targarien");
        Result result = child.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteChild() {
        child.delete();
    }
}
