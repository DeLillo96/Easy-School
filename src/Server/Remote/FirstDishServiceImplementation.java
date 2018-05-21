package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.First;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class FirstDishServiceImplementation extends AbstractBaseService implements BaseService {
    public FirstDishServiceImplementation() throws RemoteException {
        super(new DishRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), First.class);
    }

    @Override
    public JSONObject read(JSONObject parameters) {
        List list = ((DishRepository) repository).getFirst(prepareReadParameter(parameters));
        return (new Result(true, list)).toJson();
    }
}
