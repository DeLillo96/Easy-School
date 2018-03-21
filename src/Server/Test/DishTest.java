package Server.Test;

import Server.Entity.Dish;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {

    private static DishRepository dishRepository = new DishRepository();
    private static Dish dish = new Dish("Pasticcio di piccione", "Secondo");

    @BeforeAll
    static void createDish() {
        dish.save();
    }

    @Test
    void readDishByName() {
        Dish readDish = dishRepository.getDishByName(dish.getName());
        assertDishesEqual(dish, readDish);
    }

    @Test
    void readDishById() {
        Dish readDish = dishRepository.getDishById(dish.getId());
        assertDishesEqual(dish, readDish);
    }

    private void assertDishesEqual(Dish dish1, Dish dish2) {
        String message = "Read by id error";
        assertEquals(dish1.getId(), dish2.getId(), message);
        assertEquals(dish1.getName(), dish2.getName(), message);
        assertEquals(dish1.getCategory(), dish2.getCategory(), message);
    }

    @Test
    void verifyConstraint() {
        Dish newDish = new Dish(dish.getName(), "Secondo");
        Result result = newDish.save();

        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if(!result.isSuccess()) newDish.delete();
    }

    @Test
    void modifyDishes() {
        dish.setName("Pane");
        Result result = dish.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteDishes() {
        dish.delete();
    }

}
