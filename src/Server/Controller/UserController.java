package Server.Controller;

import Server.Repository.UsersRepository;
import java.rmi.RemoteException;

public class UserController extends MainController {
    public UserController() throws RemoteException {
        super(new UsersRepository());
    }
}
