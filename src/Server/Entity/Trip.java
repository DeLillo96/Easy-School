package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
        @FilterDef(name = "day", parameters = @ParamDef(name = "day", type = "integer")),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "day", condition = "day = :day"),
})
@Table(name = "Trip")
public class Trip extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "day")
    private Calendar day;

    @Column(nullable = false)
    private String code;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ChildInTrip",
            joinColumns = {@JoinColumn(name = "trip_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )
    public Set<Child> childInTrip = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "VisitedPlace",
            joinColumns = {@JoinColumn(name = "trip_id")},
            inverseJoinColumns = {@JoinColumn(name = "place_id")}
    )
    private Set<Place> places = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Vehicles",
            joinColumns = {@JoinColumn(name = "trip_id")},
            inverseJoinColumns = {@JoinColumn(name = "vehicles_id")}
    )
    private Set<Bus> vehicles = new HashSet<>();

    public Trip() {}

    public Trip(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return day;
    }

    public void setCalendar(Calendar day) {
        this.day = day;
    }

    public Set<Child> getChildInTrip() {
        return childInTrip;
    }

    public void setChildInTrip(Set<Child> childInTrip) {
        this.childInTrip = childInTrip;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public Set<Bus> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Bus> vehicles) {
        this.vehicles = vehicles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}