package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
})

@Table(name = "BusPresence")
public class BusPresence {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "child")
    private Child child;

    @ManyToOne
    @JoinColumn(name = "day")
    private Calendar day;

    @ManyToOne
    @JoinColumn(name = "bus")
    private Bus bus;

    public BusPresence() {
    }
}
