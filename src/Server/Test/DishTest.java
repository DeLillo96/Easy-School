package Server.Test;

import Server.Entity.Dish;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DishTest {

    private static DishRepository dishRepository = new DishRepository();
    private static Dish dishOne = new Dish("PaneLupo", "Primo");
    private static Dish dishTwo = new Dish("Carne di Lupo", "Secondo");

    @BeforeAll
    static void createDishes() {
        dishOne.save();
        dishTwo.save();
    }

    @Test
    void readDishes() {
        Dish temp = dishRepository.getDishByName(dishOne.getName());

        assertEquals(dishOne.getId(), temp.getId(), "Errore di id nel primo");
        assertEquals(dishOne.getName(), temp.getName(), "Errore di nome nel primo");

        temp = dishRepository.getDishById(dishTwo.getId());

        assertEquals(dishTwo.getId(), temp.getId(), "Errore di id nel secondo");
        assertEquals(dishTwo.getName(), temp.getName(), "Errore di nome nel secondo");
    }

    @Test
    void verifyConstraint() {
        Dish tempTrue = new Dish("Pane Lupo", "Primo");
        Dish tempFalse = new Dish("PaneLupo", "Primo");
        Result resultTrue = tempTrue.save();
        Result resultFalse = tempFalse.save();

        assertTrue(resultTrue.isSuccess(), "Non salva correttamente piatti simili");
        assertFalse(resultFalse.isSuccess(), "Troppo pane");

        tempTrue.delete();
    }

    @Test
    void modifyDishes() {
        dishOne.setName("Pane");
        Result result = dishOne.save();

        assertTrue(result.isSuccess(), "Mancata modifica");

        dishOne.setName(dishTwo.getName());
        result = dishOne.save();

        assertFalse(result.isSuccess(), "Non è unique");
    }

    @AfterAll
    static void deleteDishes() {
        dishOne.delete();
        dishTwo.delete();
    }

}
