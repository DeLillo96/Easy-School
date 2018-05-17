package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Side")
@DiscriminatorValue("Side")
public class Side extends Dish {
    @OneToMany(mappedBy = "side", fetch = FetchType.EAGER)
    private Set<Menu> sideMenu = new HashSet<>();

    public Side(String name) {
        super(name);
    }

    public Side() {
    }

    public Set<Menu> getSideMenu() {
        return sideMenu;
    }

    public void setSideMenu(Set<Menu> sideMenu) {
        this.sideMenu = sideMenu;
    }
}
