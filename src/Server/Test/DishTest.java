package Server.Test;

import Server.Entity.Category;
import Server.Entity.Dish;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {

    private static DishRepository dishRepository = new DishRepository();
    private static Dish dish = new Dish("Pasticcio di piccione");
    private static Dish otherDish = new Dish ("Risotto con le erbette");
    private static Category category = new Category("Secondo");

    @BeforeAll
    static void createDish() {
        category.save();
        dish.setCategory(category);
        otherDish.setCategory(category);
        dish.save();
        otherDish.save();
    }

    @Test
    void readDish() {
        Dish readDish = dishRepository.getDishById(dish.getId());
        assertEquals(dish.getId(), readDish.getId(), "Errore di lettura dell'id del piatto 1");
        assertEquals(dish.getName(), readDish.getName(), "Errore di lettura del nome del piatto 1");

        Category readCategory = dish.getCategory();
        assertEquals(category.getId(), readCategory.getId(), "Errore di lettura del nome della categoria 1");
        assertEquals(category.getName(), readCategory.getName(), "Errore di lettura del nome della categoria 1");

        readDish = dishRepository.getDishById(otherDish.getId());
        assertEquals(otherDish.getId(), readDish.getId(), "Errore di lettura dell'id del piatto 2");
        assertEquals(otherDish.getName(), readDish.getName(), "Errore di lettura del nome del piatto 2");

        readCategory = otherDish.getCategory();
        assertEquals(category.getId(), readCategory.getId(), "Errore di lettura del nome della categoria 2");
        assertEquals(category.getName(), readCategory.getName(), "Errore di lettura del nome della categoria 2");
    }

    @Test
    void verifyConstraint() {
        Dish newDish = new Dish(dish.getName());
        Result result = newDish.save();

        assertFalse(result.isSuccess(), "Errore di duplicazione");

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
    }

}