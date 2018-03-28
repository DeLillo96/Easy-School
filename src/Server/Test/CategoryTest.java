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
    private static Category first = new Category("First dish");
    private static Category second = new Category("Second dish");

    @BeforeAll
    static void createCategory() {
        first.save();
        second.save();
    }

    @Test
    void readCategory() {
        Category read = categoryRepository.getCategoryByName(first.getName());

        assertEquals(first.getId(), read.getId(), "Read Id Error");
        assertEquals(first.getName(), read.getName(), "Read Name Error");
    }

    @Test
    void verifyConstraint() {
        Category constraint = new Category(first.getName());
        Result result = constraint.save();

        assertFalse(result.isSuccess(), "Duplication Error" + result.getMessages().toString());

        if(!result.isSuccess()) constraint.delete();
    }

    @Test
    void modifyCategory() {
        first.setName("Side dish");
        Result result = first.save();

        assertTrue(result.isSuccess(), "Modify Error");
    }

    @AfterAll
    static void deleteCategory() {
        first.delete();
        second.delete();
    }

}
