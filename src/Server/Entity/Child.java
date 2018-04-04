package Server.Entity;

import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer") ),
        @FilterDef(name = "name", parameters = @ParamDef(name = "name", type = "string") ),
        @FilterDef(name = "surname", parameters = @ParamDef(name = "surname", type = "string") ),
        @FilterDef(name = "fiscalCode", parameters = @ParamDef(name = "fiscalCode", type = "string") ),
        @FilterDef(name = "birthDate", parameters = @ParamDef(name = "birthDate", type = "date") ),
        @FilterDef(name = "contacts", parameters = @ParamDef(name = "contacts", type = "string") )
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like :name"),
        @Filter(name = "surname", condition = "surname like :surname"),
        @Filter(name = "fiscalCode", condition = "fiscalCode like :fiscalCode"),
        @Filter(name = "birthDate", condition = "birthDate = :birthDate"),
        @Filter(name = "contacts", condition = "contacts = :contacts"),
})
@Table(name = "Child")
public class Child extends AbstractEntity{
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 32)
    private String surname;

    @Column(unique = true, length = 16)
    private String fiscalCode;

    @Column(nullable = false)
    private Date birthDate;

    @OneToMany(mappedBy = "affectedChild", fetch = FetchType.EAGER)
    private Set<EatingDisorder> eatingDisorders = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch=FetchType.EAGER)
    @JoinTable(
            name = "Parent",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "adult_id") }
    )
    private Set<Adult> parents = new HashSet<>();

    @ManyToMany(mappedBy = "presentChildren")
    private Set<Calendar> presences = new HashSet<>();

    public Child() { }

    public Child(String name, String surname, Date birthDate) {
        this(name, surname, null, birthDate);
    }

    public Child(String name, String surname, String fiscalCode, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.birthDate = birthDate;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
}
