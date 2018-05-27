package Server.Remote;

import Server.Entity.Calendar;
import Server.Entity.Menu;
import Server.Repository.CalendarRepository;
import Server.Repository.MenuRepository;
import Server.Result;
import Shared.DailyMenuRelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DailyMenuServiceImplementation extends UnicastRemoteObject implements DailyMenuRelationService {
    protected MenuRepository menuRepository;
    protected CalendarRepository calendarRepository;

    public DailyMenuServiceImplementation() throws RemoteException {
        menuRepository = new MenuRepository();
        calendarRepository = new CalendarRepository();
    }

    private List checkEatingDisorders(Integer calendarId) {
        return null;
    }

    @Override
    public JSONObject assign(Integer calendarId, Integer menuId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);
        Menu menu = menuRepository.getMenuById(menuId);

        for (Menu dailyMenu : calendar.getDailyMenus()) {
            if (dailyMenu.getId().equals(menuId)) {
                Result result = new Result();
                result.addData(calendar);
                return result.toJson();
            }
        }

        calendar.getDailyMenus().add(menu);
        List<String> affectedAliments = checkEatingDisorders(calendarId);
        return calendar.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List leftIds) throws Exception {
        return null;
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

    @Override
    public JSONObject saveAll(Integer calendarId, List<Integer> selectedMenuId) {
        Result returnObject = new Result();
        for (Integer menuId:selectedMenuId) {
            try {
                assign(calendarId, menuId);
            } catch (Exception e) {
                returnObject.setSuccess(false);
            }
        }
        returnObject.setSuccess(true);
        return returnObject.toJson();
    }
}
