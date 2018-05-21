package Server.Remote;

import Server.Entity.*;
import Server.Repository.*;
import Server.Result;
import Shared.AssignService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DailyTripServiceImplementation extends UnicastRemoteObject implements AssignService {
    protected DayTripRepository dayTripRepository;
    protected CalendarRepository calendarRepository;

    public DailyTripServiceImplementation() throws RemoteException {
        dayTripRepository = new DayTripRepository();
        calendarRepository = new CalendarRepository();
    }

    @Override
    public JSONObject assign(Integer calendarId, Integer tripId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);
        DayTrip dayTrip = dayTripRepository.getDayTripById(tripId);

        for (DayTrip dailyTrip : calendar.getDailyTrips()) {
            if (dailyTrip.getId().equals(tripId)) {
                Result result = new Result();
                result.addData(calendar);
                return result.toJson();
            }
        }

        calendar.getDailyTrips().add(dayTrip);
        return calendar.save().toJson();
    }

    @Override
    public JSONObject deAssign(Integer calendarId, Integer tripId) throws Exception {
        Calendar calendar = calendarRepository.getCalendarById(calendarId);

        for (DayTrip dailyTrip : calendar.getDailyTrips()) {
            if (dailyTrip.getId().equals(tripId)) {
                calendar.getDailyTrips().remove(dailyTrip);
                return calendar.save().toJson();
            }
        }

        Result result = new Result();
        result.addData(calendar);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer tripId) throws Exception {
        List list = calendarRepository.getCalendarByTripId(tripId);
        return new Result(true, list).toJson();
    }

    @Override
    public JSONObject rightRead(Integer calendarId) throws Exception {
        List list = dayTripRepository.getDailyTripByCalendar(calendarId);
        return new Result(true, list).toJson();
    }
}
