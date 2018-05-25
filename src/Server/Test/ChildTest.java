package Server.Test;

import Server.Entity.Child;
import Server.Repository.ChildRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChildTest {
    private static ChildRepository childRepository = new ChildRepository();
    private static Child child = new Child("Jon", "Snow", "SNWJHN96T27V730G", new Date());

    @BeforeAll
    static void createChild() {
        //child.save();
    }

    @AfterAll
    static void deleteChild() {
        //child.delete();
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
    void readChildByDate() {
        List readChildren = childRepository.getChildByBirthDate(child.getBirthDate());

        String message = "Read error";
        assertTrue(readChildren.size() >= 1, message);
    }

    @Test
    void verifyConstraint() {
        Child newChild = new Child("Impostor", "Impostor", child.getFiscalCode(), new Date());
        Result result = newChild.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());
        if (!result.isSuccess()) newChild.delete();

    }

    /*@Test
    void getChildByDate() {
        Child readChild = childRepository.getChildByBirthDate(child.getBirthDate());
        String message = "Read error";
        assertEquals(child.getName(), readChild.getName(), message);
    }*/

    @Test
    void readChildInTrip(){
        List result = childRepository.getChildInTrip(1);
        assertTrue(result.size() > 0);
    }

    @Test
    void modifyChild() {
        child.setSurname("Targarien");
        Result result = child.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }
}
