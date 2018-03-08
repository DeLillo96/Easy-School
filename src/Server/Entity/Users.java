package Server.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;

@Entity
@FilterDef(name="usersfilter", parameters={
        @ParamDef(name="username", type="string" ),
        @ParamDef(name="password", type="string" ),
        @ParamDef(name="email", type="string"),
        @ParamDef(name="id", type="integer")
})
@Filters({
        @Filter(name = "usersfilter", condition = "username like :username"),
        @Filter(name = "usersfilter", condition = "password like :password"),
        @Filter(name = "usersfilter", condition = "email like :email"),
        @Filter(name = "usersfilter", condition = "id = :id")
})
@Table(name = "Users")
public class Users extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(unique = true, nullable = false, length = 16)
    private String username;

    @Column(nullable = false, length = 16)
    private String password;

    @Column(unique = true, length = 32)
    private String email;

    public Users() {
        this("","",null);
    }

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Users(String username, String password) {
        this(username, password, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected boolean primaryKeysAreValid() {
        return (null != getId() && getId() > 0);
    }

    @Override
    public String parseForWhere() {
        return "username = " + username;
    }
}
