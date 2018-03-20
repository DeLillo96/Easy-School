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
    private static Alimento alimentOne = new Alimento("Formaggio");
    private static Alimento alimentTwo = new Alimento("Pane Lupo");

    @BeforeAll
    static void createALiment() {
        alimentOne.save();
        alimentTwo.save();
    }

    @Test void readAliment() {
        Alimento temp = alimentRepository.getAlimentByNome(alimentOne.getNome());
        assertEquals(alimentOne.getId(),temp.getId(),"Errore di ID");
        assertEquals(alimentOne.getNome(),temp.getNome(),"Errore di Nome");
    }

    @Test void veryfyConstraint() {
        Alimento cheesyCopy = new Alimento(alimentOne.getNome());
        Result result = cheesyCopy.save();
        assertFalse(result.isSuccess(), "FORMAGGIO PER TUTTI!");
    }

    @Test void modifyAliment() {
        alimentOne.setNome("Mozzarella");
        alimentTwo.setNome("Mozzarella");
        Result firstTry = alimentOne.save();
        Result secondTry = alimentTwo.save();
        assertTrue(firstTry.isSuccess(),"Poco formaggio");
        assertFalse(secondTry.isSuccess(),"Troppo formaggio");
    }

    @AfterAll
    static void deleteAliment() {
        alimentOne.delete();
        alimentTwo.delete();
    }

}
