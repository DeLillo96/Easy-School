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
        menuTableView.getItems().add(new Menu(this));
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
