package Server.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
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
}
