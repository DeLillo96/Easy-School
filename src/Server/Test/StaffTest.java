package Server.Test;

import Server.Entity.Staff;
import Server.Repository.StaffRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaffTest {
    private static StaffRepository staffRepository = new StaffRepository();
    private static Staff staff;
    @BeforeAll
    static void createStaff() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("1986-05-04");
            staff = new Staff("Sandor", "Clegane", "SNDCLG92H51A730S", calendarDay, "Butcher");
        }catch(Exception e) {}
        staff.save();
    }

    @AfterAll
    static void deleteStaff() {
        staff.delete();
    }

    @Test
    void readAdult() {
        Staff readAdult = staffRepository.getStaffByFiscalCode(staff.getFiscalCode());

        String message = "Read error";
        assertEquals(staff.getName(), readAdult.getName(), message);
        assertEquals(staff.getSurname(), readAdult.getSurname(), message);
        assertEquals(staff.getFiscalCode(), readAdult.getFiscalCode(), message);
    }

    @Test
    void addDoubleStaff() {
        Staff newStaff;

        newStaff = staffRepository.getStaffByFiscalCode(staff.getFiscalCode());
        newStaff.setId(0);

        Result result = newStaff.save();

        String message = "Read error";
        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());
        if (!result.isSuccess()) newStaff.delete();
    }

    @Test
    void verifyConstraint() {
        Staff newStaff = new Staff("Rory", "McCann", staff.getFiscalCode(), new Date(), "8273058105");
        Result result = new Result();
        try {
            result = newStaff.save();
        }catch (Exception e) {
            result.setSuccess(false);
        }
        assertFalse(result.isSuccess(), "Error: " + result.getMessages().toString());

        if (!result.isSuccess()) newStaff.delete();
    }

    @Test
    void modifyAdult() {
        staff.setName("Rory");
        staff.setSurname("McCannnnnn");
        Result result = staff.save();

        assertTrue(result.isSuccess(), "Error: " + result.getMessages().toString());
    }
}
