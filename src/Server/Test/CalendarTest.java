package Server.Test;

import Server.Entity.Calendar;
import Server.Repository.CalendarRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CalendarTest {
    private static CalendarRepository calendarRepository = new CalendarRepository();
    private static Calendar calendar = new Calendar(new Date());

    @BeforeAll
    static void createCalendar() {
        calendar.save();
    }

    @AfterAll
    static void deleteCalendar() {
        calendar.delete();
    }

    @Test
    void getCalendarByDate() {
        Calendar readCalendar = calendarRepository.getCalendarByDate(calendar.getDate());
        String message = "Read error";
        assertEquals(calendar.getDate(), readCalendar.getDate(), message);
    }
}
