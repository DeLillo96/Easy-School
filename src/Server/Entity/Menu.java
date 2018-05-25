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

    @ManyToMany(mappedBy = "dailyMenus")
    private Set<Calendar> date = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "first")
    private First first;

    @ManyToOne
    @JoinColumn(name = "second")
    private Second second;

    @ManyToOne
    @JoinColumn(name = "side")
    private Side side;

    @ManyToOne
    @JoinColumn(name = "sweet")
    private Sweet sweet;

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

    public void setfirst(First first) {
        this.first = first;
    }

    public Dish getsecond() {
        return second;
    }

    public void setsecond(Second second) {
        this.second = second;
    }

    public Dish getside() {
        return side;
    }

    public void setside(Side side) {
        this.side = side;
    }

    public Dish getSweet() {
        return sweet;
    }

    public void setSweet(Sweet sweet) {
        this.sweet = sweet;
    }


}