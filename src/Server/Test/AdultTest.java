package Server.Test;

import Server.Entity.Adult;
import Server.Entity.Users;
import Server.Repository.UsersRepository;
import Server.Result;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AdultTest {

    @Test
    public void adultRepositoryTest() {
    }

    @Test
    public void adultEntityTest() {
        Adult adult = new Adult("Ajeje","Brazzorf",null,new Date());
        Users user = (new UsersRepository()).getUserById(2);
        adult.setUser(user);
        Result pollo = adult.save();
        assertEquals(true, pollo.isSuccess(), "Errore di salvataggio");
    }
}
