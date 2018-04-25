package Client.Controller;


import Client.ControllerManager;
import Client.Model.Adults;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;


public class AddAdultsController extends AbstractTableController{
    @FXML private Button buttonClose;
    @FXML private TableView<Adults> allParentTableView;

    public AddAdultsController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getAdultService() );
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Adults> list = search();
            ObservableList<Adults> items = FXCollections.observableArrayList(list);
            allParentTableView.setItems(items);
        } catch (Exception e ) {
            //todo render error
        }
    }

    @FXML private void remove() {
        try {
            ControllerManager.getInstance().renderHome();
        }catch(Exception e) {}
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        //filters.put("name", "");
        //filters.put("surname", "");
        //todo parse form datePicker
        //filters.put("fiscalCode", "");

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Adults> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject adult = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) adult.get("id"));
            String name = (String) adult.get("name");
            String surname = (String) adult.get("surname");
            String fiscalCode = (String) adult.get("fiscalCode");
            String telephone = (String) adult.get("telephone");

            list.add(new Adults(this, id, name, surname, fiscalCode, new Date(), telephone));
        }
        return list;
    }
}
