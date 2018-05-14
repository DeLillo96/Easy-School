package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlimentRepository extends AbstractRepository {
    public AlimentRepository() {
        super("Aliment");
    }

    public Aliment getAlimentById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List aliment = read(params);

        return aliment != null && aliment.size() == 1 ? (Aliment) aliment.get(0) : null;
    }

    public Aliment getAlimentByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List aliment = read(params);

        return aliment != null && aliment.size() == 1 ? (Aliment) aliment.get(0) : null;
    }

    public List getAlimentByDish(Integer dishId) {
        DishRepository dishRepository = new DishRepository();
        Dish dish = dishRepository.getDishById(dishId);

        return new ArrayList(dish.getIngredients());
    }
}
