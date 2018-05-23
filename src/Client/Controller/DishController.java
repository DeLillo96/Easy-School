package Client.Controller;

import Client.ControllerManager;
import Client.Model.Dish;
import Client.Model.Menu;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DishController extends AbstractTableController {
    private Menu menu;

    public DishController() throws Exception {
        super(null);
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
    }

    @Override
    protected ArrayList<Dish> parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Dish> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dish = (JSONObject) data.get(i);

            list.add(new Dish(service, this, dish));
        }

        return list;
    }

    @FXML
    public void add() throws Exception {
        addIntoTable(new Dish(service, this));
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setMenuDish(int id, String stringName) {
        ControllerManager.getInstance().notifySuccess("Dish set correctly");
    }

}
