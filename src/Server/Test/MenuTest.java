package Server.Test;

import Server.Entity.*;
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
    private static Calendar firstDay = new Calendar(new Date());
    private static Calendar secondDay = new Calendar(new Date());
    private static First first = new First("Pidgeon Pie");
    private static Second secondOptionOne = new Second("Wolf's Left Leg");
    private static Second secondOptionTwo = new Second("Wolf's Right Leg");
    private static Side side = new Side("Wall's Potatoes");
    private static Sweet sweet = new Sweet("Revenge");
    private static Menu menuOne = new Menu();
    private static Menu menuTwo = new Menu();

    @BeforeAll
    static void createMenus() {
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
