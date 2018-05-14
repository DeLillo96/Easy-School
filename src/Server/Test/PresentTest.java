package Server.Test;

import Server.Entity.Calendar;
import Server.Entity.Child;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PresentTest {
    private static Child child = new Child("Daenerys", "Targaryen", "DNRTRG50J15S649F", new Date());
    private static Calendar calendar = new Calendar();

    @BeforeAll
    private static void initData() {
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
