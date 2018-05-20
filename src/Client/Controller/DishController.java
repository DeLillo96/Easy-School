package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Dish;
import Client.Model.Menu;
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

    private Menu menu;

    public DishController() throws Exception {
        super(null);
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
        dishTableView.getItems().add(dish);
    }

    @Override
    public void filter() {
        try {
            ArrayList<Dish> list = search(takeFilters());
            ObservableList<Dish> items = FXCollections.observableArrayList(list);
            dishTableView.setItems(items);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
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

    public void setMenuDish(int id, String stringName) {
        name.setText(stringName);
        menu.setDishData(id, stringName, category.getText());
        ControllerManager.getInstance().notifySuccess("Dish set correctly");
    }
}
