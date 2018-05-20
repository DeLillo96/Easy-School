package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Sweet")
@DiscriminatorValue("Sweet")
public class Sweet extends Dish {
    @OneToMany(mappedBy = "sweet", fetch = FetchType.EAGER)
    private Set<Menu> sweetMenu = new HashSet<>();

    public Sweet(String name) {
        super(name);
    }

    public Sweet() {
    }

    public Set<Menu> getSweetMenu() {
        return sweetMenu;
    }

    public void setSweetMenu(Set<Menu> sweetMenu) {
        this.sweetMenu = sweetMenu;
    }
}
