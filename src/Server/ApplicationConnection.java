package Server;

import org.ho.yaml.Yaml;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ApplicationConnection {

    private static HashMap<String, Connection> connection;
    private static ApplicationConnection applicationConnection = null;

    private ApplicationConnection () {
        connection = new HashMap<String, Connection>();
        HashMap dbmsConfigurations = getDBMSConfigurations();
        loadDBMSConfigurations(dbmsConfigurations);
    }

    @Nullable
    private HashMap getDBMSConfigurations() {
        try {
            File f = new File("configuration/databases.yml");
            HashMap yamlConfiguration = (HashMap) Yaml.load(f);

            return (HashMap) yamlConfiguration.get("dbms");
        } catch (FileNotFoundException e) {
            System.out.println("File not found or corrupted!\nPlease check in the path: configuration/databases.yml");
            e.printStackTrace();
        }
        return null; //SIAMO SICURI!?!?!?!?
    }

    private void loadDBMSConfigurations(HashMap dbmsConfigurations) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Library for Postgres not found!\nPlease link the library to the project.");
            e.printStackTrace();
        }

        try {
            assert dbmsConfigurations != null;
            Set set = dbmsConfigurations.entrySet();
            for (Object aSet : set) {
                Map.Entry db = (Map.Entry) aSet;

                addConnection((String) db.getKey(), (HashMap) db.getValue());
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }

    private void addConnection(String dbType, HashMap configuration) throws SQLException {
        String dbname = (String) configuration.get("dbname");
        Connection conn = DriverManager.getConnection(
                String.format("jdbc:%s://%s:%s/%s", dbType,
                        configuration.get("host"),
                        configuration.get("port"), dbname),
                (String) configuration.get("user"),
                (String) configuration.get("password")
        );

        connection.put(dbname, conn);
    }

    public static ApplicationConnection getInstance() {
        if(applicationConnection == null) {
            applicationConnection = new ApplicationConnection();
        }
        return applicationConnection;
    }

    public Connection getConnection() {
        return connection.get("application");
    }

    public Connection getConnection(String database) {
        return connection.get(database);
    }
}
