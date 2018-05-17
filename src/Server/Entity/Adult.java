package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDefs({
        @FilterDef(name = "telephone", parameters = {@ParamDef(name = "telephone", type = "string")}),
        @FilterDef(name = "user_id", parameters = {@ParamDef(name = "user_id", type = "integer")})
})
@Filters({
        @Filter(name = "telephone", condition = "telephone like '%' || :telephone || '%'"),
        @Filter(name = "user_id", condition = "user_id = :user_id"),
})

@Table(name = "Adult")
public class Adult extends Person {
    @OneToOne
    @JoinColumn(unique = true, name = "user_id")
    private Users user;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Parent",
            joinColumns = {@JoinColumn(name = "adult_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )
    private Set<Child> children = new HashSet<>();

    @Column(nullable = false, length = 10)
    private String telephone;

    public Adult() {
    }

    public Adult(String name, String surname, String fiscalCode, Date birthDate, String telephone) {
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        setBirthDate(birthDate);
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
        if (children.contains(child)) children.remove(child);
    }
}
