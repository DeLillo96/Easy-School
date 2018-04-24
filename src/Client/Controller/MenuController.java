package Client.Controller;

import Client.ControllerManager;
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
    @FXML private TableView<Menu> menuTableView;

    public MenuController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getMenuService() );
    }

    @FXML
    public void initialize() {
        filter();
    }

    @Override
    protected JSONObject takeFilters() {
        //todo take value of choice-box
        return null;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Menu> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject menu = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) menu.get("id"));
            String first = (String) ((JSONObject) menu.get("first")).get("name");
            String second = (String) ((JSONObject) menu.get("second")).get("name");
            String side = (String) ((JSONObject) menu.get("side")).get("name");
            String sweet = (String) ((JSONObject) menu.get("sweet")).get("name");

            Menu newMenu = new Menu(this, id, first, second, side, sweet);
            newMenu.prepareChoiceBoxes();
            list.add(newMenu);
        }

        return list;
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Menu> list = search();
            ObservableList<Menu> items = FXCollections.observableArrayList(list);
            menuTableView.setItems(items);
        } catch (Exception e ) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }
}
