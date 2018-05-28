package Server.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Side")
@DiscriminatorValue("Side")
public class Side extends Dish {

    public Side(String name) {
        super(name);
    }

    public Side() {
    }

}
