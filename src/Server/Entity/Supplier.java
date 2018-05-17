package Server.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Supplier")
public class Supplier extends Person {
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

    public String getIVA() {
        return getFiscalCode();
    }

    public void setIVA(String iva) {
        setFiscalCode(iva);
    }
}
