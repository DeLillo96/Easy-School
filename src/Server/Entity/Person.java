package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctName = this.getName();
        if(!validateString(correctName, "^[\\p{L} .'-]+$")) throw new IllegalArgumentException("Violated constraints on name field (No symbols allowed, no numbers allowed, max length = 32)");
        this.setName(nameCorrector(correctName));

        String correctSurname = this.getSurname();
        if(!validateString(correctSurname, "^[\\p{L} .'-]+$")) throw new IllegalArgumentException("Violated constraints on surname field (No symbols allowed, no numbers allowed, max length = 32)");
        this.setSurname(nameCorrector(correctSurname));

        String correctFiscalCode = this.getFiscalCode();
        if(!validateString(correctFiscalCode, "^[a-zA-Z0-9]*$")) throw new IllegalArgumentException("Violated constraints on fiscal code field (Only numbers and letters are allowed, no spaces)");
        if(this.getFiscalCode().length()!=16) throw new IllegalArgumentException("Violated constraints on fiscal code field (This field has to be filled with a sequence of 16 letters or numbers in any combination)");
        this.setFiscalCode(correctFiscalCode.toUpperCase());
    }
}