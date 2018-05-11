package Client.Controller;

import Client.ControllerManager;
import Client.Model.Children;
import Client.Model.DayTrips;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;

public class DayTripsController extends AbstractTableController {
    /* FILTERS */
    @FXML private TextField nameTextField;

    /* TABLE */
    @FXML private TableView<DayTrips> dayTripTableView;
    @FXML private TableColumn buttonsColumn;
    @FXML private TableColumn nameColumn;

    public DayTripsController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getDayTripService() );
    }

    @FXML
    public void initialize() {
        filter();
    }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<DayTrips> list = search();
            ObservableList<DayTrips> items = FXCollections.observableArrayList(list);
            dayTripTableView.setItems(items);
        } catch (Exception e ) {
            ControllerManager.getInstance().notifyError(e.getMessage());
        }
    }

    @FXML
    public void add() throws Exception {
        dayTripTableView.getItems().add(new DayTrips(this));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<DayTrips> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject dayTrip = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) dayTrip.get("id"));
            String name = (String) dayTrip.get("name");

            list.add(new DayTrips(this, id, name));
        }

        return list;
    }
}