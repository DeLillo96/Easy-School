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
        @FilterDef(name = "code", parameters = @ParamDef(name = "code", type = "string")),
        @FilterDef(name = "companyName", parameters = @ParamDef(name = "companyName", type = "string"))
})

@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "code", condition = "code like '%' || :code || '%'"),
        @Filter(name = "companyName", condition = "companyName like '%' || :companyName || '%'"),
})

@Table(name = "Bus")

public class Bus extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String companyName;

    @ManyToMany(mappedBy = "vehicles", fetch = FetchType.EAGER)
    private Set<Trip> trips = new HashSet<>();

    @ManyToMany(mappedBy = "vehicles", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<ChildInVehicle> childs = new HashSet<>();

    public Bus() {
    }

    public Bus(String code, String companyName) {
        this.code = code;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }

    public Set<ChildInVehicle> getChilds() {
        return childs;
    }

    public void setChilds(Set<ChildInVehicle> childs) {
        this.childs = childs;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctCode = this.getCode();
        if(!validateString(getCode(), "^[a-zA-Z0-9]*$")) throw new IllegalArgumentException("Violated constraints on code/license plate field (Only numbers and letters are allowed, no spaces)");
        this.setCode(correctCode.toUpperCase());

        String correctCompanyName = this.getCompanyName();
        this.setCompanyName(nameCorrector(companyName));
    }
}
