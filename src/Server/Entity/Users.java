package Server.Entity;

import java.util.Hashtable;

public class Users {
    private Integer id;
    private String username;
    private String password;
    private String email;

    public Users(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Users(Hashtable options) {
        if(options.containsKey("id")) id = (Integer) options.get("id");
        if(options.containsKey("username")) username = (String) options.get("username");
        if(options.containsKey("password")) password = (String) options.get("password");
        if(options.containsKey("email")) email = (String) options.get("email");
    }
}
