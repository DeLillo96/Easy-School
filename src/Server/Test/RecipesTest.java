package Server.Test;

import Server.Entity.Aliment;
import Server.Entity.Dish;
import Server.Entity.Side;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipesTest {

    private static Dish dish = new Side("Bread & Wolf");
    private static Aliment firstAlim = new Aliment("Bread");
    private static Aliment secondAlim = new Aliment("Wolf");
    private DishRepository dishRepository = new DishRepository();

    @BeforeAll
    static void createNewRecipe() {
        firstAlim.save();
        secondAlim.save();
        dish.getIngredients().add(firstAlim);
        dish.save();
    }

    @AfterAll
    static void deleteParents() {
        dish.delete();
        firstAlim.delete();
        secondAlim.delete();
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
}