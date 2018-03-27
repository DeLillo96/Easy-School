package Server.Repository;

import Server.Entity.Dish;
import Server.Entity.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MenuRepository extends AbstractRepository{

    public MenuRepository() {super("Menu"); }

    public Menu getMenuById(Integer id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        List menus = read(params);

        return menus != null && menus.size() == 1 ? (Menu) menus.get(0) : null;
    }

    public Set<Menu> getMenusByFirstDish(String firstDish) {
        DishRepository dishRepository = new DishRepository();
        Dish firstDishes = dishRepository.getDishByName(firstDish);
        return firstDishes.getFirstMenu();
    }

    public Set<Menu> getMenusBySecondDish(String secondDish) {
        DishRepository dishRepository = new DishRepository();
        Dish secondDishes = dishRepository.getDishByName(secondDish);
        return secondDishes.getSecondMenu();
    }

    public Set<Menu> getMenusBySideDish(String sideDish) {
        DishRepository dishRepository = new DishRepository();
        Dish sideDishes = dishRepository.getDishByName(sideDish);
        return sideDishes.getSideMenu();
    }

    public Set<Menu> getMenuBySweet(String sweet) {
        DishRepository dishRepository = new DishRepository();
        Dish sweets = dishRepository.getDishByName(sweet);
        return sweets.getSweet();
    }
}