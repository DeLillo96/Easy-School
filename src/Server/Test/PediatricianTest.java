package Server.Test;

import Server.Entity.Child;
import Server.Entity.Pediatrician;
import Server.Repository.AdultRepository;
import Server.Repository.PediatricianRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PediatricianTest {
    private static Pediatrician doctor;
    private static Child child;
    private PediatricianRepository pediatricianRepository = new PediatricianRepository();

    @BeforeAll
    static void createPediatrician() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("2006-05-04");
            child = new Child("Jon", "Snow", "SNWJHN96T27V730G", calendarDay);
        }catch(Exception e) {}
        child.save();
        try {
            calendarDay = simpleDateFormat.parse("1955-05-04");
            doctor = new Pediatrician("Catelyn", "Tully", "CRLTLL93D65L153G", calendarDay, "7263017233");
        }catch(Exception e) {}
        doctor.getChildren().add(child);
        doctor.save();
    }

    @AfterAll
    static void deletePediatrician() {
        doctor.delete();
        child.delete();
    }

    @Test
    void readChildWithPediatrician() {
        Pediatrician readPediatrician = pediatricianRepository.getPediatricianByFiscalCode(doctor.getFiscalCode());

        assertNotNull(readPediatrician, "read child error");
        assertTrue(readPediatrician.getChildren().size() >= 1, "Failed join");
    }

    @Test
    void addChild() {
        assertTrue(child.getPediatrician().add(doctor), "Failed add pediatrician operation");

        Result result = child.save();
        assertTrue(result.isSuccess(), "Error during saving operation " + result.getMessages().toString());
    }
}
