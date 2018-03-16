package Server.Test;

import Server.Entity.Child;
import Server.Repository.ChildRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChildTest {
    private static String nome = "John";
    private static String cognome = "Snow";
    private static String codiceFiscale = "SNWJHN96T27V730G";
    private static Date nascita = new Date();
    private static String contatti = "Corvo verso Grande Inverno";
    private static ChildRepository childRepository = new ChildRepository();
    private static Child child;

    @BeforeAll
    static void createChild() {
        child = new Child(nome, cognome, codiceFiscale, nascita, contatti);
        child.save();
    }

    @Test void readChild() {
        Child readChild = childRepository.getChildByFiscalCode(codiceFiscale);
        String message = "errore di lettura";

        assertEquals(child.getNome(), readChild.getNome(), message);
        assertEquals(child.getCognome(), readChild.getCognome(), message);
        assertEquals(child.getNascita(), readChild.getNascita(), message);
        assertEquals(child.getContatti(), readChild.getContatti(), message);
    }

    @Test void verifyConstraint() {
        Child impostore = new Child(nome, cognome, codiceFiscale, nascita, contatti);
        Result result = impostore.save();

        assertFalse(result.isSuccess(), "Le costraint sono state violate");
    }

    @Test void modifyChild() {
        child.setCognome("Targarien");
        Result result = child.save();

        assertTrue(result.isSuccess(), "Errore di salvataggio + " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteChild() {
        child.delete();
    }
}
