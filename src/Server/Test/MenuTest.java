package Server.Test;

import Server.Entity.Calendar;
import Server.Entity.Category;
import Server.Entity.Dish;
import Server.Entity.Menu;
import Server.Repository.CalendarRepository;
import Server.Repository.CategoryRepository;
import Server.Repository.DishRepository;
import Server.Repository.MenuRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuTest {

    private static CategoryRepository categoryRepository = new CategoryRepository();
    private static MenuRepository menuRepository = new MenuRepository();
    private static CalendarRepository calendarRepository = new CalendarRepository();
    private static DishRepository dishRepository = new DishRepository();
    private static Category firstCategory = new Category("First dish");
    private static Category secondCategory = new Category("Second dish");
    private static Category sideCategory = new Category("Side dish");
    private static Category sweetCategory = new Category("Sweet");
    private static Calendar firstDay = new Calendar(new Date());
    private static Calendar secondDay = new Calendar(new Date());
    private static Dish firstDish = new Dish("Pidgeon Pie");
    private static Dish secondDishOptionOne = new Dish("Wolf's Left Leg");
    private static Dish secondDishOptionTwo = new Dish("Wolf's Right Leg");
    private static Dish sideDish = new Dish("Wall's Potatoes");
    private static Dish sweetDish = new Dish("Revenge");
    private static Menu menuOne = new Menu();
    private static Menu menuTwo = new Menu();

    @BeforeAll
    static void createMenus() {
        firstCategory.save();
        secondCategory.save();
        sideCategory.save();
        sweetCategory.save();
        firstDish.setCategory(firstCategory);
        secondDishOptionOne.setCategory(secondCategory);
        secondDishOptionTwo.setCategory(secondCategory);
        sideDish.setCategory(sideCategory);
        sweetDish.setCategory(sweetCategory);
        firstDish.save();
        secondDishOptionOne.save();
        secondDishOptionTwo.save();
        sideDish.save();
        sweetDish.save();
        menuOne.setFirstDish(firstDish);
        menuOne.setSecondDish(secondDishOptionOne);
        menuOne.setSideDish(sideDish);
        menuOne.setSweet(sweetDish);
        menuTwo.setFirstDish(firstDish);
        menuTwo.setSecondDish(secondDishOptionTwo);
        menuTwo.setSideDish(sideDish);
        menuTwo.setSweet(sweetDish);
        menuOne.save();
        menuTwo.save();
        firstDay.setDailyMenu(menuOne);
        secondDay.setDailyMenu(menuTwo);
        firstDay.save();
        secondDay.save();
    }

    @Test
    void readMenusByFirstDish() {
        Set result = menuRepository.getMenusByFirstDish(firstDish.getName());
        assertNotNull(result);
    }

    @Test
    void readMenusBySecondDish() {
        Set resultOnMenuOne = menuRepository.getMenusBySecondDish((secondDishOptionOne.getName()));
        Set resultOnMenuTwo = menuRepository.getMenusBySecondDish((secondDishOptionTwo.getName()));
        assertNotNull(resultOnMenuOne);
        assertNotNull(resultOnMenuTwo);
    }

    @Test
    void readDailyMenuOne() {
        Set result = calendarRepository.getCalendarByMenuId(menuOne.getId());
        assertNotNull(result);
    }

    @AfterAll
    static void deleteMenus() {
        firstDay.delete();
        secondDay.delete();
        menuOne.delete();
        menuTwo.delete();
        firstDish.delete();
        secondDishOptionOne.delete();
        secondDishOptionTwo.delete();
        sideDish.delete();
        sweetDish.delete();
        firstCategory.delete();
        secondCategory.delete();
        sideCategory.delete();
        sweetCategory.delete();
    }
}
