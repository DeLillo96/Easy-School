package Server.Repository;

import Server.Entity.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    public Calendar getCalendarByDate(Date date) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("date", date);
        List calendar = read(params);

        return calendar != null && calendar.size() == 1 ? (Calendar) calendar.get(0) : null;
    }

}
