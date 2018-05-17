package Server.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Second")
@DiscriminatorValue("Second")
public class Second extends Dish {
    @OneToMany(mappedBy = "second", fetch = FetchType.EAGER)
    private Set<Menu> secondMenu = new HashSet<>();

    public Second(String name) {
        super(name);
    }

    public Second() {
    }

    public Set<Menu> getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(Set<Menu> secondMenu) {
        this.secondMenu = secondMenu;
    }
}
