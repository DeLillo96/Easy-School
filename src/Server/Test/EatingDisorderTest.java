package Server.Test;

import Server.Entity.*;
import Server.Repository.AlimentRepository;
import Server.Repository.ChildRepository;
import Server.Repository.EatingDisorderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EatingDisorderTest {

    private static EatingDisorderRepository eatingDisorderRepository = new EatingDisorderRepository();
    private static ChildRepository childRepository = new ChildRepository();
    private static AlimentRepository alimentRepository = new AlimentRepository();
    private static Child childOne;
    private static Child childTwo;
    private static Aliment alimentOne = new Aliment("Flour");
    private static Aliment alimentTwo = new Aliment("Wolf");
    private static EatingDisorder eatingDisorderOne;
    private static EatingDisorder eatingDisorderTwo;
    private static EatingDisorder eatingDisorderThree;

    @BeforeAll
    static void createChild() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date calendarDay;
        try {
            calendarDay = simpleDateFormat.parse("2006-05-04");
            childOne = new Child("Jon", "Snow", "SNWJHN96T27V730G", calendarDay);
        }catch(Exception e) {}
        try {
            calendarDay = simpleDateFormat.parse("2006-05-04");
            childTwo = new Child("Arya", "Stark", "RYSTRK83F57K058V", calendarDay);
        }catch(Exception e) {}
        childOne.save();
        childTwo.save();
        alimentOne.save();
        alimentTwo.save();
        eatingDisorderOne = new Allergy(childOne, alimentOne);
        eatingDisorderTwo = new Allergy(childOne, alimentTwo);
        eatingDisorderThree = new Intolerance(childTwo, alimentOne);
        eatingDisorderOne.save();
        eatingDisorderTwo.save();
        eatingDisorderThree.save();
    }

    @AfterAll
    static void deleteChild() {
        eatingDisorderOne.delete();
        eatingDisorderTwo.delete();
        eatingDisorderThree.delete();
        alimentOne.delete();
        alimentTwo.delete();
        childOne.delete();
        childTwo.delete();
    }

    @Test
    void readEatingDisorderByAffectedChildString() {
        List result = eatingDisorderRepository.getEatingDisorderByAffectedChild(childOne.getFiscalCode());
        assertNotNull(result);
    }

    @Test
    void readEatingDisorderByAffectedChildInteger() {
        List result = eatingDisorderRepository.getEatingDisorderByAffectedChild(childTwo.getId());
        assertNotNull(result);
    }

    @Test
    void readEatingDisorderByAffectedAliment() {
        Set result = eatingDisorderRepository.getEatingDisorderByAffectedAliment(alimentOne.getName());
        assertNotNull(result);
    }

}
