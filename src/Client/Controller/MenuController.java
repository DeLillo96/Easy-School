package Client.Controller;

import Client.Model.Menu;
import Client.Remote.RemoteManager;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MenuController extends AbstractTableController {

    public MenuController() throws Exception {
        super(RemoteManager.getInstance().getRemoteServicesManager().getMenuService());
    }

    @FXML
    public void initialize() {
        filter();
    }

    /**
     * Generates a new Menu model in order to add it to the tableView as a new row
     * @throws Exception
     */
    @FXML
    public void add() throws Exception {
        addIntoTable(new Menu(this));
    }

    @Override
    protected JSONObject takeFilters() {
        return new JSONObject();
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

}
