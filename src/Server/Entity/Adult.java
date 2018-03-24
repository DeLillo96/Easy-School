package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDef(name = "id", parameters = {
        @ParamDef(name = "id", type = "integer" )
})
@FilterDef(name = "name", parameters = {
        @ParamDef(name = "name", type = "string")
})
@FilterDef(name = "surname", parameters = {
        @ParamDef(name = "surname", type = "string")
})
@FilterDef(name = "fiscalCode", parameters = {
        @ParamDef(name = "fiscalCode", type = "string")
})
@FilterDef(name = "birthDate", parameters = {
        @ParamDef(name = "birthDate", type = "date")
})
@FilterDef(name = "user_id", parameters = {
        @ParamDef(name = "user_id", type = "integer")
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like :name"),
        @Filter(name = "surname", condition = "surname like :surname"),
        @Filter(name = "fiscalCode", condition = "fiscalCode like :fiscalCode"),
        @Filter(name = "birthDate", condition = "birthDate = :birthDate"),
        @Filter(name = "user_id", condition = "user_id = :user_id"),
})

@Table(name = "Adult")
public class Adult extends AbstractEntity {
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

    @OneToOne
    @JoinColumn(unique = true, name = "user_id")
    private Users user;

    @ManyToMany(mappedBy = "parents")
    private Set<Child> children = new HashSet<>();

    public Adult() { }

    public Adult(String name, String surname, String fiscalCode, Date birthDate) {
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public void addChildren(Child child) {
        children.add(child);
    }

    public void removeChild(Child child) {
        if(children.contains(child)) children.remove(child);
    }
}