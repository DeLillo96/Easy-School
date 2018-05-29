package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@FilterDefs({
        @FilterDef(name = "mansion", parameters = {@ParamDef(name = "mansion", type = "string")})
})
@Filters({
        @Filter(name = "mansion", condition = "mansion like '%' || :mansion || '%'")
})
@Table(name = "Staff")
public class Staff extends Person {
    @Column(length = 64)
    private String mansion;

    public String getMansion() {
        return mansion;
    }

    public void setMansion(String mansion) {
        this.mansion = mansion;
    }

    public Staff() {}

    public Staff(String name, String surname, String fiscalCode, Date birthDate, String mansion) {
        setName(name);
        setSurname(surname);
        setFiscalCode(fiscalCode);
        setBirthDate(birthDate);
        this.mansion = mansion;
    }

    @Override
    protected void beforeSave() {
        super.beforeSave();

        String correctMansion = this.getMansion();
        if(!validateString(correctMansion, "^[\\p{L} .'-]+$")) throw new IllegalArgumentException("Violated constraints on mansion field (No symbols allowed, no numbers allowed, max length = 64)");
        this.setMansion(nameCorrector(correctMansion));

        Date birthDate = this.getBirthDate();
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        Date actualDate = today.getTime();
        long diff = actualDate.getTime() - birthDate.getTime();
        if((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)/(365.25))<16) throw new IllegalArgumentException("Violated constraints on birth date field (The person is too much young to be identified as a staff member)");
    }
}
