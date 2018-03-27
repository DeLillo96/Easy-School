package Server.Repository;

import Server.Entity.Calendar;
import Server.Entity.Menu;

import java.util.Set;

public class CalendarRepository extends AbstractRepository {

    public CalendarRepository() {super("Calendar");}

    public Set<Calendar> getCalendarByMenuId(int menu_id) {
        MenuRepository menuRepository = new MenuRepository();
        Menu menus = menuRepository.getMenuById(menu_id);
        return menus.getDate();
    }
}
