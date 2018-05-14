package Server.Repository;

import Server.Entity.Aliment;
import Server.Entity.Category;
import Server.Entity.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DishRepository extends AbstractRepository {

    public DishRepository() {
        super("Dish");
    }

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

    public List getDishByCategory(String categoryName) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category category = categoryRepository.getCategoryByName(categoryName);

        return new ArrayList(category.getDishes());
    }

    public List getDishByCategory(Integer categoryId) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category category = categoryRepository.getCategoryById(categoryId);

        return new ArrayList(category.getDishes());
    }

    public List getDishByAliment(Integer alimentId) {
        AlimentRepository alimentRepository = new AlimentRepository();
        Aliment aliment = alimentRepository.getAlimentById(alimentId);

        return aliment.getDishes() != null ? new ArrayList(aliment.getDishes()) : new ArrayList();
    }
}