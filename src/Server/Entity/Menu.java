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
    @JoinColumn(name = "first_dish")
    private Dish first;

    @ManyToOne
    @JoinColumn(name = "second_dish")
    private Dish second;

    @ManyToOne
    @JoinColumn(name = "side_dish")
    private Dish side;

    @ManyToOne
    @JoinColumn(name = "sweet")
    private Dish sweet;

    public Menu() { }

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
        if((first.getCategory().getName()==null)||(!(first.getCategory().getName().equals("First dish")))) throw new IllegalArgumentException();
        if((second.getCategory().getName()==null)||(!(second.getCategory().getName().equals("Second dish")))) throw new IllegalArgumentException();
        if((side.getCategory().getName()==null)||(!(side.getCategory().getName().equals("Side dish")))) throw new IllegalArgumentException();
        if((sweet.getCategory().getName()==null)||(!(sweet.getCategory().getName().equals("Sweet")))) throw new IllegalArgumentException();
    }
}