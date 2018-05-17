package Server.Remote;

import Server.Entity.Dish;
import Server.Entity.EntityInterface;
import Server.Entity.Menu;
import Server.Repository.MenuRepository;
import Shared.BaseService;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class MenuServiceImplementation extends AbstractBaseService implements BaseService {
    public MenuServiceImplementation() throws RemoteException {
        super(new MenuRepository());
    }

    @Override
    protected EntityInterface castJsonIntoEntity(JSONObject jsonObject) throws IOException {
        Menu menu = new Menu();
        /*
        if (jsonObject.get("id") != null) menu.setId(Integer.parseInt((String) jsonObject.get("id")));
        if (jsonObject.get("first") != null) menu.setfirst(castJsonIntoDish((JSONObject) jsonObject.get("first")));
        if (jsonObject.get("second") != null) menu.setsecond(castJsonIntoDish((JSONObject) jsonObject.get("second")));
        if (jsonObject.get("side") != null) menu.setside(castJsonIntoDish((JSONObject) jsonObject.get("side")));
        if (jsonObject.get("sweet") != null) menu.setSweet(castJsonIntoDish((JSONObject) jsonObject.get("sweet")));
        */
        return menu;
    }

    private Dish castJsonIntoDish(JSONObject jsonDish) throws IOException {
        /*
        Dish dish = new Dish();

        if (jsonDish.get("id") != null) dish.setId(Integer.parseInt(String.valueOf(jsonDish.get("id"))));
        if (jsonDish.get("name") != null) {
            dish.setName((String) jsonDish.get("name"));
        }
        if (jsonDish.get("dishCategory") != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            dish.setCategory(objectMapper.readValue(jsonDish.get("dishCategory").toString(), Category.class));
        }

        return dish;
        */
        return null;
    }
}
