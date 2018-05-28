package Server.Entity;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ChildInVehicle")
public class ChildInVehicle extends AbstractEntity {

    @Id
    @GeneratedValue

    @PrimaryKeyJoinColumn
    private Integer id;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ChildInVehicles",
            joinColumns = {@JoinColumn(name = "childinvehicle_id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_id")}
    )
    private Set<Bus> vehicles = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child")
    private Child child = new Child();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip")
    private Trip trip = new Trip();

    public ChildInVehicle() {}

    public Set<Bus> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Bus> vehicles) {
        this.vehicles = vehicles;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
