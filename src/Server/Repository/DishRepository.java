package Server.Repository;

import Server.Entity.Dish;

import java.util.HashMap;
import java.util.List;

public class DishRepository extends AbstractRepository {

    public DishRepository() {super("Dish"); }

    public Dish getDishById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List dishes = read(params);

        return dishes != null && dishes.size() == 1 ? (Dish) dishes.get(0) : null;
    }

    public Dish getDishByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List dishes = read(params);

        return dishes != null && dishes.size() == 1 ? (Dish) dishes.get(0) : null;
    }

    /*public Dish getDishByCategory(String category) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("category", category);
        List dishes = read(params);

        return dishes != null && dishes.size() == 1 ? (Dish) dishes.get(0) : null;
    }*/
}