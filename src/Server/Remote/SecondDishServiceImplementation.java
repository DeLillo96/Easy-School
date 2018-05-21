package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Second;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SecondDishServiceImplementation extends AbstractBaseService implements BaseService {
    public SecondDishServiceImplementation() throws RemoteException {
        super(new DishRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Second.class);
    }

    @Override
    public JSONObject read(JSONObject parameters) {
        List list = ((DishRepository) repository).getSecond(prepareReadParameter(parameters));
        return (new Result(true, list)).toJson();
    }
}
