package Server.Test;

import Server.Entity.Category;
import Server.Entity.Dish;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {

    private static DishRepository dishRepository = new DishRepository();
    private static Dish dish = new Dish("Pigeon pie");
    private static Dish otherDish = new Dish ("Rice with herbs");
    private static Category category = new Category("first");
    private static Category categoryTwo = new Category("second");

    @BeforeAll
    static void createDish() {
        category.save();
        categoryTwo.save();
        dish.setCategory(category);
        otherDish.setCategory(categoryTwo);
        dish.save();
        otherDish.save();
    }

    @Test
    void readDishOne() {
        Dish readDish = dishRepository.getDishById(dish.getId());
        Category readCategory = dish.getCategory();
        assertEquals(dish.getId(), readDish.getId(), "Id Error on Dish N.1");
        assertEquals(dish.getName(), readDish.getName(), "Name Error on Dish N.1");
        assertEquals(category.getId(), readCategory.getId(), "Id Error on Category N.1");
        assertEquals(category.getName(), readCategory.getName(), "Name Error on Category N.1");
    }

    @Test
    void readDishTwo() {
        Dish readDish = dishRepository.getDishById(otherDish.getId());
        Category readCategory = otherDish.getCategory();
        assertEquals(otherDish.getId(), readDish.getId(), "Id Error on Dish N.2");
        assertEquals(otherDish.getName(), readDish.getName(), "Name Error on Dish N.1");
        assertEquals(categoryTwo.getId(), readCategory.getId(), "Id Error on Category N.2");
        assertEquals(categoryTwo.getName(), readCategory.getName(), "Name Error on Category N.2");
    }

    @Test
    void readFromCategory() {
        List result = dishRepository.getDishByCategory(categoryTwo.getName());
        assertNotNull(result);
    }

    @Test
    void verifyConstraint() {
        Dish newDish = new Dish(dish.getName());
        Result result = newDish.save();

        assertFalse(result.isSuccess(), "Duplication Error");

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
        otherDish.delete();
        category.delete();
        categoryTwo.delete();
    }

}