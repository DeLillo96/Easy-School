package Server.Remote;

import Server.Entity.Aliment;
import Server.Entity.Dish;
import Server.Repository.AlimentRepository;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.AssignService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RecipesServiceImplementation extends UnicastRemoteObject implements AssignService {
    protected DishRepository dishRepository;
    protected AlimentRepository alimentRepository;

    public RecipesServiceImplementation() throws RemoteException {
        dishRepository = new DishRepository();
        alimentRepository = new AlimentRepository();
    }

    @Override
    public JSONObject assign(Integer dishId, Integer alimentId) throws Exception {
        Dish dish = dishRepository.getDishById(dishId);
        Aliment aliment = alimentRepository.getAlimentById(alimentId);

        dish.getIngredients().add(aliment);
        return dish.save().toJson();
    }

    @Override
    public JSONObject deAssign(Integer dishId, Integer alimentId) throws Exception {
        Dish dish = dishRepository.getDishById(dishId);

        for (Aliment recipe: dish.getIngredients()) {
            if (recipe.getId().equals(alimentId)) {
                dish.getIngredients().remove(recipe);
                break;
            }
        }

        return dish.save().toJson();
    }

    @Override
    public JSONObject rightRead(Integer categoryId) throws Exception {
        Result result = new Result();
        result.addData(dishRepository.getDishByCategory(categoryId));
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer leftId) throws Exception {
        return null;
    }
}
