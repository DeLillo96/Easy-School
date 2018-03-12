package Server.Test;

import Server.Entity.Users;
import Server.Repository.UsersRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    @Test
    public void usersRepositoryTest() {
        UsersRepository usersRepository = new UsersRepository();

        assertNotEquals(null, usersRepository.read(), "la lettura deve essere una lista");
    }

    @Test
    public void usersEntityTest() {
        UsersRepository usersRepository = new UsersRepository();
        Users user = usersRepository.getUserByUsername("admin");

        assertNotEquals(null, user, "Errore di lettura su DB");
        assertEquals("admin", user.getUsername(), "username letto correttamente");
        assertEquals(true, usersRepository.login(user.getUsername(), user.getPassword()), "login non effettuato correttamente");
    }
}
