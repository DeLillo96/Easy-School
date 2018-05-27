package Server.Remote;

import Server.Entity.Aliment;
import Server.Entity.Dish;
import Server.Repository.AlimentRepository;
import Server.Repository.DishRepository;
import Server.Result;
import Shared.RelationService;
import org.json.simple.JSONObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RecipesServiceImplementation extends UnicastRemoteObject implements RelationService {
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

        for (Aliment recipe : dish.getIngredients()) {
            if (recipe.getId().equals(alimentId)) {
                Result result = new Result();
                result.addData(dish);
                return result.toJson();
            }
        }

        dish.getIngredients().add(aliment);
        return dish.save().toJson();
    }

    @Override
    public JSONObject assign(Integer rightId, List leftIds) throws Exception {
        return null;
    }

    @Override
    public JSONObject assign(List rightIds, Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject deAssign(Integer dishId, Integer alimentId) throws Exception {
        Dish dish = dishRepository.getDishById(dishId);

        for (Aliment recipe : dish.getIngredients()) {
            if (recipe.getId().equals(alimentId)) {
                dish.getIngredients().remove(recipe);
                return dish.save().toJson();
            }
        }
        Result result = new Result();
        result.addData(dish);
        return result.toJson();
    }

    @Override
    public JSONObject leftRead(Integer alimentId) throws Exception {
        List list = dishRepository.getDishByAliment(alimentId);
        return new Result(true, list).toJson();
    }

    @Override
    public Integer rightCount(Integer rightId) throws Exception {
        return null;
    }

    @Override
    public Integer leftCount(Integer leftId) throws Exception {
        return null;
    }

    @Override
    public JSONObject rightRead(Integer dishId) throws Exception {
        List list = alimentRepository.getAlimentByDish(dishId);
        return new Result(true, list).toJson();
    }
}
