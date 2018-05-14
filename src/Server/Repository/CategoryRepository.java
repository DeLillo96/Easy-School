package Server.Repository;

import Server.Entity.Category;

import java.util.HashMap;
import java.util.List;

public class CategoryRepository extends AbstractRepository {

    public CategoryRepository() {
        super("Category");
    }

    public Category getCategoryById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List category = read(params);

        return category != null && category.size() == 1 ? (Category) category.get(0) : null;
    }

    public Category getCategoryByName(String name) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        List category = read(params);

        return category != null && category.size() == 1 ? (Category) category.get(0) : null;
    }

}
