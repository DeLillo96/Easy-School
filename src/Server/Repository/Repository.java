package Server.Repository;

import java.sql.*;

class Repository {
    String sql;
    private Connection connection;

    Repository() {
        connection = connect();
    }

    private Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Library for Postgres not found!\nPlease link the library to the project.");
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/application", "admin", "admin");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
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
}
