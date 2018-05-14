package Server.Repository;

import Server.Entity.Calendar;
import Server.Entity.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CalendarRepository extends AbstractRepository {

    public CalendarRepository() {
        super("Calendar");
    }

    public Calendar getCalendarById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List days = read(params);
        return days != null && days.size() == 1 ? (Calendar) days.get(0) : null;
    }

    public Set<Calendar> getCalendarByMenuId(int menu_id) {
        MenuRepository menuRepository = new MenuRepository();
        Menu menus = menuRepository.getMenuById(menu_id);
        return menus.getDate();
    }
}
