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
        Users user = new Users("ale", "pela","wkpe@woef.it");
        user.save();
        Users user2 = usersRepository.getUserById(user.getId());

        String message = "errore dei filtri";
        assertEquals(user.getId(), user2.getId(), message);
        assertEquals(user.getId(), user2.getId(), message);
        assertEquals(user.getId(), user2.getId(), message);
        assertEquals(user.getId(), user2.getId(), message);

        assertTrue(usersRepository.login(user.getUsername(),user.getPassword()), "Errore durante il login");
        user.delete();
    }
}
