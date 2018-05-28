package Server.Test;

import Server.Entity.Calendar;
import Server.Entity.Child;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PresentTest {
    private static Child child;
    private static Calendar calendar = new Calendar();

    @BeforeAll
    private static void initData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("2016-05-04");
            child = new Child("Daenerys", "Targaryen", "DNRTRG50J15S649F", calendarDay);
        }catch(Exception e) {}
        child.save();
        calendar.save();
    }

    @AfterAll
    private static void deleteData() {
        calendar.delete();
        child.delete();
    }

    @Test
    void signPresent() {
        calendar.getPresentChilds().add(child);
        Result result = calendar.save();

        assertTrue(result.isSuccess(), "Error during saving operation + " + result.getMessages().toString());
    }
}
