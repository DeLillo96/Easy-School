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
    private Dish firstDish;

    @ManyToOne
    @JoinColumn(name = "second_dish")
    private Dish secondDish;

    @ManyToOne
    @JoinColumn(name = "side_dish")
    private Dish sideDish;

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

    public Dish getFirstDish() {
        return firstDish;
    }

    public void setFirstDish(Dish firstDish) {
        this.firstDish = firstDish;
    }

    public Dish getSecondDish() {
        return secondDish;
    }

    public void setSecondDish(Dish secondDish) {
        this.secondDish = secondDish;
    }

    public Dish getSideDish() {
        return sideDish;
    }

    public void setSideDish(Dish sideDish) {
        this.sideDish = sideDish;
    }

    public Dish getSweet() {
        return sweet;
    }

    public void setSweet(Dish sweet) {
        this.sweet = sweet;
    }

    @Override
    protected void beforeSave() throws IllegalArgumentException {
        if((firstDish.getCategory().getName()==null)||(!(firstDish.getCategory().getName().equals("First dish")))) throw new IllegalArgumentException();
        if((secondDish.getCategory().getName()==null)||(!(secondDish.getCategory().getName().equals("Second dish")))) throw new IllegalArgumentException();
        if((sideDish.getCategory().getName()==null)||(!(sideDish.getCategory().getName().equals("Side dish")))) throw new IllegalArgumentException();
        if((sweet.getCategory().getName()==null)||(!(sweet.getCategory().getName().equals("Sweet")))) throw new IllegalArgumentException();
    }
}