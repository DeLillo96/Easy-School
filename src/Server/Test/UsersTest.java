package Server.Test;

import Server.Entity.Users;
import Server.Repository.UsersRepository;
import Server.Result;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {
    private static String password = "Stark";
    private static UsersRepository usersRepository = new UsersRepository();
    private static Users user = new Users("Sansa", password);

    @BeforeAll
    static void createUser() {
        user.save();
    }

    @AfterAll
    static void deleteUser() {
        user.delete();
    }

    @Test
    void readUser() {
        Users readUser = usersRepository.getUserById(user.getId());
        String message = "Error during reading operation";

        assertEquals(user.getUsername(), readUser.getUsername(), message);
        assertEquals(user.getPassword(), readUser.getPassword(), message);
        assertEquals(user.getEmail(), readUser.getEmail(), message);
    }

    @Test
    void loginUser() {
        String message = "Login error";

        assertFalse(usersRepository.login("wrong username", "wrong password"), message);
        assertFalse(usersRepository.login("wrong username", password), message);
        assertFalse(usersRepository.login(user.getUsername(), "wrong password"), message);
        assertTrue(usersRepository.login(user.getUsername(), password), message);
    }

    @Test
    void verifyConstraint() {
        Users newUser = new Users(user.getUsername(), "fake password");
        Result result = newUser.save();

        assertFalse(result.isSuccess(), "Violated constraints");
        if (!result.isSuccess()) newUser.delete();
    }

    @Test
    void modifyUser() {
        user.setEmail("sophie.turner@winterfell.com");
        Result result = user.save();

        assertTrue(result.isSuccess(), "Error during saving operation + " + result.getMessages().toString());
    }
}
