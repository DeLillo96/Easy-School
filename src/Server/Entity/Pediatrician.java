package Server.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Override
    protected void beforeSave() {
        super.beforeSave();

        Date birthDate = this.getBirthDate();
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        Date actualDate = today.getTime();
        long diff = actualDate.getTime() - birthDate.getTime();
        if((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)/(365.25))<28) throw new IllegalArgumentException();
    }
}
