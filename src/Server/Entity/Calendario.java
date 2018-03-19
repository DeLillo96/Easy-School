package Server.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Calendario")
public class Calendario extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true)
    private Date date;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch=FetchType.EAGER)
    @JoinTable(
            name = "Present",
            joinColumns = { @JoinColumn(name = "calendario_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    private Set<Child> presentChildren = new HashSet<>();

    public Calendario() {
        this.date = new Date();
    }

    public Calendario(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Child> getPresentChilds() {
        return presentChildren;
    }

    public void setPresentChilds(Set<Child> presentChilds) {
        this.presentChildren = presentChilds;
    }
}
