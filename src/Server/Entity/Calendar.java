package Server.Entity;

import Client.Model.DayTrips;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
        @FilterDef(name = "date", parameters = @ParamDef(name = "date", type = "date")),
        @FilterDef(name = "dateFrom", parameters = {@ParamDef(name = "dateFrom", type = "date")}),
        @FilterDef(name = "dateTo", parameters = {@ParamDef(name = "dateTo", type = "date")}),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "date", condition = "date = :date"),
        @Filter(name = "dateFrom", condition = "date >= :dateFrom"),
        @Filter(name = "dateTo", condition = "date <= :dateTo")
})
@Table(name = "Calendar")
public class Calendar extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, unique = true)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Present",
            joinColumns = {@JoinColumn(name = "calendar_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )
    private Set<Child> presentChildren = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "DailyMenus",
            joinColumns = {@JoinColumn(name = "calendar_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")}
    )
    private Set<Menu> dailyMenus = new HashSet<>();

    @OneToMany(mappedBy = "day", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Trip> dailyTrip = new HashSet<>();

    public Calendar() {
        this.date = new Date();
    }

    public Calendar(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);

        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public void setDate(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        this.date=cal.getTime();
    }

    public Set<Child> getPresentChilds() {
        return presentChildren;
    }

    public void setPresentChilds(Set<Child> presentChilds) {
        this.presentChildren = presentChilds;
    }

    public Set<Menu> getDailyMenus() {
        return dailyMenus;
    }

    public void setDailyMenus(Set<Menu> dailyMenus) {
        this.dailyMenus = dailyMenus;
    }

    public Set<Child> getPresentChildren() {
        return presentChildren;
    }

    public void setPresentChildren(Set<Child> presentChildren) {
        this.presentChildren = presentChildren;
    }

    public Set<Trip> getTrip() {
        return dailyTrip;
    }

    public void setTrip(Set<Trip> dailyTrip) {
        this.dailyTrip = dailyTrip;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();
    }
}
