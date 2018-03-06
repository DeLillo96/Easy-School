package Server.Entity;

import java.sql.Connection;
import java.util.Hashtable;

public class Entity {
    protected String tableName;
    protected Connection connection;

    protected Hashtable values;

    public Entity() {
        this("", null);
    }
    public Entity(String tableName, Connection connection) {
        this.tableName = tableName;
        this.connection = connection;
    }
}
