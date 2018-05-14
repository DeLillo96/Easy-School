package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Dish;
import Client.Model.Menu;
import Client.Remote.RemoteManager;
import Shared.BaseService;
import Shared.DishService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DishController extends AbstractTableController {
    @FXML
    public Label category;
    @FXML
    public Label name;
    @FXML
    private Button addButton;
    @FXML
    private TableView<Dish> dishTableView;

    private JSONObject jsonCategory;
    private Menu menu;

    public DishController() throws Exception {
        super((BaseService) RemoteManager.getInstance().getRemoteServicesManager().getDishService());
    }

    @Override
    protected JSONObject takeFilters() {
        return null;
    }

    @Override
    protected ArrayList<Dish> parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Dish> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dish = (JSONObject) data.get(i);

            list.add(new Dish(this, dish));
        }

        return list;
    }

    @FXML
    public void add() throws Exception {
        Dish dish = new Dish(this);
        dish.setCategory(jsonCategory);
        dishTableView.getItems().add(dish);
    }

    @Override
    public void filter() {
        try {
            ArrayList<Dish> list = search();
            ObservableList<Dish> items = FXCollections.observableArrayList(list);
            dishTableView.setItems(items);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    protected ArrayList<Dish> search() throws Exception {
        DishService service = RemoteManager.getInstance().getRemoteServicesManager().getDishService();
        JSONObject response = service.getDishesByCategoryName(this.getCategoryName());

        if ((boolean) response.get("success")) {
            JSONObject data = (JSONObject) response.get("data");
            return parseIntoRows(data);
        } else throw new Exception("Read from server error");
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        dishTableView.getItems().remove(abstractRowModel);
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public JSONObject getCategory() {
        return jsonCategory;
    }

    public void setCategory(JSONObject jsonCategory) {
        this.jsonCategory = jsonCategory;
    }

    public String getCategoryName() {
        return (String) jsonCategory.get("name");
    }

    public void setMenuDish(int id, String stringName) {
        menu.setDishData(id, stringName, getCategoryName());
    }
}
