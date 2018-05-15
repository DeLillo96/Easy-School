package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Aliment;
import Client.Model.Dish;
import Client.Remote.RemoteManager;
import Shared.AssignService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AlimentController extends AbstractTableController {
    protected Dish dish;
    @FXML
    private Label dishName;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TableView<Aliment> alimentTableView;

    public AlimentController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getAlimentService());
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Aliment> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Aliment aliment = new Aliment(this, (JSONObject) data.get(i));
            aliment.setDishId(dish.getId());

            list.add(aliment);
        }

        return list;
    }

    @Override
    public void filter() {
        try {
            ArrayList<Aliment> list = search();

            AssignService recipesService = RemoteManager.getInstance().getRemoteServicesManager().getRecipesService();
            JSONObject result = recipesService.rightRead(dish.getId());
            ArrayList<Aliment> recipes = parseIntoRows((JSONObject) result.get("data"));

            for (Aliment aliment : list) {
                recipes.forEach(o -> {
                    if (o.getId().equals(aliment.getId())) {
                        aliment.getSelect().setSelected(true);
                    }
                });
            }

            ObservableList<Aliment> items = FXCollections.observableArrayList(list);
            alimentTableView.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        alimentTableView.getItems().remove(abstractRowModel);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @FXML
    public void add() throws Exception {
        alimentTableView.getItems().add(new Aliment(this));
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }

}