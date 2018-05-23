package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
        @FilterDef(name = "licensePlate", parameters = @ParamDef(name = "licensePlate", type = "string")),
        @FilterDef(name = "companyName", parameters = @ParamDef(name = "companyName", type = "string"))
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
    private Integer id;

    @Column(unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String companyName;

    @ManyToMany(mappedBy = "startBuses", fetch = FetchType.EAGER)
    private Set<Place> startPlaces = new HashSet<>();

    @ManyToMany(mappedBy = "arrivalBuses", fetch = FetchType.EAGER)
    private Set<Place> destinationPlaces = new HashSet<>();

    @OneToMany(mappedBy = "day", fetch = FetchType.EAGER)
    private Set<BusPresence> busPresences = new HashSet<>();

    public Bus() {
    }

    public Bus(String licensePlate, String companyName) {
        this.licensePlate = licensePlate;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<BusPresence> getBusPresences() {
        return busPresences;
    }

    public void setBusPresences(Set<BusPresence> busPresences) {
        this.busPresences = busPresences;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctLicensePlate = this.getLicensePlate();
        if(!validateString(getLicensePlate(), "^[a-zA-Z0-9]*$")) throw new IllegalArgumentException("Violated constraints on code/license plate field (Only numbers and letters are allowed, no spaces)");
        this.setLicensePlate(correctLicensePlate.toUpperCase());

        String correctCompanyName = this.getCompanyName();
        this.setCompanyName(nameCorrector(companyName));
    }

}
