package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
        @FilterDef(name = "name", parameters = @ParamDef(name = "name", type = "string"))
})

@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like '%' || :name || '%'"),
})

@Table(name = "DayTrip")

public class DayTrip extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "dailyTrips")
    private Set<Calendar> date = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "VisitedPlace",
            joinColumns = {@JoinColumn(name = "trip_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_id")}
    )
    private Set<Place> places = new HashSet<>();

    public DayTrip() {
    }

    public DayTrip(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Calendar> getDate() {
        return date;
    }

    public void setDate(Set<Calendar> date) {
        this.date = date;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctName = this.getName();
        this.setName(nameCorrector(correctName));
    }
}
