package Server.Test;

import Server.Entity.Calendar;
import Server.Entity.Category;
import Server.Entity.Dish;
import Server.Entity.Menu;
import Server.Repository.CalendarRepository;
import Server.Repository.MenuRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MenuTest {

    private static MenuRepository menuRepository = new MenuRepository();
    private static CalendarRepository calendarRepository = new CalendarRepository();
    private static Category firstCategory = new Category("first");
    private static Category secondCategory = new Category("second");
    private static Category sideCategory = new Category("side");
    private static Category sweetCategory = new Category("sweet");
    private static Calendar firstDay = new Calendar(new Date());
    private static Calendar secondDay = new Calendar(new Date());
    private static Dish first = new Dish("Pidgeon Pie");
    private static Dish secondOptionOne = new Dish("Wolf's Left Leg");
    private static Dish secondOptionTwo = new Dish("Wolf's Right Leg");
    private static Dish side = new Dish("Wall's Potatoes");
    private static Dish sweet = new Dish("Revenge");
    private static Menu menuOne = new Menu();
    private static Menu menuTwo = new Menu();

    @BeforeAll
    static void createMenus() {
        firstCategory.save();
        secondCategory.save();
        sideCategory.save();
        sweetCategory.save();
        first.setCategory(firstCategory);
        secondOptionOne.setCategory(secondCategory);
        secondOptionTwo.setCategory(secondCategory);
        side.setCategory(sideCategory);
        sweet.setCategory(sweetCategory);
        first.save();
        secondOptionOne.save();
        secondOptionTwo.save();
        side.save();
        sweet.save();
        menuOne.setfirst(first);
        menuOne.setsecond(secondOptionOne);
        menuOne.setside(side);
        menuOne.setSweet(sweet);
        menuTwo.setfirst(first);
        menuTwo.setsecond(secondOptionTwo);
        menuTwo.setside(side);
        menuTwo.setSweet(sweet);
        menuOne.save();
        menuTwo.save();
        firstDay.setDailyMenu(menuOne);
        secondDay.setDailyMenu(menuTwo);
        firstDay.save();
        secondDay.save();
    }

    @AfterAll
    static void deleteMenus() {
        firstDay.delete();
        secondDay.delete();
        menuOne.delete();
        menuTwo.delete();
        first.delete();
        secondOptionOne.delete();
        secondOptionTwo.delete();
        side.delete();
        sweet.delete();
        firstCategory.delete();
        secondCategory.delete();
        sideCategory.delete();
        sweetCategory.delete();
    }

    @Test
    void readMenusByFirst() {
        Set result = menuRepository.getMenusByfirst(first.getName());
        assertNotNull(result);
    }

    @Test
    void readMenusBySecond() {
        Set resultOnMenuOne = menuRepository.getMenusBysecond((secondOptionOne.getName()));
        Set resultOnMenuTwo = menuRepository.getMenusBysecond((secondOptionTwo.getName()));
        assertNotNull(resultOnMenuOne);
        assertNotNull(resultOnMenuTwo);
    }

    @Test
    void readDailyMenuOne() {
        Set result = calendarRepository.getCalendarByMenuId(menuOne.getId());
        assertNotNull(result);
    }
}
