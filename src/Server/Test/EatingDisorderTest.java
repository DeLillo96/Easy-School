package Server.Test;

import Server.Entity.Aliment;
import Server.Entity.Child;
import Server.Entity.EatingDisorder;
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
    private static EatingDisorder eatingDisorderOne = new EatingDisorder(childOne,alimentOne,"Allergy");
    private static EatingDisorder eatingDisorderTwo = new EatingDisorder(childOne,alimentTwo,"Allergy");
    private static EatingDisorder eatingDisorderThree = new EatingDisorder(childTwo,alimentOne,"Allergy");

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

    @Test
    void readEatingDisorderByAffectedChild() {
        Set result = eatingDisorderRepository.getEatingDisorderByAffectedChild(childOne.getFiscalCode());
        assertNotNull(result);
    }

    @Test
    void readEatingDisorderByAffectedAliment() {
        Set result = eatingDisorderRepository.getEatingDisorderByAffectedAliment(alimentOne.getName());
        assertNotNull(result);
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

}
