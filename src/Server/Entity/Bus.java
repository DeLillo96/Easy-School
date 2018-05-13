package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer") ),
        @FilterDef(name = "licensePlate", parameters = @ParamDef(name = "licensePlate", type = "string") ),
        @FilterDef(name = "companyName", parameters = @ParamDef(name = "companyName", type = "string") )
})

@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "licensePlate", condition = "licensePlate like '%' || :licensePlate || '%'"),
        @Filter(name = "companyName", condition = "companyName like '%' || :companyName || '%'"),
})

@Table(name = "Bus")

public class Bus extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private int id;

    @Column(unique = true, length = 7)
    private String licensePlate;

    @Column(nullable = false)
    private String companyName;

    @ManyToMany(mappedBy = "buses", fetch = FetchType.EAGER)
    private Set<DayTrip> trips = new HashSet<>();

    @ManyToMany(mappedBy = "startBuses")
    private Set<Place> startPlaces = new HashSet<>();

    @ManyToMany(mappedBy = "arrivalBuses", fetch = FetchType.EAGER)
    private Set<Place> destinationPlaces = new HashSet<>();

    public Bus() { }

    public Bus(String licensePlate, String companyName) {
        this.licensePlate = licensePlate;
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<DayTrip> getTrips() {
        return trips;
    }

    public void setTrips(Set<DayTrip> trips) {
        this.trips = trips;
    }

    public Set<Place> getStartPlaces() {
        return startPlaces;
    }

    public void setStartPlaces(Set<Place> startPlaces) {
        this.startPlaces = startPlaces;
    }

    public Set<Place> getDestinationPlaces() {
        return destinationPlaces;
    }

    public void setDestinationPlaces(Set<Place> destinationPlaces) {
        this.destinationPlaces = destinationPlaces;
    }
}
