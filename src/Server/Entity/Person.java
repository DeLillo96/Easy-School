package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@FilterDefs({
        @FilterDef(name = "id", parameters = {@ParamDef(name = "id", type = "integer")}),
        @FilterDef(name = "name", parameters = {@ParamDef(name = "name", type = "string")}),
        @FilterDef(name = "surname", parameters = {@ParamDef(name = "surname", type = "string")}),
        @FilterDef(name = "fiscalCode", parameters = {@ParamDef(name = "fiscalCode", type = "string")}),
        @FilterDef(name = "birthDateFrom", parameters = {@ParamDef(name = "birthDateFrom", type = "date")}),
        @FilterDef(name = "birthDateTo", parameters = {@ParamDef(name = "birthDateTo", type = "date")}),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "name", condition = "name like '%' || :name || '%'"),
        @Filter(name = "surname", condition = "surname like '%' || :surname || '%'"),
        @Filter(name = "fiscalCode", condition = "fiscalCode like '%' || :fiscalCode || '%'"),
        @Filter(name = "birthDateFrom", condition = "birthDate >= :birthDateFrom"),
        @Filter(name = "birthDateTo", condition = "birthDate <= :birthDateTo")
})
public abstract class Person extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 32)
    private String surname;

    @Column(unique = true, length = 16)
    private String fiscalCode;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    public Person() {
    }

    public Person(String name, String surname, String fiscalCode, Date birthDate, String telephone) {
        this.name = name;
        this.surname = surname;
        this.fiscalCode = fiscalCode;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public Date getBirthDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthDate);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}