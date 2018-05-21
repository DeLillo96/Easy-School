package Shared;

import org.json.simple.JSONObject;

public interface DishService extends BaseService {
    JSONObject getDishesByCategoryName(String categoryName) throws Exception;
}
