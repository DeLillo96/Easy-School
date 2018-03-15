package Server.Test;

import Server.Entity.Users;
import Server.Repository.UsersRepository;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {
    private static String username = "Sansa";
    private static String password = "Stark";
    private static UsersRepository usersRepository = new UsersRepository();
    private static Users user;

    @BeforeAll
    static void createUser() {
        user = new Users(username, password);
        user.save();
    }

    @Test void readUser() {
        Users readUser = usersRepository.getUserById(user.getId());
        String message = "errore di lettura";

        assertEquals(user.getUsername(), readUser.getUsername(), message);
        assertEquals(user.getPassword(), readUser.getPassword(), message);
        assertNull(readUser.getEmail(), message);
    }

    @Test void loginUser(){
        String message = "Errore durante il login";

        assertFalse(usersRepository.login("wrong username", "wrong password"), message);
        assertFalse(usersRepository.login("wrong username", password), message);
        assertFalse(usersRepository.login(username, "wrong password"), message);
        assertTrue(usersRepository.login(username, password), message);
    }

    @AfterAll
    static void deleteUser() {
        user.delete();
    }
}
