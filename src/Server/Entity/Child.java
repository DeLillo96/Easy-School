package Server.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "Child")
public class Child extends Person {

    @OneToMany(mappedBy = "affectedChild", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<EatingDisorder> eatingDisorders = new HashSet<>();

    @ManyToMany(mappedBy = "children", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Adult> parents = new HashSet<>();

    @ManyToMany(mappedBy = "follow", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Pediatrician> pediatrician = new HashSet<>();

    @ManyToMany(mappedBy = "presentChildren", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Calendar> presences = new HashSet<>();

    @OneToMany(mappedBy = "child", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<BusPresence> busPresences = new HashSet<>();

    public Child() {
    }

    public Child(String name, String surname, Date birthDate) {
        this(name, surname, null, birthDate);
    }

    public Child(String name, String surname, String fiscalCode, Date birthDate) {
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        setBirthDate(birthDate);
    }

    public Set<Adult> getParents() {
        return parents;
    }

    public void setParents(Set<Adult> parents) {
        this.parents = parents;
    }

    public Set<Calendar> getPresences() {
        return presences;
    }

    public void setPresences(Set<Calendar> presences) {
        this.presences = presences;
    }

    public Set<EatingDisorder> getEatingDisorders() {
        return eatingDisorders;
    }

    public void setEatingDisorders(Set<EatingDisorder> eatingDisorders) {
        this.eatingDisorders = eatingDisorders;
    }

    public Set<BusPresence> getBusPresences() {
        return busPresences;
    }

    public void setBusPresences(Set<BusPresence> busPresences) {
        this.busPresences = busPresences;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        Date birthDate = this.getBirthDate();
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        Date actualDate = today.getTime();
        long diff = actualDate.getTime() - birthDate.getTime();
        if((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)<42)||((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)/(365.25))>16)) throw new IllegalArgumentException();
    }
}
