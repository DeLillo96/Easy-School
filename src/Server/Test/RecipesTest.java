package Server.Test;

import Server.Entity.Alimento;
import Server.Entity.Dish;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipesTest {

    private static Dish dish = new Dish("PaneLupo", "Primo");
    private static Alimento firstAlim = new Alimento("Pane");
    private static Alimento secondAlim = new Alimento("Lupo");
    private DishRepository dishRepository = new DishRepository();

    @BeforeAll
    static void createNewRecipe() {
        firstAlim.save();
        secondAlim.save();
        dish.getIngredients().add(firstAlim);
        dish.save();
    }

    @Test
    void readRecipe() {
        Dish readDish = dishRepository.getDishByName(dish.getName());

        assertNotNull(readDish, "Read recipe error");
        assertNotNull(readDish.getIngredients(), "Failed join");
    }

    @Test
    void addNewIngredient() {
        assertTrue(dish.getIngredients().add(secondAlim));

        Result result = dish.save();
        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteParents() {
        dish.delete();
        firstAlim.delete();
        secondAlim.delete();
    }
}
