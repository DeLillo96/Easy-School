package Client.Controller;

import Client.ControllerManager;
import Client.Model.*;
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

public class ArrivalBusesController extends AbstractTableController {
    /* FILTERS */
    @FXML private Text placeName;
    @FXML private TextField licensePlateTextField;
    @FXML private TextField companyNameTextField;
    @FXML private Button searchButton;

    @FXML private TableView<ArrivalBuses> arrivalBusesTableView;

    private int tripId;
    private Places place;

    public ArrivalBusesController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getBusService() );
    }

    public void setValues(int tripId, Places place) {
        this.tripId = tripId;
        this.place = place;
        placeName.setText(place.getStringName());
        filter();
    }

    @FXML
    public void initialize() { }

    @FXML
    @Override
    public void filter() {
        try {
            ArrayList<ArrivalBuses> allBuses;
            ArrayList<ArrivalBuses> selectedBuses;

            JSONObject allBusesResponse = RemoteManager.getInstance().getRemoteServicesManager().getBusService().readUsedBusesByDayTrip(tripId);
            if((boolean) allBusesResponse.get("success")) {

                JSONObject data = (JSONObject) allBusesResponse.get("data");
                allBuses = parseIntoRows(data);

            } else throw new Exception("Read from server error");

            JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getBusService().readArrivalBusesByPlace(place.getId());
            if((boolean) response.get("success")) {

                JSONObject data = (JSONObject) response.get("data");
                selectedBuses = parseIntoRows(data);

            } else throw new Exception("Read from server error");

            for (ArrivalBuses a:allBuses) {
                for (ArrivalBuses s:selectedBuses) {
                    if (a.getId()==(s.getId())) {
                        a.getSelect().setSelected(true);
                    }
                }
            }

            ObservableList<ArrivalBuses> items = FXCollections.observableArrayList(allBuses);
            arrivalBusesTableView.setItems(items);

        } catch (Exception e ) {
            //todo render error
        }
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
        ArrayList<ArrivalBuses> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject buses = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) buses.get("id"));
            String licensePlate = (String) buses.get("licensePlate");
            String companyName = (String) buses.get("companyName");

            list.add(new ArrivalBuses(this, id, licensePlate, companyName, new CheckBox(), place.getId()));
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
