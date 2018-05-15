package Client.Controller;

import Client.ControllerManager;
import Client.Model.AbstractRowModel;
import Client.Model.Menu;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MenuController extends AbstractTableController {

    /* TABLE */
    @FXML
    private TableView<Menu> menuTableView;

    public MenuController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService());
    }

    @FXML
    public void initialize() {
        filter();
    }

    @Override
    protected JSONObject takeFilters() {
        return null;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Menu> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject menu = (JSONObject) data.get(i);

            list.add(new Menu(this, menu));
        }

        return list;
    }

    @FXML
    public void add() throws Exception {
        JSONObject startingData = new JSONObject();
        JSONObject defaultDish = new JSONObject();
        JSONObject categoryDish = (JSONObject) RemoteManager.getInstance().getRemoteServicesManager().getCategoryService().readAll().get("data");
        defaultDish.put("name", "");

        defaultDish.put("dishCategory", categoryDish.get(0));
        startingData.put("first", new JSONObject(defaultDish));

        defaultDish.put("dishCategory", categoryDish.get(1));
        startingData.put("second", new JSONObject(defaultDish));

        defaultDish.put("dishCategory", categoryDish.get(2));
        startingData.put("side", new JSONObject(defaultDish));

        defaultDish.put("dishCategory", categoryDish.get(3));
        startingData.put("sweet", new JSONObject(defaultDish));

        menuTableView.getItems().add(new Menu(this, startingData));
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Menu> list = search();
            ObservableList<Menu> items = FXCollections.observableArrayList(list);
            menuTableView.setItems(items);
        } catch (Exception e) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @Override
    public void delete(AbstractRowModel abstractRowModel) {
        menuTableView.getItems().remove(abstractRowModel);
    }
}
