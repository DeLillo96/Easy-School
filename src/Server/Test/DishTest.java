package Server.Test;

import Server.Entity.Dish;
import Server.Entity.First;
import Server.Entity.Second;
import Server.Entity.Side;
import Server.Repository.DishRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DishTest {

    private static DishRepository dishRepository = new DishRepository();
    private static First first = new First("Pigeon pie");
    private static Second second = new Second("Rice with herbs");
    private static Second otherSecond = new Second("Rice with wolf");

    @BeforeAll
    static void createDish() {
        first.save();
        second.save();
        otherSecond.save();
    }

    @AfterAll
    static void deleteDishes() {
        first.delete();
        second.delete();
        otherSecond.delete();
    }

    @Test
    void readSingleDish() {
        Dish readDish = dishRepository.getDishById(first.getId());
        assertEquals(first.getId(), readDish.getId(), "Id Error on Dish N.1");
        assertEquals(first.getName(), readDish.getName(), "Name Error on Dish N.1");
    }

    @Test
    void readDishByCategory() {
        List readDishes = dishRepository.getSecond(new HashMap<>());
        assertTrue(2 <= readDishes.size(), "Id Error on Dish N.1");
    }

    @Test
    void verifyConstraint() {
        Side newDish = new Side(first.getName());
        Result result = newDish.save();

        assertFalse(result.isSuccess(), "Duplication Error");

        if (!result.isSuccess()) newDish.delete();
    }

    @Test
    void modifyDishes() {
        first.setName("Pane");
        Result result = first.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }

}