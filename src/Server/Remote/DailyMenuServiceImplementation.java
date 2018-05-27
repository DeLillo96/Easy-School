package Server.Remote;

import Server.Entity.Calendar;
import Server.Entity.Menu;
import Server.Repository.CalendarRepository;
import Server.Repository.MenuRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DailyMenuServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected MenuRepository menuRepository;
    protected CalendarRepository calendarRepository;

    public DailyMenuServiceImplementation() throws RemoteException {
        menuRepository = new MenuRepository();
        calendarRepository = new CalendarRepository();
    }

    private List<String> checkEatingDisorders(Integer calendarId) {
        List<String> list = new ArrayList<>();



        return list;
    }

    @Override
    public JSONObject assign(Integer calendarId, Integer menuId) throws Exception {
        Menu menu = menuRepository.getMenuById(menuId);

        for (Calendar calendar : menu.getDate()) {
            if (calendar.getId().equals(calendarId)) {
                Result result = new Result();
                result.addData(menu);
                return result.toJson();
            }
        }

        menu.getDate().add(calendarRepository.getCalendarById(calendarId));
        return menu.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
        Result response = new Result();
        for (Integer leftId : leftIds) {
            JSONObject result = assign(rightId, leftId);
            if(!(Boolean) result.get("success")) {
                response.setSuccess(false);
                response.addMessage(result.get("messages").toString());
            }
        }
        response.addMessages(checkEatingDisorders(rightId));
        return response.toJson();
    }

    @Override
    public JSONObject assign(List rightIds, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject deAssign(Integer calendarId, Integer menuId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);

        for (Menu dailyMenu : calendar.getDailyMenus()) {
            if (dailyMenu.getId().equals(menuId)) {
                calendar.getDailyMenus().remove(dailyMenu);
                return calendar.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(calendar);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer menuId) throws Exception {
        List list = calendarRepository.getCalendarByMenuId(menuId);
        return new Result(true, list).toJson();
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer calendarId) throws Exception {
        List list = menuRepository.getMenuByCalendar(calendarId);
        return new Result(true, list).toJson();
    }
}
