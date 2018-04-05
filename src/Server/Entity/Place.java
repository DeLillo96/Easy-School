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
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer") ),
        @FilterDef(name = "name", parameters = @ParamDef(name = "name", type = "string") ),
        @FilterDef(name = "address", parameters = @ParamDef(name = "address", type = "string") ),
        @FilterDef(name = "cost", parameters = @ParamDef(name = "cost", type = "integer") )
})

@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like :name"),
        @Filter(name = "address", condition = "address like :address"),
        @Filter(name = "cost", condition = "cost <= :cost"),
})

@Table(name = "Place")

public class Place extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double cost;

    @ManyToMany(mappedBy = "places", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<DayTrip> trips = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "startBus")
    private Bus startBus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "arrivalBus")
    private Bus arrivalBus;

    public Place() { }

    public Place(String name, String address, double cost) {
        this.name = name;
        this.address = address;
        this.cost = cost;
    }

    public int getId() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<DayTrip> getTrips() {
        return trips;
    }

    public void setTrips(Set<DayTrip> trips) {
        this.trips = trips;
    }

    public Bus getStartBus() {
        return startBus;
    }

    public void setStartBus(Bus startBus) {
        this.startBus = startBus;
    }

    public Bus getArrivalBus() {
        return arrivalBus;
    }

    public void setArrivalBus(Bus arrivalBus) {
        this.arrivalBus = arrivalBus;
    }
}
