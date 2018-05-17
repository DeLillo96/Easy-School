package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Allergy")
@DiscriminatorValue("allergy")
public class Allergy extends EatingDisorder {
    public Allergy(Child affectedChild, Aliment affectedAliment) {
        super(affectedChild, affectedAliment);
    }

    public Allergy() {
    }
}
