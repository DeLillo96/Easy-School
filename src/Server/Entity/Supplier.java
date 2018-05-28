package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = {@ParamDef(name = "id", type = "integer")}),
        @FilterDef(name = "name", parameters = {@ParamDef(name = "name", type = "string")}),
        @FilterDef(name = "iva", parameters = {@ParamDef(name = "iva", type = "string")})
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like '%' || :name || '%'"),
        @Filter(name = "iva", condition = "iva like '%' || :iva || '%'")
})

@Table(name = "Supplier")
public class Supplier extends AbstractEntity{

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 16)
    private String iva;

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

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Supply",
            joinColumns = {@JoinColumn(name = "supplier_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )



    private Set<Aliment> supply = new HashSet<>();

    public Set<Aliment> getSupply() {
        return supply;
    }

    public void setSupply(Set<Aliment> supply) {
        this.supply = supply;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctIVA = this.getIva();
        if(!validateString(correctIVA, "^[a-zA-Z0-9]*$")) throw new IllegalArgumentException("Violated constraints on iva code field (Only numbers and letters are allowed, no spaces)");
        if(this.getIva().length()!=16) throw new IllegalArgumentException("Violated constraints on fiscal code field (This field has to be filled with a sequence of 16 letters or numbers in any combination)");
        this.setIva(correctIVA.toUpperCase());
    }

}
