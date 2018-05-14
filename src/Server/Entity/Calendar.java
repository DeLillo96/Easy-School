package Server.Entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Calendar")
public class Calendar extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true)
    private Date date;

    @ManyToMany(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Present",
            joinColumns = {@JoinColumn(name = "calendar_id")},
            inverseJoinColumns = {@JoinColumn(name = "child_id")}
    )
    private Set<Child> presentChildren = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "daily_menu")
    private Menu dailyMenu;

    @OneToMany(mappedBy = "day", fetch = FetchType.EAGER)
    private Set<DayTrip> trips = new HashSet<>();

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
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Child> getPresentChilds() {
        return presentChildren;
    }

    public void setPresentChilds(Set<Child> presentChilds) {
        this.presentChildren = presentChilds;
    }

    public Menu getDailyMenu() {
        return dailyMenu;
    }

    public void setDailyMenu(Menu dailyMenu) {
        this.dailyMenu = dailyMenu;
    }

    public Set<DayTrip> getTrips() {
        return trips;
    }

    public void setTrips(Set<DayTrip> trips) {
        this.trips = trips;
    }
}
