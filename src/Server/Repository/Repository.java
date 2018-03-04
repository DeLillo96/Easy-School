package Server.Repository;

import Server.ApplicationConnection;
import Server.Entity.Entity;

import java.sql.*;

public class Repository {
    private String sql;
    private Connection connection;
    private String tableName;

    public Repository() { this(ApplicationConnection.getInstance().getConnection(),"",""); }

    public Repository(String sql, String tableName) {
        this(ApplicationConnection.getInstance().getConnection(), sql, tableName);
    }

    public Repository(Connection connection, String sql, String tableName) {
        this.sql = sql;
        this.tableName = tableName;
        this.connection = connection;
    }

    private PreparedStatement prepareStmt() throws SQLException {
        return connection.prepareStatement(sql);
    }

    private ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    public ResultSet readAll() {
        try {
            PreparedStatement stmt = prepareStmt();
            if(stmt != null) return execute(stmt);
        } catch (SQLException e) {
            System.out.println("Read failed!");
            e.printStackTrace();
        }
        return null;
    }

    public Entity getEntityInstance() {
        return new Entity(tableName, connection);
    }
}
