package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Repository.AdultRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParentsTest {
    private static Adult mom = new Adult("Catelyn", "Tully", "CRLTLL93D65L153G", new Date(), "7263017233");
    private static Child child = new Child("Arya", "Stark", "RYSTRK83F57K058V", new Date());
    private AdultRepository adultRepository = new AdultRepository();

    @BeforeAll
    static void createParents() {
        child.save();
        mom.getChildren().add(child);
        mom.save();
    }

    @AfterAll
    static void deleteParents() {
        mom.delete();
        child.delete();
    }

    @Test
    void readChildWithParents() {
        Adult readAdult = adultRepository.getAdultByFiscalCode(mom.getFiscalCode());

        assertNotNull(readAdult, "read child error");
        assertTrue(readAdult.getChildren().size() >= 1, "Failed join");
    }

    @Test
    void addParent() {
        assertTrue(child.getParents().add(mom), "Failed add parent operation");

        Result result = child.save();
        assertTrue(result.isSuccess(), "Error during saving operation " + result.getMessages().toString());
    }
}
