package Server.Repository;

public class UsersRepository extends Repository {
    private static String sql = "select id, username, password, email from Users";
    private static String tableName = "users";

    public UsersRepository() {
        super(sql, tableName);
    }
}
