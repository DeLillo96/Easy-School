package Server.Controller;

import Server.Repository.UsersRepository;
import Server.Result;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;

public class UserControllerImplementation extends AbstractController implements UserController {
    public UserControllerImplementation() throws RemoteException {
        super(new UsersRepository());
    }

    @Override
    public JSONObject login(String username, String password) {
        Result result = new Result();

        if(!((new UsersRepository()).login(username, password))) {
            result.setSuccess(false);
            result.addMessage("Password and/or Username are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject logout(String username, String password) {
        return (new Result(true)).toJson();
    }
}
