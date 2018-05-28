package Server.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "First")
@DiscriminatorValue("First")
public class First extends Dish {
    public First() {
    }

    public First(String name) {
        super(name);
    }
}
