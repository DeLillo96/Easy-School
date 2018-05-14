package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDefs({
        @FilterDef(name = "id", parameters = {@ParamDef(name = "id", type = "integer")}),
        @FilterDef(name = "name", parameters = {@ParamDef(name = "name", type = "string")}),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like :name"),
})

@Table(name = "Dish")
public class Dish extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category dishCategory;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Recipes",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "aliment_id")}
    )
    private Set<Aliment> ingredients = new HashSet<>();

    @OneToMany(mappedBy = "first", fetch = FetchType.EAGER)
    private Set<Menu> firstMenu = new HashSet<>();

    @OneToMany(mappedBy = "second", fetch = FetchType.EAGER)
    private Set<Menu> secondMenu = new HashSet<>();

    @OneToMany(mappedBy = "side", fetch = FetchType.EAGER)
    private Set<Menu> sideMenu = new HashSet<>();

    @OneToMany(mappedBy = "sweet", fetch = FetchType.EAGER)
    private Set<Menu> sweet = new HashSet<>();

    public Dish() {
    }

    public Dish(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return dishCategory;
    }

    public void setCategory(Category category) {
        this.dishCategory = category;
    }

    public Set<Aliment> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Aliment> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Menu> getFirstMenu() {
        return firstMenu;
    }

    public void setFirstMenu(Set<Menu> firstMenu) {
        this.firstMenu = firstMenu;
    }

    public Set<Menu> getSecondMenu() {
        return secondMenu;
    }

    public void setSecondMenu(Set<Menu> secondMenu) {
        this.secondMenu = secondMenu;
    }

    public Set<Menu> getSideMenu() {
        return sideMenu;
    }

    public void setSideMenu(Set<Menu> sideMenu) {
        this.sideMenu = sideMenu;
    }

    public Set<Menu> getSweet() {
        return sweet;
    }

    public void setSweet(Set<Menu> sweet) {
        this.sweet = sweet;
    }
}
