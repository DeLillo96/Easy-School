package Server.Test;

import Server.Entity.Alimento;
import Server.Repository.AlimentRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlimentTest {

    private static AlimentRepository alimentRepository = new AlimentRepository();
    private static Alimento aliment = new Alimento("farina");

    @BeforeAll
    static void createALiment() {
        aliment.save();
    }

    @Test void readAliment() {
        Alimento readAliment = alimentRepository.getAlimentByNome(aliment.getNome());

        String message = "Read error";
        assertEquals(aliment.getId(), readAliment.getId(), message);
        assertEquals(aliment.getNome(), readAliment.getNome(), message);
    }

    @Test void veryfyConstraint() {
        Alimento newAliment = new Alimento(aliment.getNome());
        Result result = newAliment.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if(!result.isSuccess()) newAliment.delete();
    }

    @Test void modifyAliment() {
        aliment.setNome("Mozzarella");
        Result result = aliment.save();

        assertTrue(result.isSuccess(),"Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteAliment() {
        aliment.delete();
    }

}
