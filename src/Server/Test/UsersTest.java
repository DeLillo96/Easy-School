package Server.Test;

import Server.Entity.Users;
import Server.Repository.UsersRepository;
import Server.Result;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {
    private static String password = "Stark";
    private static UsersRepository usersRepository = new UsersRepository();
    private static Users user = new Users("Sansa", password);

    @BeforeAll
    static void createUser() {
        user.save();
    }

    @Test void readUser() {
        Users readUser = usersRepository.getUserById(user.getId());
        String message = "errore di lettura";

        assertEquals(user.getUsername(), readUser.getUsername(), message);
        assertEquals(user.getPassword(), readUser.getPassword(), message);
        assertEquals(user.getEmail(), readUser.getEmail(), message);
    }

    @Test void loginUser(){
        String message = "Errore durante il login";

        assertFalse(usersRepository.login("wrong username", "wrong password"), message);
        assertFalse(usersRepository.login("wrong username", user.getPassword()), message);
        assertFalse(usersRepository.login(user.getUsername(), "wrong password"), message);
        assertTrue(usersRepository.login(user.getUsername(), password), message);
    }

    @Test void verifyConstraint() {
        Users impostore = new Users(user.getUsername(), "fake password");
        Result result = impostore.save();

        assertFalse(result.isSuccess(), "Le costraint sono state violate");
    }

    @Test void modifyUser() {
        user.setEmail("sophie.turner@grandeinverno.com");
        Result result = user.save();

        assertTrue(result.isSuccess(), "Errore di salvataggio + " + result.getMessages().toString());
    }

    @AfterAll
    static void deleteUser() {
        user.delete();
    }
}
