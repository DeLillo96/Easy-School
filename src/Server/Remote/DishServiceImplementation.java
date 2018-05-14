package Server.Remote;

import Server.Entity.Category;
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
    protected EntityInterface castJsonIntoEntity(JSONObject jsonDish) throws IOException {
        Dish dish = new Dish();

        if (jsonDish.get("id") != null) dish.setId(Integer.parseInt((String) jsonDish.get("id")));
        if (jsonDish.get("name") != null) dish.setName((String) jsonDish.get("name"));
        if (jsonDish.get("dishCategory") != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            dish.setCategory(objectMapper.readValue(jsonDish.get("dishCategory").toString(), Category.class));
        }

        return dish;
    }

    @Override
    public JSONObject getDishesByCategoryName(String categoryName) {
        Result result = new Result();

        List response = (new DishRepository()).getDishByCategory(categoryName);
        if (response != null) {
            result.setData(response);
        } else {
            result.setSuccess(false);
            result.addMessage("dish category are invalid");
        }

        return result.toJson();
    }
}
