package Server;

import Server.Repository.UsersRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws SQLException {

        UsersRepository usersRepository = new UsersRepository();
        ResultSet result = usersRepository.readAll();
        while (result.next()) {
            System.out.println(result.getInt("id")
                    + " " + result.getString("username")
                    + " " + result.getString("password")
                    + " " + result.getString("email")
            );
        }
    }
}
