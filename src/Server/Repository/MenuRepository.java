package Server.Repository;

import Server.Entity.Dish;
import Server.Entity.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MenuRepository extends AbstractRepository {

    public MenuRepository() {
        super("Menu");
    }

    public Menu getMenuById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List menus = read(params);

        return menus != null && menus.size() == 1 ? (Menu) menus.get(0) : null;
    }

    public Set<Menu> getMenusByfirst(String first) {
        DishRepository dishRepository = new DishRepository();
        Dish firstes = dishRepository.getDishByName(first);
        return firstes.getFirstMenu();
    }

    public Set<Menu> getMenusBysecond(String second) {
        DishRepository dishRepository = new DishRepository();
        Dish secondes = dishRepository.getDishByName(second);
        return secondes.getSecondMenu();
    }

    public Set<Menu> getMenusByside(String side) {
        DishRepository dishRepository = new DishRepository();
        Dish sidees = dishRepository.getDishByName(side);
        return sidees.getSideMenu();
    }

    public Set<Menu> getMenuBySweet(String sweet) {
        DishRepository dishRepository = new DishRepository();
        Dish sweets = dishRepository.getDishByName(sweet);
        return sweets.getSweet();
    }
}