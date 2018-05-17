package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Intolerance")
@DiscriminatorValue("intolerance")
public class Intolerance extends EatingDisorder {
    public Intolerance(Child affectedChild, Aliment affectedAliment) {
        super(affectedChild, affectedAliment);
    }

    public Intolerance() {
    }
}
