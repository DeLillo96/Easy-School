package Server.Test;

import Server.Entity.Category;
import Server.Repository.CategoryRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryTest {

    private static CategoryRepository categoryRepository = new CategoryRepository();
    private static Category first = new Category("Primo");
    private static Category second = new Category("Secondo");

    @BeforeAll
    static void createCategory() {
        first.save();
        second.save();
    }

    @Test
    void readCategory() {
        Category read = categoryRepository.getCategoryByName(first.getName());

        assertEquals(first.getId(), read.getId(), "Errore nella lettura dell'id");
        assertEquals(first.getName(), read.getName(), "Errore nella lettura del nome");
    }

    @Test
    void verifyConstraint() {
        Category constraint = new Category(first.getName());
        Result result = constraint.save();

        assertFalse(result.isSuccess(), "Errore di duplicazione");

        if(!result.isSuccess()) constraint.delete();
    }

    @Test
    void modifyCategory() {
        first.setName("Contorno");
        Result result = first.save();

        assertTrue(result.isSuccess(), "Errore nella modifica");
    }

    @AfterAll
    static void deleteCategory() {
        first.delete();
        second.delete();
    }

}
