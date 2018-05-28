package Server.Remote;

import Server.Entity.Calendar;
import Server.Entity.Dish;
import Server.Repository.CalendarRepository;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DailyDishServiceImplementation extends UnicastRemoteObject implements RelationService {
    protected DishRepository dishRepository;
    protected CalendarRepository calendarRepository;

    public DailyDishServiceImplementation() throws RemoteException {
        dishRepository = new DishRepository();
        calendarRepository = new CalendarRepository();
    }

    private List<String> checkEatingDisorders(Integer calendarId) {
        List<String> list = new ArrayList<>();
        List eatingDisorder = dishRepository.getListOfEatingDisorderByDay(calendarId);
        for (int i = 0; i < eatingDisorder.size(); i++) {
            Object[] strings = (Object []) eatingDisorder.get(i);
            list.add(strings[1] + " - " + strings[0]);
        }
        return list;
    }

    @Override
    public JSONObject assign(Integer calendarId, Integer dishId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);

        for (Dish dish : calendar.getDishes()) {
            if (dish.getId().equals(dishId)) {
                Result result = new Result();
                result.addData(calendar);
                return result.toJson();
            }
        }

        calendar.getDishes().add(dishRepository.getDishById(dishId));
        return calendar.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List<Integer> leftIds) throws Exception {
        Result response = new Result();
        Calendar calendar = calendarRepository.getCalendarById(rightId);
        calendar.getDishes().clear();
        calendar.save();
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
    public JSONObject deAssign(Integer calendarId, Integer dishId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);

        for (Dish dailyDish : calendar.getDishes()) {
            if (dailyDish.getId().equals(dishId)) {
                calendar.getDishes().remove(dailyDish);
                return calendar.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(calendar);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer dishId) throws Exception {
        return null;
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
        List list = dishRepository.getAllDailyDish(calendarId);
        return new Result(checkEatingDisorders(calendarId), true, list).toJson();
    }
}
