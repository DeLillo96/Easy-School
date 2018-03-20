package Server.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.jboss.logging.Param;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDef(name = "id", parameters = {
        @ParamDef(name = "id", type = "integer")
})
@FilterDef(name = "name", parameters = {
        @ParamDef(name = "name", type = "string")
})
@FilterDef(name = "category", parameters = {
        @ParamDef(name = "category", type = "string")
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like :name"),
        @Filter(name = "category", condition = "category like :category"),
})

@Table(name = "Dish")
public class Dish extends AbstractEntity{

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false, length = 16)
    private String category;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch=FetchType.EAGER)
    @JoinTable(
            name = "Recipes",
            joinColumns = { @JoinColumn(name = "dish_id") },
            inverseJoinColumns = { @JoinColumn(name = "aliment_id") }
    )
    private Set<Alimento> ingredients = new HashSet<>();

    public Dish() { }

    public Dish(String name, String category) {
        this.name = name;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Alimento> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Alimento> ingredients) {
        this.ingredients = ingredients;
    }
}
