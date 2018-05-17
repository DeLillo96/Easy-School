package Shared;

import org.json.simple.JSONObject;

import java.rmi.Remote;

public interface DishService extends BaseService {
    JSONObject getDishesByCategoryName(String categoryName) throws Exception;
}
