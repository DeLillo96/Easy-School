package Server.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Sweet")
@DiscriminatorValue("Sweet")
public class Sweet extends Dish {

    public Sweet(String name) {
        super(name);
    }

    public Sweet() {
    }
}
