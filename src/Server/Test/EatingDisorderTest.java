package Server.Test;

import Server.Entity.*;
import Server.Repository.AlimentRepository;
import Server.Repository.ChildRepository;
import Server.Repository.EatingDisorderRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EatingDisorderTest {

    private static EatingDisorderRepository eatingDisorderRepository = new EatingDisorderRepository();
    private static ChildRepository childRepository = new ChildRepository();
    private static AlimentRepository alimentRepository = new AlimentRepository();
    private static Child childOne = new Child("Jon", "Snow", "SNWJHN96T27V730G", new Date());
    private static Child childTwo = new Child("Arya", "Stark", "RYSTRK83F57K058V", new Date());
    private static Aliment alimentOne = new Aliment("Flour");
    private static Aliment alimentTwo = new Aliment("Wolf");
    private static EatingDisorder eatingDisorderOne = new Allergy(childOne, alimentOne);
    private static EatingDisorder eatingDisorderTwo = new Allergy(childOne, alimentTwo);
    private static EatingDisorder eatingDisorderThree = new Intolerance(childTwo, alimentOne);

    @BeforeAll
    static void createEatingDisorder() {
        childOne.save();
        childTwo.save();
        alimentOne.save();
        alimentTwo.save();
        eatingDisorderOne.save();
        eatingDisorderTwo.save();
        eatingDisorderThree.save();
    }

    @AfterAll
    static void deleteEatingDisorder() {
        eatingDisorderOne.delete();
        eatingDisorderTwo.delete();
        eatingDisorderThree.delete();
        childOne.delete();
        childTwo.delete();
        alimentOne.delete();
        alimentTwo.delete();
    }

    @Test
    void readEatingDisorderByAffectedChild() {
        List result = eatingDisorderRepository.getEatingDisorderByAffectedChild(childOne.getFiscalCode());
        assertNotNull(result);
    }

    @Test
    void readEatingDisorderByAffectedAliment() {
        Set result = eatingDisorderRepository.getEatingDisorderByAffectedAliment(alimentOne.getName());
        assertNotNull(result);
    }

}
