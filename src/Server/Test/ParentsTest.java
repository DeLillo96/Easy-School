package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Child;
import Server.Repository.ChildRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParentsTest {
    private static Adult dad = new Adult("Eddard", "Stark", "EDDSTK93H57V379L", new Date(), "7395061038");
    private static Adult mom = new Adult("Catelyn", "Tully", "CRLTLL93D65L153G", new Date(), "7263017233");
    private static Child child = new Child("Arya", "Stark", "RYSTRK83F57K058V", new Date());
    private ChildRepository childRepository = new ChildRepository();

    @BeforeAll
    static void createParents() {
        mom.save();
        dad.save();
        child.getParents().add(dad);
        child.save();
    }

    @AfterAll
    static void deleteParents() {
        child.delete();
        dad.delete();
        mom.delete();
    }

    @Test
    void readChildWithParents() {
        Child readChild = childRepository.getChildByFiscalCode(child.getFiscalCode());

        assertNotNull(readChild, "read child error");
        assertNotNull(readChild.getParents(), "Failed join");
    }

    @Test
    void addParent() {
        assertTrue(child.getParents().add(mom), "Failed add parent operation");

        Result result = child.save();
        assertTrue(result.isSuccess(), "Error during saving operation " + result.getMessages().toString());
    }
}
