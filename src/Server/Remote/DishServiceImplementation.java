package Server.Remote;

import Server.Entity.Dish;
import Server.Entity.EntityInterface;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.DishService;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class DishServiceImplementation extends AbstractBaseService implements DishService {
    public DishServiceImplementation() throws RemoteException {
        super(new DishRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonObject.toString(), Dish.class);
    }

    @Override
    public JSONObject getDishesByCategoryName(String categoryName) throws Exception {
        Result result = new Result();

        List response = (new DishRepository()).getDishByCategory(categoryName);
        if(response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("dish category are invalid");
        }

        return result.toJson();
    }
}
