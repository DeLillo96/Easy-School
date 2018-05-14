package Server.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDef(name = "id", parameters = {
        @ParamDef(name = "id", type = "integer")
})
@Filter(name = "id", condition = "id = :id")

@Table(name = "Menu")

public class Menu extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @OneToMany(mappedBy = "dailyMenu", fetch = FetchType.EAGER)
    private Set<Calendar> date = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "first")
    private Dish first;

    @ManyToOne
    @JoinColumn(name = "second")
    private Dish second;

    @ManyToOne
    @JoinColumn(name = "side")
    private Dish side;

    @ManyToOne
    @JoinColumn(name = "sweet")
    private Dish sweet;

    public Menu() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Calendar> getDate() {
        return date;
    }

    public void setDate(Set<Calendar> date) {
        this.date = date;
    }

    public Dish getfirst() {
        return first;
    }

    public void setfirst(Dish first) {
        this.first = first;
    }

    public Dish getsecond() {
        return second;
    }

    public void setsecond(Dish second) {
        this.second = second;
    }

    public Dish getside() {
        return side;
    }

    public void setside(Dish side) {
        this.side = side;
    }

    public Dish getSweet() {
        return sweet;
    }

    public void setSweet(Dish sweet) {
        this.sweet = sweet;
    }

    @Override
    protected void beforeSave() throws IllegalArgumentException {
        if (!validDish(first, "first") ||
                !validDish(second, "second") ||
                !validDish(side, "side") ||
                !validDish(sweet, "sweet")
                ) throw new IllegalArgumentException();
    }

    protected boolean validDish(Dish dish, String categoryName) {
        return dish.getCategory() == null || dish.getCategory().getName().equals(categoryName);
    }
}