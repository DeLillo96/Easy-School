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
        Child readChild = childRepository.getChildByFiscalCode(child.getFiscalCode());

        String message = "Read error";
        assertEquals(child.getName(), readChild.getName(), message);
        assertEquals(child.getSurname(), readChild.getSurname(), message);
        assertEquals(child.getBirthDate(), readChild.getBirthDate(), message);
        assertEquals(child.getContacts(), readChild.getContacts(), message);
    }

    @Test void verifyConstraint() {
        Child newChild = new Child(
                "Impostor",
                "Impostor",
                child.getFiscalCode(),
                new Date(),
                "Nobody");
        Result result = newChild.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());
        if(!result.isSuccess()) newChild.delete();

    }

    @Test void modifyChild() {
        child.setSurname("Targarien");
        Result result = child.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteChild() {
        child.delete();
    }
}
