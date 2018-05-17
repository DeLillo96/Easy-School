package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "First")
@DiscriminatorValue("First")
public class First extends Dish {
    @OneToMany(mappedBy = "first", fetch = FetchType.EAGER)
    private Set<Menu> firstMenu = new HashSet<>();

    public First() {
    }

    public First(String name) {
        super(name);
    }

    public Set<Menu> getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(Set<Menu> firstMenu) {
        this.firstMenu = firstMenu;
    }

}
