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
        @FilterDef(name = "name", parameters = @ParamDef(name = "name", type = "string")),
        @FilterDef(name = "address", parameters = @ParamDef(name = "address", type = "string")),
        @FilterDef(name = "cost", parameters = @ParamDef(name = "cost", type = "integer"))
})

@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like '%' || :name || '%'"),
        @Filter(name = "address", condition = "address like '%' || :address || '%'"),
        @Filter(name = "cost", condition = "cost <= :cost"),
})

@Table(name = "Place")

public class Place extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double cost;

    @ManyToMany(mappedBy = "places", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private Set<Trip> trips = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "StartingBuses",
            joinColumns = {@JoinColumn(name = "place_id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_id")}
    )
    private Set<Bus> startBuses = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ArrivingBuses",
            joinColumns = {@JoinColumn(name = "place_id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_id")}
    )
    private Set<Bus> arrivalBuses = new HashSet<>();

    public Place() {
    }

    public Place(String name, String address, double cost) {
        this.name = name;
        this.address = address;
        this.cost = cost;
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

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public Set<Bus> getStartBuses() {
        return startBuses;
    }

    public void setStartBuses(Set<Bus> startBuses) {
        this.startBuses = startBuses;
    }

    public Set<Bus> getArrivalBuses() {
        return arrivalBuses;
    }

    public void setArrivalBuses(Set<Bus> arrivalBuses) {
        this.arrivalBuses = arrivalBuses;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctName = this.getName();
        this.setName(nameCorrector(correctName));

        String correctAddress = this.getAddress();
        this.setAddress(nameCorrector(correctAddress));

        Double correctCost = this.getCost();
        if(correctCost < 0) throw new IllegalArgumentException("Violated constraints on cost field (Negative cost)");

    }
}
