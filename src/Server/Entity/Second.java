package Server.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Second")
@DiscriminatorValue("Second")
public class Second extends Dish {

    public Second(String name) {
        super(name);
    }

    public Second() {
    }

}
