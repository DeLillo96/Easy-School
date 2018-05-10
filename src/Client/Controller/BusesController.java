package Client.Controller;

import Client.ControllerManager;
import Client.Model.Adults;
import Client.Model.Buses;
import Client.Model.Children;
import Client.Model.DayTrips;
import Client.Remote.RemoteManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusesController extends AbstractTableController {
    /* FILTERS */
    @FXML private Text tripName;
    @FXML private TextField licensePlateTextField;
    @FXML private TextField companyNameTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;

    @FXML private TableView<Buses> busesTableView;

    private DayTrips trip;

    public BusesController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getBusService() );
    }

    public void setTrip(DayTrips trip) {
        this.trip = trip;
        tripName.setText(trip.getStringName());
        filter();
    }

    @FXML
    public void initialize() { }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<Buses> allBuses = search();
            ArrayList<Buses> selectedBuses;

            JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getBusService().readUsedBusesByDayTrip(trip.getId());
            if((boolean) response.get("success")) {

                JSONObject data = (JSONObject) response.get("data");
                selectedBuses = parseIntoRows(data);

            } else throw new Exception("Read from server error");

            for (Buses a:allBuses) {
                for (Buses s:selectedBuses) {
                    if (a.getId()==(s.getId())) {
                        a.getSelect().setSelected(true);
                    }
                }
            }

            ObservableList<Buses> items = FXCollections.observableArrayList(allBuses);
            busesTableView.setItems(items);

        } catch (Exception e ) {
            //todo render error
        }
    }

    @FXML
    public void add() throws Exception {
        busesTableView.getItems().add(new Buses(this, trip.getId()));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("licensePlate", licensePlateTextField.getText());
        filters.put("surname", companyNameTextField.getText());

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Buses> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject buses = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) buses.get("id"));
            String licensePlate = (String) buses.get("licensePlate");
            String companyName = (String) buses.get("companyName");

            list.add(new Buses(this, id, licensePlate, companyName, new CheckBox(), trip.getId()));
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
