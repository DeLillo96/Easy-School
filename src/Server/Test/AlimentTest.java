package Server.Test;

import Server.Entity.Aliment;
import Server.Repository.AlimentRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlimentTest {

    private static AlimentRepository alimentRepository = new AlimentRepository();
    private static Aliment aliment = new Aliment("Flour");

    @BeforeAll
    static void createALiment() {
        aliment.save();
    }

    @AfterAll
    static void deleteAliment() {
        aliment.delete();
    }

    @Test
    void readAliment() {
        Aliment readAliment = alimentRepository.getAlimentByName(aliment.getName());

        String message = "Read error";
        assertEquals(aliment.getId(), readAliment.getId(), message);
        assertEquals(aliment.getName(), readAliment.getName(), message);
    }

    @Test
    void veryfyConstraint() {
        Aliment newAliment = new Aliment(aliment.getName());
        Result result = newAliment.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if (!result.isSuccess()) newAliment.delete();
    }

    @Test
    void modifyAliment() {
        aliment.setName("Cheese");
        Result result = aliment.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

}
