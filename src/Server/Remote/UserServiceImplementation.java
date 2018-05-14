package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Users;
import Server.Repository.UsersRepository;
import Server.Result;
import Shared.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class UserServiceImplementation extends AbstractBaseService implements UserService {
    public UserServiceImplementation() throws RemoteException {
        super(new UsersRepository());
    }

    @Override
    public JSONObject login(String username, String password) {
        Result result = new Result();

        if (!((new UsersRepository()).login(username, password))) {
            result.setSuccess(false);
            result.addMessage("Password or Username are invalid");
        }

        return result.toJson();
    }

    @Override
    public JSONObject logout(String username, String password) {
        return (new Result(true)).toJson();
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Users.class);
    }
}
