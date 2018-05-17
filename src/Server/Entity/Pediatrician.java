package Server.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Pediatrician")
public class Pediatrician extends Person {
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Follow",
            joinColumns = {@JoinColumn(name = "pediatrician_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )
    private Set<Child> follow = new HashSet<>();

    public Pediatrician() {
    }

    public Pediatrician(String name, String surname, String fiscalCode, Date birthDate, String telephone) {
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        setBirthDate(birthDate);
    }

    public Set<Child> getChildren() {
        return follow;
    }

    public void setChildren(Set<Child> children) {
        this.follow = children;
    }
}
