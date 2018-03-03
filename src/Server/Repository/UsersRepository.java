package Server.Repository;

public class UsersRepository extends Repository {
    private String sql = "select id, username, password, email from Users";;

    public UsersRepository() {
        super.sql = sql;
    }
}
