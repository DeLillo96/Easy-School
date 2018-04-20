package Server.Remote;

import Server.Entity.Dish;
import Server.Entity.EntityInterface;
import Server.Repository.DishRepository;
import Shared.BaseService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;

public class DishServiceImplementation extends AbstractBaseService implements BaseService {
    public DishServiceImplementation() throws RemoteException {
        super(new DishRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Dish.class);
    }
}
