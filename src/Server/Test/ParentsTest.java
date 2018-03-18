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
    private static Adult dad = new Adult("Eddard", "Stark", "EDDSTK93H57V379L", new Date());
    private static Adult mom = new Adult("Catelyn", "Tully", "CRLTLL93D65L153G", new Date());
    private static Child child = new Child("Arya", "Stark", "RYSTRK83F57K058V", new Date(), "Valar Morghunis");
    private ChildRepository childRepository = new ChildRepository();

    @BeforeAll
    static void createParents() {
        child.getParents().add(dad);
        child.save();
    }

    @Test void readChildWithParents() {
        Child readChild = childRepository.getChildByFiscalCode(child.getCodiceFiscale());

        assertNotNull(readChild, "read child error");
        assertNotNull(readChild.getParents(), "join fallita");
    }

    @Test void addParent() {
        assertTrue(child.getParents().add(mom), "aggiunta genitore fallita");

        Result result = child.save();
        assertTrue(result.isSuccess());
    }

    @AfterAll
    static void deleteParents() {
        child.setParents(null);
        child.save();
        child.delete();
        dad.delete();
        mom.delete();
    }
}
