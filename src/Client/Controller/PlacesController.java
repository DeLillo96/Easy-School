package Client.Controller;

import Client.ControllerManager;
import Client.Model.*;
import Client.Remote.RemoteManager;
//import com.sun.javafx.scene.control.DoubleField;
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

public class PlacesController extends AbstractTableController {
    /* FILTERS */
    @FXML private Text tripName;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField maxCostTextField;
    @FXML private Button searchButton;
    @FXML private Button addButton;

    @FXML private TableView<Places> placesTableView;

    private DayTrips trip;

    public PlacesController() throws Exception {
        super( RemoteManager.getInstance().getRemoteServicesManager().getPlaceService() );
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
            ArrayList<Places> allPlaces = search();
            ArrayList<Places> selectedPlaces;

            JSONObject response = RemoteManager.getInstance().getRemoteServicesManager().getPlaceService().readVisitedPlaceByTripId(trip.getId());
            if((boolean) response.get("success")) {

                JSONObject data = (JSONObject) response.get("data");
                selectedPlaces = parseIntoRows(data);

            } else throw new Exception("Read from server error");

            for (Places a:allPlaces) {
                for (Places s:selectedPlaces) {
                    if (a.getId()==(s.getId())) {
                        a.getSelect().setSelected(true);
                    }
                }
            }

            ObservableList<Places> items = FXCollections.observableArrayList(allPlaces);
            placesTableView.setItems(items);

        } catch (Exception e ) {
            //todo render error
        }
    }

    @FXML
    public void add() throws Exception {
        placesTableView.getItems().add(new Places(this, trip.getId()));
    }

    @Override
    protected JSONObject takeFilters() {
        JSONObject filters = new JSONObject();

        filters.put("name", nameTextField.getText());
        filters.put("address", addressTextField.getText());
        /*if(!(maxCostTextField.getValue()==0)) {
            filters.put("cost", maxCostTextField.getValue());
        }*/

        return filters;
    }

    @Override
    protected ArrayList parseIntoRows(JSONObject data) throws Exception {
        ArrayList<Places> list = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            JSONObject places = (JSONObject) data.get(i);

            Integer id = Integer.parseInt((String) places.get("id"));
            String name = (String) places.get("name");
            String address = (String) places.get("address");
            String cost = (String) places.get("cost");

            list.add(new Places(this, id, name, address, cost, new CheckBox(), trip.getId()));
        }
        return list;
    }

    public void remove() {
        ControllerManager.getInstance().removePopup();
    }
}
