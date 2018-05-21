package Server.Remote;

import Server.Entity.EntityInterface;
import Server.Entity.Side;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class SideDishServiceImplementation extends AbstractBaseService implements BaseService {
    public SideDishServiceImplementation() throws RemoteException {
        super(new DishRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Side.class);
    }

    @Override
    public JSONObject read(JSONObject parameters) {
        List list = ((DishRepository) repository).getSide(prepareReadParameter(parameters));
        return (new Result(true, list)).toJson();
    }
}
